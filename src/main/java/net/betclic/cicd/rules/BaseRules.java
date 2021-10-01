package net.betclic.cicd.rules;

import JaxbClasses.CompliantRulesCodeExamples;
import JaxbClasses.Rule;
import JaxbClasses.RuleImplementation;
import JaxbClasses.ViolatingRulesCodeExamples;
import net.betclic.cicd.issuesearcher.BehaviourAction;

import java.util.ArrayList;
import java.util.List;

public enum BaseRules {

    INSTANCE;

    static final String TAG = "Reliability";

    public static Rule createGrantRule() {
        List<String> listIssuesRegex = new ArrayList<>();
        listIssuesRegex.add("\\s+ON\\s*\\[.*?\\].\\[.*?]\\s*TO\\s*\\[.*?\\];|\\s+ON\\s*TYPE::\\[.*?\\].\\[.*?]\\s*TO\\s*\\[.*?\\];");
        Rule rule = new Rule(
                "SG-009",
                "CREATE GRANT block is not compliant",
                "<h2>Description</h2><p>The GRANT block should always be written the same way, as defined in the code compliant.\n" +
                        "Here are the elements forbiden:\n" + "- Schema defined between brackets\n" +
                        "- Every procedure name or function must be surrounded by brackets\n" + "- Semicolon at the end", TAG, "2min",
                "GRANT\\s*EXEC\\s*ON\\s*.*?TO\\s+\\S*|GRANT\\s*EXECUTE\\s*ON\\s*.*?TO\\s+\\S*",
                listIssuesRegex,
                null,
                "GRANT\\s*EXEC\\s*ON|GRANT\\s*EXECUTE\\s*ON",
                BehaviourAction.FAIL_IF_NOT_FOUND);
        setRuleImplementation(rule,
                "CREATE GRANT block is not compliant",
                "GRANT EXECUTE ON common_users_get TO example",
                "GRANT EXECUTE ON [dbo].[common_users_get] TO [example];");
        return rule;
    }

    public Rule createGrantGoRule() {
        Rule rule = new Rule(
                "SG-005",
                "GRANT block must be preceded by a GO separator",
                "<h2>Description</h2><p>The stored procedure (function, etc) body must be separated from the associated GRANT statements by a GO separator." +
                        " Go Statement must be written before the first Grant and after the last one.", TAG, "2min");
        setRuleImplementation(rule,
                "GRANT block must be preceded by a GO separator",
                "ORDER BY EXAMPLE\n" + "GRANT EXECUTE ON [dbo].[common_users_get] TO [db_BackOffice];",
                "ORDER BY EXAMPLECOMPLIANT\n" + "GO\n" + "GRANT EXECUTE ON [dbo].[common_users_get] TO [db_BackOffice];\n" + "GO");
        return rule;
    }

    public Rule createUseRule() {
        Rule rule = new Rule(
                "SG-002",
                "Unexpected USE keyword found",
                "<h2>Description</h2><p>Using the USE keyword in SQL scripts (including stored procedures, etc) is prohibited.</p>",
                TAG,
                "2min");
        setRuleImplementation(rule,
                "Avoid USE [Database] at the beginning of the script.",
                "use [Betclick] SELECT TOP 10 * FROM dbo.users;",
                "SELECT TOP 10 * FROM dbo.users;");
        return rule;
    }

    public Rule createIdentityRule() {
        Rule rule = new Rule(
                "SG-024",
                "@@IDENTITY used",
                "<h2>Description</h2><p>The @@IDENTITY keyword is forbidden, as it will return incorrect values when triggers are used.</p>",
                TAG,
                "2min");
        setRuleImplementation(rule,
                "Unexpected @@IDENTITY keyword found",
                "SELECT @@IENTITY;",
                "SELECT SCOPE_IDENTITY();");
        return rule;
    }

    public Rule createErrorRule() {
        Rule rule = new Rule(
                "SG-025",
                "@@ERROR used",
                "<h2>Description</h2><p>The @@ERROR keyword is forbidden, as it will return incorrect values when triggers are used.</p>",
                TAG,
                "2min");
        setRuleImplementation(rule,
                "Unexpected @@ERROR keyword found",
                "SELECT @@ERROR;",
                "SELECT SCOPE_IDENTITY();");
        return rule;
    }

    public Rule createErrorHandleRule() {
        Rule rule = new Rule(
                "SG-026",
                "Unexpected error_handle call found",
                "<h2>Description</h2><p>Starting with SQL Server 2012, the THROW keyword has been introduced, meaning we don't have to use the customer error_handle procedure anymore.\n" +
                        "Please replace its use with proper THROW calls.</p>",
                TAG,
                "2min");
        setRuleImplementation(rule,
                "Unexpected error_handle call found",
                "BEGIN CATCH IF XACT_STATE() <> 0 ROLLBACK TRANSACTION\n" + "\tEXEC dbo.error_handle\n" + "END CATCH",
                "BEGIN CATCH IF XACT_STATE() <> 0 ROLLBACK TRANSACTION;\n" + "\tTHROW;\n" + "END CATCH");
        return rule;
    }

    public Rule createGrantBlockMissingRule() {
        Rule rule = new Rule(
                "SG-011",
                "GRANT block missing",
                "<h2>Description</h2><p>Stored procedures are usually executed by application logins. For this to happen," +
                        " you need to GRANT them to be executed by the proper role(s).</p>", TAG,
                "2min");
        setRuleImplementation(rule,
                "GRANT block missing",
                "CREATE OR ALTER PROCEDURE [dbo].[my_proc]",
                "CREATE OR ALTER PROCEDURE [dbo].[my_proc] ...\n" + "GRANT EXECUTE ON [dbo].[my_proc] TO [db_FrontOffice];");
        return rule;
    }

    public Rule createThrowRule() {
        Rule rule = new Rule("SG-027",
                "THROW must be preceded by a semicolon",
                "<h2>Description</h2><p>The statement preceding a THROW statement must always be ended with a semicolon.</p>",
                TAG,
                "2min");
        setRuleImplementation(rule,
                "THROW must be preceded by a semicolon",
                "THROW",
                "THROW;");
        return rule;
    }

    public Rule createProcedureRule() {
        Rule rule = new Rule(
                "SG-015",
                "CREATE PROCEDURE block is not compliant",
                "<h2>Description</h2><p>The line where the stored procedure is declared must be written the same way",
                TAG,
                "2min");
        setRuleImplementation(rule,
                "CREATE PROCEDURE block is not compliant",
                "CREATE OR ALTER PROC [dbo].[my_proc]",
                "CREATE OR ALTER PROCEDURE [dbo].[my_proc]");
        return rule;
    }

    public Rule createViewRule() {
        Rule rule = new Rule(
                "SG-018",
                "CREATE VIEW block is not compliant",
                "<h2>Description</h2><p>The line where the view is declared must always be written the same way",
                TAG,
                "2min");
        setRuleImplementation(rule,
                "CREATE VIEW block is not compliant",
                "CREATE VIEW myView",
                "CREATE VIEW [dbo].[vMyView]");
        return rule;
    }

    public Rule createTriggerRule() {
        Rule rule = new Rule(
                "SG-017",
                "CREATE TRIGGER block is not compliant",
                "<h2>Description</h2><p>The line where the trigger is declared must always be written the same way",
                TAG,
                "2min");
        setRuleImplementation(rule,
                "CREATE TRIGGER block is not compliant",
                "CREATE TRIGGER myTrigger",
                "CREATE TRIGGER [dbo].[u_Trigger]\n" + "OR\n" + "CREATE TRIGGER [dbo].[d_Trigger]\n" +
                        "OR\n" + "CREATE TRIGGER [dbo].[i_Trigger]");
        return rule;
    }

    public Rule createHeaderRule() {
        Rule rule = new Rule(
                "SG-001",
                "Header missing",
                "<h2>Description</h2><p>All scripts, stored procedure, functions, etc that you change must be tagged with the version you want the change to be part of.",
                TAG,
                "2min");
        setRuleImplementation(rule,
                "Header Missing",
                "/*\n" + "Failure,\n" + "header is missing\n" + "\n" + "*/\n" + "\n" + "SET ANSI_NULLS ON\n" +
                        "GO\n" + "SET QUOTED_IDENTIFIER ON\n" + "GO",
                "/*\n" + "Success\n" + "Header is present with\n" + "Following fields are mandatory :\n" +
                        "Title\n" + "Description\n" + "Parameters\n" + "History\n" + "*/\n" + "\n" + "SET ANSI_NULLS ON\n" +
                        "GO\n" + "SET QUOTED_IDENTIFIER ON\n" + "GO");
        return rule;
    }


    public Rule createFunctionRule() {
        Rule rule = new Rule(
                "SG-016",
                "CREATE FUNCTION block is not compliant",
                "<h2>Description</h2><p>The line where the function is declared must always be written the same way, as defined in the code compliant",
                TAG,
                "2min");
        setRuleImplementation(rule,
                "CREATE function block is not compliant",
                "CREATE FUNCTION myFunction",
                "CREATE FUNCTION [dbo].[myFunction]");
        return rule;
    }

    public Rule createPrintRule() {
        Rule rule = new Rule(
                "SG-007", "PRINT statement must be commented out",
                "<h2>Description</h2><p>Print statements are useful for debug. But we will not debug in production and they will generate useless network traffic, possibly on trans-country links.\n" + System.lineSeparator() +
                        "You should ensure no PRINT will be executed in production, and get rid of them before checking in.",
                TAG,
                "2min");

        setRuleImplementation(rule,
                "PRINT statement must be commented out",
                "PRINT 'My comment",
                "/* PRINT 'My comment' */ " + System.lineSeparator() + "OR" + System.lineSeparator() + "--PRINT 'My comment'");
        return rule;
    }

    public Rule createSchemaObjectRule() {
        Rule rule = new Rule(
                "SG-021",
                " Schema/Object name is invalid in CREATE or ALTER block in stored procedure",
                "<h2>Description</h2><p>The object and schema name referenced in the CREATE or ALTER block must match those in the file name." + System.lineSeparator() +
                        "Be aware that the comparison is case sensitive. You might encounter the issue even if there's only a casing discrepancy.",
                TAG,
                "2min");

        setRuleImplementation(rule, " Schema/Object name is invalid in CREATE or ALTER block in stored procedure",
                "Name file: OfferingProviders.ProviderSports_Get_ByProviderId.sql " + System.lineSeparator() +
                        "Create procedure: create or alter procedure [Dbo].[myProcedure]",
                "Name file: OfferingProviders.ProviderSports_Get_ByProviderId.sql" + System.lineSeparator() +
                        "Create procedure: create or alter procedure [OfferingProviders].[ProviderSports_Get_ByProviderId]");
        return rule;
    }

    public Rule createSchemaObjectGrantRule() {
        Rule rule = new Rule(
                "SG-022",
                "Schema/Object name is invalid in GRANT block",
                "<h2>Description</h2><p>The object and schema name referenced in the GRANT block must match those in the file name.",
                TAG,
                "2min");

        setRuleImplementation(rule,
                "Schema/Object name is invalid in GRANT block",
                "Name file: OfferingProviders.ProviderSports_Get_ByProviderId.sql " + System.lineSeparator() +
                        "GRANT EXECUTE ON [offering].[selection_applications] TO [LoadTestUser];",
                "Name file: OfferingProviders.ProviderSports_Get_ByProviderId.sql" + System.lineSeparator() +
                        "GRANT EXECUTE ON [OfferingProviders].[ProviderSports_Get_ByProviderId] TO [LoadTestUser];");
        return rule;
    }

    private static void setRuleImplementation(Rule rule, String ruleViolationMessage, String violatingCodeExample, String compliantCodeExample) {
        ViolatingRulesCodeExamples violatingRulesCodeExamples = new ViolatingRulesCodeExamples();
        violatingRulesCodeExamples.getRuleCodeExample().add(violatingCodeExample);
        CompliantRulesCodeExamples compliantRulesCodeExamples = new CompliantRulesCodeExamples();
        compliantRulesCodeExamples.getRuleCodeExample().add(compliantCodeExample);
        RuleImplementation impl = new RuleImplementation(ruleViolationMessage, violatingRulesCodeExamples, compliantRulesCodeExamples);
        rule.setRuleImplementation(impl);
    }
}