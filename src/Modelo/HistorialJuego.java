package Modelo;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class HistorialJuego {
    private static final String nombreArchivo = "historial_juego.txt";

    public void guardarHistorial(String winningColor) {
        // Registramos cada movimiento del juego.
        try(FileWriter writer = new FileWriter(nombreArchivo, true)){
            String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("Fecha: " + fechaHora + "\n");
            writer.write("Resultado: " + winningColor + "\n");
            writer.write("----------------------------\n");
        } catch (IOException e) {
            System.err.println("Error al guardar el historial: " + e.getMessage());
        }
    }

    public String leerHistorial() {
        StringBuilder historial = new StringBuilder();
        try (BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while((linea = lector.readLine()) != null) {
                historial.append(linea).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error al leer el historial: " + e.getMessage());
        }
        return historial.toString();
    }

    public void eliminarHistorial() {
        try(PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            writer.print("");
        } catch (IOException e) {
            System.err.println("Error al eliminar el historial: " + e.getMessage());
        }
    }
}
