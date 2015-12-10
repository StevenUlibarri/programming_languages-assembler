package com.sulibarri.assembler.parser;

import com.sulibarri.assembler.parser.instructions.*;
import com.sulibarri.assembler.tokenizer.Token;

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.StandardOpenOption.*;


/**
 * Created by Steven on 11/30/2015.
 */
public class Parser {

    private List<Instruction> instructions;
    private LabelTable labelTable;
    private List<BranchInstruction> branchesWithUnresolvedLabels;
    private int line;

    OpenOption[] options = new OpenOption[] { WRITE, CREATE_NEW, APPEND };

    private static String[] movOpcodes = {"movw", "movt"};
    private static String[] dataProcOpcodes = {"add", "subs", "or"};
    private static String[] loadStoreOpcodes = {"ldr", "str"};
    private static String[] branchOpcodes = {"bne", "bal"};

    private static Pattern registerPattern = Pattern.compile("[rN](\\d{1,2})");
    private static Pattern hexPattern = Pattern.compile("0x([0-9ABCDEFabcdef]+)");

    public Parser() {
        this.instructions = new ArrayList<Instruction>();
        this.labelTable = new LabelTable();
        this.branchesWithUnresolvedLabels = new ArrayList<BranchInstruction>();
        this.line = 0;
    }

    public void parseLine(LinkedList<Token> tokens) throws SyntaxException {
        this.line++;
        tryParseLabel(tokens);
        tryParseInstruction(tokens);
    }

    public void toFile(String file) throws IOException, SyntaxException {
        if(!branchesWithUnresolvedLabels.isEmpty())
            resolveLabels();

        Path p = Paths.get(file);

        if(Files.exists(p)) {
            Files.delete(p);
        }

        ByteBuffer buffer = ByteBuffer.allocate(line * 4);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        for(Instruction i : instructions) {
//            System.out.println(Integer.toHexString(i.getIntInstruction()));
//            System.out.println(i.toString());
            buffer.putInt(i.getIntInstruction());
        }
        Files.write(p, buffer.array());
    }

    private void resolveLabels() throws SyntaxException {
        for(BranchInstruction b: branchesWithUnresolvedLabels) {
            if(labelTable.containsLabel(b.getLabel()))
                b.setLabelReference(labelTable.getLocationForLabel(b.getLabel()));
            else {
                throw new SyntaxException("Undefined label", b.getLineNumber());
            }
        }
    }

    private void tryParseLabel(LinkedList<Token> tokens) throws SyntaxException {
        try {
            Token label = tokens.removeFirst();
            Token colon = tokens.removeFirst();

            if(isLabel(label) &&
                    isColon(colon)) {
                labelTable.addLabel(label.getVal(), this.line);
            }
            else {
                tokens.addFirst(colon);
                tokens.addFirst(label);
            }
        }
        catch (NoSuchElementException e) {
            throw new SyntaxException("incomplete instruction", this.line);
        }
    }

    private void tryParseInstruction(LinkedList<Token> tokens) throws SyntaxException {
        if(tryParseMov(tokens) ||
                tryParseDataProc(tokens) ||
                tryParseLoadStore(tokens) ||
                tryParseBranch(tokens)) {}
        else {
            throw new SyntaxException("Syntax error", this.line);
        }
    }

    private boolean tryParseMov(LinkedList<Token> tokens) throws SyntaxException {
        if(tokens.size() != 4)
            return false;

        Token opCode = tokens.removeFirst();
        Token destRegister = tokens.removeFirst();
        Token comma = tokens.removeFirst();
        Token value = tokens.removeFirst();

        if(tokens.isEmpty() && isMovCode(opCode) &&
                isRegister(destRegister) &&
                isComma(comma) &&
                isNumeric(value)) {
            if(opCode.getVal().equals("movw")){
                instructions.add(new MovWInstruction(destRegister.getVal(), value.getVal(), this.line));
            }
            else if(opCode.getVal().equals("movt")) {
                instructions.add(new MovTInstruction(destRegister.getVal(), value.getVal(), this.line));
            }
            return true;
        }
        else {
            tokens.addFirst(value);
            tokens.addFirst(comma);
            tokens.addFirst(destRegister);
            tokens.addFirst(opCode);
            return false;
        }
    }

    private boolean tryParseDataProc(LinkedList<Token> tokens) throws SyntaxException {
        if(tokens.size() != 6)
            return false;

        Token opCode = tokens.removeFirst();
        Token destRegister = tokens.removeFirst();
        Token comma0 = tokens.removeFirst();
        Token operandRegister = tokens.removeFirst();
        Token comma1 = tokens.removeFirst();
        Token value = tokens.removeFirst();

        if(tokens.isEmpty() && isDataProcCode(opCode) &&
                isRegister(destRegister) &&
                isComma(comma0) &&
                isRegister(operandRegister) &&
                isComma(comma1) &&
                isNumeric(value)) {
            if(opCode.getVal().equals("add")) {
                instructions.add(new AddInstruction(destRegister.getVal(),
                        operandRegister.getVal(),
                        value.getVal(),
                        this.line));
            }
            else if(opCode.getVal().equals("subs")) {
                instructions.add(new SubsInstruction(destRegister.getVal(),
                        operandRegister.getVal(),
                        value.getVal(),
                        this.line));
            }
            else if(opCode.getVal().equals("or")) {
                instructions.add(new OrInstruction(destRegister.getVal(),
                        operandRegister.getVal(),
                        value.getVal(),
                        this.line));
            }
            return true;
        }
        else {
            tokens.addFirst(value);
            tokens.addFirst(comma1);
            tokens.addFirst(operandRegister);
            tokens.addFirst(comma0);
            tokens.addFirst(destRegister);
            tokens.addFirst(opCode);
            return false;
        }
    }

    private boolean tryParseLoadStore(LinkedList<Token> tokens) throws SyntaxException {
        if(tokens.size() != 4)
            return false;

        Token opCode = tokens.removeFirst();
        Token destRegister = tokens.removeFirst();
        Token comma = tokens.removeFirst();
        Token baseRegister = tokens.removeFirst();

        if(tokens.isEmpty() && isLoadStoreCode(opCode) &&
                isRegister(destRegister) &&
                isComma(comma) &&
                isRegister(baseRegister)) {
            if(opCode.getVal().equals("ldr")) {
                instructions.add(new LdrInstruction(destRegister.getVal(),
                        baseRegister.getVal(),
                        "0",
                        this.line));
            }
            else if(opCode.getVal().equals("str")) {
                instructions.add(new StrInstruction(destRegister.getVal(),
                        baseRegister.getVal(),
                        "0",
                        this.line));
            }
            return true;
        }
        else {
            tokens.addFirst(baseRegister);
            tokens.addFirst(comma);
            tokens.addFirst(destRegister);
            tokens.addFirst(opCode);
            return false;
        }
    }

    private boolean tryParseBranch(LinkedList<Token> tokens) throws SyntaxException {
        if(tokens.size() != 2)
            return false;

        Token opCode = tokens.removeFirst();
        Token label = tokens.removeFirst();

        if(tokens.isEmpty() && isBranchCode(opCode) &&
                isAlphanumeric(label)) {
            BranchInstruction i = null;
            if(opCode.getVal().equals("bal")) {
                i = new BalInstruction(label.getVal(), this.line);
            }
            else if(opCode.getVal().equals("bne")) {
                i = new BneInstruction(label.getVal(), this.line);
            }
            if(i == null)
                throw new SyntaxException("the thing that should have happened happened", this.line);

            if(labelTable.containsLabel(label.getVal())) {
                i.setLabelReference(labelTable.getLocationForLabel(label.getVal()));
            }
            else {
                branchesWithUnresolvedLabels.add(i);
            }
            instructions.add(i);
            return true;
        }
        else {
            tokens.addFirst(label);
            tokens.addFirst(opCode);
            return false;
        }
    }

    private boolean arrayContains(String[] arr, String target) {
        boolean contains = false;
        for(String s : arr) {
            if(s.equalsIgnoreCase(target))
                return true;
        }
        return contains;
    }

    private boolean isAlphanumeric(Token t) {
        return t.getType() == Token.TokenType.ALPHANUMERIC;
    }

    private boolean isSpecialCharacter(Token t) {
        return t.getType() == Token.TokenType.SPECIAL_CHARACTER;
    }

    private boolean isHex(Token t) {
        return t.getType() == Token.TokenType.HEX;
    }

    private boolean isDecimal(Token t) {
        return t.getType() == Token.TokenType.DECIMAL;
    }

    private boolean isLabel(Token t) {
        return t.getType() == Token.TokenType.ALPHANUMERIC;
    }

    private boolean isColon(Token t) {
        return this.isSpecialCharacter(t) && ":".equals(t.getVal());
    }

    private boolean isComma(Token t) {
        return isSpecialCharacter(t) && ",".equals(t.getVal());
    }

    private boolean isMovCode(Token t) {
        return isAlphanumeric(t) && arrayContains(Parser.movOpcodes, t.getVal());
    }

    private boolean isDataProcCode(Token t) {
        return  isAlphanumeric(t) && arrayContains(Parser.dataProcOpcodes, t.getVal());
    }

    private boolean isLoadStoreCode(Token t) {
        return isAlphanumeric(t) && arrayContains(Parser.loadStoreOpcodes, t.getVal());
    }

    private boolean isBranchCode(Token t) {
        return isAlphanumeric(t) && arrayContains(Parser.branchOpcodes, t.getVal());
    }

    private boolean isNumeric(Token t) {
        boolean numeric = false;
        if(isDecimal(t))
            numeric = isDecimalLiteral(t.getVal());
        if(isHex(t))
            numeric = isHexLiteral(t.getVal());
        return numeric;
    }

    private boolean isDecimalLiteral(String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isHexLiteral(String s) {
        try {
            Matcher m = Parser.hexPattern.matcher(s);
            if(m.matches()) {
                Integer.parseInt(m.group(1), 16);
                return true;
            }
            else return false;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isRegister(Token t) {
        return isAlphanumeric(t) && isRegisterLiteral(t.getVal());
    }

    private boolean isRegisterLiteral(String s) {
        Matcher m = Parser.registerPattern.matcher(s);
        int register;
        if(m.matches()) {
            register = Integer.parseInt(m.group(1));
            return register >= 0 && register <= 15;
        }
        else
            return false;
    }
}
