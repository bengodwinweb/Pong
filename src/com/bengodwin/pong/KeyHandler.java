package com.bengodwin.pong;

import javafx.scene.input.KeyEvent;

public class KeyHandler {

    private Pong game;
    private Paddle left;
    private Paddle right;

    public KeyHandler() {
        this.game = Main.getGame();
        this.left = game.getLeftPaddle();
        this.right = game.getRightPaddle();
    }

    public void handleKeyPressed(KeyEvent e) {
        boolean twoPlayer = game.getGameMode() == GameMode.TWO_PLAYER;
        switch (e.getCode()) {
            case W: left.moveUp(); break;
            case S: left.moveDown(); break;
            case UP: if(twoPlayer) right.moveUp(); break;
            case DOWN: if(twoPlayer) right.moveDown(); break;
            case SPACE:
                if (game.getGameStatus() == GameStatus.PAUSED || game.getGameStatus() == GameStatus.ACTIVE) game.pause();
                break;
        }
    }

    public void handleKeyReleased(KeyEvent e) {
        boolean twoPlayer = game.getGameMode() == GameMode.TWO_PLAYER;
        switch (e.getCode()) {
            case W: if(left.getVelocity() < 0) left.stop(); break;
            case S: if(left.getVelocity() > 0) left.stop(); break;
            case UP: if (twoPlayer && right.getVelocity() < 0) right.stop(); break;
            case DOWN: if (twoPlayer && right.getVelocity() > 0) right.stop(); break;
        }
    }
}
