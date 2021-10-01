package net.betclic.cicd.language;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.misc.Interval;

public class ChangingCharStream implements CharStream {

    private final CharStream stream;
    private final boolean upper;


    public CharStream getStream() {
        return stream;
    }

    public boolean isUpper() {
        return upper;
    }

    public ChangingCharStream(CodePointCharStream stream, final boolean upper) {
        this.upper = upper;
        this.stream = stream;
    }

    @Override
    public String getText(Interval interval) {
        return stream.getText(interval);
    }

    @Override
    public void consume() {
        stream.consume();
    }

    @Override
    public int LA(int i) {
        return stream.LA(i);
    }

    @Override
    public int mark() {
        return stream.mark();
    }

    @Override
    public void release(int i) {
        stream.release(i);
    }

    @Override
    public int index() {
        return stream.index();
    }

    @Override
    public void seek(int i) {
         stream.seek(i);
    }

    @Override
    public int size() {
        return stream.size();
    }

    @Override
    public String getSourceName() {
        return stream.getSourceName();
    }
}
