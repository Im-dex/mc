import java.math.BigInteger;

/**
 * Created by dex on 24.03.2014.
 */
public class NumberToken extends Token {

    public final BigInteger value;

    public NumberToken(TokenType type, int startOfsset, int endOffset, int line, int column, BigInteger value) {
        super(type, startOfsset, endOffset, line, column);
        this.value = value;
    }
}
