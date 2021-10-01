package net.betclic.cicd.rules;

import JaxbClasses.Rule;
import net.betclic.cicd.settings.Constants;
import org.apache.tools.ant.taskdefs.Jar;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

/**
 * BaseRules Tester.
 *
 * @author <Nait Amara>
 * @version 1.0
 * @since <pre>Apr 6, 2021</pre>
 */
public class TSQLRulesTest {
    @Mock
    File file;
    @Mock
    JarFile jarFile;
    @Mock
    Enumeration<JarEntry> entries;

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

    @Test
    public void testLoadRules(){
        TSQLRules.loadRules();
    }
    @Test
    public void testLoadRulesFromJar() {
        List<Rule> ruleList = new ArrayList<>();
        Mockito.when(jarFile.entries()).thenReturn(entries);
        TSQLRules.loadCustomRulesFromPluginJar(ruleList, Constants.CUSTOM_RULES_FOLDER, jarFile);
    }

}

