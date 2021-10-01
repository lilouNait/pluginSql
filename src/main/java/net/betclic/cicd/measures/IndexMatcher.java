package net.betclic.cicd.measures;

import JaxbClasses.RuleDistanceIndexMatchType;
import JaxbClasses.RuleImplementation;
import net.betclic.cicd.scanner.IParsedNode;

public class IndexMatcher implements IMatcher {
    @Override
    public boolean match(IParsedNode node, RuleImplementation rule) {
        RuleDistanceIndexMatchType indexMatchType = rule.getIndexCheckType();
        if ((rule.getIndex() > 0) || (rule.getIndex() < 0)) {
            int val = node.getIndex();
            int val1 = node.getIndex2();
            if (((indexMatchType == RuleDistanceIndexMatchType.LESS) && (val > rule.getIndex())) ||
                    ((indexMatchType == RuleDistanceIndexMatchType.EQUALS) && (val != rule.getIndex())) ||
                    ((indexMatchType == RuleDistanceIndexMatchType.MORE) && (rule.getIndex() < val)) ||
                    (indexMatchType == RuleDistanceIndexMatchType.LESS) && (val1 > rule.getIndex()) ||
                    ((indexMatchType == RuleDistanceIndexMatchType.EQUALS) && (val1 != rule.getIndex())) ||
                    ((indexMatchType == RuleDistanceIndexMatchType.MORE) && (rule.getIndex() < val1))) {
                return false;
            }
        }
        return true;
    }
}
