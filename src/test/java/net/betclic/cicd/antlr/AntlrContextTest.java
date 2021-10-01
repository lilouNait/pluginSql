package net.betclic.cicd.antlr;

import JaxbClasses.SqlRules;
import net.betclic.cicd.language.DialectLanguageTypesMap;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

public class AntlrContextTest {

    @Mock
    List<SqlRules> rules;

    @Mock
    CommonToken token;
    @Mock
    Lexer lexer;
    @Mock
    Vocabulary vocabulary;
    @Mock
    CommonTokenStream stream;
    @Mock
    ParseTree parseTree;
    @Mock
    List<Token> tokenList;
    @Mock
    DialectLanguageTypesMap type;
    @Mock
    AntlrContext antlrContext;

    @InjectMocks
    AntlrContext context;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLexerOk() {
        context.getLexer();
        Assert.assertEquals(lexer, context.getLexer());
    }

    @Test
    public void testGetTypesOk() {
        context.getTypes();
        Assert.assertEquals(type, context.getTypes());
    }

    @Test
    public void testGetTree() {
        context.getTree();
        Assert.assertEquals(parseTree, context.getTree());
    }

    @Test
    public void testGetStream() {
        context.getStream();
        Assert.assertEquals(stream, context.getStream());
    }

    @Test
    public void testGetRules() {
        context.getRules();
        Assert.assertEquals(rules, context.getRules());
    }

    @Test
    public void testSetters() {
        antlrContext.setTypes(type);
        verify(antlrContext, atLeastOnce()).setTypes(type);
        antlrContext.setLexer(lexer);
        verify(antlrContext, times(1)).setLexer(lexer);
        antlrContext.setTree(parseTree);
        verify(antlrContext, times(1)).setTree(parseTree);
        antlrContext.setStream(stream);
        verify(antlrContext, times(1)).setStream(stream);
        antlrContext.setRules(rules);
        verify(antlrContext, times(1)).setRules(rules);
    }

    @Test
    public void testAntlrTypeIsStringKo() {
        Set<Integer> set = new HashSet<>();
        set.add(2);
        when(context.getTypes().getStringTokens()).thenReturn(set);
        Assert.assertFalse(context.isString(token));
    }

    @Test
    public void testEqualsTrue() {
        AntlrContext antlrContext2 = new AntlrContext();
        antlrContext2.equals(antlrContext2);
        Assert.assertTrue(antlrContext.equals(antlrContext));
    }

    @Test
    public void testEqualsFalse() {
        AntlrContext antlrContextTest1 = new AntlrContext();
        antlrContextTest1.equals(context);
        Assert.assertFalse(antlrContextTest1.equals(context));

        Assert.assertFalse(antlrContextTest1.equals(null));
        Assert.assertFalse(antlrContextTest1.equals(type));
    }

    @Test
    public void testHashCode() {
        AntlrContext antlrContextTestHash = new AntlrContext();
        antlrContextTestHash.hashCode();
        Assert.assertNotNull(antlrContextTestHash.hashCode());
    }

    @Test
    public void testAntlrTypeIsCommentko() {
        Set<Integer> set = new HashSet<>();
        set.add(2);
        Assert.assertFalse(context.isComment(token));
    }

    @Test
    public void testAntlrIsNotKeyWord() {
        AntlrContext antlrContextKeyWordTest = new AntlrContext();
        Assert.assertFalse(antlrContextKeyWordTest.isKeyword(token));
        antlrContextKeyWordTest.setLexer(lexer);
        when(lexer.getVocabulary()).thenReturn(vocabulary);
        antlrContextKeyWordTest.setTree(parseTree);
        Assert.assertFalse(antlrContextKeyWordTest.isKeyword(token));
    }

    @Test
    public void testAntlrIsKeyWord() {
        AntlrContext antlrContextKeyWordTest = new AntlrContext();
        antlrContextKeyWordTest.setLexer(lexer);
        antlrContextKeyWordTest.setTree(parseTree);
        when(lexer.getVocabulary()).thenReturn(vocabulary);
        when(token.getText()).thenReturn("USE");
        when(lexer.getVocabulary().getSymbolicName(token.getType())).thenReturn("use");
        Assert.assertTrue(antlrContextKeyWordTest.isKeyword(token));
    }

    @Test
    public void testGetAllTokens() {
        when(stream.getTokens()).thenReturn(tokenList);
        Assert.assertEquals(tokenList, context.getAllTokens());
    }

}

