
package comp4102project4;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DisplayContentController implements Initializable {

    @FXML
    private Button addNew;
    @FXML
    private Button update;
    @FXML
    private Button delete;
    @FXML
    private Button displayContents;
    @FXML
    private Button customQuery;
    @FXML
    private TableColumn<Data, String> column1;
    @FXML
    private TableColumn<Data, String> column2;
    @FXML
    private TableColumn<Data, String> column3;
    @FXML
    private TableColumn<Data, String> column4;
    @FXML
    private TableColumn<Data, String> column5;
    @FXML
    private TextField data1;
    @FXML
    private TextField data3;
    @FXML
    private TextField data2;
    @FXML
    private TextField data5;
    @FXML
    private TextField data4;
    @FXML
    private Button doIt;
    @FXML
    private Label listLabel;
    Connection connection;
    private PreparedStatement preparedStatement;
    boolean student = false;
    boolean enrollment = false;
    boolean course = false;
    boolean add = false;
    boolean updatee = false;
    boolean deletee = false;
    @FXML
    private TableView<Data> table;
    @FXML
    private Button studentButton;
    @FXML
    private Button courseButton;
    @FXML
    private Button enrollmentButton;

    public int id;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data1.setDisable(true);
        data2.setDisable(true);
        data3.setDisable(true);
        data4.setDisable(true);
        data5.setDisable(true);
        doIt.setDisable(true);

        column1.setText("1");
        column2.setText("2");
        column3.setText("3");
        column4.setText("4");
        column5.setText("5");

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/comp4102", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(DisplayContentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setRowFactory(tv -> {
            TableRow<Data> row = new TableRow<>();
            row.setOnMouseClicked(event2 -> {
                if (event2.getClickCount() == 1 && (!row.isEmpty())) {
                    Data rowData = row.getItem();

                    id = table.getSelectionModel().getSelectedIndex();
                }
            });
            return row;
        });

    }

    public void setTable(String queryString) {

        LinkedList<Data> datasList = new LinkedList();
        ObservableList<Data> datas;
        try {
            preparedStatement = connection.prepareStatement(queryString);
        } catch (SQLException ex) {
            Logger.getLogger(DisplayContentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ResultSet rset;
        try {
            rset = preparedStatement.executeQuery();

            while (rset.next()) {
                try {
                    String data1 = rset.getString(1);
                    String data2 = rset.getString(2);
                    String data3 = rset.getString(3);
                    String data4 = rset.getString(4);
                    String data5 = rset.getString(5);
                    ArrayList<String> datasArray = new ArrayList<String>();
                    datasArray.add(data1);
                    datasArray.add(data2);
                    datasArray.add(data3);
                    datasArray.add(data4);
                    datasArray.add(data5);

                    datasList.add(new Data(datasArray.get(0), datasArray.get(1), datasArray.get(2), datasArray.get(3), datasArray.get(4)));

                    datas = FXCollections.observableArrayList(datasList);
                    table.setItems(datas);
                    column1.setCellValueFactory(new PropertyValueFactory<>(datasList.get(0).data1Property().getName()));
                    column2.setCellValueFactory(new PropertyValueFactory<>(datasList.get(0).data2Property().getName()));
                    column3.setCellValueFactory(new PropertyValueFactory<>(datasList.get(0).data3Property().getName()));
                    column4.setCellValueFactory(new PropertyValueFactory<>(datasList.get(0).data4Property().getName()));
                    column5.setCellValueFactory(new PropertyValueFactory<>(datasList.get(0).data5Property().getName()));
                    table.getColumns().setAll(column1, column2, column3, column4, column5);

                } catch (Exception e) {

                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(DisplayContentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException, SQLException {

        if (event.getSource() == addNew) {
            data1.setDisable(false);
            data2.setDisable(false);
            data3.setDisable(false);
            data4.setDisable(false);
            data5.setDisable(false);
            doIt.setDisable(false);
            add = true;

        } else if (event.getSource() == update) {
            data1.setDisable(false);
            data2.setDisable(false);
            data3.setDisable(false);
            data4.setDisable(false);
            data5.setDisable(false);
            doIt.setDisable(false);

            updatee = true;

        } else if (event.getSource() == delete) {
            deletee = true;

            if (student) {

                Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ResultSet rset = stmt.executeQuery("select * from student");
                rset.absolute(id + 1);
                rset.deleteRow();
                rset.moveToCurrentRow();
                setTable("select * from student");
                deletee = false;
            } else if (course) {
                Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ResultSet rset = stmt.executeQuery("select * from course");
                rset.absolute(id + 1);
                rset.deleteRow();
                rset.moveToCurrentRow();
                setTable("select * from course");
                deletee = false;
            } else if (enrollment) {
                PreparedStatement prepStmt = connection.prepareStatement(
                        "select * from enrollment",
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);

                ResultSet rset = prepStmt.executeQuery("select * from enrollment");
                rset.absolute(id + 1);
                rset.deleteRow();
                rset.moveToCurrentRow();
                setTable("select * from enrollment");
                deletee = false;
            }

        } else if (event.getSource() == doIt) {

            data1.setDisable(true);
            data2.setDisable(true);
            data3.setDisable(true);
            data4.setDisable(true);
            data5.setDisable(true);
            doIt.setDisable(true);

            if (add) {

                if (student) {

                    Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ResultSet rset = stmt.executeQuery("select * from student");
                    rset.moveToInsertRow();
                    try {
                        rset.updateString("ssn", data1.getText());
                        rset.updateString("firstName", data2.getText());
                        rset.updateString("middleName", data3.getText());
                        rset.updateString("lastName", data4.getText());
                        rset.updateString("departmentId", data5.getText());
                    } catch (Exception e) {

                    }
                    rset.insertRow();
                    rset.moveToCurrentRow();
                    setTable("select * from student");
                    add = false;
                } else if (course) {
                    Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ResultSet rset = stmt.executeQuery("select * from course");
                    rset.moveToInsertRow();
                    try {
                        rset.updateString("courseCode", data1.getText());
                        rset.updateString("courseName", data2.getText());
                        rset.updateString("ects", data3.getText());
                        rset.updateString("numHours", data4.getText());
                        rset.updateString("prereq_courseCode", data5.getText());
                    } catch (Exception e) {

                    }
                    rset.insertRow();
                    rset.moveToCurrentRow();
                    setTable("select * from course");
                    add = false;
                } else if (enrollment) {
                    PreparedStatement prepStmt = connection.prepareStatement(
                            "select * from enrollment",
                            ResultSet.TYPE_FORWARD_ONLY,
                            ResultSet.CONCUR_UPDATABLE);

                    ResultSet rset = prepStmt.executeQuery("select * from enrollment");
                    rset.moveToInsertRow();
                    try {
                        rset.updateString("enrollmentId", data1.getText());
                        rset.updateString("sssn", data2.getText());
                        rset.updateString("courseCode", data3.getText());
                        rset.updateString("yearr", data4.getText());
                        rset.updateString("semester", data5.getText());
                    } catch (Exception e) {

                    }
                    rset.insertRow();
                    rset.moveToCurrentRow();
                    setTable("select * from enrollment");
                    add = false;
                }
            }

            if (updatee) {

                if (student) {

                    Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ResultSet rset = stmt.executeQuery("select * from student");
                    rset.absolute(id + 1);
                    try {
                        rset.updateString("ssn", data1.getText());
                        rset.updateString("firstName", data2.getText());
                        rset.updateString("middleName", data3.getText());
                        rset.updateString("lastName", data4.getText());
                        rset.updateString("departmentId", data5.getText());
                    } catch (Exception e) {

                    }
                    rset.updateRow();
                    rset.moveToCurrentRow();
                    setTable("select * from student");
                    updatee = false;
                } else if (course) {
                    Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ResultSet rset = stmt.executeQuery("select * from course");
                    rset.absolute(id + 1);
                    try {
                        rset.updateString("courseCode", data1.getText());
                        rset.updateString("courseName", data2.getText());
                        rset.updateString("ects", data3.getText());
                        rset.updateString("numHours", data4.getText());
                        rset.updateString("prereq_courseCode", data5.getText());
                    } catch (Exception e) {

                    }
                    rset.updateRow();
                    rset.moveToCurrentRow();
                    setTable("select * from course");
                    updatee = false;
                } else if (enrollment) {
                    PreparedStatement prepStmt = connection.prepareStatement(
                            "select * from enrollment",
                            ResultSet.TYPE_FORWARD_ONLY,
                            ResultSet.CONCUR_UPDATABLE);

                    ResultSet rset = prepStmt.executeQuery("select * from enrollment");
                    rset.absolute(id + 1);
                    try {
                        rset.updateString("enrollmentId", data1.getText());
                        rset.updateString("sssn", data2.getText());
                        rset.updateString("courseCode", data3.getText());
                        rset.updateString("yearr", data4.getText());
                        rset.updateString("semester", data5.getText());
                    } catch (Exception e) {

                    }
                    rset.updateRow();
                    rset.moveToCurrentRow();
                    setTable("select * from enrollment");
                    updatee = false;
                }

            }

        } else if (event.getSource() == customQuery) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomQuery.fxml"));
            Parent root = loader.load();
            CustomQueryController controller = loader.getController();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("DB Browser");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } else if (event.getSource() == studentButton) {

            student = true;
            enrollment = false;
            course = false;
            String queryString = "select * from student";
            setTable(queryString);

            column1.setText("ssn");
            column2.setText("firstName");
            column3.setText("middleName");
            column4.setText("lastName");
            column5.setText("departmentId");

        } else if (event.getSource() == courseButton) {

            student = false;
            enrollment = false;
            course = true;

            String queryString = "select * from course";
            setTable(queryString);

            column1.setText("courseCode");
            column2.setText("courseName");
            column3.setText("ects");
            column4.setText("numHours");
            column5.setText("prereq_courseCode");

        } else if (event.getSource() == enrollmentButton) {

            student = false;
            enrollment = true;
            course = false;
            String queryString = "select * from enrollment";
            setTable(queryString);
            column1.setText("enrollmentId");
            column2.setText("sssn");
            column3.setText("courseCode");
            column4.setText("yearr");
            column5.setText("semester");

        }

    }

}
