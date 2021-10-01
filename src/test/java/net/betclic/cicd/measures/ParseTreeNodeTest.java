package net.betclic.cicd.measures;

import net.betclic.cicd.scanner.IParsedNode;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * ParseTreeNode Tester.
 *
 * @author <Authors Nait Amara>
 * @version 1.0
 * @since <pre>Apr 6, 2021</pre>
 */
public class ParseTreeNodeTest {

    @Mock
    ParseTree tree;
    @Mock
    Token token;

    @Mock
    ParserRuleContext parserRuleContext;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method: getClassName()
     */
    @Test
    public void testGetClassName() {
        ParseTreeNode parseTreeNodeTest = new ParseTreeNode(tree);
        parseTreeNodeTest.getClassName();
        Assert.assertEquals(tree.getClass().getSimpleName(), parseTreeNodeTest.getClassName());
    }

    /**
     * Method: getText()
     */
    @Test
    public void testGetText() {
        ParseTreeNode parseTreeNodeGetText = new ParseTreeNode(tree);
        parseTreeNodeGetText.getText();
        Assert.assertEquals(tree.getText(), parseTreeNodeGetText.getText());
    }

    /**
     * Method: getLine()
     */
    @Test
    public void testGetLine() {
        ParseTreeNode parseTreeNode1 = new ParseTreeNode(tree);
        parseTreeNode1.getLine();
        Assert.assertEquals(-1, parseTreeNode1.getLine());
    }

    /**
     * Method: getLine()
     */
    @Test
    public void testGetLineInstanceOfToken() {
        ParseTreeNode parseTreeNode2 = new ParseTreeNode(tree);
        when(tree.getPayload()).thenReturn(token);
        Assert.assertEquals(token.getLine(), parseTreeNode2.getLine());
    }

    /**
     * Method: getLine()
     */
    @Test
    public void testGetLineInstanceOfTerminalNode() {
        ParseTreeNode parseTreeNode3 = new ParseTreeNode(tree);
        TerminalNode terminalNode = new TerminalNodeImpl(token);
        when(tree.getPayload()).thenReturn(terminalNode);
        Assert.assertEquals(terminalNode.getSymbol().getLine(), parseTreeNode3.getLine());
    }

    /**
     * Method: getLine()
     */
    @Test
    public void testGetLineInstanceOfParserRuleContext() {
        ParseTreeNode parseTreeNode4 = new ParseTreeNode(tree);
        when(tree.getPayload()).thenReturn(parserRuleContext);
        when(parserRuleContext.getStart()).thenReturn(token);
        parseTreeNode4.getLine();
        Assert.assertEquals(parserRuleContext.getStart().getLine(), parseTreeNode4.getLine());
    }

    /**
     * Method: getParents() argument null
     */
    @Test
    public void testGetParentsTreeNull() {
        ParseTreeNode parseTreeNode5 = new ParseTreeNode(null);
        parseTreeNode5.getParents();
        Assert.assertArrayEquals(new IParsedNode[0], parseTreeNode5.getParents());
    }

    /**
     * Method: getParents() argument not null
     */
    @Test
    public void testGetParentsTreeNotNull() {
        ParseTreeNode parseTreeNode6 = new ParseTreeNode(tree);
        parseTreeNode6.getParents();
        Assert.assertArrayEquals(new IParsedNode[0], parseTreeNode6.getParents());
    }

    /**
     * Method: getParent()
     */
    @Test
    public void testGetParent() {
        ParseTreeNode parseTreeNodeGetParentTest = new ParseTreeNode(tree);
        parseTreeNodeGetParentTest.getParents();
        Assert.assertNotNull(parseTreeNodeGetParentTest.getParents());
    }

    /**
     * Method: getControlFlowParent()
     */
    @Test
    public void testGetControlFlowParent() {
        ParseTreeNode parseTreeNodeControl = new ParseTreeNode(tree);
        parseTreeNodeControl.getControlFlowParent();
        Assert.assertNull(parseTreeNodeControl.getControlFlowParent());
    }

    /**
     * Method: getUses()
     */
    @Test
    public void testGetUses() {
        ParseTreeNode parseTreeNodeGetUses = new ParseTreeNode(tree);
        parseTreeNodeGetUses.getUses();
        Assert.assertNotNull(parseTreeNodeGetUses.getUses());
    }

    /**
     * Method: getChildren()
     */
    @Test
    public void testGetChildren() {
        ParseTreeNode parseTreeNode7 = new ParseTreeNode(tree);
        parseTreeNode7.getChildren();
        Assert.assertArrayEquals(new IParsedNode[0], parseTreeNode7.getChildren());
    }

    /**
     * Method: getDistance()
     */
    @Test
    public void testGetDistance() {
        ParseTreeNode parseTreeNodeTestDistance = new ParseTreeNode(tree, 2, 3, 2);
        Assert.assertEquals(2, parseTreeNodeTestDistance.getDistance());
    }

    /**
     * Method: getIndex()
     */
    @Test
    public void testGetIndex() {
        ParseTreeNode parseTreeNodeTestIndex = new ParseTreeNode(tree, 2, 3, 2);
        parseTreeNodeTestIndex.getIndex();
        Assert.assertEquals(3, parseTreeNodeTestIndex.getIndex());
    }

    /**
     * Method: getIndex2()
     */
    @Test
    public void testGetIndex2() {
        ParseTreeNode parseTreeNodeTestIndex2 = new ParseTreeNode(tree, 2, 3, 2);
        parseTreeNodeTestIndex2.getIndex2();
        Assert.assertEquals(2, parseTreeNodeTestIndex2.getIndex2());
    }

    /**
     * Method: getGlobalIndex()
     */
    @Test
    public void testGetGlobalIndex() {
        ParseTreeNode parsedNodeTestGlobalIndex = new ParseTreeNode(tree);
        parsedNodeTestGlobalIndex.getGlobalIndex();
        Assert.assertNotNull(parsedNodeTestGlobalIndex.getGlobalIndex());
    }

    /**
     * Method: hashCode()
     */
    @Test
    public void testHashCode() {
        ParseTreeNode parseTreeNodeTestHash = new ParseTreeNode(null);
        parseTreeNodeTestHash.hashCode();
        Assert.assertEquals(31, parseTreeNodeTestHash.hashCode());
    }

    /**
     * Method: equals(Object obj)
     */
    @Test
    public void testEqualsTrue() {
        ParseTreeNode parseTreeNodeTest1 = new ParseTreeNode(tree);
        ParseTreeNode parseTreeNodeTest2 = new ParseTreeNode(tree);
        boolean result = parseTreeNodeTest1.equals(parseTreeNodeTest2);
        Assert.assertTrue(result);
    }

    @Test
    public void testEqualsFalse() {
        ParseTreeNode parseTreeNodeTest1 = new ParseTreeNode(tree);
        parseTreeNodeTest1.equals(null);
        Assert.assertFalse(parseTreeNodeTest1.equals(null));
    }

    @Test
    public void testEqualsFalseTreeDifferent() {
        ParseTreeNode parseTreeNodeToTest = new ParseTreeNode(tree);
        ParseTreeNode parseTreeNodeToTest1 = new ParseTreeNode(null);
        parseTreeNodeToTest.equals(parseTreeNodeToTest1);
        Assert.assertFalse(parseTreeNodeToTest.equals(parseTreeNodeToTest1));
    }

    @Test
    public void testGetSiblings() {
        ParseTreeNode parseTreeNodeTestSiblings = new ParseTreeNode(tree);
        parseTreeNodeTestSiblings.getSiblings();
    }
}
