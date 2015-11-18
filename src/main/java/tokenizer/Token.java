package tokenizer;

/**
 * Created by steven on 11/18/15.
 */
public class Token {

    public enum TokenType {
        REGISTER,
        OPCODE,
        NUMBER,
        LABEL,
        NEWLINE,
    }

    private String val;

    public Token(String val) {
        this.val = val;
    }
}
