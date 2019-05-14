package com.darakay.transpiler.core.lang;

import com.darakay.transpiler.core.semantic.SemanticNode;
import com.darakay.transpiler.core.semantic.SemanticNodeType;
import com.darakay.transpiler.core.tokens.PreparedToken;
import com.darakay.transpiler.core.tokens.Token;
import com.darakay.transpiler.core.exceptions.UnknownSemanticNodeType;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class BaseSemantic implements Semantic {
    private HashMap<String, Function<SemanticNode, SemanticNode>[]> treeFuncs;
    private HashMap<SemanticNodeType, Function<SemanticNode, List<Token>>> convertingFuncs;

    private Function<SemanticNode, SemanticNode> plainTextTreeFunc;

    @Override
    public Function<SemanticNode, SemanticNode>[] getTreeFunctionsByPreparedToken(PreparedToken preparedToken) {
        String value = preparedToken.value();
        if(treeFuncs.containsKey(value))
            return treeFuncs.get(value);
        return new Function[]{ plainTextTreeFunc };
    }

    @Override
    public Function<SemanticNode, List<Token>> getFunctionsForConvertingSemanticNodeToTokens(SemanticNode node)
            throws UnknownSemanticNodeType {
        if(!convertingFuncs.containsKey(node.type()))
            throw new UnknownSemanticNodeType(node.type().name());
        return convertingFuncs.get(node.type());
    }
}
