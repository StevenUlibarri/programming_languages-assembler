package com.sulibarri.assembler.parser.instructions;

import com.sulibarri.assembler.parser.SyntaxException;

/**
 * Created by steven on 12/3/15.
 */
public class StrInstruction extends LoadStoreInstruction {

    protected String sourceRegister;

    public StrInstruction(String sourceRegister, String baseRegister, String offsetValue, int lineNumber) {
        super(baseRegister, offsetValue, lineNumber);
        this.opcode = "STR";
        this.sourceRegister = sourceRegister;
    }

    @Override
    public int getIntInstruction() throws SyntaxException {
        int condition = setConditionCode("al");
        int opcode = setOpcode();
        int baseRegister = setBaseRegister(this.baseRegister);
        int sourceRegister = setSourceRegister(this.sourceRegister);
        int offset = setOffset(this.offsetValue);

        return condition ^ opcode ^ baseRegister ^ sourceRegister ^ offset;
    }

    protected int setSourceRegister(String register) {
        return registerStringToBtye(register) << 12;
    }

    protected int setOpcode() {
        return 0x40 << 20;
    }

    public String toString() {
        return this.getLineNumber()
                + " " + this.opcode
                + " " + this.sourceRegister
                + " " + this.baseRegister;
    }
}
