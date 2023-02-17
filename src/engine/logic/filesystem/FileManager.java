package engine.logic.filesystem;

import java.io.*;
import java.util.Arrays;

public class FileManager {

    public String readRawFile(String filename) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return stringBuilder.toString();
     }

    public void writeRawFile(String filename, String text) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            writer.write(text);

            writer.flush();
            writer.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Object readObjFile(String filename) {
        Object val = null;

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename));
            val = inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return val;
    }

    public void writeObjFile(String filename, Object obj) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename));
            outputStream.writeObject(obj);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeEngineFile(String filename, EngineFile engineFile) {
        StringBuilder strFile = new StringBuilder();
        strFile.append(engineFile.numbers.length + " " + engineFile.decimals.length + " " + engineFile.texts.length + "\n");
        for (Integer integer : engineFile.numbers) {
            strFile.append(integer + " ");
        }
        strFile.append("\n");
        for (Float decimal : engineFile.decimals) {
            strFile.append(decimal + " ");
        }
        strFile.append("\n");
        for (String text : engineFile.texts) {
            strFile.append(text + " ");
        }

        writeRawFile(filename, strFile.toString());
    }

    public EngineFile readEngineFile(String filename) {
        String contents = readRawFile(filename);
        String[] flines = contents.split("\n");
        String[] lines = new String[4];
        int ni = 0;
        for (String fline : flines) {
            lines[ni] = fline;
            ni += 1;
        }
        String[] headerData = lines[0].split(" ");
        String[] strNumbers = lines[1].split(" ");
        String[] strDecimals = lines[2].split(" ");
        String[] texts = lines[3].split(" ");

        EngineFile engineFile = new EngineFile(Integer.parseInt(headerData[0]), Integer.parseInt(headerData[1]), Integer.parseInt(headerData[2]));



        return null;
    }
}
