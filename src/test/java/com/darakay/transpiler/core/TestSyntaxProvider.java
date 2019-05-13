package com.darakay.transpiler.core;

import com.darakay.transpiler.core.lang.BaseSyntax;
import com.darakay.transpiler.core.lang.Syntax;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;

public class TestSyntaxProvider implements SyntaxProvider {
    private static final String PAIRED_MAP = "paired";
    private static final String UNPAIRED_SET = "unpaired";

    @Override
    public Syntax getSyntax() {
        try {
            Class clazz = Class.forName("com.darakay.transpiler.core.lang.BaseSyntax");
            BaseSyntax syntax = (BaseSyntax) clazz.newInstance();
            Field pairedMapField = clazz.getDeclaredField(PAIRED_MAP);
            Field unpairedSetField = clazz.getDeclaredField(UNPAIRED_SET);
            initialField(pairedMapField, createPairedMap(), syntax);
            initialField(unpairedSetField, createUnpairedSet(), syntax);
            return syntax;
        } catch (InstantiationException | NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
            return null;
        }
    }

    private void initialField(Field field, Object value, Object o){
        field.setAccessible(true);
        try {
            field.set(o, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, String> createPairedMap(){
        HashMap<String, String> map = new HashMap<>();
        map.put("<h1>", "</h1>");
        map.put("<em>", "</em>");
        map.put("<b>", "</b>");
        return map;
    }

    private HashSet<String> createUnpairedSet(){
        HashSet<String> set = new HashSet<>();
        set.add("<a>");
        set.add("<textarea>");
        return set;
    }
}
