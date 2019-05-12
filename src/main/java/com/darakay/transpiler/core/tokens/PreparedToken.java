package com.darakay.transpiler.core.tokens;

import java.util.Objects;

public class PreparedToken {
    private Token token;
    private BracketType bracketType;

    public PreparedToken(Token token, BracketType bracketType) {
        this.token = token;
        this.bracketType = bracketType;
    }

    public String value() {
        return null;
    }

    public BracketType bracketType() {
        return bracketType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreparedToken)) return false;
        PreparedToken that = (PreparedToken) o;
        return token.equals(that.token) &&
                bracketType == that.bracketType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, bracketType);
    }

    @Override
    public String toString() {
        return "PreparedToken{" +
                "token=" + token +
                ", bracketType=" + bracketType +
                '}';
    }
}
