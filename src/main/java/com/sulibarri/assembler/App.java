package com.sulibarri.assembler;

import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, TokenizerException
    {
        Tokenizer t = new Tokenizer();
        BufferedReader br = new BufferedReader(new FileReader("./assembly/blink.asm"));
        String line;
        while((line = br.readLine()) != null) {
//            if(line.length() > 0) {
                t.setLine(line);
                while(t.hasNext()) {
                    System.out.println(t.next());
                }
//            }
        }
    }
}
