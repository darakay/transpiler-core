package com.darakay.transpiler.core.syntax;

import com.darakay.transpiler.core.tokens.PreparedToken;

 interface BaseSyntaxAnalyzerState {
    PreparedToken getApprovedToken(BaseSyntaxAnalyzer syntaxAnalyzer);

    void handleNonTerminateToken(BaseSyntaxAnalyzer syntaxAnalyzer);

    void handleClosingBracketToken(BaseSyntaxAnalyzer syntaxAnalyzer);

    void handleOpenBracketToken(BaseSyntaxAnalyzer syntaxAnalyzer);
}
