package com.sulibarri.assembler.parser.instructions;

import com.sulibarri.assembler.parser.SyntaxException;

/**
 * Created by steven on 12/3/15.
 */
public class BneInstruction extends BranchInstruction {

    public BneInstruction(String label, int lineNumber) {
        super(label, lineNumber);
        this.opcode = "BNE";
    }

    @Override
    public int getIntInstruction() throws SyntaxException {
        int condition = setConditionCode(opcode.substring(1,3));
        int opCode = setOpcode();
        int value = setValue();

//        System.out.println("BNE");
//        System.out.println(opcode.substring(1,3));
//        System.out.println(Integer.toHexString(condition));
//        System.out.println(Integer.toHexString(opCode));
//        System.out.println(Integer.toHexString(value));

        return condition ^ opCode ^ value;
        //return 0;
    }

}
