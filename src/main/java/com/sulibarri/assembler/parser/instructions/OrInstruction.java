package com.sulibarri.assembler.parser.instructions;

import com.sulibarri.assembler.parser.SyntaxException;

/**
 * Created by steven on 12/3/15.
 */
public class OrInstruction extends DataProcInstruction {

    public OrInstruction(String destinationRegister, String operandRegister, String value, int lineNumber) {
        super(destinationRegister, operandRegister, value, lineNumber);
        this.opcode = "OR";
    }

    @Override
    public int getIntInstruction() throws SyntaxException {
        int condition = setConditionCode("al");
        int opCode = setOpcode();
        int operandRegister = setOperandRegister(this.operandRegister);
        int destinationRegister = setDestinationRegister(this.destinationRegister);
        int value = setImm12Rotation(this.value);

        return condition ^ opCode ^ operandRegister ^ destinationRegister ^ value;
    }

    protected int setOpcode() {
        return 0x38 << 20;
    }
}
