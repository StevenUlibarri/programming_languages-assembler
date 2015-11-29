package tokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

/**
 * Created by steven on 11/27/15.
 */
public class TokenWrapper {

    private BufferedReader br;
    private Stack<Token> unGetStack;

    private Tokenizer t;

    public TokenWrapper(String file) throws IOException {

        br = new BufferedReader(new FileReader(file));
        unGetStack = new Stack<Token>();
        t = new Tokenizer();

    }

    public Token next() throws IOException {

    }

    public boolean hasNext() {
        return t.hasNext() || !unGetStack.isEmpty();
    }


}
