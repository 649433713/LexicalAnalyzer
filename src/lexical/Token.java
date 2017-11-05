package lexical;

/**
 * @author yinywf
 * Created on 2017/11/4
 **/
public class Token {

    private TokenType tokenType;
    private String value;

    @Override
    public String toString() {
        return "( " + tokenType.toString() + " , " + value + " )";
    }

    public Token(TokenType tokenType, String value) {
        this.tokenType = tokenType;
        this.value = value;
    }

}