package view;

import domain.Tema;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.TemeService;
import utils.ListEvent;
import utils.Observer;
import validator.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    private ObservableList<Tema> model = FXCollections.observableArrayList();
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

        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
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
            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tema>() {
            @Override
            public void changed(ObservableValue<? extends Tema> observable, Tema oldValue, Tema newValue) {
                showTemaDetails(newValue);
            }
        });
    }

    @Override
    public void notifyEvent(ListEvent<Tema> e) {
        model.setAll(StreamSupport.stream(e.getList().spliterator(), false).collect(Collectors.toList()));
    }

    public void setService(TemeService ser) {
        service = ser;
        List<Tema> tem = new ArrayList<>();
        service.getTeme().forEach(tem::add);
        this.model.setAll(tem);
    }

    public ObservableList<Tema> getModel() {
        return model;
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
