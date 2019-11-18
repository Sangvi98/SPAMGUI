import java.io.FileInputStream;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class QuestionReader {

    public Question question1;
    public Question question2;
    public Question question3;
    public Question question4;
    public Question question5;
    public Question question6;
    private String filename;

    QuestionReader(String filename) {
        this.filename = filename;
    }

    public void fileReader() throws FileNotFoundException{


        File file = new File(filename);

        Scanner questionsFile = new Scanner(file);


        question1 = questionGenerator(questionsFile.nextLine());
        question2 = questionGenerator(questionsFile.nextLine());
        question3 = questionGenerator(questionsFile.nextLine());
        question4 = questionGenerator(questionsFile.nextLine());
        question5 = questionGenerator(questionsFile.nextLine());
        question6 = questionGenerator(questionsFile.nextLine());

    }

    public Question questionGenerator(String line) {
        String delimiter = ",";
        String[] questionLine = line.split(delimiter);
        System.out.println(questionLine[0] + questionLine[1] + questionLine[2] + questionLine[3] + questionLine[4] + questionLine[5]);
        return new Question(questionLine[0], questionLine[1], questionLine[2], questionLine[3], questionLine[4], Integer.parseInt(questionLine[5].trim()));
    }

}

