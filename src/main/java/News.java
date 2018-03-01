import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class News {
    @JsonProperty("content")
    private String content;

    @JsonProperty("headline")
    private String headline;

    @JsonProperty("date")
    private String date;

    @JsonProperty("rank")
    private String rank;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "News{" +
                "content='" + content + '\'' +
                ", headline='" + headline + '\'' +
                ", date='" + date + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }
}
