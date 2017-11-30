/*
 * CSC-225 - Advanced JAVA Programming
 * Project 8 - TicTacToe Lab Assignment
 * Class Description : This is the TicTacToe program
 * Author            : Original Author Unknown - Code supplied in class
 * Modified by       : Jacob Johncox, Benjamin Kleynhans
 * Date              : Novermber 30, 2017
 * Filename          : TicTacToe.java
 */
package tictactoe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * <h1>
 * CSC-225 - Advanced JAVA Programming - TicTacToe Lab Assignment
 * </h1>
 * <p>
 * This is the TicTacToe program
 * </p>
 *
 * @author Jacob Johncox (M00864758)
 * @author Benjamin Kleynhans (M00858194)
 *
 * @version 1.0
 * @since November 30, 2017
 */
public class TicTacToe extends Application {

    private char whoseTurn = 'X';                                               // Indicate which player has a trun, initially it is the X player
    private Cell[][] cell = new Cell[3][3];                                     // Create and initialize cell
    private Label lblStatus = new Label("'X's turn to play");                   // Create and initialize a status label

    @Override                                                                   // Override the start method in the Application class
    public void start (Stage primaryStage) {
        GridPane pane = new GridPane();                                         // Pane to hold cell
        for (int i = 0; i < 3; i++) {                                           // Loop through 2D array to add panes
            for (int j = 0; j < 3; j++) {
                pane.add(cell[i][j] = new Cell(), j, i);
            }
        }

        BorderPane borderPane = new BorderPane();                               // Create a new borderpane
        borderPane.setCenter(pane);                                             // Add gridpane to borderpane
        borderPane.setBottom(lblStatus);                                        // Add label to bottom of borderpane

        Scene scene = new Scene(borderPane, 450, 170);                          // Create a scene and place it in the stage
        primaryStage.setTitle("TicTacToe");                                     // Set title of program
        primaryStage.setScene(scene);                                           // Place scene in stage
        primaryStage.show();                                                    // Display stage
    }

    public boolean isFull () {                                                  // Determine if the cells are all occupied
        for (int i = 0; i < 3; i++) {                                           // Loop through 2D array to add panes
            for (int j = 0; j < 3; j++) {
                if (cell[i][j].getToken() == ' ') {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Determine if the player with the specified token wins
     *
     * @param token value of cell clicked on
     *
     * @return true if the game is won or false if the game is not won
     */
    public boolean isWon (char token) {

        for (int i = 0; i < 3; i++) {
            if (cell[i][0].getToken() == token                                  // Check in the designated positions for tokens
                && cell[i][1].getToken() == token
                && cell[i][2].getToken() == token) {
                return true;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (cell[0][j].getToken() == token                                  // Check in the designated positions for tokens
                && cell[1][j].getToken() == token
                && cell[2][j].getToken() == token) {
                return true;
            }
        }

        if (cell[0][0].getToken() == token                                      // Check in the designated positions for tokens
            && cell[1][1].getToken() == token
            && cell[2][2].getToken() == token) {
            return true;
        }

        if (cell[0][2].getToken() == token                                      // Check in the designated positions for tokens
            && cell[1][1].getToken() == token
            && cell[2][0].getToken() == token) {
            return true;
        }

        return false;

    }

    public class Cell extends Pane {                                            // An inner class for a cell

        private char token = ' ';                                               // Token used for this cell

        public Cell () {
            setStyle("-fx-border-color: black");
            this.setPrefSize(2000, 2000);
            this.setOnMouseClicked(e -> handleMouseClick());
        }

        public char getToken () {                                                // Return token
            return token;
        }

        public void setToken (char c) {                                         // Set a new token
            token = c;

            if (token == 'X') {                                                 // Check if the token is for the X player
                Line line1 = new Line(                                          // Define the size of the cell
                        10,
                        10,
                        this.getWidth() - 10,
                        this.getHeight() - 10);

                line1.endXProperty().bind(this.widthProperty().subtract(10));
                line1.endYProperty().bind(this.heightProperty().subtract(10));

                Line line2 = new Line(                                          // Define the size of the cell
                        10,
                        this.getHeight() - 10,
                        this.getWidth() - 10,
                        10);

                line2.startYProperty().bind(this.heightProperty().subtract(10));
                line2.endXProperty().bind(this.widthProperty().subtract(10));

                this.getChildren().addAll(line1, line2);                        // Add the lines to the pane
            } else if (token == 'O') {                                          // Check if the token is for the O player
                Ellipse ellipse = new Ellipse(
                        this.getWidth() / 2,
                        this.getHeight() / 2,
                        this.getWidth() / 2 - 10,
                        this.getHeight() / 2 - 10);

                ellipse.centerXProperty().bind(this.widthProperty().divide(2)); // Set XY coordinates of cells
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                ellipse.setStroke(Color.BLACK);                                 // Set color of characters on the board
                ellipse.setFill(Color.WHITE);

                getChildren().add(ellipse);                                     // Add an ellipse object
            }
        }

        private void handleMouseClick () {                                       // Handle a mouse click event

            if (token == ' ' && whoseTurn != ' ') {                             // If cell is empty and game is not over
                setToken(whoseTurn);
            }

            if (isWon(whoseTurn)) {
                lblStatus.setText(whoseTurn + " won! The game is over");
                whoseTurn = ' ';                                                // Game is over
            } else if (isFull()) {
                lblStatus.setText("Draw! The game is over");
                whoseTurn = ' ';                                                // Game is over
            } else {
                whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';                     // Change the turn
                lblStatus.setText(whoseTurn + "'s turn");                       // Display whose turn
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main (String[] args) {
        launch(args);
    }

}
