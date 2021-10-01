package net.betclic.cicd.measures;

import JaxbClasses.Names;
import JaxbClasses.RuleImplementation;
import JaxbClasses.RuleMatchType;
import net.betclic.cicd.scanner.IParsedNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ClassNameMatcherTest {

    @Test
    public void testMatchOk() {
        RuleImplementation ruleImplementation = new RuleImplementation();
        ruleImplementation.setRuleMatchType(RuleMatchType.TEXT_ONLY);
        List<String> listTextItem = new ArrayList<>();
        listTextItem.add("test");
        Names names = new Names();
        names.setTextItem(listTextItem);
        ruleImplementation.setNames(names);

        IParsedNode iParsedNode = new IParsedNode() {
            @Override
            public String getClassName() {
                return "test";
            }

            @Override
            public String getText() {
                return null;
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
        ClassNameMatcher classNameMatcher = new ClassNameMatcher();
        classNameMatcher.match(iParsedNode, ruleImplementation);
        Assert.assertEquals(true, classNameMatcher.match(iParsedNode, ruleImplementation));
    }

}
