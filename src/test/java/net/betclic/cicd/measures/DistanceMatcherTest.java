package net.betclic.cicd.measures;

import JaxbClasses.RuleDistanceIndexMatchType;
import JaxbClasses.RuleImplementation;
import net.betclic.cicd.scanner.IParsedNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class DistanceMatcherTest {

    @Mock
    IParsedNode item;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMatchTrue() {
        DistanceMatcher distanceMatcher = new DistanceMatcher();
        RuleImplementation ruleImplementation = new RuleImplementation();
        ruleImplementation.setDistance(0);
        distanceMatcher.match(item, ruleImplementation);
        Assert.assertEquals(true, distanceMatcher.match(item, ruleImplementation));
    }

    @Test
    public void testMacthDistanceIndexLess() {
        DistanceMatcher distanceMatcher = new DistanceMatcher();
        RuleImplementation ruleImplementation = new RuleImplementation();
        ruleImplementation.setDistance(2);
        when(item.getDistance()).thenReturn(3);
        ruleImplementation.setDistanceCheckType(RuleDistanceIndexMatchType.LESS);
        distanceMatcher.match(item, ruleImplementation);
        Assert.assertEquals(false, distanceMatcher.match(item, ruleImplementation));
    }

    @Test
    public void testMacthDistanceIndexMore() {
        DistanceMatcher distanceMatcher = new DistanceMatcher();
        RuleImplementation ruleImplementation = new RuleImplementation();
        ruleImplementation.setDistance(2);
        when(item.getDistance()).thenReturn(1);
        ruleImplementation.setDistanceCheckType(RuleDistanceIndexMatchType.MORE);
        distanceMatcher.match(item, ruleImplementation);
        Assert.assertEquals(false, distanceMatcher.match(item, ruleImplementation));
    }

    @Test
    public void testMatchDistanceIndexEqualsFalse() {
        DistanceMatcher distanceMatcher = new DistanceMatcher();
        RuleImplementation ruleImplementation = new RuleImplementation();
        ruleImplementation.setDistance(3);
        when(item.getDistance()).thenReturn(3);
        ruleImplementation.setDistanceCheckType(RuleDistanceIndexMatchType.EQUALS);
        distanceMatcher.match(item, ruleImplementation);
        Assert.assertEquals(true, distanceMatcher.match(item, ruleImplementation));
    }

    @Test
    public void testMatchDistanceIndexEqualsKo() {
        DistanceMatcher distanceMatcher = new DistanceMatcher();
        RuleImplementation ruleImplementation = new RuleImplementation();
        ruleImplementation.setDistance(3);
        when(item.getDistance()).thenReturn(2);
        ruleImplementation.setDistanceCheckType(RuleDistanceIndexMatchType.EQUALS);
        distanceMatcher.match(item, ruleImplementation);
        Assert.assertEquals(false, distanceMatcher.match(item, ruleImplementation));
    }


}
