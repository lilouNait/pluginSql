package net.betclic.cicd.language;

import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.misc.Interval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ChangingCharStreamTest {

    @Mock
    ChangingCharStream changingCharStream;
    @Mock
    Interval interval;
    @Mock
    CodePointCharStream code;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testgetStream() {
        ChangingCharStream changingCharStream1 = new ChangingCharStream(code, false);
        changingCharStream1.getStream();
        changingCharStream.getStream();
        verify(changingCharStream, times(1)).getStream();
    }

    @Test
    public void testIsUpper() {
        ChangingCharStream chargingCharStream2 = new ChangingCharStream(code, true);
        chargingCharStream2.isUpper();
        Assert.assertEquals(true, chargingCharStream2.isUpper());
        changingCharStream.isUpper();
        verify(changingCharStream, times(1)).isUpper();
    }

    @Test
    public void getText() {
        ChangingCharStream changingCharStream3 = new ChangingCharStream(code, true);
        changingCharStream3.getText(interval);
        Assert.assertEquals(null, changingCharStream3.getText(interval));
        changingCharStream.getText(interval);
        verify(changingCharStream, times(1)).getText(interval);
    }

    @Test
    public void testConsume() {
        ChangingCharStream changingCharStream4 = new ChangingCharStream(code, true);
        changingCharStream4.consume();
        changingCharStream.consume();
        verify(changingCharStream, times(1)).consume();
    }

    @Test
    public void testOverriddenMethods() {
        ChangingCharStream changingCharStream5 = new ChangingCharStream(code, true);
        changingCharStream5.LA(5);
        changingCharStream5.mark();
        changingCharStream5.release(3);
        changingCharStream5.index();
        changingCharStream5.seek(4);
        changingCharStream5.size();
        changingCharStream5.getSourceName();

        Assert.assertEquals(changingCharStream5.LA(5), code.LA(5));
        Assert.assertEquals(changingCharStream5.mark(), code.mark());
        Assert.assertEquals(changingCharStream5.index(), code.index());
        Assert.assertEquals(changingCharStream5.size(), code.size());
        Assert.assertEquals(changingCharStream5.getSourceName(), code.getSourceName());

        changingCharStream.LA(5);
        changingCharStream.mark();
        changingCharStream.release(3);
        changingCharStream.index();
        changingCharStream.seek(4);
        changingCharStream.size();
        changingCharStream.getSourceName();
        verify(changingCharStream, times(1)).LA(5);
        verify(changingCharStream, times(1)).mark();
        verify(changingCharStream, times(1)).release(3);
        verify(changingCharStream, times(1)).index();
        verify(changingCharStream, times(1)).seek(4);
        Assert.assertEquals(changingCharStream.size(), 0);
        verify(changingCharStream, times(1)).getSourceName();
    }
}
