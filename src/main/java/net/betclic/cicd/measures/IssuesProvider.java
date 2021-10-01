package net.betclic.cicd.measures;

import JaxbClasses.RuleImplementation;
import net.betclic.cicd.antlr.AntlrContext;
import net.betclic.cicd.scanner.IParsedNode;
import org.apache.commons.lang3.StringUtils;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import java.util.List;
import java.util.Map;

public class IssuesProvider {
    private static final Logger LOGGER = Loggers.get(IssuesProvider.class);
    private final ViolationsAnalyzer violationsAnalyzer = new ViolationsAnalyzer();
    private final ViolationsSearcher searcher = new ViolationsSearcher();

    public SqlIssuesList recursiveAnalyzer(AntlrContext ctx) {
        final SqlIssuesList list = new SqlIssuesList();
        final RulesMatchingVisitor visitor = new RulesMatchingVisitor(ctx.getRules());
        try {
            visitor.visit(ctx.getTree());
            List<FoundMatch> matches = visitor.getMatches();
            for (FoundMatch m : matches) {
                Map<RuleImplementation, List<IParsedNode>> checkedRules = searcher.search(m);
                final ViolationsAnalyzer.FoundViolation violations = violationsAnalyzer.isMatch(checkedRules);

                if (violations.isFailuresFound()) {
                    SqlIssue e = mapToSqlIssue(m);
                    list.addIssue(e);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Unexpected exception while doing method analyze", e);
        }
        return list;
    }

    protected static SqlIssue mapToSqlIssue(FoundMatch foundMatch) {
        SqlIssue sqlIssue = new SqlIssue();
        sqlIssue.setRepo(foundMatch.getSqlRules().getRepoKey());
        sqlIssue.setLine(foundMatch.getLine());
        try {
            if (StringUtils.equalsIgnoreCase("LINEAR", foundMatch.getRule().getRemediationFunction())
                    && !StringUtils.isBlank(foundMatch.getRule().getDebtRemediationFunctionCoefficient())) {
                sqlIssue.setDebtRemediationEffort(org.sonar.api.utils.Duration
                        .decode(foundMatch.getRule().getDebtRemediationFunctionCoefficient(), 8).toMinutes());
            }
        } catch (Exception p) {
            LOGGER.warn("Unexpected exception while setting debtRemediationEffort");
        }
        sqlIssue.setDescription(foundMatch.getRule().getDescription());
        sqlIssue.setRuleType(foundMatch.getRule().getRuleType());
        sqlIssue.setKey(foundMatch.getRule().getKey());
        sqlIssue.setSeverity(foundMatch.getRule().getSeverity());
        sqlIssue.setAdhoc(foundMatch.getSqlRules().isIsAdhoc());
        sqlIssue.setMessage(foundMatch.getRule().getRuleImplementation().getRuleViolationMessage());
        sqlIssue.setName(foundMatch.getRule().getName());
        sqlIssue.setExternal(foundMatch.getSqlRules().getExternal());

        return sqlIssue;
    }
}
