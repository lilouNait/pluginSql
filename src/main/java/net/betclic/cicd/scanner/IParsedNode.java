package net.betclic.cicd.scanner;

public interface IParsedNode {

     String getClassName();

     String getText();

     int getLine();

     default IParsedNode[] getParents() {
        return new IParsedNode[0];
    }

     default IParsedNode[] getSiblings() {
        return new IParsedNode[0];
    }
     IParsedNode getControlFlowParent();

     default IParsedNode[] getUses() {
        return new IParsedNode[0];
    }

     default IParsedNode[] getChildren() {
        return new IParsedNode[0];
    }

     default int getDistance() {
        return 0;
    }

     default int getIndex() {
        return 0;
    }

     default int getIndex2() {
        return 0;
    }

     default int getGlobalIndex() {
        return 0;
    }
}
