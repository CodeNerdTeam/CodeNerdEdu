package codenerdteam.codenerdedu.model;

public class CourseModel {
    private int image;
    private String title;
    private String totalLesson;

    public CourseModel() {}

    public CourseModel(int image, String title, String totalLesson) {
        this.image = image;
        this.title = title;
        this.totalLesson = totalLesson;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotalLesson() {
        return totalLesson;
    }

    public void setTotalLesson(String totalLesson) {
        this.totalLesson = totalLesson;
    }
}
