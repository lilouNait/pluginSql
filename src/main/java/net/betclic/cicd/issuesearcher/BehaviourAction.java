package net.betclic.cicd.issuesearcher;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum BehaviourAction {
    FAIL_IF_FOUND, FAIL_IF_NOT_FOUND
}
