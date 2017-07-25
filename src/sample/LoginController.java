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
    //get instance of Login model -> for calling non-static methods from static
    public LoginModel loginModel = new LoginModel();

    //our FMXL controles
    @FXML
    private Label isConnected;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField passInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //display if database connection is there
        if(loginModel.isDBConnected()) {
            isConnected.setText("is connected");
        } else {
            isConnected.setText("not connected");
        }
    }

    //when user presses enter or clicks button -> tries to login
    public void loginAction(ActionEvent event) {
        try {
            //see if there is an user with that password
            if(loginModel.isLogin(nameInput.getText(), passInput.getText())) {
                isConnected.setText("Username and password is correct");

                //hide login window
                ((Node)event.getSource()).getScene().getWindow().hide();

                //get new window
                Stage primaryStage = new Stage();

                //on close -> send database informations who has logged out
                primaryStage.setOnCloseRequest(e -> {
                    try {
                        loginModel.logoutActivity(nameInput.getText());
                    } catch (SQLException e1) {
                        System.out.println("Database failed");
                        e1.printStackTrace();
                    }
                });

                //load fmxl of new window
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("Interface.fxml").openStream());

                //put username on new window
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
