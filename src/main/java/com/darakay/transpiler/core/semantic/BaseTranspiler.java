package com.darakay.transpiler.core.semantic;

import com.darakay.transpiler.core.exceptions.UnknownSemanticNodeType;
import com.darakay.transpiler.core.lang.Lang;
import com.darakay.transpiler.core.tokens.PreparedToken;
import com.darakay.transpiler.core.tokens.Token;
import com.darakay.transpiler.core.tokens.TokenType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class BaseTranspiler implements Transpiler {
    private final Lang source;
    private final Lang target;
    private SemanticTree semanticTree;

    public BaseTranspiler(Lang sourceLang, Lang targetLang){
        this.source = sourceLang;
        this.target = targetLang;
        semanticTree = new SemanticTree();
    }

    @Override
    public List<Token> translate(PreparedToken token) {
        Function[] functions = source.semantic().getTreeFunctionsByPreparedToken(token);
        for(Function<SemanticNode, SemanticNode> f : functions)
            semanticTree.applyFunction(f);
        if(semanticTree.currentNode().isTerminate())
            return translate(semanticTree.currentNode());
        return new ArrayList<>();
    }

    private List<Token> translate(SemanticNode currentNode) {
        try {
            Function<SemanticNode, List<Token>> func =
                    target.semantic().getFunctionsForConvertingSemanticNodeToTokens(currentNode);
            return func.apply(currentNode);
        } catch (UnknownSemanticNodeType unknownSemanticNodeType) {
            return Arrays.asList(new Token(currentNode.getInnerText(), TokenType.NON_TERMINATE));
        }
    }
}
