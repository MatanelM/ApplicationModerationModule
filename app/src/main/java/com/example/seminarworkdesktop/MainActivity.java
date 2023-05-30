package com.example.seminarworkdesktop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moderation.openai.Callback;
import com.example.moderation.openai.GsonManager;
import com.example.moderation.openai.HarassmentType;
import com.example.moderation.openai.HardcodedRequester;
import com.example.moderation.openai.HttpRequestable;
import com.example.moderation.openai.Moderator;
import com.example.moderation.openai.Response;
import com.example.toast_instyle.StyleToast;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    Button button;

    HttpRequestable moderator;

    private LinearLayout chatLayout;

    @Inject
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moderator = Moderator.get(this);

        findViews();
        initViews();
    }

    public void addMessageBox(String message){
        TextView textView = new TextView(MainActivity.this);
        textView.setText(message);
        textView.setBackground(ContextCompat.getDrawable(this, R.drawable.message_box));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textView.setBackgroundColor(Color.rgb( 152, 238, 204));
        textView.setTextColor(Color.rgb( 30, 30, 30));

        textView.setPadding(5, 7, 7, 6);
        lp.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(lp);

        chatLayout.addView(textView);
    }


    private void initViews() {

        button.setOnClickListener(l -> {
            // given text
            String text = editText.getText().toString().trim();

            // send for a check
            moderator.send(text, new Callback() {
                @Override
                public void onResponse(Response response) {
                    if ( response.getResults().isFlagged() ){
                        StyleToast.makeText(getBaseContext(), Toast.LENGTH_SHORT,StyleToast.ERROR, "This message is inappropriate",false).show();
                    }else {
                        // gets the type of harassment made
                        HarassmentType violatedType = Moderator.get(getBaseContext()).getTypeFlagged(response);

                        if (violatedType == null ) return;

                        // checks if the violation has exceeded some value
                        if ( Moderator.get(getBaseContext()).getResultByType(violatedType, response) >= 0.3){
                            // shows a warning matching the violation
                            StyleToast.makeText(getBaseContext(), Toast.LENGTH_SHORT,StyleToast.INFO, "This message is disturbing: "+violatedType.getPrettyName(),false).show();
                        }
                        else if ( Moderator.get(getBaseContext()).getResultByType(violatedType, response) >= 0.2){
                            // shows an info matching the message
                            StyleToast.makeText(getBaseContext(), Toast.LENGTH_SHORT,StyleToast.WARNING, "This message is not standard: "+violatedType.getPrettyName(),false).show();
                        }else{
                            // show a success message
                            StyleToast.makeText(getBaseContext(), Toast.LENGTH_SHORT,StyleToast.SUCCESS, "This message is ok: "+violatedType.getPrettyName(),false).show();
                        }
                    }
                    // send the message as the routine finished

                }

                @Override
                public void onFailure(Exception error) {
                    addMessageBox(error.getMessage());
                    Log.e("Error", error.getMessage());
                }
            });


            if(!text.isEmpty()) {
                addMessageBox(text);
                editText.setText("");
            }
        });

    }

    private void findViews() {
        chatLayout = findViewById(R.id.chat_layout);
        editText = findViewById(R.id.editTextText);
        button = findViewById(R.id.button);
    }


}