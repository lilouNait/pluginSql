package net.betclic.cicd.measures;

import JaxbClasses.RuleImplementation;
import JaxbClasses.RuleMatchType;
import net.betclic.cicd.scanner.IParsedNode;

import java.util.List;

public class ClassNameMatcher implements IMatcher {
    @Override
    public boolean match(IParsedNode item, RuleImplementation ruleImplementation) {
        if ((ruleImplementation!=null && ruleImplementation.getRuleMatchType() != null) && ruleImplementation.getRuleMatchType() == RuleMatchType.TEXT_ONLY) {
            return true;
        }

        if (ruleImplementation!= null && ruleImplementation.getNames() != null) {
            final List<String> names = ruleImplementation.getNames().getTextItem();
            return names.contains(item.getClassName());
        }
        return false;
    }
}

