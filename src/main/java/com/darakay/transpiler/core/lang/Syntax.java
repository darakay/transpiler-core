package com.darakay.transpiler.core.lang;

import com.darakay.transpiler.core.tokens.BracketType;
import com.darakay.transpiler.core.tokens.Token;

public interface Syntax {
    boolean arePairedTokens(Token open, Token closed);
    BracketType getBracketType(Token token);
    Token getClosedTokenByOpen(Token open);
}
