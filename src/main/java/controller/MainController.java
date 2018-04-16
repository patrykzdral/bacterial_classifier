package controller;

import auxiliaryStructures.ExaminedContainer;
import auxiliaryStructures.ProcedureHistory;
import auxiliaryStructures.TestBacteria;
import customMessageBox.CustomMessageBox;
import database.Service;
import database.entity.Examined;
import database.entity.Flagella;
import database.entity.History;
import database.entity.Toughness;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import xml_parser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Button buttonDeleteFromWaitingList;
    public Button buttonClassifyWaitingList;
    public Button buttonAddToWaitingList;
    public Button buttonClassifyOneGenotype;
    public Button buttonDeleteFromHistory;
    public Button buttonSaveToXML;

    public TextField textFieldGenotype;
    public TableColumn<ProcedureHistory, String> tableColumnClassifiedGenotype;
    public TableColumn<ProcedureHistory, String> tableColumnClassifiedGenotypeClass;
    public TableColumn<ProcedureHistory, Integer> tableColumnClassifiedGenotypeAlpha;
    public TableColumn<ProcedureHistory, Integer> tableColumnClassifiedGenotypeBeta;
    public TableColumn<ProcedureHistory, Integer> tableColumnClassifiedGenotypeGamma;
    public TableColumn<ProcedureHistory, Date> tableColumnClassifiedGenotypeDate;

    private XmlParser xmlParser;
    private CustomMessageBox customMessageBox;
    private Service service;

    private ObservableList<Examined> observableListExaminedBacterias = FXCollections.observableArrayList();
    private ObservableList<TestBacteria> observableListWaitingList = FXCollections.observableArrayList();
    private ObservableList<ProcedureHistory> observableListProcedureHistory = FXCollections.observableArrayList();

    public TableView<ProcedureHistory> tableViewHistory;
    public TableView<TestBacteria> tableViewWaitingList;
    public TableColumn<TestBacteria, Integer> tableColumnGenotype;
    public TableColumn<TestBacteria, Integer> tableColumnGenotypeAlpha;
    public TableColumn<TestBacteria, Integer> tableColumnGenotypeBeta;
    public TableColumn<TestBacteria, Integer> tableColumnGenotypeGamma;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableColumnGenotype.setCellValueFactory(new PropertyValueFactory<>("genotype"));
        tableColumnGenotypeAlpha.setCellValueFactory(new PropertyValueFactory<>("alpha"));
        tableColumnGenotypeBeta.setCellValueFactory(new PropertyValueFactory<>("beta"));
        tableColumnGenotypeGamma.setCellValueFactory(new PropertyValueFactory<>("gamma"));

        tableColumnClassifiedGenotype.setCellValueFactory(new PropertyValueFactory<>("genotype"));
        tableColumnClassifiedGenotypeClass.setCellValueFactory(new PropertyValueFactory<>("bacteriaClass"));
        tableColumnClassifiedGenotypeAlpha.setCellValueFactory(new PropertyValueFactory<>("alpha"));
        tableColumnClassifiedGenotypeBeta.setCellValueFactory(new PropertyValueFactory<>("beta"));
        tableColumnClassifiedGenotypeGamma.setCellValueFactory(new PropertyValueFactory<>("gamma"));
        tableColumnClassifiedGenotypeDate.setCellValueFactory(new PropertyValueFactory<>("date"));


        tableViewWaitingList.setItems(observableListWaitingList);
        tableViewHistory.setItems(observableListProcedureHistory);

        customMessageBox = new CustomMessageBox();
        service = new Service(ChooseDatabaseController.dbConnection);
        xmlParser = new XmlParser();


        updateHistory();
        updateExaminedView();
    }

    private void nnAlgorithm(TestBacteria testBacteria) throws SQLException {
        Double minDistance = Double.MAX_VALUE;
        Double currentDistance = Double.MAX_VALUE;
        List<Flagella> flagellaList;
        List<Toughness> toughnessList;

        Flagella newFlagella = new Flagella();
        Toughness newToughness = new Toughness();
        Flagella nearestFlagella = null;
        Toughness nearestToughness = null;
        Examined examined = null;
        try {
            examined = service.getEntityByGenotype(testBacteria.getGenotype().toString());
        }catch(SQLException e){

        }
        flagellaList = new ArrayList<>(service.getFlagellaList());
        toughnessList = new ArrayList<>(service.getToughnessList());

        for (Flagella flagella : flagellaList) {
            currentDistance = Math.sqrt(Math.pow((flagella.getAlpha() - testBacteria.getAlpha()), 2) + Math.pow(flagella.getBeta() - testBacteria.getBeta(), 2));
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                nearestFlagella = flagella;
            }
        }
        newFlagella.setAlpha(testBacteria.getAlpha());
        newFlagella.setBeta(testBacteria.getBeta());
        newFlagella.setNumber(nearestFlagella.getNumber());
        newFlagella.setId(nearestFlagella.getId());

        minDistance = Double.MAX_VALUE;
        for (Toughness toughness : toughnessList) {
            currentDistance = Math.sqrt(Math.pow((toughness.getBeta() - testBacteria.getBeta()), 2) + Math.pow(toughness.getGamma() - testBacteria.getGamma(), 2));
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                nearestToughness = toughness;
            }
        }
        newToughness.setId(nearestToughness.getId());
        newToughness.setBeta(testBacteria.getBeta());
        newToughness.setGamma(testBacteria.getGamma());
        newToughness.setRank(nearestToughness.getRank());


        if (examined == null) {

            examined = new Examined(testBacteria.getGenotype().toString(), String.valueOf(newFlagella.getNumber()) + newToughness.getRank(), newFlagella.getId(), newToughness.getId());
            service.saveExamined(examined);
            service.saveHistory(new History(examined.getId()));
        } else {
            examined.setBclass(String.valueOf(nearestFlagella.getNumber()) + nearestToughness.getRank());
            examined.setFlagellaId(nearestFlagella.getId());
            examined.setToughnessId(nearestToughness.getId());
            service.updateExamined(examined);

            updateHistory();

        }
    }

    public void writeToXML_onAction(ActionEvent actionEvent) {
        File rootDirectory;
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Choose the root directory");
        rootDirectory = dc.showDialog(null);
        if (rootDirectory.isDirectory()) {
            List<Examined> examined;
            examined = new ArrayList<>(observableListExaminedBacterias);
            ExaminedContainer examinedContainer = new ExaminedContainer();
            examinedContainer.setExaminedBacteriaList(examined);
            try {
                xmlParser.write(examined, rootDirectory.getPath());
            } catch (JAXBException e) {
                e.printStackTrace();
            }

        }
    }

    public void deleteFromHistory_onAction(ActionEvent actionEvent) {
        if (tableViewHistory.getSelectionModel().getSelectedItem() != null) {
            ProcedureHistory historyItem = tableViewHistory.getSelectionModel().getSelectedItem();
            try {
                service.deleteExaminedByGenotype(historyItem.getGenotype());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            updateHistory();
            updateExaminedView();
        } else {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie", "Operacja usunięcia nie powiedzie się.", "Powód: nie zaznaczono elementu.").showAndWait();
        }
    }

    public void classifyOneGenotype_onAction(ActionEvent actionEvent) {
        try {
            TestBacteria testBacteria = new TestBacteria(Integer.parseInt(textFieldGenotype.getText()));
            try {
                nnAlgorithm(testBacteria);
            } catch (SQLException e) {
                e.printStackTrace();
                customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie", "Operacja klasyfikacji nie powiodła  się.", "Powód: Błąd SQL").showAndWait();
            }
            updateExaminedView();
            updateHistory();
        } catch (NumberFormatException | StringIndexOutOfBoundsException ex) {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie", "Operacja klasyfikacji nie powiodła  się.", "Powód: wprowadzono niepoprawny format genotypu.").showAndWait();
        }


    }

    public void addToWaitingList_onAction(ActionEvent actionEvent) {
        try {
            TestBacteria testBacteria = new TestBacteria(Integer.parseInt(textFieldGenotype.getText()));
            observableListWaitingList.add(testBacteria);
        } catch (NumberFormatException | StringIndexOutOfBoundsException ex) {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie", "Operacja dodania do kolejki nie powiodła  się.", "Powód: wprowadzono niepoprawny format genotypu").showAndWait();
        }
    }

    public void classifyWaitingList_onAction(ActionEvent actionEvent) {
        try {
            ChooseDatabaseController.dbConnection.getConnection().setAutoCommit(false);
            for (TestBacteria testBacteria : observableListWaitingList) {
                nnAlgorithm(testBacteria);
                updateExaminedView();
            }
            ChooseDatabaseController.dbConnection.getConnection().commit();
            ChooseDatabaseController.dbConnection.getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie", "Operacja klasyfikacji nie powiodła  się.", "Powód: Błąd SQL").showAndWait();
        } catch (StringIndexOutOfBoundsException | NumberFormatException ex) {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie", "Operacja klasyfikacji nie powiodła  się.", "Powód: wprowadzono niepoprwany format genotypu").showAndWait();
        }
        observableListWaitingList.clear();
        updateHistory();
        updateExaminedView();
    }

    public void deleteFromWaitingList_onAction(ActionEvent actionEvent) {
        if (tableViewWaitingList.getSelectionModel().getSelectedItem() != null) {
            observableListWaitingList.remove(tableViewWaitingList.getSelectionModel().getSelectedItem());
        } else {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie", "Operacja usunięcia nie powiedzie się.", "Powód: nie zaznaczono elementu.").showAndWait();
        }
    }

    private void updateHistory() {
        try {

            observableListProcedureHistory.clear();
            observableListProcedureHistory.setAll(service.getHistoryOfExaminedBacteria(null));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateExaminedView() {
        try {
            observableListExaminedBacterias.clear();
            observableListExaminedBacterias.addAll(service.getExaminedList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
