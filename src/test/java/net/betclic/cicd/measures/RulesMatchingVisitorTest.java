package net.betclic.cicd.measures;

import JaxbClasses.SqlRules;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

/**
 * RulesMatchingVisitor Tester.
 *
 * @author <Authors NaitAmara>
 * @version 1.0
 * @since <pre>Apr 6, 2021</pre>
 */
public class RulesMatchingVisitorTest {

    @Mock
    List<SqlRules> rules;
    @Mock
    ParseTree tree;
    @Mock
    SqlRules sqlRules;

    @InjectMocks
    RulesMatchingVisitor rulesMatchingVisitor;

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method: getMatches()
     */
    @Test
    public void testGetMatches() {
        List<FoundMatch> found = rulesMatchingVisitor.getMatches();
        Assert.assertEquals(0, found.size());
    }

    /**
     * Method: visit(final ParseTree tree)
     */
    @Test
    public void testVisit() {
        rules.add(sqlRules);
        RulesMatchingVisitor rulesMatchingVisitorTest = new RulesMatchingVisitor(rules);
        rulesMatchingVisitorTest.visit(tree);
        rulesMatchingVisitorTest.getMatches();
        List<FoundMatch> found = new LinkedList<>();
        Assert.assertEquals(found, rulesMatchingVisitorTest.getMatches());
    }


}
