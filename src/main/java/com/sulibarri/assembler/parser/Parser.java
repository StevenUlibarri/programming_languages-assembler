package com.sulibarri.assembler.parser;

import com.sulibarri.assembler.parser.instructions.Instruction;
import com.sulibarri.assembler.tokenizer.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Steven the Sodomizer on 11/30/2015.
 */
public class Parser {

    private List<Instruction> instructions;

    public Parser() {
        this.instructions = new ArrayList<Instruction>();
    }

    public void parseLine(Queue<Token> tokens) throws ParserException {
        tryParseLabel(tokens);
        tryParseInstruction(tokens);
    }

    public void toFile(String file) throws IOException {

    }

    private void tryParseLabel(Queue<Token> tokens) throws ParserException {

    }

    private void tryParseInstruction(Queue<Token> tokens) throws ParserException {
        tryParseMov(tokens);
        tryParseDataProc(tokens);
        tryParseLoadStore(tokens);
        tryParseBranch(tokens);
    }
}
