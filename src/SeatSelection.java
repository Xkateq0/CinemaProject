import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.swing.*;
public class SeatSelection extends JFrame {

    private CUser uzytkownik;
private final CShowing seans;
private JToggleButton[] seatButtons;
private Map<String, CTicket> ticketsMap = new HashMap<>();

    public SeatSelection(CShowing seans,CUser uzytkownik) {
        this.seans=seans;
        initComponents();
        seatButtons = new JToggleButton[] {
                seatA1, seatA2, seatA3, seatA4, seatA5, seatA6, seatA7, seatA8, seatA9, seatA10,seatA11, 
                seatB1, seatB2, seatB3, seatB4, seatB5, seatB6, seatB7, seatB8, seatB9,seatB10, seatB11, 
                seatC1, seatC2, seatC3, seatC4, seatC5, seatC6, seatC7, seatC8,seatC9, seatC10, seatC11, 
                seatD1, seatD2, seatD3, seatD4, seatD5, seatD6, seatD7, seatD8, seatD9, seatD10, seatD11, 
                seatE1, seatE2, seatE3, seatE4, seatE5, seatE6, seatE7, seatE8, seatE9, seatE10, seatE11
        };
        this.uzytkownik=uzytkownik;
        customComponents();
        updateSeats();
        jPanel2.setVisible(true);
        ticket_op.setVisible(false);
        ImageIcon icona = new ImageIcon(getClass().getResource("Image/sofa.png"));
        setIconImage(icona.getImage());
        setResizable(false);
        
    }

    private void customComponents()
    {
        CManage<CMovie> movieManager= new CManage<> (CMovie.class);
        CMovie movie = movieManager.getById(seans.getIdMovie());
        if(movie!=null)
        {titleTxt.setText(movie.getTitle());}
        dateTxt.setText(seans.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    + " " + seans.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        ImageIcon cover = new ImageIcon(Objects.requireNonNull(scaleImage(Objects.requireNonNull(movie).getImagePath(), 190, 270)));
        cover_l.setIcon(cover);
        confirmBtn.addActionListener(e -> finalizeReservation());

    }

    private Image scaleImage(String imagePath, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage();

            BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaledImage.createGraphics();

            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            g2d.drawImage(img, 0, 0, width, height, null);
            g2d.dispose();

            return scaledImage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Zaktualizuj stan siedzen na podstawie tabeli z CShowing
    private void updateSeats()
    {
        next_btn.setEnabled(false);
        CShowing.CSeat[] seats= seans.getSeats();
        for (int i = 0; i < seats.length && i < seatButtons.length; i++) {
            if(seats[i].isOccupied())
            {
                seatButtons[i].setEnabled(false);
                seatButtons[i].setBackground(new Color(99,99,99));
            }
            
        
        }
        
      for (JToggleButton button : seatButtons) {
        button.addActionListener(e -> {
            String seat = button.getText(); // Pobierz tekst przycisku (np. "A1")
            if (button.isSelected()) {
                // Dodaj bilet, jeśli przycisk został zaznaczony
                addTicket(seat, "STANDARD");
            } else {
                // Usuń bilet, jeśli przycisk został odkliknięty
                removeTicket(seat);
            }

            // Zaktualizuj stan przycisku "Dalej"
            updateNextButtonState();
        });
    }
}
    
    private boolean isAnySeatSelected() {
    for (JToggleButton button : seatButtons) {
        if (button.isSelected()) {
            return true;
        }
    }
    return false;
}
    
    private void updateNextButtonState() {
    next_btn.setEnabled(isAnySeatSelected());
}
//WYBOR BILETOW

private void addTicket(String seat, String ticketType) {
    // Tworzenie obiektu CTicket z typu ticketType
    TypeTicket type = TypeTicket.valueOf(ticketType.toUpperCase());
    CTicket ticket = new CTicket(type);
    ticket.setSeat(seat); // Ustawiamy numer miejsca dla biletu

    ticketsMap.put(seat, ticket);

    // Utwórz panel dla biletu
    JPanel ticketPanel = new JPanel();
    ticketPanel.setName(seat); // Ustawiamy nazwę panelu, żeby łatwo go znaleźć później
    ticketPanel.setLayout(new GridBagLayout());
    ticketPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    ticketPanel.setBackground(new Color(245, 245, 245));

    int panelWidth = 350;
    int panelHeight = 70;

    ticketPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
    ticketPanel.setMinimumSize(new Dimension(panelWidth, panelHeight));
    ticketPanel.setMaximumSize(new Dimension(panelWidth, panelHeight));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Ikona biletu
    JLabel iconLabel = new JLabel(new ImageIcon(getClass().getResource("Image/ticket.png")));
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridheight = 2;
    gbc.weightx = 0;
    ticketPanel.add(iconLabel, gbc);

    // Opis biletu
    JLabel ticketLabel = new JLabel("<html><b>" + ticket.getTypeTicket().name() + "</b><br>SALA: " + seans.getIdHall() + "<br>SEAT: " + seat + "</html>");
    ticketLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.gridheight = 1;
    gbc.weightx = 1;
    ticketPanel.add(ticketLabel, gbc);

    // Cena
    JLabel priceLabel = new JLabel(String.format("%.2f zł", ticket.getPriceTicket()));
    priceLabel.setFont(new Font("Arial", Font.BOLD, 12));
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.gridheight = 1;
    gbc.weightx = 1;
    ticketPanel.add(priceLabel, gbc);

    // Przyciski
    JButton changeTypeButton = new JButton("Zmień rodzaj");
    JButton removeButton = new JButton("X");
    removeButton.setForeground(Color.RED);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
    buttonPanel.setOpaque(false);
    buttonPanel.add(changeTypeButton);
    buttonPanel.add(removeButton);

    gbc.gridx = 2;
    gbc.gridy = 0;
    gbc.gridheight = 2;
    gbc.weightx = 0;
    ticketPanel.add(buttonPanel, gbc);

    // Obsługa zmiany typu biletu
    changeTypeButton.addActionListener(e -> {
        String[] options = {"STANDARD", "REDUCED", "STUDENT", "SENIOR"};
        String newType = (String) JOptionPane.showInputDialog(
                this,
                "Wybierz typ biletu:",
                "Zmiana typu biletu",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                ticket.getTypeTicket().name());

        if (newType != null) {
            ticket.setTypeTicket(TypeTicket.valueOf(newType));
            ticketLabel.setText("<html><b>" + ticket.getTypeTicket().name() + "</b><br>SALA: " + seans.getIdHall() + "<br>SEAT: " + seat + "</html>");
            priceLabel.setText(String.format("%.2f zł", ticket.getPriceTicket()));
            updateSummary(); // Odśwież podsumowanie
        }
    });

    removeButton.addActionListener(e -> {
        ticketsMap.remove(seat); // Usunięcie biletu z mapy
        ticketsPanel.remove(ticketPanel);
        ticketsPanel.revalidate();
        ticketsPanel.repaint();
        updateSummary(); // Odśwież podsumowanie
    });

    // Dodaj panel biletu do głównego kontenera
    ticketsPanel.add(ticketPanel);
    ticketsPanel.revalidate();
    ticketsPanel.repaint();

    updateSummary(); // Odśwież podsumowanie
}

private void updateSummary() {
    int ticketCount = ticketsMap.size();
    double totalPrice = ticketsMap.values().stream()
            .mapToDouble(CTicket::getPriceTicket)
            .sum();

    totalTicketsLabel.setText("Liczba biletów: " + ticketCount);
    totalPriceLabel.setText("Razem: " + String.format("%.2f zł", totalPrice));
    confirmBtn.setEnabled(ticketCount > 0); // Aktywuj przycisk tylko, gdy są bilety
}

private void removeTicket(String seat) {
        ticketsMap.remove(seat); // Usuwamy bilet z mapy

        for (Component component : ticketsPanel.getComponents()) {
            if (component instanceof JPanel && seat.equals(component.getName())) {
                ticketsPanel.remove(component); // Usuwamy panel z GUI
                ticketsPanel.revalidate();
                ticketsPanel.repaint();
                break;
            }
        }

        updateSummary(); // Odśwież podsumowanie
    }

    private void finalizeReservation() {
        // Tworzymy obiekt CReservation dla danego seansu
        CReservation reservation = new CReservation(seans.getId());

        // Dodajemy wszystkie wybrane bilety do rezerwacji
        for (Map.Entry<String, CTicket> entry : ticketsMap.entrySet()) {
            CTicket ticket = entry.getValue();
            reservation.addTicket(ticket);

            String seat = ticket.getSeat();
            for (int i = 0; i < seans.getSeats().length; i++) {
                if (seatButtons[i].getText().equals(seat)) {
                    seans.getSeats()[i].setOccupied(true);
                    break;
                }
            }
        }

        // Zapisujemy rezerwację w bazie danych
        CManage<CReservation> reservationManager = new CManage<>(CReservation.class);
        reservationManager.save(reservation);

        CManage<CShowing> showingManager = new CManage<>(CShowing.class);
        showingManager.save(seans);

        try {
            reservationManager.close(); // Zapisujemy zmiany w pliku
            showingManager.close();
            Logger.log(uzytkownik.getName()+ " dodał rezerwacje "+ reservation.getId());
            new resFrame(reservation).setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Wystąpił błąd podczas zapisywania rezerwacji.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // Informacja o sukcesie
        JOptionPane.showMessageDialog(this, "Rezerwacja została pomyślnie zapisana!", "Sukces", JOptionPane.INFORMATION_MESSAGE);

        // Zamykamy okno wyboru miejsc
        this.dispose();
    }

//    private void finalizeReservation() {
//        try {
//            cashier.finalizeReservation(seans, ticketsMap); // cashier to instancja CCashier
//            JOptionPane.showMessageDialog(this, "Rezerwacja została pomyślnie zapisana!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
//            this.dispose();
//        } catch (RuntimeException e) {
//            JOptionPane.showMessageDialog(this, e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
//        }
//    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        sideInfo = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        cover_l = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();
        titleTxt = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        dateTxt = new javax.swing.JLabel();
        ticket_op = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ticketScrlPane = new javax.swing.JScrollPane();
        ticketsPanel = new javax.swing.JPanel();
        confirmBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();
        totalTicketsLabel = new javax.swing.JLabel();
        totalPriceLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        seatsPanel = new javax.swing.JPanel();
        seatA1 = new javax.swing.JToggleButton();
        seatA2 = new javax.swing.JToggleButton();
        seatA3 = new javax.swing.JToggleButton();
        seatA4 = new javax.swing.JToggleButton();
        seatA5 = new javax.swing.JToggleButton();
        seatA6 = new javax.swing.JToggleButton();
        seatA7 = new javax.swing.JToggleButton();
        seatA9 = new javax.swing.JToggleButton();
        seatA8 = new javax.swing.JToggleButton();
        seatA10 = new javax.swing.JToggleButton();
        seatA11 = new javax.swing.JToggleButton();
        seatB1 = new javax.swing.JToggleButton();
        seatB2 = new javax.swing.JToggleButton();
        seatB3 = new javax.swing.JToggleButton();
        seatB4 = new javax.swing.JToggleButton();
        seatB6 = new javax.swing.JToggleButton();
        seatB5 = new javax.swing.JToggleButton();
        seatB7 = new javax.swing.JToggleButton();
        seatB9 = new javax.swing.JToggleButton();
        seatB8 = new javax.swing.JToggleButton();
        seatB10 = new javax.swing.JToggleButton();
        seatB11 = new javax.swing.JToggleButton();
        seatC1 = new javax.swing.JToggleButton();
        seatC2 = new javax.swing.JToggleButton();
        seatC3 = new javax.swing.JToggleButton();
        seatC4 = new javax.swing.JToggleButton();
        seatC5 = new javax.swing.JToggleButton();
        seatC6 = new javax.swing.JToggleButton();
        seatC7 = new javax.swing.JToggleButton();
        seatC8 = new javax.swing.JToggleButton();
        seatC9 = new javax.swing.JToggleButton();
        seatC10 = new javax.swing.JToggleButton();
        seatC11 = new javax.swing.JToggleButton();
        seatD1 = new javax.swing.JToggleButton();
        seatD2 = new javax.swing.JToggleButton();
        seatD3 = new javax.swing.JToggleButton();
        seatD4 = new javax.swing.JToggleButton();
        seatD5 = new javax.swing.JToggleButton();
        seatD6 = new javax.swing.JToggleButton();
        seatD7 = new javax.swing.JToggleButton();
        seatD8 = new javax.swing.JToggleButton();
        seatD9 = new javax.swing.JToggleButton();
        seatD10 = new javax.swing.JToggleButton();
        seatD11 = new javax.swing.JToggleButton();
        seatE1 = new javax.swing.JToggleButton();
        seatE2 = new javax.swing.JToggleButton();
        seatE3 = new javax.swing.JToggleButton();
        seatE4 = new javax.swing.JToggleButton();
        seatE5 = new javax.swing.JToggleButton();
        seatE6 = new javax.swing.JToggleButton();
        seatE7 = new javax.swing.JToggleButton();
        seatE8 = new javax.swing.JToggleButton();
        seatE9 = new javax.swing.JToggleButton();
        seatE10 = new javax.swing.JToggleButton();
        seatE11 = new javax.swing.JToggleButton();
        seatNumber1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        seatNumber2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        next_btn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        bg.setBackground(new java.awt.Color(255, 255, 255));

        sideInfo.setBackground(new java.awt.Color(75, 0, 130));

        jPanel1.setBackground(new java.awt.Color(74, 85, 199));

        cover_l.setBackground(new java.awt.Color(204, 204, 255));
        cover_l.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cover_l, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cover_l, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("TITLE");

        titleTxt.setForeground(new java.awt.Color(255, 255, 255));
        titleTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleTxt.setText("jLabel3");
        titleTxt.setFont(new java.awt.Font("Segoe UI", 1, 16));

        dateLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        dateLabel.setForeground(new java.awt.Color(255, 255, 255));
        dateLabel.setText("DATE");

        dateTxt.setForeground(new java.awt.Color(255, 255, 255));
        dateTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateTxt.setText("jLabel3");
        dateTxt.setFont(new java.awt.Font("Segoe UI", 1, 16));

        javax.swing.GroupLayout sideInfoLayout = new javax.swing.GroupLayout(sideInfo);
        sideInfo.setLayout(sideInfoLayout);
        sideInfoLayout.setHorizontalGroup(
            sideInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideInfoLayout.createSequentialGroup()
                .addGroup(sideInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(sideInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(sideInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(sideInfoLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(dateLabel)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(sideInfoLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(sideInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(titleLabel)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 17, Short.MAX_VALUE)))
                .addContainerGap())
        );
        sideInfoLayout.setVerticalGroup(
            sideInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideInfoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118)
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titleTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ticket_op.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("WYBÓR BILETÓW");

        ticketScrlPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ticketScrlPane.setToolTipText("");

        ticketsPanel.setLayout(new javax.swing.BoxLayout(ticketsPanel, javax.swing.BoxLayout.Y_AXIS));
        ticketScrlPane.setViewportView(ticketsPanel);

        confirmBtn.setText("ZATWIERDŹ");

        backBtn.setText("WRÓĆ");
        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backBtnMouseClicked(evt);
            }
        });

        totalTicketsLabel.setText("0");

        totalPriceLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        totalPriceLabel.setText("0,00 zł");

        javax.swing.GroupLayout ticket_opLayout = new javax.swing.GroupLayout(ticket_op);
        ticket_op.setLayout(ticket_opLayout);
        ticket_opLayout.setHorizontalGroup(
            ticket_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ticket_opLayout.createSequentialGroup()
                .addContainerGap(541, Short.MAX_VALUE)
                .addGroup(ticket_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ticket_opLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(388, 388, 388))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ticket_opLayout.createSequentialGroup()
                        .addGroup(ticket_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(confirmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(440, 440, 440))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ticket_opLayout.createSequentialGroup()
                        .addGroup(ticket_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(totalTicketsLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                            .addComponent(totalPriceLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
                        .addGap(475, 475, 475))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ticket_opLayout.createSequentialGroup()
                        .addComponent(ticketScrlPane, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(326, 326, 326))))
        );
        ticket_opLayout.setVerticalGroup(
            ticket_opLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ticket_opLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(ticketScrlPane, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalTicketsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalPriceLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(confirmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        seatsPanel.setBackground(new java.awt.Color(255, 255, 255));

        seatA1.setBackground(new java.awt.Color(186, 216, 153));
        seatA1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatA1.setForeground(new java.awt.Color(186, 216, 153));
        seatA1.setText("A1");
        seatA1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatA1ActionPerformed(evt);
            }
        });

        seatA2.setBackground(new java.awt.Color(186, 216, 153));
        seatA2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatA2.setForeground(new java.awt.Color(186, 216, 153));
        seatA2.setText("A2");
        seatA2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatA2ActionPerformed(evt);
            }
        });

        seatA3.setBackground(new java.awt.Color(186, 216, 153));
        seatA3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatA3.setForeground(new java.awt.Color(186, 216, 153));
        seatA3.setText("A3");
        seatA3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatA3ActionPerformed(evt);
            }
        });

        seatA4.setBackground(new java.awt.Color(186, 216, 153));
        seatA4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatA4.setForeground(new java.awt.Color(186, 216, 153));
        seatA4.setText("A4");
        seatA4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatA4ActionPerformed(evt);
            }
        });

        seatA5.setBackground(new java.awt.Color(186, 216, 153));
        seatA5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatA5.setForeground(new java.awt.Color(186, 216, 153));
        seatA5.setText("A5");
        seatA5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatA5ActionPerformed(evt);
            }
        });

        seatA6.setBackground(new java.awt.Color(186, 216, 153));
        seatA6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatA6.setForeground(new java.awt.Color(186, 216, 153));
        seatA6.setText("A6");
        seatA6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatA6ActionPerformed(evt);
            }
        });

        seatA7.setBackground(new java.awt.Color(186, 216, 153));
        seatA7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatA7.setForeground(new java.awt.Color(186, 216, 153));
        seatA7.setText("A7");
        seatA7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatA7ActionPerformed(evt);
            }
        });

        seatA9.setBackground(new java.awt.Color(186, 216, 153));
        seatA9.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatA9.setForeground(new java.awt.Color(186, 216, 153));
        seatA9.setText("A9");
        seatA9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatA9ActionPerformed(evt);
            }
        });

        seatA8.setBackground(new java.awt.Color(186, 216, 153));
        seatA8.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatA8.setForeground(new java.awt.Color(186, 216, 153));
        seatA8.setText("A8");
        seatA8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatA8ActionPerformed(evt);
            }
        });

        seatA10.setBackground(new java.awt.Color(186, 216, 153));
        seatA10.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatA10.setForeground(new java.awt.Color(186, 216, 153));
        seatA10.setText("A10");
        seatA10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatA10ActionPerformed(evt);
            }
        });

        seatA11.setBackground(new java.awt.Color(186, 216, 153));
        seatA11.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatA11.setForeground(new java.awt.Color(186, 216, 153));
        seatA11.setText("A11");
        seatA11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatA11ActionPerformed(evt);
            }
        });

        seatB1.setBackground(new java.awt.Color(186, 216, 153));
        seatB1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatB1.setForeground(new java.awt.Color(186, 216, 153));
        seatB1.setText("B1");
        seatB1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatB1ActionPerformed(evt);
            }
        });

        seatB2.setBackground(new java.awt.Color(186, 216, 153));
        seatB2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatB2.setForeground(new java.awt.Color(186, 216, 153));
        seatB2.setText("B2");
        seatB2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatB2ActionPerformed(evt);
            }
        });

        seatB3.setBackground(new java.awt.Color(186, 216, 153));
        seatB3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatB3.setForeground(new java.awt.Color(186, 216, 153));
        seatB3.setText("B3");
        seatB3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatB3ActionPerformed(evt);
            }
        });

        seatB4.setBackground(new java.awt.Color(186, 216, 153));
        seatB4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatB4.setForeground(new java.awt.Color(186, 216, 153));
        seatB4.setText("B4");
        seatB4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatB4ActionPerformed(evt);
            }
        });

        seatB6.setBackground(new java.awt.Color(186, 216, 153));
        seatB6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatB6.setForeground(new java.awt.Color(186, 216, 153));
        seatB6.setText("B6");
        seatB6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatB6ActionPerformed(evt);
            }
        });

        seatB5.setBackground(new java.awt.Color(186, 216, 153));
        seatB5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatB5.setForeground(new java.awt.Color(186, 216, 153));
        seatB5.setText("B5");
        seatB5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatB5ActionPerformed(evt);
            }
        });

        seatB7.setBackground(new java.awt.Color(186, 216, 153));
        seatB7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatB7.setForeground(new java.awt.Color(186, 216, 153));
        seatB7.setText("B7");
        seatB7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatB7ActionPerformed(evt);
            }
        });

        seatB9.setBackground(new java.awt.Color(186, 216, 153));
        seatB9.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatB9.setForeground(new java.awt.Color(186, 216, 153));
        seatB9.setText("B9");
        seatB9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatB9ActionPerformed(evt);
            }
        });

        seatB8.setBackground(new java.awt.Color(186, 216, 153));
        seatB8.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatB8.setForeground(new java.awt.Color(186, 216, 153));
        seatB8.setText("B8");
        seatB8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatB8ActionPerformed(evt);
            }
        });

        seatB10.setBackground(new java.awt.Color(186, 216, 153));
        seatB10.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatB10.setForeground(new java.awt.Color(186, 216, 153));
        seatB10.setText("B10");
        seatB10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatB10ActionPerformed(evt);
            }
        });

        seatB11.setBackground(new java.awt.Color(186, 216, 153));
        seatB11.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatB11.setForeground(new java.awt.Color(186, 216, 153));
        seatB11.setText("B11");
        seatB11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatB11ActionPerformed(evt);
            }
        });

        seatC1.setBackground(new java.awt.Color(186, 216, 153));
        seatC1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatC1.setForeground(new java.awt.Color(186, 216, 153));
        seatC1.setText("C1");
        seatC1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatC1ActionPerformed(evt);
            }
        });

        seatC2.setBackground(new java.awt.Color(186, 216, 153));
        seatC2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatC2.setForeground(new java.awt.Color(186, 216, 153));
        seatC2.setText("C2");
        seatC2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatC2ActionPerformed(evt);
            }
        });

        seatC3.setBackground(new java.awt.Color(186, 216, 153));
        seatC3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatC3.setForeground(new java.awt.Color(186, 216, 153));
        seatC3.setText("C3");
        seatC3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatC3ActionPerformed(evt);
            }
        });

        seatC4.setBackground(new java.awt.Color(186, 216, 153));
        seatC4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatC4.setForeground(new java.awt.Color(186, 216, 153));
        seatC4.setText("C4");
        seatC4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatC4ActionPerformed(evt);
            }
        });

        seatC5.setBackground(new java.awt.Color(186, 216, 153));
        seatC5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatC5.setForeground(new java.awt.Color(186, 216, 153));
        seatC5.setText("C5");
        seatC5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatC5ActionPerformed(evt);
            }
        });

        seatC6.setBackground(new java.awt.Color(186, 216, 153));
        seatC6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatC6.setForeground(new java.awt.Color(186, 216, 153));
        seatC6.setText("C6");
        seatC6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatC6ActionPerformed(evt);
            }
        });

        seatC7.setBackground(new java.awt.Color(186, 216, 153));
        seatC7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatC7.setForeground(new java.awt.Color(186, 216, 153));
        seatC7.setText("C7");
        seatC7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatC7ActionPerformed(evt);
            }
        });

        seatC8.setBackground(new java.awt.Color(186, 216, 153));
        seatC8.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatC8.setForeground(new java.awt.Color(186, 216, 153));
        seatC8.setText("C8");
        seatC8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatC8ActionPerformed(evt);
            }
        });

        seatC9.setBackground(new java.awt.Color(186, 216, 153));
        seatC9.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatC9.setForeground(new java.awt.Color(186, 216, 153));
        seatC9.setText("C9");
        seatC9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatC9ActionPerformed(evt);
            }
        });

        seatC10.setBackground(new java.awt.Color(186, 216, 153));
        seatC10.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatC10.setForeground(new java.awt.Color(186, 216, 153));
        seatC10.setText("C10");
        seatC10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatC10ActionPerformed(evt);
            }
        });

        seatC11.setBackground(new java.awt.Color(186, 216, 153));
        seatC11.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatC11.setForeground(new java.awt.Color(186, 216, 153));
        seatC11.setText("C11");
        seatC11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatC11ActionPerformed(evt);
            }
        });

        seatD1.setBackground(new java.awt.Color(186, 216, 153));
        seatD1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatD1.setForeground(new java.awt.Color(186, 216, 153));
        seatD1.setText("D1");
        seatD1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatD1ActionPerformed(evt);
            }
        });

        seatD2.setBackground(new java.awt.Color(186, 216, 153));
        seatD2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatD2.setForeground(new java.awt.Color(186, 216, 153));
        seatD2.setText("D2");
        seatD2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatD2ActionPerformed(evt);
            }
        });

        seatD3.setBackground(new java.awt.Color(186, 216, 153));
        seatD3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatD3.setForeground(new java.awt.Color(186, 216, 153));
        seatD3.setText("D3");
        seatD3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatD3ActionPerformed(evt);
            }
        });

        seatD4.setBackground(new java.awt.Color(186, 216, 153));
        seatD4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatD4.setForeground(new java.awt.Color(186, 216, 153));
        seatD4.setText("D4");
        seatD4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatD4ActionPerformed(evt);
            }
        });

        seatD5.setBackground(new java.awt.Color(186, 216, 153));
        seatD5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatD5.setForeground(new java.awt.Color(186, 216, 153));
        seatD5.setText("D5");
        seatD5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatD5ActionPerformed(evt);
            }
        });

        seatD6.setBackground(new java.awt.Color(186, 216, 153));
        seatD6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatD6.setForeground(new java.awt.Color(186, 216, 153));
        seatD6.setText("D6");
        seatD6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatD6ActionPerformed(evt);
            }
        });

        seatD7.setBackground(new java.awt.Color(186, 216, 153));
        seatD7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatD7.setForeground(new java.awt.Color(186, 216, 153));
        seatD7.setText("D7");
        seatD7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatD7ActionPerformed(evt);
            }
        });

        seatD8.setBackground(new java.awt.Color(186, 216, 153));
        seatD8.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatD8.setForeground(new java.awt.Color(186, 216, 153));
        seatD8.setText("D8");
        seatD8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatD8ActionPerformed(evt);
            }
        });

        seatD9.setBackground(new java.awt.Color(186, 216, 153));
        seatD9.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatD9.setForeground(new java.awt.Color(186, 216, 153));
        seatD9.setText("D9");
        seatD9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatD9ActionPerformed(evt);
            }
        });

        seatD10.setBackground(new java.awt.Color(186, 216, 153));
        seatD10.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatD10.setForeground(new java.awt.Color(186, 216, 153));
        seatD10.setText("D10");
        seatD10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatD10ActionPerformed(evt);
            }
        });

        seatD11.setBackground(new java.awt.Color(186, 216, 153));
        seatD11.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatD11.setForeground(new java.awt.Color(186, 216, 153));
        seatD11.setText("D11");
        seatD11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatD11ActionPerformed(evt);
            }
        });

        seatE1.setBackground(new java.awt.Color(186, 216, 153));
        seatE1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatE1.setForeground(new java.awt.Color(186, 216, 153));
        seatE1.setText("E1");
        seatE1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatE1ActionPerformed(evt);
            }
        });

        seatE2.setBackground(new java.awt.Color(186, 216, 153));
        seatE2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatE2.setForeground(new java.awt.Color(186, 216, 153));
        seatE2.setText("E2");
        seatE2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatE2ActionPerformed(evt);
            }
        });

        seatE3.setBackground(new java.awt.Color(186, 216, 153));
        seatE3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatE3.setForeground(new java.awt.Color(186, 216, 153));
        seatE3.setText("E3");
        seatE3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatE3ActionPerformed(evt);
            }
        });

        seatE4.setBackground(new java.awt.Color(186, 216, 153));
        seatE4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatE4.setForeground(new java.awt.Color(186, 216, 153));
        seatE4.setText("E4");
        seatE4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatE4ActionPerformed(evt);
            }
        });

        seatE5.setBackground(new java.awt.Color(186, 216, 153));
        seatE5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatE5.setForeground(new java.awt.Color(186, 216, 153));
        seatE5.setText("E5");
        seatE5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatE5ActionPerformed(evt);
            }
        });

        seatE6.setBackground(new java.awt.Color(186, 216, 153));
        seatE6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatE6.setForeground(new java.awt.Color(186, 216, 153));
        seatE6.setText("E6");
        seatE6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatE6ActionPerformed(evt);
            }
        });

        seatE7.setBackground(new java.awt.Color(186, 216, 153));
        seatE7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatE7.setForeground(new java.awt.Color(186, 216, 153));
        seatE7.setText("E7");
        seatE7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatE7ActionPerformed(evt);
            }
        });

        seatE8.setBackground(new java.awt.Color(186, 216, 153));
        seatE8.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatE8.setForeground(new java.awt.Color(186, 216, 153));
        seatE8.setText("E8");
        seatE8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatE8ActionPerformed(evt);
            }
        });

        seatE9.setBackground(new java.awt.Color(186, 216, 153));
        seatE9.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatE9.setForeground(new java.awt.Color(186, 216, 153));
        seatE9.setText("E9");
        seatE9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatE9ActionPerformed(evt);
            }
        });

        seatE10.setBackground(new java.awt.Color(186, 216, 153));
        seatE10.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatE10.setForeground(new java.awt.Color(186, 216, 153));
        seatE10.setText("E10");
        seatE10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatE10ActionPerformed(evt);
            }
        });

        seatE11.setBackground(new java.awt.Color(186, 216, 153));
        seatE11.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        seatE11.setForeground(new java.awt.Color(186, 216, 153));
        seatE11.setText("E11");
        seatE11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seatE11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout seatsPanelLayout = new javax.swing.GroupLayout(seatsPanel);
        seatsPanel.setLayout(seatsPanelLayout);
        seatsPanelLayout.setHorizontalGroup(
            seatsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seatsPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(seatsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(seatsPanelLayout.createSequentialGroup()
                        .addComponent(seatA1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatA2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatA3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatA4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatA5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatA6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatA7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatA8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatA9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatA10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatA11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(seatsPanelLayout.createSequentialGroup()
                        .addComponent(seatB1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatB2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatB3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatB4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatB5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatB6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatB7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatB8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatB9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatB10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatB11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(seatsPanelLayout.createSequentialGroup()
                        .addComponent(seatC1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatC2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatC3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatC4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatC5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatC6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatC7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatC8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatC9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatC10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatC11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(seatsPanelLayout.createSequentialGroup()
                        .addComponent(seatD1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatD2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatD3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatD4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatD5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatD6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatD7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatD8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatD9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatD10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatD11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(seatsPanelLayout.createSequentialGroup()
                        .addComponent(seatE1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatE2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatE3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatE4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatE5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatE6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatE7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatE8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatE9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatE10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(seatE11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        seatsPanelLayout.setVerticalGroup(
            seatsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seatsPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(seatsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(seatA1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatA3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatA2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatA4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatA6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatA5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatA7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatA9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatA8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatA11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatA10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(seatsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(seatB1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatB3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatB2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatB4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatB6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatB5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatB7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatB9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatB8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatB11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatB10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(seatsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(seatC1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatC3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatC2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatC4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatC6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatC5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatC7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatC9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatC8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatC11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatC10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(seatsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(seatD1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatD3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatD2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatD4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatD6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatD5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatD7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatD9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatD8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatD11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatD10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(seatsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(seatE1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatE3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatE2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatE4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatE6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatE5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatE7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatE9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatE8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatE11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatE10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("1");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("2");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("4");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("3");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("5");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("6");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("7");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("8");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("9");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("10");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("11");

        javax.swing.GroupLayout seatNumber1Layout = new javax.swing.GroupLayout(seatNumber1);
        seatNumber1.setLayout(seatNumber1Layout);
        seatNumber1Layout.setHorizontalGroup(
            seatNumber1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seatNumber1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );
        seatNumber1Layout.setVerticalGroup(
            seatNumber1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seatNumber1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(seatNumber1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel7)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addContainerGap())
        );

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("B");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("A");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("D");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("C");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("E");

        javax.swing.GroupLayout seatNumber2Layout = new javax.swing.GroupLayout(seatNumber2);
        seatNumber2.setLayout(seatNumber2Layout);
        seatNumber2Layout.setHorizontalGroup(
            seatNumber2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seatNumber2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(seatNumber2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(seatNumber2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        seatNumber2Layout.setVerticalGroup(
            seatNumber2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seatNumber2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel15)
                .addGap(38, 38, 38)
                .addComponent(jLabel14)
                .addGap(47, 47, 47)
                .addComponent(jLabel17)
                .addGap(48, 48, 48)
                .addComponent(jLabel16)
                .addGap(50, 50, 50)
                .addComponent(jLabel18)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(seatNumber2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(seatNumber1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(seatsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(seatNumber1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(seatNumber2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addComponent(seatsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/ekran1.png"))); // NOI18N

        next_btn.setBackground(new java.awt.Color(72, 61, 139));
        next_btn.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        next_btn.setForeground(new java.awt.Color(255, 255, 255));
        next_btn.setText("DALEJ");
        next_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                next_btnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(139, 139, 139)
                        .addComponent(next_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(next_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel19)
                        .addGap(10, 10, 10)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addComponent(sideInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(ticket_op, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(bgLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(ticket_op, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void seatA1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatA1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatA1ActionPerformed

    private void seatA3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatA3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatA3ActionPerformed

    private void seatA2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatA2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatA2ActionPerformed

    private void seatA4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatA4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatA4ActionPerformed

    private void seatA6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatA6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatA6ActionPerformed

    private void seatA5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatA5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatA5ActionPerformed

    private void seatA7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatA7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatA7ActionPerformed

    private void seatA9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatA9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatA9ActionPerformed

    private void seatA8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatA8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatA8ActionPerformed

    private void seatA10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatA10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatA10ActionPerformed

    private void seatA11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatA11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatA11ActionPerformed

    private void seatB1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatB1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatB1ActionPerformed

    private void seatB3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatB3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatB3ActionPerformed

    private void seatB2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatB2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatB2ActionPerformed

    private void seatB4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatB4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatB4ActionPerformed

    private void seatB6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatB6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatB6ActionPerformed

    private void seatB5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatB5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatB5ActionPerformed

    private void seatB7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatB7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatB7ActionPerformed

    private void seatB9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatB9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatB9ActionPerformed

    private void seatB8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatB8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatB8ActionPerformed

    private void seatB10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatB10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatB10ActionPerformed

    private void seatB11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatB11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatB11ActionPerformed

    private void seatC3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatC3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatC3ActionPerformed

    private void seatC2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatC2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatC2ActionPerformed

    private void seatC6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatC6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatC6ActionPerformed

    private void seatC5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatC5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatC5ActionPerformed

    private void seatC4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatC4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatC4ActionPerformed

    private void seatC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatC1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatC1ActionPerformed

    private void seatC9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatC9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatC9ActionPerformed

    private void seatC8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatC8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatC8ActionPerformed

    private void seatC7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatC7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatC7ActionPerformed

    private void seatC10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatC10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatC10ActionPerformed

    private void seatC11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatC11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatC11ActionPerformed

    private void seatD9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatD9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatD9ActionPerformed

    private void seatD5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatD5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatD5ActionPerformed

    private void seatD11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatD11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatD11ActionPerformed

    private void seatD2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatD2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatD2ActionPerformed

    private void seatD3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatD3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatD3ActionPerformed

    private void seatD1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatD1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatD1ActionPerformed

    private void seatD8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatD8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatD8ActionPerformed

    private void seatD10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatD10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatD10ActionPerformed

    private void seatD6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatD6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatD6ActionPerformed

    private void seatD4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatD4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatD4ActionPerformed

    private void seatD7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatD7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatD7ActionPerformed

    private void seatE1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatE1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatE1ActionPerformed

    private void seatE8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatE8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatE8ActionPerformed

    private void seatE6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatE6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatE6ActionPerformed

    private void seatE9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatE9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatE9ActionPerformed

    private void seatE4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatE4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatE4ActionPerformed

    private void seatE5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatE5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatE5ActionPerformed

    private void seatE2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatE2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatE2ActionPerformed

    private void seatE11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatE11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatE11ActionPerformed

    private void seatE10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatE10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatE10ActionPerformed

    private void seatE3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatE3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatE3ActionPerformed

    private void seatE7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seatE7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seatE7ActionPerformed

    private void next_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_next_btnMouseClicked
        ticket_op.setVisible(true);
        jPanel2.setVisible(false);
    }//GEN-LAST:event_next_btnMouseClicked

    private void backBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backBtnMouseClicked
        jPanel2.setVisible(true);
        ticket_op.setVisible(false);
    }//GEN-LAST:event_backBtnMouseClicked

    /**
     * @param args the command line arguments
     */
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JPanel bg;
    private javax.swing.JButton confirmBtn;
    private javax.swing.JLabel cover_l;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel dateTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton next_btn;
    private javax.swing.JToggleButton seatA1;
    private javax.swing.JToggleButton seatA10;
    private javax.swing.JToggleButton seatA11;
    private javax.swing.JToggleButton seatA2;
    private javax.swing.JToggleButton seatA3;
    private javax.swing.JToggleButton seatA4;
    private javax.swing.JToggleButton seatA5;
    private javax.swing.JToggleButton seatA6;
    private javax.swing.JToggleButton seatA7;
    private javax.swing.JToggleButton seatA8;
    private javax.swing.JToggleButton seatA9;
    private javax.swing.JToggleButton seatB1;
    private javax.swing.JToggleButton seatB10;
    private javax.swing.JToggleButton seatB11;
    private javax.swing.JToggleButton seatB2;
    private javax.swing.JToggleButton seatB3;
    private javax.swing.JToggleButton seatB4;
    private javax.swing.JToggleButton seatB5;
    private javax.swing.JToggleButton seatB6;
    private javax.swing.JToggleButton seatB7;
    private javax.swing.JToggleButton seatB8;
    private javax.swing.JToggleButton seatB9;
    private javax.swing.JToggleButton seatC1;
    private javax.swing.JToggleButton seatC10;
    private javax.swing.JToggleButton seatC11;
    private javax.swing.JToggleButton seatC2;
    private javax.swing.JToggleButton seatC3;
    private javax.swing.JToggleButton seatC4;
    private javax.swing.JToggleButton seatC5;
    private javax.swing.JToggleButton seatC6;
    private javax.swing.JToggleButton seatC7;
    private javax.swing.JToggleButton seatC8;
    private javax.swing.JToggleButton seatC9;
    private javax.swing.JToggleButton seatD1;
    private javax.swing.JToggleButton seatD10;
    private javax.swing.JToggleButton seatD11;
    private javax.swing.JToggleButton seatD2;
    private javax.swing.JToggleButton seatD3;
    private javax.swing.JToggleButton seatD4;
    private javax.swing.JToggleButton seatD5;
    private javax.swing.JToggleButton seatD6;
    private javax.swing.JToggleButton seatD7;
    private javax.swing.JToggleButton seatD8;
    private javax.swing.JToggleButton seatD9;
    private javax.swing.JToggleButton seatE1;
    private javax.swing.JToggleButton seatE10;
    private javax.swing.JToggleButton seatE11;
    private javax.swing.JToggleButton seatE2;
    private javax.swing.JToggleButton seatE3;
    private javax.swing.JToggleButton seatE4;
    private javax.swing.JToggleButton seatE5;
    private javax.swing.JToggleButton seatE6;
    private javax.swing.JToggleButton seatE7;
    private javax.swing.JToggleButton seatE8;
    private javax.swing.JToggleButton seatE9;
    private javax.swing.JPanel seatNumber1;
    private javax.swing.JPanel seatNumber2;
    private javax.swing.JPanel seatsPanel;
    private javax.swing.JPanel sideInfo;
    private javax.swing.JScrollPane ticketScrlPane;
    private javax.swing.JPanel ticket_op;
    private javax.swing.JPanel ticketsPanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel titleTxt;
    private javax.swing.JLabel totalPriceLabel;
    private javax.swing.JLabel totalTicketsLabel;
    // End of variables declaration//GEN-END:variables
}
