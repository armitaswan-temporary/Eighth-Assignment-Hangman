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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    // first menu
    @FXML
    Button SignupBTN;
    @FXML
    Button LoginBTN;
    @FXML
    public void signup(ActionEvent event) throws IOException {
        loadScene("SigninMenu.fxml", event);
    }
    @FXML
    public void login(ActionEvent event) throws IOException {
        loadScene("LoginMenu.fxml", event);

    }

    // sign up menu
    @FXML
    TextField signupNameBTN;
    @FXML
    TextField signupUsernameBTN;
    @FXML
    TextField signupPasswordBTN;
    @FXML
    Button gameMenuBTN;
    static Account currentUser;
    @FXML
    public void switchToGameMenuS(ActionEvent event) throws IOException, SQLException {
        String name = signupNameBTN.getText();
        String username = signupUsernameBTN.getText();
        String password = signupPasswordBTN.getText();

        DatabaseManager.createUser(username, password, name);
        currentUser = DatabaseManager.readUser(username);

        loadScene("GameMenu.fxml", event);
    }

    // login menu
    @FXML
    TextField loginUsernameBTN;
    @FXML
    TextField loginPasswordBTN;
    @FXML
    Button gameMenuBTN2;
    @FXML
    public void switchToGameMenuL(ActionEvent event) throws IOException, SQLException {
        String username = loginUsernameBTN.getText();
        String password = loginPasswordBTN.getText();

        if (DatabaseManager.findUser (username)) {
            Account user = DatabaseManager.readUser(username);
            assert user != null;
            if (password.equals (user.getPassword())) {
                currentUser = user;
            }
            else {
                System.out.println ("wrong password");
            }
        }
        else {
            System.out.println ("user not found");
        }
        loadScene("GameMenu.fxml", event);
    }

    // game menu
    @FXML
    Button easyBTN;
    @FXML
    Button hardBTN;
    @FXML
    Button historyBTN;
    @FXML
    Button leaderboardBTN;
    @FXML
    ListView <String> leaderboard;
    static int difficulty;
    @FXML
    public void switchToGame(ActionEvent event) throws IOException, SQLException {
        if (easyBTN.isPressed())
            difficulty = 1;
        else if (hardBTN.isPressed())
            difficulty = 2;
        loadScene("Game.fxml", event);
    }
    @FXML
    public void showLeaderboard() {
        leaderboard.setVisible (!leaderboard.isVisible());
    }

    @FXML
    private void loadScene(String fxmlFile, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
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
        try {
            List<Game> records = DatabaseManager.readGame ();
            for (Game record : records) {
                leaderboard.getItems().add(record.toString ());
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}