package com.sulibarri.assembler.parser.instructions;

import com.sulibarri.assembler.parser.SyntaxException;

/**
 * Created by steven on 12/3/15.
 */
public class MovTInstruction extends MovInstruction {

    public MovTInstruction(String destinationRegister, String value, int lineNumber) {
        super(destinationRegister, value, lineNumber);
        this.opcode = "MOVT";
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
        return 0x34 << 20;
    }
}
