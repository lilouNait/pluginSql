package net.betclic.cicd.issuesSearcher;

import net.betclic.cicd.antlr.AntlrContext;
import net.betclic.cicd.issuesearcher.IssuesSearcher;
import net.betclic.cicd.language.TSQLDialect;
import net.betclic.cicd.measures.SqlIssuesList;
import net.betclic.cicd.settings.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.sonar.api.batch.fs.InputFile;

import java.net.URI;

/**
 * IssuesProvider Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 6, 2021</pre>
 */
public class IssuesSearcherTest {

    @Mock
    InputFile inputFile;

    @InjectMocks
    IssuesSearcher issuesSearcher;

    String fileNameTest = "test";

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method: analyze(AntlrContext ctx)
     */

    @Test
    public void testAnalyzeOk() {

        TSQLDialect tsqlDialect1 = new TSQLDialect();
        String texte = "use betclic";
        AntlrContext antlrContext = tsqlDialect1.parse(texte);
        tsqlDialect1.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(antlrContext.getTree().getText(), "usebetclic<EOF>");
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());
    }

    @Test
    public void testTriggerRuleTestOk() {
        TSQLDialect tsqlDialect7 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();
        StringBuilder sb = new StringBuilder();
        sb.append("/*\n");
        sb.append("Title : Offer.SG-012 - OK sportGateway_SportsIqLiveMarketMappings_Insert\n");
        sb.append("Description :\n");
        sb.append("Insert sports IQ live market mappings\n");
        sb.append("Parameters :\n");
        sb.append("- @MarketMappings - List of market mappings\n");
        sb.append("History :\n");
        sb.append("2020-12-28 - FQN - Create stored proc\n");
        sb.append("/*\n");
        sb.append("Description\n");
        sb.append("*/\n");
        sb.append("create trigger [xx].[u_xx]\r\n");
        sb.append("create trigger [xx].[d_xx]\r\n");
        sb.append("create trigger [xx].[i_xx]\r\n");
        AntlrContext antlrContext = tsqlDialect7.parse(sb.toString());
        tsqlDialect7.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(0, sqlIssuesList.getAllIssues().size());
    }

    @Test
    public void testTriggerRuleTestKo() {
        TSQLDialect tsqlDialect7 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();
        StringBuilder sb = new StringBuilder();
        sb.append("/*\n");
        sb.append("Title : Offer.SG-012 - OK sportGateway_SportsIqLiveMarketMappings_Insert\n");
        sb.append("Description :\n");
        sb.append("Insert sports IQ live market mappings\n");
        sb.append("Parameters :\n");
        sb.append("- @MarketMappings - List of market mappings\n");
        sb.append("History :\n");
        sb.append("2020-12-28 - FQN - Create stored proc\n");
        sb.append("/*\n");
        sb.append("Description\n");
        sb.append("*/\n");
        sb.append("create trigger [xx].[ax]\n");
        sb.append("create trigger [xx].[afghx]\n");
        sb.append("create trigger [xx].[afgx]\n");
        sb.append("create trigger xx].[ax]\n");
        sb.append("create trigger [xx.[afghx]\n");
        sb.append("create trigger [afgx]\n");
        AntlrContext antlrContext = tsqlDialect7.parse(sb.toString());
        tsqlDialect7.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals("SG-017", sqlIssuesList.getAllIssues().iterator().next().getKey());
        Assert.assertEquals(6, sqlIssuesList.getAllIssues().size());
    }

    @Test
    public void testHeaderRuleOk() {
        TSQLDialect tsqlDialect7 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();
        StringBuilder sb = new StringBuilder();
        sb.append("/*");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("Title : Offer.SG-012 - OK sportGateway_SportsIqLiveMarketMappings_Insert");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("Description :");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("Insert sports IQ live market mappings");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("Parameters :");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("- @MarketMappings - List of market mappings");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("History :");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("2020-12-28 - FQN - Create stored proc");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("*/");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("SET");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("ANSI_NULLS ON");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("GO");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("SET QUOTED_IDENTIFIER ON");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("GO");
        AntlrContext antlrContext = tsqlDialect7.parse(sb.toString());
        tsqlDialect7.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(0, sqlIssuesList.getAllIssues().size());
    }

    /**
     * Test the rule header missing using the skip lines, when we skip the description lines, sonar mustn't see them, so he raises an issue
     */
    @Test
    public void testHeaderRuleKo() {
        TSQLDialect tsqlDialect7 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();
        StringBuilder sb = new StringBuilder();
        sb.append("/*SONAR_SKIP_LINES*/");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("Title : Offer.SG-012 - OK sportGateway_SportsIqLiveMarketMappings_Insert");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("Description :");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("Insert sports IQ live market mappings");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("Parameters :");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("- @MarketMappings - List of market mappings");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("History :");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("2020-12-28 - FQN - Create stored proc");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("/*SONAR_END_SKIP_LINES*/");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("SET");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("ANSI_NULLS ON");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("GO");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("SET QUOTED_IDENTIFIER ON");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("GO");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("/*SONAR_SKIP_LINES*/");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("Title : Offer.SG-012 - OK sportGateway_SportsIqLiveMarketMappings_Insert");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("Description :");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("Insert sports IQ live market mappings");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("Parameters :");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("- @MarketMappings - List of market mappings");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("History :");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("2020-12-28 - FQN - Create stored proc");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("/*SONAR_END_SKIP_LINES*/");

        AntlrContext antlrContext = tsqlDialect7.parse(sb.toString());
        tsqlDialect7.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals("SG-001", sqlIssuesList.getAllIssues().iterator().next().getKey());
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().iterator().next().getLine());
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());
    }

    /**
     * Testing when we miss to write the key word "TITLE" correctly, sonar must raise an issue because in this test, it is written "TITE"
     */
    @Test
    public void testHeaderRuleKo2() {
        TSQLDialect tsqlDialect7 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();
        StringBuilder sb = new StringBuilder();
        sb.append("/*");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("Tite : Offer.SG-012 - OK sportGateway_SportsIqLiveMarketMappings_Insert");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("Description :");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("Insert sports IQ live market mappings");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("Parameters :");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("- @MarketMappings - List of market mappings");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("History :");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("2020-12-28 - FQN - Create stored proc");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("*/");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("SET");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("ANSI_NULLS ON");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("GO");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("SET QUOTED_IDENTIFIER ON");
        sb.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_UNIX);
        sb.append("GO");
        AntlrContext antlrContext = tsqlDialect7.parse(sb.toString());
        tsqlDialect7.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals("SG-001", sqlIssuesList.getAllIssues().iterator().next().getKey());
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().iterator().next().getLine());
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());
    }

    @Test
    public void testCreateFunctionRule() {
        TSQLDialect tsqlDialect7 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();
        StringBuilder sb = new StringBuilder();
        sb.append("create function affering][create]\n");
        sb.append("create function [afferisfgbng].[creafgdte]\n");
        sb.append("/*\n");
        sb.append("Title ");
        sb.append("Description \n");
        sb.append("Parameters :\n");
        sb.append("History :\n");
        sb.append("*/\n");
        AntlrContext antlrContext = tsqlDialect7.parse(sb.toString());
        tsqlDialect7.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals("SG-016", sqlIssuesList.getAllIssues().iterator().next().getKey());
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());
    }

    /**
     * I commented out this test because the rule SG-005 causes a bug, working on it
     */
    @Test
    public void testGrantBlockRuleOk() {
        TSQLDialect tsqlDialect8 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();
        StringBuilder sb = new StringBuilder();
        sb.append("GO");
        sb.append("GRANT EXECUTE ON [CCC].[common_users_get] TO [db_bbbbbbice];\t\n");
        sb.append("GRANT EXECUTE ON TYPE::[CCC].[common_users_get] TO [db_bbbbbbice];\t\n");

        sb.append("GO");
        sb.append("/*\t\n");
        sb.append("Title ");
        sb.append("Description \t\n");
        sb.append("Parameters :\n");
        sb.append("History :\n");
        sb.append("*/\n");
        sb.append("select * from db");
        AntlrContext antlrContext = tsqlDialect8.parse(sb.toString());
        tsqlDialect8.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(0, sqlIssuesList.getAllIssues().size());
    }

    @Test
    public void testGrantBlockRuleKo() {
        TSQLDialect tsqlDialect9 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();
        StringBuilder sb = new StringBuilder();
        sb.append("/*\n");
        sb.append("Title ");
        sb.append("Description \n");
        sb.append("Parameters :\n");
        sb.append("History :\n");
        sb.append("*/\n");
        sb.append("Go\n");
        sb.append("GRANT EXEC ON TYPE::[xx].[common_users_get] TO [db_BackOffice]\n");
        sb.append("GRANT EXEC ON TYPE::[xx.[common_users_get] TO [db_BackOffice];\n");
        sb.append("GRANT EXEC ON TYPE::xx].[common_users_get] TO [db_BackOffice];\n");
        sb.append("GRANT EXEC ON TYPE::[xx].common_users_get] TO [db_BackOffice];\n");
        sb.append("GRANT EXEC ON TYPE::[xx].[common_users_get TO [db_BackOffice];\n");
        sb.append("GRANT EXEC ON TYPE::[xx].[common_users_get] TO db_BackOffice];\n");
        sb.append("GRANT EXEC ON TYPE::[xx].[5common_users_get] TO [db_BackOffice;\n");

        sb.append("GRANT EXEC ON TYPE::[xx].common_users_get TO [db_BackOffice];\n");
        sb.append("GRANT EXECUTE ON TYPE::xx.common_users_get TO db_BackOffice;\n");
        sb.append("GRANT EXEC ON TYPE::5common_users_get TO db_BackOffice;\n");
        sb.append("GRANT EXEC ON TYPE::[5common_users_get] TO db_BackOffice\n");
        sb.append("GRANT EXEC ON TYPE::xx.[5common_users_get] TO db_BackOffice\n");
        sb.append("GRANT SELECT ON TYPE::xx.[5common_users_get] TO [db_BackOffice];\n");
        sb.append("GO\n");
        sb.append("select * from db;");

        sb.append("Go\n");
        sb.append("GRANT EXEC ON common_users_get TO db_BackOffice;\n");
        sb.append("GRANT EXEC ON [common_users_get] TO [db_BackOffice];\n");
        sb.append("GRANT EXEC ON [xx].[common_users_get] TO db_BackOffice;\n");
        sb.append("GRANT SELECT ON [xx].[common_users_get] TO db_BackOffice;\n");

        sb.append("GO\n");

        AntlrContext antlrContext = tsqlDialect9.parse(sb.toString());
        tsqlDialect9.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals("SG-009", sqlIssuesList.getAllIssues().iterator().next().getKey());
        Assert.assertEquals(15, sqlIssuesList.getAllIssues().size());
    }


    /**
     * I commented out this test because i'm working on a bug on the rule SG-005
     */
    @Test
    public void testGrantBlockRuleWithExecuteOnStatementKo() {
        TSQLDialect tsqlDialect9 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();
        StringBuilder sb = new StringBuilder();
        sb.append("/*\n");
        sb.append("Title ");
        sb.append("Description \n");
        sb.append("Parameters :\n");
        sb.append("History :\n");
        sb.append("*/\n");
        sb.append("Go\n");
        sb.append("GRANT EXECUTE ON TYPE::[xx].[common_users_get] TO [db_BackOffice];\n");
        sb.append("GRANT EXECUTE ON TYPE::[xx.[common_users_get] TO [db_BackOffice];\n");
        sb.append("GRANT EXECUTE ON TYPE::xx].[common_users_get] TO [db_BackOffice];\n");
        sb.append("GRANT EXECUTE ON TYPE::[xx].common_users_get] TO [db_BackOffice];\n");
        sb.append("GRANT EXECUTE ON TYPE::[xx].[common_users_get TO [db_BackOffice];\n");
        sb.append("GRANT EXECUTE ON TYPE::[xx].[common_users_get] TO db_BackOffice];\n");
        sb.append("GRANT EXECUTE ON TYPE::[xx].[5common_users_get] TO [db_BackOffice;\n");

        sb.append("GRANT EXECUTE ON TYPE::[xx].common_users_get TO [db_BackOffice];\n");
        sb.append("GRANT EXECUTE ON TYPE::xx.common_users_get TO db_BackOffice;\n");
        sb.append("GRANT EXECUTE ON TYPE::common_users_get TO db_BackOffice;\n");
        sb.append("GRANT EXECUTE ON TYPE::[common_users_get] TO [db_BackOffice];\n");
        sb.append("GRANT EXECUTE ON TYPE::[common_users_get] TO db_BackOffice\n");
        sb.append("GRANT EXECUTE ON TYPE::xx.[common_users_get] TO db_BackOffice\n");
        sb.append("GRANT SELECT ON TYPE::xx.[common_users_get] TO db_BackOffice\n");
        sb.append("GO\n");
        sb.append("select * from db;");

        sb.append("Go\n");
        sb.append("GRANT EXECUTE ON common_users_get TO db_BackOffice;\n");
        sb.append("GRANT EXECUTE ON [common_users_get] TO [db_BackOffice];\n");
        sb.append("GRANT EXECUTE ON [xx].[common_users_get] TO db_BackOffice;\n");
        sb.append("GRANT SELECT ON [xx].[common_users_get] TO db_BackOffice;\n");

        sb.append("GO\n");
        AntlrContext antlrContext = tsqlDialect9.parse(sb.toString());
        tsqlDialect9.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals("SG-009", sqlIssuesList.getAllIssues().iterator().next().getKey());
        Assert.assertEquals(15, sqlIssuesList.getAllIssues().size());
    }

    @Test
    public void testGrantGORuleKo() {
        TSQLDialect tsqlDialect9 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();
        StringBuilder sb = new StringBuilder();
        sb.append("/*\n");
        sb.append("Title ");
        sb.append("Description \n");
        sb.append("Parameters :\n");
        sb.append("History :\n");
        sb.append("*/\n");
        sb.append("GRANT EXECUTE ON [xx].[5common_users_get] TO [db_BackOffice];\n");
        sb.append("GRANT EXECUTE ON [xx].[common_users_get] TO [db_BackOffice];\n");
        sb.append("GRANT EXECUTE ON [xx].[common_users_get] TO [db_BackOffice];\n");
        AntlrContext antlrContext = tsqlDialect9.parse(sb.toString());
        tsqlDialect9.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals("SG-005", sqlIssuesList.getAllIssues().iterator().next().getKey());
        Assert.assertEquals(3, sqlIssuesList.getAllIssues().size());
    }

    @Test
    public void testGrantGORuleOk() {
        TSQLDialect tsqlDialect9 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();
        StringBuilder sb = new StringBuilder();
        sb.append("/*\n");
        sb.append("Title ");
        sb.append("Description \n");
        sb.append("Parameters :\n");
        sb.append("History :\n");
        sb.append("*/\n");
        sb.append("GO\n");
        sb.append("GRANT EXEC ON TYPE::[xx].[5common_users_get] TO [db_BackOffice];\n");
        sb.append("GO\n\n");
        sb.append("select * from lilia; \n\n");

        sb.append("Go\n\n\n");
        sb.append("GRANT EXECUTE ON [xx].[5common_users_get] TO [db_BackOffice];\n");
        sb.append("GRANT EXECUTE ON [xx].[5common_users_get] TO [db_BackOffice];\n");
        sb.append("GO");
        AntlrContext antlrContext = tsqlDialect9.parse(sb.toString());
        tsqlDialect9.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(0, sqlIssuesList.getAllIssues().size());
    }

    @Test
    public void testCreateProcedureRuleWithoutAlterKo() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb = new StringBuilder();
        sb.append("/*\n");
        sb.append("Title ");
        sb.append("Description \n");
        sb.append("Parameters :\n");
        sb.append("History :\n");
        sb.append("*/\n");
        sb.append("create procedure [dbo].[myprocedure] As Begin try\n");
        sb.append("select * from dbo.betclic\n");
        sb.append("end try\n");
        AntlrContext antlrContext = tsqlDialect5.parse(sb.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());
        Assert.assertEquals("SG-015", sqlIssuesList.getAllIssues().iterator().next().getKey());
    }

    @Test
    public void testCreateProcRulerWithAlterProcKo() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb = new StringBuilder();
        sb.append("/*\n");
        sb.append("Title ");
        sb.append("Description \n");
        sb.append("Parameters :\n");
        sb.append("History :\n");
        sb.append("*/\n");
        sb.append("create or alter proc [dbo].[myprocedure] As Begin try\n");
        sb.append("SET NOCOUNT ON;\n\n");
        sb.append("SET XACT_ABORT ON;\n\n");
        sb.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");

        sb.append("\n");
        AntlrContext antlrContext = tsqlDialect5.parse(sb.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());
        Assert.assertEquals("SG-015", sqlIssuesList.getAllIssues().iterator().next().getKey());
    }

    @Test
    public void testCreateProcRuleWithFirstHugSchemaMissingKo() {

        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb = new StringBuilder();
        sb.append("/*\n");
        sb.append("Title ");
        sb.append("Description \n");
        sb.append("Parameters :\n");
        sb.append("History :\n");
        sb.append("*/\n");
        sb.append("create or alter procedure dbo1].[myprocedure] As Begin try\n");
        sb.append("SET NOCOUNT ON;\n\n");
        sb.append("SET XACT_ABORT ON;\n\n");
        sb.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        sb.append("\n");
        AntlrContext antlrContext = tsqlDialect5.parse(sb.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());

        Assert.assertEquals("SG-015", sqlIssuesList.getAllIssues().iterator().next().getKey());
    }


    @Test
    public void testCreateProcRuleWithSecondHugSchemaMissingKo() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb1 = new StringBuilder();
        sb1.append("/*\n");
        sb1.append("Title ");
        sb1.append("Description \n");
        sb1.append("Parameters :\n");
        sb1.append("History :\n");
        sb1.append("*/\n");
        sb1.append("create or alter procedure [dbo2.[myprocedure] As Begin try\n");
        sb1.append("SET NOCOUNT ON;\n");
        sb1.append("SET XACT_ABORT ON;\n");
        sb1.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");

        sb1.append("\n");
        AntlrContext antlrContext = tsqlDialect5.parse(sb1.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());
        Assert.assertEquals("SG-015", sqlIssuesList.getAllIssues().iterator().next().getKey());
    }


    @Test
    public void testCreateProcRuleWithHugNameFirstMissingKo() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb2 = new StringBuilder();
        sb2.append("/*\n");
        sb2.append("Title ");
        sb2.append("Description \n");
        sb2.append("Parameters :\n");
        sb2.append("History :\n");
        sb2.append("*/\n");
        sb2.append("create or alter procedure [dbo3].myprocedure] As Begin try\n");
        sb2.append("SET NOCOUNT ON;\n");
        sb2.append("SET XACT_ABORT ON;\n");
        sb2.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        sb2.append("\n");
        AntlrContext antlrContext = tsqlDialect5.parse(sb2.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());

        Assert.assertEquals("SG-015", sqlIssuesList.getAllIssues().iterator().next().getKey());
    }


    @Test
    public void testCreateProcRuleWithHugSchemaMissingKo() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb3 = new StringBuilder();
        sb3.append("/*\n");
        sb3.append("Title ");
        sb3.append("Description \n");
        sb3.append("Parameters :\n");
        sb3.append("History :\n");
        sb3.append("*/\n");
        sb3.append("create or alter procedure [dbo4].[myprocedure As Begin try\n");
        sb3.append("SET NOCOUNT ON;\n\n");
        sb3.append("SET XACT_ABORT ON;\n\n");
        sb3.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        sb3.append("\n");
        AntlrContext antlrContext = tsqlDialect5.parse(sb3.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());
        Assert.assertEquals("SG-015", sqlIssuesList.getAllIssues().iterator().next().getKey());
    }


    @Test
    public void testCreateProcRuleWithHugNameMissingKo() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb4 = new StringBuilder();
        sb4.append("/*\n");
        sb4.append("Title ");
        sb4.append("Description \n");
        sb4.append("Parameters :\n");
        sb4.append("History :\n");
        sb4.append("*/\n");
        sb4.append("create or alter procedure [dbo5]. myprocedure As Begin try\n");
        sb4.append("SET NOCOUNT ON;\n\n");
        sb4.append("SET XACT_ABORT ON;\n\n");
        sb4.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        sb4.append("\n");
        AntlrContext antlrContext = tsqlDialect5.parse(sb4.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals("SG-015", sqlIssuesList.getAllIssues().iterator().next().getKey());
    }


    @Test

    public void testCreateProcRuleOk() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append("create or alter procedure [dbo6].[myprocedure] As Begin try\n");
        sb5.append("SET NOCOUNT ON;\n\n");
        sb5.append("SET XACT_ABORT ON;\n\n");
        sb5.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        sb5.append("GRANT EXECUTE ON [dbo6].[myprocedure] As Begin try\n");
        sb5.append("\n");
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileNameTest);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(0, sqlIssuesList.getAllIssues().size());
    }

    @Test
    public void testCreateSchemaObjectRuleOk() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title : Offer.Test_DeleteOffer\n");
        sb5.append("Description:\n");
        sb5.append("Delete all item related to match generated by a specific provider for a given external Id\n");
        sb5.append("this is use after integration test to clean data that are created\n");
        sb5.append("Parameters :\n");
        sb5.append("- @ExternalId BIGINT\n");
        sb5.append("- @ProviderId INT\n");
        sb5.append("History:\n");
        sb5.append("2021-01-11 - LBO - Creation\n");
        sb5.append("*/\n");
        sb5.append("CREATE OR ALTER PROCEDURE [Offer].[Test_DeleteOffer]\n");
        sb5.append("(\n");
        sb5.append("@ExternalId BIGINT,\n");
        sb5.append("@ProviderId INT\n");
        sb5.append(")\n");
        sb5.append("AS\n");
        sb5.append("BEGIN \n");
        sb5.append("TRY\n");
        sb5.append("GRANT EXECUTE ON [Offer].[Test_DeleteOffer] TO [TEST];\n");

        sb5.append("SET NOCOUNT ON;\n");
        sb5.append("SET XACT_ABORT ON; \n");
        sb5.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        sb5.append("DECLARE @MatchId BIGINT\n");
        sb5.append("DECLARE @ProviderData TABLE (ExternalId INT, MatchId INT, ProviderId TINYINT)\n");
        sb5.append("DECLARE @ProviderLiveData TABLE (ExternalId INT, LiveMatchId INT, ProviderId TINYINT)\n");
        sb5.append("INSERT INTO @ProviderData(ExternalId, MatchId, ProviderId)\n");
        sb5.append("INSERT INTO @ProviderData(ExternalId, MatchId, ProviderId)\n");
        sb5.append("INNER JOIN live_match lm ON lm.id = tlm.LiveMatchId\n");
        sb5.append("tWHERE lm.match_id = @MatchId\n");
        sb5.append("BEGIN TRAN\n");

        String fileName = "Offer.Test_DeleteOffer.sql";

        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(2, sqlIssuesList.getAllIssues().size());

    }


    @Test
    public void testCreateSchemaObjectCreateProcedureRuleKo() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append("create or alter procedure [Legal].[common_match_getMappedToBetradarButNotMappedToSrij] As Begin try\n");
        sb5.append("SET NOCOUNT ON;\n\n");
        sb5.append("SET XACT_ABORT ON;\n\n");
        sb5.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        sb5.append("GRANT EXECUTE ON [Legal].[common_match_getMappedToBetradarButNotMappedToSrij] TO [TEST];\n");

        sb5.append("create or alter procedure [dbo].[myProcedureTest] As Begin try\n");
        sb5.append("GRANT EXECUTE ON [dbo].[myProcedureTest] TO [TEST];\n");

        sb5.append("\n");
        String fileName = "dbo.myProcedureTest.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());
        Assert.assertEquals("SG-021", sqlIssuesList.getAllIssues().iterator().next().getKey());

    }

    /**
     * Test the rule SG-022 on a classic GRANT
     */
    @Test
    public void testCreateSchemaObjectOnClassicGrantRuleKo() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append("Go\n");
        sb5.append("GRANT EXECUTE ON [xx].[5common_users_get] TO [db_BackOffice];\n");
        sb5.append("GRANT EXECUTE ON [xx].[common_users_get] TO [db_BackOffice];\n");
        sb5.append("GRANT EXECUTE ON [dbo].[myProcedureTest] TO [db_BackOffice];\n");
        sb5.append("GO");
        sb5.append("\n");
        String fileName = "dbo.myProcedureTest.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(2, sqlIssuesList.getAllIssues().size());
        Assert.assertEquals("SG-022", sqlIssuesList.getAllIssues().iterator().next().getKey());

    }

    /**
     * Test the rule SG-022 on a Grant that contains a TYPE keyword
     */
    @Test
    public void testCreateSchemaObjectOnGrantTypeRuleKo() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append("Go\n");
        sb5.append("GRANT EXEC ON TYPE::[dbo].[myProcedureTest] TO [LoadTestUser];\n");
        sb5.append("GRANT EXEC ON TYPE::[offering].[BigIntList] TO [LoadTestUser];\n");
        sb5.append("GRANT EXECUTE ON [dbo].[myProcedureTest] TO [db_BackOffice];\n");
        sb5.append("GO");
        sb5.append("\n");
        String fileName = "dbo.myProcedureTest.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());
        Assert.assertEquals("SG-022", sqlIssuesList.getAllIssues().iterator().next().getKey());

    }

    @Test
    public void testGrantSeparatorRuleOk() {
        TSQLDialect tsqlDialect9 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();
        StringBuilder sb = new StringBuilder();
        sb.append("/*\n");
        sb.append("Title\n");
        sb.append("Description \n");
        sb.append("Parameters :\n");
        sb.append("History :\n");
        sb.append("*/\n");
        sb.append("Go\n");
        sb.append("GRANT EXECUTE ON [xx].[5common_users_get] TO [db_BackOffice];\n");
        sb.append("GRANT EXECUTE ON [xx].[common_users_get] TO [db_BackOffice];\n");
        sb.append("GRANT EXECUTE ON [xx].[common_users_get] TO [db_BackOffice];\n");
        sb.append("GO");
        AntlrContext antlrContext = tsqlDialect9.parse(sb.toString());
        tsqlDialect9.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn("test");
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(0, sqlIssuesList.getAllIssues().size());
    }


    @Test
    public void testGrantSeparatorRuleKo() {
        TSQLDialect tsqlDialect9 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();
        StringBuilder sb = new StringBuilder();
        sb.append("/*\n");
        sb.append("Title ");
        sb.append("Description \n");
        sb.append("Parameters :\n");
        sb.append("History :\n");
        sb.append("*/\n");
        sb.append("Go\n");
        sb.append("GRANT EXECUTE ON [xx].[5common_users_get] TO [db_BackOffice];\n");
        sb.append("GO\n");
        sb.append("GRANT EXECUTE ON [xx].[common_users_get] TO [db_BackOffice];\n");
        sb.append("GO\n");
        AntlrContext antlrContext = tsqlDialect9.parse(sb.toString());
        tsqlDialect9.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn("test");
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals("SG-006", sqlIssuesList.getAllIssues().iterator().next().getKey());
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());
    }

    @Test
    public void testCreateAsMissingRuleOk() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append("create or alter procedure [dbo].[myProcedureTest] (myProcedureTest) AS BEGIN TRY\n");
        sb5.append("SET NOCOUNT ON;\n\n");
        sb5.append("SET XACT_ABORT ON;\n\n");
        sb5.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        sb5.append("GRANT EXECUTE ON [dbo].[myProcedureTest] [TEST];\n");
        sb5.append("\n");
        String fileName = "dbo.myProcedureTest.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(0, sqlIssuesList.getAllIssues().size());


    }

    @Test
    public void testCreateAsMissingRuleKo() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/* Title ");
        sb5.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_SYSTEM);
        sb5.append("Description ");
        sb5.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_SYSTEM);
        sb5.append("Parameters :");
        sb5.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_SYSTEM);
        sb5.append("History :");
        sb5.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_SYSTEM);
        sb5.append("*/");
        sb5.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_SYSTEM);
        sb5.append("select * from dbo");
        sb5.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_SYSTEM);
        sb5.append("CREATE OR ALTER PROCEDURE [dbo].[myProcedureTest] BEGIN TRY ");
        sb5.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_SYSTEM);
        sb5.append("CREATE OR ALTER PROCEDURE [dbo].[myProcedureTest] BEGIN TRY ");
        sb5.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_SYSTEM);
        sb5.append("CREATE OR ALTER PROCEDURE [dbo].[myProcedureTest] (ngrdg ");
        sb5.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_SYSTEM);
        sb5.append("rdgdfgtg) BEGIN TRY ");
        sb5.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_SYSTEM);
        sb5.append("CREATE OR ALTER PROCEDURE [dbo].[myProcedureTest] AS BEGIN TRY ");
        sb5.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_SYSTEM);
        sb5.append("CREATE OR ALTER PROCEDURE [dbo].[myProcedureTest] (ngrdgdfgtg) BEGIN TRY ");
        sb5.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_SYSTEM);
        sb5.append("CREATE OR ALTER PROCEDURE [dbo].[myProcedureTest] BEGIN TRY ");
        sb5.append(Constants.PLUGIN_SQL_FILES_LINE_SEPARATOR_SYSTEM);
        sb5.append("GRANT EXECUTE ON [dbo].[myProcedureTest] TO [TEST];");
        sb5.append("SET NOCOUNT ON;\n\n");
        sb5.append("SET XACT_ABORT ON;\n\n");
        sb5.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        String fileName = "dbo.myProcedureTest.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(5, sqlIssuesList.getAllIssues().size());
        Assert.assertEquals("SG-012", sqlIssuesList.getAllIssues().iterator().next().getKey());
    }

    @Test
    public void testCreateAsMissingRuleWithWindowsLineSeparatorKo() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/* Title \n");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append("select * from dbo \n");
        sb5.append("create or alter procedure [dbo].[myProcedureTest] (skdjncksdnc) BEGIN TRY \n");
        sb5.append("CREATE OR ALTER PROCEDURE [dbo].[myProcedureTest] (ngrdgd \n");
        sb5.append("rdgdfgtg) BEGIN TRY \n");
        sb5.append("CREATE OR ALTER PROCEDURE [dbo].[myProcedureTest] (ngrdgdfgtg) BEGIN TRY \n");
        sb5.append("CREATE OR ALTER PROCEDURE [dbo].[myProcedureTest] BEGIN TRY");
        sb5.append("SET NOCOUNT ON;\n\n");
        sb5.append("SET XACT_ABORT ON;\n\n");
        sb5.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        sb5.append("GRANT EXECUTE ON [dbo].[myProcedureTest] TO [TEST];\n");

        String fileName = "dbo.myProcedureTest.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(4, sqlIssuesList.getAllIssues().size());
        Assert.assertEquals("SG-012", sqlIssuesList.getAllIssues().iterator().next().getKey());
    }

    @Test
    public void testCreateBeginTryRuleKo() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append("create or alter procedure [Legal].[common_match_getMappedToBetradarButNotMappedToSrij] As BEGIN\n");
        sb5.append("create or alter procedure [Legal].[common_match_getMappedToBetradarButNotMappedToSrij] As BEGIN TRY\n");
        sb5.append("create or alter procedure [Legal].[common_match_getMappedToBetradarButNotMappedToSrij] \n");
        sb5.append("SET NOCOUNT ON;\n\n");
        sb5.append("SET XACT_ABORT ON;\n\n");
        sb5.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        sb5.append("GRANT EXECUTE ON [Legal].[common_match_getMappedToBetradarButNotMappedToSrij] TO [TEST];\n");

        sb5.append("SET NOCOUNT ON;\n\n");
        sb5.append("SET XACT_ABORT ON;\n\n");
        sb5.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");

        sb5.append("AS BEGIN \n");
        sb5.append("\n");
        String fileName = "Legal.common_match_getMappedToBetradarButNotMappedToSrij.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(2, sqlIssuesList.getAllIssues().size());
        Assert.assertEquals("SG-013", sqlIssuesList.getAllIssues().iterator().next().getKey());
    }

    @Test
    public void testCreateBeginTryRuleOk() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append("create or alter procedure [Legal].[common_match_getMappedToBetradarButNotMappedToSrij] As BEGIN TRY\n");
        sb5.append("create or alter procedure [Legal].[common_match_getMappedToBetradarButNotMappedToSrij] As BEGIN TRY\n");
        sb5.append("create or alter procedure [Legal].[common_match_getMappedToBetradarButNotMappedToSrij] \n");
        sb5.append("SET NOCOUNT ON;\n\n");
        sb5.append("SET XACT_ABORT ON;\n\n");
        sb5.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        sb5.append("AS BEGIN TRY\n");
        sb5.append("GRANT EXECUTE ON [Legal].[common_match_getMappedToBetradarButNotMappedToSrij] As BEGIN TRY\n");
        sb5.append("\n");
        String fileName = "Legal.common_match_getMappedToBetradarButNotMappedToSrij.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(0, sqlIssuesList.getAllIssues().size());
    }

    /**
     *TEST Rule : SG-014 that is supposed to break an issue when a keyword TRY is found in a script file
     * A file is considered as a scrip when it's contained in a directory that contains the key work SCRIPTS in the directory name
     * Case description: Test when an sql file in contained in a directory that is not named Scripts and a keyword TRY is present in this file
     * Result : Sonar break three issues, because he found three keywords TRY
     */

    @Test
    public void testCreateUnexpectedTryRuleKo() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append(" [Legal].[common_match_getMappedToBetradarButNotMapped] As BEGIN RETRY\n");
        sb5.append(" [Legal].[common_match_getMappedToBetradarButNotMapped] As BEGIN TRY\n");
        sb5.append(" [Legal].[common_match_getMappedToBetradarButNotMapped] \n");
        sb5.append("AS BEGIN try\n");
        sb5.append("GRANT EXECUTE ON [Legal].[common_match_getMappedToBetradarButNotMapped] TO [TEST];\n");

        sb5.append("\n");
        String fileName = "Legal.common_match_getMappedToBetradarButNotMapped.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        Mockito.when(inputFile.uri()).thenReturn(URI.create("/C:/app/SG012-TEST/Scripts/Legal.common_match_getMappedToBetradarButNotMapped.sql"));
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(2, sqlIssuesList.getAllIssues().size());
        Assert.assertEquals("SG-014", sqlIssuesList.getAllIssues().iterator().next().getKey());

    }

    /**
     *TEST Rule : SG-014 that is supposed to break an issue when a keyword TRY is found in a script file
     * A file is considered as a scrip when it's contained in a directory that contain the key work SCRIPTS in the directory name
     * Case description: Test when an sql file in contained in a directory that is not named Scripts and a keyword TRY is present in this file
     * Result : Sonar don't see any issue
     */
    @Test
    public void testCreateUnexpectedTryRuleOk() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("TRY \n");
        sb5.append("*/\n");
        sb5.append("TRY \n");
        sb5.append("Select * from db\n");
        sb5.append("\n");
        String fileName = "Legal.common_match_getMappedToBetradarButNotMapped.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        Mockito.when(inputFile.uri()).thenReturn(URI.create("/C:/app/SG012-TEST/Legal.common_match_getMappedToBetradarButNotMapped.sql"));
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(0, sqlIssuesList.getAllIssues().size());
    }

    @Test
    public void testCreateGrantMissingRuleKo() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append("create or alter procedure [Legal].[common_match_get] As BEGIN TRY\n");
        sb5.append("SET NOCOUNT ON;\n\n");
        sb5.append("SET XACT_ABORT ON;\n\n");
        sb5.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        sb5.append("GRANT EXECUTE on [Legal].[test] As BEGIN TRY\n");
        sb5.append("\n");
        String fileName = "Legal.common_match_get.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());
        Assert.assertEquals("SG-011", sqlIssuesList.getAllIssues().iterator().next().getKey());
    }

    @Test
    public void testCreateGrantMissingWithExecRuleOk() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append("create or alter procedure [Legal].[common_match_get] As BEGIN TRY\n");
        sb5.append("SET NOCOUNT ON;\n\n");
        sb5.append("SET XACT_ABORT ON;\n\n");
        sb5.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        sb5.append("GRANT EXEC on [Legal].[common_match_get] As BEGIN TRY\n");
        sb5.append("\n");
        String fileName = "Legal.common_match_get.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(0, sqlIssuesList.getAllIssues().size());
    }

    @Test
    public void testCreateGrantMissingRuleOk() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append("create or alter procedure [Legal].[common_match_get] As BEGIN TRY\n");
        sb5.append("SET NOCOUNT ON;\n\n");
        sb5.append("SET XACT_ABORT ON;\n\n");
        sb5.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        sb5.append("GRANT EXECUTE or alter procedure [Legal].[common_match_get] As BEGIN TRY\n");
        sb5.append("\n");
        String fileName = "Legal.common_match_get.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(0, sqlIssuesList.getAllIssues().size());
    }

    @Test
    public void testCreateFromClauseUsingOldStyleJoinRuleKo() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append("SELECT  FROM [t].[MatchId], [t].[TosApplicationRefererId] WHERE [t].[TosApplicationRefererId] = [t].[TosApplicationRefererId]\n");
        sb5.append("SELECT * FROM [fff].[dfvdf],[dfdf].[dfdfv] WHERE [sdsd].[dfdf] = [sdsd].[dfdf]\n \n");
        sb5.append("\n");
        String fileName = "Legal.common_match_get.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
    }

    @Test
    public void testCreateFromClauseUsingOldStyleJoinRuleOk() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append("SELECT * \n" +
                "FROM Table1 \n" +
                "INNER JOIN Table2 ON Table1.a = Table2.b\n");
        sb5.append("\n");
        String fileName = "Legal.common_match_get.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(0, sqlIssuesList.getAllIssues().size());
    }

    @Test
    public void testCreateSetTransactionIsolationRuleOk() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append("create or alter procedure [Legal].[common_match_get] As BEGIN TRY\n");
        sb5.append("SET NOCOUNT ON;\n");
        sb5.append("SET XACT_ABORT ON;\n");
        sb5.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        sb5.append("GRANT EXECUTE or alter procedure [Legal].[common_match_get] As BEGIN TRY\n");

        sb5.append("\n");
        String fileName = "Legal.common_match_get.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(0, sqlIssuesList.getAllIssues().size());

    }

    @Test
    public void testCreateSetTransactionIsolationRuleKo() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append("create or alter procedure [Legal].[common_match_get] As BEGIN TRY\n");

        sb5.append("GRANT EXECUTE or alter procedure [Legal].[common_match_get] As BEGIN TRY\n");
        sb5.append("\n");
        String fileName = "Legal.common_match_get.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());
        Assert.assertEquals("SG-003", sqlIssuesList.getAllIssues().iterator().next().getKey());

    }

    @Test
    public void testDropTempTableRuleKo() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append("create or alter procedure [Legal].[common_match_get] As BEGIN TRY\n");
        sb5.append("SET NOCOUNT ON;\n");
        sb5.append("SET XACT_ABORT ON;\n");
        sb5.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        sb5.append("GRANT EXECUTE or alter procedure [Legal].[common_match_get] As BEGIN TRY\n");
        sb5.append("CREATE TABLE #tempUser\n");
        sb5.append("(\n");
        sb5.append("id int\n");
        sb5.append("DROP TABLE #tempUser\n");
        sb5.append(")\n");

        String fileName = "Legal.common_match_get.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());
        Assert.assertEquals("SG-030", sqlIssuesList.getAllIssues().iterator().next().getKey());

    }
    @Test
    public void testDropTempTableRuleOk() {
        TSQLDialect tsqlDialect5 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder sb5 = new StringBuilder();
        sb5.append("/*\n");
        sb5.append("Title ");
        sb5.append("Description \n");
        sb5.append("Parameters :\n");
        sb5.append("History :\n");
        sb5.append("*/\n");
        sb5.append("create or alter procedure [Legal].[common_match_get] As BEGIN TRY\n");
        sb5.append("SET NOCOUNT ON;\n");
        sb5.append("SET XACT_ABORT ON;\n");
        sb5.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;\n");
        sb5.append("GRANT EXECUTE or alter procedure [Legal].[common_match_get] As BEGIN TRY\n");
        sb5.append("\n");
        String fileName = "Legal.common_match_get.sql";
        AntlrContext antlrContext = tsqlDialect5.parse(sb5.toString());
        tsqlDialect5.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(0, sqlIssuesList.getAllIssues().size());

    }
}