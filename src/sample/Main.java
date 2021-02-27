package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Main extends Application {

    ActivatingArrowThread activatingArrowThread;
    ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>(0);
    private VBox vBox;


    private boolean isActive = false;

    public VBox group2() {
        TextField textField = new TextField();


        textField.setMinWidth(250);
        Button button1 = new Button();
        button1.setMinWidth(125);
        button1.setMinHeight(50);
        Button button2 = new Button();
        button2.setMinWidth(125);
        button2.setMinHeight(50);


        HBox hBox = new HBox(button1, button2);
        VBox vBox = new VBox(textField, hBox);

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                button2.setText(textField.getText());
            }
        });
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String temp = button1.getText();
                button1.setText(button2.getText());
                button2.setText(temp);
            }
        });
        return vBox;
    }

    void timer() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }
    }

    Pair activate(int idx1, int idx2) {
        Pair idx = new Pair();

        idx.setFirst(idx1);
        idx.setSecond(idx2);

        if (!isActive) {
            return idx;
        }

        if (idx1 > idx2) return idx;
        else if (idx1 == idx2) {
            CheckBox first = checkBoxes.get(idx1);
            first.fire();
            timer();
            int temp = idx.getFirst();
            idx.setFirst(++temp);
            return idx;
        }
        CheckBox first = checkBoxes.get(idx1);
        first.fire();
        timer();
        CheckBox last = checkBoxes.get(idx2);
        last.fire();
        timer();
        return activate(idx1 + 1, idx2 - 1);
    }

    public VBox group10() {
        TextField textField = new TextField();
        textField.setMaxWidth(250);
        Button button1 = new Button();
        button1.graphicProperty().setValue(new ImageView("/images/button1_image.png"));

        Button button2 = new Button();
        button2.graphicProperty().setValue(new ImageView("/images/button2_image.png"));

        Button button3 = new Button();
        button3.graphicProperty().setValue(new ImageView("/images/button3_image.png"));

        HBox hBox = new HBox(button1, button2, button3);
        vBox = new VBox(textField, hBox);

        button1.setOnAction(actionEvent -> {
            isActive = false;
            if (activatingArrowThread != null) {
                timer();
                activatingArrowThread.interrupt();
            }
            int num = Integer.parseInt(textField.getText());
            for (CheckBox item : checkBoxes) {
                vBox.getChildren().remove(item);
            }
            checkBoxes = new ArrayList<CheckBox>(0);

            for (int i = 0; i < num; i++) {
                checkBoxes.add(new CheckBox());
                vBox.getChildren().add(checkBoxes.get(i));
            }


            activatingArrowThread = new ActivatingArrowThread(this, 0, num - 1);
        });
        button2.setOnAction(actionEvent -> {
            isActive = true;
            if (!activatingArrowThread.isAlive())
                activatingArrowThread.start();
        });
        button3.setOnAction(actionEvent -> {
            isActive = false;
        });
        return vBox;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("nice app");
        primaryStage.getIcons().add(new Image("/images/rainbow.png"));


        HBox hBox = new HBox(group2(), group10());

        int sym;
        System.out.println(sym);

        primaryStage.setScene(new Scene(hBox, 450, 450));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
