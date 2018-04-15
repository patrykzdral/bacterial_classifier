package controller;

import auxiliaryStructures.TestBacteria;
import customMessageBox.CustomMessageBox;
import auxiliaryStructures.ProcedureHistory;
import database.Service;
import database.dao.DbConnection;
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
import auxiliaryStructures.ExaminedContainer;
import xml_parser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URL;
import java.sql.*;
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

    private DbConnection dbConnection;
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
        tableColumnGenotype.setCellValueFactory(new PropertyValueFactory<TestBacteria, Integer>("genotype"));
        tableColumnGenotypeAlpha.setCellValueFactory(new PropertyValueFactory<TestBacteria, Integer>("alpha"));
        tableColumnGenotypeBeta.setCellValueFactory(new PropertyValueFactory<TestBacteria, Integer>("beta"));
        tableColumnGenotypeGamma.setCellValueFactory(new PropertyValueFactory<TestBacteria, Integer>("gamma"));

        tableColumnClassifiedGenotype.setCellValueFactory(new PropertyValueFactory<ProcedureHistory, String>("genotype"));
        tableColumnClassifiedGenotypeClass.setCellValueFactory(new PropertyValueFactory<ProcedureHistory, String>("bacteriaClass"));
        tableColumnClassifiedGenotypeAlpha.setCellValueFactory(new PropertyValueFactory<ProcedureHistory, Integer>("alpha"));
        tableColumnClassifiedGenotypeBeta.setCellValueFactory(new PropertyValueFactory<ProcedureHistory, Integer>("beta"));
        tableColumnClassifiedGenotypeGamma.setCellValueFactory(new PropertyValueFactory<ProcedureHistory, Integer>("gamma"));
        tableColumnClassifiedGenotypeDate.setCellValueFactory(new PropertyValueFactory<ProcedureHistory, Date>("date"));


        tableViewWaitingList.setItems(observableListWaitingList);
        tableViewHistory.setItems(observableListProcedureHistory);

        customMessageBox = new CustomMessageBox();

        try {
            dbConnection = new DbConnection();
            service = new Service(dbConnection);
            xmlParser = new XmlParser();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        updateHistory();
        updateExaminedView();
    }
    public Examined nnAlgorithm(TestBacteria testBacteria) {
        Double minDistance = Double.MAX_VALUE;
        Double currentDistance=Double.MAX_VALUE;
        List<Flagella> flagellaList = null;
        List<Toughness> toughnessList = null;

        Flagella newFlagella = new Flagella();
        Toughness newToughness = new Toughness();
        Flagella nearestFlagella = null;
        Toughness nearestToughness=null;
        Examined examined = null ;
        try {
            System.out.println(testBacteria.getGenotype().toString());
            examined = service.getEntityByGenotype(testBacteria.getGenotype().toString());
            System.out.println(examined);
        } catch (SQLException e) {
        }

        try {
            flagellaList = new ArrayList<>(service.getFlagellaList());
            toughnessList = new ArrayList<>(service.getToughnessList());

        } catch (SQLException e) {
        }
        for(Flagella flagella : flagellaList){
            currentDistance= Math.sqrt(Math.pow((flagella.getAlpha()-testBacteria.getAlpha()),2)+Math.pow(flagella.getBeta()-testBacteria.getBeta(),2));
            if(currentDistance<minDistance){
                minDistance=currentDistance;
                nearestFlagella = flagella;
            }
        }
        newFlagella.setAlpha(testBacteria.getAlpha());
        newFlagella.setBeta(testBacteria.getBeta());
        newFlagella.setNumber(nearestFlagella.getNumber());
        newFlagella.setId(nearestFlagella.getId());

        minDistance=Double.MAX_VALUE;
        for(Toughness toughness : toughnessList){
            currentDistance= Math.sqrt(Math.pow((toughness.getBeta()-testBacteria.getBeta()),2)+Math.pow(toughness.getGamma()-testBacteria.getGamma(),2));
            if(currentDistance<minDistance){
                minDistance=currentDistance;
                nearestToughness = toughness;
            }
        }
        newToughness.setId(nearestToughness.getId());
        newToughness.setBeta(testBacteria.getBeta());
        newToughness.setGamma(testBacteria.getGamma());
        newToughness.setRank(nearestToughness.getRank());



        if (examined == null) {

            examined = new Examined(testBacteria.getGenotype().toString(), String.valueOf(newFlagella.getNumber()) + newToughness.getRank(), newFlagella.getId(), newToughness.getId());
            try {
                service.saveExamined(examined);
                service.saveHistory(new History(examined.getId()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            examined.setBclass(String.valueOf(nearestFlagella.getNumber()) + nearestToughness.getRank());
            examined.setFlagellaId(nearestFlagella.getId());
            examined.setToughnessId(nearestToughness.getId());
            try {
                service.updateExamined(examined);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            updateHistory();

        }
        return examined;
    }

    public void writeToXML_onAction(ActionEvent actionEvent) {
        File rootDirectory;
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Choose the root directory");
        rootDirectory = dc.showDialog(null);
        if (rootDirectory.isDirectory()) {
            List<Examined> examined = new ArrayList<>(observableListExaminedBacterias);
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
            } observableListProcedureHistory.remove(historyItem);
        } else {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie", "Operacja usunięcia nie powiedzie się.", "Powód: nie zaznaczono elementu.").showAndWait();
        }
    }

    public void classifyOneGenotype_onAction(ActionEvent actionEvent) {
        try {
            TestBacteria testBacteria = new TestBacteria(Integer.parseInt(textFieldGenotype.getText()));
            Examined examined = nnAlgorithm(testBacteria);
            observableListExaminedBacterias.add(examined);
            updateHistory();
            updateExaminedView();
            try {
                service.saveExamined(examined);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch(NumberFormatException|StringIndexOutOfBoundsException ex){
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie", "Operacja klasyfikacji nie powiodła  się.", "Powód: wprowadzono niepoprawny format genotypu.").showAndWait();        }


    }

    public void addToWaitingList_onAction(ActionEvent actionEvent) {
        try {
            TestBacteria testBacteria = new TestBacteria(Integer.parseInt(textFieldGenotype.getText()));
            observableListWaitingList.add(testBacteria);
        }catch(NumberFormatException|StringIndexOutOfBoundsException ex){
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie", "Operacja dodania do kolejki nie powiodła  się.", "Powód: wprowadzono niepoprawny format genotypu").showAndWait();        }
    }

    public void classifyWaitingList_onAction(ActionEvent actionEvent) {
        try {
            dbConnection.getConnection().setAutoCommit(false);
            for (TestBacteria testBacteria : observableListWaitingList) {
                Examined examined = nnAlgorithm(testBacteria);
                observableListExaminedBacterias.add(examined);
                try {
                    service.saveExamined(examined);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            dbConnection.getConnection().commit();
            dbConnection.getConnection().setAutoCommit(true);
        } catch (SQLException  e) {
            e.printStackTrace();
        }catch (StringIndexOutOfBoundsException|NumberFormatException ex){
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
