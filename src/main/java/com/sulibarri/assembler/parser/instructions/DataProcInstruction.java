package com.sulibarri.assembler.parser.instructions;

import com.sulibarri.assembler.parser.SyntaxException;

/**
 * Created by steven on 12/3/15.
 */
public abstract class DataProcInstruction extends Instruction {

    protected String destinationRegister;
    protected String operandRegister;
    protected String value;

    public DataProcInstruction(String destinationRegister, String operandRegister, String value, int lineNumber) {
        super(lineNumber);
        this.destinationRegister = destinationRegister;
        this.operandRegister = operandRegister;
        this.value = value;
    }

    public String toString() {
        return this.getLineNumber()
                + " " + this.opcode
                + " " + destinationRegister
                + " " + operandRegister
                + " " + this.value;
    }

    protected int setOperandRegister(String register) {
        return registerStringToBtye(register) << 16;
    }

    protected int setDestinationRegister(String register) {
        return registerStringToBtye(register) << 12;
    }

    protected int setImm12Rotation(String value) throws SyntaxException {

        int val = setImm12(value);

        for(int i = 0; i <= 15; i++) {
            if((val & ~0xFF) == 0) {
                int rotate = i << 8;
                return rotate ^ val;
            }
            else {
                val = (val << 2) | (val >>> 30);
            }
        }

        throw new SyntaxException("impossible value", this.getLineNumber());
    }

    protected int setImm12(String value) {
        return this.valueStringtoInt(value);
    }
}
