package net.betclic.cicd.rules;

import JaxbClasses.SqlRules;
import net.betclic.cicd.settings.Constants;
import org.sonar.api.server.rule.RulesDefinition;

import java.util.List;

public class SqlRulesDefinition implements RulesDefinition {

    @Override
    public void define(Context context) {

        List<SqlRules> rules = SQLDialectRules.getInstance().getRulesForQualityProfile();

        for (SqlRules sqlRules : rules) {
            NewRepository repository = context.createRepository(sqlRules.getRepoKey(), Constants.LANGUAGE_KEY)
                    .setName(sqlRules.getRepoName());

            for (JaxbClasses.Rule rule : sqlRules.getRules()) {
                NewRule x1Rule = repository.createRule(rule.getKey()).setName(rule.getName())
                        .setHtmlDescription(rule.getDescription());

                x1Rule.setActivatedByDefault(true);
            }
            repository.done();

        }

    }

}
