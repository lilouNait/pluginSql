package net.betclic.cicd.measures;

import JaxbClasses.RuleDistanceIndexMatchType;
import JaxbClasses.RuleImplementation;
import net.betclic.cicd.scanner.IParsedNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * IndexMatcher Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 6, 2021</pre>
 */
public class IndexMatcherTest {

    @Mock
    IParsedNode node;

    @Mock
    RuleImplementation ruleImplementation;

    @InjectMocks
    IndexMatcher indexMatcher;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method: match(IParsedNode node, RuleImplementation rule) when rule.getIndex() > 0
     */
    @Test
    public void testMatchNodeZero() {
        when(ruleImplementation.getIndex()).thenReturn(0);
        indexMatcher.match(node, ruleImplementation);
        Assert.assertEquals(true, indexMatcher.match(node, ruleImplementation));
    }

    @Test
    public void testMatchNodeNotNullEquals() {
        when(ruleImplementation.getIndex()).thenReturn(1);
        when(ruleImplementation.getIndexCheckType()).thenReturn(RuleDistanceIndexMatchType.EQUALS);
        Assert.assertEquals(false, indexMatcher.match(node, ruleImplementation));
    }

    @Test
    public void testMatchNodeNotNullLess() {
        when(ruleImplementation.getIndex()).thenReturn(1);
        when(ruleImplementation.getIndexCheckType()).thenReturn(RuleDistanceIndexMatchType.LESS);
        when(node.getIndex()).thenReturn(2);
        Assert.assertEquals(false, indexMatcher.match(node, ruleImplementation));
    }

    @Test
    public void testMatchNodeNotNullMore() {
        when(ruleImplementation.getIndex()).thenReturn(1);
        when(ruleImplementation.getIndexCheckType()).thenReturn(RuleDistanceIndexMatchType.MORE);
        when(node.getIndex()).thenReturn(3);
        Assert.assertEquals(false, indexMatcher.match(node, ruleImplementation));
    }

    /**
     * Method: match(IParsedNode node, RuleImplementation rule) when rule.getIndex() < 0
     */

    @Test
    public void testMatchNodeEquals() {
        when(ruleImplementation.getIndex()).thenReturn(-1);
        when(ruleImplementation.getIndexCheckType()).thenReturn(RuleDistanceIndexMatchType.LESS);
        when(node.getIndex2()).thenReturn(2);
        Assert.assertEquals(false, indexMatcher.match(node, ruleImplementation));
    }

    @Test
    public void testMatchNodeLess() {
        when(ruleImplementation.getIndex()).thenReturn(-1);
        when(ruleImplementation.getIndexCheckType()).thenReturn(RuleDistanceIndexMatchType.EQUALS);
        when(node.getIndex2()).thenReturn(2);
        Assert.assertEquals(false, indexMatcher.match(node, ruleImplementation));
    }

    @Test
    public void testMatchNodeMore() {
        when(ruleImplementation.getIndex()).thenReturn(-1);
        when(ruleImplementation.getIndexCheckType()).thenReturn(RuleDistanceIndexMatchType.MORE);
        when(node.getIndex()).thenReturn(3);
        Assert.assertEquals(false, indexMatcher.match(node, ruleImplementation));
    }

}
