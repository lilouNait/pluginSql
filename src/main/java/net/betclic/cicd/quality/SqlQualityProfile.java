package net.betclic.cicd.quality;

import JaxbClasses.Rule;
import JaxbClasses.SqlRules;
import net.betclic.cicd.rules.SQLDialectRules;
import net.betclic.cicd.settings.Constants;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;

import java.util.List;

public class SqlQualityProfile implements BuiltInQualityProfilesDefinition {

    @Override
    public void define(Context context) {
        final NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile("SQL rules", Constants.LANGUAGE_KEY)
                .setDefault(true);
        final List<SqlRules> rules = SQLDialectRules.getInstance().getRulesForQualityProfile();
        for (SqlRules sqlRules : rules) {
            for (Rule rule : sqlRules.getRules()) {
                profile.activateRule(sqlRules.getRepoKey(), rule.getKey());
            }
        }
        profile.done();
    }
}
