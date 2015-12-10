package com.sulibarri.assembler.parser.instructions;

import com.sulibarri.assembler.parser.SyntaxException;

/**
 * Created by steven on 12/3/15.
 */
public abstract class MovInstruction extends Instruction {

    protected String destinationRegister;
    protected String value;

    public MovInstruction(String destinationRegister, String value, int lineNumber) {
        super(lineNumber);
        this.destinationRegister = destinationRegister;
        this.value = value;
    }

    protected int setDestinationRegister(String register) {
        return registerStringToBtye(register) << 12;
    }

    protected int setValue(String value) throws SyntaxException {
        int val = valueStringtoInt(value);

        if((val & ~0xFFFF) != 0)
            throw new SyntaxException("oversized arg (>16)", this.getLineNumber());

        int topBits = (val >> 12) << 16;
        int bottomBits = val & 0xFFF;
        return topBits ^ bottomBits;
    }

    public String toString() {
        return this.getLineNumber()
                + " " + this.opcode
                + " " + this.destinationRegister
                + " " + this.value;
    }
}
