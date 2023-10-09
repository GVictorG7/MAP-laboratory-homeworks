package view;

import domain.Tema;
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
import service.TemeService;
import utils.ListEvent;
import utils.Observer;
import validator.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class TemaController implements Observer<Tema> {
    @FXML
    TableView tableView;
    @FXML
    TableColumn tableColumnNumar;
    @FXML
    TableColumn tableColumnDeadline;
    @FXML
    TableColumn tableColumnDescriere;
    @FXML
    ChoiceBox<String> choiceBox;
    private final ObservableList<Tema> model = FXCollections.observableArrayList();
    private TemeService service;
    @FXML
    private TextField textFieldNumar;
    @FXML
    private TextField textFieldDeadline;
    @FXML
    private TextField textFieldDescriere;

    @FXML
    public void initialize() {
        tableColumnNumar.setCellValueFactory(new PropertyValueFactory<Tema, String>("nr"));
        tableColumnDeadline.setCellValueFactory(new PropertyValueFactory<Tema, String>("deadline"));
        tableColumnDescriere.setCellValueFactory(new PropertyValueFactory<Tema, String>("descriere"));
        tableView.setItems(model);

        ObservableList<Tema> filtered = FXCollections.observableArrayList();

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "default":
                    tableView.setItems(model);
                    break;
                case "deadline":
                    filtered.clear();
                    filtered.addAll(service.filtrareDeadline(Integer.parseInt(textFieldDeadline.getText())));
                    tableView.setItems(filtered);
                    break;
                case "descriere":
                    filtered.clear();
                    filtered.addAll(service.filtrareDesc(textFieldDescriere.getText()));
                    tableView.setItems(filtered);
                    break;
                case "urmatoarele 2 saptamani":
                    filtered.clear();
                    filtered.addAll(service.filtrareNext2Weeks(Integer.parseInt(textFieldDeadline.getText())));
                    tableView.setItems(filtered);
                    break;
            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Tema>) (observable, oldValue, newValue) -> showTemaDetails(newValue));
    }

    @Override
    public void notifyEvent(ListEvent<Tema> e) {
        model.setAll(new ArrayList<>(e.getList()));
    }

    void setService(TemeService ser) {
        service = ser;
        List<Tema> tem = new ArrayList<>(service.getTeme());
        this.model.setAll(tem);
    }

    public void handleAddTema(ActionEvent actionEvent) {
        try {
            service.save(extractTema());
            showTema(Alert.AlertType.INFORMATION, "Tema adaugata", "Tema a fost adaugata.");
        } catch (ValidationException e) {
            showTema(Alert.AlertType.ERROR, "Mesaj eroare", "ValidationException: " + e.getMessage());
        } catch (NumberFormatException e) {
            showTema(Alert.AlertType.ERROR, "Mesaj eroare", "NumberFormatException : " + e.getMessage());
        }
    }

    public void handleUpdateTema(ActionEvent actionEvent) {
        try {
            Tema toUpdate = extractTema();
            service.update(toUpdate.getNr(), toUpdate);
            showTema(Alert.AlertType.INFORMATION, "Tema modificata", "Tema a fost modificata.");
        } catch (ValidationException e) {
            showTema(Alert.AlertType.ERROR, "Mesaj eroare", "ValidationException: " + e.getMessage());
        } catch (NumberFormatException e) {
            showTema(Alert.AlertType.ERROR, "Mesaj eroare", "NumberFormatException : " + e.getMessage());
        }
    }

    public void handleDeleteTema(ActionEvent actionEvent) {
        service.deleteTema(extractTema().getNr());
        showTema(Alert.AlertType.INFORMATION, "Tema Eliminata", "Tema a fost eliminata");
    }

    public void clearFields(ActionEvent actionEvent) {
        textFieldNumar.setText("");
        textFieldDeadline.setText("");
        textFieldDescriere.setText("");
    }

    private Tema extractTema() throws NumberFormatException {
        int nr = Integer.parseInt(textFieldNumar.getText());
        int deadline = Integer.parseInt(textFieldDeadline.getText());
        String descriere = textFieldDescriere.getText();
        return new Tema(nr, deadline, descriere);
    }

    private void showTema(Alert.AlertType type, String header, String text) {
        Alert message = new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }

    private void showTemaDetails(Tema tema) {
        if (tema != null) {
            textFieldNumar.setText(Integer.toString(tema.getNr()));
            textFieldDeadline.setText(Integer.toString(tema.getDeadline()));
            textFieldDescriere.setText(tema.getDescriere());
        } else clearFields(null);
    }
}
