package tokenizer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Created by steven on 11/18/15.
 */
public class Tokenizer {

    private enum State {
        A,B,C,D,E,F,G,H,I
    }

    private static char[] specialChars = {',','-',':'};

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
        this.currentLine = (line + "\n").toLowerCase();
        lineNumber++;
        lineIndex = 0;
        this.state = State.A;
        this.builder.setLength(0);
    }

    public boolean hasNext() {
        if(currentLine == null)
            return false;
        else if (currentLine.charAt(0) == '\n')
            return false;
        else
            return lineIndex < currentLine.length();
    }

    public Token next() throws TokenizerException {
        return makeToken();
    }

    private Token makeToken() throws TokenizerException {
        Token token = null;
        while(token == null && lineIndex < currentLine.length()) {
            char c = currentLine.charAt(lineIndex);
            switch (this.state) {
                case A:
                    if(isWhiteSpace(c)) {
                        lineIndex++;
                        builder.append(c);
                    }
                    else if(isAlpha(c)){
                        lineIndex++;
                        builder.append(c);
                        this.state = State.B;
                    }
                    else if(isNumeric(c)) {
                        lineIndex++;
                        builder.append(c);
                        this.state = State.E;
                    }
                    else if(isZero(c)) {
                        lineIndex++;
                        builder.append(c);
                        this.state = State.C;
                    }
                    else if(isSpecialCharacter(c)) {
                        lineIndex++;
                        builder.append(c);
                        this.state = State.F;
                    }
                    else if(isBackSlash(c)) {
                        lineIndex++;
                        this.state = State.G;
                    }
                    else if(isAstericks(c)) {
                        throw new TokenizerException(this.lineNumber);
                    }
                    else if(isNewLine(c)) {
                        lineIndex++;
                    }
                    break;
                case B:
                    if(isWhiteSpace(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.ALPHANUMERIC);
                        builder.setLength(0);
                        this.state = State.A;
                    }
                    else if(isAlpha(c)){
                        lineIndex++;
                        builder.append(c);
                    }
                    else if(isNumeric(c)) {
                        lineIndex++;
                        builder.append(c);
                    }
                    else if(isZero(c)) {
                        lineIndex++;
                        builder.append(c);
                    }
                    else if(isSpecialCharacter(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.ALPHANUMERIC);
                        builder.setLength(0);
                        builder.append(c);
                        this.state = State.F;
                    }
                    else if(isBackSlash(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.ALPHANUMERIC);
                        builder.setLength(0);
                        this.state = State.G;
                    }
                    else if(isAstericks(c)) {
                        throw new TokenizerException(this.lineNumber);
                    }
                    else if(isNewLine(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.ALPHANUMERIC);
                    }
                    break;
                case C:
                    if(isWhiteSpace(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.ALPHANUMERIC);
                        builder.append(c);
                        this.state = State.A;
                    }
                    else if(isAlpha(c)){
                        if(c == 'x'){
                            lineIndex++;
                            builder.append(c);
                            this.state = State.D;
                        }
                        else
                            throw new TokenizerException(this.lineNumber);
                    }
                    else if(isNumeric(c)) {
                        lineIndex++;
                        builder.append(c);
                        this.state = State.E;
                    }
                    else if(isZero(c)) {
                        lineIndex++;
                        builder.append(c);
                        this.state = State.E;
                    }
                    else if(isSpecialCharacter(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.ALPHANUMERIC);
                        builder.setLength(0);
                        builder.append(c);
                        this.state = State.F;
                    }
                    else if(isBackSlash(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.ALPHANUMERIC);
                        builder.setLength(0);
                        this.state =State.G;
                    }
                    else if(isAstericks(c)) {
                        throw new TokenizerException(this.lineNumber);
                    }
                    else if(isNewLine(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.DECIMAL);
                    }
                    break;
                case D:
                    if(isWhiteSpace(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.HEX);
                        builder.setLength(0);
                        this.state = State.A;
                    }
                    else if(isAlpha(c)){
                        if(isValidHexChar(c)) {
                            lineIndex++;
                            builder.append(c);
                        }
                        else
                            throw new TokenizerException("Invalid hex", this.lineNumber);
                    }
                    else if(isNumeric(c)) {
                        lineIndex++;
                        builder.append(c);
                    }
                    else if(isZero(c)) {
                        lineIndex++;
                        builder.append(c);
                    }
                    else if(isSpecialCharacter(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.HEX);
                        builder.setLength(0);
                        builder.append(c);
                        this.state = State.F;
                    }
                    else if(isBackSlash(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.HEX);
                        builder.setLength(0);
                        this.state = State.G;
                    }
                    else if(isAstericks(c)) {
                        throw new TokenizerException(this.lineNumber);
                    }
                    else if(isNewLine(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.HEX);
                    }
                    break;
                case E:
                    if(isWhiteSpace(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.DECIMAL);
                        builder.setLength(0);
                        this.state = State.A;
                    }
                    else if(isAlpha(c)){
                        throw new TokenizerException(this.lineNumber);
                    }
                    else if(isNumeric(c)) {
                        lineIndex++;
                        builder.append(c);
                    }
                    else if(isZero(c)) {
                        lineIndex++;
                        builder.append(c);
                    }
                    else if(isSpecialCharacter(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.DECIMAL);
                        builder.setLength(0);
                        builder.append(c);
                        this.state = State.F;
                    }
                    else if(isBackSlash(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.DECIMAL);
                        builder.setLength(0);
                        this.state = State.G;
                    }
                    else if(isAstericks(c)) {
                        throw new TokenizerException(this.lineNumber);
                    }
                    else if(isNewLine(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.DECIMAL);
                    }
                    break;
                case F:
                    if(isWhiteSpace(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.SPECIAL_CHARACTER);
                        builder.setLength(0);
                        this.state = State.A;
                    }
                    else if(isAlpha(c)){
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.SPECIAL_CHARACTER);
                        builder.setLength(0);
                        builder.append(c);
                        this.state = State.B;
                    }
                    else if(isNumeric(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.SPECIAL_CHARACTER);
                        builder.setLength(0);
                        builder.append(c);
                        this.state = State.E;
                    }
                    else if(isZero(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.SPECIAL_CHARACTER);
                        builder.setLength(0);
                        builder.append(c);
                        this.state = State.C;
                    }
                    else if(isSpecialCharacter(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.SPECIAL_CHARACTER);
                        builder.setLength(0);
                        builder.append(c);
                    }
                    else if(isBackSlash(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.SPECIAL_CHARACTER);
                        builder.setLength(0);
                        this.state = State.G;
                    }
                    else if(isAstericks(c)) {
                        throw new TokenizerException(this.lineNumber);
                    }
                    else if(isNewLine(c)) {
                        lineIndex++;
                        token = new Token(builder.toString(), Token.TokenType.SPECIAL_CHARACTER);
                    }
                    break;
                case G:
                    if(isWhiteSpace(c)) {
                        throw new TokenizerException(this.lineNumber);
                    }
                    else if(isAlpha(c)){
                        throw new TokenizerException(this.lineNumber);
                    }
                    else if(isZero(c)) {
                        throw new TokenizerException(this.lineNumber);
                    }
                    else if(isNumeric(c)) {
                        throw new TokenizerException(this.lineNumber);
                    }
                    else if(isSpecialCharacter(c)) {
                        throw new TokenizerException(this.lineNumber);
                    }
                    else if(isBackSlash(c)) {
                        throw new TokenizerException(this.lineNumber);
                    }
                    else if(isAstericks(c)) {
                        lineIndex++;
                        this.state = State.H;
                    }
                    else if(isNewLine(c)) {
                        throw new TokenizerException(this.lineNumber);
                    }
                    break;
                case H:
                    if(isWhiteSpace(c)) {
                        lineIndex++;
                    }
                    else if(isAlpha(c)){
                        lineIndex++;
                    }
                    else if(isZero(c)) {
                        lineIndex++;
                    }
                    else if(isNumeric(c)) {
                        lineIndex++;
                    }
                    else if(isSpecialCharacter(c)) {
                        lineIndex++;
                    }
                    else if(isBackSlash(c)) {
                        lineIndex++;
                    }
                    else if(isAstericks(c)) {
                        lineIndex++;
                        this.state = State.I;
                    }
                    else if(isNewLine(c)) {
                        throw new TokenizerException("Unclosed comment",this.lineNumber);
                    }
                    break;
                case I:
                    if(isWhiteSpace(c)) {
                        lineIndex++;
                        this.state = State.H;
                    }
                    else if(isAlpha(c)){
                        lineIndex++;
                        this.state = State.H;
                    }
                    else if(isZero(c)) {
                        lineIndex++;
                        this.state = State.H;
                    }
                    else if(isNumeric(c)) {
                        lineIndex++;
                        this.state = State.H;
                    }
                    else if(isSpecialCharacter(c)) {
                        lineIndex++;
                        this.state = State.H;
                    }
                    else if(isBackSlash(c)) {
                        lineIndex++;
                        this.state = State.A;
                    }
                    else if(isAstericks(c)) {
                        lineIndex++;
                        this.state = State.I;
                    }
                    else if(isNewLine(c)) {
                        lineIndex++;
                    }
                    break;
                default:
                    break;
            }
        }
        return token;
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
        return c >= 49 && c <= 57;
    }

    private boolean isSpecialCharacter(char c) {
        boolean is = false;
        for(char sc : this.specialChars) {
            if(c == sc)
                return true;
        }
        return is;
    }

    private boolean isBackSlash(char c) {
        return c == '/';
    }

    private boolean isAstericks(char c) {
        return c == '*';
    }

    private boolean isNewLine(char c) {
        return c == '\n';
    }

    private boolean isValidHexChar(char c) {
        return c >= 'a' && c <= 'f';
    }

    public int getLineNumber() {
        return this.lineNumber;
    }
}
