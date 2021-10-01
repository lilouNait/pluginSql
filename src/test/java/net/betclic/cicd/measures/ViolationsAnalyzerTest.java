package net.betclic.cicd.measures;

import JaxbClasses.Rule;
import JaxbClasses.RuleImplementation;
import net.betclic.cicd.scanner.IParsedNode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ViolationsAnalyzerTest {

    @Mock
    Map<RuleImplementation, List<IParsedNode>> items;
    @Mock
    Set<Map.Entry<RuleImplementation,List<IParsedNode>>> entrySet;
    @Mock
    RuleImplementation ruleImplementation;
    @Mock
    List<IParsedNode> iParsedNodes;
    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void foundViolationTest(){
        ViolationsAnalyzer violationsAnalyzer = new ViolationsAnalyzer();
        items.put(ruleImplementation,iParsedNodes);
        violationsAnalyzer.isMatch(items);
    }
}
