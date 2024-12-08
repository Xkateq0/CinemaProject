import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.image.BufferedImage;
import java.util.List;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
import java.awt.Image;
/**
 *
 * @author yande
 */
public class Administrator extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public Administrator() {
        initComponents();
        setTitle("Cinema Project");
        CManage<CMovie> movieManager= new CManage<> (CMovie.class);
        List<CMovie> allMovies =movieManager.getAll();
        updateTable(jTable1,allMovies);
        CManage<CShowing> showingMenager = new CManage<> (CShowing.class);
        List<CShowing> allShowing =showingMenager.getAll();
        updateTable2(jTable2,allShowing, allMovies);
    }

    public void updateTable(JTable jTable1, List<CMovie> allMovies) {
        // Pobierz model tabeli
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        jTable1.setRowHeight(150);

        // Ustaw nagłówki tabeli (ID, Obrazek, Tytuł)
        model.setColumnIdentifiers(new String[]{
                "ID", "Image", "Title"
        });

        // Usuń wszystkie istniejące wiersze (jeśli są)
        model.setRowCount(0);

        // Iteruj po wszystkich filmach i dodaj je do tabeli
        for (CMovie movie : allMovies) {
            Object[] row = new Object[]{
                    movie.getId(),  // ID filmu
                    new ImageIcon(scaleImage(movie.getImagePath(), 100, 100)),  // Obrazek (ścieżka do obrazu)
                    movie.getTitle()  // Tytuł filmu
            };

            // Dodaj wiersz do modelu tabeli
            model.addRow(row);
        }

        // Ustawienie renderera, by wyświetlać obrazki w tabeli
        jTable1.getColumnModel().getColumn(1).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof ImageIcon) {
                    // Tworzymy etykietę, na której będziemy wyświetlać obraz
                    JLabel label = new JLabel((ImageIcon) value);
                    label.setText("");  // Ukryj tekst, tylko obrazek będzie widoczny
                    label.setHorizontalAlignment(JLabel.CENTER);  // Wyrównanie obrazu do środka
                    return label;
                }
                return new JLabel();  // Zwróć pustą etykietę, jeśli wartość nie jest obrazkiem
            }
        });
    }

    // Metoda skalująca obraz za pomocą Graphics2D
    private Image scaleImage(String imagePath, int width, int height) {
        try {
            // Ładujemy obraz
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage();

            // Tworzymy buforowany obraz o nowych rozmiarach
            BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaledImage.createGraphics();

            // Ustawiamy opcje renderowania dla wysokiej jakości
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);  // Interpolacja bicubiczna
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  // Włączenie antyaliasingu
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);  // Wysoka jakość renderowania

            // Rysujemy obraz na nowym obrazie
            g2d.drawImage(img, 0, 0, width, height, null);
            g2d.dispose();  // Zwalniamy zasoby

            return scaledImage;  // Zwracamy przeskalowany obraz
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Zwracamy null, jeśli coś pójdzie nie tak
        }
    }

    public void updateTable2(JTable jTable2, List<CShowing> allShowing, List<CMovie> allMovies) {
        // Pobierz model tabeli
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

        // Ustaw nagłówki tabeli (ID, Tytuł Filmu, Data, Godzina rozpoczęcia, Sala)
        model.setColumnIdentifiers(new String[] {
                "ID", "Tytuł Filmu", "Data", "Godzina rozpoczęcia", "Sala"
        });

        // Usuń wszystkie istniejące wiersze (jeśli są)
        model.setRowCount(0);

        // Iteruj po wszystkich pokazach i dodaj je do tabeli
        for (CShowing showing : allShowing) {
            // Pobierz tytuł filmu na podstawie ID
            String movieTitle = showing.getMovieTitle(allMovies);

            // Dodaj wiersz do tabeli
            Object[] row = new Object[]{
                    showing.getId(),  // ID pokazu
                    movieTitle,        // Tytuł filmu
                    showing.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), // Data
                    showing.getTime().format(DateTimeFormatter.ofPattern("hh:mm a")),    // Godzina rozpoczęcia
                    showing.getIdHall() // Sala
            };

            // Dodaj wiersz do modelu tabeli
            model.addRow(row);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddMovie = new javax.swing.JFrame();
        Panel_AddMovie = new javax.swing.JPanel();
        Image = new javax.swing.JButton();
        LabelTitle = new javax.swing.JLabel();
        Title = new javax.swing.JTextField();
        LabelCast = new javax.swing.JLabel();
        Cast = new javax.swing.JTextField();
        LabelGenre = new javax.swing.JLabel();
        Genre = new javax.swing.JComboBox<>();
        LabelDuration = new javax.swing.JLabel();
        Duration = new javax.swing.JSpinner();
        LabelMovieDescription = new javax.swing.JLabel();
        MovieDescription = new javax.swing.JTextField();
        AddShow = new javax.swing.JFrame();
        Panel_AddShow = new javax.swing.JPanel();
        LabelMovie = new javax.swing.JLabel();
        idMovie = new javax.swing.JComboBox<>();
        LabelHall = new javax.swing.JLabel();
        idHall = new javax.swing.JComboBox<>();
        LabelDate = new javax.swing.JLabel();
        Date = new javax.swing.JFormattedTextField();
        LabelTime = new javax.swing.JLabel();
        Time = new javax.swing.JFormattedTextField();
        bg = new javax.swing.JPanel();
        repertuar_p = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        RepertuarLabel = new javax.swing.JLabel();
        SeansLabel = new javax.swing.JLabel();
        sidemenu = new javax.swing.JPanel();
        repertuar_m = new javax.swing.JPanel();
        TextRepertuar = new javax.swing.JLabel();
        seanse_m = new javax.swing.JPanel();
        TextSeanse = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        PanelSR = new javax.swing.JPanel();
        PanelR = new javax.swing.JPanel();
        ButtonAddMovie = new javax.swing.JButton();
        FieldSerach = new javax.swing.JTextField();
        ButtonSearch = new javax.swing.JButton();
        movie = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        PanelS = new javax.swing.JPanel();
        ButtonAddShow = new javax.swing.JButton();
        FieldSearch2 = new javax.swing.JTextField();
        ButtonSearch2 = new javax.swing.JButton();
        show = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        AddMovie.setMaximumSize(new java.awt.Dimension(620, 380));
        AddMovie.setMinimumSize(new java.awt.Dimension(620, 380));
        AddMovie.setPreferredSize(new java.awt.Dimension(610, 350));
        AddMovie.setResizable(false);
        AddMovie.getContentPane().setLayout(null);

        Panel_AddMovie.setBackground(new java.awt.Color(245, 245, 245));
        Panel_AddMovie.setForeground(new java.awt.Color(245, 245, 245));
        Panel_AddMovie.setMaximumSize(new java.awt.Dimension(650, 400));
        Panel_AddMovie.setMinimumSize(new java.awt.Dimension(650, 400));
        Panel_AddMovie.setPreferredSize(new java.awt.Dimension(610, 350));

        Image.setBackground(new java.awt.Color(128, 128, 128));
        Image.setForeground(new java.awt.Color(220, 220, 220));
        Image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/111_preview.png"))); // NOI18N
        Image.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImageActionPerformed(evt);
            }
        });

        LabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelTitle.setForeground(new java.awt.Color(0, 0, 0));
        LabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelTitle.setText("Tytuł Filmu");

        Title.setText("Wpisz tytuł filmu");

        LabelCast.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelCast.setForeground(new java.awt.Color(0, 0, 0));
        LabelCast.setText("Obsada filmu");

        Cast.setText("Wpisz obsade filmu");

        LabelGenre.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelGenre.setForeground(new java.awt.Color(0, 0, 0));
        LabelGenre.setText("Gatunek filmu");

        Genre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        LabelDuration.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelDuration.setForeground(new java.awt.Color(0, 0, 0));
        LabelDuration.setText("Czas filmu");
        LabelDuration.setToolTipText("");

        LabelMovieDescription.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelMovieDescription.setForeground(new java.awt.Color(0, 0, 0));
        LabelMovieDescription.setText("Opis Filmu");

        MovieDescription.setText("Wpisz opis filmu");

        javax.swing.GroupLayout Panel_AddMovieLayout = new javax.swing.GroupLayout(Panel_AddMovie);
        Panel_AddMovie.setLayout(Panel_AddMovieLayout);
        Panel_AddMovieLayout.setHorizontalGroup(
            Panel_AddMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AddMovieLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(Image, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Panel_AddMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_AddMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(LabelCast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LabelTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AddMovieLayout.createSequentialGroup()
                            .addComponent(Genre, 0, 168, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(Duration, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6))
                        .addGroup(Panel_AddMovieLayout.createSequentialGroup()
                            .addComponent(LabelGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(LabelDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(Title, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Panel_AddMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(Cast, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                        .addComponent(LabelMovieDescription, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(MovieDescription, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        Panel_AddMovieLayout.setVerticalGroup(
            Panel_AddMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AddMovieLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(Panel_AddMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AddMovieLayout.createSequentialGroup()
                        .addComponent(LabelTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(LabelCast)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Cast, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LabelMovieDescription)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MovieDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Panel_AddMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelDuration)
                            .addComponent(LabelGenre))
                        .addGap(4, 4, 4)
                        .addGroup(Panel_AddMovieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Genre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Duration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AddMovieLayout.createSequentialGroup()
                        .addComponent(Image, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
        );

        AddMovie.getContentPane().add(Panel_AddMovie);
        Panel_AddMovie.setBounds(0, 0, 610, 350);

        AddShow.setMaximumSize(new java.awt.Dimension(570, 330));
        AddShow.setMinimumSize(new java.awt.Dimension(570, 330));
        AddShow.setPreferredSize(new java.awt.Dimension(560, 290));
        AddShow.setResizable(false);
        AddShow.getContentPane().setLayout(null);

        Panel_AddShow.setBackground(new java.awt.Color(245, 245, 245));
        Panel_AddShow.setForeground(new java.awt.Color(245, 245, 245));
        Panel_AddShow.setMaximumSize(new java.awt.Dimension(560, 290));

        LabelMovie.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelMovie.setForeground(new java.awt.Color(0, 0, 0));
        LabelMovie.setText("Wybierz tytuł filmu");

        idMovie.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        LabelHall.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelHall.setForeground(new java.awt.Color(0, 0, 0));
        LabelHall.setText("Wybierz sale");

        idHall.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        LabelDate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelDate.setForeground(new java.awt.Color(0, 0, 0));
        LabelDate.setText("Wpisz date");

        Date.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));

        LabelTime.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelTime.setForeground(new java.awt.Color(0, 0, 0));
        LabelTime.setText("Wpisz godzine");

        Time.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));

        javax.swing.GroupLayout Panel_AddShowLayout = new javax.swing.GroupLayout(Panel_AddShow);
        Panel_AddShow.setLayout(Panel_AddShowLayout);
        Panel_AddShowLayout.setHorizontalGroup(
            Panel_AddShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AddShowLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(Panel_AddShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(Panel_AddShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(LabelMovie, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
                        .addComponent(LabelHall, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LabelDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LabelTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(idMovie, 0, 505, Short.MAX_VALUE)
                    .addComponent(idHall, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Date)
                    .addComponent(Time))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        Panel_AddShowLayout.setVerticalGroup(
            Panel_AddShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AddShowLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(LabelMovie)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idMovie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelHall)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idHall, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(LabelDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        AddShow.getContentPane().add(Panel_AddShow);
        Panel_AddShow.setBounds(0, 0, 560, 290);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setPreferredSize(new java.awt.Dimension(1920, 1080));
        bg.setLayout(new javax.swing.BoxLayout(bg, javax.swing.BoxLayout.LINE_AXIS));

        repertuar_p.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(106, 90, 205));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setLayout(new java.awt.CardLayout());

        RepertuarLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        RepertuarLabel.setText("REPERTUAR");
        jPanel2.add(RepertuarLabel, "card2");

        SeansLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        SeansLabel.setText("SEANS");
        jPanel2.add(SeansLabel, "card2");

        sidemenu.setBackground(new java.awt.Color(75, 0, 130));
        sidemenu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sidemenu.setMaximumSize(new java.awt.Dimension(151, 428));

        repertuar_m.setBackground(new java.awt.Color(72, 61, 139));
        repertuar_m.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repertuar_mMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                repertuar_mMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                repertuar_mMouseExited(evt);
            }
        });

        TextRepertuar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TextRepertuar.setForeground(new java.awt.Color(255, 255, 255));
        TextRepertuar.setText("REPERTUAR");

        javax.swing.GroupLayout repertuar_mLayout = new javax.swing.GroupLayout(repertuar_m);
        repertuar_m.setLayout(repertuar_mLayout);
        repertuar_mLayout.setHorizontalGroup(
            repertuar_mLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(repertuar_mLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(TextRepertuar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        repertuar_mLayout.setVerticalGroup(
            repertuar_mLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(repertuar_mLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(TextRepertuar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        seanse_m.setBackground(new java.awt.Color(72, 61, 139));
        seanse_m.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seanse_mMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                seanse_mMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                seanse_mMouseExited(evt);
            }
        });

        TextSeanse.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TextSeanse.setForeground(new java.awt.Color(255, 255, 255));
        TextSeanse.setText("SEANSE");

        javax.swing.GroupLayout seanse_mLayout = new javax.swing.GroupLayout(seanse_m);
        seanse_m.setLayout(seanse_mLayout);
        seanse_mLayout.setHorizontalGroup(
            seanse_mLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seanse_mLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(TextSeanse)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        seanse_mLayout.setVerticalGroup(
            seanse_mLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seanse_mLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(TextSeanse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout sidemenuLayout = new javax.swing.GroupLayout(sidemenu);
        sidemenu.setLayout(sidemenuLayout);
        sidemenuLayout.setHorizontalGroup(
            sidemenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(repertuar_m, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(seanse_m, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidemenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );
        sidemenuLayout.setVerticalGroup(
            sidemenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidemenuLayout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addGroup(sidemenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidemenuLayout.createSequentialGroup()
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78))
                    .addGroup(sidemenuLayout.createSequentialGroup()
                        .addComponent(repertuar_m, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(seanse_m, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(187, 187, 187)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelSR.setLayout(new java.awt.CardLayout());

        PanelR.setForeground(new java.awt.Color(245, 245, 245));

        ButtonAddMovie.setBackground(new java.awt.Color(72, 61, 139));
        ButtonAddMovie.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonAddMovie.setText("Dodaj Film");
        ButtonAddMovie.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ButtonAddMovie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonAddMovieActionPerformed(evt);
            }
        });

        FieldSerach.setText("Wyszukaj film");

        ButtonSearch.setText("Szukaj");
        ButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSearchActionPerformed(evt);
            }
        });

        movie.setBackground(new java.awt.Color(255, 255, 255));
        movie.setForeground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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

        javax.swing.GroupLayout PanelRLayout = new javax.swing.GroupLayout(PanelR);
        PanelR.setLayout(PanelRLayout);
        PanelRLayout.setHorizontalGroup(
            PanelRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelRLayout.createSequentialGroup()
                        .addComponent(ButtonAddMovie, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 590, Short.MAX_VALUE)
                        .addComponent(FieldSerach, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSearch)
                        .addGap(12, 12, 12))
                    .addGroup(PanelRLayout.createSequentialGroup()
                        .addComponent(movie, javax.swing.GroupLayout.DEFAULT_SIZE, 1075, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        PanelRLayout.setVerticalGroup(
            PanelRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 22, Short.MAX_VALUE)
                        .addComponent(FieldSerach))
                    .addComponent(ButtonAddMovie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(movie, javax.swing.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelSR.add(PanelR, "card3");

        ButtonAddShow.setBackground(new java.awt.Color(72, 61, 139));
        ButtonAddShow.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonAddShow.setText("Dodaj Seans");
        ButtonAddShow.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ButtonAddShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonAddShowActionPerformed(evt);
            }
        });

        FieldSearch2.setText("Wyszukaj film");

        ButtonSearch2.setText("Szukaj");
        ButtonSearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSearch2ActionPerformed(evt);
            }
        });

        show.setBackground(new java.awt.Color(255, 255, 255));
        show.setForeground(new java.awt.Color(255, 255, 255));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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

        javax.swing.GroupLayout PanelSLayout = new javax.swing.GroupLayout(PanelS);
        PanelS.setLayout(PanelSLayout);
        PanelSLayout.setHorizontalGroup(
            PanelSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelSLayout.createSequentialGroup()
                        .addComponent(ButtonAddShow, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 590, Short.MAX_VALUE)
                        .addComponent(FieldSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSearch2)
                        .addGap(12, 12, 12))
                    .addGroup(PanelSLayout.createSequentialGroup()
                        .addComponent(show, javax.swing.GroupLayout.DEFAULT_SIZE, 1075, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        PanelSLayout.setVerticalGroup(
            PanelSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ButtonSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, Short.MAX_VALUE)
                        .addComponent(FieldSearch2))
                    .addComponent(ButtonAddShow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(show, javax.swing.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelSR.add(PanelS, "card3");

        javax.swing.GroupLayout repertuar_pLayout = new javax.swing.GroupLayout(repertuar_p);
        repertuar_p.setLayout(repertuar_pLayout);
        repertuar_pLayout.setHorizontalGroup(
            repertuar_pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(repertuar_pLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(sidemenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(repertuar_pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelSR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        repertuar_pLayout.setVerticalGroup(
            repertuar_pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(repertuar_pLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelSR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(sidemenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bg.add(repertuar_p);

        getContentPane().add(bg, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void repertuar_mMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repertuar_mMouseEntered
       repertuar_m.setBackground(new java.awt.Color(106,90,205));
    }//GEN-LAST:event_repertuar_mMouseEntered

    private void repertuar_mMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repertuar_mMouseExited
        repertuar_m.setBackground(new java.awt.Color(72,61,139));
    }//GEN-LAST:event_repertuar_mMouseExited

    private void seanse_mMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seanse_mMouseEntered
        seanse_m.setBackground(new java.awt.Color(106,90,205));
    }//GEN-LAST:event_seanse_mMouseEntered

    private void seanse_mMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seanse_mMouseExited
       seanse_m.setBackground(new java.awt.Color(72,61,139));
    }//GEN-LAST:event_seanse_mMouseExited

    private void ButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonSearchActionPerformed

    private void ImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ImageActionPerformed

    private void ButtonSearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSearch2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonSearch2ActionPerformed

    private void ButtonAddMovieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonAddMovieActionPerformed
        setTitle("Dodaj Film");
        AddMovie.setVisible(true);  // Pokazuje okno
    }//GEN-LAST:event_ButtonAddMovieActionPerformed

    private void ButtonAddShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonAddShowActionPerformed
        setTitle("Dodaj Seans");
        AddShow.setVisible(true);
    }//GEN-LAST:event_ButtonAddShowActionPerformed

    private void repertuar_mMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repertuar_mMouseClicked
        PanelS.setVisible(false);
        PanelR.setVisible(true);
        SeansLabel.setVisible(false);
        RepertuarLabel.setVisible(true);
    }//GEN-LAST:event_repertuar_mMouseClicked

    private void seanse_mMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seanse_mMouseClicked
        PanelR.setVisible(false);
        PanelS.setVisible(true);
        SeansLabel.setVisible(true);
        RepertuarLabel.setVisible(false);
    }//GEN-LAST:event_seanse_mMouseClicked



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame AddMovie;
    private javax.swing.JFrame AddShow;
    private javax.swing.JButton ButtonAddMovie;
    private javax.swing.JButton ButtonAddShow;
    private javax.swing.JButton ButtonSearch;
    private javax.swing.JButton ButtonSearch2;
    private javax.swing.JTextField Cast;
    private javax.swing.JFormattedTextField Date;
    private javax.swing.JSpinner Duration;
    private javax.swing.JTextField FieldSearch2;
    private javax.swing.JTextField FieldSerach;
    private javax.swing.JComboBox<String> Genre;
    private javax.swing.JButton Image;
    private javax.swing.JLabel LabelCast;
    private javax.swing.JLabel LabelDate;
    private javax.swing.JLabel LabelDuration;
    private javax.swing.JLabel LabelGenre;
    private javax.swing.JLabel LabelHall;
    private javax.swing.JLabel LabelMovie;
    private javax.swing.JLabel LabelMovieDescription;
    private javax.swing.JLabel LabelTime;
    private javax.swing.JLabel LabelTitle;
    private javax.swing.JTextField MovieDescription;
    private javax.swing.JPanel PanelR;
    private javax.swing.JPanel PanelS;
    private javax.swing.JPanel PanelSR;
    private javax.swing.JPanel Panel_AddMovie;
    private javax.swing.JPanel Panel_AddShow;
    private javax.swing.JLabel RepertuarLabel;
    private javax.swing.JLabel SeansLabel;
    private javax.swing.JLabel TextRepertuar;
    private javax.swing.JLabel TextSeanse;
    private javax.swing.JFormattedTextField Time;
    private javax.swing.JTextField Title;
    private javax.swing.JPanel bg;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JComboBox<String> idHall;
    private javax.swing.JComboBox<String> idMovie;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JScrollPane movie;
    private javax.swing.JPanel repertuar_m;
    private javax.swing.JPanel repertuar_p;
    private javax.swing.JPanel seanse_m;
    private javax.swing.JScrollPane show;
    private javax.swing.JPanel sidemenu;
    // End of variables declaration//GEN-END:variables
}
