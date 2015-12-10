package com.sulibarri.assembler.parser.instructions;

import com.sulibarri.assembler.parser.SyntaxException;

/**
 * Created by Steven on 11/30/2015.
 */
public abstract class Instruction {

    private int lineNumber;
    protected String opcode;

    public Instruction(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public String getOpcode() {
        return this.opcode;
    }

    public abstract int getIntInstruction() throws SyntaxException;

    protected int setConditionCode(String condition) {
        int instruction = 0;
        if("al".equalsIgnoreCase(condition)) {
            instruction = 0xE << 28;
        }
        else if("ne".equalsIgnoreCase(condition)) {
            instruction = 0x1 << 28;
        }
        return instruction;
    }

    protected abstract int setOpcode();

    protected byte registerStringToBtye(String register) {
        String r = register.substring(1);
        return (byte)Integer.parseInt(r);
    }

    protected int valueStringtoInt(String value) {
        int val = 0;
        if(value.length() > 1 && value.substring(0,2).equalsIgnoreCase("0x"))
            val = Integer.parseInt(value.substring(2), 16);
        else
            val = Integer.parseInt(value);

        return val;
    }
}
