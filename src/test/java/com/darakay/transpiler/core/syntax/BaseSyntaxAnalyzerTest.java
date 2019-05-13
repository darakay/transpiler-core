package com.darakay.transpiler.core.syntax;

import com.darakay.transpiler.core.TestSemanticProvider;
import com.darakay.transpiler.core.TestSyntaxProvider;
import com.darakay.transpiler.core.TestUtils;
import com.darakay.transpiler.core.exceptions.NotFoundJSONField;
import com.darakay.transpiler.core.lang.Lang;
import com.darakay.transpiler.core.tokens.PreparedToken;
import com.darakay.transpiler.core.tokens.Token;
import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseSyntaxAnalyzerTest {
    private static SyntaxAnalyzer syntaxAnalyzer;

    @BeforeClass
    public static void setUp() {
        Lang.createLang(new TestSyntaxProvider(), new TestSemanticProvider(), "test_lang");
        syntaxAnalyzer = new BaseSyntaxAnalyzer(Lang.lang("test_lang"));
    }

    @Test
    public void syntaxAnalyzer_ShouldReturnAllTokens_ThenTokensAreCorrect()
            throws ParseException, NotFoundJSONField, IOException {

        Token[] testTokens = TestUtils.getTokensFrom("/correct/test");
        List<PreparedToken> actual = getActual(testTokens);
        PreparedToken[] expected = TestUtils.getPreparedTokensFrom("/correct/expected");

        assertThat(actual).containsSequence(expected);

    }

    @Test
    public void syntaxAnalyzer_ShouldReturnCorrectTokenSequence_ThenNoClosedToken()
            throws ParseException, NotFoundJSONField, IOException {

        Token[] testTokens = TestUtils.getTokensFrom("/no-closed/test");
        List<PreparedToken> actual = getActual(testTokens);
        PreparedToken[] expected = TestUtils.getPreparedTokensFrom("/no-closed/expected");
        assertThat(actual).containsSequence(expected);

    }

    @Test
    public void syntaxAnalyzer_ShouldReturnCorrectTokenSequence_ThenNoOpenToken()
            throws ParseException, NotFoundJSONField, IOException {

        Token[] testTokens = TestUtils.getTokensFrom("/no-open/test");
        List<PreparedToken> actual = getActual(testTokens);
        PreparedToken[] expected = TestUtils.getPreparedTokensFrom("/no-open/expected");

        assertThat(actual).containsSequence(expected);

    }

    @Test
    public void syntaxAnalyzer_ShouldReturnCorrectTokenSequence_ThenClosedTokenIsInvalid()
            throws ParseException, NotFoundJSONField, IOException {

        Token[] testTokens = TestUtils.getTokensFrom("/closed-is-invalid/test");
        List<PreparedToken> actual = getActual(testTokens);
        PreparedToken[] expected = TestUtils.getPreparedTokensFrom("/closed-is-invalid/expected");

        assertThat(actual).containsSequence(expected);

    }

    @Test
    public void syntaxAnalyzer_ShouldReturnAllTokens_ThenFewNestedTokens()
            throws ParseException, NotFoundJSONField, IOException {

        Token[] testTokens = TestUtils.getTokensFrom("/correct-nested/test");
        List<PreparedToken> actual = getActual(testTokens);
        PreparedToken[] expected = TestUtils.getPreparedTokensFrom("/correct-nested/expected");

        assertThat(actual).containsSequence(expected);

    }

    private List<PreparedToken> getActual(Token[] test){
        ArrayList<PreparedToken> actual = new ArrayList<>();
        for(int i = 0; i < test.length; i++) {
            boolean isLast = i == test.length -1;
            actual.add(syntaxAnalyzer.getPreparedToken(test[i], isLast));
        }
        if(!syntaxAnalyzer.getCorrectedTokens().isEmpty())
            actual.addAll(syntaxAnalyzer.getCorrectedTokens());
        return actual;
    }
}