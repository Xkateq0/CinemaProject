
public class CMovie extends CBase {
    private String title;
    private String cast;
    private String genre;
    private int duration;

    public CMovie(String title,String cast,String genre,int duration){
        this.title=title;
        this.cast=cast;
        this.genre=genre;
        this.duration=duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setCast(String cast) {
        this.cast = cast;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getTitle() {
        return this.title;
    }
    public String getCast() {
        return this.cast;
    }
    public int getDuration() {
        return this.duration;
    }
    public String getGenre() {
        return this.genre;
    }


}
