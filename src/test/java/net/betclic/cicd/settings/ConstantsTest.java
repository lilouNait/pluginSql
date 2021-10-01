package net.betclic.cicd.settings;

import org.junit.Assert;
import org.junit.Test;

/**
 * Constants Tester.
 *
 * @author <Authors Nait Amara>
 * @version 1.0
 * @since <pre>Apr 13, 2021</pre>
 */
public class ConstantsTest {

    @Test
    public void constantTest() {
        String dialect = Constants.PLUGIN_SQL_DIALECT;
        String languageKey = Constants.LANGUAGE_KEY;
        String pluginSuffixes = Constants.PLUGIN_SUFFIXES;
        String pluginRulesToSkip = Constants.PLUGIN_SQL_RULES_SKIP;
        String dialectResultExpected = "sql";
        String languageKeyExpected = "sql";
        String pluginSuffixesExpected = ".sql";
        String pluginRulesToSkipExpected = "sonar.sql.rules.skip";
        Assert.assertEquals(dialectResultExpected, dialect);
        Assert.assertEquals(languageKeyExpected, languageKey);
        Assert.assertEquals(pluginSuffixesExpected, pluginSuffixes);
        Assert.assertEquals(pluginRulesToSkipExpected, pluginRulesToSkip);
        Assert.assertEquals("net.betclic.cicd.settings.Constants", Constants.class.getName());
    }

}
