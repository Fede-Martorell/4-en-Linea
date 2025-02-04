/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author feder
 */
import Controlador.Connect4Game;
import Modelo.HistorialJuego;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

/** Test setting Swing's JComponents properties and appearances */
@SuppressWarnings("serial")
public class GUI extends JFrame {
    private final Container cp;
    private final Connect4Game game;
    private HistorialJuego historialJuego = new HistorialJuego();

    // TODO: Get it from the board
    int rows;
    int columns;

    // TODO: Calculate them instead
    int windowWidth = 750;
    int windowHeight = 650;

    // Prepare ImageIcons to be used with JComponents
    private ImageIcon iconEmpty = null;
    private ImageIcon iconRed = null;
    private ImageIcon iconYellow = null;

    private final String title = "Connect 4 - ";

    private void mapUpdater(JButton button) {
        int row10plusCol = Integer.parseInt(button.getName());
        int col = row10plusCol % 10;

        boolean player1turn = game.Is1playing();
        if(player1turn) setTitle(title + "Yellow");
        else setTitle(title + "Red");

        int addedRow = game.round(col);

        if(addedRow != -1) {
            JButton buttonToUpdate = ((JButton)(cp.getComponent(columns * addedRow + col)));

            if(game.Is1playing()) buttonToUpdate.setIcon(iconYellow);
            else buttonToUpdate.setIcon(iconRed);

            // check for winner
            if(game.checkForWinnerInGUI(col)) {
                JOptionPane.showMessageDialog(null, "Has ganado!");
                JOptionPane.showMessageDialog(null, "Historial."+ historialJuego.leerHistorial());
                historialJuego.eliminarHistorial();
                int reply = JOptionPane.showConfirmDialog(null, "Quieres jugar nuevamente?", null, JOptionPane.YES_NO_OPTION);
                if(reply == JOptionPane.YES_OPTION) {
                    System.out.println("Intentando jugar nuevamente...");
                    game.reset(6, 7);
                    resetBoard();
                } else {
                    System.exit(0);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Porfavor selecciona una ubicacion valida.");
        }
    }

    public void resetBoard() {
        for(int row = 0; row < rows; row++)
            for (int col = 0; col < columns; col++)
                ((JButton)(cp.getComponent(columns * row + col))).setIcon(iconEmpty);
    }

    /** Constructor to setup the GUI */
    public GUI(boolean player1turn, Connect4Game game, int rows, int columns) {
        this.game = game;
        this.rows = rows;
        this.columns = columns;

        // Prepare Icons
        // Image path relative to the project root (i.e., bin)
        String imgEmptyFilename = "Imagenes/empty.png";
        URL imgURL = getClass().getClassLoader().getResource(imgEmptyFilename);
        if (imgURL != null) iconEmpty = new ImageIcon(imgURL);
        else System.err.println("No se pudo encontrar la imagen: " + imgEmptyFilename);

        String imgRedFilename = "Imagenes/red.png";
        imgURL = getClass().getClassLoader().getResource(imgRedFilename);
        if (imgURL != null) iconRed = new ImageIcon(imgURL);
        else System.err.println("No se pudo encontrar la imagen: " + imgRedFilename);


        String imgYellowFilename = "Imagenes/yellow.png";
        imgURL = getClass().getClassLoader().getResource(imgYellowFilename);
        if (imgURL != null) iconYellow = new ImageIcon(imgURL);
        else System.err.println("No se pudo encontrar la imagen: " + imgYellowFilename);

        cp = getContentPane();
        cp.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        for(int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                JButton button = new JButton(); // use setter to set text and icon
                button.setIcon(iconEmpty);
                button.setPreferredSize(new Dimension(100, 100));
                // row * 10 + col
                button.setName(Integer.toString((row * 10 + col)));

                button.addActionListener(actionEvent -> mapUpdater(((JButton) (actionEvent.getSource()))));
                cp.add(button);
            }
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if(!player1turn) setTitle(title + "Yellow");
        else setTitle(title + "Red");
        setLocationRelativeTo(null); // Centrado de Ejecutable en pantalla.
        setSize(windowWidth, windowHeight);
        setVisible(true);
    }
}