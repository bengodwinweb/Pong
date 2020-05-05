package com.bengodwin.pong;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Ball extends Rectangle {

    public static final int WIDTH = 12;
    public static final int HALF_WIDTH = WIDTH / 2;
    public static final double X_VELOCITY = 6.5;

    private double xVelocity;
    private double yVelocity;

    public Ball(double x, double y, double xVelocity, double yVelocity) {
        super(x - HALF_WIDTH, y - HALF_WIDTH, WIDTH, WIDTH);
        this.setFill(Color.WHITE);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public Ball() {
        this(Main.HALF_W, Main.HALF_W, 0, 0);
    }

    public void moveTo(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }
}
