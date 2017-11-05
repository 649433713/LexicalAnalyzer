package lexical;

/**
 * @author yinywf
 * Created on 2017/11/4
 **/
public class Main {

    public static void main(String[] args) {
        LexAnalyzer lexAnalyzer1 = new LexAnalyzer(FileIOHandler.readFile("Main.txt"));
        lexAnalyzer1.outputTokens("ResultOfMain.txt");

        LexAnalyzer lexAnalyzer2 = new LexAnalyzer(FileIOHandler.readFile("LexAnalyzer.txt"));
        lexAnalyzer2.outputTokens("ResultOfLexAnalyzer.txt");
    }
}
