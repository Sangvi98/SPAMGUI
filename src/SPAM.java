//import javafx.*;

/**
 *
 *                                 TODO
 *
 *  The first page of participant number, researcher name, and 'next' button,
 *  which should be associated with a timer based on the actual PC time.
 *
 *  The second page is a 'ready' button, also associated with the timer.
 *
 *  The following pages are the placeholders for the actual questions.
 *  We could build in two formats, one page of multiple choices and the
 *  other is text entry. We want to know how long did the
 *  users/participants spend on each question.
 *
 *  Export the time on each page/action and actual input into a csv. file.
 *
 *  Conditional statement of checking the actual input
 *  (i.e. multiple choice) and give a final score of the input.
 *
 *  Implement Tetris between each question.
 *
 */



import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SPAM extends Application {

    public Stopwatch timer = new Stopwatch();
    private double timeElapsed;
    private PrintWriter writer;
    public StringBuilder sb = new StringBuilder();
    public StringBuilder header = new StringBuilder();

    Question question1;
    Question question2;
    Question question3;
    Question question4;
    Question question5;
    Question question6;



    @Override
    public void start(Stage primaryStage) throws java.io.FileNotFoundException{

        //creating the questions
        QuestionReader questionReader = new QuestionReader("eadas");
        questionReader.fileReader();
        question1 = questionReader.question1;
        question2 = questionReader.question2;
        question3 = questionReader.question3;
        question4 = questionReader.question4;
        question5 = questionReader.question5;
        question6 = questionReader.question6;

        primaryStage.setTitle("Information");
        primaryStage.setMaximized(true);
        Button nextButton = new Button("Next");
        Label participantLabel = new Label("Participant Number: ");
        TextField participantNumber = new TextField("");
        HBox participantInputs = new HBox();
        participantInputs.getChildren().addAll(participantLabel, participantNumber);
        Label researcherLabel = new Label("Researcher Name: ");
        TextField researcherName = new TextField("");
        HBox researcherInputs = new HBox();
        researcherInputs.getChildren().addAll(researcherLabel, researcherName);
        VBox layout = new VBox();
        
        layout.getChildren().addAll(participantInputs, researcherInputs, nextButton);
    
        layout.setAlignment(Pos.CENTER);

        HBox hori = new HBox();

        Region spacer1 = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        hori.getChildren().addAll(spacer1, layout, spacer2);
        layout.setAlignment(Pos.CENTER);




        Scene scene1 = new Scene(hori, 400,500);
        primaryStage.setScene(scene1);


        Button readyButton = new Button();
        readyButton.setAlignment(Pos.CENTER);
        readyButton.setMaxHeight(600);
        readyButton.setMaxWidth(1000);
//        StringBuilder sb = new StringBuilder();


        nextButton.setOnAction(e -> {

            header.append("participant number:");
            header.append(',');
            header.append("researcher name:");
            header.append(',');
            header.append("local comp start time");
            header.append(',');
            header.append("next -> ready");
            header.append(',');

            header.append("question1");
            header.append(',');
            header.append("correct/incorrect");
            header.append(',');
            header.append("option time");
            header.append(',');
            header.append("submit time");
            header.append(',');

            header.append("question2");
            header.append(',');
            header.append("correct/incorrect");
            header.append(',');
            header.append("option time");
            header.append(',');
            header.append("submit time");
            header.append(',');

            header.append("question3");
            header.append(',');
            header.append("correct/incorrect");
            header.append(',');
            header.append("option time");
            header.append(',');
            header.append("submit time");
            header.append(',');

            header.append("question4");
            header.append(',');
            header.append("correct/incorrect");
            header.append(',');
            header.append("option time");
            header.append(',');
            header.append("submit time");
            header.append(',');

            header.append("question5");
            header.append(',');
            header.append("correct/incorrect");
            header.append(',');
            header.append("option time");
            header.append(',');
            header.append("submit time");
            header.append(',');

            header.append("question6");
            header.append(',');
            header.append("correct/incorrect");
            header.append(',');
            header.append("option time");
            header.append(',');
            header.append("submit time");
            header.append(',');

            header.append("\n");


            sb.append(participantNumber.getText());
            sb.append(',');
            sb.append(researcherName.getText());
            sb.append(',');

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            sb.append(dateFormat.format(date));

            sb.append(',');

            timer.start();




            VBox layout2 = new VBox();
            HBox hori2 = new HBox();

//            HBox.setHgrow(spacer1, Priority.ALWAYS);
//            HBox.setHgrow(spacer2, Priority.ALWAYS);

            hori2.getChildren().addAll(spacer1, layout2, spacer2);

            Region spacer3 = new Region();
            Region spacer4 = new Region();
            VBox.setVgrow(spacer3, Priority.ALWAYS);
            VBox.setVgrow(spacer4, Priority.ALWAYS);

            layout2.getChildren().addAll(spacer3, readyButton, spacer4);
            readyButton.setText("\n\n\n\n\t\t\t\t\tReady?\t\t\t\t\t\n\n\n\n\n\n\n");
            Scene scene2 = new Scene(hori2, 300,300);
            primaryStage.setScene(scene2);
            primaryStage.setFullScreen(true);
                }
                );
        readyButton.setOnAction(e -> {
            timeElapsed = timer.stop();
            sb.append(timeElapsed);
            sb.append(',');
            timer.start();
            question1.startTimer();

            //set scene to window 3
            primaryStage.setScene(new Scene(null));
            primaryStage.setFullScreen(true);
            this.question1.submitq1.setOnAction(f -> {

                this.question1.submitTime = this.timer.stop();
                if (this.question1.answerChosen == this.question1.correctOption) {
                    this.question1.optionString = "correct";
                } else {
                    this.question1.optionString = "incorrect";
                }

                sb.append(question1.answerChosen);
                sb.append(',');
                sb.append(question1.optionString);
                sb.append(',');
                sb.append(question1.firstChoice);
                sb.append(',');
                sb.append(question1.submitTime);
                sb.append(',');
    


                try {
                    writer = new PrintWriter(new File("test.csv"));
                    writer.write(header.toString());
                    writer.write(sb.toString());
                    writer.flush();
                    writer.close();
                    //sb.append("\n");

                } catch (FileNotFoundException error) {
                    System.out.println(error.getMessage());
                }

                Main main = new Main();
                primaryStage.toBack();
                main.start(new Stage());


                primaryStage.setScene(new Scene(null));
                primaryStage.setFullScreen(true);

                this.question2.submitq1.setOnAction(g -> {

                    this.question2.submitTime = this.timer.stop();
                    if (this.question2.answerChosen == this.question1.correctOption) {
                        this.question2.optionString = "correct";
                    } else {
                        this.question2.optionString = "incorrect";
                    }

                    sb.append(question2.answerChosen);
                    sb.append(',');
                    sb.append(question2.optionString);
                    sb.append(',');
                    sb.append(question2.firstChoice);
                    sb.append(',');
                    sb.append(question2.submitTime);
                    sb.append(',');



                    try {
                        writer = new PrintWriter(new File("test.csv"));
                        writer.write(header.toString());
                        writer.write(sb.toString());
                        writer.flush();
                        writer.close();
                        //sb.append("\n");

                    } catch (FileNotFoundException error) {
                        System.out.println(error.getMessage());
                    }

                    primaryStage.setScene(new Scene(null));
                    primaryStage.setFullScreen(true);

                    this.question3.submitq1.setOnAction(c -> {

                        this.question3.submitTime = this.timer.stop();
                        if (this.question3.answerChosen == this.question3.correctOption) {
                            this.question3.optionString = "correct";
                        } else {
                            this.question3.optionString = "incorrect";
                        }

                        sb.append(question3.answerChosen);
                        sb.append(',');
                        sb.append(question3.optionString);
                        sb.append(',');
                        sb.append(question3.firstChoice);
                        sb.append(',');
                        sb.append(question3.submitTime);
                        sb.append(',');


                        try {
                            writer = new PrintWriter(new File("test.csv"));
                            writer.write(header.toString());
                            writer.write(sb.toString());
                            writer.flush();
                            writer.close();
                            //sb.append("\n");

                        } catch (FileNotFoundException error) {
                            System.out.println(error.getMessage());
                        }

                        primaryStage.setScene(new Scene(null));
                        primaryStage.setFullScreen(true);

                        this.question4.submitq1.setOnAction(k -> {

                            this.question4.submitTime = this.timer.stop();
                            if (this.question4.answerChosen == this.question4.correctOption) {
                                this.question4.optionString = "correct";
                            } else {
                                this.question4.optionString = "incorrect";
                            }

                            sb.append(question4.answerChosen);
                            sb.append(',');
                            sb.append(question4.optionString);
                            sb.append(',');
                            sb.append(question4.firstChoice);
                            sb.append(',');
                            sb.append(question4.submitTime);
                            sb.append(',');


                            try {
                                writer = new PrintWriter(new File("test.csv"));
                                writer.write(header.toString());
                                writer.write(sb.toString());
                                writer.flush();
                                writer.close();
                                //sb.append("\n");

                            } catch (FileNotFoundException error) {
                                System.out.println(error.getMessage());
                            }

                            primaryStage.setScene(new Scene(null));
                            primaryStage.setFullScreen(true);

                            this.question5.submitq1.setOnAction(l -> {

                                this.question5.submitTime = this.timer.stop();
                                if (this.question5.answerChosen == this.question5.correctOption) {
                                    this.question5.optionString = "correct";
                                } else {
                                    this.question5.optionString = "incorrect";
                                }

                                sb.append(question5.answerChosen);
                                sb.append(',');
                                sb.append(question5.optionString);
                                sb.append(',');
                                sb.append(question5.firstChoice);
                                sb.append(',');
                                sb.append(question5.submitTime);
                                sb.append(',');


                                try {
                                    writer = new PrintWriter(new File("test.csv"));
                                    writer.write(header.toString());
                                    writer.write(sb.toString());
                                    writer.flush();
                                    writer.close();
                                    //sb.append("\n");

                                } catch (FileNotFoundException error) {
                                    System.out.println(error.getMessage());
                                }

                                primaryStage.setScene(new Scene(null));
                                primaryStage.setFullScreen(true);

                                this.question6.submitq1.setOnAction(o -> {

                                    this.question6.submitTime = this.timer.stop();
                                    if (this.question6.answerChosen == this.question4.correctOption) {
                                        this.question6.optionString = "correct";
                                    } else {
                                        this.question6.optionString = "incorrect";
                                    }

                                    sb.append(question6.answerChosen);
                                    sb.append(',');
                                    sb.append(question6.optionString);
                                    sb.append(',');
                                    sb.append(question6.firstChoice);
                                    sb.append(',');
                                    sb.append(question6.submitTime);
                                    sb.append(',');


                                    try {
                                        writer = new PrintWriter(new File("test.csv"));
                                        writer.write(header.toString());
                                        writer.write(sb.toString());
                                        writer.flush();
                                        writer.close();
                                        //sb.append("\n");

                                    } catch (FileNotFoundException error) {
                                        System.out.println(error.getMessage());
                                    }
                                });
                            });
                        });

                    });

                });

            });
            
        });



        primaryStage.show();
        primaryStage.setMaximized(true);
        primaryStage.setMaximized(true);

    }
    

    public static void main(String[] args) {
        launch(args);
    }

}
