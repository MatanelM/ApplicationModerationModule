package com.example.moderation.openai;

import java.util.Map;

public class Response {
    private String id;
    private String model;

    private Results results;


    public Response() {

    }

    @Override
    public String toString() {
        return "Response{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", reponse='"+ results + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public Results getResults(){return results;}

    public static class Results{
        private ModerationCategory categories;
        private Map<String, Double> category_scores;
        private boolean flagged;

        public Results() {
        }

        @Override
        public String toString() {
            return "Results{" +
                    "categories=" + categories +
                    ", category_scores=" + category_scores +
                    ", flagged=" + flagged +
                    '}';
        }

        public Results(ModerationCategory categories, Map<String, Double> categoryScores, boolean flagged) {
            this.categories = categories;
            this.category_scores = categoryScores;
            this.flagged = flagged;
        }

        public ModerationCategory getCategories() {
            return categories;
        }

        public Map<String, Double> getCategoryScores() {
            return category_scores;
        }

        public boolean isFlagged() {
            return flagged;
        }

        public void setCategories(ModerationCategory categories) {
            this.categories = categories;
        }

        public Map<String, Double> getCategory_scores() {
            return category_scores;
        }

        public void setCategory_scores(Map<String, Double> category_scores) {
            this.category_scores = category_scores;
        }

        public void setFlagged(boolean flagged) {
            this.flagged = flagged;
        }
    }

    public static class ModerationCategory {
        private boolean hate;
        private boolean hateThreatening;
        private boolean selfHarm;
        private boolean sexual;
        private boolean sexualMinors;
        private boolean violence;
        private boolean violenceGraphic;

        public ModerationCategory(){}

        public ModerationCategory(boolean hate, boolean hateThreatening, boolean selfHarm,
                                  boolean sexual, boolean sexualMinors, boolean violence,
                                  boolean violenceGraphic) {
            this.hate = hate;
            this.hateThreatening = hateThreatening;
            this.selfHarm = selfHarm;
            this.sexual = sexual;
            this.sexualMinors = sexualMinors;
            this.violence = violence;
            this.violenceGraphic = violenceGraphic;
        }

        @Override
        public String toString() {
            return "ModerationCategory{" +
                    "hate=" + hate +
                    ", hateThreatening=" + hateThreatening +
                    ", selfHarm=" + selfHarm +
                    ", sexual=" + sexual +
                    ", sexualMinors=" + sexualMinors +
                    ", violence=" + violence +
                    ", violenceGraphic=" + violenceGraphic +
                    '}';
        }

        public boolean isHate() {
            return hate;
        }

        public boolean isHateThreatening() {
            return hateThreatening;
        }

        public boolean isSelfHarm() {
            return selfHarm;
        }

        public boolean isSexual() {
            return sexual;
        }

        public boolean isSexualMinors() {
            return sexualMinors;
        }

        public boolean isViolence() {
            return violence;
        }

        public boolean isViolenceGraphic() {
            return violenceGraphic;
        }

        public void setHate(boolean hate) {
            this.hate = hate;
        }

        public void setHateThreatening(boolean hateThreatening) {
            this.hateThreatening = hateThreatening;
        }

        public void setSelfHarm(boolean selfHarm) {
            this.selfHarm = selfHarm;
        }

        public void setSexual(boolean sexual) {
            this.sexual = sexual;
        }

        public void setSexualMinors(boolean sexualMinors) {
            this.sexualMinors = sexualMinors;
        }

        public void setViolence(boolean violence) {
            this.violence = violence;
        }

        public void setViolenceGraphic(boolean violenceGraphic) {
            this.violenceGraphic = violenceGraphic;
        }
    }

}
