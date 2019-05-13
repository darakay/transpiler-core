package com.darakay.transpiler.core.syntax;

import com.darakay.transpiler.core.tokens.BracketType;
import com.darakay.transpiler.core.tokens.PreparedToken;
import com.darakay.transpiler.core.tokens.Token;
import com.darakay.transpiler.core.tokens.TokenType;

class InvalidSequenceBaseSyntaxAnalyzerState implements BaseSyntaxAnalyzerState {
    @Override
    public void handleOpenBracketToken(BaseSyntaxAnalyzer syntaxAnalyzer) {
        syntaxAnalyzer.setState(new InvalidSequenceBaseSyntaxAnalyzerState());
    }

    @Override
    public void handleClosingBracketToken(BaseSyntaxAnalyzer syntaxAnalyzer) {
        syntaxAnalyzer.setState(new InvalidSequenceBaseSyntaxAnalyzerState());
    }

    @Override
    public void handleNonTerminateToken(BaseSyntaxAnalyzer syntaxAnalyzer) {
        syntaxAnalyzer.setState(new InvalidSequenceBaseSyntaxAnalyzerState());
    }

    @Override
    public PreparedToken getApprovedToken(BaseSyntaxAnalyzer syntaxAnalyzer) {
        Token currentToken = syntaxAnalyzer.currentToken();
        Token correctedToken = new Token(currentToken.value(), TokenType.NON_TERMINATE);
        return correctedToken.toPreparedToken(BracketType.NO);
    }
}
