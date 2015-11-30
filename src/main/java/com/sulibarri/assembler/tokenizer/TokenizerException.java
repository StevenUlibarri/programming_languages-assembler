package com.sulibarri.assembler.tokenizer;

/**
 * Created by steven on 11/26/15.
 */
public class TokenizerException extends Exception {

    public TokenizerException(int lineNumber) {
        super("Tokenizer exception at line " + lineNumber);
    }

    public TokenizerException(String message, int lineNumber) {
        super(message + " at line " + lineNumber);
    }
}
