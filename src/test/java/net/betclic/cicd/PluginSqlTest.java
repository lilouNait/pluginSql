package net.betclic.cicd;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sonar.api.Plugin;
import org.sonar.api.SonarRuntime;

/**
 * PluginSql Tester.
 *
 * @author <Authors NaitAmara>
 * @version 1.0
 * @since <pre>Apr 14, 2021</pre>
 */
public class PluginSqlTest {

    @Mock
    SonarRuntime sonarRuntime;

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method: define(Context context)
     */
    @Test
    public void testDefine() {
        PluginSql pluginSql = new PluginSql();
        Plugin.Context contextTest = new Plugin.Context(sonarRuntime);
        pluginSql.define(contextTest);
        Assert.assertEquals(7, contextTest.getExtensions().size());
    }

}
