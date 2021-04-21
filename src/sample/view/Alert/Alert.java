package sample.view.Alert;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
public class Alert {
    private static volatile Alert alert = null;
    Stage alertPopUp;
    Parent root;
    Label alertMsg;
    private static String msg;

    private Alert() {
        try {
            alertPopUp = new Stage();
            FXMLLoader fxmloader = new FXMLLoader();
            fxmloader.setController("sample.view.Alert.Alert");
            root = fxmloader.load(getClass().getResource("alert.fxml"));
            alertMsg = (Label) root.lookup("#alertMsg");
            alertMsg.setText(msg);
            alertPopUp.setScene(new Scene(root, 300, 150));
            alertPopUp.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Alert getAlert(String alertMsg) {
        String msg = alertMsg;
        if (alert == null){
            synchronized (Alert.class){
                if (alert == null){
                    alert = new Alert();
                }
            }
        }
        return alert;
    }

}
