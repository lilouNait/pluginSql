package net.betclic.cicd.measures;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * SqlIssuesList Tester.
 *
 * @author <Authors NaitAmara>
 * @version 1.0
 * @since <pre>Apr 6, 2021</pre>
 */
public class SqlIssuesListTest {

    @Mock
    SqlIssue sqlIssue;
    @Mock
    SqlIssuesList sqlIssuesList;

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method: getIssues()
     */
    @Test
    public void testGetIssues() {
        SqlIssuesList sqlIssuesListTestGetIssues = new SqlIssuesList();
        sqlIssuesListTestGetIssues.addIssue(sqlIssue);
        Map<String, Set<SqlIssue>> getIssues = sqlIssuesListTestGetIssues.getIssues();
        Assert.assertEquals(1, getIssues.size());
    }

    /**
     * Method: getaLLIssues()
     */
    @Test
    public void testGetaLLIssues() {
        SqlIssuesList sqlIssuesListTestGetAllIssues = new SqlIssuesList();
        sqlIssuesListTestGetAllIssues.addIssue(sqlIssue);
        Collection<SqlIssue> getAllIssues = sqlIssuesListTestGetAllIssues.getAllIssues();
        Assert.assertEquals(1, getAllIssues.size());
    }

    /**
     * Method: addIssue(final SqlIssue issue)
     */
    @Test
    public void testAddIssue() {
        SqlIssuesList sqlIssuesListAddIssue = new SqlIssuesList();
        sqlIssuesListAddIssue.addIssue(sqlIssue);
        sqlIssuesList.addIssue(sqlIssue);
        Mockito.verify(sqlIssuesList, Mockito.times(1)).addIssue(sqlIssue);
    }


}
