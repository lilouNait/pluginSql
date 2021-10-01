package net.betclic.cicd.measures;

import net.betclic.cicd.antlr.AntlrContext;
import net.betclic.cicd.language.TSQLDialect;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sonar.api.batch.fs.InputFile;

/**
 * IssuesProvider Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 6, 2021</pre>
 */
public class IssuesProviderTest {

    @InjectMocks
    IssuesProvider issuesProvider;

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
        SqlIssuesList sqlIssuesList = issuesProvider.recursiveAnalyzer(antlrContext);
        Assert.assertEquals(antlrContext.getTree().getText(), "usebetclic<EOF>");
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());
    }

    /**
     * Method: mapToSqlIssue(FoundMatch m)
     */

    @Test
    public void testMapToSqlIssue() {
        TSQLDialect tsqlDialect2 = new TSQLDialect();
        String texte = "use betclic select * from dbo.users";
        AntlrContext antlrContext = tsqlDialect2.parse(texte);
        tsqlDialect2.addRulesToContext(antlrContext);
        SqlIssuesList sqlIssuesList = issuesProvider.recursiveAnalyzer(antlrContext);
    }

    @Test
    public void testMapToSqlIssueThrowRule() {
        TSQLDialect tsqlDialect3 = new TSQLDialect();
        StringBuilder sb = new StringBuilder();
        sb.append("BEGIN CATCH\n");
        sb.append("IF XACT_STATE() <> 0 ROLLBACK TRANSACTION;\n");
        sb.append("THROW\n");
        sb.append("END CATCH\n");
        AntlrContext antlrContext = tsqlDialect3.parse(sb.toString());
        tsqlDialect3.addRulesToContext(antlrContext);
        SqlIssuesList sqlIssuesList = issuesProvider.recursiveAnalyzer(antlrContext);
    }

    @Test
    public void testViewRule() {
        TSQLDialect tsqlDialect6 = new TSQLDialect();
        StringBuilder sb = new StringBuilder();
        sb.append("Create view rgtgr].[vyhtyh]\n" +
                        "Create view trhe");
        AntlrContext antlrContext = tsqlDialect6.parse(sb.toString());
        tsqlDialect6.addRulesToContext(antlrContext);
        SqlIssuesList sqlIssuesList = issuesProvider.recursiveAnalyzer(antlrContext);
        Assert.assertEquals("SG-018", sqlIssuesList.getAllIssues().iterator().next().getKey());
        Assert.assertEquals(2, sqlIssuesList.getAllIssues().size());
    }

    @Test
    public void testPrintRule() {
        TSQLDialect tsqlDialect6 = new TSQLDialect();
        StringBuilder sb = new StringBuilder();
        sb.append("select * from db;"+
                "Print 'My function'\n" +
                "Create view [dbo].[vMyview]");
        AntlrContext antlrContext = tsqlDialect6.parse(sb.toString());
        tsqlDialect6.addRulesToContext(antlrContext);
        SqlIssuesList sqlIssuesList = issuesProvider.recursiveAnalyzer(antlrContext);
        Assert.assertEquals("SG-007", sqlIssuesList.getAllIssues().iterator().next().getKey());
        Assert.assertEquals(1, sqlIssuesList.getAllIssues().size());
    }

}