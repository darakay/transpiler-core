package com.darakay.transpiler.core;

import com.darakay.transpiler.core.exceptions.UnknownSemanticNodeType;
import com.darakay.transpiler.core.lang.Semantic;
import com.darakay.transpiler.core.lang.SemanticNode;
import com.darakay.transpiler.core.lang.SemanticNodeType;
import com.darakay.transpiler.core.tokens.BracketType;
import com.darakay.transpiler.core.tokens.PreparedToken;
import com.darakay.transpiler.core.tokens.Token;
import com.darakay.transpiler.core.tokens.TokenType;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;


public class SemanticImplTest {
    private static TestSemanticProvider provider = new TestSemanticProvider();
    private static Semantic semantic;

    @BeforeClass
    public static void setUp() throws Exception {
        semantic = provider.getSemantic();
    }

    @Ignore
    @Test
    public void getTreeFunctionsByPreparedToken_ShouldReturnCorrectFunctions_ThenTokenIsTerminate() {
        PreparedToken pt = new PreparedToken(new Token("<em>", TokenType.TERMINATE), BracketType.OPEN);
        Function[] expected = provider.getExpectedTreeFunctions();
        assertThat(semantic.getTreeFunctionsByPreparedToken(pt)).containsSequence(expected);
    }

    @Test
    public void getTreeFunctionsByPreparedToken_ShouldReturnNonTerminateTokenHandler_ThenTokenIsNonTerminate() {
        PreparedToken pt = new PreparedToken(new Token("<em>", TokenType.NON_TERMINATE), BracketType.OPEN);
        Function[] expected = provider.getNonTerminateTokenHandler();
        assertThat(semantic.getTreeFunctionsByPreparedToken(pt)).containsSequence(expected);
    }


    @Test
    public void getFunctionForConvertingSemanticNodeToTokens_ShouldReturnCorrectFunctions()
            throws UnknownSemanticNodeType {
        SemanticNode sn = provider.getKnownSemanticNode();
        Function expected = provider.getExpectedConvertFunction();
        assertThat(semantic.getFunctionsForConvertingSemanticNodeToTokens(sn)).isEqualTo(expected);
    }

    @Test(expected = UnknownSemanticNodeType.class)
    public void getFunctionForConvertingSemanticNodeToTokens_ShouldThrowUnknownSemanticNodeType_ThenNodeIsUnknown()
            throws UnknownSemanticNodeType {
        SemanticNode sn = new SemanticNode(SemanticNodeType.HEADER2);
        Function expected = provider.getExpectedConvertFunction();
        semantic.getFunctionsForConvertingSemanticNodeToTokens(sn);
    }
}