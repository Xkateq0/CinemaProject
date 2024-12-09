import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CShowing extends CBase {
  private LocalDate date;
  private LocalTime time;
  private int idMovie;
  private int idHall;
  private CSeat [] seats;

  public CShowing()
  {
    this.seats= new CSeat[55];
    for (int i = 0; i < 55; i++) {
      seats[i] = new CSeat();
      seats[i].setId(i + 1);}
  }

  public CShowing(LocalDate date, LocalTime time, int idMovie, int idHall) {
    this.date = date;
    this.time = time;
    this.idMovie = idMovie;
    this.idHall = idHall;
    this.seats = new CSeat[55];
    for (int i = 0; i < 55; i++) {
      seats[i] = new CSeat();
      seats[i].setId(i + 1);}

  }
  public LocalDate getDate() {
    return date;
  }
  public LocalTime getTime() {
    return time;
  }
  public int getIdMovie() {
    return idMovie;
  }
  public int getIdHall() {
    return idHall;
  }
  public void setDate(LocalDate date) {
    this.date = date;
  }
  public void setTime(LocalTime time) {
    this.time = time;
  }
  public void setIdMovie(int idMovie) {
    this.idMovie = idMovie;
  }
  public void setIdHall(int idHall) {
    this.idHall = idHall;
  }

  @Override
  public String serialize() {
    return getId() + ";" + date + ";" + time + ";" + idMovie + ";" + idHall;
  }
  @Override
  public String toString() {
    return "Showing {" +
            "Date=" + date +
            ", Time=" + time +
            ", Movie ID=" + idMovie +
            ", Hall ID=" + idHall +
            '}';
  }

  @Override
  public void deserialize(String data) {
    String[] fields = data.split(";");
    setId(Integer.parseInt(fields[0]));
    this.date = LocalDate.parse(fields[1]);
    this.time = LocalTime.parse(fields[2]);
    this.idMovie = Integer.parseInt(fields[3]);
    this.idHall = Integer.parseInt(fields[4]);
  }

  public String getMovieTitle(List<CMovie> allMovies) {
    for (CMovie movie : allMovies) {
      if (movie.getId() == this.idMovie) {
        return movie.getTitle();
      }
    }
    return "Unknown";
  }
}
