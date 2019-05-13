package com.darakay.transpiler.core.syntax;

import com.darakay.transpiler.core.tokens.PreparedToken;
import com.darakay.transpiler.core.tokens.Token;

import java.util.List;

public interface SyntaxAnalyzer {
    PreparedToken getPreparedToken(Token token, boolean isLastToken);
    List<PreparedToken> getCorrectedTokens();
}
