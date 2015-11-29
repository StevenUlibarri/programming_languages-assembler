package tokenizer;

/**
 * Created by steven on 11/18/15.
 */
public class Token {

    public enum TokenType {
        SPECIAL_CHARACTER,
        DECIMAL,
        HEX,
        ALPHANUMERIC
    }

    private String val;
    private TokenType type;

    public Token(String val, TokenType type) {
        this.val = val;
        this.type = type;
    }

    public String getVal() {
        return this.val;
    }

    public TokenType getType() {
        return this.type;
    }

    public String toString() {
        return this.type + ": " + this.val;
    }
}
