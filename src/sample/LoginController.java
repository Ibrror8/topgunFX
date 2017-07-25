package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public LoginModel loginModel = new LoginModel();

    @FXML
    private Label isConnected;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField passInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(loginModel.isDBConnected()) {
            isConnected.setText("is connected");
        } else {
            isConnected.setText("not connected");
        }
    }

    public void loginAction(ActionEvent event) {
        try {
            if(loginModel.isLogin(nameInput.getText(), passInput.getText())) {
                isConnected.setText("Username and password is correct");

                //hide login window
                ((Node)event.getSource()).getScene().getWindow().hide();

                //get new window
                Stage primaryStage = new Stage();

                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("Interface.fxml").openStream());

                //pur username on new window
                InterfaceController interfaceController = (InterfaceController)loader.getController();
                interfaceController.getUser(nameInput.getText());

                //show new window
                primaryStage.setTitle("Hello World");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } else {
                isConnected.setText("Username or password not correct");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
