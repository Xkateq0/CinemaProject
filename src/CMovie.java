
public class CMovie extends CBase {
    private String imagePath ;
    private String title;
    private String movieDescription;
    private String cast;
    private String genre;
    private int duration;

    public CMovie(String title,String cast,String genre,int duration, String imagePath ,String movieDescription){
        this.imagePath = imagePath;
        this.title=title;
        this.movieDescription=movieDescription;
        this.cast=cast;
        this.genre=genre;
        this.duration=duration;
    }

    public CMovie()
    {
        this.imagePath="";
        this.title="";
        this.movieDescription="";
        this.cast="";
        this.genre="";
        this.duration=0;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
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
    public String getImagePath() {
        return this.imagePath;
    }
    public String getTitle() {
        return this.title;
    }

    public String getMovieDescription() {
        return movieDescription;
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
        // Return serialized string with all fields separated by semicolons
        return getId() + ";" + imagePath + ";" + title + ";" + cast + ";" + genre + ";" + duration + ";" + movieDescription;
    }

    // ToString method to display object info
    @Override
    public String toString() {
        return "id=" + getId() +
                ", image=" + imagePath + '\'' +
                ", title='" + title + '\'' +
                ", cast='" + cast + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", description='" + movieDescription + '\'';
    }

    // Deserialize method - wczytuje dane z postaci tekstowej
    public void deserialize(String data) {
        // Rozdzielamy dane na części, oddzielone średnikami
        String[] parts = data.split(";");

        // Ustawiamy ID
        setId(Integer.parseInt(parts[0]));

        // Ustawiamy pozostałe pola
        imagePath = parts[1];
        title = parts[2];
        movieDescription= parts[3];
        cast = parts[4];
        genre = parts[5];
        duration = Integer.parseInt(parts[6]);
    }
}
