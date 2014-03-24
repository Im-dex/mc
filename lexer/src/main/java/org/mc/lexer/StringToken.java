/**
 * Created by dex on 24.03.2014.
 */
public class StringToken extends Token {

    public final String value;

    public StringToken(TokenType type, int startOfsset, int endOffset, int line, int column, String value) {
        super(type, startOfsset, endOffset, line, column);
        this.value = value;
    }
}
