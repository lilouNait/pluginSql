package net.betclic.cicd.issuesearcher;

import JaxbClasses.Rule;
import JaxbClasses.SqlRules;
import net.betclic.cicd.antlr.AntlrContext;
import net.betclic.cicd.measures.ParseTreeNode;
import net.betclic.cicd.measures.SqlIssue;
import net.betclic.cicd.measures.SqlIssuesList;
import net.betclic.cicd.rules.TSQLRules;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.StringUtils;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.betclic.cicd.issuesearcher.IssuesSearcher.GrantContext.*;

/**
 * @Author: Lilia Nait Amara
 * @Version
 * @since: 06-2021
 * Class that searches issues using the tree of antlrContext with an iterative way. Rules are defined in TsqlRules
 */

public class IssuesSearcher {

    private static final Logger LOGGER = Loggers.get(IssuesSearcher.class);

    /**
     * This method is the entry point of the analyzer done using an iterative way to search violations from the parsed text AntlrContexte
     * The method searchViolations search issues from rules using the parsed text antlrContext
     * The method checkFileDescription check if the sql file contains a description and returns an issue if not. The text used without parsing in this method
     *
     * @param antlrContext : context that contains the file Contents parsed + the rules defined in the quality profile
     * @return sqlIssuesList: list of issues founded
     */
    public synchronized SqlIssuesList iterativeAnalyzer(AntlrContext antlrContext, InputFile inputFile) {

        SqlIssuesList sqlIssuesList = new SqlIssuesList();
        try {
            searchViolations(antlrContext, sqlIssuesList, inputFile);
            checkFileDescription(antlrContext.getBasicText(), sqlIssuesList);
        } catch (Exception e) {
            LOGGER.warn("Unexpected exception while doing method analyze", e);
        }
        return sqlIssuesList;
    }

    /**
     * Search and add issue if founded
     *
     * @param antlrContext
     * @param sqlIssuesList
     */
    private synchronized void searchViolations(AntlrContext antlrContext, SqlIssuesList sqlIssuesList, InputFile inputFile) {
        ParseTree tree = antlrContext.getTree();
        GrantContext grantContext = new GrantContext();
        grantContext.setGoGroupOpen(false);

        for (int currentNode = 0; currentNode < tree.getChildCount(); currentNode++) {

            if (tree.getChild(currentNode) != null && tree.getChild(currentNode).getText() != null) {
                findIssuesUsingJavaAlgorithm(antlrContext, sqlIssuesList, inputFile, grantContext, currentNode);
            }
        }

        findIssuesUsingRegex(antlrContext, sqlIssuesList, inputFile);
    }

    private synchronized void findIssuesUsingJavaAlgorithm(AntlrContext antlrContext, SqlIssuesList sqlIssuesList, InputFile inputFile, GrantContext grantContext, int currentNode) {
        String fileName = inputFile.filename();
        ParseTree tree = antlrContext.getTree();
        //Check Trigger rule
        Rule triggerRule = TSQLRules.createCreationTriggerRule();
        if (tree.getChild(currentNode).getText().toUpperCase(Locale.ROOT).startsWith(triggerRule.getRuleImplementation().getNames().getTextItem().get(0))) {
            SqlIssue sqlIssue = checkIssueFromTriggerRule(tree, currentNode, triggerRule);
            addIssue(sqlIssuesList, sqlIssue);
        }
        //Check Function rule
        Rule functionRule = TSQLRules.createCreateFunctionRule();
        if (tree.getChild(currentNode).getText().toUpperCase(Locale.ROOT).startsWith(functionRule.getRuleImplementation().getNames().getTextItem().get(0))) {
            SqlIssue sqlIssue = checkIssueFromFunctionRule(tree, currentNode, functionRule);
            addIssue(sqlIssuesList, sqlIssue);
        }
        //check Grant rule
        //I have to correct this code (to not erase)
        Rule grantRule = TSQLRules.createGrantRule();
        checkIssuesFromGrantStartAndFinishByGoInGrant(antlrContext, sqlIssuesList, grantContext, currentNode);

        //Check if schema is correct in the Grant statement
        checkIssuesFromSchemasGrantRule(antlrContext, sqlIssuesList, grantContext, currentNode, grantRule, fileName);

        //check create procedure rule
        Rule procedureRule = TSQLRules.createCreationProcedureRule();
        checkIssueFromCreateProcedureRule(antlrContext, sqlIssuesList, currentNode, procedureRule, fileName);
    }

    private synchronized void findIssuesUsingRegex(AntlrContext antlrContext, SqlIssuesList sqlIssuesList, InputFile inputFile) {
        for (SqlRules sqlRules : antlrContext.getRules()) {
            Rule rule = sqlRules.getRules().get(0);

            if (inputFile.uri() != null && rule.getConditionContainedInFileName() != null &&
                    rule.getContextRegex() != null && rule.getListIssuesRegex() != null) {
                checkIssuesFromRegexInSpecificFiles(antlrContext, sqlIssuesList, inputFile, rule);
            } else {
                if (rule.getContextRegex() != null && rule.getListIssuesRegex() != null && rule.getConditionContainedInFileName() == null
                        && rule.getConditionContainedInFile() == null) {
                    checkIssueFromRegex(antlrContext.getTextWithoutComments(), sqlIssuesList, rule, BehaviourAction.FAIL_IF_FOUND, null);
                }
            }
            if (rule.getConditionContainedInFile() != null && rule.getBehaviourIfConditionFileFound() != null) {
                checkIssuesFromRegexWithSpecificCondition(antlrContext, sqlIssuesList, rule, rule.getConditionContainedInFile(), rule.getBehaviourIfConditionFileFound());
            }
        }
    }

    private synchronized void checkIssuesFromRegexWithSpecificCondition(AntlrContext antlrContext, SqlIssuesList sqlIssuesList, Rule rule, String condition, BehaviourAction behaviourAction) {

        Pattern patternCondition = Pattern.compile(condition, Pattern.DOTALL);
        Matcher matcherCondition = patternCondition.matcher(antlrContext.getTextWithoutComments());
        if (matcherCondition.find()) {
            checkIssueFromRegex(antlrContext.getTextWithoutComments(), sqlIssuesList, rule, behaviourAction, matcherCondition);
        }
    }

    private synchronized void checkIssuesFromRegexInSpecificFiles(AntlrContext antlrContext, SqlIssuesList sqlIssuesList, InputFile inputFile, Rule rule) {
        String[] directories = inputFile.uri().getPath().split("/");
        for (int i = 1; i < directories.length; i++) {
            if (directories[i].endsWith(".sql")) {
                String directoryFileName = directories[i - 1];
                if (directoryFileName.toUpperCase(Locale.ROOT).contains(rule.getConditionContainedInFileName())) {
                    checkIssueFromRegex(antlrContext.getTextWithoutComments(), sqlIssuesList, rule, BehaviourAction.FAIL_IF_FOUND, null);

                }
            }
        }
    }

    private synchronized void checkIssueFromRegex(String text, SqlIssuesList sqlIssuesList, Rule rule, BehaviourAction behaviourAction, Matcher matcherCondition) {
        Pattern pattern = Pattern.compile(rule.getContextRegex(), Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            int line = lineNumberFromIndex(text, matcher.toMatchResult().start());
            String content = matcher.toMatchResult().group();

            for (String regex : rule.getListIssuesRegex()) {
                Pattern pattern1 = Pattern.compile(regex, Pattern.DOTALL);
                Matcher matcher1 = pattern1.matcher(content);
                if (behaviourAction == BehaviourAction.FAIL_IF_FOUND) {

                    while (matcher1.find()) {
                        createIssueFromRegex(sqlIssuesList, rule, line, content, matcher1);
                    }
                } else if (behaviourAction == BehaviourAction.FAIL_IF_NOT_FOUND && !matcher1.find()) {
                    createIssueFromRegex(sqlIssuesList, rule, line, text, matcherCondition);
                }

            }
        }
    }

    /**
     * Class that will allow to know if there is a first Grant seen in the antlrTree or not
     * Used in Grant rule
     */

    public class GrantContext {
        private boolean goGroupOpen = false;
        public static final String STATEMENT_GRANT = "GRANT";
        public static final String STATEMENT_GO = "GO";
        public static final String KEYWORD_GRANTTYPE = "TYPE:";
        private GrantType grantType;

        public boolean isGoGroupOpen() {
            return goGroupOpen;
        }

        public void setGoGroupOpen(boolean goGroupOpen) {
            this.goGroupOpen = goGroupOpen;
        }

        public GrantType getGrantType() {
            return grantType;
        }

        public void setGrantType(GrantType grantType) {
            this.grantType = grantType;
        }
    }

    private void createIssueFromRegex(SqlIssuesList sqlIssuesList, Rule rule, int line, String content, Matcher matcher1) {
        SqlIssue sqlIssue = new SqlIssue();
        sqlIssue.setExternal(false);
        sqlIssue.setAdhoc(true);
        initializeIssue(line, rule, sqlIssue);
        addIssue(sqlIssuesList, sqlIssue);
    }


    private int lineNumberFromIndex(String text, int index) {
        return StringUtils.countMatches(text.substring(0, index), '\n') + 1;

    }


    private void checkIssueFromCreateProcedureRule(AntlrContext antlrContext, SqlIssuesList sqlIssuesList, int currentNode, Rule procedureRule, String fileName) {
        ParseTree tree = antlrContext.getTree();
        String currentNodeChild = tree.getChild(currentNode).getText().toUpperCase(Locale.ROOT);
        if (currentNodeChild.startsWith("CREATEPROC") ||
                tree.getChild(currentNode).getText().toUpperCase(Locale.ROOT).startsWith("CREATEPROCEDURE")) {
            createIssue(tree, sqlIssuesList, currentNode, procedureRule);
        }
        if (currentNodeChild.startsWith("CREATEOR") && tree.getChild(currentNode + 2) != null) {
            String secondNodeChild = tree.getChild(currentNode + 2).getText().toUpperCase(Locale.ROOT);
            if (tree.getChild(currentNode + 3) != null && tree.getChild(currentNode + 4) != null
                    && secondNodeChild.startsWith("ALTERPROCEDURE")) {
                String schemaProcedureNode = tree.getChild(currentNode + 3).getText().toUpperCase(Locale.ROOT);
                String nameProcedureNode = tree.getChild(currentNode + 4).getText().toUpperCase(Locale.ROOT);
                if (!(schemaProcedureNode.startsWith("[") &&
                        schemaProcedureNode.endsWith("].") &&
                        nameProcedureNode.startsWith("[") &&
                        nameProcedureNode.contains("]"))) {
                    createIssue(tree, sqlIssuesList, currentNode, procedureRule);
                } else {
                    //Check SchemaObject Rule in stored procedure
                    Rule schemaProcedureRule = TSQLRules.createSchemaObjectCreateProcedureRule();
                    checkIssuesFromSchemaObjectRule(schemaProcedureNode, nameProcedureNode, currentNode, sqlIssuesList, antlrContext, fileName, schemaProcedureRule);
                    //Check GrantMissing Rule in Stored procedure
                    checkIssuesFromGrantMissingRule(schemaProcedureNode, nameProcedureNode, antlrContext, sqlIssuesList, currentNode);
                }
            } else if (secondNodeChild.startsWith("ALTERPROC")) {
                createIssue(tree, sqlIssuesList, currentNode, procedureRule);
            }
        }
    }

    private void checkIssuesFromGrantMissingRule(String schemaProcedureNode, String nameProcedureNode, AntlrContext antlrContext, SqlIssuesList sqlIssuesList, int currentNode) {
        String finalSchemaWithoutBrackets = schemaProcedureNode.replaceAll("\\[|\\].", "");
        String finalNameWithoutBrackets = nameProcedureNode.replaceAll("\\[|\\].*", "");

        Pattern pattern = Pattern.compile("GRANT\\s*(EXECUTE|EXEC)\\s*(.*?)\\s*\\[" + finalSchemaWithoutBrackets + "\\].\\[" + finalNameWithoutBrackets + "\\]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(antlrContext.getTextWithoutComments());
        if (!matcher.find()) {
            Rule grantMissingRule = TSQLRules.createGrantMissingRule();
            createIssue(antlrContext.getTree(), sqlIssuesList, currentNode, grantMissingRule);
        }
    }

    private void checkIssuesFromSchemaObjectRule(String schema, String name, int currentNode, SqlIssuesList sqlIssuesList, AntlrContext antlrContext, String fileName, Rule rule) {
        ParseTree tree = antlrContext.getTree();
        if (fileName != null && fileName.contains(".")) {
            //We split when finding a point to separate the schema from the name of the file
            String[] fileNameSplit = fileName.split("\\.");
            //We erase the brackets using a simple regex from the schema and name procedure to compare it to the schema found in the file name

            String finalSchema = schema.replaceAll("\\[|\\].", "");
            String finalName = name.replaceAll("\\[|\\].*", "");
            if (fileNameSplit.length > 1) {
                String finalSchemaFromFile = fileNameSplit[0].toUpperCase(Locale.ROOT);
                String finalNameFromFile = fileNameSplit[1].toUpperCase(Locale.ROOT);
                if (!(finalNameFromFile.equals(finalName) && finalSchemaFromFile.equals(finalSchema))) {
                    createIssue(tree, sqlIssuesList, currentNode, rule);
                }
            }

        }
    }

    private void createIssue(ParseTree tree, SqlIssuesList sqlIssuesList, int currentNode, Rule procedureRule) {
        SqlIssue sqlIssue = new SqlIssue();
        sqlIssue.setDebtRemediationEffort(2);
        initializeIssue(new ParseTreeNode(tree.getChild(currentNode)).getLine(), procedureRule, sqlIssue);
        sqlIssue.setExternal(false);
        sqlIssue.setAdhoc(true);
        addIssue(sqlIssuesList, sqlIssue);
    }

    private void checkIssuesFromSchemasGrantRule(AntlrContext antlrContext, SqlIssuesList sqlIssuesList, GrantContext grantContext, int currentNode, Rule grantRule, String fileName) {
        ParseTree tree = antlrContext.getTree();
        if (tree.getChild(currentNode).getText().toUpperCase(Locale.ROOT).equals(
                "GRANT")) {
            if (tree.getChild(currentNode + 2) != null && tree.getChild(currentNode + 3).getText() != null &&
                    !tree.getChild(currentNode + 3).getText().toUpperCase(Locale.ROOT).equals(KEYWORD_GRANTTYPE)) {
                checkOnClassicGrant(antlrContext, currentNode, fileName, sqlIssuesList);
                grantContext.setGrantType(GrantType.CLASSIC);
            } else if (tree.getChild(currentNode + 2) != null && tree.getChild(currentNode + 3).getText() != null &&
                    tree.getChild(currentNode + 3).getText().toUpperCase(Locale.ROOT).startsWith(KEYWORD_GRANTTYPE)) {
                checkOnTypeGrant(antlrContext, currentNode, fileName, sqlIssuesList);
                grantContext.setGrantType(GrantType.TYPE);
            }
        }
    }

    private void checkIssuesFromGrantStartAndFinishByGoInGrant(AntlrContext antlrContext, SqlIssuesList sqlIssuesList, GrantContext grantContext, int currentNode) {
        ParseTree tree = antlrContext.getTree();
        SqlIssue sqlIssue = checkIssueFromGoGrantRule(tree, currentNode, grantContext);
        addIssue(sqlIssuesList, sqlIssue);
    }

    private void addIssue(SqlIssuesList sqlIssuesList, SqlIssue sqlIssue) {
        if (sqlIssue != null) {
            sqlIssuesList.addIssue(sqlIssue);
        }
    }

    /**
     * Inspect if the rule SG-016 function is respecte
     * Rule SG-016 exmaple code compliant: create function [xx].[xx]
     * Rule SG-016 example code not compliant: create function xx
     *
     * @param tree
     * @param currentNode
     * @param functionRule
     * @return sqlIssue
     */
    private SqlIssue checkIssueFromFunctionRule(ParseTree tree, int currentNode, Rule functionRule) {
        SqlIssue sqlIssue = null;
        if (!((tree.getChild(currentNode + 1).getText().toUpperCase(Locale.ROOT).startsWith("[") && tree.getChild(currentNode + 1).getText().toUpperCase(Locale.ROOT).endsWith("].")) ||
                (tree.getChild(currentNode + 2).getText().toUpperCase(Locale.ROOT).startsWith("[")) && tree.getChild(currentNode + 2).getText().toUpperCase(Locale.ROOT).endsWith("]"))) {
            sqlIssue = new SqlIssue();
            sqlIssue.setDebtRemediationEffort(2);
            initializeIssue(new ParseTreeNode(tree.getChild(currentNode)).getLine(), functionRule, sqlIssue);
            sqlIssue.setExternal(false);
            sqlIssue.setAdhoc(true);
        }
        return sqlIssue;
    }

    /**
     * Inspect if the rule SG-017 is respected or not
     * Example code compliant of this rule : create trigger [xx].[u_xx] or create trigger [xx].[i_xx] or create trigger [xx].[d_xx]
     *
     * @param tree
     * @param currentNode
     * @param triggerRule
     * @return sqlIssue
     */
    private SqlIssue checkIssueFromTriggerRule(ParseTree tree, int currentNode, Rule triggerRule) {
        SqlIssue sqlIssue = null;
        if (tree.getChild(currentNode + 1) != null && tree.getChild(currentNode + 2) != null &&
                !(tree.getChild(currentNode + 1).getText().toUpperCase(Locale.ROOT).startsWith("[") &&
                        tree.getChild(currentNode + 1).getText().toUpperCase(Locale.ROOT).endsWith("].") &&
                        (tree.getChild(currentNode + 2).getText().toUpperCase(Locale.ROOT).startsWith("[U_") ||
                                tree.getChild(currentNode + 2).getText().toUpperCase(Locale.ROOT).startsWith("[D_") ||
                                tree.getChild(currentNode + 2).getText().toUpperCase(Locale.ROOT).startsWith("[I_")))) {
            sqlIssue = new SqlIssue();
            initializeIssue(new ParseTreeNode(tree.getChild(currentNode)).getLine(), triggerRule, sqlIssue);
            sqlIssue.setDebtRemediationEffort(2);
            sqlIssue.setExternal(false);
            sqlIssue.setAdhoc(true);
        }
        return sqlIssue;
    }

    /**
     * Inspect if the SG-009 rule is respected or note
     * Compliant code:
     * Non compliant code:
     *
     * @param currentNode
     * @return sqlIssue
     */

    private Boolean checkOnTypeGrant(AntlrContext antlrContext, int currentNode, String fileName, SqlIssuesList sqlIssuesList) {
        ParseTree tree = antlrContext.getTree();
        boolean grantCompliant = true;
        if (tree.getChild(currentNode + 2) != null && tree.getChild(currentNode + 2).getText().toUpperCase(Locale.ROOT).equals("EXECUTEON") |
                tree.getChild(currentNode + 2) != null && tree.getChild(currentNode + 2).getText().toUpperCase(Locale.ROOT).equals("EXECON")) {
            if (tree.getChild(currentNode + 3) != null &&
                    tree.getChild(currentNode + 4) != null && tree.getChild(currentNode + 5) != null &&
                    !(tree.getChild(currentNode + 3).getText().toUpperCase(Locale.ROOT).startsWith(KEYWORD_GRANTTYPE) &&
                            tree.getChild(currentNode + 4).getText().toUpperCase(Locale.ROOT).startsWith(":") &&
                            tree.getChild(currentNode + 5).getText().toUpperCase(Locale.ROOT).startsWith("[") &&
                            tree.getChild(currentNode + 5).getText().toUpperCase(Locale.ROOT).endsWith("].") &&
                            tree.getChild(currentNode + 6).getText().toUpperCase(Locale.ROOT).startsWith("[") &&
                            tree.getChild(currentNode + 6).getText().toUpperCase(Locale.ROOT).endsWith("]TO") &&
                            tree.getChild(currentNode + 7).getText().toUpperCase(Locale.ROOT).startsWith("[") &&
                            tree.getChild(currentNode + 7).getText().toUpperCase(Locale.ROOT).contains("]") &&
                            tree.getChild(currentNode + 7).getText().toUpperCase(Locale.ROOT).endsWith(";"))) {
                grantCompliant = false;
            }
            if (grantCompliant) {
                initializeIssueFromSchemaGrantRule(antlrContext, currentNode, fileName, sqlIssuesList, 5, 6);
            }
        }
        return grantCompliant;
    }


    private boolean checkOnClassicGrant(AntlrContext antlrContext, int currentNode, String fileName, SqlIssuesList sqlIssuesList) {
        ParseTree tree = antlrContext.getTree();
        boolean grantCompliant = true;
        if (tree.getChild(currentNode + 2) != null && tree.getChild(currentNode + 2).getText().toUpperCase(Locale.ROOT).equals("EXECUTEON") |
                tree.getChild(currentNode + 2).getText().toUpperCase(Locale.ROOT).equals("EXECON")) {
            if (tree.getChild(currentNode + 3) != null &&
                    tree.getChild(currentNode + 4) != null && tree.getChild(currentNode + 5) != null &&
                    !(tree.getChild(currentNode + 3).getText().toUpperCase(Locale.ROOT).startsWith("[") &&
                            tree.getChild(currentNode + 3).getText().toUpperCase(Locale.ROOT).endsWith("].") &&
                            tree.getChild(currentNode + 4).getText().toUpperCase(Locale.ROOT).startsWith("[") &&
                            tree.getChild(currentNode + 4).getText().toUpperCase(Locale.ROOT).endsWith("]TO") &&
                            tree.getChild(currentNode + 5).getText().toUpperCase(Locale.ROOT).startsWith("[") &&
                            tree.getChild(currentNode + 5).getText().toUpperCase(Locale.ROOT).contains("]") &&
                            tree.getChild(currentNode + 5).getText().toUpperCase(Locale.ROOT).endsWith(";"))) {
                grantCompliant = false;
            }
            if (grantCompliant) {
                initializeIssueFromSchemaGrantRule(antlrContext, currentNode, fileName, sqlIssuesList, 3, 4);
            }
        }
        return grantCompliant;
    }

    private void initializeIssueFromSchemaGrantRule(AntlrContext antlrContext, int currentNode, String fileName, SqlIssuesList sqlIssuesList, int schemaNodeRange, int nameNodeRange) {
        ParseTree tree = antlrContext.getTree();
        Rule schemaObjectGrantRule = TSQLRules.createSchemaObjectGrantRule();
        String schema = tree.getChild(currentNode + schemaNodeRange).getText().toUpperCase(Locale.ROOT);
        String name = tree.getChild(currentNode + nameNodeRange).getText().toUpperCase(Locale.ROOT);
        checkIssuesFromSchemaObjectRule(schema, name, currentNode, sqlIssuesList, antlrContext, fileName, schemaObjectGrantRule);
    }

    /**
     * @param tree
     * @param currentNode
     * @param grantContext
     * @return sqlIssue
     */
    private SqlIssue checkIssueFromGoGrantRule(ParseTree tree, int currentNode, GrantContext grantContext) {

        SqlIssue sqlIssue = null;
        if (tree != null && tree.getChild(currentNode + 1) != null) {
            if (tree.getChild(currentNode).getText().toUpperCase(Locale.ROOT).startsWith(STATEMENT_GO)) {
                grantContext.setGoGroupOpen(true);
            }
            if (tree.getChild(currentNode).getText().toUpperCase(Locale.ROOT).startsWith(STATEMENT_GRANT))
                sqlIssue = caseGrantCheck(tree, currentNode, grantContext);
        }
        return sqlIssue;
    }

    private SqlIssue caseGrantCheck(ParseTree tree, int currentNode, GrantContext grantContext) {
        SqlIssue sqlIssue = null;
        Rule grantGoRule = TSQLRules.createGrantGoRule();
        if (!grantContext.isGoGroupOpen()) {
            sqlIssue = new SqlIssue();
            initializeIssue(new ParseTreeNode(tree.getChild(currentNode)).getLine(), grantGoRule, sqlIssue);
            sqlIssue.setDebtRemediationEffort(2);
            sqlIssue.setExternal(false);
            sqlIssue.setAdhoc(true);
        }
        return sqlIssue;
    }


    /**
     * This method inject all the informations necessary to raise an issue when found
     *
     * @param line
     * @param ruleDescriptor
     * @param sqlIssue
     */
    private void initializeIssue(int line, Rule ruleDescriptor, SqlIssue sqlIssue) {
        if (ruleDescriptor != null && ruleDescriptor.getRuleImplementation() != null) {
            SqlRules tsqlRules = null;
            try {
                tsqlRules = TSQLRules.getRules().get(0);
            } catch (Exception e) {
                LOGGER.warn("Unpexcted exception while trying to get rules");
            }
            sqlIssue.setLine(line);
            sqlIssue.setRepo(tsqlRules.getRepoKey());
            sqlIssue.setDescription(ruleDescriptor.getDescription());
            sqlIssue.setKey(ruleDescriptor.getKey());
            sqlIssue.setSeverity(ruleDescriptor.getSeverity());
            sqlIssue.setName(ruleDescriptor.getName());
            sqlIssue.setMessage(ruleDescriptor.getRuleImplementation().getRuleViolationMessage());
        }
    }

    /**
     * This method inspect if each sql file contain a specific description
     * Example sql file compliant: : /*Title Description Paramenters History * / select * from db
     * Example sql file not compliant : select * from DB
     *
     * @param text
     * @param sqlIssuesList
     */
    private void checkFileDescription(String text, SqlIssuesList sqlIssuesList) throws IOException, URISyntaxException {
        boolean desc = false;
        if (text != null && text.contains("/*") && (text.toUpperCase(Locale.ROOT).contains("DESCRIPTION") && text.toUpperCase(Locale.ROOT).contains("PARAMETERS") &&
                text.toUpperCase(Locale.ROOT).contains("TITLE") && text.toUpperCase(Locale.ROOT).contains("HISTORY"))) {
            desc = true;
        }
        if (!desc) {
            SqlIssue sqlIssue = new SqlIssue();
            sqlIssue.setDebtRemediationEffort(2);
            sqlIssue.setLine(1);
            sqlIssue.setRepo(TSQLRules.getRules().get(0).getRepoKey());
            sqlIssue.setDescription(TSQLRules.createHeaderRule().getDescription());
            sqlIssue.setKey(TSQLRules.createHeaderRule().getKey());
            sqlIssue.setSeverity(TSQLRules.createHeaderRule().getSeverity());
            sqlIssue.setName(TSQLRules.createHeaderRule().getName());
            sqlIssue.setMessage(TSQLRules.createHeaderRule().getRuleImplementation().getRuleViolationMessage());
            sqlIssue.setExternal(false);
            sqlIssue.setAdhoc(true);
            sqlIssuesList.addIssue(sqlIssue);
        }

    }

}
