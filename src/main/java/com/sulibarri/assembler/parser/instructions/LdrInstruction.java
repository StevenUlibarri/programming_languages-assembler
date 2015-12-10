package com.sulibarri.assembler.parser.instructions;

import com.sulibarri.assembler.parser.SyntaxException;

/**
 * Created by steven on 12/3/15.
 */
public class LdrInstruction extends LoadStoreInstruction {

    protected String destinationRegister;

    public LdrInstruction(String destinationRegister, String baseRegister, String offsetValue, int lineNumber) {
        super(baseRegister, offsetValue, lineNumber);
        this.opcode = "LDR";
        this.destinationRegister = destinationRegister;
    }

    @Override
    public int getIntInstruction() throws SyntaxException {
        int condition = setConditionCode("al");
        int opcode = setOpcode();
        int baseRegister = setBaseRegister(this.baseRegister);
        int sourceRegister = setDestinationRegister(this.destinationRegister);
        int offset = setOffset(this.offsetValue);

        return condition ^ opcode ^ baseRegister ^ sourceRegister ^ offset;
    }

    protected int setDestinationRegister(String register) {
        return registerStringToBtye(register) << 12;
    }

    protected int setOpcode() {
        return 0x41 << 20;
    }

    public String toString() {
        return this.getLineNumber()
                + " " + this.opcode
                + " " + this.destinationRegister
                + " " + this.baseRegister;
    }
}
