package com.sulibarri.assembler;

import com.sulibarri.assembler.parser.Parser;
import com.sulibarri.assembler.tokenizer.Token;
import com.sulibarri.assembler.tokenizer.Tokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            Tokenizer tokenizer = new Tokenizer();
            Parser parser = new Parser();
            BufferedReader br = new BufferedReader(new FileReader("./assembly/blink.asm"));
            String line;
            while((line = br.readLine()) != null) {
                tokenizer.setLine(line);
                Queue<Token> tokens = new LinkedList<Token>();
                while(tokenizer.hasNext()) {
                    Token t = tokenizer.next();
                    if(t != null)
                        tokens.add(t);
                }
                parser.parseLine(tokens);
            }
            parser.toFile("");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
