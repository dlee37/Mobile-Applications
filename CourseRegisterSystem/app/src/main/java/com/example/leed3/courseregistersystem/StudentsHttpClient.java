package com.example.leed3.courseregistersystem;

/**
 * Created by leed3 on 4/9/2016.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class StudentsHttpClient implements Serializable {
    private static String BASE_URL = "http://www.cs.bc.edu/~signoril/students.json";

    public JSONObject getStudentData() {
        HttpURLConnection con = null;
        InputStream is = null;
        JSONObject jsonObject;
        try {
            String student = BASE_URL;
            jsonObject = JSONfunctions.getJSONfromURL(student);
            System.out.println(jsonObject.toString());
            return jsonObject;
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }
        return null;
    }
}