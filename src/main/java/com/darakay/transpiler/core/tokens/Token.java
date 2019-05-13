package com.darakay.transpiler.core.tokens;

import com.darakay.transpiler.core.exceptions.NotFoundJSONField;
import org.json.simple.JSONObject;

import java.util.Objects;

public class Token {
    private static final String VALUE = "value";
    private static final String TERM = "term";

    private String value;
    private TokenType tokenType;

    public Token(String value, TokenType tokenType) {
        this.value = value;
        this.tokenType = tokenType;
    }

    public Token(JSONObject json) throws NotFoundJSONField {
        this.value = (String)getFieldFrom(VALUE, json);
        this.tokenType =
                ((Boolean)getFieldFrom(TERM, json)) ? TokenType.TERMINATE : TokenType.NON_TERMINATE;
    }

    private Object getFieldFrom(String fieldName, JSONObject json) throws NotFoundJSONField {
        if(json.containsKey(fieldName))
            return json.get(fieldName);
        throw new NotFoundJSONField(fieldName);
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

    public PreparedToken toPreparedToken(BracketType bracketType) {
        return new PreparedToken(this, bracketType);
    }
}
