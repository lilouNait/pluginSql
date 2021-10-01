package net.betclic.cicd.measures;

import JaxbClasses.RuleImplementation;
import net.betclic.cicd.scanner.IParsedNode;

public interface IMatcher {
    boolean match(IParsedNode item, RuleImplementation ruleImplementation);

}
