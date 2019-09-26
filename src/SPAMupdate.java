import com.opencsv.CSVWriter;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static javafx.application.Application.launch;


public class SPAMupdate extends Application{
    public Stopwatch readyTimer = new Stopwatch();
    public Stopwatch submitTimer = new Stopwatch();
    public Stopwatch playTimer = new Stopwatch();
    private FileWriter writer ;
    public StringBuilder sb = new StringBuilder();
    public StringBuilder header = new StringBuilder();
    public Question currentQuestion;
    private Main CurrentMain;
    Button readyButton;
    Stage primaryStage;
    Region spacer1;
    Region spacer2;
    Queue<Question> questions;
    Button submitButton;
    Button continueButton;
    Button playButton;
    Button closeAppButton;
    PauseTransition wait;


    //arraylist that contains the info to be added to the csv file
    ArrayList<String> participantInfo = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        this.primaryStage = primaryStage;

        //creating the window for the researcher to enter the participant
        //number and their name
        primaryStage.setTitle("Information");
        primaryStage.setMaximized(true);
        Button nextButton = new Button();
        nextButton.setAlignment(Pos.CENTER);
        nextButton.setText("Next");
        nextButton.setMinSize(50, 30 );

        //information/fields related to the participant
        Label participantLabel = new Label("Participant Number: ");
        participantLabel.setStyle("-fx-font-family: Verdana;" +
                "-fx-font-size: 14;");
        TextField participantNumber = new TextField("");
        HBox participantInputs = new HBox();
        participantInputs.getChildren().addAll(participantLabel, participantNumber);

        //information related to the researcher
        Label researcherLabel = new Label("Researcher Name:");
        researcherLabel.setPadding(new Insets(0, 16, 0, 0));
        researcherLabel.setStyle("-fx-font-family: Verdana;" +
                "-fx-font-size: 14;");
        TextField researcherName = new TextField("");
        HBox researcherInputs = new HBox();
        researcherInputs.getChildren().addAll(researcherLabel, researcherName);

        //adding in the options for the questions
        ToggleGroup questionGroup = new ToggleGroup();
        RadioButton questions1 = new RadioButton();
        questions1.setToggleGroup(questionGroup);
        questions1.setText("Question Set 1");
        RadioButton questions2 = new RadioButton();
        questions2.setToggleGroup(questionGroup);
        questions2.setText("Questions Set 2");
        HBox questionRadioButtons = new HBox();
        questionRadioButtons.getChildren().addAll(questions1, questions2);

        //formatting the start page to input information
        VBox layout = new VBox();
        layout.getChildren().addAll(participantInputs, researcherInputs, nextButton, questionRadioButtons);
        layout.setAlignment(Pos.CENTER);

        //adding spacing for ~aesthetics~
        HBox hori = new HBox();
        spacer1 = new Region();
        spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        hori.getChildren().addAll(spacer1, layout, spacer2);
        layout.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(hori, 400,500);
        primaryStage.setScene(scene1);



        //These are the buttons that will be used in the program.
        //formatting the ready button
        readyButton = new Button();
        readyButton.setAlignment(Pos.CENTER);
        readyButton.setMaxHeight(600);
        readyButton.setMaxWidth(1000);

        //formatting the submit button
        submitButton = new Button();
        submitButton.setText("Submit");
        submitButton.setMinSize(100, 70);
        submitButton.setPadding(new Insets(15));
        submitButton.setStyle("-fx-font-family: Verdana;" +
                "-fx-font-size: 25;");
        playButton = new Button();

        //Formatting the closeAppButton
        closeAppButton = new Button();
        closeAppButton.setAlignment(Pos.CENTER);
        closeAppButton.setMaxHeight(600);
        closeAppButton.setMaxWidth(1000);
        closeAppButton.setMinSize(300,250);
        closeAppButton.setFont(Font.font ("Verdana", 30));
        closeAppButton.setText("Close App");


        //Formatting the continue button
        continueButton = new Button();
        continueButton.setMinSize(60, 60);
        continueButton.setAlignment(Pos.TOP_LEFT);
        Image star = new Image("star.png");
        ImageView starview = new ImageView(star);
        starview.setFitHeight(60);
        starview.setFitWidth(60);
        continueButton.setGraphic(starview);
        continueButton.setAlignment(Pos.CENTER);

        /**
         * After the details of the researcher and the participant are filled in
         * set up the page for the first tetris run.
         */
        nextButton.setOnAction(e -> {


            //creating the questions - the question reader contains the
            //6 different questions for the SPAM queries
            QuestionReader questionReader;
            if (questions1.isSelected()) {
                questionReader = new QuestionReader("./out/production/SPAM/Questions1.csv");
            } else {
                questionReader = new QuestionReader("./out/production/SPAM/Question2.csv");
            }

            try {
                questionReader.fileReader();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            questions = new LinkedList<>();

            //creating an queue of questions for easier access later on
            questions.add(questionReader.question1);
            questions.add(questionReader.question2);
            questions.add(questionReader.question3);
            questions.add(questionReader.question4);
            questions.add(questionReader.question5);
            questions.add(questionReader.question6);

            //adding the current date and time to the Participant's info
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            participantInfo.add(formatter.format(date));

            //taking in the information for participant number
            //and researcher name
            participantInfo.add(participantNumber.getText());
            participantInfo.add(researcherName.getText());

            //Creating a new Tetris game
            CurrentMain = new Main();
            //Generating the layout for the Tetris Play Screen
            generateTetrisPlay();
            playTimer.start(); //Starting the timer for the Play button


        });

        playButton.setOnAction(i -> {
            //Recording the time that it took to press the Play button
            participantInfo.add(Double.toString(playTimer.stop()));

            //Creating a new timer for the Play button
            playTimer = new Stopwatch();

            //Opening up the Tetris game
            CurrentMain.start(new Stage());
        });

        //displaying the questions after Ready is clicked
        readyButton.setOnAction(t -> {
            wait.stop();
            wait = new PauseTransition();

            //Stopping the timer for the Ready button
            currentQuestion.readyTime = readyTimer.stop();

            //Creating a new timer for the ready button
            readyTimer = new Stopwatch();

            //Generating the layout for the Question Screen
            //Choosing an option will enable the submit button and
            //the last option pressed with the recorded answer
            VBox layout3 = new VBox();
            layout3.getChildren().addAll(currentQuestion.run(), submitButton);
            submitButton.setDisable(true);
            currentQuestion.q1option1.setOnAction(event -> {
                submitButton.setDisable(false);
                currentQuestion.answerChosen = 1;
            });
            currentQuestion.q1option2.setOnAction(event -> {
                submitButton.setDisable(false);
                currentQuestion.answerChosen = 2;
            });
            currentQuestion.q1option3.setOnAction(event -> {
                submitButton.setDisable(false);
                currentQuestion.answerChosen = 3;
            });
            currentQuestion.q1option4.setOnAction(event -> {
                submitButton.setDisable(false);
                currentQuestion.answerChosen = 4;
            });
            layout3.setAlignment(Pos.CENTER);
            Scene questionScene = new Scene(layout3);
            primaryStage.setScene(questionScene);
            primaryStage.setFullScreen(true);
            primaryStage.setMaximized(true);
            submitTimer.start(); //Timer for the Submit button is started
        });

        submitButton.setOnAction(g -> {

            //Stopping the timer for the submit button and recording the time
            currentQuestion.submitTime = submitTimer.stop();

            //creating a new timer for the submit button of the next question
            submitTimer = new Stopwatch();

            //Adding in information of the latest run of tetris and the question that
            //was just answered
            participantInfo.add(CurrentMain.gameInfo[0]); //tetris score
            participantInfo.add(CurrentMain.gameInfo[1]); //tetris time
            participantInfo.add(CurrentMain.gameInfo[2]); //tetris resets
            participantInfo.add(Double.toString(currentQuestion.readyTime)); //adding the time to click Ready button
            participantInfo.add(Double.toString(currentQuestion.submitTime)); //time to submit answer
            participantInfo.add(Integer.toString(currentQuestion.answerChosen)); //the answer chosen

            //this is what will be added to the csv file if the option chosen is correct or not
            if (currentQuestion.answerChosen == currentQuestion.correctOption) {
                participantInfo.add("1"); // 1 means that the correct answer was chosen
            } else {
                participantInfo.add("0"); // 0 means that the wrong answer was chosen
            }

            //Setting up a new Tetris game for the next iteration
            CurrentMain = new Main();

            //Generating the Tetris Play Screen
            generateTetrisPlay();
            //Starting the timer for when the Play button is clicked
            playTimer.start();
        });

        continueButton.setOnAction(event -> {
            participantInfo.add("0.0"); //adding a time of 0 to playRT
            if (questions.peek() == null) {
                generateCloseAppScreen();
            } else {
                //Generating the Query Ready Screen
                generateReadyScreen();
                readyTimer.start();
                playTimer.stop();
                playTimer = new Stopwatch(); //Creating a new timer for the play button
            }
        });

        closeAppButton.setOnAction(e -> {
            //Adding in information of the last run of tetris
            participantInfo.add(CurrentMain.gameInfo[0]); //tetris score
            participantInfo.add(CurrentMain.gameInfo[1]); //tetris time
            participantInfo.add(CurrentMain.gameInfo[2]); //tetris resets

            //setting up the writing process to the file
            try {
                //adding in the averages to the participant's info
                generateAverages();

                //writing to the csv file
                writer = new FileWriter("C:\\Users\\sangh\\floobits\\share\\Sangvi98\\SPAM\\src\\test.csv", true);
                CSVWriter csvWriter = new CSVWriter(writer);
                System.out.println(Arrays.toString(participantInfo.toArray()));
                Object[] infoObjArr = participantInfo.toArray();
                String[] infoStrArr = Arrays.copyOf(infoObjArr, infoObjArr.length, String[].class);
                csvWriter.writeNext(infoStrArr);
                csvWriter.close();
            } catch (IOException y) {
                y.printStackTrace();
            }
            Platform.exit();
        });

        primaryStage.show();
    }

    public ArrayList<String> getUserInfo() {
        return participantInfo;
    }

    public void generateReadyScreen() {
        //starting the timer for the ready button
        readyTimer.start();

        //Generating the layout for the Query Ready Screen
        VBox layout2 = new VBox();
        HBox hori2 = new HBox();
        hori2.getChildren().addAll(spacer1, layout2, spacer2);
        Region spacer3 = new Region();
        Region spacer4 = new Region();
        VBox.setVgrow(spacer3, Priority.ALWAYS);
        VBox.setVgrow(spacer4, Priority.ALWAYS);
        layout2.getChildren().addAll(spacer3, readyButton, spacer4);
        readyButton.setMinSize(300,250);
        readyButton.setFont(Font.font ("Verdana", 30));
        readyButton.setText("Ready?");
        Scene scene2 = new Scene(hori2, 1600,1600);
        primaryStage.setScene(scene2);

        currentQuestion = questions.poll();

        wait = new PauseTransition(Duration.seconds(5));
        wait.setOnFinished((e) -> {
            generateTetrisPlay();

            wait.stop();
            //Stopping the timer for the Ready button
            currentQuestion.readyTime = readyTimer.stop();

            //Creating a new timer for the ready button
            readyTimer = new Stopwatch();

            //setting up a new timer for the playscreen
            playTimer = new Stopwatch();
            playTimer.start();

            //Adding in information of the latest run of tetris and the question that
            //was just answered
            participantInfo.add(CurrentMain.gameInfo[0]); //tetris score
            System.out.println(CurrentMain.gameInfo[0]);
            participantInfo.add(CurrentMain.gameInfo[1]); //tetris time
            System.out.println(CurrentMain.gameInfo[1]);
            participantInfo.add(CurrentMain.gameInfo[2]); //tetris resets
            System.out.println(CurrentMain.gameInfo[2]);

            participantInfo.add(Double.toString(5.0)); //adding the time to click Ready button
            participantInfo.add(Double.toString(0.0)); //time to submit answer
            participantInfo.add(Integer.toString(0)); //the answer chosen
            //since the participant has missed the question this counts as a inaccuracy
            participantInfo.add("0");

            //Setting up a new Tetris game for the next iteration
            //CurrentMain = new Main();
            wait = new PauseTransition();

            CurrentMain = new Main();
        });
        wait.play();
    }

    public void generateTetrisPlay() {
        VBox layout2 = new VBox();
        HBox hori2 = new HBox();
        hori2.getChildren().addAll(spacer1, layout2, spacer2);
        Region spacer3 = new Region();
        Region spacer4 = new Region();
        VBox.setVgrow(spacer3, Priority.ALWAYS);
        VBox.setVgrow(spacer4, Priority.ALWAYS);
        layout2.getChildren().addAll(spacer3, playButton, spacer4);
        playButton.setMinSize(300,250);
        playButton.setFont(Font.font ("Verdana", 30));
        playButton.setText("Play?");
        layout2.setAlignment(Pos.CENTER);
        BorderPane borderpane = new BorderPane();
        borderpane.setCenter(layout2);
        continueButton.setAlignment(Pos.TOP_LEFT);
        borderpane.setTop(continueButton);
        Scene scene2 = new Scene(borderpane, 300,300);
        primaryStage.setScene(scene2);
        scene2.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.Q) && questions.peek()!= null) {
                generateReadyScreen();
            } else {
                generateCloseAppScreen();
            }
        });
        primaryStage.setFullScreen(true);
        primaryStage.setMaximized(true);

    }

    public void generateCloseAppScreen() {
        VBox layout2 = new VBox();
        HBox hori2 = new HBox();
        hori2.getChildren().addAll(spacer1, layout2, spacer2);
        Region spacer3 = new Region();
        Region spacer4 = new Region();
        VBox.setVgrow(spacer3, Priority.ALWAYS);
        VBox.setVgrow(spacer4, Priority.ALWAYS);
        layout2.getChildren().addAll(spacer3, closeAppButton, spacer4);
        Scene scene2 = new Scene(hori2, 1600,1600);
        primaryStage.setScene(scene2);
    }


    public void generateAverages() {
        //Total Tetris Time
        Double totalTetrisTime = 0.0;
        for (int i = 0; i < 7; i++) {
            totalTetrisTime += Double.parseDouble(participantInfo.get((i * 8) + 5));
        }
        participantInfo.add(Double.toString(totalTetrisTime));

        //Total Tetris Resets
        Integer totalTetrisResets = 0;
        for (int i = 0; i < 7; i++) {
            totalTetrisResets += Integer.parseInt(participantInfo.get((i * 8) + 6));
        }
        participantInfo.add(Integer.toString(totalTetrisResets));

        //Total Tetris Score
        Integer totalTetrisScore = 0;
        for (int i = 0; i < 7; i++) {
            totalTetrisScore += Integer.parseInt(participantInfo.get((i * 8) + 4));
        }
        participantInfo.add(Integer.toString(totalTetrisScore));

        //Average Ready RT
        Double averageReadyRT = 0.0;
        for (int i = 0; i < 6; i++) {
            averageReadyRT += Double.parseDouble(participantInfo.get((i * 8) + 7));
        }
        averageReadyRT = averageReadyRT / 6.0;
        participantInfo.add(Double.toString(averageReadyRT));

        //Average Question RT
        Double averageQuestionRT = 0.0;
        for (int i = 0; i < 6; i++) {
            averageQuestionRT += Double.parseDouble(participantInfo.get((i * 8) + 8));
        }
        averageQuestionRT = averageQuestionRT / 6.0;
        participantInfo.add(Double.toString(averageQuestionRT));

        //Average Question Accuracy
        Double averageQuestionAccuracy = 0.0;
        for (int i = 0; i < 6; i++) {
            averageQuestionAccuracy += Double.parseDouble(participantInfo.get((i * 8) + 10));
        }
        averageQuestionAccuracy = averageQuestionAccuracy / 6.0;
        participantInfo.add(Double.toString(averageQuestionAccuracy));
    }


    public static void main(String[] args) {
        launch(args);

    }



}
