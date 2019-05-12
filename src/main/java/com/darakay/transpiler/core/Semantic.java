package com.darakay.transpiler.core;

import java.util.function.Function;

public interface Semantic {
    Function[] getTreeFunctionsByPreparedToken(PreparedToken preparedToken);
    Function<SemanticNode, Token[]> getFunctionsForConvertingSemanticNodeToTokens(SemanticNode node)
            throws UnknownSemanticNodeType;
}
