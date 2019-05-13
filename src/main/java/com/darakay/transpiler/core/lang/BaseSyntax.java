package com.darakay.transpiler.core.lang;

import com.darakay.transpiler.core.tokens.BracketType;
import com.darakay.transpiler.core.tokens.Token;
import com.darakay.transpiler.core.tokens.TokenType;

import java.util.HashMap;
import java.util.HashSet;

public class BaseSyntax implements Syntax {
    private HashMap<String, String> paired;
    private HashSet<String> unpaired;

    public BaseSyntax(){}

    @Override
    public boolean arePairedTokens(Token open, Token closed){
        if(open.tokenType() == TokenType.NON_TERMINATE || closed.tokenType() == TokenType.NON_TERMINATE)
            return false;
        String opnValue = open.value();
        String closedValue = closed.value();
        if(paired.containsKey(opnValue))
            return closedValue.equals(paired.get(opnValue));
        return false;
    }

    @Override
    public BracketType getBracketType(Token token){
        if(token.tokenType() == TokenType.NON_TERMINATE)
            return BracketType.NO;
        String tokenValue = token.value();
        if(unpaired.contains(tokenValue))
            return BracketType.NO;
        if(paired.containsKey(tokenValue))
            return BracketType.OPEN;
        return BracketType.CLOSED;
    }


    @Override
    public Token getClosedTokenByOpen(Token open){
        if(open.tokenType() == TokenType.NON_TERMINATE)
            throw new IllegalArgumentException("Token {" + open + "} isn't terminate");
        if(unpaired.contains(open.value()))
            throw new IllegalArgumentException("Token {" + open + "} isn't paired token");
        if(paired.containsKey(open.value()))
            return new Token(paired.get(open.value()), TokenType.TERMINATE);
        throw new IllegalArgumentException("Unknown token {" + open + "}");
    }
}
