public class Actor {

    private Long id;
    private String login;
    private String displayLogin;
    private String gravatarId;
    private String url;
    private String avatarUrl;

    public Actor(Long id, String login, String displayLogin, String gravatarId, String url, String avatarUrl) {
        this.id = id;
        this.login = login;
        this.displayLogin = displayLogin;
        this.gravatarId = gravatarId;
        this.url = url;
        this.avatarUrl = avatarUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDisplayLogin() {
        return displayLogin;
    }

    public void setDisplayLogin(String displayLogin) {
        this.displayLogin = displayLogin;
    }

    public String getGravatarId() {
        return gravatarId;
    }

    public void setGravatarId(String gravatarId) {
        this.gravatarId = gravatarId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", displayLogin='" + displayLogin + '\'' +
                ", gravatarId='" + gravatarId + '\'' +
                ", url='" + url + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
