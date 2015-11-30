package com.sulibarri.assembler.parser;

/**
 * Created by Steven on 11/30/2015.
 */
public class ParserException extends Exception {

    public ParserException(String message, int lineNumber) {
        super(message + " at line " + lineNumber);
    }
}
