import java.util.ArrayList;
import java.util.List;

public class CReservation extends CBase{
    private int idShowing;
    private List<CTicket> tickets;

    public CReservation(int idShowing){
        this.idShowing = idShowing;
        this.tickets = new ArrayList<>();
    }

    public CReservation() {
        this.tickets = new ArrayList<>();
    }


    public List<CTicket> getTickets(){return this.tickets;}
    public void addTicket(CTicket ticket) {
        tickets.add(ticket);
    }

    public int getIdShowing() {
        return idShowing;
    }

    public void setIdShowing(int idShowing) {
        this.idShowing = idShowing;
    }

    public double calculateTotalPrice() {
        return tickets.stream().mapToDouble(CTicket::getPriceTicket).sum();
    }

    @Override
    public String serialize() {
       StringBuilder result = new StringBuilder();
       result.append(getId()).append(";").append(idShowing).append(";");

       for(CTicket ticket : tickets) {
           result.append(ticket.serialize()).append(";");
       }
       if(!tickets.isEmpty()) {
           result.deleteCharAt(result.length()-1);
       }
       return result.toString();
    }

    @Override
    public void deserialize(String data) {
        String[] fields = data.split(";");
        setId(Integer.parseInt(fields[0]));
        this.idShowing=Integer.parseInt(fields[1]);

        this.tickets = new ArrayList<>();
        for (int i = 2; i < fields.length; i++) { // Pola od indeksu 2 zawierają dane biletów
            if (!fields[i].isEmpty()) {
                CTicket ticket = new CTicket();
                ticket.deserialize(fields[i]); // Deserializacja biletu
                tickets.add(ticket); // Dodanie biletu do listy
            }
    }}
}
