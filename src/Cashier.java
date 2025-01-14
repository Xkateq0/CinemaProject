import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import javax.swing.text.MaskFormatter;
/**
 *
 * @author yande
 */
public class Cashier extends JFrame {

    private  final CManage<CMovie> movieManager;
    private final List<CMovie> allMovies;
    private final CManage<CShowing> showingMenager;
    private final List<CShowing> allShowing;
    private  CManage<CReservation> reservationManager;
    private List<CReservation> allReservation;
    private CUser uzytkownik;
    public Cashier(CUser uzytkownik) {
        initComponents();    
     //Wczytanie baz danych
        movieManager= new CManage<> (CMovie.class);
        allMovies =movieManager.getAll();
        showingMenager = new CManage<> (CShowing.class);
        allShowing =showingMenager.getAll();
        reservationManager=new CManage<>(CReservation.class);
        allReservation=reservationManager.getAll();
        this.uzytkownik=uzytkownik;


        
     customComponents();
        
    }
    
    private void customComponents()
    {
        setExtendedState(Cashier.MAXIMIZED_BOTH);
        setTitle("Katana - Panel kasjera");
        ImageIcon icona = new ImageIcon(Objects.requireNonNull(getClass().getResource("Image/katana.png")));
        setIconImage(icona.getImage());
        
         logL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new Login().setVisible(true);
                JOptionPane.showMessageDialog(null, "Wylogowano");
            }
        });
        updateTable(jTable1,allMovies);
        updateTable2(jTable2,allShowing, allMovies);
        updateTable3(jTable3, allMovies,allReservation);
        
         try {
        MaskFormatter dateFormatter = new MaskFormatter("####-##-##");
        dateFormatter.setPlaceholderCharacter(' ');
        dateSearch.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(dateFormatter));
    } catch (ParseException e) {
        e.printStackTrace();
    }
    }
    
// Tabela z repertuarami
public void updateTable(JTable jTable1, List<CMovie> allMovies) {
    DefaultTableModel model = new DefaultTableModel(new String[]{" ", "Tytuł", "Obsada", "Opis", "Gatunek"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Blokuje edycję wszystkich komórek
        }
    };
    jTable1.setModel(model);
    jTable1.setFont(new Font("Arial", Font.PLAIN, 18));
    jTable1.getTableHeader().setReorderingAllowed(false);

    model.setRowCount(0);

    for (CMovie movie : allMovies) {
        Object[] row = new Object[]{
                new ImageIcon(Objects.requireNonNull(scaleImage(movie.getImagePath(), 190, 270))),
                movie.getTitle(),
                movie.getCast(),
                movie.getMovieDescription(),
                movie.getGenre()
        };

        model.addRow(row);
    }

    // Renderer dla obrazów

    jTable1.getColumnModel().getColumn(0).setCellRenderer((table, value, isSelected, _, _, _) -> {
        if (value instanceof ImageIcon) {
            JLabel label = new JLabel((ImageIcon) value);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            if (isSelected) {
                label.setBackground(table.getSelectionBackground());
                label.setOpaque(true);
            }
            return label;
        }
        return new JLabel();
    });

    // Ustawienie wyśrodkowania tekstu i zawijania w kolumnach 1-4
    for (int i = 1; i <= 4; i++) {

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);  // Wyśrodkowanie w poziomie
        centerRenderer.setVerticalAlignment(JLabel.CENTER);    // Wyśrodkowanie w pionie
        jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

        // Tworzenie JLabel dla zawijania tekstu
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setBackground(new Color(255,255,255));
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setHorizontalAlignment(JLabel.CENTER);  // Wyśrodkowanie w poziomie
        label.setVerticalAlignment(JLabel.CENTER);    // Wyśrodkowanie w pionie
        label.setText(" ");

        label.setText("<html><div style='width: 150px;'>" + label.getText() + "</div></html>");

        jTable1.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                label.setText("<html><div style='width: 150px;'>" + value.toString() + "</div></html>");
                return label;
            }
        });
    }

    jTable1.setRowHeight(277);
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
    
    
// Tabela z seansami
public void updateTable2(JTable jTable2, List<CShowing> allShowing, List<CMovie> allMovies) {
    DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Tytuł Filmu", "Data", "Godzina rozpoczęcia", "Sala", ""}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 5;
        }
    };

    jTable2.setModel(model);
    jTable2.setFont(new Font("Arial", Font.PLAIN, 18));
    jTable2.setRowHeight(70);
    model.setRowCount(0);

    for (CShowing showing : allShowing) {
        String movieTitle = showing.getMovieTitle(allMovies);

        LocalDateTime showingDateTime = LocalDateTime.of(showing.getDate(), showing.getTime());

        if (showingDateTime.isBefore(LocalDateTime.now())) {
            continue; // Pominięcie seansu, jeśli data i godzina już minęły
        }

        Object[] row = new Object[]{
                showing.getId(),
                movieTitle,
                showing.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                showing.getTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                showing.getIdHall(),
                " " // Pusta kolumna na przyciski
        };
        model.addRow(row);
    }

    // Renderer dla panelu przycisków w ostatniej kolumnie
    jTable2.getColumnModel().getColumn(5).setCellRenderer((table, _, isSelected, _, row, _) -> {
        JPanel panel = createButtonPanel2(jTable2, row, allShowing);
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(table.getBackground());
        }
        return panel;
    });

    jTable2.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JTextField()) {
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return createButtonPanel2(table, row, allShowing);
        }

        @Override
        public Object getCellEditorValue() {
            return null; // Wartość edytora nie jest używana
        }
    });

    // Centrowanie tekstu w kolumnach
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    for (int i = 0; i < jTable2.getColumnCount() - 1; i++) {
        jTable2.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    // Ustawienie szerokości kolumn
    jTable2.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
    jTable2.getColumnModel().getColumn(1).setPreferredWidth(200); // Tytuł Filmu
    jTable2.getColumnModel().getColumn(2).setPreferredWidth(100); // Data
    jTable2.getColumnModel().getColumn(3).setPreferredWidth(120); // Godzina
    jTable2.getColumnModel().getColumn(4).setPreferredWidth(50);  // Sala
    jTable2.getColumnModel().getColumn(5).setPreferredWidth(200); // Przycisk
}

private JPanel createButtonPanel2(JTable table, int row, List<CShowing> allShowing) {
    JButton selectButton = new JButton("WYBIERZ");
    selectButton.setBackground(new Color(72, 61, 139));
    selectButton.setForeground(Color.WHITE);
    selectButton.setFocusPainted(false);
    selectButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    selectButton.setFont(new Font("Arial", Font.BOLD, 16));
    selectButton.setPreferredSize(new Dimension(150, 40));
    selectButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Dodanie ActionListener
    selectButton.addActionListener(e -> {
        int showingId = (int) table.getModel().getValueAt(row, 0);
        CShowing selectedShowing = allShowing.stream()
                .filter(showing -> showing.getId() == showingId)
                .findFirst()
                .orElse(null);

        if (selectedShowing != null) {
            SeatSelection seatSelection = new SeatSelection(selectedShowing, uzytkownik);
            seatSelection.setVisible(true);
        } else {
            System.err.println("Nie znaleziono seansu o ID: " + showingId);
        }
    });

    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 0, 5, 0);
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(selectButton, gbc);

    return panel;
}

// Tabela z rezerwacjami
public void updateTable3(JTable jTable3, List<CMovie> allMovies, List<CReservation> allReservation) {

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Tytuł Filmu", "Data", "Ilosc biletow" , "Dochód", ""}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };
        jTable3.setModel(model);
        jTable3.setFont(new Font("Arial", Font.PLAIN, 18));
        jTable3.setRowHeight(70);
        model.setRowCount(0);

        for (CReservation reservation : allReservation) {
            int idShowing = reservation.getIdShowing();

            CShowing showing = showingMenager.getById(idShowing);

            if (showing != null) {
                String movieTitle = showing.getMovieTitle(allMovies);

                Object[] row = new Object[]{
                        reservation.getId(),
                        movieTitle,
                        showing.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        reservation.ticketNumber(),
                        reservation.calculateTotalPrice(),
                        " " // Pusta kolumna na przyciski
                };
                model.addRow(row);
            } else {
                System.err.println("Nie znaleziono seansu o ID: " + idShowing);
            }
        }
        // Renderer dla panelu przycisków w ostatniej kolumnie
        jTable3.getColumnModel().getColumn(5).setCellRenderer((table, _, isSelected, _, row, _) -> {
            JPanel panel3 = createButtonPanel3(jTable3, row, reservationManager);
            if (isSelected) {
                panel3.setBackground(table.getSelectionBackground());
            } else {
                panel3.setBackground(table.getBackground());
            }
            return panel3;
        });

        jTable3.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JTextField()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return createButtonPanel3(table, row, reservationManager);
            }

            @Override
            public Object getCellEditorValue() {
                return null; // Wartość edytora nie jest używana
            }
        });

        // Centrowanie tekstu w kolumnach
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < jTable3.getColumnCount()-1; i++) {
            jTable3.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Ustawienie szerokości kolumn
        jTable3.getColumnModel().getColumn(0).setPreferredWidth(150);  // ID
        jTable3.getColumnModel().getColumn(0).setResizable(false);
        jTable3.getColumnModel().getColumn(1).setPreferredWidth(385); // Tytuł Filmu
        jTable3.getColumnModel().getColumn(1).setResizable(false);
        jTable3.getColumnModel().getColumn(2).setPreferredWidth(300); // Data
        jTable3.getColumnModel().getColumn(2).setResizable(false);
        jTable3.getColumnModel().getColumn(3).setPreferredWidth(150); // Ilosc Biletow
        jTable3.getColumnModel().getColumn(3).setResizable(false);
        jTable3.getColumnModel().getColumn(4).setPreferredWidth(150);  // Dochod Biletow
        jTable3.getColumnModel().getColumn(4).setResizable(false);
        jTable3.getColumnModel().getColumn(5).setPreferredWidth(200); // Przycisk
        jTable3.getColumnModel().getColumn(5).setResizable(false);

        jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable3.getTableHeader().setReorderingAllowed(false);

    }
    private JPanel createButtonPanel3(JTable table, int row, CManage<CReservation> reservationManage) {
        JButton displayButton = new JButton("Wyswietl");
        displayButton.setBackground(new Color(72, 61, 139));
        displayButton.setForeground(Color.WHITE);
        displayButton.setFocusPainted(false);
        displayButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        displayButton.setFont(new Font("Arial", Font.BOLD, 16));
        displayButton.setPreferredSize(new Dimension(150, 40));
        displayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Poprawiamy ActionListener
        displayButton.addActionListener(e -> {
            int reservationId = (int) table.getModel().getValueAt(row, 0);
            CReservation reservation = reservationManage.getById(reservationId);
            resFrame resFrameWindow = new resFrame(reservation);
            resFrameWindow.setVisible(true);
        });

        JPanel panel3 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel3.add(displayButton, gbc);

        return panel3;
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
        repertuar_p = new JPanel();
        jPanel2 = new JPanel();
        RepertuarLabel = new JLabel();
        SeansLabel = new JLabel();
        RezerwacjaLabel = new JLabel();
        sidemenu = new JPanel();
        repertuar_m = new JPanel();
        TextRepertuar = new JLabel();
        seanse_m = new JPanel();
        TextSeanse = new JLabel();
        rezerwacje_m = new JPanel();
        TextRezerwacje = new JLabel();
        filler1 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(0, 32767));
        logL = new JLabel();
        PanelSR = new JPanel();
        PanelR = new JPanel();
        FieldSerach = new JTextField();
        ButtonSearch = new JButton();
        movie = new JScrollPane();
        jTable1 = new JTable();
        PanelS = new JPanel();
        FieldSearch2 = new JTextField();
        ButtonSearch2 = new JButton();
        show = new JScrollPane();
        jTable2 = new JTable();
        dateSearch = new JFormattedTextField();
        DateSeatchBtn = new JButton();
        PanelRe = new JPanel();
        FieldSerach1 = new JTextField();
        ButtonSearch1 = new JButton();
        reservation = new JScrollPane();
        jTable3 = new JTable();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new Color(255, 255, 255));

        bg.setBackground(new Color(255, 255, 255));
        bg.setPreferredSize(new Dimension(1920, 1080));
        bg.setLayout(new BoxLayout(bg, BoxLayout.LINE_AXIS));

        repertuar_p.setBackground(new Color(255, 255, 255));

        jPanel2.setBackground(new Color(106, 90, 205));
        jPanel2.setBorder(BorderFactory.createTitledBorder(""));
        jPanel2.setLayout(new CardLayout());

        RepertuarLabel.setFont(new Font("Segoe UI", 0, 36)); // NOI18N
        RepertuarLabel.setText("REPERTUAR");
        jPanel2.add(RepertuarLabel, "card2");

        SeansLabel.setFont(new Font("Segoe UI", 0, 36)); // NOI18N
        SeansLabel.setText("SEANS");
        jPanel2.add(SeansLabel, "card2");

        RezerwacjaLabel.setFont(new Font("Segoe UI", 0, 36)); // NOI18N
        RezerwacjaLabel.setText("REZERWACJE");
        jPanel2.add(RezerwacjaLabel, "card2");

        sidemenu.setBackground(new Color(75, 0, 130));
        sidemenu.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        sidemenu.setMaximumSize(new Dimension(151, 428));

        repertuar_m.setBackground(new Color(72, 61, 139));
        repertuar_m.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                repertuar_mMouseClicked(evt);
            }
            public void mouseEntered(MouseEvent evt) {
                repertuar_mMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                repertuar_mMouseExited(evt);
            }
        });

        TextRepertuar.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        TextRepertuar.setForeground(new Color(255, 255, 255));
        TextRepertuar.setText("REPERTUAR");

        GroupLayout repertuar_mLayout = new GroupLayout(repertuar_m);
        repertuar_m.setLayout(repertuar_mLayout);
        repertuar_mLayout.setHorizontalGroup(
            repertuar_mLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(repertuar_mLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(TextRepertuar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        repertuar_mLayout.setVerticalGroup(
            repertuar_mLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(repertuar_mLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(TextRepertuar)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        seanse_m.setBackground(new Color(72, 61, 139));
        seanse_m.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                seanse_mMouseClicked(evt);
            }
            public void mouseEntered(MouseEvent evt) {
                seanse_mMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                seanse_mMouseExited(evt);
            }
        });

        TextSeanse.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        TextSeanse.setForeground(new Color(255, 255, 255));
        TextSeanse.setHorizontalAlignment(SwingConstants.CENTER);
        TextSeanse.setText("SEANSE");

        GroupLayout seanse_mLayout = new GroupLayout(seanse_m);
        seanse_m.setLayout(seanse_mLayout);
        seanse_mLayout.setHorizontalGroup(
            seanse_mLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(TextSeanse, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        seanse_mLayout.setVerticalGroup(
            seanse_mLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(TextSeanse, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        rezerwacje_m.setBackground(new Color(72, 61, 139));
        rezerwacje_m.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                rezerwacje_mMouseClicked(evt);
            }
            public void mouseEntered(MouseEvent evt) {
                rezerwacje_mMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                rezerwacje_mMouseExited(evt);
            }
        });

        TextRezerwacje.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        TextRezerwacje.setForeground(new Color(255, 255, 255));
        TextRezerwacje.setHorizontalAlignment(SwingConstants.CENTER);
        TextRezerwacje.setText("REZERWACJE");

        GroupLayout rezerwacje_mLayout = new GroupLayout(rezerwacje_m);
        rezerwacje_m.setLayout(rezerwacje_mLayout);
        rezerwacje_mLayout.setHorizontalGroup(
            rezerwacje_mLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(TextRezerwacje, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        rezerwacje_mLayout.setVerticalGroup(
            rezerwacje_mLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(TextRezerwacje, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        logL.setIcon(new ImageIcon(getClass().getResource("/Image/logO.png"))); // NOI18N

        GroupLayout sidemenuLayout = new GroupLayout(sidemenu);
        sidemenu.setLayout(sidemenuLayout);
        sidemenuLayout.setHorizontalGroup(
            sidemenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(repertuar_m, GroupLayout.Alignment.CENTER, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(GroupLayout.Alignment.CENTER, sidemenuLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(seanse_m, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rezerwacje_m, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(sidemenuLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filler1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
            .addGroup(sidemenuLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(logL, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        sidemenuLayout.setVerticalGroup(
            sidemenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(sidemenuLayout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addGroup(sidemenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(filler1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(sidemenuLayout.createSequentialGroup()
                        .addComponent(repertuar_m, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(seanse_m, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rezerwacje_m, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logL, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        PanelSR.setLayout(new CardLayout());

        PanelR.setForeground(new Color(245, 245, 245));

        FieldSerach.setText("Wyszukaj film");

        ButtonSearch.setText("Szukaj");
        ButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ButtonSearchActionPerformed(evt);
            }
        });

        movie.setBackground(new Color(255, 255, 255));
        movie.setForeground(new Color(255, 255, 255));

        jTable1.setFont(new Font("Arial", 0, 18)); // NOI18N
        jTable1.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setToolTipText("");
        jTable1.setRowHeight(277);
        movie.setViewportView(jTable1);

        GroupLayout PanelRLayout = new GroupLayout(PanelR);
        PanelR.setLayout(PanelRLayout);
        PanelRLayout.setHorizontalGroup(
            PanelRLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(PanelRLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelRLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(PanelRLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(FieldSerach, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSearch)
                        .addGap(12, 12, 12))
                    .addGroup(PanelRLayout.createSequentialGroup()
                        .addComponent(movie, GroupLayout.DEFAULT_SIZE, 1773, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        PanelRLayout.setVerticalGroup(
            PanelRLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(PanelRLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelRLayout.createParallelGroup(GroupLayout.Alignment.BASELINE, false)
                    .addComponent(ButtonSearch, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(FieldSerach))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(movie, GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelSR.add(PanelR, "card3");

        FieldSearch2.setText("Wyszukaj film");

        ButtonSearch2.setText("Szukaj");
        ButtonSearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ButtonSearch2ActionPerformed(evt);
            }
        });

        show.setBackground(new Color(255, 255, 255));
        show.setForeground(new Color(255, 255, 255));

        jTable2.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable2.setToolTipText("");
        show.setViewportView(jTable2);

        dateSearch.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat(""))));

        DateSeatchBtn.setText("Szukaj");
        DateSeatchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                DateSeatchBtnActionPerformed(evt);
            }
        });

        GroupLayout PanelSLayout = new GroupLayout(PanelS);
        PanelS.setLayout(PanelSLayout);
        PanelSLayout.setHorizontalGroup(
            PanelSLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(PanelSLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelSLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(PanelSLayout.createSequentialGroup()
                        .addGap(0, 1247, Short.MAX_VALUE)
                        .addComponent(dateSearch, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DateSeatchBtn)
                        .addGap(20, 20, 20)
                        .addComponent(FieldSearch2, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSearch2)
                        .addGap(12, 12, 12))
                    .addGroup(PanelSLayout.createSequentialGroup()
                        .addComponent(show, GroupLayout.DEFAULT_SIZE, 1773, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        PanelSLayout.setVerticalGroup(
            PanelSLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(PanelSLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelSLayout.createParallelGroup(GroupLayout.Alignment.BASELINE, false)
                    .addComponent(ButtonSearch2, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(FieldSearch2)
                    .addComponent(dateSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(DateSeatchBtn, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(show, GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelSR.add(PanelS, "card3");

        PanelRe.setForeground(new Color(245, 245, 245));

        FieldSerach1.setText("Wyszukaj rezerwację");
        FieldSerach1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                FieldSerach1ActionPerformed(evt);
            }
        });

        ButtonSearch1.setText("Szukaj");
        ButtonSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ButtonSearch1ActionPerformed(evt);
            }
        });

        reservation.setBackground(new Color(255, 255, 255));
        reservation.setForeground(new Color(255, 255, 255));

        jTable3.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable3.setToolTipText("");
        reservation.setViewportView(jTable3);

        GroupLayout PanelReLayout = new GroupLayout(PanelRe);
        PanelRe.setLayout(PanelReLayout);
        PanelReLayout.setHorizontalGroup(
            PanelReLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(PanelReLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelReLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(PanelReLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(FieldSerach1, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSearch1)
                        .addGap(12, 12, 12))
                    .addGroup(PanelReLayout.createSequentialGroup()
                        .addComponent(reservation, GroupLayout.DEFAULT_SIZE, 1773, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        PanelReLayout.setVerticalGroup(
            PanelReLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(PanelReLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelReLayout.createParallelGroup(GroupLayout.Alignment.BASELINE, false)
                    .addComponent(ButtonSearch1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(FieldSerach1))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reservation, GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelSR.add(PanelRe, "card3");

        GroupLayout repertuar_pLayout = new GroupLayout(repertuar_p);
        repertuar_p.setLayout(repertuar_pLayout);
        repertuar_pLayout.setHorizontalGroup(
            repertuar_pLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(repertuar_pLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(sidemenu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(repertuar_pLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelSR, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        repertuar_pLayout.setVerticalGroup(
            repertuar_pLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(repertuar_pLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelSR, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(sidemenu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bg.add(repertuar_p);

        getContentPane().add(bg, BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonSearchActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ButtonSearchActionPerformed
        String searchQuery = FieldSerach.getText().trim().toLowerCase();

        if (searchQuery.isEmpty()) {
            updateTable(jTable1, movieManager.getAll());
        } else {
            List<CMovie> filteredMovies = new ArrayList<>();

            for (CMovie movie : movieManager.getAll()) {
                if (movie.getTitle().toLowerCase().contains(searchQuery)) {
                    filteredMovies.add(movie);
                }
            }

            updateTable(jTable1, filteredMovies);
        }
    }//GEN-LAST:event_ButtonSearchActionPerformed


    private void ButtonSearch2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ButtonSearch2ActionPerformed
        String searchQuery = FieldSearch2.getText().trim().toLowerCase();
        CManage<CMovie> movieManager = new CManage<>(CMovie.class);
        List<CMovie> allMovies = movieManager.getAll();

        if (searchQuery.isEmpty()) {

            updateTable2(jTable2, allShowing, allMovies);
        } else {

            List<CShowing> filteredShows = new ArrayList<>();

            for (CShowing showing : showingMenager.getAll()) {
                String movieTitle = showing.getMovieTitle(allMovies);
                if (movieTitle.toLowerCase().contains(searchQuery)) {
                    filteredShows.add(showing);
                }
            }
            updateTable2(jTable2, filteredShows, allMovies);
        }
    }//GEN-LAST:event_ButtonSearch2ActionPerformed


    private void DateSeatchBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_DateSeatchBtnActionPerformed
       String searchDate = dateSearch.getText();
       
        if (searchDate.isEmpty()) {
        updateTable2(jTable2, showingMenager.getAll(), allMovies); // Odśwież tabelę z wszystkimi wynikami
    } else {
        try {
            // Używamy DateTimeFormatter do parsowania i formatowania daty
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate searchLocalDate = LocalDate.parse(searchDate, formatter);

            // Filtrujemy listę `allShowing` według daty
            List<CShowing> filteredShowings = new ArrayList<>();

            for (CShowing showing : allShowing) {
                if (showing.getDate().equals(searchLocalDate)) {
                    filteredShowings.add(showing);
                }
            }

            // Aktualizujemy tabelę `jTable2` przefiltrowanymi danymi
            updateTable2(jTable2, filteredShowings, allMovies);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Nieprawidłowy format daty! Użyj formatu: yyyy-MM-dd", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_DateSeatchBtnActionPerformed

    private void ButtonSearch1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ButtonSearch1ActionPerformed
        String searchQuery = FieldSerach1.getText().trim();

        if (searchQuery.isEmpty()) {
            updateTable3(jTable3, allMovies, allReservation);
        } else {
            try {
                int searchId = Integer.parseInt(searchQuery);

                List<CReservation> filteredReservations = allReservation.stream()
                        .filter(reservation -> reservation.getId() == searchId)
                        .toList();

                if (filteredReservations.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nie znaleziono rezerwacji o podanym ID.", "Brak wyników", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    updateTable3(jTable3, allMovies, filteredReservations);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Proszę wprowadzić poprawne ID (liczbę).", "Błąd wyszukiwania", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_ButtonSearch1ActionPerformed

    private void FieldSerach1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_FieldSerach1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldSerach1ActionPerformed

    private void rezerwacje_mMouseExited(MouseEvent evt) {//GEN-FIRST:event_rezerwacje_mMouseExited
        rezerwacje_m.setBackground(new Color(72,61,139));
    }//GEN-LAST:event_rezerwacje_mMouseExited

    private void rezerwacje_mMouseEntered(MouseEvent evt) {//GEN-FIRST:event_rezerwacje_mMouseEntered
        rezerwacje_m.setBackground(new Color(106,90,205));
    }//GEN-LAST:event_rezerwacje_mMouseEntered

    private void rezerwacje_mMouseClicked(MouseEvent evt) {//GEN-FIRST:event_rezerwacje_mMouseClicked
        PanelR.setVisible(false);
        PanelRe.setVisible(true);
        PanelS.setVisible(false);
        reservationManager = new CManage<>(CReservation.class);
        allReservation=reservationManager.getAll();
        updateTable3(jTable3, allMovies, allReservation);
        SeansLabel.setVisible(false);
        RepertuarLabel.setVisible(false);
        RezerwacjaLabel.setVisible(true);
    }//GEN-LAST:event_rezerwacje_mMouseClicked

    private void seanse_mMouseExited(MouseEvent evt) {//GEN-FIRST:event_seanse_mMouseExited
        seanse_m.setBackground(new Color(72,61,139));
    }//GEN-LAST:event_seanse_mMouseExited

    private void seanse_mMouseEntered(MouseEvent evt) {//GEN-FIRST:event_seanse_mMouseEntered
        seanse_m.setBackground(new Color(106,90,205));
    }//GEN-LAST:event_seanse_mMouseEntered

    private void seanse_mMouseClicked(MouseEvent evt) {//GEN-FIRST:event_seanse_mMouseClicked
        PanelR.setVisible(false);
        PanelRe.setVisible(false);
        PanelS.setVisible(true);
        updateTable2(jTable2,allShowing, allMovies);
        SeansLabel.setVisible(true);
        RepertuarLabel.setVisible(false);
        RezerwacjaLabel.setVisible(false);
    }//GEN-LAST:event_seanse_mMouseClicked

    private void repertuar_mMouseExited(MouseEvent evt) {//GEN-FIRST:event_repertuar_mMouseExited
        repertuar_m.setBackground(new Color(72,61,139));
    }//GEN-LAST:event_repertuar_mMouseExited

    private void repertuar_mMouseEntered(MouseEvent evt) {//GEN-FIRST:event_repertuar_mMouseEntered
        repertuar_m.setBackground(new Color(106,90,205));
    }//GEN-LAST:event_repertuar_mMouseEntered

    private void repertuar_mMouseClicked(MouseEvent evt) {//GEN-FIRST:event_repertuar_mMouseClicked
        PanelS.setVisible(false);
        PanelR.setVisible(true);
        PanelRe.setVisible(false);
        updateTable(jTable1,allMovies);
        SeansLabel.setVisible(false);
        RepertuarLabel.setVisible(true);
        RezerwacjaLabel.setVisible(false);
    }//GEN-LAST:event_repertuar_mMouseClicked



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton ButtonSearch;
    private JButton ButtonSearch1;
    private JButton ButtonSearch2;
    private JButton DateSeatchBtn;
    private JTextField FieldSearch2;
    private JTextField FieldSerach;
    private JTextField FieldSerach1;
    private JPanel PanelR;
    private JPanel PanelRe;
    private JPanel PanelS;
    private JPanel PanelSR;
    private JLabel RepertuarLabel;
    private JLabel RezerwacjaLabel;
    private JLabel SeansLabel;
    private JLabel TextRepertuar;
    private JLabel TextRezerwacje;
    private JLabel TextSeanse;
    private JPanel bg;
    private JFormattedTextField dateSearch;
    private Box.Filler filler1;
    private JPanel jPanel2;
    private JTable jTable1;
    private JTable jTable2;
    private JTable jTable3;
    private JLabel logL;
    private JScrollPane movie;
    private JPanel repertuar_m;
    private JPanel repertuar_p;
    private JScrollPane reservation;
    private JPanel rezerwacje_m;
    private JPanel seanse_m;
    private JScrollPane show;
    private JPanel sidemenu;
    // End of variables declaration//GEN-END:variables
}
