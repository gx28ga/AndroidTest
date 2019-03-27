package com.axun.androidtest;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class UserInfoUtils {

    public static boolean saveInfo (Context context, String username, String password){
//        String path=context.getFilesDir().getPath();
        try{

            String result=username+"##"+password;
            FileOutputStream fos=context.openFileOutput("info.txt",0);
//            File file=new File(path,"info.txt");
//            FileOutputStream fos=new FileOutputStream(file);
            fos.write(result.getBytes());
            fos.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public static Map<String,String> readInfo(Context context){
        Map<String,String> map=new HashMap<>();



        String path=context.getFilesDir().getPath();
        try{
//            File file=new File(path,"info.txt");
            FileInputStream fis=context.openFileInput("info.txt");
            BufferedReader br=new BufferedReader(new InputStreamReader(fis));
            String info=br.readLine();
            String [] infoArr=info.split("##");
            map.put("username",infoArr[0]);
            map.put("password",infoArr[1]);
        }catch (Exception e){
            e.printStackTrace();

        }


        return map;

    }
}
