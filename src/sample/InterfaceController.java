package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class InterfaceController implements Initializable {
    @FXML
    private Label money;

    @FXML
    private Label day;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void getUser(String user) {
        money.setText(user);
    }
    










}
