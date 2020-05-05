package com.bengodwin.pong;

import javafx.scene.control.Button;

public class PickModeScreen extends OptionsScreen {

    public PickModeScreen() {
        super("select game mode", null, GameStatus.INIT);
        addOnePlayerButton();
        addTwoPlayerButton();
    }

    private void addOnePlayerButton() {
        Button onePlayerButton = new Button("ONE PLAYER");
        onePlayerButton.setOnAction(e -> {
            Main.getGame().setGameMode(GameMode.ONE_PLAYER);
            removeFromScreen();
            new DirectionsScreen().show();
        });
        addButton(onePlayerButton);
    }

    private void addTwoPlayerButton() {
        Button twoPlayerButton = new Button("TWO PLAYER");
        twoPlayerButton.setOnAction(e -> {
            Main.getGame().setGameMode(GameMode.TWO_PLAYER);
            removeFromScreen();
            new DirectionsScreen().show();
        });
        addButton(twoPlayerButton);
    }
}
