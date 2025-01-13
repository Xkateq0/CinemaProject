import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class CManage<T extends CBase> {

    private final Map<Integer, T> baza = new HashMap<>();
    private final String fileName;

    // Konstruktor wczytuje dane z pliku, jeśli istnieje
    public CManage(Class<T> clazz) {
        this.fileName ="src/BazaDannych/" + clazz.getSimpleName() + ".txt"; // Nazwa pliku oparta na nazwie klasy
        //System.out.println(this.fileName);

        File file = new File(fileName);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        // Tworzymy instancję klasy T
                        T entity = clazz.getDeclaredConstructor().newInstance();
                        // Deserializujemy dane do obiektu
                        entity.deserialize(line);
                        // Dodajemy obiekt do bazy
                        baza.put(entity.getId(), entity);
                    } catch (Exception e) {
                        System.err.println("Błąd podczas wczytywania obiektu: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("Błąd podczas odczytu pliku: " + e.getMessage());
            }
        }
    }

    // Pobiera obiekt na podstawie jego ID.
    public T getById(int id) {
        T entity = baza.get(id);
        if (entity != null) {
            return entity;
        } else {
            throw new IllegalArgumentException("Nie znaleziono obiektu w bazie o ID: " + id);
        }
    }

    // Zapisuje obiekt do bazy danych.
    public void save(T entity) {
        if (entity.getId() == -1) {
            // Jeśli ID nie zostało ustawione, przydzielamy nowe
            int newId = baza.isEmpty() ? 1 : baza.keySet().stream().max(Integer::compare).get() + 1;
            entity.setId(newId);
        }
        baza.put(entity.getId(), entity);
    }

    // Usuwa obiekt z bazy danych.
    public void remove(T entity) {
        baza.remove(entity.getId());
    }

    // Zwraca wszystkie obiekty w bazie danych w postaci listy.
    public List<T> getAll() {
        return new ArrayList<>(baza.values());
    }

    // Zapisuje wszystkie obiekty w bazie danych do pliku.
    public void close() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (T entity : baza.values()) {
                writer.write(entity.serialize());
                writer.newLine();
            }
        }
    }


}
