
## Get Started
This tutorial will guide you through the process of integrating and using the Moderator module in your Android application. By following these steps, you will be able to moderate user inputs and handle the responses accordingly.

## **Add dependencies**

Make sure you have added the necessary dependencies for the Moderator module in your app-level build.gradle file. Follow this simple [setup](setup.md) guide for instruction.


## **Initialize Moderator**
In your MainActivity class, import the necessary classes and inject the Moderator instance.


```
EditText editText;
Button button;
Moderator moderator;

@Inject
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    moderator = Moderator.get(this);

    editText = findViewById(R.id.editTextText);
    button = findViewById(R.id.button);
}
```

## **Implement Button Click Listener**

```
button.setOnClickListener(l -> {
    // given text
    String text = editText.getText().toString().trim();

    // send to a moderator 
    moderator.send(text, new Callback() {
        @Override
        public void onResponse(Response response) {
            if ( response.getResults().isFlagged() ){
                // the message is flagged as inappropriate
            }
            // send the message as the routine is finished
        }

        @Override
        public void onFailure(Exception error) {
            Log.e("Error", error.getMessage());
        }
    });
});
```
## **Customize Handling of Response**

In the onResponse() method of the Callback, you can customize the actions to be taken based on the moderation response. Handle the flagged message or proceed with the routine based on the moderation results.

By following these steps, you have successfully integrated the Moderator module into your application and implemented the logic to send user inputs for moderation. Customize the response handling as per your application's requirements.

Note: The Response object should be imported from moderation module, not to be confused with other modules.

## **Advanced Usage: Handling Extra Cases**

### **Modify the onResponse() method**

Inside the onResponse() method of the Callback, add the necessary logic to handle different cases based on the type of harassment and the severity of the violation.


```

moderator.send(text, new Callback() {
    @Override
    public void onResponse(Response response) {
        if ( response.getResults().isFlagged() ){
            // the message is flagged as inappropriate
        }else{
            HarassmentType violatedType = HarassmentType.HATE_THREATENING;

            if (violatedType == null) {
                return;
            }
            double violationScore = Moderator.get(getBaseContext()).getResultByType(violatedType, response);

            if (violationScore >= 0.3) {
                // Perform actions when the message is considered highly disturbing
                showMessage("This message is highly disturbing. Take appropriate action.");
            } else if (violationScore >= 0.2) {
                // Perform actions when the message should be warned
                showMessage("This message should be warned. Proceed with caution.");
            } else {
                // Perform actions when the message is considered legitimate
                showMessage("This message is legitimate. No action required.");
            }
        }
        // send the message as the routine is finished
    }

    @Override
    public void onFailure(Exception error) {
        Log.e("Error", error.getMessage());
    }
});

```

### **Customize the Actions**
Modify the code within each if block to include the specific actions you want to perform based on the severity of the violation. For example, you might display different alert messages, apply specific filters, or take appropriate actions based on the severity level.

## **Conclusion**
By incorporating the additional checks based on the type of harassment and the severity of the violation, you can handle different cases and take specific actions accordingly. This allows for more nuanced handling of messages based on the moderation results.
