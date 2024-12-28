import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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
import javax.swing.text.MaskFormatter;
/**
 *
 * @author yande
 */
public class Cashier extends JFrame {

    private  CManage<CMovie> movieManager;
    private List<CMovie> allMovies;
    private CManage<CShowing> showingMenager;
    private List<CShowing> allShowing;
    public Cashier() {
        initComponents();    
     //Wczytanie baz danych
        movieManager= new CManage<> (CMovie.class);
        allMovies =movieManager.getAll();
        showingMenager = new CManage<> (CShowing.class);
        allShowing =showingMenager.getAll();
        
     customComponents();
        
    }
    
    private void customComponents()
    {
        setExtendedState(Cashier.MAXIMIZED_BOTH);
        setTitle("Katana - Panel kasjera");
        ImageIcon icona = new ImageIcon(getClass().getResource("Image/katana.png"));
        setIconImage(icona.getImage());
        
         logL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                JOptionPane.showMessageDialog(null, "Wylogowano");
                new Login().setVisible(true);
            }
        });
        updateTable(jTable1,allMovies);
        updateTable2(jTable2,allShowing, allMovies);
        
         try {
        MaskFormatter dateFormatter = new MaskFormatter("####-##-##");
        dateFormatter.setPlaceholderCharacter(' ');
        dateSearch.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(dateFormatter));
    } catch (ParseException e) {
        e.printStackTrace();
    }
    }
    

    public void updateTable(JTable jTable1, List<CMovie> allMovies) {
        DefaultTableModel model = new DefaultTableModel(new String[]{" ", "Tytuł","Obsada","Opis","Gatunek"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Blokuje edycję wszystkich komórek
            }
        };
        jTable1.setModel(model);

        
        jTable1.setRowHeight(277);

        model.setRowCount(0);

        for (CMovie movie : allMovies) {
            Object[] row = new Object[]{
                    new ImageIcon(scaleImage(movie.getImagePath(), 190, 270)),
                    movie.getTitle(),
                    movie.getCast(),
                    movie.getMovieDescription(),
                    movie.getGenre()
            };

            model.addRow(row);
        }

        //Obraz
        jTable1.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof ImageIcon) {
                    JLabel label = new JLabel((ImageIcon) value);
                    label.setText("");
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setVerticalAlignment(JLabel.CENTER);
                    return label;
                }
                return new JLabel();
            }
        });
        
        
        
     DefaultTableCellRenderer wrappingAndCenterRenderer = new DefaultTableCellRenderer() {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Wywołujemy standardową funkcję renderera, żeby otrzymać standardowy komponent (np. JLabel)
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Jeżeli to jest JTextArea
        if (comp instanceof JLabel) {
            JLabel label = (JLabel) comp;

            // Ustawiamy wyśrodkowanie tekstu poziome i pionowe
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
        } else if (comp instanceof JTextArea) { // Jeśli to jest JTextArea
            JTextArea textArea = (JTextArea) comp;
            textArea.setLineWrap(true); // Włączenie zawijania tekstu
            textArea.setWrapStyleWord(true); // Zawijanie całych słów
            textArea.setOpaque(true);
            textArea.setBorder(null);
            textArea.setFont(new Font("Arial", Font.PLAIN, 30)); // Czcionka i rozmiar tekstu
            textArea.setAlignmentX(Component.CENTER_ALIGNMENT); // Wyrównanie poziome
            textArea.setAlignmentY(Component.CENTER_ALIGNMENT); // Wyrównanie pionowe

            // Jeśli komórka jest wybrana, ustawiamy tło na kolor selekcji
            if (isSelected) {
                textArea.setBackground(table.getSelectionBackground());
                textArea.setForeground(table.getSelectionForeground());
            } else {
                textArea.setBackground(table.getBackground());
                textArea.setForeground(table.getForeground());
            }
        }

        return comp;
    }
};
     
     

    // Ustawienie rendererów dla kolumn "Tytuł", "Obsada", "Opis" i "Gatunek"
    jTable1.getColumnModel().getColumn(1).setCellRenderer(wrappingAndCenterRenderer); // "Tytuł"
    jTable1.getColumnModel().getColumn(2).setCellRenderer(wrappingAndCenterRenderer); // "Obsada"
    jTable1.getColumnModel().getColumn(3).setCellRenderer(wrappingAndCenterRenderer); // "Opis"
    jTable1.getColumnModel().getColumn(4).setCellRenderer(wrappingAndCenterRenderer); // "Gatunek"

    // Ustawienie szerokości kolumn i zablokowanie ich zmiany rozmiaru
    jTable1.getColumnModel().getColumn(0).setPreferredWidth(199);
    jTable1.getColumnModel().getColumn(0).setResizable(false);
    jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
    jTable1.getColumnModel().getColumn(1).setResizable(false);
    jTable1.getColumnModel().getColumn(2).setPreferredWidth(300);
    jTable1.getColumnModel().getColumn(2).setResizable(false);
    jTable1.getColumnModel().getColumn(3).setPreferredWidth(300);
    jTable1.getColumnModel().getColumn(3).setResizable(false);
    jTable1.getColumnModel().getColumn(4).setPreferredWidth(150);
    jTable1.getColumnModel().getColumn(4).setResizable(false);

    jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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

    public void updateTable2(JTable jTable2, List<CShowing> allShowing, List<CMovie> allMovies) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Tylko ostatnia kolumna (przycisk) jest interaktywna
                return column == 5;
            }
        };

        model.setColumnIdentifiers(new String[]{
                "ID", "Tytuł Filmu", "Data", "Godzina rozpoczęcia", "Sala", "Akcja"
        });

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
                    showing.getTime().format(DateTimeFormatter.ofPattern("hh:mm a")),
                    showing.getIdHall(),
                    "WYBIERZ" // Placeholder tekstowy dla przycisku
            };



            model.addRow(row);
        }

        jTable2.setModel(model);
        jTable2.setRowHeight(50);

        // Centrowanie tekstu w kolumnach
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < jTable2.getColumnCount(); i++) {
            jTable2.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Ustawienie szerokości kolumn
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(200); // Tytuł Filmu
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(100); // Data
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(120); // Godzina
        jTable2.getColumnModel().getColumn(4).setPreferredWidth(50);  // Sala
        jTable2.getColumnModel().getColumn(5).setPreferredWidth(100); // Przycisk


    // Renderer dla przycisku
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(72, 61, 139));
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setFont(new Font("Arial", Font.BOLD, 12));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value == null ? "" : value.toString());
            return this;
        }
    }

    // Editor dla przycisku
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private boolean clicked;
        private CShowing showing;

        public ButtonEditor(JCheckBox checkBox, List<CShowing> allShowing) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(new Color(72, 61, 139));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            button.addActionListener(e -> {
                if (clicked) {
                    // Uruchomienie SeatSelection z odpowiednim showing
                    SeatSelection seatSelection = new SeatSelection(showing);
                    seatSelection.setVisible(true);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            // Pobranie obiektu CShowing na podstawie wiersza
            showing = allShowing.stream()
                    .filter(s -> s.getId() == (int) table.getValueAt(row, 0))
                    .findFirst()
                    .orElse(null);

            button.setText(value == null ? "" : value.toString());
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            clicked = false;
            return button.getText();
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }
        // Dodanie przycisku do kolumny "Akcja"
        jTable2.getColumn("Akcja").setCellRenderer(new ButtonRenderer());
        jTable2.getColumn("Akcja").setCellEditor(new ButtonEditor(new JCheckBox(), allShowing));
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
        sidemenu = new JPanel();
        repertuar_m = new JPanel();
        TextRepertuar = new JLabel();
        seanse_m = new JPanel();
        TextSeanse = new JLabel();
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
        TextSeanse.setText("SEANSE");

        GroupLayout seanse_mLayout = new GroupLayout(seanse_m);
        seanse_m.setLayout(seanse_mLayout);
        seanse_mLayout.setHorizontalGroup(
            seanse_mLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, seanse_mLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TextSeanse)
                .addGap(33, 33, 33))
        );
        seanse_mLayout.setVerticalGroup(
            seanse_mLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(seanse_mLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(TextSeanse, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        logL.setIcon(new ImageIcon(getClass().getResource("/Image/logO.png"))); // NOI18N

        GroupLayout sidemenuLayout = new GroupLayout(sidemenu);
        sidemenu.setLayout(sidemenuLayout);
        sidemenuLayout.setHorizontalGroup(
            sidemenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(repertuar_m, GroupLayout.Alignment.CENTER, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(GroupLayout.Alignment.CENTER, sidemenuLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(seanse_m, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logL, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
            .addGroup(sidemenuLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filler1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
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
                        .addComponent(seanse_m, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 664, Short.MAX_VALUE)
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
        ));
        jTable1.setToolTipText("");
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
                        .addGap(0, 549, Short.MAX_VALUE)
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
                .addComponent(show, GroupLayout.DEFAULT_SIZE, 828, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelSR.add(PanelS, "card3");

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

    private void repertuar_mMouseEntered(MouseEvent evt) {//GEN-FIRST:event_repertuar_mMouseEntered
       repertuar_m.setBackground(new Color(106,90,205));
    }//GEN-LAST:event_repertuar_mMouseEntered

    private void repertuar_mMouseExited(MouseEvent evt) {//GEN-FIRST:event_repertuar_mMouseExited
        repertuar_m.setBackground(new Color(72,61,139));
    }//GEN-LAST:event_repertuar_mMouseExited

    private void seanse_mMouseEntered(MouseEvent evt) {//GEN-FIRST:event_seanse_mMouseEntered
        seanse_m.setBackground(new Color(106,90,205));
    }//GEN-LAST:event_seanse_mMouseEntered

    private void seanse_mMouseExited(MouseEvent evt) {//GEN-FIRST:event_seanse_mMouseExited
       seanse_m.setBackground(new Color(72,61,139));
    }//GEN-LAST:event_seanse_mMouseExited

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
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonSearch2ActionPerformed

    private void repertuar_mMouseClicked(MouseEvent evt) {//GEN-FIRST:event_repertuar_mMouseClicked
        PanelS.setVisible(false);
        PanelR.setVisible(true);
        updateTable(jTable1,allMovies);
        SeansLabel.setVisible(false);
        RepertuarLabel.setVisible(true);
    }//GEN-LAST:event_repertuar_mMouseClicked

    private void seanse_mMouseClicked(MouseEvent evt) {//GEN-FIRST:event_seanse_mMouseClicked
        PanelR.setVisible(false);
        PanelS.setVisible(true);
        updateTable2(jTable2,allShowing, allMovies);
        SeansLabel.setVisible(true);
        RepertuarLabel.setVisible(false);
    }//GEN-LAST:event_seanse_mMouseClicked

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

    public static void main(String[] args) {
        // Uruchomienie aplikacji Cashier1
       EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cashier().setVisible(true);
            } });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton ButtonSearch;
    private JButton ButtonSearch2;
    private JButton DateSeatchBtn;
    private JTextField FieldSearch2;
    private JTextField FieldSerach;
    private JPanel PanelR;
    private JPanel PanelS;
    private JPanel PanelSR;
    private JLabel RepertuarLabel;
    private JLabel SeansLabel;
    private JLabel TextRepertuar;
    private JLabel TextSeanse;
    private JPanel bg;
    private JFormattedTextField dateSearch;
    private Box.Filler filler1;
    private JPanel jPanel2;
    private JTable jTable1;
    private JTable jTable2;
    private JLabel logL;
    private JScrollPane movie;
    private JPanel repertuar_m;
    private JPanel repertuar_p;
    private JPanel seanse_m;
    private JScrollPane show;
    private JPanel sidemenu;
    // End of variables declaration//GEN-END:variables
}
