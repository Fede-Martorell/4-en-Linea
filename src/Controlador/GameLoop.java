
package Controlador;

import Vista.GUI;

/**
 *
 * @author feder
 */
public class GameLoop {
    private final Connect4Game game; // Objeto que representa la lógica del juego Conecta 4.
    private final GUI ourGUI; // Objeto que representa la interfaz gráfica del juego.

    /**
     * Constructor de GameLoop.
     * Inicializa el juego y la interfaz gráfica.
     */
    public GameLoop() {
        // Crea una nueva instancia del juego Conecta 4 con fichas "R" (rojo) y "Y" (amarillo),
        // y un tablero de 6 filas por 7 columnas.
        game = new Connect4Game("R", "Y", 6, 7);

        // Crea la interfaz gráfica y la enlaza con el juego.
        // Se pasa el turno actual del jugador 1 (Is1playing()), la instancia del juego
        // y las dimensiones del tablero.
        ourGUI = new GUI(game.Is1playing(), game, 6, 7);
    }
}