package com.darakay.transpiler.core;

import com.darakay.transpiler.core.exceptions.NotFoundJSONField;
import com.darakay.transpiler.core.tokens.BracketType;
import com.darakay.transpiler.core.tokens.PreparedToken;
import com.darakay.transpiler.core.tokens.Token;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    static JSONArray jsonArrayFrom(String fileName) throws IOException, ParseException {
        InputStream in = TestUtils.class.getResourceAsStream(fileName);
        JSONParser parser = new JSONParser();
        return (JSONArray) parser.parse(new InputStreamReader(in));
    }

    public static Token[] getTokensFrom(String fileName) throws IOException, ParseException, NotFoundJSONField {
        ArrayList<Token> tokens = new ArrayList<>();
        for(Object o : jsonArrayFrom(fileName)){
            JSONObject json = (JSONObject)o;
            tokens.add(new Token(json));
        }
        return tokens.toArray(new Token[tokens.size()]);
    }

    public static PreparedToken[] getPreparedTokensFrom(String fileName)
            throws IOException, ParseException, NotFoundJSONField {
        ArrayList<PreparedToken> tokens = new ArrayList<>();
        for(Object o : jsonArrayFrom(fileName)){
            JSONObject json = (JSONObject)o;
            Token innerToken = new Token(json);
            tokens.add(new PreparedToken(innerToken, getBracketType(json)));
        }
        return tokens.toArray(new PreparedToken[tokens.size()]);
    }

    private static BracketType getBracketType(JSONObject jsonObject){
        String bt = (String) jsonObject.get("bt");
        if(bt.equals(BracketType.OPEN.name()))
            return BracketType.OPEN;
        if(bt.equals(BracketType.CLOSED.name()))
            return BracketType.CLOSED;
        if(bt.equals(BracketType.NO.name()))
            return BracketType.NO;
        return null;
    }

}

