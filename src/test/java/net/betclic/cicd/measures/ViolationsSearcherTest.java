package net.betclic.cicd.measures;

import JaxbClasses.*;
import net.betclic.cicd.scanner.IParsedNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

/**
 * ViolationsSearcher Tester.
 *
 * @author <Authors Nait Amara>
 * @version 1.0
 * @since <pre>Apr 6, 2021</pre>
 */
public class ViolationsSearcherTest {

    @Mock
    FoundMatch foundMatch;
    @Mock
    Rule rule;
    @Mock
    RuleImplementation ruleImplementation;
    @Mock
    UsesRules usesRules;
    @Mock
    ParentRules parentRules;
    @Mock
    SiblingsRules siblingsRules;

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method: search(FoundMatch match)
     */
    @Test
    public void testSearchZeroFounds() {
        ViolationsSearcher violationsSearcher = new ViolationsSearcher();
        Mockito.when(foundMatch.getRule()).thenReturn(rule);
        Mockito.when(foundMatch.getRule().getRuleImplementation()).thenReturn(ruleImplementation);
        Mockito.when(foundMatch.getRule().getRuleImplementation().getParentRules()).thenReturn(parentRules);
        Mockito.when(foundMatch.getRule().getRuleImplementation().getUsesRules()).thenReturn(usesRules);
        Mockito.when(foundMatch.getRule().getRuleImplementation().getSiblingsRules()).thenReturn(siblingsRules);
        Map<RuleImplementation, List<IParsedNode>> resultFound = violationsSearcher.search(foundMatch);
        Assert.assertEquals(1, resultFound.size());
    }

}