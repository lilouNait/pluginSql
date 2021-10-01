package net.betclic.cicd.scanner;

import net.betclic.cicd.measures.SqlIssue;
import net.betclic.cicd.measures.SqlIssuesList;
import net.betclic.cicd.settings.Constants;
import org.apache.commons.lang3.StringUtils;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.Severity;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewExternalIssue;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.rules.RuleType;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.*;

public class IssueUtil {
    private static final Logger LOGGER = Loggers.get(IssueUtil.class);

    protected IssueUtil() {
        LOGGER.info("BaseSensor class");
    }
    public static List<InputFile> find(SensorContext context, String path) throws IOException {
        FilePredicates p = context.fileSystem().predicates();
        Set<InputFile> files = new HashSet<>();
        URI uri = new File(path).getCanonicalFile().toURI();

        context.fileSystem().inputFiles(p.hasLanguage(Constants.LANGUAGE_KEY)).forEach(inputFile -> {
            if (uri.equals(inputFile.uri())) {
                files.add(inputFile);
            }
        });
        return new ArrayList<>(files);
    }

    public static synchronized void addIssues(SensorContext context, final SqlIssuesList issues,
                                              final InputFile file) throws IOException {
        if(context.config()!=null){
        final List<String> rulesToSkip = Arrays
                .asList(context.config().getStringArray(Constants.PLUGIN_SQL_RULES_SKIP));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Found {} issues", issues.getAllIssues().size());
        }

        for (final Map.Entry<String, Set<SqlIssue>> fileIssues : issues.getIssues().entrySet()) {

            String fileName = fileIssues.getKey();

            InputFile main = file;
            if (main == null) {
                final List<InputFile> files = find(context, fileName);
                main = files.get(0);
            }

            saveAddedIssues(context, rulesToSkip, fileIssues, fileName, main);

        }
    }
    }

    private static void saveAddedIssues(SensorContext context, List<String> rulesToSkip, Map.Entry<String
            , Set<SqlIssue>> fileIssues, String fileName, InputFile main) {
        for (final SqlIssue issue : fileIssues.getValue()) {
            try {

                if ((rulesToSkip.contains(issue.getUniqueKey())) || (issue.isExternal())) {
                    if (issue.isExternal()) {
                        issueExternal(context, main, issue);
                    }
                    continue;
                }
                if (issue.isAdhoc()) {
                    issueAdhoc(context, issue);
                }
                final NewIssue newIssue = context.newIssue().forRule(RuleKey.of(issue.getRepo(), issue.getKey()));
                final NewIssueLocation loc = newIssue.newLocation().on(main).message(issue.getMessage());
                if (issue.getLine() > 0) {
                    loc.at(main.selectLine(issue.getLine()));
                }
                saveIssue(newIssue, loc);
            } catch (Exception e) {
                LOGGER.warn("Unexpected error adding issue on file " + fileName, e);
            }
        }
    }

    private static void saveIssue(NewIssue newIssue, NewIssueLocation loc) {
        try {
            NewIssue newIssueAtLoc = newIssue.at(loc);
            newIssueAtLoc.save();
        } catch (Exception e) {
            LOGGER.warn("save not donne " + e);
        }
    }

    private static void issueAdhoc(SensorContext context, SqlIssue issue) {
        context.newAdHocRule().description(issue.getDescription()).engineId(issue.getRepo())
                .name(issue.getName()).ruleId(issue.getKey())
                .severity(extractSeverity(issue.getSeverity()))
                .type(RuleType.valueOf(issue.getRuleType())).save();
    }

    private static void issueExternal(SensorContext context, InputFile main, SqlIssue issue) {
        final NewExternalIssue newExternalIssue = context.newExternalIssue().engineId(issue.getKey())
                .engineId(issue.getRepo()).type(RuleType.valueOf(issue.getRuleType()));
        if (issue.getDebtRemediationEffort() > 0) {
            newExternalIssue.remediationEffortMinutes(issue.getDebtRemediationEffort());
        }
        final NewIssueLocation location = newExternalIssue.newLocation().on(main)
                .message(issue.getMessage());
        if (issue.getLine() > 0) {
            location.at(main.selectLine(issue.getLine()));
        }
        newExternalIssue.at(location).severity(extractSeverity(issue.getSeverity())).save();
    }

    private static Severity extractSeverity(final String severityValue) {
        String severity = "MAJOR";
        if (severityValue != null) {
            severity = severityValue.toUpperCase();
        }
        if ("ERROR".equalsIgnoreCase(severity)) {
            return Severity.CRITICAL;
        }
        if (("WARNING".equalsIgnoreCase(severity)) || (StringUtils.containsIgnoreCase(severityValue, "HIGH"))) {
            return Severity.MAJOR;
        }
        if (StringUtils.containsIgnoreCase(severityValue, "LOW")) {
            return Severity.INFO;
        }
        try {
            return Severity.valueOf(severity);
        } catch (Exception e) {
            LOGGER.warn("Unexpected exception while trying to return severity", e);
        }
        return Severity.MAJOR;
    }
}
