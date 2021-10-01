package net.betclic.cicd.measures;

import JaxbClasses.RuleImplementation;
import net.betclic.cicd.scanner.IParsedNode;

public interface INodesMatcher {
    boolean matches(IParsedNode item, IParsedNode parent, RuleImplementation rule);

}
