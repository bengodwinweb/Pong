package com.bengodwin.pong;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.shape.*;
import javafx.scene.text.Font;

import java.util.*;

public class Pong {

    public static final int WINNING_SCORE = 10;

    private int leftScore;
    private int rightScore;
    private boolean serveLeft;
    private GameMode gameMode;
    private GameStatus gameStatus;

    private Pane root;
    private Label leftScoreLabel;
    private Label rightScoreLabel;
    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private Ball ball;
    private Path middleLine;
    private boolean ballInPlay;

    public Pong() {
        this.root = Main.getRoot();
    }

    public void play() {
        this.gameStatus = GameStatus.INIT;
        this.leftScore = 0;
        this.rightScore = 0;
        this.serveLeft = true;
        this.ballInPlay = false;

        root.getChildren().clear();

        middleLine = new Path(new MoveTo(Main.HALF_W, Main.MIN), new VLineTo(Main.H));
        middleLine.setStrokeWidth(2);
        middleLine.setStroke(Color.WHITE);
        middleLine.getStrokeDashArray().addAll(10d, 8d);

        leftPaddle = new Paddle();
        leftPaddle.setX(Paddle.OFFSET);

        rightPaddle = new Paddle();
        rightPaddle.setX(Main.W - Paddle.OFFSET - Paddle.WIDTH);

        HBox labelsBox = new HBox();
        labelsBox.setBackground(null);
        labelsBox.setLayoutX(Main.HALF_W - 50);
        labelsBox.setAlignment(Pos.CENTER);
        labelsBox.setSpacing(60);
        labelsBox.setPrefHeight(40);

        leftScoreLabel = new Label();
        leftScoreLabel.setBackground(null);
        leftScoreLabel.setTextFill(Color.WHITE);
        leftScoreLabel.setText("0");
        leftScoreLabel.setFont(new Font("Arial", 36));

        rightScoreLabel = new Label();
        rightScoreLabel.setTextFill(Color.WHITE);
        rightScoreLabel.setText("0");
        rightScoreLabel.setFont(new Font("Arial", 36));

        labelsBox.getChildren().addAll(rightScoreLabel, leftScoreLabel);
        root.getChildren().addAll(middleLine, leftPaddle, rightPaddle, labelsBox);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (gameStatus == GameStatus.ACTIVE) {
                    leftPaddle.move();
                    rightPaddle.move();
                    if (ballInPlay) moveBall();
                    if (gameMode == GameMode.ONE_PLAYER) moveComputerPaddle();
                }
            }
        };
        timer.start();

        new PickModeScreen().show();
    }

    public void pause() {
        new PauseScreen().show();
    }

    public void serve() {
        if (gameStatus != GameStatus.ACTIVE) return;
        if (ballInPlay) return;

        double x = Main.HALF_W;
        double y = (new Random()).nextInt((int) Main.HALF_H / 2) + Main.HALF_H / 2;

        double xVelocity = serveLeft ? -Ball.X_VELOCITY : Ball.X_VELOCITY;
        double yVelocity = (new Random()).nextInt(3) + 1;

        ball = new Ball(x, y, xVelocity, yVelocity);
        root.getChildren().add(ball);
        ballInPlay = true;
    }

    private void moveBall() {
        double newX = ball.getX() + ball.getxVelocity();
        double newY = ball.getY() + ball.getyVelocity();

        if (newX <= 0) {
            endPoint(true);
            return;
        }
        if (newX + Ball.WIDTH >= Main.W) {
            endPoint(false);
            return;
        }

        if (newY < 0) {
            newY = Math.abs(newY);
            ball.setyVelocity(-ball.getyVelocity());
        }
        if (newY + Ball.WIDTH > Main.H) {
            newY = Main.H - (newY + Ball.WIDTH - Main.H) - Ball.WIDTH;
            ball.setyVelocity(-ball.getyVelocity());
        }

        boolean leftCollisionPossible = ball.getX() >= Paddle.OFFSET && ball.getX() <= Paddle.OFFSET + Paddle.WIDTH && ball.getxVelocity() < 0;
        boolean rightCollisionPossible = ball.getX() + Ball.WIDTH <= Main.W - Paddle.OFFSET && ball.getX() + Ball.WIDTH >= Main.W - Paddle.OFFSET - Paddle.WIDTH && ball.getxVelocity() > 0;

        if (leftCollisionPossible) checkCollision(leftPaddle);
        if (rightCollisionPossible) checkCollision(rightPaddle);

        ball.moveTo(newX, newY);
    }

    private void moveComputerPaddle() {
        double ballLocation = ball.getY() + Ball.HALF_WIDTH;
        double paddleMid = rightPaddle.getY() + Paddle.HALF_HEIGHT;

        if (ballInPlay) {
            if (ballLocation < rightPaddle.getY()) rightPaddle.moveUp();
            else if (ballLocation >= rightPaddle.getY() && ballLocation < paddleMid) rightPaddle.moveUpSlow();
            else if (ballLocation >= rightPaddle.getY() && ballLocation < rightPaddle.getY() + Paddle.HEIGHT) rightPaddle.moveDownSlow();
            else rightPaddle.moveDown();
        } else {
            double bottomThird = Main.H - Main.H / 3;
            if (paddleMid < bottomThird) rightPaddle.moveDownSlow();
            else if (paddleMid == bottomThird) rightPaddle.stop();
            else rightPaddle.moveUpSlow();
        }
    }

    private void checkCollision(Paddle paddle) {
        Point2D upperLeft = new Point2D(ball.getX(), ball.getY());
        Point2D upperRight = new Point2D(ball.getX() + Ball.WIDTH, ball.getY());
        Point2D lowerLeft = new Point2D(ball.getX(), ball.getY() + Ball.WIDTH);
        Point2D lowerRight = new Point2D(ball.getX() + Ball.WIDTH, ball.getY() + Ball.WIDTH);

        Point2D[] corners = {upperLeft, upperRight, lowerLeft, lowerRight};

        for (Point2D corner : corners) {
            if (paddle.contains(corner)) {
                ball.setxVelocity(-ball.getxVelocity());
                ball.setyVelocity((ball.getY() - (paddle.getY() + Paddle.HALF_HEIGHT)) / 8);
                return;
            }
        }
    }

    public void endPoint(boolean left) {
        ballInPlay = false;
        root.getChildren().remove(ball);

        serveLeft = !left;

        if (left) incrementLeftScore();
        else incrementRightScore();

        if (leftScore == WINNING_SCORE || rightScore == WINNING_SCORE) endGame();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> serve());
            }
        }, 500);
    }

    public void incrementRightScore() {
        this.rightScore += 1;
        rightScoreLabel.setText(Integer.toString(rightScore));
    }

    public void incrementLeftScore() {
        this.leftScore += 1;
        leftScoreLabel.setText(Integer.toString(leftScore));
    }

    public void endGame() {
        new EndGameScreen(getWinner()).show();
    }

    public String getWinner() {
        if (leftScore == rightScore) return "Game was a draw";
        if (leftScore > rightScore) return "Player 2 wins";
        return "Player 1 wins";
    }

    public void reset() {
        root.getChildren().remove(ball);
        ballInPlay = false;

        this.rightScore = 0;
        this.leftScore = 0;
        leftScoreLabel.setText("0");
        rightScoreLabel.setText("0");
    }

    public void hideElements() {
        middleLine.setVisible(false);
        if (ballInPlay) ball.setVisible(false);
    }

    public void showElements() {
        middleLine.setVisible(true);
        if (ballInPlay) ball.setVisible(true);
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public Paddle getLeftPaddle() {
        return leftPaddle;
    }

    public Paddle getRightPaddle() {
        return rightPaddle;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public boolean ballInPlay() {
        return ballInPlay;
    }
}

