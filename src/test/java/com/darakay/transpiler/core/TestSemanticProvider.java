package com.darakay.transpiler.core;

import com.darakay.transpiler.core.lang.Semantic;
import com.darakay.transpiler.core.lang.BaseSemantic;
import com.darakay.transpiler.core.semantic.SemanticNode;
import com.darakay.transpiler.core.semantic.SemanticNodeType;
import com.darakay.transpiler.core.tokens.Token;
import com.darakay.transpiler.core.tokens.TokenType;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.function.Function;

public class TestSemanticProvider implements SemanticProvider {
    private HashMap<String, Function<SemanticNode, SemanticNode>[]> treeFuncs = new HashMap<>();
    private HashMap<SemanticNodeType, Function<SemanticNode, Token[]>> convertingFuncs = new HashMap<>();

    private static Function[] expectedTreeFuncs = new Function[]{(node) -> node};
    private static Function<SemanticNode, Token[]> expectedConvertingFunc
            = (node) -> new Token[]{new Token("<h1>", TokenType.TERMINATE)};
    private static Function<SemanticNode, SemanticNode> handler = (node) -> node;

    public TestSemanticProvider(){
        treeFuncs.put("<em>", expectedTreeFuncs);
        convertingFuncs.put(SemanticNodeType.HEADER1, expectedConvertingFunc);
    }

    @Override
    public Semantic getSemantic() {
        try {
            Class clazz = Class.forName("com.darakay.transpiler.core.lang.BaseSemantic");
            BaseSemantic semantic = (BaseSemantic) clazz.newInstance();
            Field field1 = clazz.getDeclaredField("treeFuncs");
            Field field2 = clazz.getDeclaredField("convertingFuncs");
            Field field3 = clazz.getDeclaredField("plainTextTreeFunc");
            field1.setAccessible(true);
            field2.setAccessible(true);
            field3.setAccessible(true);
            field1.set(semantic, treeFuncs);
            field2.set(semantic, convertingFuncs);
            field3.set(semantic, handler);
            return semantic;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Function[] getExpectedTreeFunctions() {
        return expectedTreeFuncs;
    }

    public Function[] getNonTerminateTokenHandler() {
        return new Function[]{handler};
    }

    public SemanticNode getKnownSemanticNode() {
        return new SemanticNode(SemanticNodeType.HEADER1);
    }

    public Function<SemanticNode, Token[]> getExpectedConvertFunction() {
        return expectedConvertingFunc;
    }
}
