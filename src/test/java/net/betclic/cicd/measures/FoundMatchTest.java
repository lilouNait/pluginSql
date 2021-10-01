package net.betclic.cicd.measures;

import JaxbClasses.Rule;
import JaxbClasses.SqlRules;
import net.betclic.cicd.scanner.IParsedNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * FoundMatch Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 6, 2021</pre>
 */
public class FoundMatchTest {

    @Mock
    IParsedNode node;

    @Mock
    IParsedNode node1;
    @Mock
    Rule rule;
    @Mock
    SqlRules rules;

    @Mock
    FoundMatch foundMatch;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method: setNode(IParsedNode node)
     */
    @Test
    public void testSetNode() {
        foundMatch.setNode(node);
        verify(foundMatch, times(1)).setNode(node);
    }

    /**
     * Method: setRule(Rule rule)
     */
    @Test
    public void testSetRule() {
        foundMatch.setRule(rule);
        verify(foundMatch, times(1)).setRule(rule);
    }

    /**
     * Method: setSqlRules(SqlRules sqlRules)
     */
    @Test
    public void testSetSqlRules() {
        foundMatch.setSqlRules(rules);
        verify(foundMatch, times(1)).setSqlRules(rules);
    }

    /**
     * Method: getNode()
     */
    @Test
    public void testGetNode() {
        FoundMatch foundMatch1 = new FoundMatch();
        foundMatch1.setNode(node);
        foundMatch1.getNode();
        assertEquals(node, foundMatch1.getNode());
    }

    /**
     * Method: getRule()
     */
    @Test
    public void testGetRule() {
        FoundMatch foundMatch1 = new FoundMatch();
        foundMatch1.setRule(rule);
        foundMatch1.getRule();
        assertEquals(rule, foundMatch1.getRule());
    }

    /**
     * Method: getSqlRules()
     */
    @Test
    public void testGetSqlRules() {
        FoundMatch foundMatch1 = new FoundMatch();
        foundMatch1.setSqlRules(rules);
        foundMatch1.getSqlRules();
        assertEquals(rules, foundMatch1.getSqlRules());
    }

    /**
     * Method: getLine()
     */
    @Test
    public void testGetLineNodeNotNull() {
        FoundMatch foundMatch1 = new FoundMatch();
        foundMatch1.setNode(node);
        foundMatch1.getLine();
        when(node.getLine()).thenReturn(2);
        assertEquals(2, foundMatch1.getLine());
    }

    @Test
    public void testGetLineNodeNull() {
        FoundMatch foundMatch1 = new FoundMatch();
        foundMatch1.setNode(null);
        foundMatch1.getLine();
        assertEquals(1, foundMatch1.getLine());
    }

    /**
     * Method: hashCode()
     */
    @Test
    public void testHashCode() {
        FoundMatch foundMatchTestHashCode = new FoundMatch();
        foundMatchTestHashCode.hashCode();
        Assert.assertEquals(31, foundMatchTestHashCode.hashCode());
    }

    /**
     * Method: equals(Object obj)
     */
    @Test
    public void testEquals() {
        FoundMatch foundMatch1 = new FoundMatch();
        foundMatch1.setNode(node);
        foundMatch1.setSqlRules(rules);
        foundMatch1.setRule(rule);

        FoundMatch foundMatch2 = new FoundMatch();
        foundMatch2.setNode(node);
        foundMatch2.setSqlRules(rules);
        foundMatch2.setRule(rule);

        foundMatch.equals(foundMatch1);
        Assert.assertFalse(foundMatch.equals(foundMatch1));
        Assert.assertTrue(foundMatch1.equals(foundMatch2));
        Assert.assertFalse(foundMatch.equals(null));
        Assert.assertFalse(foundMatch.equals(rule));

        foundMatch1.setNode(null);
        Assert.assertFalse(foundMatch1.equals(foundMatch2));

        foundMatch1.setNode(node1);
        Assert.assertFalse(foundMatch1.equals(foundMatch2));

        foundMatch2.setNode(null);
        Assert.assertFalse(foundMatch1.equals(foundMatch2));
    }

}

