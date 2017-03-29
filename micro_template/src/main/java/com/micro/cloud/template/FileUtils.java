package com.micro.cloud.template;

import java.io.*;
import java.util.Map;

/**
 * Created by maybo on 2016/12/22.
 */
public class FileUtils {
    public static String filePath = "/usr/local/share/app.conf";

    public static void writeConf(String str) throws IOException {


        File file = new File(filePath);
        if (!file.exists()) {

            file.createNewFile();

        }
        FileWriter writer = new FileWriter(filePath);
        writer.write(str);
        writer.close();

    }

    public static void writeRun(String str) throws IOException {


        File file = new File("/usr/local/share/run.sh");
        if (!file.exists()) {

            file.createNewFile();
            FileWriter writer = new FileWriter("/usr/local/share/run.sh");
            writer.write(str);
            writer.close();
        }


    }

    public static String streamToString(InputStream in) throws IOException {
        byte[] buffer = new byte[2048];
        int readBytes = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while ((readBytes = in.read(buffer)) > 0) {
            stringBuilder.append(new String(buffer, 0, readBytes));
        }
        return stringBuilder.toString();
    }

    public synchronized static void writeObject(Object obj, String file) {
        if(!new File(file).exists()){
            try {
                new File(file).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            OutputStream out = new FileOutputStream(file);
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                objectOutputStream.writeObject(obj);
                objectOutputStream.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Object readObject(String file) {
       if(!new File(file).exists()){
           return null;
       }
        Object obj = null;
        try {
            InputStream in = new FileInputStream(file);
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(in);
                try {
                    obj = objectInputStream.readObject();
                    objectInputStream.close();
                    in.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    return obj;
}
}
