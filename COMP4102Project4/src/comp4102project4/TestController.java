package comp4102project4;



        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.stage.Stage;

        import java.io.IOException;

public class TestController {

    @FXML
    private Button action;

    @FXML
    void action(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomQuery.fxml"));
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
