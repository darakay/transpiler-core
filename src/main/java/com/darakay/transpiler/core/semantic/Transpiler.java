package com.darakay.transpiler.core.semantic;

import com.darakay.transpiler.core.tokens.PreparedToken;
import com.darakay.transpiler.core.tokens.Token;

import java.util.List;

public interface Transpiler {

     List<Token> translate(PreparedToken token);
}
