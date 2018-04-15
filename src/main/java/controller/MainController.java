package controller;

import auxiliaryStructures.TestBacteria;
import customMessageBox.CustomMessageBox;
import auxiliaryStructures.ProcedureHistory;
import database.Service;
import database.dao.DbConnection;
import database.entity.Examined;
import database.entity.History;
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
    private ResultSet rs;
    private PreparedStatement ps;
    private CallableStatement cs;
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

    public Examined NnAlgorithm(TestBacteria testBacteria) {
        Examined bacteria = new Examined();
        int firstDifference = Integer.MAX_VALUE;
        int secondDifference = Integer.MAX_VALUE;
        int searchAlpha = 0;
        int searchBeta = 0;
        int searchGamma = 0;
        try {
            ps = dbConnection.getConnection().prepareStatement("SELECT alpha From flagella");
            ps.execute();
            rs = ps.getResultSet();
            while (rs.next()) {
                if (firstDifference > Math.abs(rs.getInt(1) - testBacteria.getAlpha())) {
                    firstDifference = Math.abs(rs.getInt(1) - testBacteria.getAlpha());
                    searchAlpha = rs.getInt(1);
                }
            }

            ps = dbConnection.getConnection().prepareStatement("SELECT beta From flagella WHERE alpha=(?)");
            ps.setInt(1, searchAlpha);
            ps.execute();
            rs = ps.getResultSet();
            while (rs.next()) {
                if (secondDifference > Math.abs(rs.getInt(1) - testBacteria.getBeta())) {
                    secondDifference = Math.abs(rs.getInt(1) - testBacteria.getBeta());
                    searchBeta = rs.getInt(1);
                }
            }

            ps = dbConnection.getConnection().prepareStatement("SELECT number,id From flagella WHERE alpha=(?) AND beta=(?)");
            ps.setInt(1, searchAlpha);
            ps.setInt(2, searchBeta);
            ps.execute();
            rs = ps.getResultSet();
            rs.next();
            bacteria.setBclass(String.valueOf(rs.getInt(1)));
            bacteria.setFlagellaId(rs.getInt(2));
            //###########################################################################
            firstDifference = Integer.MAX_VALUE;
            secondDifference = Integer.MAX_VALUE;

            ps = dbConnection.getConnection().prepareStatement("SELECT beta From toughness");
            ps.execute();
            rs = ps.getResultSet();
            while (rs.next()) {
                if (firstDifference > Math.abs(rs.getInt(1) - testBacteria.getBeta())) {
                    firstDifference = Math.abs(rs.getInt(1) - testBacteria.getBeta());
                    searchBeta = rs.getInt(1);
                }
            }

            ps = dbConnection.getConnection().prepareStatement("SELECT gamma From toughness WHERE beta=(?)");
            ps.setInt(1, searchBeta);
            ps.execute();
            rs = ps.getResultSet();
            while (rs.next()) {
                if (secondDifference > Math.abs(rs.getInt(1) - testBacteria.getGamma())) {
                    secondDifference = Math.abs(rs.getInt(1) - testBacteria.getGamma());
                    searchGamma = rs.getInt(1);
                }
            }

            ps = dbConnection.getConnection().prepareStatement("SELECT  rank,id From toughness WHERE beta=(?) AND gamma=(?)");
            ps.setInt(1, searchBeta);
            ps.setInt(2, searchGamma);
            ps.execute();
            rs = ps.getResultSet();
            rs.next();
            bacteria.setGenotype(testBacteria.getGenotype().toString());
            bacteria.setBclass(bacteria.getBclass().concat(rs.getString(1)));
            bacteria.setToughnessId(rs.getInt(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bacteria;
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
            //service.deleteHistoryById(historyIte)
            observableListProcedureHistory.remove(historyItem);
        } else {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie", "Operacja usunięcia nie powiedzie się.", "Powód: nie zaznaczono elementu.").showAndWait();
        }
    }

    public void classifyOneGenotype_onAction(ActionEvent actionEvent) {
        TestBacteria testBacteria = new TestBacteria(Integer.parseInt(textFieldGenotype.getText()));
        Examined examined = NnAlgorithm(testBacteria);
        observableListExaminedBacterias.add(examined);
        try {
            service.saveExamined(examined);
            java.util.Date utilDate = new java.util.Date();
            service.saveHistory(new History(examined.getId(), new Date(utilDate.getTime())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateHistory();
        updateExaminedView();

    }

    public void addToWaitingList_onAction(ActionEvent actionEvent) {
        TestBacteria testBacteria = new TestBacteria(Integer.parseInt(textFieldGenotype.getText()));
        observableListWaitingList.add(testBacteria);
    }

    public void classifyWaitingList_onAction(ActionEvent actionEvent) {
        try {
            dbConnection.getConnection().setAutoCommit(false);
            for (TestBacteria testBacteria : observableListWaitingList) {
                Examined examined = NnAlgorithm(testBacteria);
                observableListExaminedBacterias.add(examined);
                try {
                    service.saveExamined(examined);
                    java.util.Date utilDate = new java.util.Date();
                    service.saveHistory(new History(examined.getId(), new Date(utilDate.getTime())));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            dbConnection.getConnection().commit();
            dbConnection.getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
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
