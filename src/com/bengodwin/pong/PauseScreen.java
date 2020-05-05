package com.bengodwin.pong;

import javafx.scene.control.Button;

public class PauseScreen extends OptionsScreen {

    public PauseScreen() {
        super("PAUSED", null, GameStatus.PAUSED);
        addResumeButton();
        addQuitButton();
    }

    private void addResumeButton() {
        Button resumeButton = new Button("RESUME");
        resumeButton.setOnAction(e -> {
            Main.getGame().setGameStatus(GameStatus.ACTIVE);
            Main.getGame().serve();
            removeFromScreen();
        });
        addButton(resumeButton);
    }

    private void addQuitButton() {
        Button quitButton = new Button("END GAME");
        quitButton.setOnAction(e -> {
            removeFromScreen();
            new EndGameScreen(Main.getGame().getWinner()).show();
        });
        addButton(quitButton);
    }
}
