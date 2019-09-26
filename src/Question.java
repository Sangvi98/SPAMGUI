import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Question {
    public String question;
    public String option1;
    public String option2;
    public String option3;
    public String option4;
    public Button submitq1;
    public int correctOption;
    public String optionString;

    public int answerChosen;
    public boolean alreadyChosen;
    public double firstChoice;
    public double readyTime;
    public double submitTime;
    public boolean answerSelected = false;
    public RadioButton q1option1, q1option2, q1option3, q1option4;


    public Stopwatch qTimer = new Stopwatch();
    public Question(String question, String option1, String option2, String option3, String option4,
                    int correctOption){
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctOption = correctOption;
        this.alreadyChosen = false;
        this.answerChosen = 0;


    }

    public VBox run() {
        Label question1 = new Label(question);
        question1.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        submitq1 = new Button();
        submitq1.setDisable(true);
        submitq1.setText("Submit");
        q1option1 = new RadioButton(option1);
        q1option1.setStyle("-fx-font-family: Arial;" +
                "-fx-font-size: 30");
        q1option1.setPadding(new Insets(30));
        q1option2 = new RadioButton(option2);
        q1option2.setStyle("-fx-font-family: Arial;" +
                "-fx-font-size: 30");
        q1option2.setPadding(new Insets(30));
        q1option3 = new RadioButton(option3);
        q1option3.setStyle("-fx-font-family: Arial;" +
                "-fx-font-size: 30");
        q1option3.setPadding(new Insets(30));
        q1option4 = new RadioButton(option4);
        q1option4.setStyle("-fx-font-family: Arial;" +
                "-fx-font-size: 30");
        q1option4.setPadding(new Insets(30));

        ToggleGroup tg = new ToggleGroup();
        q1option1.setToggleGroup(tg);
        q1option2.setToggleGroup(tg);
        q1option3.setToggleGroup(tg);
        q1option4.setToggleGroup(tg);

        q1option1.setOnAction(e -> {
            submitq1.setDisable(false);
            answerSelected = true;
            if (!alreadyChosen) {
                firstChoice = qTimer.stop();
                qTimer.start();
                alreadyChosen = true;
            }

            answerChosen = 1;

            if (correctOption == 1) {
                optionString = option1 + "/ correct";
            }
            else {
                optionString = option1 + "/ incorrect";
            }
        });
        q1option2.setOnAction(e -> {
            submitq1.setDisable(false);
            answerSelected = true;

            if (!alreadyChosen) {
                firstChoice = qTimer.stop();
                qTimer.start();
                alreadyChosen = true;
            }

            answerChosen = 2;

            if (correctOption == 2) {
               optionString = option2 + "/ correct";
            }
            else {
                optionString = option2 + "/ incorrect";
            }
        });
        q1option3.setOnAction(e -> {
            answerSelected = true;
            submitq1.setDisable(false);

            if (!alreadyChosen) {
                firstChoice = qTimer.stop();
                qTimer.start();
                alreadyChosen = true;
            }

            answerChosen = 3;

            if (correctOption == 3) {
                optionString = option3 + "/ correct";
            }
            else {
                optionString = option3 + "/ incorrect";
            }
        });
        q1option4.setOnAction(e -> {
            answerSelected = true;
            submitq1.setDisable(false);

            if (!alreadyChosen) {
                firstChoice = qTimer.stop();
                qTimer.start();
                alreadyChosen = true;
            }

            answerChosen = 4;

            if (correctOption == 4) {
                optionString = option4 + "/ correct";
            }
            else {
                optionString = option4 + "/ incorrect";
            }
        });


        //writing the submitted answer to the file


        //writing the time that submit is pressed to the file


        VBox question1layout = new VBox();
        question1layout.getChildren().addAll(question1, q1option1, q1option2, q1option3, q1option4);
        question1layout.setAlignment(Pos.CENTER);
        //Scene q1Scene = new Scene(question1layout);



        return question1layout;
    }

    public void startTimer() {
        qTimer.start();
    }




}
