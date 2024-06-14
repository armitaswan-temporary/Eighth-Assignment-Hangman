package hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.json.JSONObject;

public class GameController implements Initializable {

    LocalDateTime startTime;
    String Key = "3GHeGCidPr0E3R27KSGtAMFiNiSMfFk0t9JxwXb2";
    String word;
    int letterSize;
    boolean won = false;
    int life = 7;
    int score = 0;

    @FXML
    TextField Input;
    @FXML
    ImageView one;
    @FXML
    ImageView two;
    @FXML
    ImageView three;
    @FXML
    ImageView four;
    @FXML
    ImageView five;
    @FXML
    ImageView six;
    @FXML
    ImageView seven;
    @FXML
    Button checkBTN;
    @FXML
    Label first;
    @FXML
    Label second;
    @FXML
    Label third;
    @FXML
    Label fourth;
    @FXML
    Label fifth;
    @FXML
    Label sixth;
    @FXML
    Label seventh;
    @FXML
    Label eighth;
    @FXML
    Label msg;
    @FXML
    Button backBTN;
    @FXML
    Label timeShow;
    @FXML
    Label scoreShow;

    public int difficulty = MainMenuController.difficulty;

    public void getRandomWordFromApi () {
        String Url = "https://api.api-ninjas.com/v1/randomword";

        try {
            URL url = new URL (Url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection ();
            httpURLConnection.setRequestProperty ("X-Api-Key", Key);

            if (httpURLConnection.getResponseCode () == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner (httpURLConnection.getInputStream ());
                String  serverResponse = scanner.nextLine ();

                JSONObject jsonData = new JSONObject (serverResponse);
                if (jsonData.getString ("word").length () <= 8) {
                    word = jsonData.getString ("word");
                }
                else {
                    getRandomWordFromApi ();
                }
            }
        }
        catch (Exception e) {
            System.out.println (e.getMessage ());
        }
    }

    public void startGame() {
        if (letterSize <= 7) {
            eighth.setText("✘");
            eighth.setVisible(true);
        }
        else if (letterSize <= 6) {
            seventh.setText("✘");
            seventh.setVisible(true);
        }
        else if (letterSize <= 5) {
            sixth.setText("✘");
            sixth.setVisible(true);
        }
        else if (letterSize <= 4) {
            fifth.setText("✘");
            fifth.setVisible(true);
        }
        else if (letterSize <= 3) {
            fourth.setText("✘");
            fourth.setVisible(true);
        }
        else if (letterSize <= 2) {
            third.setText("✘");
            third.setVisible(true);
        }
        else if (letterSize <= 3) {
            second.setText("✘");
            second.setVisible(true);
        }
    }

    public void correctGuess(int index,String c) {
        if (index == 0 && index < letterSize) {
            if (!first.getText().equalsIgnoreCase(c)) {
                first.setText (c);
                score++;
            }
        }
        else if (index == 1 && index < letterSize) {
            if (!second.getText().equalsIgnoreCase(c)) {
                second.setText (c);
                score++;
            }
        }
        else if (index == 2 && index < letterSize) {
            if (!third.getText().equalsIgnoreCase(c)) {
                third.setText (c);
                score++;
            }
        }
        else if (index == 3 && index < letterSize) {
            if (!fourth.getText().equalsIgnoreCase(c)) {
                fourth.setText (c);
                score++;
            }
        }
        else if (index == 4 && index < letterSize) {
            if (!fifth.getText().equalsIgnoreCase(c)) {
                fifth.setText (c);
                score++;
            }
        }
        else if (index == 5 && index < letterSize) {
            if (!sixth.getText().equalsIgnoreCase(c)) {
                sixth.setText (c);
                score++;
            }
        }
        else if (index == 6 && index < letterSize) {
            if (!seventh.getText().equalsIgnoreCase(c)) {
                seventh.setText (c);
                score++;
            }
        }
        else if (index == 7 && index < letterSize) {
            if (!eighth.getText().equalsIgnoreCase(c)) {
                eighth.setText (c);
                score++;
            }
        }

        if (score >= letterSize) {
            LocalDateTime endTime = LocalDateTime.now();
            Duration duration = Duration.between (startTime, endTime);
            int timeTaken = (int)duration.getSeconds();

            checkBTN.setVisible (false);
            msg.setText("You Won!");
            msg.setVisible (true);
            timeShow.setText(String.valueOf(timeTaken));
            timeShow.setVisible(true);
            DatabaseManager.createGame (MainMenuController.currentUser.getUsername (), word, 7 - life, timeTaken, true);
        }
        scoreShow.setText (String.valueOf (score));
    }

    public void wrongGuess() {
        if (life == 7) {
            one.setVisible(true);
        }
        else if (life == 6) {
            two.setVisible(true);
        }
        else if (life == 5) {
            three.setVisible(true);
        }
        else if (life == 4) {
            four.setVisible(true);
        }
        else if (life == 3) {
            five.setVisible(true);
        }
        else if (life == 2) {
            six.setVisible(true);
        }
        else if (life == 1) {
            LocalDateTime endTime = LocalDateTime.now();
            Duration duration = Duration.between (startTime, endTime);
            int timeTaken = (int)duration.getSeconds();

            seven.setVisible(true);
            checkBTN.setVisible (false);
            msg.setText("You Lost!");
            msg.setVisible (true);
            timeShow.setText(String.valueOf(timeTaken));
            timeShow.setVisible(true);
            DatabaseManager.createGame (MainMenuController.currentUser.getUsername (), word, 7 - life, timeTaken, false);
        }
        life--;
    }

    @FXML
    public void check() {
        String guessedChar = Input.getText();
        if (word.toLowerCase().contains (guessedChar.toLowerCase())) {
            int index = 0;
            for (int i = 0; i < word.length (); i++) {
                char c = word.charAt(i);
                if (String.valueOf(c).equalsIgnoreCase(guessedChar)) {
                    correctGuess(index, Character.toString(c));
                }
                index++;
            }
        }
        else {
            wrongGuess();
        }
    }

    @FXML
    public void switchToMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("GameMenu.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startTime = LocalDateTime.now ();
        getRandomWordFromApi ();
        try {
            Thread.sleep (500);
        }
        catch (InterruptedException e) {
            throw new RuntimeException (e);
        }
        System.out.println ("Word: " + word);
        word = word.toUpperCase ();
        letterSize = word.length ();
        startGame();
    }
}
