package com.bengodwin.pong;

import javafx.scene.control.Button;

public class DirectionsScreen extends OptionsScreen {

    private static final String pauseDirection = "Press <SPACE> to pause";
    private static final String onePlayerDirections = "Player 1:\n\tW - move up\n\tD - move down\n\n";
    private static final String twoPlayerDirections = "Player 2:\n\tUP - move up\n\tDOWN - move down\n\n";

    public DirectionsScreen() {
        super("directions", (Main.getGame().getGameMode() == GameMode.ONE_PLAYER ? onePlayerDirections : onePlayerDirections + twoPlayerDirections) + pauseDirection, GameStatus.INIT);
        addStartButton();
        addBackButton();
    }

    public void addStartButton() {
        Button startButton = new Button("START");
        startButton.setOnAction(e -> {
            removeFromScreen();
            Main.getGame().setGameStatus(GameStatus.ACTIVE);
            Main.getGame().serve();
        });
        addButton(startButton);
    }

    public void addBackButton() {
        Button backButton = new Button("BACK");
        backButton.setOnAction(e -> {
            removeFromScreen();
            new PickModeScreen().show();
        });
        addButton(backButton);
    }
}
