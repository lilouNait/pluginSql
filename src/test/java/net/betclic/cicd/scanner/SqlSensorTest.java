package net.betclic.cicd.scanner;

import net.betclic.cicd.settings.Constants;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.internal.SensorContextTester;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SqlSensorTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Mock
    SensorDescriptor sensorDescriptor;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDescribe() {
        SqlSensor mySqlSensor = new SqlSensor();
        mySqlSensor.describe(sensorDescriptor);
        verify(sensorDescriptor, times(1)).onlyOnLanguage(Constants.LANGUAGE_KEY);
    }

    @Test
    public void testExecute() {
        SqlSensor mySqlSensor1 = new SqlSensor();
        SensorContextTester sensorContextTester = SensorContextTester.create(folder.getRoot());
        mySqlSensor1.execute(sensorContextTester);
    }
}
