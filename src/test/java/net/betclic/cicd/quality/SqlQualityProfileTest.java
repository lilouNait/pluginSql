package net.betclic.cicd.quality;

import net.betclic.cicd.settings.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;

/**
 * SqlQualityProfile Tester.
 *
 * @author <Authors Nait Amara>
 * @version 1.0
 * @since <pre>Apr 5, 2021</pre>
 */
public class SqlQualityProfileTest {

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method: define(Context context)
     */
    @Test
    public void testDefine() {
        SqlQualityProfile sqlQualityProfileTest = new SqlQualityProfile();
        BuiltInQualityProfilesDefinition.Context context = new BuiltInQualityProfilesDefinition.Context();
        context.profile("SQL rules", Constants.LANGUAGE_KEY);
        sqlQualityProfileTest.define(context);
        String constantLanguageKey = Constants.LANGUAGE_KEY;
        Assert.assertEquals("sql", constantLanguageKey);
    }

}

