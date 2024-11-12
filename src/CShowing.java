import java.time.LocalDate;
import java.time.LocalTime;


public class CShowing extends CBase {
  private LocalDate date;
  private LocalTime time;
  private int idMovie;
  private int idHall;

  public CShowing(LocalDate date, LocalTime time, int idMovie, int idHall) {
    this.date = date;
    this.time = time;
    this.idMovie = idMovie;
    this.idHall = idHall;
  }
  public LocalDate getDate() {return date;}
  public LocalTime getTime() {return time;}
  public int getIdMovie() {return idMovie;}
  public int getIdHall() {return idHall;}
  public void setDate(LocalDate date) {this.date = date;}
  public void setTime(LocalTime time) {this.time = time;}
  public void setIdMovie(int idMovie) {this.idMovie = idMovie;}
  public void setIdHall(int idHall) {this.idHall = idHall;}
}
