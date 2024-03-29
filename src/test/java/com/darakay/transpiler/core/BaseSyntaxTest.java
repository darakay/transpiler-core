package com.darakay.transpiler.core;

import com.darakay.transpiler.core.lang.BaseSyntax;
import com.darakay.transpiler.core.lang.Syntax;
import com.darakay.transpiler.core.tokens.BracketType;
import com.darakay.transpiler.core.tokens.Token;
import com.darakay.transpiler.core.tokens.TokenType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;


public class BaseSyntaxTest {
    private static Syntax syntax;

    @BeforeClass
    public static void setUp() throws Exception {
        syntax = new TestSyntaxProvider().getSyntax();
    }

    @Test
    public void arePairedTokens_ShouldReturnTrue_ThenTokensArePaired() {
        Token opn = new Token("<em>", TokenType.TERMINATE);
        Token clsd = new Token("</em>", TokenType.TERMINATE);
        assertThat(syntax.arePairedTokens(opn, clsd)).isTrue();
    }

    @Test
    public void arePairedTokens_ShouldReturnFalse_ThenOpenTokenIsNonTerminate() {
        Token opn = new Token("<em>", TokenType.NON_TERMINATE);
        Token clsd = new Token("</em>", TokenType.TERMINATE);
        assertThat(syntax.arePairedTokens(opn, clsd)).isFalse();
    }

    @Test
    public void arePairedTokens_ShouldReturnFalse_ThenClosedTokenIsNonTerminate() {
        Token opn = new Token("<em>", TokenType.TERMINATE);
        Token clsd = new Token("</em>", TokenType.NON_TERMINATE);
        assertThat(syntax.arePairedTokens(opn, clsd)).isFalse();
    }

    @Test
    public void arePairedTokens_ShouldReturnFalse_ThenOneOfTokenIsNotPaired() {
        Token opn = new Token("<a>", TokenType.TERMINATE);
        Token clsd = new Token("</em>", TokenType.NON_TERMINATE);
        assertThat(syntax.arePairedTokens(opn, clsd)).isFalse();
    }

    @Test
    public void getTokenType_ShouldReturnOpen_ThenTokenIsOpen() {
        Token t = new Token("<h1>", TokenType.TERMINATE);
        assertThat(syntax.getBracketType(t)).isEqualTo(BracketType.OPEN);
    }

    @Test
    public void getTokenType_ShouldReturnClosed_ThenTokenIsClosed() {
        Token t = new Token("</h1>", TokenType.TERMINATE);
        assertThat(syntax.getBracketType(t)).isEqualTo(BracketType.CLOSED);
    }

    @Test
    public void getTokenType_ShouldReturnNo_ThenTokenIsNotBracket() {
        Token t = new Token("<a>", TokenType.TERMINATE);
        assertThat(syntax.getBracketType(t)).isEqualTo(BracketType.NO);
    }

    @Test
    public void getTokenType_ShouldReturnNo_ThenTokenIsNonTerminate() {
        Token t = new Token("<a>", TokenType.NON_TERMINATE);
        assertThat(syntax.getBracketType(t)).isEqualTo(BracketType.NO);
    }

    @Test
    public void getClosedTokenByOpen_ShouldReturnClosedToken_ThenTokenIsOpen() {
        Token open = new Token("<h1>", TokenType.TERMINATE);
        Token closed = new Token("</h1>", TokenType.TERMINATE);
        assertThat(syntax.getClosedTokenByOpen(open)).isEqualTo(closed);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getClosedTokenByOpen_ShouldThrowIllegalArgumentException_ThenTokenIsClosed() {
        Token open = new Token("</h1>", TokenType.TERMINATE);
        syntax.getClosedTokenByOpen(open);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getClosedTokenByOpen_ShouldThrowIllegalArgumentException_ThenTokenIsNotPairedToken() {
        Token open = new Token("<a>", TokenType.TERMINATE);
        syntax.getClosedTokenByOpen(open);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getClosedTokenByOpen_ShouldThrowIllegalArgumentException_ThenTokenIsNonTerminate() {
        Token open = new Token("<a>", TokenType.NON_TERMINATE);
        syntax.getClosedTokenByOpen(open);
    }
}