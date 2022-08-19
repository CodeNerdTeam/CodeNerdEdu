package codenerdteam.codenerdedu.model;

public class ModelVideo {
    public String title, videoUrl;

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

    public ModelVideo() {
    }

    public ModelVideo(String title, String videoUrl) {
        this.title = title;
        this.videoUrl = videoUrl;
    }
}
