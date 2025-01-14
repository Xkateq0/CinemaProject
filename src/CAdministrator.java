import java.time.LocalDate;
import java.time.LocalTime;

public class CAdministrator extends CUser {
    public CAdministrator(String username, String password) {
        super(username, password);
    }
    @Override
    public void displayOptions() {
        System.out.println("1. Add a movie");
        System.out.println("2. Set movie data");
        System.out.println("3. Set movie times");
        System.out.println("4. Assign halls to a movie");
    }

    public CAdministrator() {};

    public void setMovie(CMovie movie,String title,String cast,int duration,String genre){
        movie.setTitle(title);
        movie.setCast(cast);
        movie.setDuration(duration);
        movie.setGenre(genre);
    }
    public void setShowing(CShowing show, LocalDate date, LocalTime time, int idMovie, int idHall ){
        show.setDate(date);
        show.setTime(time);
        show.setIdMovie(idMovie);
        show.setIdHall(idHall);
    }
    @Override
    public String getName()
    {
        return "Administrator";
    }



}
