package net.betclic.cicd.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

/**
 * BaseRules Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 6, 2021</pre>
 */
public class BaseRulesTest {

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method: getUseRule()
     */
    @Test
    public void testGetUseRule() {
        TSQLRules.createUseRule().getName();
        String nameRule = "Unexpected USE keyword found";
        Assert.assertEquals(nameRule, TSQLRules.createUseRule().getName());
    }

    /**
     * Method: getIdentityRule()
     */
    @Test
    public void testGetIdentityRule() {
        TSQLRules.createUseRule().getName();
        String nameRule = "Unexpected USE keyword found";
        Assert.assertEquals(nameRule, TSQLRules.createUseRule().getName());
    }

    /**
     * Method: getError()
     */
    @Test
    public void testGetError() {
        TSQLRules.createErrorRule().getName();
        String nameRule = "@@ERROR used";
        Assert.assertEquals(nameRule, TSQLRules.createErrorRule().getName());
    }

}

