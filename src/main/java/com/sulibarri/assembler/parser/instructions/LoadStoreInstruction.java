package com.sulibarri.assembler.parser.instructions;

/**
 * Created by steven on 12/3/15.
 */
public abstract class LoadStoreInstruction extends Instruction {

    protected String baseRegister;
    protected String offsetValue;

    public LoadStoreInstruction(String baseRegister, String offsetValue, int lineNumber) {
        super(lineNumber);
        this.baseRegister = baseRegister;
        this.offsetValue = offsetValue;
    }

    protected int setBaseRegister(String register) {
        return registerStringToBtye(register) << 16;
    }

    protected int setOffset(String value) {
        return valueStringtoInt(value);
    }
}
