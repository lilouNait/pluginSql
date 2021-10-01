package net.betclic.cicd.rules;

import JaxbClasses.Rule;
import JaxbClasses.SqlRules;

import java.util.*;

public class SQLDialectRules {

    private static SQLDialectRules sqlDialectRules = new SQLDialectRules();

    public static SQLDialectRules getInstance() {
        return sqlDialectRules;
    }

    public List<SqlRules> getRules() {
        List<SqlRules> rules = new ArrayList<>();
        rules.addAll(TSQLRules.getRules());
        return rules;
    }

    /**
     * @return a list of SqlRules that will be charged in the quality profile
     */
    public List<SqlRules> getRulesForQualityProfile() {
        Map<String, Rule> rules = new TreeMap<>();
        getRules().forEach(sqlRules -> sqlRules.getRules().forEach(rule -> settingQualityProfileRule(rules, rule)));
        SqlRules customRules = new SqlRules();
        customRules.setRepoKey("SQL-RULES");
        customRules.setRepoName("SQL Plugin checks");
        customRules.getRules().addAll(rules.values());
        return Arrays.asList(customRules);
    }

    private void settingQualityProfileRule(Map<String, Rule> rules, Rule rule) {
        String key = rule.getKey();
        if ((!rules.containsKey(key)) && ((!rule.getRuleImplementation().getViolatingRulesCodeExamples().getRuleCodeExample().isEmpty()
                || !rule.getRuleImplementation().getCompliantRulesCodeExamples().getRuleCodeExample()
                .isEmpty()) && (!rule.getRuleImplementation().getViolatingRulesCodeExamples().getRuleCodeExample()
                .isEmpty())) && (!rule.getRuleImplementation().getCompliantRulesCodeExamples().getRuleCodeExample()
                .isEmpty())) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(rule.getDescription());
            stringBuilder.append("<h2>Code examples</h2>");
            stringBuilder.append("<h3>Non-compliant</h3>");
            for (String x : rule.getRuleImplementation().getViolatingRulesCodeExamples()
                    .getRuleCodeExample()) {
                stringBuilder.append("<pre><code>" + x + "</code></pre>");
            }
            stringBuilder.append("<h3>Compliant</h3>");
            for (String x : rule.getRuleImplementation().getCompliantRulesCodeExamples()
                    .getRuleCodeExample()) {
                stringBuilder.append("<pre><code>" + x + "</code></pre>");
            }
            rule.setDescription(stringBuilder.toString());
            rules.put(key, rule);
        }
    }
}