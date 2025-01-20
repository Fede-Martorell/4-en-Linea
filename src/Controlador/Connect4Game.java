
package Controlador;
import Modelo.Board;
import Modelo.HistorialJuego;
import Modelo.Piece;
import java.util.Random;

public class Connect4Game {
    private Board board;
    private final String color1;
    private final String color2;
    private HistorialJuego historial = new HistorialJuego();

    // true, es el turno player 1
    // false, es el turno del player 2
    private boolean is1playing;

    public boolean Is1playing() {
        return is1playing;
    }

    public Connect4Game(String color1, String color2, int rows, int columns) {
        this.board = new Board(rows, columns);
        this.color1 = color1;
        this.color2 = color2;

        is1playing = (new Random()).nextBoolean();
    }

    public int round(int col) {
        String color = is1playing ? color1 : color2;

        int row = board.addPiece(col, color);

        if(row != -1) is1playing = !is1playing;

        return row;
    }

    public boolean checkForWinnerInGUI(int column) {
        String winningColor;

        // Invertimos el turno de juego, debido a la informacion tardia.
        if(!is1playing) {
            winningColor = color1;
        } else {
            winningColor = color2;
        }

        return checkForWinner(column, winningColor);
    }

    private boolean checkDiagonal(int row, int col, String winningColor, boolean rightDiagonal) {
        int winningStreak = 4;
        int reverser = rightDiagonal ? 1 : -1;
        int rows = board.getRows();
        int columns = board.getColumns();
        Piece[][] ourBoard = board.getOurBoard();

        for(int winRow = row - 3, winCol = col - (3 * reverser); winRow <= row + 3; winRow++, winCol += reverser) {
            if (!rightDiagonal) {
                if (winRow < 0 || winCol < 0) continue;
                if (winRow >= rows || winCol >= columns) break;
            } else {
                if(winRow < 0 || winCol >= columns) continue;
                if(winRow >= rows || winCol < 0) break;
            }

            if(ourBoard[winRow][winCol] != null && ourBoard[winRow][winCol].getColor().equals(winningColor)) {
                if (--winningStreak == 0) return true;
            } else winningStreak = 4;
        }
        return false;
    }

    public boolean checkForWinner(int col, String winningColor) {
        int rows = board.getRows();
        int columns = board.getColumns();
        Piece[][] ourBoard = board.getOurBoard();

        for(int row = 0; row < rows; row++) {
            if (ourBoard[row][col] != null) {
                // Si esta variable llega a 0, Ganamos.
                int winningStreak = 3;

                // Control Vertical.
                for (int winRow = row + 1; winRow < rows; winRow++) {
                    if (ourBoard[winRow][col].getColor().equals(winningColor)) {
                        winningStreak--;
                        if (winningStreak == 0) return true;
                    } else winningStreak = 3;
                }

                winningStreak = 4;
                // Control Horizontal.
                for (int winCol = col - 3; winCol <= col + 3; winCol++) {
                    if (winCol < 0) continue;
                    if (winCol >= columns) break;

                    if (ourBoard[row][winCol] != null && ourBoard[row][winCol].getColor().equals(winningColor)) {
                        winningStreak--;
                        if (winningStreak == 0) return true;
                    } else winningStreak = 4;
                }

                //Gracias a un mismo control dependiendo del resultado podemos obtener que diagonal estamos obteniendo.
                // si el resultado es falso, la diagonal va a ser hacia la izquierda.
                // si el resultado es verdadero, la diagonal va a ser hacia la derecha.
                //Diagonal derecha (rightDiagonal = true)    Diagonal izquierda (rightDiagonal = false)
                //      ↗                                         ↖
                //    ↗                                             ↖
                //  ↗                                                 ↖
                //X (punto inicial)                                     X (punto inicial)
                if (checkDiagonal(row, col, winningColor, false)) return true;
                if (checkDiagonal(row, col, winningColor, true)) return true;

                historial.guardarHistorial(winningColor);

                return false;
            }
        }
        return false;
    }

    public void reset(int rows, int columns) {
        this.board = new Board(rows, columns);
        is1playing = (new Random()).nextBoolean();
    }
}
