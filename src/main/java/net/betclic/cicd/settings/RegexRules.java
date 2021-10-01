package net.betclic.cicd.settings;

public class RegexRules {

    public static final String AS_MISSING_RULE_REGEX ="\\)\\s*BEGIN|\\]\\s*BEGIN";
    public static final String TRY_MISSING_RULE_REGEX ="\\)\\s*BEGIN\\s*[^TRY]$";
    public static  final String CREATE_PROCEDURE_REGEX_CONTEXT = "CREATE\\s*OR\\s*ALTER\\s*PROCEDURE(.*?)\\s*BEGIN|CREATE\\s*OR\\s*ALTER\\s*PROCEDURE(.*?)\\s*BEGIN\\s*TRY";
}
