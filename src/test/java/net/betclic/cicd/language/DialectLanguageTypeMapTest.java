package net.betclic.cicd.language;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DialectLanguageTypeMapTest {

    @Mock
    DialectLanguageTypesMap dialectLanguageTypesMapMock;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCommentTokenOk() {
        DialectLanguageTypesMap dialectLanguageTypesMap = new DialectLanguageTypesMap();
        dialectLanguageTypesMap.addCommentToken(2);
        Set<Integer> set = new HashSet<>();
        set.add(2);
        Assert.assertEquals(set, dialectLanguageTypesMap.getCommentTokens());
    }

    @Test
    public void testAddStringTokenOk() {
        DialectLanguageTypesMap dialectLanguageTypesMap = new DialectLanguageTypesMap();
        dialectLanguageTypesMap.addStringToken(2);
        Set<Integer> set = new HashSet<>();
        set.add(2);
        Assert.assertEquals(set, dialectLanguageTypesMap.getStringTokens());
    }

    @Test
    public void testGetAcyclomaticComplexityOk() {
        Assert.assertNotNull(dialectLanguageTypesMapMock.getCyclomatiComplexity());
        verify(dialectLanguageTypesMapMock, times(1)).getCyclomatiComplexity();
    }

    @Test
    public void testGetCognitiveComplexityOk() {
        dialectLanguageTypesMapMock.addCognitiveComplexityType(String.class);
        Assert.assertNotNull(dialectLanguageTypesMapMock.getCognitiveComplexity());
        verify(dialectLanguageTypesMapMock, times(1)).getCognitiveComplexity();
    }
}

