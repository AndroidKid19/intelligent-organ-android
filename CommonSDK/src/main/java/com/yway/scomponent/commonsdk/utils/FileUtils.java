package com.yway.scomponent.commonsdk.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Yuan on 2018/9/20.
 */

public class FileUtils {

    public static String encodeToString(String path) throws FileNotFoundException {
        //获取手机中的图片
        FileInputStream fis = new FileInputStream(path);
        Bitmap bitmap  = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //清空画图缓存否则下次获取图片时还是原图片
        if (null != bitmap) {
            //对图片进行压缩，100为不压缩，并写入字节流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        }
        //获取图片的二进制
        byte[] compress_head_photo = bos.toByteArray();
        try {
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //对二进制数组进行编码
        String result =  Base64.encodeToString(compress_head_photo, Base64.NO_WRAP);
        return result;
    }

    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try{
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data,Base64.NO_CLOSE);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    public static String uri2FilePath(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {

            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
}
