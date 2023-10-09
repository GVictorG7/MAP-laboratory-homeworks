package view;

import domain.Student;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.StudentService;
import utils.ListEvent;
import utils.Observer;
import validator.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class StudentController implements Observer<Student> {
    @FXML
    TableView tableView;
    @FXML
    TableColumn tableColumnIdStudent;
    @FXML
    TableColumn tableColumnNume;
    @FXML
    TableColumn tableColumnGrupa;
    @FXML
    TableColumn tableColumnEmail;
    @FXML
    TableColumn tableColumnProf;
    @FXML
    ChoiceBox<String> choiceBox;
    private final ObservableList<Student> model;
    private StudentService studentService;
    @FXML
    private TextField textFieldIdStudent;
    @FXML
    private TextField textFieldNume;
    @FXML
    private TextField textFieldGrupa;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldProf;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
        List<Student> st = new ArrayList<>(studentService.getStudenti());
        model = FXCollections.observableArrayList(st);
    }

    private static void showStudent(Alert.AlertType type, String header, String text) {
        Alert message = new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }

    private static void showErrorSrudent(String text) {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }

    @FXML
    public void initialize() {
        tableColumnIdStudent.setCellValueFactory(new PropertyValueFactory<Student, String>("idStudent"));
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<Student, String>("nume"));
        tableColumnGrupa.setCellValueFactory(new PropertyValueFactory<Student, String>("grupa"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        tableColumnProf.setCellValueFactory(new PropertyValueFactory<Student, String>("prof"));
        tableView.setItems(model);

        ObservableList<Student> filtered = FXCollections.observableArrayList();

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "default":
                    tableView.setItems(model);
                    break;
                case "Info Romana":
                    filtered.clear();
                    filtered.addAll(studentService.filtrareIR());
                    tableView.setItems(filtered);
                    break;
                case "dupa Prof":
                    filtered.clear();
                    String prof = textFieldProf.getText();
                    filtered.addAll(studentService.filtrareProf(prof));
                    tableView.setItems(filtered);
                    break;
                case "dupa Grupa":
                    filtered.clear();
                    int grupa = Integer.parseInt(textFieldGrupa.getText());
                    filtered.addAll(studentService.filtrareGrupa(grupa));
                    tableView.setItems(filtered);
            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Student>) (observable, oldValue, newValue) -> showStudentDetails(newValue));
    }

    @Override
    public void notifyEvent(ListEvent<Student> e) {
        model.setAll(new ArrayList<>(e.getList()));
    }

    void setService(StudentService ser) {
        studentService = ser;
        List<Student> st = new ArrayList<>(studentService.getStudenti());
        this.model.setAll(st);
    }

    ObservableList<Student> getModel() {
        return model;
    }

    public void handleAddStudent(ActionEvent actionEvent) {
        try {
            Student toAdd = extractStudent();
            studentService.save(toAdd);
            showStudent(Alert.AlertType.INFORMATION, "Student adaugat", "Studentul a fost adaugat.");
        } catch (ValidationException e) {
            showErrorSrudent("Valdiation exception: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            showErrorSrudent("Input exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleUpdateStudent(ActionEvent actionEvent) {
        try {
            Student toUpdate = extractStudent();
            studentService.update(toUpdate.getIdStudent(), toUpdate);
            showStudent(Alert.AlertType.INFORMATION, "Student modificat", "Datele Studentului au fost modificate.");
        } catch (ValidationException e) {
            showErrorSrudent("Valdiation exception: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            showErrorSrudent("Input exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleClearFields(ActionEvent actionEvent) {
        textFieldIdStudent.setText("");
        textFieldNume.setText("");
        textFieldGrupa.setText("");
        textFieldEmail.setText("");
        textFieldProf.setText("");
    }

    public void handleDeleteStudent(ActionEvent actionEvent) {
        Student deletedSt = extractStudent();
        studentService.deleteStudent(deletedSt.getIdStudent());
        showStudent(Alert.AlertType.INFORMATION, "Student eliminat", "Studentul a fost eliminat.");
    }

    private Student extractStudent() throws NumberFormatException {
        int id = Integer.parseInt(textFieldIdStudent.getText());
        String nume = textFieldNume.getText();
        int grupa = Integer.parseInt(textFieldGrupa.getText());
        String email = textFieldEmail.getText();
        String prof = textFieldProf.getText();
        return new Student(id, nume, grupa, email, prof);
    }

    void showStudentDetails(Student newValue) {
        if (newValue != null) {
            textFieldIdStudent.setText(Integer.toString(newValue.getIdStudent()));
            textFieldNume.setText(newValue.getNume());
            textFieldGrupa.setText(Integer.toString(newValue.getGrupa()));
            textFieldEmail.setText(newValue.getEmail());
            textFieldProf.setText(newValue.getProf());
        } else {
            textFieldIdStudent.setText("");
            textFieldNume.setText("");
            textFieldGrupa.setText("");
            textFieldEmail.setText("");
            textFieldProf.setText("");
        }
    }
}
