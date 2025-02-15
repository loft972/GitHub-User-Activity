public class Commits {

    private String sha;
    private Author author;
    private String message;
    private boolean distinct;
    private String url;

    public Commits(String sha, Author author, String message, boolean distinct, String url) {
        this.sha = sha;
        this.author = author;
        this.message = message;
        this.distinct = distinct;
        this.url = url;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Commits{" +
                "sha='" + sha + '\'' +
                ", author=" + author +
                ", message='" + message + '\'' +
                ", distinct=" + distinct +
                ", url='" + url + '\'' +
                '}';
    }
}
