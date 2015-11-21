package tokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by steven on 11/18/15.
 */
public class Tokenizer implements Iterator<Token>{

    private enum State {
        A,B,C,D,E,F,G,H,I
    }

    private int lineIndex;
    private int lineNumber;
    private String currentLine;

    private StringBuilder builder;

    private State state;

    public Tokenizer() {
        this.lineIndex = 0;
        this.lineNumber = 0;
        this.builder = new StringBuilder();
    }

    public void setLine(String line) {
        this.currentLine = line;
        lineNumber++;
        lineIndex = 0;
        this.state = State.A;
    }

    public boolean hasNext() {
        if(currentLine == null)
            return false;
        else
            return lineIndex < currentLine.length();
    }

    public Token next() {
        return makeToken();
    }

    private Token makeToken() {
        Token token = null;
        while(token != null) {
            switch (this.state) {
                case A:

                    break;
                case B:

                    break;
                case C:

                    break;
                case D:

                    break;
                case E:

                    break;
                case F:

                    break;
                case G:

                    break;
                case H:

                    break;
                case I:

                    break;
                default:
                    break;
            }
        }
        return token;
    }

    public void remove() {

    }

    public void forEachRemaining(Consumer<? super Token> action) {

    }
}
