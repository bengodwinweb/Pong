package com.bengodwin.pong;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class OptionsScreen extends Pane {

    private VBox vbox;
    private HBox buttonBox;

    public OptionsScreen(String title, String text, GameStatus gameStatus) {
        this.vbox = new VBox();
        this.buttonBox = new HBox();
        Main.getGame().setGameStatus(gameStatus);

        vbox.setSpacing(45);
        vbox.setPrefWidth(Main.W);
        vbox.setPrefHeight(Main.H);
        vbox.setAlignment(Pos.CENTER);
        vbox.setBackground(null);

        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);

        Label titleLabel = new Label(title.toUpperCase());
        titleLabel.setFont(new Font("Arial", 36));
        titleLabel.setTextFill(Color.WHITE);

        Label textLabel = new Label(text);
        textLabel.setFont(new Font("Arial", 18));
        textLabel.setTextFill(Color.WHITE);

        vbox.getChildren().addAll(titleLabel, textLabel, buttonBox);
    }

    protected void addButton(Button button) {
        buttonBox.getChildren().add(button);
    }

    public void show() {
        Main.getGame().hideElements();
        Main.getRoot().getChildren().add(vbox);
    }

    public void removeFromScreen() {
        Main.getGame().showElements();
        Main.getRoot().getChildren().remove(vbox);
    }
}
