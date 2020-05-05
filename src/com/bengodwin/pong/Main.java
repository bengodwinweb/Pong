package com.bengodwin.pong;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public final static double W = 650, H = 480, MIN = 0;
    public final static double HALF_W = W / 2, HALF_H = H / 2;

    private static Pane root;
    private static Pong game;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new Pane();
        root.setBackground(null);
        Scene scene = new Scene(root, W, H, Color.BLACK);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        game = new Pong();
        game.play();

        KeyHandler handler = new KeyHandler();
        scene.setOnKeyPressed(handler::handleKeyPressed);
        scene.setOnKeyReleased(handler::handleKeyReleased);

        primaryStage.setTitle("Pong");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static Pane getRoot() {
        return root;
    }

    public static Pong getGame() {
        return game;
    }
}
