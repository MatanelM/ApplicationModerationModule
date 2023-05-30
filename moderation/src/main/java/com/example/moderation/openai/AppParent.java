package com.example.moderation.openai;

import android.app.Activity;
import android.app.Application;

public abstract class AppParent extends Application {

    protected abstract String getOpenaiApiKey();

    @Override
    public void onCreate() {
        super.onCreate();

        GsonManager.init(this);

        try{
            String api_key = getOpenaiApiKey();
            Moderator.get(new Activity())
                    .setOpenaiApiKey(api_key);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
