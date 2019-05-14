package com.darakay.transpiler.core.semantic;

import java.util.function.Function;

class SemanticTree {
    private final SemanticNode root;
    private SemanticNode currentNode;

    SemanticTree(){
        this.root =  new SemanticNode(SemanticNodeType.ROOT);
        currentNode = root;
    }

    void applyFunction(Function<SemanticNode, SemanticNode> func){
        currentNode = func.apply(currentNode);
    }

    SemanticNode currentNode() {
        return currentNode;
    }

    SemanticNode root() {
        return root;
    }
}
