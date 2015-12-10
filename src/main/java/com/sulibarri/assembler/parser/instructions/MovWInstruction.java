package com.sulibarri.assembler.parser.instructions;

import com.sulibarri.assembler.parser.SyntaxException;

/**
 * Created by steven on 12/3/15.
 */
public class MovWInstruction extends MovInstruction {

    public MovWInstruction(String destinationRegister, String value, int lineNumber) {
        super(destinationRegister, value, lineNumber);
        this.opcode = "MOVW";
    }

    @Override
    public int getIntInstruction() throws SyntaxException {
        int condition = setConditionCode("al");
        int opCode = setOpcode();
        int destinationRegister = setDestinationRegister(this.destinationRegister);
        int value = setValue(this.value);

        return condition ^ opCode ^ destinationRegister ^ value;
    }

    protected int setOpcode() {
        return 0x30 << 20;
    }
}
