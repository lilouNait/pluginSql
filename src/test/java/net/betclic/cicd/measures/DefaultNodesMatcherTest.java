package net.betclic.cicd.measures;

import JaxbClasses.Names;
import JaxbClasses.RuleImplementation;
import JaxbClasses.RuleMatchType;
import net.betclic.cicd.scanner.IParsedNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author <Authors Nait Amara>
 * @version 1.0
 * @since <pre>Apr 4, 2021</pre>
 */
public class DefaultNodesMatcherTest {

    @Mock
    IParsedNode node;

    @Mock
    IParsedNode parseNode;

    @Mock
    RuleImplementation rule;

    @Mock
    IParsedNode item;

    @Mock
    IParsedNode parent;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMatchesFalse() {
        DefaultNodesMatcher nodesMatcher = new DefaultNodesMatcher();
        nodesMatcher.matches(node, parseNode, rule);
        Assert.assertFalse(nodesMatcher.matches(node, parseNode, rule));
    }

    @Test
    public void testMacthesTrue() {

        RuleImplementation ruleImplementation = new RuleImplementation();
        DefaultNodesMatcher dnm = new DefaultNodesMatcher();
        List<String> items = new ArrayList<>();
        items.add("test");
        Names names = new Names();
        names.setTextItem(items);
        ruleImplementation.setNames(names);
        ruleImplementation.setRuleMatchType(RuleMatchType.CLASS_ONLY);
        ruleImplementation.setDistance(0);
        ruleImplementation.setIndex(0);
        when(item.getClassName()).thenReturn("test");
        dnm.matches(item, parent, ruleImplementation);
        Assert.assertTrue(dnm.matches(item, parent, ruleImplementation));
    }

    @Test
    public void testMatchesFullParentNotNull() {
        RuleImplementation ruleImplementation = new RuleImplementation();
        DefaultNodesMatcher matcher = new DefaultNodesMatcher();
        List<String> items = new ArrayList<>();
        items.add("test");
        Names names = new Names();
        names.setTextItem(items);
        ruleImplementation.setNames(names);
        ruleImplementation.setRuleMatchType(RuleMatchType.FULL);
        ruleImplementation.setDistance(0);
        ruleImplementation.setIndex(0);
        when(item.getClassName()).thenReturn("test");
        matcher.matches(item, parent, ruleImplementation);
        Assert.assertFalse(matcher.matches(item, parent, ruleImplementation));
    }

    @Test
    public void testMatchesFullParentNull() {
        RuleImplementation ruleImplementation = new RuleImplementation();
        DefaultNodesMatcher matcher1 = new DefaultNodesMatcher();
        List<String> items = new ArrayList<>();
        items.add("test");
        Names names = new Names();
        names.setTextItem(items);
        ruleImplementation.setNames(names);
        ruleImplementation.setRuleMatchType(RuleMatchType.FULL);
        ruleImplementation.setDistance(0);
        ruleImplementation.setIndex(0);
        when(item.getClassName()).thenReturn("test");
        matcher1.matches(item, parent, ruleImplementation);
        Assert.assertEquals(false, matcher1.matches(item, null, ruleImplementation));
    }

    @Test
    public void testMacthesStrictpParentNull() {
        RuleImplementation ruleImplementation = new RuleImplementation();
        DefaultNodesMatcher matcher3 = new DefaultNodesMatcher();
        List<String> items = new ArrayList<>();
        items.add("test");
        Names names = new Names();
        names.setTextItem(items);
        ruleImplementation.setNames(names);
        ruleImplementation.setRuleMatchType(RuleMatchType.STRICT);
        ruleImplementation.setDistance(0);
        ruleImplementation.setIndex(0);
        when(item.getClassName()).thenReturn("test");
        matcher3.matches(item, parent, ruleImplementation);
        Assert.assertEquals(false, matcher3.matches(item, parent, ruleImplementation));
    }

}
