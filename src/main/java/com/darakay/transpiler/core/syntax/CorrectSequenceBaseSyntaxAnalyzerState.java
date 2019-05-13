package com.darakay.transpiler.core.syntax;

import com.darakay.transpiler.core.tokens.PreparedToken;
import com.darakay.transpiler.core.tokens.Token;

class CorrectSequenceBaseSyntaxAnalyzerState implements BaseSyntaxAnalyzerState {
    private Token approvedToken;

    CorrectSequenceBaseSyntaxAnalyzerState(){}

    CorrectSequenceBaseSyntaxAnalyzerState(Token approvedToken){
        this.approvedToken = approvedToken;
    }


    @Override
    public void handleOpenBracketToken(BaseSyntaxAnalyzer syntaxAnalyzer) {
        Token currentToken = syntaxAnalyzer.currentToken();
        syntaxAnalyzer.stack().push(currentToken);
        syntaxAnalyzer.setState(new OpenBracketBaseSyntaxAnalyzerState());
    }
    @Override
    public void handleClosingBracketToken(BaseSyntaxAnalyzer syntaxAnalyzer) {
        syntaxAnalyzer.setState(new InvalidSequenceBaseSyntaxAnalyzerState());
    }

    @Override
    public void handleNonTerminateToken(BaseSyntaxAnalyzer syntaxAnalyzer) {
        syntaxAnalyzer.setState(new CorrectSequenceBaseSyntaxAnalyzerState());
    }

    @Override
    public PreparedToken getApprovedToken(BaseSyntaxAnalyzer syntaxAnalyzer) {
        if(approvedToken != null)
            return approvedToken.toPreparedToken(syntaxAnalyzer.getBracketType(approvedToken));

        return syntaxAnalyzer.currentToken()
                .toPreparedToken(syntaxAnalyzer.getBracketType(syntaxAnalyzer.currentToken()));
    }
}
