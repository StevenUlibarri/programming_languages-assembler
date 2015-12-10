package com.sulibarri.assembler;

import com.sulibarri.assembler.parser.Parser;
import com.sulibarri.assembler.parser.SyntaxException;
import com.sulibarri.assembler.tokenizer.Token;
import com.sulibarri.assembler.tokenizer.Tokenizer;
import com.sulibarri.assembler.tokenizer.TokenizerException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class App 
{
    public static void main( String[] args ) throws IOException {
        try {
            Tokenizer tokenizer = new Tokenizer();
            Parser parser = new Parser();
            BufferedReader br = new BufferedReader(new FileReader("./assembly/blink.asm"));
            String line;
            while((line = br.readLine()) != null) {
                tokenizer.setLine(line);
                LinkedList<Token> tokens = new LinkedList<Token>();
                while(tokenizer.hasNext()) {
                    Token t = tokenizer.next();
                    if(t != null)
                        tokens.add(t);
                }
                if(!tokens.isEmpty())
                    parser.parseLine(tokens);
            }
            parser.toFile("./images/kernel7.img");
        }
        catch (TokenizerException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        catch (SyntaxException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }

//        int cond = 0xE << 28;
//        int code = 0x38 << 20;
//        int opRegister = registerStringToBtye("R3") << 16;
//        int destRegister = registerStringToBtye("R3") << 12;
//        int val = setImm12("0x00200000");
//
//        int i = cond ^ code ^ opRegister ^ destRegister ^ val;
//        System.out.println(Integer.toHexString(i));

//        System.out.println(Integer.toHexString(setImm12("0x00200000")));
//        int val = 0x00200000;
//        int rot = 30;
//        System.out.println(Integer.toBinaryString(val));
//        for(int ii = 0; ii <= 15; ii++) {
//            val = (val >>> 2) | (val << rot);
//            System.out.println(Integer.toBinaryString(val));
//        }
    }

//    private static byte registerStringToBtye(String register) {
//        String r = register.substring(1);
//        return (byte)Integer.parseInt(r);
//    }
//
//    private static int setImm12(String value) {
//        int val = 0;
//        if(value.substring(0,2).equalsIgnoreCase("0x"))
//            val = Integer.parseInt(value.substring(2), 16);
//        else
//            val = Integer.parseInt(value);
//
//        for(int i = 0; i <= 15; i++) {
//            if((val & ~0xFF) == 0) {
//                int rotate = i << 8;
//                return rotate ^ val;
//            }
//            else {
//                val = (val << 2) | (val >>> 30);
//            }
//        }
//
//        System.out.println("impossible");
//
//        return 0;
//    }
}
