package com.example.moderation.openai;

public class HardcodedRequester implements HttpRequestable{


    @Override
    public void send(String text, com.example.moderation.openai.Callback callback) {
        String json = "{\"id\":\"modr-XXXXX\",\"model\":\"text-moderation-001\",\"results\":[{\"categories\":{\"hate\":false,\"hate/threatening\":false,\"self-harm\":false,\"sexual\":false,\"sexual/minors\":false,\"violence\":false,\"violence/graphic\":false},\"category_scores\":{\"hate\":0.18805529177188873,\"hate/threatening\":0.0001250059431185946,\"self-harm\":0.0003706029092427343,\"sexual\":0.0008735615410842001,\"sexual/minors\":0.0007470346172340214,\"violence\":0.0041268812492489815,\"violence/graphic\":0.00023186142789199948},\"flagged\":false}]}";

        json = json.replace("[", "").replace("]", "");
        Response output = GsonManager.getInstance().fromJson(json, Response.class);


        callback.onResponse(output);
        System.out.println();
    }

}
