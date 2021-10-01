package net.betclic.cicd.rules;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.sonar.api.server.rule.RulesDefinition;

/**
 * SqlRulesDefinition Tester.
 *
 * @author <Authors Nait Amara>
 * @version 1.0
 * @since <pre>Apr 6, 2021</pre>
 */
public class SqlRulesDefinitionTest {

    @Mock
    RulesDefinition.Context context;
    @Mock
    SqlRulesDefinition sqlRulesDefinition;

    @Before
    public void before(){
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method: define(Context context)
     */
    @Test
    public void testDefine() {
        SqlRulesDefinition sqlRulesDefinition1= new SqlRulesDefinition();
        RulesDefinition.Context context1 = new RulesDefinition.Context();
        sqlRulesDefinition1.define(context1);
        sqlRulesDefinition.define(context);
        Mockito.verify(sqlRulesDefinition, Mockito.times(1)).define(context);
    }

}


