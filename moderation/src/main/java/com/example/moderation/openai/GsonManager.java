package com.example.moderation.openai;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GsonManager {
/**
Examples:

save object of class GameManager to file:
        
        String gameManagerJson = GsonManager.getInstance().toGson(this.gameManager);
        GsonManager.getInstance().putString(SP_KEY_GAME_MANAGER, gameManagerJson);

load object of class GameManager from file

         String gameManagerString = GsonManager.getInstance().getString(SP_KEY_GAME_MANAGER, "");
         if ( gameManagerString.equals("")) return;
         GameManager tempGM = GsonManager.getInstance().fromJson(gameManagerString, GameManager.class);
         // check for null values etc.
**/
    private static final String DB_FILE = "DB_FILE";

    private static GsonManager instance = null;
    private SharedPreferences preferences;



    private GsonManager(Context context){
        preferences = context.getSharedPreferences(DB_FILE,Context.MODE_PRIVATE);
    }

    public static void init(Context context){
        if (instance == null)
            instance = new GsonManager(context);
    }

    public static GsonManager getInstance() {

        return instance;
    }

    public void putInt(String key, int value) {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int defValue) {

        return preferences.getInt(key, defValue);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public String toGson(Object obj){
        Gson gson = new Gson();
        return new Gson().toJson(obj);
    }

    public <T> T fromJson(String key, Class<T> cls){
        return new Gson().fromJson(key, cls);
    }

    public <T> ArrayList<String> getFieldsOfObject(Object obj, Class<T> cs){
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        // Parse the JSON to get the fields
        T parsedObject = gson.fromJson(json, cs);
        Field[] fields = parsedObject.getClass().getDeclaredFields();
        ArrayList<String> result = new ArrayList<>();

        // Print the name and value of each field
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value;
            try {
                value = field.get(parsedObject);
            } catch (IllegalAccessException e) {
                value = null;
            }
            result.add(name);
            System.out.println(name + " = " + value);
        }
        return result;
    }

}
