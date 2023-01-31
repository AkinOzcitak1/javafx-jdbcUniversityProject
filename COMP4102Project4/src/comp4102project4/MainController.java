package comp4102project4;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class MainController implements Initializable {

    @FXML
    private Button connect;
    @FXML
    private Button displayContents;
    @FXML
    private Button customQuery;
    @FXML
    private ListView<String> tablesList;
    @FXML
    private TextArea query;
    @FXML
    private Button execute;
    @FXML
    private TextArea result;
    private PreparedStatement preparedStatement;
    @FXML
    private Label listLabel;
    @FXML
    private Label resultLabel;
    Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayContents.setDisable(true);
        customQuery.setDisable(true);
        tablesList.setDisable(true);
        query.setDisable(true);
        execute.setDisable(true);
        result.setDisable(true);
        listLabel.setDisable(true);
        resultLabel.setDisable(true);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        if (event.getSource() == connect) {
            displayContents.setDisable(false);
            customQuery.setDisable(false);
            tablesList.setDisable(false);
            listLabel.setDisable(false);
            tablesList.getItems().add("Student");
            tablesList.getItems().add("Course");
            tablesList.getItems().add("Enrollment");

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/comp4102", "root", "");
            System.out.println("Database connected");

        } else if (event.getSource() == customQuery) {
            query.setDisable(false);
            execute.setDisable(false);

        } else if (event.getSource() == execute) {
            try {
                result.setDisable(false);
                resultLabel.setDisable(false);

                String queryString = query.getText();

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
