package com.darakay.transpiler.core;

import java.util.Objects;

class SemanticNode {
    private SemanticNodeType type;
    SemanticNode(SemanticNodeType type) {
        this.type = type;
    }

    SemanticNodeType type() {
        return type;
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
