package customMessageBox;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * <p>customBox.customMessageBox.CustomMessageBox class.</p>
 *
 * @author Patryk Zdral
 * @version $Id: $Id
 */
public class CustomMessageBox {

    public CustomMessageBox() {
    }

    /**
     * <p>showMessageBox.</p>
     *
     * @param alertType a javafx.scene.control.Alert$AlertType object.
     * @param title a {@link String} object.
     * @param header a {@link String} object.
     * @param content a {@link String} object.
     * @return a javafx.scene.control.Alert object.
     */
    public Alert showMessageBox(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    /**
     * <p>showConfirmMessageBox.</p>
     *
     * @param alertType a javafx.scene.control.Alert$AlertType object.
     * @param title a {@link String} object.
     * @param header a {@link String} object.
     * @param content a {@link String} object.
     * @param confirmText a {@link String} object.
     * @param cancelText a {@link String} object.
     * @return a javafx.scene.control.Alert object.
     */
    public Alert showConfirmMessageBox(Alert.AlertType alertType, String title, String header, String content,
                                       String confirmText, String cancelText) {
        ButtonType confirmButton = new ButtonType(confirmText, ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType(cancelText, ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(alertType, content, confirmButton, cancelButton);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.setTitle(title);
        alert.setHeaderText(header);
        return alert;
    }
}
