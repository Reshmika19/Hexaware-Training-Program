package entity;

public class Suggestion {
    private int suggestionId;
    private int userId;
    private String suggestionText;

    public Suggestion() {
    }
    public Suggestion(int suggestionId, int userId, String suggestionText) {
        this.suggestionId = suggestionId;
        this.userId = userId;
        this.suggestionText = suggestionText;
    }

    public int getSuggestionId() {
        return suggestionId;
    }
    public void setSuggestionId(int suggestionId) {
        this.suggestionId = suggestionId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getSuggestionText() {
        return suggestionText;
    }
    public void setSuggestionText(String suggestionText) {
        this.suggestionText = suggestionText;
    }

    @Override
    public String toString() {
        return "SuggestionId : " + suggestionId +
                ", UserId : " + userId +
                ", Suggestion : '" + suggestionText + '\'';
    }
}
