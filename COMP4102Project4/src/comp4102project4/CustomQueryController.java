
package comp4102project4;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class CustomQueryController implements Initializable {

    @FXML
    private Label listLabel;
    @FXML
    private ListView<String> tablesList;
    @FXML
    private Button displayContents;
    @FXML
    private Button customQuery;
    @FXML
    private TextArea query;
    @FXML
    private Button execute;
    @FXML
    private Label resultLabel;
    @FXML
    private TextArea result;

    private PreparedStatement preparedStatement;
    Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resultLabel.setDisable(true);
        result.setDisable(true);
        tablesList.getItems().add("Student");
        tablesList.getItems().add("Course");
        tablesList.getItems().add("Enrollment");
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == execute) {
            resultLabel.setDisable(false);
            result.setDisable(false);
            result.setText("");

            try {
                result.setDisable(false);
                resultLabel.setDisable(false);

                String queryString = query.getText();

                connection = DriverManager.getConnection("jdbc:mysql://localhost/comp4102", "root", "");
                preparedStatement = connection.prepareStatement(queryString);

                ResultSet rset = preparedStatement.executeQuery();

                while (rset.next()) {
                    try {

                        String data1 = rset.getString(1);

                        String data2 = rset.getString(2);

                        String data3 = rset.getString(3);

                        String data4 = rset.getString(4);

                        String data5 = rset.getString(5);

                        result.setText(result.getText() + "\n" + data1 + " "
                                + " " + data2 + " " + data3 + " " + data4 + " " + data5
                        );

                    } catch (Exception e) {

                    }

                }
            } catch (Exception e) {

            }

        } else if (event.getSource() == displayContents) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DisplayContent.fxml"));
            Parent root = loader.load();
            DisplayContentController controller = loader.getController();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("DB Browser");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        }

    }
}
