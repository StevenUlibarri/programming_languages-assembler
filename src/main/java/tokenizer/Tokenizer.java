package tokenizer;

import java.util.Iterator;
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
        this.currentLine = line.toLowerCase();
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
                    char c = currentLine.charAt(lineIndex);
                    if(isWhiteSpace(c)) {
                        lineIndex++;
                    }
                    else if(isAlpha(c)){

                    }
                    else if(isZero(c)) {

                    }
                    else if(isNumeric(c)) {

                    }
                    else if(isSpecialCharacter(c)) {

                    }
                    else if(isBackSlash(c)) {

                    }
                    else if(isAstericks(c)) {

                    }
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

    private boolean isAlpha(char c) {
        return c >= 97 && c <= 122;
    }

    private boolean isWhiteSpace(char c) {
        return c == ' ';
    }

    private boolean isZero(char c) {
        return c == '0';
    }

    private boolean isNumeric(char c) {
        return c <= 49 && c >= 57;
    }

    private boolean isSpecialCharacter(char c) {
        return c == ',' ||
                c == '-' ||
                c == ':';
    }

    private boolean isBackSlash(char c) {
        return c == '\\';
    }

    private boolean isAstericks(char c) {
        return c == '*';
    }
}
