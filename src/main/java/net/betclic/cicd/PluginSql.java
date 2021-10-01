package net.betclic.cicd;

import net.betclic.cicd.language.SqlLanguages;
import net.betclic.cicd.quality.SqlQualityProfile;
import net.betclic.cicd.rules.SqlRulesDefinition;
import net.betclic.cicd.scanner.IssueUtil;
import net.betclic.cicd.scanner.SqlSensor;
import net.betclic.cicd.settings.Constants;
import org.sonar.api.Plugin;
import org.sonar.api.PropertyType;
import org.sonar.api.config.PropertyDefinition;

/**
 * Entry point of the sql plugin containing the custom net.betclic.cicd.rules
 */
public class PluginSql implements Plugin{

    public void define(Context context) {

        context.addExtension(PropertyDefinition.builder(Constants.PLUGIN_SQL_DIALECT).name("SQL dialect")
                .description("SQL dialect for analysis").defaultValue("tsql").type(PropertyType.STRING).build());
        context.addExtension(PropertyDefinition.builder(Constants.PLUGIN_SUFFIXES).name("File suffixes")
                .description("File suffixes which will be reported belonging to SQL language").multiValues(true)
                .defaultValue(".sql").type(PropertyType.STRING).build());
        context.addExtensions(SqlSensor.class, SqlRulesDefinition.class, SqlLanguages.class, SqlQualityProfile.class,
                IssueUtil.class);

    }
}
