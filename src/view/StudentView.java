package view;

import domain.Student;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StudentView {

    private TextField txtfieldidStudent = new TextField();
    private TextField txtfieldNume = new TextField();
    private TextField txtfieldGrupa = new TextField();
    private TextField txtfieldEmail = new TextField();
    private TextField txtfieldProf = new TextField();
    private TableView<Student> tableView = new TableView<>();
    private StudentController ctrl;
    private BorderPane borderPane;

    public StudentView(StudentController ctrl) {
        this.ctrl = ctrl;
        initView();
    }

    private void initView() {
        borderPane = new BorderPane();
        borderPane.setTop(initTop());
        borderPane.setLeft(initLeft());
        borderPane.setCenter(initCenter());
    }

    private AnchorPane initTop() {
        AnchorPane topAnchorPane = new AnchorPane();

        Label titleLabel = new Label("Student Operations...");
        topAnchorPane.getChildren().add(titleLabel);
        AnchorPane.setTopAnchor(titleLabel, 20d);
        AnchorPane.setLeftAnchor(titleLabel, 100d);
        titleLabel.setFont(new Font(30));
        return topAnchorPane;
    }

    private AnchorPane initLeft() {
        AnchorPane leftAnchorPane = new AnchorPane();
        tableView = createStudentTableView();
        leftAnchorPane.getChildren().add(tableView);
        AnchorPane.setLeftAnchor(tableView, 20d);
        return leftAnchorPane;
    }

    private AnchorPane initCenter() {
        AnchorPane centerAnchorPane = new AnchorPane();
        GridPane gridPane = createGridPane();
        AnchorPane.setRightAnchor(gridPane, 20d);
        centerAnchorPane.getChildren().add(gridPane);

        HBox buttonZone = new HBox();
        Button addButton = new Button("Add");
        addButton.setOnAction(ctrl::handleAddStudent);
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(ctrl::handleDeleteStudent);
        Button updateButton = new Button("Update");
        updateButton.setOnAction(ctrl::handleUpdateStudent);
        Button clearButton = new Button("Clear Fields");
        clearButton.setOnAction(ctrl::handleClearFields);
        buttonZone.getChildren().addAll(addButton, deleteButton, updateButton, clearButton);
        AnchorPane.setBottomAnchor(buttonZone, 5d);

        centerAnchorPane.getChildren().add(buttonZone);
        return centerAnchorPane;
    }

    private GridPane createGridPane() {
        GridPane gp = new GridPane();
        Label labelidStudent = createLabel("idStudent");
        Label labelNume = createLabel("Nume:");
        Label labelGrupa = createLabel("Grupa:");
        Label labelEmail = createLabel("Email:");
        Label labelProf = createLabel("Prof:");
        gp.add(labelidStudent, 0, 0);
        gp.add(this.txtfieldidStudent, 1, 0);
        gp.add(labelNume, 0, 1);
        gp.add(this.txtfieldNume, 1, 1);
        gp.add(labelGrupa, 0, 2);
        gp.add(this.txtfieldGrupa, 1, 2);
        gp.add(labelEmail, 0, 3);
        gp.add(this.txtfieldEmail, 1, 3);
        gp.add(labelProf, 0, 4);
        gp.add(this.txtfieldProf, 1, 4);
        gp.setHgap(5);
        gp.setVgap(5);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPrefWidth(100);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPrefWidth(250);

        gp.getColumnConstraints().addAll(c1, c2);
        return gp;
    }

    private TableView<Student> createStudentTableView() {
        TableColumn<Student, String> colidStudent = new TableColumn<>("idStudent");
        TableColumn<Student, String> colNume = new TableColumn<>("Nume");
        TableColumn<Student, String> colGrupa = new TableColumn<>("Grupa");
        TableColumn<Student, String> colEmail = new TableColumn<>("Email");
        TableColumn<Student, String> colProf = new TableColumn<>("Prof");
        tableView.getColumns().addAll(colidStudent, colNume, colGrupa, colEmail, colProf);
        colidStudent.setCellValueFactory(new PropertyValueFactory<>("idStudent"));
        colNume.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        colGrupa.setCellValueFactory(new PropertyValueFactory<>("Grupa"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colProf.setCellValueFactory(new PropertyValueFactory<>("Prof"));
        tableView.setItems(ctrl.getModel());
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                ctrl.showStudentDetails(newValue);
            }
        });
        return tableView;
    }

    public BorderPane getView() {
        return borderPane;
    }

    private Label createLabel(String s) {
        Label l = new Label(s);
        l.setFont(new Font(15));
        l.setTextFill(Color.BLUE);
        l.setStyle("-fx-font-weight: bold");
        return l;
    }
}
