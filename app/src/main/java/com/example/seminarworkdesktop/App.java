package com.example.seminarworkdesktop;

import android.app.Application;

import com.example.moderation.openai.AppParent;
import com.example.moderation.openai.GsonManager;
import com.example.moderation.openai.HarassmentType;
import com.example.moderation.openai.Moderator;

public class App extends AppParent {

    @Override
    protected String getOpenaiApiKey() {
        return "";
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

}
