package com.sulibarri.assembler.parser.instructions;

import com.sulibarri.assembler.parser.SyntaxException;

/**
 * Created by steven on 12/3/15.
 */
public class BalInstruction extends BranchInstruction {
    public BalInstruction(String label, int lineNumber) {
        super(label, lineNumber);
        this.opcode = "BAL";
    }

    @Override
    public int getIntInstruction() throws SyntaxException {
        int condition = setConditionCode(opcode.substring(1,3));
        int opCode = setOpcode();
        int value = setValue();
//
//        System.out.println("BAL");
//        System.out.println(this.getLineNumber());
//        System.out.println(this.labelDefinitionLine);
//        System.out.println(opcode.substring(1,3));
//        System.out.println(Integer.toHexString(condition));
//        System.out.println(Integer.toHexString(opCode));
//        System.out.println(Integer.toHexString(value));

        return condition ^ opCode ^ value;
    }
}
