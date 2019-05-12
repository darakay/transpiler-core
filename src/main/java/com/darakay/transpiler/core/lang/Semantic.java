package com.darakay.transpiler.core.lang;

import com.darakay.transpiler.core.tokens.PreparedToken;
import com.darakay.transpiler.core.tokens.Token;
import com.darakay.transpiler.core.exceptions.UnknownSemanticNodeType;

import java.util.function.Function;

public interface Semantic {
    Function[] getTreeFunctionsByPreparedToken(PreparedToken preparedToken);
    Function<SemanticNode, Token[]> getFunctionsForConvertingSemanticNodeToTokens(SemanticNode node)
            throws UnknownSemanticNodeType;
}
