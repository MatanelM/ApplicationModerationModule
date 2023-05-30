package com.example.moderation.openai;

public interface HttpRequestable {

    void send(String text, Callback callback);

}
