
import java. awt. AlphaComposite;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;


public class resFrame extends JFrame {
    private final CReservation reservation;

    public resFrame(CReservation reservation) {
        initComponents();
        this.reservation = reservation;

        customComponents();
        menuOp();
    }

    private void menuOp() {
        saveM.addActionListener(e -> takeScreenshot());
        printM.addActionListener(e->sendPrint());
    }

    public void takeScreenshot() {
        try {
            // Tworzenie obrazu BufferedImage o rozmiarze komponentu
            BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            paint(img.getGraphics());

            // Upewnienie się, że folder istnieje
            File directory = new File("src/Image/Reservation");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generowanie unikalnej nazwy pliku
            String filename = "src/Image/Reservation/reservation" + System.currentTimeMillis() + ".png";
            File outputfile = new File(filename);

            // Zapis obrazu do pliku PNG
            ImageIO.write(img, "png", outputfile);

            // Informacja dla użytkownika
            JOptionPane.showMessageDialog(this, "Zrzut ekranu zapisany: " + filename);

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Wystąpił błąd podczas zapisywania zrzutu ekranu.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void sendPrint() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("Print Ticket");

        printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) {
                return Printable.NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) graphics;
            double leftMargin = 50;  // Margines z lewej strony
            double topMargin = 50;   // Margines z góry

            // Przesunięcie początkowe o topMargin, żeby obciąć 10 jednostek z góry
            g2d.translate(pageFormat.getImageableX() + leftMargin, pageFormat.getImageableY() + topMargin);

            // Obliczanie skali
            double scaleX = pageFormat.getImageableWidth() / getWidth();
            double scaleY = pageFormat.getImageableHeight() / getHeight();
            double scale = Math.min(scaleX, scaleY) / 2;
            g2d.scale(scale, scale);

            // Tylko część interfejsu do wydrukowania - np. bilety
            bg.paint(g2d);  // Rysowanie tylko panelu z biletami

            return Printable.PAGE_EXISTS;
        });

        if (printerJob.printDialog()) {
            try {
                printerJob.print();
            } catch (PrinterException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Wystąpił błąd podczas drukowania.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    private void customComponents()
    {
      // Podstawowe informacje
    resLabel.setText("REZERWACJA : " +  String.valueOf(reservation.getId()));
    CShowing seans = new CShowing();

    CManage<CShowing> showingManager = new CManage<>(CShowing.class);
    CManage<CMovie> movieManager = new CManage<>(CMovie.class);
    List<CMovie> allMovies = movieManager.getAll();
    seans = showingManager.getById(reservation.getIdShowing());

    if(seans != null) {
        titleLabel.setText(seans.getMovieTitle(allMovies));
        dateLabel.setText(seans.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) +
                " | " + seans.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        hallLabel.setText("Sala:" + seans.getIdHall());
    }

    // Bilety
    ticketsPanel.removeAll(); // Czyszczenie panelu
    ticketsPanel.setLayout(new BoxLayout(ticketsPanel, BoxLayout.Y_AXIS));  // BoxLayout w pionie

    for(CTicket ticket : reservation.getTickets()) {
        JPanel ticketPanel = new JPanel();
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
        JLabel ticketLabel = new JLabel("<html><b>" + ticket.getTypeTicket().name() + "<br>SEAT: " + ticket.getSeat() + "</html>");
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

        // Dodaj panel biletu do głównego kontenera
        ticketsPanel.add(ticketPanel);
    }

    // Odśwież panel biletów
    ticketsPanel.revalidate();
    ticketsPanel.repaint();

    // Podsumowanie
    tpriceLabel.setText(String.valueOf(reservation.calculateTotalPrice()));

    // Dopasowanie wysokości JFrame do zawartości
    this.pack(); // Automatycznie dopasowuje rozmiar okna
}

    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new JPanel();
        allPanel = new JPanel();
        infoPanel = new JPanel();
        titleLabel = new JLabel();
        dateLabel = new JLabel();
        hallLabel = new JLabel();
        resLabel = new JLabel();
        ticketsPanel = new JPanel();
        sumPanel = new JPanel();
        tpriceLabel = new JLabel();
        jLabel3 = new JLabel();
        jMenuBar1 = new JMenuBar();
        jMenu1 = new JMenu();
        saveM = new JMenuItem();
        printM = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new Color(255, 255, 255));

        allPanel.setLayout(new BorderLayout());

        infoPanel.setBackground(new Color(102, 0, 102));
        infoPanel.setForeground(new Color(255, 255, 255));

        titleLabel.setFont(new Font("Segoe UI", 0, 16)); // NOI18N
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("1999-12-12 | 16:37");

        dateLabel.setFont(new Font("Segoe UI", 1, 16)); // NOI18N
        dateLabel.setForeground(new Color(255, 255, 255));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dateLabel.setText("Title");

        hallLabel.setFont(new Font("Segoe UI", 0, 16)); // NOI18N
        hallLabel.setForeground(new Color(255, 255, 255));
        hallLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hallLabel.setText("Sala: 1");

        resLabel.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
        resLabel.setForeground(new Color(255, 255, 255));
        resLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resLabel.setText("REZERWACJA: 00");
        resLabel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));

        GroupLayout infoPanelLayout = new GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(resLabel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(dateLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hallLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, infoPanelLayout.createSequentialGroup()
                .addComponent(resLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titleLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hallLabel)
                .addGap(19, 19, 19))
        );

        allPanel.add(infoPanel, BorderLayout.NORTH);

        ticketsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        ticketsPanel.setAutoscrolls(true);
        ticketsPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        ticketsPanel.setLayout(new BoxLayout(ticketsPanel, BoxLayout.LINE_AXIS));
        allPanel.add(ticketsPanel, BorderLayout.CENTER);
        ticketsPanel.getAccessibleContext().setAccessibleDescription("");

        sumPanel.setBackground(new Color(143, 68, 143));

        tpriceLabel.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        tpriceLabel.setForeground(new Color(255, 255, 255));
        tpriceLabel.setText("00,00 zl");

        jLabel3.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new Color(255, 255, 255));
        jLabel3.setText("Razem: ");

        GroupLayout sumPanelLayout = new GroupLayout(sumPanel);
        sumPanel.setLayout(sumPanelLayout);
        sumPanelLayout.setHorizontalGroup(
            sumPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, sumPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tpriceLabel, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
        );
        sumPanelLayout.setVerticalGroup(
            sumPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(sumPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sumPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(tpriceLabel, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jLabel3))
                .addContainerGap())
        );

        allPanel.add(sumPanel, BorderLayout.PAGE_END);

        GroupLayout bgLayout = new GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(allPanel, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(allPanel, GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
        );

        getContentPane().add(bg, BorderLayout.CENTER);

        jMenu1.setText("File");

        saveM.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        saveM.setText("Save");
        jMenu1.add(saveM);

        printM.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        printM.setText("Print");
        jMenu1.add(printM);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel allPanel;
    private JPanel bg;
    private JLabel dateLabel;
    private JLabel hallLabel;
    private JPanel infoPanel;
    private JLabel jLabel3;
    private JMenu jMenu1;
    private JMenuBar jMenuBar1;
    private JMenuItem printM;
    private JLabel resLabel;
    private JMenuItem saveM;
    private JPanel sumPanel;
    private JPanel ticketsPanel;
    private JLabel titleLabel;
    private JLabel tpriceLabel;
    // End of variables declaration//GEN-END:variables
}
