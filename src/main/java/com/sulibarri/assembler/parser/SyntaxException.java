package com.sulibarri.assembler.parser;

/**
 * Created by Steven on 11/30/2015.
 */
public class SyntaxException extends Exception {

    public SyntaxException(String message, int lineNumber) {
        super(message + " at line " + lineNumber);
    }
}
