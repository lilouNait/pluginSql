package net.betclic.cicd.language;

import net.betclic.cicd.antlr.AntlrContext;
import net.betclic.cicd.issuesearcher.IssuesSearcher;
import net.betclic.cicd.measures.SqlIssuesList;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.sonar.api.batch.fs.InputFile;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TSQLDialectTest {

    @Mock
    CharStream charStream;

    @Mock
    InputFile inputFile;

    @Mock
    CommonTokenStream commonTokenStream;

    @Mock
    TSQLDialect tsqlDialect;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getLexer() {
        tsqlDialect.getLexer(charStream);
        verify(tsqlDialect, times(1)).getLexer(charStream);
    }

    @Test
    public void getParser() {
        TSQLDialect tSQLd = new TSQLDialect();
        tSQLd.getTypesMap();

        tsqlDialect.getParseTree(commonTokenStream);
        verify(tsqlDialect, times(1)).getParseTree(commonTokenStream);
    }

    @Test
    public void testGetTypesMap() {
        tsqlDialect.getTypesMap();
        verify(tsqlDialect, times(1)).getTypesMap();
    }

    @Test
    public void testGetTypeMapContent() {
        TSQLDialect tsqlDialect1 = new TSQLDialect();
        DialectLanguageTypesMap dialectLanguageTypesMap = tsqlDialect1.getTypesMap();
        Assert.assertEquals(dialectLanguageTypesMap.getCommentTokens(), tsqlDialect1.getTypesMap().getCommentTokens());
        Assert.assertEquals(dialectLanguageTypesMap.getStringTokens(), tsqlDialect1.getTypesMap().getStringTokens());
        Assert.assertEquals(dialectLanguageTypesMap.getCognitiveComplexity(), tsqlDialect1.getTypesMap().getCognitiveComplexity());
        Assert.assertEquals(dialectLanguageTypesMap.getCyclomatiComplexity(), tsqlDialect1.getTypesMap().getCyclomatiComplexity());
    }

    @Test
    public void testParse() {
        TSQLDialect tsqlDialectParser = new TSQLDialect();
        TSQLDialect tsqlDialectToCompare = new TSQLDialect();
        String textToParse = "test:select * from db";
        AntlrContext antlrContext = tsqlDialectParser.parse(textToParse);
        Lexer lexer = tsqlDialectToCompare.getLexer(new ChangingCharStream(CharStreams.fromString(textToParse), true));
        ParseTree parseTree = tsqlDialectToCompare.getParseTree(new CommonTokenStream(lexer));

        Assert.assertEquals(antlrContext.getTree().getText(), "test:select*fromdb<EOF>");
        Assert.assertEquals(antlrContext.getStream().getText(), textToParse);
        Assert.assertEquals(antlrContext.getLexer().getText(), lexer.getText());
        Assert.assertEquals(antlrContext.getTree().getText(), parseTree.getText());
    }

    @Test
    public void testParseWithSkipLines() {
        TSQLDialect tsqlDialectParser1 = new TSQLDialect();
        TSQLDialect tsqlDialectToCompare1 = new TSQLDialect();
        String textToParse = "/*Script is failed due to select @@IDENTITY statement*/\n" +
                "select @@IDENTITY\n" +
                "/*SONAR_SKIP_LINES*/\n" +
                "use Betclic \n" +
                "/*SONAR_END_SKIP_LINES*/\n" +
                "select @@IDENTITY";
        AntlrContext antlrContext = tsqlDialectParser1.parse(textToParse);
        Lexer lexer = tsqlDialectToCompare1.getLexer(new ChangingCharStream(CharStreams.fromString(textToParse), true));
        ParseTree parseTree = tsqlDialectToCompare1.getParseTree(new CommonTokenStream(lexer));

    }

    @Test
    public void testParseWithSkipLinesOk() {
        TSQLDialect tsqlDialectParser1 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder textToParse = new StringBuilder();
        textToParse.append("/*");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("Title");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("Description ");
        textToParse.append(System.getProperty("line.separator"));

        textToParse.append("Parameters :");
        textToParse.append(System.getProperty("line.separator"));

        textToParse.append("History :");
        textToParse.append(System.getProperty("line.separator"));

        textToParse.append("*/");
        textToParse.append(System.getProperty("line.separator"));

        textToParse.append("/*SONAR_SKIP_LINES*/");
        textToParse.append(System.getProperty("line.separator"));

        textToParse.append("use Betclic ");
        textToParse.append(System.getProperty("line.separator"));

        textToParse.append("/*SONAR_END_SKIP_LINES*/");
        textToParse.append(System.getProperty("line.separator"));

        String fileName = "dbo.myProcedureTest.sql";
        AntlrContext antlrContext = tsqlDialectParser1.parse(textToParse.toString());
        tsqlDialectParser1.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(0, sqlIssuesList.getAllIssues().size());

    }

    @Test
    public void testParseWithSkipLinesKo() {
        TSQLDialect tsqlDialectParser1 = new TSQLDialect();
        IssuesSearcher issuesSearcher = new IssuesSearcher();

        StringBuilder textToParse = new StringBuilder();
        textToParse.append("/*");
        textToParse.append(System.getProperty("line.separator"));

        textToParse.append("Title");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("Description ");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("Parameters :");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("History :");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("*/");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("create or alter procedure db.cc ");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("/*SONAR_SKIP_LINES*/");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("create or alter procedure db.cc  ");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("/*SONAR_END_SKIP_LINES*/");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("create or alter proc xx.cc ");
        textToParse.append("SET NOCOUNT ON;");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("SET XACT_ABORT ON;");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;");
        textToParse.append(System.getProperty("line.separator"));
        String fileName = "dbo.myProcedureTest.sql";
        AntlrContext antlrContext = tsqlDialectParser1.parse(textToParse.toString());
        tsqlDialectParser1.addRulesToContext(antlrContext);
        Mockito.when(inputFile.filename()).thenReturn(fileName);
        SqlIssuesList sqlIssuesList = issuesSearcher.iterativeAnalyzer(antlrContext, inputFile);
        Assert.assertEquals(2, sqlIssuesList.getAllIssues().size());
        Assert.assertEquals("SG-015", sqlIssuesList.getAllIssues().iterator().next().getKey());
    }

    @Test
    public void testParseTextWithoutCommentOk() {
        TSQLDialect tsqlDialectParser1 = new TSQLDialect();

        StringBuilder textToParse = new StringBuilder();
        textToParse.append("select * from db /* khvkk");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("Title");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("Description ");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("Parameters :");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("History :");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("*/");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("--create or alter procedure db.cc ");
        textToParse.append(System.getProperty("line.separator"));

        textToParse.append("SET NOCOUNT ON;");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("SET XACT_ABORT ON;");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;");


        AntlrContext antlrContext = tsqlDialectParser1.parse(textToParse.toString());
        Assert.assertNotNull(antlrContext.getTextWithoutComments());
    }

    /**
     * Test with comment that starts is the fisrt lign
     */
    @Test
    public void testParseTextWithoutCommentSecondCaseOk() {
        TSQLDialect tsqlDialectParser1 = new TSQLDialect();

        StringBuilder textToParse = new StringBuilder();
        textToParse.append("/* khvkk");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("Title");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("Description ");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("Parameters :");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("History :");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("*/");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("--create or alter procedure db.cc ");

        textToParse.append("SET NOCOUNT ON;");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("SET XACT_ABORT ON;");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;");

        AntlrContext antlrContext = tsqlDialectParser1.parse(textToParse.toString());
        Assert.assertNotNull(antlrContext.getTextWithoutComments());
    }

    /**
     * Test with a windows line separator \n
     */
    @Test
    public void testParseTextWithoutCommentThirdCase() {
        TSQLDialect tsqlDialectParser1 = new TSQLDialect();

        StringBuilder textToParse = new StringBuilder();
        textToParse.append("/* khvkk");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("Title");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("Description ");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("Parameters :");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("History :");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("*/");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("--create or alter procedure db.cc ");

        textToParse.append("SET NOCOUNT ON;");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("SET XACT_ABORT ON;");
        textToParse.append(System.getProperty("line.separator"));
        textToParse.append("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;");

        AntlrContext antlrContext = tsqlDialectParser1.parse(textToParse.toString());
        Assert.assertNotNull(antlrContext.getTextWithoutComments());
    }


}
