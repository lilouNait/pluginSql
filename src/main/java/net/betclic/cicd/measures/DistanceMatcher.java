package net.betclic.cicd.measures;

import JaxbClasses.RuleDistanceIndexMatchType;
import JaxbClasses.RuleImplementation;
import net.betclic.cicd.scanner.IParsedNode;

public class DistanceMatcher implements IMatcher {
    @Override
    public boolean match(IParsedNode item, RuleImplementation rule) {
        boolean isMatching = true;
        int val = item.getDistance();
        if (((rule.getDistanceCheckType() == RuleDistanceIndexMatchType.LESS) && (val > rule.getDistance())) ||
                ((rule.getDistanceCheckType() == RuleDistanceIndexMatchType.MORE) && (val < rule.getDistance())) ||
                ((rule.getDistanceCheckType() == RuleDistanceIndexMatchType.EQUALS) && (rule.getDistance() != val))) {
            isMatching = false;
        }
        return isMatching;
    }
}
