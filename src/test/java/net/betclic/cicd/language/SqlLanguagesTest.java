package net.betclic.cicd.language;

import net.betclic.cicd.settings.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sonar.api.config.Configuration;

import static org.mockito.Mockito.when;

public class SqlLanguagesTest {

    @Mock
    Configuration configuration;

    @Mock
    Constants constants;

    @InjectMocks
    SqlLanguages sqlLanguages;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSuffixesNotNullOk() {
        String[] resultToImprove = {"sonar.sql.file.suffixes"};
        when(configuration.getStringArray(constants.PLUGIN_SUFFIXES)).thenReturn(resultToImprove);
        Assert.assertArrayEquals(resultToImprove, sqlLanguages.getFileSuffixes());
    }

}

