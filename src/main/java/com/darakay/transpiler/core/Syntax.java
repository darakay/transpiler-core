package com.darakay.transpiler.core;

public interface Syntax {
    boolean arePairedTokens(Token open, Token closed);
    BracketType getBracketType(Token token);
    public Token getClosedTokenByOpen(Token open);
}
