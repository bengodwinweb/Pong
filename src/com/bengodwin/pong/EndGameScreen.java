package com.bengodwin.pong;

import javafx.scene.control.Button;

public class EndGameScreen extends OptionsScreen {

    public EndGameScreen(String winnerText) {
        super("GAME OVER", winnerText, GameStatus.FINISHED);
        addPlayAgainButton();
        addChangeModeButton();
    }

    public void addPlayAgainButton() {
        Button playAgainButton = new Button("PLAY AGAIN");
        playAgainButton.setOnAction(e -> {
            removeFromScreen();
            Main.getGame().setGameStatus(GameStatus.ACTIVE);
            Main.getGame().reset();
            Main.getGame().serve();
        });
        addButton(playAgainButton);
    }

    public void addChangeModeButton() {
        Button changeModeButton = new Button("CHANGE MODE");
        changeModeButton.setOnAction(e -> {
            removeFromScreen();
            Main.getGame().reset();
            new PickModeScreen().show();
        });
        addButton(changeModeButton);
    }
}
