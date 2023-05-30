## **Customization**

The module provides customization options to tailor the moderation process according to your specific requirements. You can modify the weights assigned to different harassment types, adjust the flagging bar threshold, and access specific results for each type.

To add weights to the harassment types, use the addWeight() function. You can set a multiplication factor to the given result, enabling you to track and flag specific types more effectively. Recommended weights can range from 1.5 for detecting minor harassments to higher values like 1000 for harder-to-detect harassments.

Additionally, you can set the flagging bar using the setFlaggingBar() function. The flag is raised when a response for a type exceeds the flagging bar threshold. By adjusting this threshold, you can control the tolerance for flagging messages. For example, setting the bar to 0.75 will result in a lower tolerance for flagging.

To retrieve specific type results, use the getResultByType() function, which allows you to obtain the result for a particular harassment type from the moderation response. You can also check if a specific type is flagged using the isFlaggedByType() function.

By utilizing these customization options, you can create a tailored moderator for your application messages, ensuring efficient and effective moderation.

Example:


```
// Set weights and flagging bar
moderator.addWeight(HarassmentType.HATE, 1.2)
         .addWeight(HarassmentType.SELF_HARM, 1.5)
         .setFlaggingBar(0.75);

// Button listener checking for hate content
button.setOnClickListener(l -> {
    String text = editText.getText().toString().trim();

    moderator.send(text, new Callback() {
        @Override
        public void onResponse(Response response) {
            if (moderator.isFlaggedByType(HarassmentType.HATE, response)) {
                // Perform actions for flagged hate content
            } else {
                // Perform actions for non-flagged content
            }
        }

        @Override
        public void onFailure(Exception error) {
            Log.e("Error", error.getMessage());
        }
    });
});
```

For more information on the moderation API and its capabilities, refer to the [OpenAI Moderation API documentation](https://platform.openai.com/docs/guides/moderation/quickstart).

With these customization features, you can fine-tune the moderation process and handle different types of harassment according to your application's needs.
