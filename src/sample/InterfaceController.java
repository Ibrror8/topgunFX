package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class InterfaceController implements Initializable {

    //FXML controles
    @FXML
    private Label userLabel;
    @FXML
    private Label moneyLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    //gets called by LoginController
    public void getUser(String user) {
        userLabel.setText(user);

        //instance of Loginmodel
        LoginModel l = new LoginModel();

        //display money by asking the database
        try {
            String money = l.getUserData(user, "money");
            double value = Double.parseDouble(money);

            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.GERMANY);
            String currency = format.format(value);
            System.out.println("Currency in Germany: " + currency);
            
            moneyLabel.setText(currency);
            //moneyLabel.setText(value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }











}
