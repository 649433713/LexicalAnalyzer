package lexical;

import java.io.*;
import java.util.List;

/**
 * @author yinywf
 * Created on 2017/11/3
 **/
public class FileIOHandler {

    public static char[] readFile(String filePath) {

        char[] fileBuffer = new char[100000];
        int num = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
            num = reader.read(fileBuffer);
            if(num == fileBuffer.length) {
                System.err.println("File too long");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        char[] fileContent = new char[num];
        System.arraycopy(fileBuffer, 0, fileContent, 0, num);
        return fileContent;
    }

    public static void saveFile(List<String> content, String filePath) throws IOException {
        File file = new File(filePath);
        FileWriter writer = new FileWriter(file, false);

        for (String line : content) {
            System.out.println(line);
            writer.write(line + "\n");
        }

        writer.flush();
        writer.close();
    }

}
