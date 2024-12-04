import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
public class CManage <T extends CBase> {

    private Map<Integer, T> baza = new HashMap<>();
    private String fileName;

    //Konstruktor wczytuje dane z pliku jesli istnieje
    public CManage(Class<T> clazz) throws IOException {
        if (!CBase.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Class is not a subclass of CBase");

        }
        fileName = clazz.getSimpleName() + ".txt";
        loadDatabase();
    }

    //Odczytywanie danych z pliku
    private void loadDatabase() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    T entity = deserialize(line);
                    save(entity);
                }
            }
        } catch (FileNotFoundException e) {
        }
    }


    /**
     * Pobiera obiekt na podstawie jego ID.
     * @param id identyfikator obiektu
     * @return obiekt o zadanym ID
     * @throws IllegalArgumentException jeśli obiekt o podanym ID nie istnieje
     */
    public T getById(int id) {
        T entity = baza.get(id);
        if (entity != null) {
            return entity;
        } else {
            throw new IllegalArgumentException("Nie znaleziono obiektu w bazie o ID: " + id);
        }
    }
    /**
     * Zapisuje obiekt do bazy danych.
     * Jeżeli obiekt nie ma przypisanego id, nadaje mu nowe unikalne id.
     * @param entity obiekt do zapisania
     */
    public void save(T entity) {
        if (entity.getId() == -1) {
            // Jeśli ID nie zostało ustawione, przydzielamy nowe
            int newId = baza.isEmpty() ? 1 : baza.keySet().stream().max(Integer::compare).get() + 1;
            entity.setId(newId);
        }
        baza.put(entity.getId(), entity);
    }

    /**
     * Usuwa obiekt z bazy danych.
     * @param entity obiekt do usunięcia
     */
    public void remove(T entity) {
        baza.remove(entity.getId());
    }

    /**
     * Zwraca wszystkie obiekty w bazie danych w postaci listy.
     * @return lista wszystkich obiektów
     */
    public List<T> getAll() {
        return new ArrayList<>(baza.values());
    }

    /**
     * Zapisuje wszystkie obiekty w bazie danych do pliku.
     * @throws IOException błąd związany z operacjami na pliku
     */
    public void close() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (T entity : baza.values()) {
                writer.write(entity.serialize());
                writer.newLine();
            }
        }
    }

    /**
     * Deserializuje obiekt z linii tekstu.
     * @param data linia tekstu zawierająca dane do deserializacji
     * @return obiekt typu T
     */
    private T deserialize(String data) {
        // Załóżmy, że klasy dziedziczące po CBase implementują deserializację
        // Przykład: wywołanie MojObiekt.deserialize(data) w przypadku obiektów typu MojObiekt
        // W zależności od typu T, odpowiednia metoda deserialize będzie wywoływana
        throw new UnsupportedOperationException("Metoda deserializacji powinna być zaimplementowana w klasach dziedziczących.");
    }
}