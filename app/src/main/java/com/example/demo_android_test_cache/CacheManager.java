package com.example.demo_android_test_cache;

import android.content.Context;
import android.util.Base64;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Anson on 2017/10/17.
 */

public class CacheManager {
    public enum Type {
        E_TAG, LAST_MODIFIED
    }

    private Context context;

    public CacheManager(Context context) {
        this.context = context;
    }

    public void put(String method, String url, Type type, String value){
        File file = new File(context.getCacheDir(), getEncodedFileName(method,url,type));
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(value);
            bw.close();
        } catch (IOException e) {
            System.out.println(">>>>>>>>>>>>>>>>" +e.getMessage());
        }
    }

    public String get(String method, String url, Type type) {
        File file = new File(context.getCacheDir(), getEncodedFileName(method,url,type));
        try {
            Scanner scanner = new Scanner(file);
            String value = scanner.useDelimiter("\\Z").next();
            scanner.close();
            return value;
        }catch (FileNotFoundException e){
            return "";
        }
    }

    public void cleanDir() {
        File[] files = context.getCacheDir().listFiles();
        for (File file : files) {
            file.delete();
        }
    }


    public long getDirSize() {
        long size = 0;
        File[] files = context.getCacheDir().listFiles();

        for (File file : files) {
            if (file.isFile()) {
                size += file.length();
            }
        }
        return size;
    }

    private String getEncodedFileName(String method, String url, Type type){
        String fileName = type.toString() + "^" + method + "^" + url;
        String encodedName = Base64.encodeToString(fileName.getBytes(),Base64.DEFAULT);
        return encodedName;
    }
}
