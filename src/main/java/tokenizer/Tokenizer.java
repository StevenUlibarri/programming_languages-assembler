package tokenizer;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Created by steven on 11/18/15.
 */
public class Tokenizer implements Iterator<Token>{

    public Tokenizer() {
             }

    public boolean hasNext() {
        return false;
    }

    public Token next() {
        return null;
    }

    public void remove() {

    }

    public void forEachRemaining(Consumer<? super Token> action) {

    }
}
