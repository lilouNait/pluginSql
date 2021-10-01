package net.betclic.cicd.scanner;

import net.betclic.cicd.measures.SqlIssue;
import net.betclic.cicd.measures.SqlIssuesList;
import net.betclic.cicd.settings.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.config.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * BaseSensor Tester.
 *
 * @author <Authors Nait Amara>
 * @version 1.0
 * @since <pre>Apr 14, 2021</pre>
 */
public class IssueUtilTest {
    @Mock
    SensorContext sensorContext;
    @Mock
    FileSystem fileSystem;
    @Mock
    FilePredicates filePredicates;
    @Mock
    SqlIssuesList issuesList;
    @Mock
    InputFile file;
    @Mock
    Map<String, Set<SqlIssue>> sqlIssueMap;
    @Mock
    Configuration configuration;

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method: find(SensorContext context, String path)
     */
    @Test
    public void testFind() throws IOException {
        String path = "/C:/sonarqube_sqlplugin/pathTest";
        Mockito.when(sensorContext.fileSystem()).thenReturn(fileSystem);
        Mockito.when(sensorContext.fileSystem().predicates()).thenReturn(filePredicates);
        IssueUtil.find(sensorContext, path);

        List<InputFile> inputFiles = new ArrayList<>();
        Assert.assertEquals(inputFiles, IssueUtil.find(sensorContext, path));
    }

    /**
     * Method: addIssues(SensorContext context, final SqlIssuesList issues, final InputFile file)
     */
    @Test
    public void testAddIssues() throws IOException {
        Mockito.when(sensorContext.config()).thenReturn(configuration);
        String[] t = new String[1];
        t[0] = "test";
        Mockito.when(sensorContext.config().getStringArray(Constants.PLUGIN_SQL_RULES_SKIP)).thenReturn(t);
        Mockito.when(issuesList.getIssues()).thenReturn(sqlIssueMap);
        IssueUtil.addIssues(sensorContext, issuesList, file);
    }
}
