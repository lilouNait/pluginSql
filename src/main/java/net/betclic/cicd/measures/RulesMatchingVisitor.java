package net.betclic.cicd.measures;

import JaxbClasses.RuleImplementation;
import JaxbClasses.RuleMode;
import JaxbClasses.SqlRules;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RulesMatchingVisitor extends AbstractParseTreeVisitor<Void> {
    private List<SqlRules> sqlRulesList;
    private final ClassNameMatcher matcher = new ClassNameMatcher();

    private List<FoundMatch> matches = new LinkedList<>();
    private Map<String, FoundMatch> groupMatches = new HashMap<>();

    private final TextMatcher textMatcher = new TextMatcher();

    public RulesMatchingVisitor(List<SqlRules> sqlRulesList) {
        this.sqlRulesList = sqlRulesList;
    }


    public List<FoundMatch> getMatches() {
        List<FoundMatch> found = new LinkedList<>();
        found.addAll(matches);
        found.addAll(groupMatches.values());
        return found;
    }

    @Override
    public Void visit(final ParseTree tree) {
        if (tree != null) {
            final int n = tree.getChildCount();

            for (int i = 0; i < n; i++) {
                final ParseTree c = tree.getChild(i);
                visit(c);
            }
        }
        final ParseTreeNode node = new ParseTreeNode(tree);

        sqlRulesList.forEach(rules -> rules.getRules()
                .stream().filter(rule -> {
                    RuleImplementation ruleImplementation = rule.getRuleImplementation();
                    return this.matcher.match(node, ruleImplementation) && this.textMatcher.match(node, ruleImplementation);
                })
                .forEach(rule -> {
                    FoundMatch match = new FoundMatch();
                    match.setRule(rule);
                    match.setNode(node);
                    match.setSqlRules(rules);
                    if (RuleMode.GROUP.equals(rule.getRuleImplementation().getRuleMode())) {
                        this.groupMatches.putIfAbsent(node.getText(), match);
                    } else {
                        this.matches.add(match);
                    }
                }));
        return defaultResult();
    }

}
