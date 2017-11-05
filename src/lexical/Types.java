package lexical;

/**
 * @author yinywf
 * Created on 2017/11/4
 **/
public class Types {

    public static String[] KEY_WORD = {
            "abstract", "continue", "for", "new" , "switch" ,
            "assert" , "default" , "goto" , "package" , "synchronized",
            "boolean" , "do" , "if" , "private" , "this",
            "break" , "double" , "implements" , "protected" , "throw",
            "byte" , "else" , "import" , "public" , "throws",
            "case" , "enum" , "instanceof" , "return" , "transient",
            "catch" , "extends" , "int" , "short" , "try",
            "char" ,"final" , "interface" , "static" , "void",
            "class" , "finally" , "long" , "strictfp" , "volatile",
            "const" , "float" , "native" , "super", "while"
    };

    public static char[] DELIMITER = {
            '%','(',')','{','}','!',';',',','.','[','[','?',':','~','^','[',']'
    };

    public static String[] OPERATOR = {
            "+","+=","-","-=","*","*=","/","/=","=","==","&","|","&&","||","!","!="
            ,"<","<=",">",">=","++" , "--","|","&",
    };
}
