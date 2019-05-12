package com.darakay.transpiler.core;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Lang {
    private static ConcurrentHashMap<String, Lang> langs = new ConcurrentHashMap<>();
    private static final String MD = "markdown";

    private Syntax syntax;
    private Semantic semantic;
    private String name;

    private Lang(Syntax syntax, Semantic semantic, String name) {
        this.syntax = syntax;
        this.semantic = semantic;
        this.name = name;
    }

    public static Lang markdown(){
        if(langs.containsKey(MD))
            return langs.get(MD);
        else {
            BaseSyntaxProvider symp = new BaseSyntaxProvider(MD);
            BaseSemanticProvider semp = new BaseSemanticProvider(MD);
            return createLang(symp, semp, MD);
        }
    }

    public static Lang createLang(SyntaxProvider syntaxProvider, SemanticProvider semanticProvider, String name){
        Lang lang = new Lang(syntaxProvider.getSyntax(), semanticProvider.getSemantic(), name);
        if(!langs.containsKey(name))
            langs.put(name, lang);
        return langs.get(name);
    }

    public static Lang lang(String name){
        if(langs.containsKey(name))
            return langs.get(name);
        throw new IllegalArgumentException("Unknown lang " + name);
    }

    public Syntax syntax(){
        return syntax;
    }

    public Semantic semantic(){
        return semantic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lang)) return false;
        Lang lang = (Lang) o;
        return syntax.equals(lang.syntax) &&
                semantic.equals(lang.semantic) &&
                name.equals(lang.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(syntax, semantic, name);
    }

    @Override
    public String toString() {
        return "Lang{" +
                "syntax=" + syntax +
                ", semantic=" + semantic +
                ", name='" + name + '\'' +
                '}';
    }
}
