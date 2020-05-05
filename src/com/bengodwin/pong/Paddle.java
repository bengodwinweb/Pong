package com.bengodwin.pong;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {

    public static final int WIDTH = 10;
    public static final int HALF_WIDTH = WIDTH / 2;
    public static final int HEIGHT = 80;
    public static final int HALF_HEIGHT = HEIGHT / 2;
    public static final int OFFSET = 16;
    public static final double VELOCITY = 4;
    public static final double SLOW_VELOCITY = VELOCITY / 3;

    private double velocity = 0;

    public Paddle() {
        super(WIDTH, HEIGHT, Color.WHITE);
        setY(Main.HALF_H - HALF_HEIGHT);
    }

    public void moveUp() {
        velocity = -VELOCITY;
    }

    public void moveUpSlow() {
        velocity = -SLOW_VELOCITY;
    }

    public void stop() {
        velocity = 0;
    }

    public void moveDown() {
        velocity = VELOCITY;
    }

    public void moveDownSlow() {
        velocity = SLOW_VELOCITY;
    }

    public void move() {
        double newPosition = getY() + velocity;
        if (newPosition <= Main.MIN) setY(Main.MIN);
        if (newPosition >= Main.H - HEIGHT) setY(Main.H - HEIGHT);
        else setY(newPosition);
    }

    public double getVelocity() {
        return velocity;
    }
}
