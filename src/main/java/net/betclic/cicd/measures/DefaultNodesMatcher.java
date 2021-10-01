package net.betclic.cicd.measures;

import JaxbClasses.RuleImplementation;
import net.betclic.cicd.scanner.IParsedNode;
import org.apache.commons.lang3.StringUtils;

public class DefaultNodesMatcher implements INodesMatcher {

    private final IMatcher[] matchers = new IMatcher[]{new ClassNameMatcher(), new TextMatcher(), new IndexMatcher(),
            new DistanceMatcher()};

    @Override
    public boolean matches(IParsedNode item, IParsedNode parent, RuleImplementation rule) {
        for (final IMatcher matcher : matchers) {
            if (!matcher.match(item, rule)) {
                return false;
            }
        }

        switch (rule.getRuleMatchType()) {
            case FULL:
                if (ruleMatchTypeFull(item, parent)) return false;
                break;
            case STRICT:
                if (ruleMatchTypeStrict(item, parent)) return false;
                break;
            default:
                return true;
        }
        return true;
    }

    private boolean ruleMatchTypeStrict(IParsedNode item, IParsedNode parent) {
        final IParsedNode parent1 = parent.getControlFlowParent();
        final IParsedNode parent2 = item.getControlFlowParent();
        return !StringUtils.equalsIgnoreCase(item.getText(), parent.getText()) || parent1 == null || !parent.equals(parent2);
    }

    private boolean ruleMatchTypeFull(IParsedNode item, IParsedNode parent) {
        return parent == null || item == null || (!StringUtils.containsIgnoreCase(item.getText(), parent.getText())
                && !StringUtils.containsIgnoreCase(parent.getText(), item.getText()));
    }
}
