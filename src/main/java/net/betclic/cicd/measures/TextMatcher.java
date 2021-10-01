package net.betclic.cicd.measures;

import JaxbClasses.RuleImplementation;
import JaxbClasses.RuleMatchType;
import JaxbClasses.TextCheckType;
import net.betclic.cicd.scanner.IParsedNode;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class TextMatcher implements IMatcher{
    @Override
    public boolean match(IParsedNode item, RuleImplementation ruleImplementation) {
        if (ruleImplementation.getRuleMatchType() == RuleMatchType.CLASS_ONLY) {
            return true;
        }
        final TextCheckType type = ruleImplementation.getTextCheckType();

        final String text = item.getText();
        if ((ruleImplementation.getTextToFind() != null)) {
            final List<String> names = ruleImplementation.getTextToFind().getTextItem();
            if (names.isEmpty()) {
                return true;
            }
            for (final String searchItem : names) {
                if (switchType(type, text, searchItem)) return true;
            }
        }
        return false;
    }

    private boolean switchType(TextCheckType type, String text, String searchItem) {
        switch (type) {
            case DEFAULT:
            case CONTAINS:
                if (StringUtils.containsIgnoreCase(text, searchItem)) {
                    return true;
                }
                break;

            case REGEXP:
                if (text.matches(searchItem)) {
                    return true;
                }
                break;
            case STRICT:
                if (text.equalsIgnoreCase(searchItem)) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }


}
