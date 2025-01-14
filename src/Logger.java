import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static BufferedWriter writer;

    // Statyczny blok do otwierania pliku logów
    static {
        try {
            writer = new BufferedWriter(new FileWriter("Logger.txt", true)); // Tryb dopisywania
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Statyczna metoda do logowania wiadomości
    public static void log(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Statyczna metoda zamykająca plik logów
    public static void close() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}