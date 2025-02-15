public class Payload {

    private int repositoryId;
    private int pushId;
    private int size;
    private int distinctSize;
    private String ref;
    private String head;
    private String before;
    private Commits commits;


    public Payload(int repositoryId, int pushId, int size, int distinctSize, String ref, String head, String before, Commits commits) {
        this.repositoryId = repositoryId;
        this.pushId = pushId;
        this.size = size;
        this.distinctSize = distinctSize;
        this.ref = ref;
        this.head = head;
        this.before = before;
        this.commits = commits;
    }

    public int getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(int repositoryId) {
        this.repositoryId = repositoryId;
    }

    public int getPushId() {
        return pushId;
    }

    public void setPushId(int pushId) {
        this.pushId = pushId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDistinctSize() {
        return distinctSize;
    }

    public void setDistinctSize(int distinctSize) {
        this.distinctSize = distinctSize;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public Commits getCommits() {
        return commits;
    }

    public void setCommits(Commits commits) {
        this.commits = commits;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "repositoryId=" + repositoryId +
                ", pushId=" + pushId +
                ", size=" + size +
                ", distinctSize=" + distinctSize +
                ", ref='" + ref + '\'' +
                ", head='" + head + '\'' +
                ", before='" + before + '\'' +
                ", commits=" + commits +
                '}';
    }
}
