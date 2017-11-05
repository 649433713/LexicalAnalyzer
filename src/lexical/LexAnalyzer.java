package lexical;

import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yinywf
 * Created on 2017/11/4
 **/
public class LexAnalyzer {

    private char[] input;

    private List<Token> tokens;

    private int pointer;

    private int fileLength;

    public LexAnalyzer(char[] input){
        this.pointer = 0;
        this.input = input;
        this.tokens = new ArrayList<>();
        this.fileLength = input.length;
    }


    public List<Token> handle() {

        boolean isSingleLineAnnotation = false;
        boolean isMultilineAnnotation = false;
        StringBuilder builder = new StringBuilder();

        while (pointer < fileLength) {
            builder.setLength(0);
            char tempC = input[pointer];
            builder.append(tempC);

            // 单行注释
            if (isSingleLineAnnotation) {
                pointer++;
                if (tempC == '\n') {
                    isSingleLineAnnotation = false;
                }
                continue;
            }
            // 多行注释
            else if (isMultilineAnnotation) {
                if (tempC == '*' && input[pointer + 1] =='/'){
                    addToken("*/" , TokenType.ANNOTATION);
                    isMultilineAnnotation = false;
                    pointer += 2;
                } else {
                    pointer++;
                }
                continue;
            }

            if (tempC == '\\') {
                pointer += 2;
                addToken("\\", TokenType.DELIMITER);
                continue;
            }
            // 读取到字符，判断是关键字还是识别符
            else if (Character.isLetter(tempC)) {
                // 循环遍历后续字符
                while (++pointer < fileLength) {
                    char c = input[pointer];
                    String s = builder.toString();
                    // 字符合法
                    if (Character.isLetterOrDigit(c) || c == '_') {
                        builder.append(c);
                    }
                    // 读取到空字符
                    else if (checkIfEmptyChar(c)) {
                        // 判断是否是关键字
                        if (checkIfKeyWord(s)) {
                            addToken(s, TokenType.KEY_WORD);
                            break;
                        } else {
                            addToken(s, TokenType.IDENTIFIER);
                            break;
                        }
                    } else if (checkIfDelimiter(c) || checkIfOperator(c + "")) {
                        addToken(s, TokenType.IDENTIFIER);
                        // 返回指针位置
                        pointer--;
                        break;
                    }
                    // 非法字符
                    else {
                        builder.append(c);
                        getSpareInvalidSeq(builder);
                        addToken(builder.toString(), TokenType.INVALID_IDENTIFIER);
                        break;
                    }
                }
            }
            // 读取到数字
            else if (Character.isDigit(tempC)) {
                boolean isDouble = false;
                while (++pointer < fileLength) {
                    char c = input[pointer];

                    if (Character.isDigit(c)) {
                        builder.append(c);
                    } else if (c == '.') {
                        if (isDouble) {
                            builder.append(c);
                            getSpareInvalidSeq(builder);
                            addToken(builder.toString(), TokenType.INVALID_NUM);
                            break;
                        } else {
                            isDouble = true;
                            builder.append(c);
                        }
                    } else if (checkIfDelimiter(c)) {
                        if (isDouble) {
                            addToken(builder.toString(), TokenType.DOUBLE);
                        } else {
                            addToken(builder.toString(), TokenType.INT);
                        }
                        pointer--;
                        break;
                    } else {
                        builder.append(c);
                        getSpareInvalidSeq(builder);
                        addToken(builder.toString(), TokenType.INVALID_NUM);
                        break;
                    }

                }
            } else if (tempC == '/') {
                if (input[pointer + 1] == '*') {
                    isMultilineAnnotation = true;
                    addToken("/*", TokenType.ANNOTATION);
                    pointer += 2;
                    continue;
                } else if (input[pointer + 1] == '/') {
                    isSingleLineAnnotation = true;
                    addToken("//", TokenType.ANNOTATION);
                    pointer += 2;
                    continue;
                } else if (++pointer < fileLength) {
                    char c = input[pointer];
                    // 为 / 操作符
                    if (checkIfEmptyChar(c) || Character.isLetterOrDigit(c)) {
                        addToken("/", TokenType.OPERATOR);
                        if (Character.isLetterOrDigit(c)) {
                            pointer--;
                        }
                    } else {
                        builder.append('/');
                        builder.append(c);
                        getSpareInvalidSeq(builder);
                        addToken(builder.toString(), TokenType.INVALID_IDENTIFIER);
                    }
                } else {
                    addToken("/", TokenType.OPERATOR);
                }
            } else if (tempC == '-' || tempC == '+' || tempC == '*' || tempC == '=' || tempC == '|' || tempC == '&' || tempC == '<' || tempC == '>') {

                builder.append(input[pointer + 1]);
                if (checkIfOperator(builder.toString())) {
                    addToken(builder.toString(), TokenType.OPERATOR);
                    pointer++;
                } else if (++pointer < fileLength) {
                    builder.setLength(0);

                    char c = input[pointer];
                    if (checkIfEmptyChar(c) || Character.isLetterOrDigit(c)) {
                        addToken(tempC + "", TokenType.OPERATOR);
                        if (Character.isLetterOrDigit(c)) {
                            pointer--;
                        }
                    } else {
                        builder.append('/');
                        builder.append(c);
                        getSpareInvalidSeq(builder);
                        addToken(builder.toString(), TokenType.INVALID_IDENTIFIER);
                    }
                } else {
                    addToken(tempC + "", TokenType.OPERATOR);
                }
            } else if (checkIfDelimiter(tempC)) {
                addToken(builder.toString(), TokenType.DELIMITER);
            } else if (tempC == '\'' || tempC == '"') {
                addToken(tempC + "", TokenType.DELIMITER);
                builder.setLength(0);
                while (++pointer < fileLength) {
                    char c = input[pointer];

                    if (c == '\\') {
                        builder.append(c);
                        builder.append(input[pointer + 1]);
                        pointer++;
                    } else if (c != tempC) {
                        builder.append(c);
                    }
                    // 字符串
                    else {
                        addToken(builder.toString(), TokenType.STRING_CONSTANTS);
                        addToken(c + "", TokenType.DELIMITER);
                        break;
                    }
                }
            }
            // 空字符不作处理
            else if (checkIfEmptyChar(tempC)) {

            } else {
                System.err.println("无法识别处理" + tempC);
            }
            pointer++;
        }

        return tokens;
    }

    private void addToken(String seq, TokenType type) {
        tokens.add(new Token(type, seq));
    }

    @Contract(pure = true)
    private boolean checkIfEmptyChar(char c) {
        return c == ' ' || c == '\n' || c == '\t';
    }

    @Contract(value = "null -> false",pure = true)
    private boolean checkIfKeyWord(String seq) {
        for (String temp : Types.KEY_WORD) {
            if (seq.equals(temp)) {
                return true;
            }
        }
        return false;
    }

    @Contract(pure = true)
    private boolean checkIfDelimiter(char c) {
        for (char temp : Types.DELIMITER) {
            if (temp == c) {
                return true;
            }
        }
        return false;
    }

    @Contract(value = "null -> false",pure = true)
    private boolean checkIfOperator(String str) {
        for (String temp : Types.OPERATOR) {
            if (str.equals(temp)) {
                return true;
            }
        }
        return false;
    }

    private void getSpareInvalidSeq(StringBuilder builder) {
        while (++pointer < fileLength && !checkIfEmptyChar(input[pointer])) {
            builder.append(input[pointer]);
        }
    }

    public void outputTokens(String filePath) {
        handle();
        try {
            FileIOHandler.saveFile(tokens.stream().map(Token::toString).collect(Collectors.toList()), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
