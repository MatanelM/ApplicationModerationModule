package com.example.moderation.openai;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import dagger.Module;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Module
public class Moderator implements HttpRequestable{
    /*

        in order to run this code, API_KEY should be not empty.
        Go to your api keys at https://platform.openai.com/account/api-keys and copy your key
        Then open your application's gradle file and write the following line:
        defaultConfig {
            ...
            buildConfigField("String", "OPENAI_API_KEY", "<OPENAI_API_KEY>")
        }

     */
    private String API_KEY = "";
    private Context context = null;

    // defines the amount of weight to each of the categories
    private Map<HarassmentType, Double> weights;
    private Double flaggingBar = 0.8;
    private static Moderator _instance = null;
    public Moderator(Context context)
    {
        this.weights = new HashMap<>();
        this.context = context;
    }

    public synchronized static Moderator get(Context context){
        if ( _instance == null ) {
            _instance = new Moderator(context);
        }
        return _instance;
    }

    public Moderator setFlaggingBar(double flagBar){
        this.flaggingBar = flagBar;
        return _instance;
    }

    public Moderator setOpenaiApiKey(String key){
        API_KEY = key;
        return _instance;
    }

    @Override
    public void send(String text, Callback callback){

        String apiUrl = "https://api.openai.com/v1/moderations";
        String endpoint = "";
        String param1 = "";
        String param2 = "";
        String urlParams = param1 + "&" + param2;

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String json = "{\"input\": \""+text+"\"}";
        RequestBody body = RequestBody.create(json, mediaType);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiUrl + endpoint)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        textView.setText("Error occurred");
                        callback.onFailure(e);
                        Log.d("Error", e.getMessage());
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result = response.body().string();
                            result = result.replace("[", "").replace("]", "");
                            com.example.moderation.openai.Response output = GsonManager.getInstance().fromJson(result
                                    , com.example.moderation.openai.Response.class);

                            applyWeights(output);
                            callback.onResponse(output);
                        }catch (Exception e){
                            callback.onFailure(e);
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

    }

    public Moderator addWeight(HarassmentType type, double weight)
    {
        weights.put(type, weight);
        return _instance;
    }

    private Moderator applyWeights(com.example.moderation.openai.Response response)
    {
        Map<String, Double> category_scores = response.getResults().getCategory_scores();
        weights.forEach((k, v) -> {
            switch(k)
            {
                case HATE: category_scores.put("hate", category_scores.get("hate")*v);break;
                case SEXUAL:category_scores.put("sexual", category_scores.get("sexual")*v);break;
                case VIOLENCE:category_scores.put("violence", category_scores.get("violence")*v);break;
                case SELF_HARM:category_scores.put("self-harm", category_scores.get("self-harm")*v);break;
                case SEXUAL_MINORS:category_scores.put("sexual/minors", category_scores.get("sexual/minors")*v);break;
                case HATE_THREATENING:category_scores.put("hate/threatening", category_scores.get("hate/threatening")*v);break;
                case VIOLENCE_GRAPHIC:category_scores.put("violence/graphic", category_scores.get("violence/graphic")*v);break;
                default:break;
            }
        });
        response.getResults().setCategory_scores(category_scores);
        com.example.moderation.openai.Response.ModerationCategory modCats = response.getResults().getCategories();
        category_scores.forEach((k, v) -> {
            if ( v > this.flaggingBar )
            {
                switch(k)
                {
                    case "hate": modCats.setHate(true);break;
                    case "sexual": modCats.setSexual(true);break;
                    case "violence": modCats.setViolence(true);break;
                    case "self-harm": modCats.setSelfHarm(true);break;
                    case "sexual/minors": modCats.setSexualMinors(true);break;
                    case "hate/threatening": modCats.setHateThreatening(true);break;
                    case "violence/graphic": modCats.setViolenceGraphic(true);break;
                    default:break;
                }
                response.getResults().setFlagged(true);
            }
        });
        response.getResults().setCategories(modCats);
        return _instance;
    }

    public boolean isFlagged(com.example.moderation.openai.Response response){
        return response.getResults().isFlagged();
    }

    public boolean isFlaggedByType(HarassmentType type, com.example.moderation.openai.Response response){
        boolean isFlagged = false;
        com.example.moderation.openai.Response.ModerationCategory cats = response.getResults().getCategories();
        switch(type)
        {
            case HATE: isFlagged = cats.isHate(); break;
            case SEXUAL:isFlagged = cats.isSexual();break;
            case VIOLENCE:isFlagged = cats.isViolence();break;
            case SELF_HARM:isFlagged = cats.isSelfHarm();break;
            case SEXUAL_MINORS:isFlagged = cats.isSexualMinors();break;
            case HATE_THREATENING:isFlagged = cats.isHateThreatening();break;
            case VIOLENCE_GRAPHIC:isFlagged = cats.isViolenceGraphic();break;
            default:break;
        }
        return isFlagged;
    }

    public Double getResultByType(HarassmentType type, com.example.moderation.openai.Response response){
        String key = type.getKey();
        return response.getResults().getCategoryScores().get(key);
    }

    public HarassmentType getTypeFlagged(com.example.moderation.openai.Response response){

        HarassmentType[] types = HarassmentType.values();

        for(int i = 0 ; i < types.length; i ++ ){

            String key = types[i].getKey();
            Double value = response.getResults().getCategoryScores().getOrDefault(key, 0.0);

            if (value >= flaggingBar) return types[i];
        }

        return null;
    }

}
