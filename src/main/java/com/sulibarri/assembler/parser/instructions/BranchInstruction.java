package com.sulibarri.assembler.parser.instructions;

/**
 * Created by steven on 12/3/15.
 */
public abstract class BranchInstruction extends Instruction {

    private String label;
    protected int labelDefinitionLine;

    public BranchInstruction(String label, int lineNumber) {
        super(lineNumber);
        this.label = label;
        this.labelDefinitionLine = labelDefinitionLine;
    }

    public void setLabelReference(int ref) {
        this.labelDefinitionLine = ref;
    }

    public String toString() {
        return this.getLineNumber()
                + " " + this.opcode
                + " " + this.label
                + "(" + this.labelDefinitionLine + ")";
    }

    public String getLabel() {
        return this.label;
    }

    protected int setOpcode() {
        return 0xA << 24;
    }

    protected int setValue() {
//        System.out.println("val");
//        System.out.println(Integer.toHexString(labelDefinitionLine - getLineNumber() - 2));
        return (labelDefinitionLine - getLineNumber() - 2) & 0xFFFFFF;
    }
}
