
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

    public CMovie()
    {
        this.title="";
        this.cast="";
        this.genre="";
        this.duration=0;
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
        return getId() + ";" + title + ";" + cast + ";" + genre + ";" + duration;
    }

    public String toString() {
        return
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", cast='" + cast + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration ;
    }

    public void deserialize(String data) {
        // Rozbijamy dane na części (za pomocą przecinków)
        String[] parts = data.split(";");

        // Ustawiamy ID
        setId(Integer.parseInt(parts[0]));

        // Ustawiamy kolejne pola na podstawie rozdzielonych danych
        title = parts[1];
        cast = parts[2];
        genre = parts[3];
        duration = Integer.parseInt(parts[4]);
    }

}
