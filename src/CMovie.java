
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
    @Override
    public String serialize() {
        return getId() + "," + title + "," + cast + "," + genre + "," + duration;
    }

    @Override
    public void deserialize(String data) {
        String[] fields = data.split(",");
        setId(Integer.parseInt(fields[0]));
        this.title = fields[1];
        this.cast=fields[2];
        this.genre = fields[3];
        this.duration = Integer.parseInt(fields[4]);
    }

}
