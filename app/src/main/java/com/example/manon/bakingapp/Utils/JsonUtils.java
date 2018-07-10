package com.example.manon.bakingapp.Utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.manon.bakingapp.R;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {
    // read json file from asset folder
    public static String loadJsonFromAsset(Context context, String filename){
        String json = null;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream input = assetManager.open(filename);
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
