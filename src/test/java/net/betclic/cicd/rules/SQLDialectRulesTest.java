package net.betclic.cicd.rules;

import JaxbClasses.SqlRules;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * SQLDialectRules Tester.
 *
 * @author <Authors Nait Amara>
 * @version 1.0
 * @since <pre>Apr 6, 2021</pre>
 */
public class SQLDialectRulesTest {

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method: getRules()
     */
    @Test
    public void testGetRules() throws IOException, URISyntaxException {
        List<SqlRules> rules = SQLDialectRules.getInstance().getRules();
        int numberOfRules = TSQLRules.getRules().size();
        Assert.assertEquals(numberOfRules, rules.size());
    }

    /**
     * Method: getRulesForQualityProfile()
     */
    @Test
    public void testGetRulesForQualityProfile() throws IOException, URISyntaxException {
        List<SqlRules> rulesQualityProfile = SQLDialectRules.getInstance().getRulesForQualityProfile();
        int numberOfRulesQualityProfile = TSQLRules.getRules().size();
        Assert.assertEquals(numberOfRulesQualityProfile, rulesQualityProfile.iterator().next().getRules().size());
    }
}
