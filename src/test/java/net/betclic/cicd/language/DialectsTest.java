package net.betclic.cicd.language;

import net.betclic.cicd.antlr.AntlrContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class DialectsTest {

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testParseOk() {
        TSQLDialect tsqlDialect = new TSQLDialect();
        AntlrContext antlrContext= tsqlDialect.parse("test:");
        AntlrContext context = tsqlDialect.addRulesToContext(antlrContext);
        Assert.assertEquals("test:<EOF>", context.getTree().getText());
    }
}
