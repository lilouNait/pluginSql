package net.betclic.cicd.measures;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * SqlIssue Tester.
 *
 * @author <Authors Nait Amara>
 * @version 1.0
 * @since <pre>Apr 6, 2021</pre>
 */
public class SqlIssueTest {

    @Mock
    SqlIssue sqlIssueMock;

    @Mock
    SqlIssue sqlIssueTestEqal;

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method: toString()
     */
    @Test
    public void testToString() {
        SqlIssue sqlIssueTestToString = new SqlIssue();
        sqlIssueTestToString.toString();
        String toString = sqlIssueMock.toString();
        String result = "sqlIssueMock";
        Assert.assertEquals(result, toString);
    }

    @Test
    public void testToStringReturnLongMessage() {
        SqlIssue sqlIssueToStringTest = new SqlIssue();
        sqlIssueToStringTest.setMessage("test");
        sqlIssueToStringTest.setLine(2);
        String result1 = "SqlIssue [line=2, repo=null, fileName=null, isAdhoc=false, description=null, name=null, message=test, key=null, severity=null]";
        String toString1 = sqlIssueToStringTest.toString();
        Assert.assertEquals(result1, toString1);
    }

    /**
     * Method: hashCode()
     */
    @Test
    public void testHashCode() {
        SqlIssue sqlIssuesTestHash = new SqlIssue();
        int hashResult = sqlIssuesTestHash.hashCode();
        Assert.assertNotNull(hashResult);
    }
    /**
     * Method: equals(Object obj)
     */
    @Test
    public void testEqualsFalse() {
        SqlIssue sqlIssueEquals1 = new SqlIssue();
        SqlIssue sqlIssueEquals2 = new SqlIssue();
        sqlIssueEquals2.setAdhoc(true);
        Assert.assertFalse(sqlIssueEquals1.equals(sqlIssueEquals2));
        boolean response = sqlIssueMock.equals(sqlIssueTestEqal);
        Assert.assertFalse(response);
    }

    @Test
    public void testEqualsTrue() {
        SqlIssue sqlIssue1 = new SqlIssue();
        SqlIssue sqlIssue2 = new SqlIssue();
        boolean response = sqlIssue1.equals(sqlIssue2);
        Assert.assertTrue(response);
    }

    /**
     * Method: getLine()
     */
    @Test
    public void testGetLine() {
        SqlIssue sqlIssueTestLine = new SqlIssue();
        sqlIssueTestLine.setLine(2);
        Assert.assertEquals(2, sqlIssueTestLine.getLine());
    }

    /**
     * Method: getMessage()
     */
    @Test
    public void testGetMessage() {
        SqlIssue sqlIssueGetMessage = new SqlIssue();
        sqlIssueGetMessage.setMessage("message for test");
        String message = "message for test";
        Assert.assertEquals(message, sqlIssueGetMessage.getMessage());
    }

    /**
     * Method: getRuleType()
     */
    @Test
    public void testGetRuleType() {
        SqlIssue sqlIssueRuleTypeTest = new SqlIssue();
        String ruleType = "CODE_SMELL";
        Assert.assertEquals(ruleType, sqlIssueRuleTypeTest.getRuleType());
    }

    /**
     * Method: isAdhoc()
     */
    @Test
    public void testIsAdhoc() {
        SqlIssue sqlIssueIsAdhocTest = new SqlIssue();
        sqlIssueIsAdhocTest.setAdhoc(true);
        Assert.assertTrue(sqlIssueIsAdhocTest.isAdhoc());
    }

    /**
     * Method: setFileName(String fileName)
     */
    @Test
    public void testSetFileName() {
        SqlIssue sqlIssueFileNameTest = new SqlIssue();
        sqlIssueFileNameTest.setFileName("SG-002");
        String nameFile = "SG-002";
        Assert.assertEquals(nameFile, sqlIssueFileNameTest.getFileName());
    }
}