package net.betclic.cicd.measures;

import JaxbClasses.RuleImplementation;
import net.betclic.cicd.scanner.IParsedNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViolationsSearcher {

    private INodesMatcher matcher = new DefaultNodesMatcher();

    public Map<RuleImplementation, List<IParsedNode>> search(FoundMatch match) {

        Map<RuleImplementation, List<IParsedNode>> founds = new HashMap<>();
        if (match.getRule() != null) {
            RuleImplementation ruleImpl = match.getRule().getRuleImplementation();

            initMap(founds, ruleImpl);
            visit(match.getNode(),null,ruleImpl, founds);
        }
         return founds;
    }

    private void visit(final IParsedNode item,final IParsedNode parent, final RuleImplementation rule,
                       Map<RuleImplementation, List<IParsedNode>> matches) {
        if (!matcher.matches(item, parent, rule)) {
            return;
        }
        List<IParsedNode> temp = matches.getOrDefault(rule, new ArrayList<>());
        temp.add(item);
        matches.put(rule, temp);

        if ((rule.getChildrenRules() != null) && (!rule.getChildrenRules().getRuleImplementation().isEmpty())) {
            for (IParsedNode nodeToCheck : item.getChildren()) {
                for (RuleImplementation ruleToCheck : rule.getChildrenRules().getRuleImplementation()) {
                    visit(nodeToCheck,item, ruleToCheck, matches);
                }
            }
        }
        if ((rule.getParentRules() != null) && (!rule.getParentRules().getRuleImplementation().isEmpty())) {
            for (IParsedNode nodeToCheck : item.getParents()) {
                for (RuleImplementation ruleToCheck : rule.getParentRules().getRuleImplementation()) {
                    visit(nodeToCheck, item,ruleToCheck, matches);
                }
            }
        }
        if ((rule.getSiblingsRules() != null) && (!rule.getSiblingsRules().getRuleImplementation().isEmpty())) {
            for (IParsedNode nodeToCheck : item.getSiblings()) {
                for (RuleImplementation ruleToCheck : rule.getParentRules().getRuleImplementation()) {
                    visit(nodeToCheck, item, ruleToCheck, matches);
                }
            }
        }
        if ((rule.getUsesRules() != null) && (!rule.getUsesRules().getRuleImplementation().isEmpty())) {
            for (IParsedNode nodeToCheck : item.getUses()) {
                for (RuleImplementation ruleToCheck : rule.getUsesRules().getRuleImplementation()) {
                    visit(nodeToCheck, item, ruleToCheck, matches);
                }
            }
        }
    }

    private static void initMap(final Map<RuleImplementation, List<IParsedNode>> map, final RuleImplementation ruleImpl) {
        map.putIfAbsent(ruleImpl, new ArrayList<>());
        if (ruleImpl.getChildrenRules() != null) {
            for (final RuleImplementation i : ruleImpl.getChildrenRules().getRuleImplementation()) {
                initMap(map, i);
            }
        }
        if (ruleImpl.getParentRules() != null) {
            for (final RuleImplementation i : ruleImpl.getParentRules().getRuleImplementation()) {
                initMap(map, i);
            }
        }
        if (ruleImpl.getUsesRules() != null) {
            for (final RuleImplementation i : ruleImpl.getUsesRules().getRuleImplementation()) {
                initMap(map, i);
            }
        }
        if (ruleImpl.getSiblingsRules() != null) {
            for (final RuleImplementation i : ruleImpl.getSiblingsRules().getRuleImplementation()) {
                initMap(map, i);
            }
        }
    }
}
