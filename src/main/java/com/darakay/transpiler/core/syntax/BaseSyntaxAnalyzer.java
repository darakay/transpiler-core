package com.darakay.transpiler.core.syntax;

import com.darakay.transpiler.core.lang.Lang;
import com.darakay.transpiler.core.tokens.BracketType;
import com.darakay.transpiler.core.tokens.PreparedToken;
import com.darakay.transpiler.core.tokens.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BaseSyntaxAnalyzer implements SyntaxAnalyzer {
    private final Stack<Token> tokenStack = new Stack<>();
    private final Lang lang;
    private BaseSyntaxAnalyzerState state = new CorrectSequenceBaseSyntaxAnalyzerState();
    private Token currentToken;
    private boolean isLastToken;
    private List<PreparedToken> correctedToken = new ArrayList<>();

    public BaseSyntaxAnalyzer(Lang lang){
        this.lang = lang;
    }

    @Override
    public PreparedToken getPreparedToken(Token token, boolean isLastToken) {
        this.currentToken = token;
        this.isLastToken = isLastToken;
        handleToken(token);
        return state.getApprovedToken(this);
    }

    @Override
    public List<PreparedToken> getCorrectedTokens() {
        return correctedToken;
    }


    private void handleToken(Token currentToken){
        BracketType bracketType = lang.syntax().getBracketType(currentToken);
        switch (bracketType){
            case NO:
                state.handleNonTerminateToken(this);
                break;
            case CLOSED:
                state.handleClosingBracketToken(this);
                break;
            case OPEN:
                state.handleOpenBracketToken(this);
                break;
        }
    }

    Token currentToken() {
        return currentToken;
    }

    void setState(BaseSyntaxAnalyzerState state){
        this.state = state;
    }

    Stack<Token> stack(){
        return tokenStack;
    }

    boolean arePairedTokens(Token open, Token closed){
        return lang.syntax().arePairedTokens(open, closed);
    }

    boolean isLastToken(){
        return isLastToken;
    }

    Token getClosingBracketByOpen(Token open) {
        return lang.syntax().getClosedTokenByOpen(open);
    }

    BracketType getBracketType(Token token){
        return lang.syntax().getBracketType(token);
    }

    void addCorrectedToken(PreparedToken token){
        correctedToken.add(token);
    }
}
