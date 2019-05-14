package com.darakay.transpiler.core;

import com.darakay.transpiler.core.lang.Lang;
import com.darakay.transpiler.core.semantic.BaseTranspiler;
import com.darakay.transpiler.core.semantic.Transpiler;
import com.darakay.transpiler.core.syntax.BaseSyntaxAnalyzer;
import com.darakay.transpiler.core.syntax.SyntaxAnalyzer;
import com.darakay.transpiler.core.tokens.PreparedToken;
import com.darakay.transpiler.core.tokens.Token;

import java.util.ArrayList;
import java.util.List;

class BaseProcessor{
    static List<Token> convertTo(Lang sourceLang, Lang targetLang,  Token[] source){
        List<Token> result = new ArrayList<>();
        SyntaxAnalyzer syntaxAnalyzer = new BaseSyntaxAnalyzer(sourceLang);
        Transpiler transpiler = new BaseTranspiler(sourceLang, targetLang);
        for(int i = 0; i < source.length; i++){
            PreparedToken preparedToken =
                    syntaxAnalyzer.getPreparedToken(source[i], i==source.length-1);
            result.addAll(transpiler.translate(preparedToken));
        }
        for(PreparedToken preparedToken : syntaxAnalyzer.getCorrectedTokens())
            result.addAll(transpiler.translate(preparedToken));
        return result;
    }
}
