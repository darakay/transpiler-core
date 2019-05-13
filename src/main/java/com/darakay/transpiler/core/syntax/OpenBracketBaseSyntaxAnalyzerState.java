package com.darakay.transpiler.core.syntax;

import com.darakay.transpiler.core.tokens.BracketType;
import com.darakay.transpiler.core.tokens.PreparedToken;
import com.darakay.transpiler.core.tokens.Token;

class OpenBracketBaseSyntaxAnalyzerState implements BaseSyntaxAnalyzerState {
    @Override
    public void handleOpenBracketToken(BaseSyntaxAnalyzer syntaxAnalyzer) {
        Token currentToken = syntaxAnalyzer.currentToken();
        syntaxAnalyzer.stack().push(currentToken);
        syntaxAnalyzer.setState(new OpenBracketBaseSyntaxAnalyzerState());
    }

    @Override
    public void handleClosingBracketToken(BaseSyntaxAnalyzer syntaxAnalyzer) {
        Token closingToken = syntaxAnalyzer.currentToken();
        Token lastOpenToken = syntaxAnalyzer.stack().peek();

        if(syntaxAnalyzer.arePairedTokens(lastOpenToken, closingToken))
            popRelevantOpenBracket(syntaxAnalyzer);
        else
            handleInvalidClosingBracket(lastOpenToken, syntaxAnalyzer);
    }

    private void popRelevantOpenBracket(BaseSyntaxAnalyzer syntaxAnalyzer){
        syntaxAnalyzer.stack().pop();
        if(syntaxAnalyzer.stack().empty())
            syntaxAnalyzer.setState(new CorrectSequenceBaseSyntaxAnalyzerState());
        else
            syntaxAnalyzer.setState(new OpenBracketBaseSyntaxAnalyzerState());
    }

    private void handleInvalidClosingBracket(Token open, BaseSyntaxAnalyzer syntaxAnalyzer){
        Token approvedToken = syntaxAnalyzer.getClosingBracketByOpen(open);
        syntaxAnalyzer.setState(new CorrectSequenceBaseSyntaxAnalyzerState(approvedToken));
    }

    @Override
    public void handleNonTerminateToken(BaseSyntaxAnalyzer syntaxAnalyzer) {
        syntaxAnalyzer.setState(new OpenBracketBaseSyntaxAnalyzerState());
    }

    @Override
    public PreparedToken getApprovedToken(BaseSyntaxAnalyzer syntaxAnalyzer) {
        BracketType bt = syntaxAnalyzer.getBracketType(syntaxAnalyzer.currentToken());
        if(syntaxAnalyzer.isLastToken())
            addRelevantClosingToken(syntaxAnalyzer);
        return syntaxAnalyzer.currentToken().toPreparedToken(bt);
    }

    private void addRelevantClosingToken(BaseSyntaxAnalyzer syntaxAnalyzer){
        Token opn =  syntaxAnalyzer.stack().peek();
        Token closed = syntaxAnalyzer.getClosingBracketByOpen(opn);
        syntaxAnalyzer.addCorrectedToken(closed.toPreparedToken(BracketType.CLOSED));
    }
}
