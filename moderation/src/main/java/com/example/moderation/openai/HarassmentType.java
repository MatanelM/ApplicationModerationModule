package com.example.moderation.openai;

public enum HarassmentType {

    HATE("Hate", "hate"),
    HATE_THREATENING("Threatening Hate", "hate/threatening"),
    SELF_HARM("Self-Harm", "self-harm"),
    SEXUAL("Sexual", "sexual"),
    SEXUAL_MINORS("Sexual Minors", "sexual/minors"),
    VIOLENCE("Violence", "violence"),
    VIOLENCE_GRAPHIC("Graphic Violence", "violence/graphic");

    private String prettyName;
    private String key;

    HarassmentType(String prettyName, String key) {
        this.prettyName = prettyName;
        this.key = key;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public String getKey() {
        return key;
    }

}
