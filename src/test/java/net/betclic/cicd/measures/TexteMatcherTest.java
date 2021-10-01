package net.betclic.cicd.measures;

import JaxbClasses.RuleImplementation;
import JaxbClasses.TextCheckType;
import JaxbClasses.TextToFind;
import net.betclic.cicd.scanner.IParsedNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class TexteMatcherTest {


    @Mock
    IParsedNode node;

    @Mock
    TextToFind textToFind;
    @Mock
    RuleImplementation ruleImplementationTest;
    @Mock
    List<String> t;
    @Mock
    TextCheckType type;

    @InjectMocks
    TextMatcher textMatcher;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMatchNamesEmpty() {
        RuleImplementation ruleImplementation = new RuleImplementation();
        ruleImplementation.setTextToFind(textToFind);
        textMatcher.match(node, ruleImplementation);
        Assert.assertTrue(textMatcher.match(node, ruleImplementation));
    }

    @Test
    public void testMatchSwitchType() {
        RuleImplementation ruleImplementation = new RuleImplementation();
        IParsedNode iParsedNode = new IParsedNode() {
            @Override
            public String getClassName() {
                return null;
            }

            @Override
            public String getText() {
                return "USE";
            }

            @Override
            public int getLine() {
                return 0;
            }

            @Override
            public IParsedNode getControlFlowParent() {
                return null;
            }
        };
        ruleImplementation.setTextToFind(textToFind);
        ruleImplementation.setTextCheckType(TextCheckType.CONTAINS);
        List<String> names = new ArrayList<>();
        names.add("USE");
        Mockito.when(textToFind.getTextItem()).thenReturn(names);
        textMatcher.match(iParsedNode, ruleImplementation);
        Assert.assertTrue(textMatcher.match(iParsedNode, ruleImplementation));

        ruleImplementation.setTextCheckType(TextCheckType.STRICT);
        Assert.assertTrue(textMatcher.match(iParsedNode, ruleImplementation));

        ruleImplementation.setTextCheckType(TextCheckType.REGEXP);
        Assert.assertTrue(textMatcher.match(iParsedNode, ruleImplementation));
    }
}
