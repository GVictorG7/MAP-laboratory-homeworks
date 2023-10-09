package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.NoteService;
import service.StudentService;
import service.TemeService;

import java.io.IOException;

public class MainController {
    private final StudentService studentService = new StudentService();
    private final TemeService temeService = new TemeService();
    private final NoteService noteService = new NoteService();

    public void handleLaunchStudentCRUD(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("StudentFXML.fxml"));
            AnchorPane root = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Student CRUD");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            StudentController studentController = loader.getController();
            studentController.setService(studentService);
            studentService.addObserver(studentController);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLaunchTemeCRUD(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("TemaFXML.fxml"));
            AnchorPane root = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tema CRUD");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            TemaController temaController = loader.getController();
            temaController.setService(temeService);
            temeService.addObserver(temaController);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLaunchNoteCRUD(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("NotaFXML.fxml"));
            AnchorPane root = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nota CRUD");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            NotaController notaController = loader.getController();
            notaController.setService(noteService);
            noteService.addObserver(notaController);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
