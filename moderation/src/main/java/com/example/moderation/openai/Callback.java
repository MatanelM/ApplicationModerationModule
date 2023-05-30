package com.example.moderation.openai;

import java.io.IOException;

public interface Callback  {

    void onResponse(Response response);

    void onFailure(Exception e);

}
