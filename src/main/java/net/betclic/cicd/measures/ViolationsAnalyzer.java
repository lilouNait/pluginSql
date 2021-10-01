package net.betclic.cicd.measures;

import JaxbClasses.RuleDistanceIndexMatchType;
import JaxbClasses.RuleImplementation;
import JaxbClasses.RuleResultType;
import net.betclic.cicd.scanner.IParsedNode;

import java.util.*;
import java.util.stream.Collectors;

public class ViolationsAnalyzer {

    public static class FoundViolation {
        public final Set<IParsedNode> violatingNodes = new HashSet<>();
        private boolean failuresFound = false;

        public boolean isFailuresFound() {
            return failuresFound;
        }

    }

    public FoundViolation isMatch(
            Map<RuleImplementation, List<IParsedNode>> items) {
        boolean skipSatisfied = false;
        boolean failuresFound = false;
        FoundViolation violation = new FoundViolation();
        for (Map.Entry<RuleImplementation, List<IParsedNode>> item : items.entrySet()) {

            RuleImplementation impl = item.getKey();
            List<IParsedNode> values = item.getValue();

            if (RuleResultType.DEFAULT == impl.getRuleResultType()) {
                continue;
            }

            skipSatisfied = isSkipSatisfied(skipSatisfied, impl, values);
            failuresFound = isFailuresFound(failuresFound, violation, impl, values);
        }

        if (skipSatisfied) {
            violation.failuresFound = false;
            return violation;
        }

        boolean orderViodlated = isOrderViodlated(items, violation);

        violation.failuresFound = failuresFound || orderViodlated;
        return violation;
    }

    private boolean isSkipSatisfied(boolean skipSatisfied, RuleImplementation impl, List<IParsedNode> values) {
        if ((RuleResultType.SKIP_IF_FOUND == impl.getRuleResultType() && !values.isEmpty()) ||
                (RuleResultType.SKIP_IF_NOT_FOUND == impl.getRuleResultType() && values.isEmpty())) {
            skipSatisfied = true;
        }
        return skipSatisfied;
    }

    private boolean isFailuresFound(boolean failuresFound, FoundViolation violation, RuleImplementation impl, List<IParsedNode> values) {
        if ((RuleResultType.FAIL_IF_FOUND == impl.getRuleResultType() && !values.isEmpty()) ||
                (RuleResultType.FAIL_IF_NOT_FOUND == impl.getRuleResultType() && values.isEmpty()) ||
                (RuleResultType.FAIL_IF_MORE_FOUND == impl.getRuleResultType() && values.size() > impl.getTimes()) ||
                (RuleResultType.FAIL_IF_LESS_FOUND == impl.getRuleResultType() && values.size() < impl.getTimes())) {
            failuresFound = true;
            violation.violatingNodes.addAll(values);

        }
        return failuresFound;
    }

    private boolean isOrderViodlated(Map<RuleImplementation, List<IParsedNode>> items, FoundViolation violation) {
        boolean orderViodlated = false;
        // check order
        List<RuleImplementation> rules = items.keySet().stream()
                .sorted(Comparator.comparing(RuleImplementation::getIndex)).collect(Collectors.toList());
        int prev = 0;
        for (RuleImplementation rule : rules) {
            if (rule.getIndex() == 0 || rule.getDistanceCheckType() != RuleDistanceIndexMatchType.BEFOREORAFTER) {
                continue;
            }
            for (IParsedNode node : items.get(rule)) {
                if (node.getGlobalIndex() < prev) {
                    orderViodlated = true;
                    violation.violatingNodes.add(node);
                    break;
                }
                prev = node.getGlobalIndex();
            }
        }
        return orderViodlated;
    }
}
