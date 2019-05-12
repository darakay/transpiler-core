package com.darakay.transpiler.core.tokens;

import java.util.Objects;

public class Token {
    private String value;
    private TokenType tokenType;

    public Token(String value, TokenType tokenType) {
        this.value = value;
        this.tokenType = tokenType;
    }

    public String value() {
        return value;
    }

    public TokenType tokenType() {
        return tokenType;
    }

    @Override
    public String toString() {
        return "Token{" +
                "value='" + value + '\'' +
                ", tokenType=" + tokenType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token = (Token) o;
        return value.equals(token.value) &&
                tokenType == token.tokenType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, tokenType);
    }
}
