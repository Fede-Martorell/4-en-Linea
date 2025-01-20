
package Modelo;

public class Board {

    private int rows;
    private int columns;

    Piece [][]ourBoard;

    public Piece[][] getOurBoard() {
        return ourBoard;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int addPiece(int colToAdd, String color) {
        // Dentro del rango normal
        if(colToAdd >= 0 && colToAdd < columns) {
            // podemos agregar
            if(ourBoard[0][colToAdd] == null) {
                boolean addedThePiece = false;
                int addedRow = -1;
                for(int row = rows - 1; row >= 0; row--)
                    if(ourBoard[row][colToAdd] == null) {
                        ourBoard[row][colToAdd] = new Piece();
                        ourBoard[row][colToAdd].setColor(color);
                        addedThePiece = true;
                        addedRow = row;
                        break;
                    }
                return addedRow;
            } else {
                // esa fila esta llena
                System.err.println("Esta columna esta llena, porfavor escoja otra.");
                return -1;
            }
        } else {
            // Fuera de rango
            System.err.println("El numero que estas probando no es soportado, porfavor elija uno dentro de los limites.");
            return -1;
        }
    }

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        ourBoard = new Piece[rows][columns];
        for(int row = 0; row < rows; row++)
            for(int col = 0; col < columns; col++)
                ourBoard[row][col] = null;
    }
}
