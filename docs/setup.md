# Setting up the module

Welcome to the module setup guide! This resource's puprpose is to provide step-by-step instructions to quickly implement the module into your project. Enhance functionality, improve user experience, and streamline workflows with ease. For the latest module release, please visit [github.com](https://github.com/MatanelM/AndroidApplicationModeration).

## **Setting up with gradle**

Add the module to your project next to the application folder, and add the following line of code to the grade.build file.

```
dependencies {

    implementation project(':moderation')
    ...

}
```

## **Initialization**

### **Initialization from Application**

Create an App class that extends AppParent.

```
public class App extends AppParent {
    // Class implementation goes here
}
```

Implement the function to return the API key.

```
public class App extends AppParent {
    // Other class methods and configurations

    /**
     * Retrieves the API key.
     *
     * @return The API key.
     */
    public static String getApiKey() {
        // TODO: Implement the logic to retrieve the API key
        return "YOUR_API_KEY";
    }
}
```

Replace "YOUR_API_KEY" with the actual implementation to fetch the API key dynamically.


### **Initialization from MainActivity**

In your MainActivity class, import the necessary classes. Inside the onCreate() method of MainActivity, initialize the module and retrieve the API key.


```
Moderator moderator;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Other initialization code

    // Initialize the module
    moderator = Moderator.get(this);

    // Retrieve the API key from the App class
    String apiKey = "YOUR_API_KEY";

    // Set the API key in the module
    moderator.setOpenaiApiKey(apiKey);

}

```

### **Setting the API key from build config**

Setting the api key from a BuildConfig is a good practice to ensure a more secured credentials. 
To set the API key from a BuildConfig class, follow these steps:

<ol>
<li>Open your module's build.gradle file.</li>
<li>Inside the android block, add a buildConfigField to define the API key as a build configuration field.</li>

```
android {
    defaultConfig {
        ...
        buildConfigField("String", "API_KEY", "\"YOUR_API_KEY\"")
    }
}
```
<li>Replace "YOUR_API_KEY" with your actual API key. Note that the API key should be a string value enclosed in double quotes.</li>
<li>Sync your project to apply the changes.</li>
<li>In your App class or any other relevant class, access the API key from the BuildConfig class.</li>

```
String apiKey = BuildConfig.API_KEY;
```
</ol>

Remember to update the value of the API key in your build.gradle file based on your specific requirements and how you handle API key storage and retrieval.