package com.darakay.transpiler.core.semantic;

import java.util.Objects;

public class SemanticNode {
    private SemanticNodeType type;
    private boolean isTerminate;
    private StringBuilder innerText;

    public SemanticNode(SemanticNodeType type) {
        this.type = type;
    }

    public SemanticNode(SemanticNodeType type, boolean isTerminate){
        this.isTerminate = isTerminate;
        this.type = type;
    }

    public SemanticNodeType type() {
        return type;
    }

    boolean isTerminate() {
        return isTerminate;
    }

    public void addText(String text){
        this.innerText.append(text);
    }

    public String getInnerText(){
        return innerText.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SemanticNode)) return false;
        SemanticNode that = (SemanticNode) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "SemanticNode{" +
                "type=" + type +
                '}';
    }
}
