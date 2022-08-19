package codenerdteam.codenerdedu;

public class ModelVideo {
    String title;
    String videoUrl;
    String search;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public ModelVideo() {
    }

    public ModelVideo(String title, String videoUrl, String search) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.search = search;
    }
}
