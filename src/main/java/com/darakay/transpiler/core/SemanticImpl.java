package com.darakay.transpiler.core;

import java.util.HashMap;
import java.util.function.Function;

public class SemanticImpl implements Semantic {
    private HashMap<String, Function<SemanticNode, SemanticNode>[]> treeFuncs;
    private HashMap<SemanticNodeType, Function<SemanticNode, Token[]>> convertingFuncs;

    private Function<SemanticNode, SemanticNode> plainTextTreeFunc;

    @Override
    public Function<SemanticNode, SemanticNode>[] getTreeFunctionsByPreparedToken(PreparedToken preparedToken) {
        String value = preparedToken.value();
        if(treeFuncs.containsKey(value))
            return treeFuncs.get(value);
        return new Function[]{ plainTextTreeFunc };
    }

    @Override
    public Function<SemanticNode, Token[]> getFunctionsForConvertingSemanticNodeToTokens(SemanticNode node)
            throws UnknownSemanticNodeType {
        if(!convertingFuncs.containsKey(node.type()))
            throw new UnknownSemanticNodeType(node.type().name());
        return convertingFuncs.get(node.type());
    }
}
