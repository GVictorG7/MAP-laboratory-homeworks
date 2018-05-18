package view;

import domain.Nota;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.NoteService;
import utils.ListEvent;
import utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class NotaController implements Observer<Nota> {
    @FXML
    TableView tableView;
    @FXML
    TableColumn tableColumnStudent;
    @FXML
    TableColumn tableColumnTema;
    @FXML
    TableColumn tableColumNota;
    @FXML
    ChoiceBox<String> choiceBox;
    private ObservableList<Nota> model = FXCollections.observableArrayList();
    private NoteService service;
    @FXML
    private TextField textFieldStudent;
    @FXML
    private TextField textFieldTema;
    @FXML
    private TextField textFieldNota;
    @FXML
    private TextField textFieldSaptamana;
    @FXML
    private TextArea textAreaObservatii;

    @FXML
    public void initialize() {
        tableColumnStudent.setCellValueFactory(new PropertyValueFactory<Nota, String>("student"));
        tableColumnTema.setCellValueFactory(new PropertyValueFactory<Nota, String>("tema"));
        tableColumNota.setCellValueFactory(new PropertyValueFactory<Nota, String>("valoare"));
        tableView.setItems(model);

        ObservableList<Nota> filtered = FXCollections.observableArrayList();

        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                switch (newValue) {
                    case "default":
                        tableView.setItems(model);
                        break;
                    case "student":
                        filtered.clear();
                        filtered.addAll(service.filtrareNoteStudent(Integer.parseInt(textFieldStudent.getText())));
                        tableView.setItems(filtered);
                        break;
                    case "tema":
                        filtered.clear();
                        filtered.addAll(service.filtrareNoteTema(Integer.parseInt(textFieldTema.getText())));
                        tableView.setItems(filtered);
                        break;
                    case "sub 5":
                        filtered.clear();
                        filtered.addAll(service.filtrareNoteSub5());
                        tableView.setItems(filtered);
                        break;
                }
            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Nota>() {
            @Override
            public void changed(ObservableValue<? extends Nota> observable, Nota oldValue, Nota newValue) {
                showNotaDetails(newValue);
            }
        });
    }

    @Override
    public void notifyEvent(ListEvent<Nota> e) {
        model.setAll(StreamSupport.stream(e.getList().spliterator(), false).collect(Collectors.toList()));
    }

    public void setService(NoteService ser) {
        service = ser;
        List<Nota> not = new ArrayList<>();
        service.getNote().forEach(not::add);
        this.model.setAll(not);
    }

    public ObservableList<Nota> getModel() {
        return model;
    }

    public void handleNotare(ActionEvent actionEvent) {
        try {
            int student = Integer.parseInt(textFieldStudent.getText());
            int tema = Integer.parseInt(textFieldTema.getText());
            int nota = Integer.parseInt(textFieldNota.getText());
            int saptamana = Integer.parseInt(textFieldSaptamana.getText());
            String obs = textAreaObservatii.getText();
            service.notare(student, tema, nota, obs, saptamana);
            showNota(Alert.AlertType.INFORMATION, "Nota Adaugata", "Nota a fost adaugata");
        } catch (NumberFormatException e) {
            showNota(Alert.AlertType.ERROR, "Mesaj Eroare", "NumberFormatException: " + e.getMessage());
        }
    }

    public void clearFields(ActionEvent actionEvent) {
        textFieldStudent.setText("");
        textFieldTema.setText("");
        textFieldNota.setText("");
        textFieldSaptamana.setText("");
        textAreaObservatii.setText("");
    }

    private void showNota(Alert.AlertType type, String header, String text) {
        Alert mess = new Alert(type);
        mess.setHeaderText(header);
        mess.setContentText(text);
        mess.showAndWait();
    }

    private void showNotaDetails(Nota nota) {
        if (nota != null) {
            textFieldStudent.setText(Integer.toString(nota.getStudent()));
            textFieldTema.setText(Integer.toString(nota.getTema()));
            textFieldNota.setText(Integer.toString(nota.getValoare()));
        } else clearFields(null);
    }
}
