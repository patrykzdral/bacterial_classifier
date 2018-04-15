package database;

import auxiliaryStructures.ProcedureHistory;
import database.dao.*;
import database.entity.Examined;
import database.entity.Flagella;
import database.entity.History;
import database.entity.Toughness;

import java.sql.SQLException;
import java.util.List;

public class Service {
    private FlagellaDAO flagellaDAO;
    private ToughnessDAO toughnessDAO;
    private ExaminedDAO examinedDAO;
    private HistoryDAO historyDAO;

    public Service(DbConnection dbConnection) {
        flagellaDAO = new FlagellaDAO(dbConnection);
        toughnessDAO = new ToughnessDAO(dbConnection);
        examinedDAO = new ExaminedDAO(dbConnection);
        historyDAO = new HistoryDAO(dbConnection);
    }

    // Flagella:
    public List<Flagella> getFlagellaList() throws SQLException {
        return flagellaDAO.getEntities();
    }

    public Flagella saveFlagella(Flagella flagella) throws SQLException {
        return flagellaDAO.saveEntity(flagella);
    }

    public Flagella updateFlagella(Flagella flagella) throws SQLException {
        return flagellaDAO.updateEntity(flagella);
    }

    public Flagella getFlagellaById(Integer id) throws SQLException {
        return flagellaDAO.getEntityById(id);
    }

    public Boolean deleteFlagellaById(Integer id) throws SQLException {
        return flagellaDAO.deleteEntityById(id);
    }

    // Toughness:
    public List<Toughness> getToughnessList() throws SQLException {
        return toughnessDAO.getEntities();
    }

    public Toughness saveToughness(Toughness toughness) throws SQLException {
        return toughnessDAO.saveEntity(toughness);
    }

    public Toughness updateToughness(Toughness toughness) throws SQLException {
        return toughnessDAO.updateEntity(toughness);
    }

    public Toughness getToughnessById(Integer id) throws SQLException {
        return toughnessDAO.getEntityById(id);
    }

    public Boolean deleteToughnessById(Integer id) throws SQLException {
        return toughnessDAO.deleteEntityById(id);
    }

    // Examined:
    public List<Examined> getExaminedList() throws SQLException {
        return examinedDAO.getEntities();
    }

    public Examined saveExamined(Examined examined) throws SQLException {
        return examinedDAO.saveEntity(examined);
    }

    public Examined updateExamined(Examined toughness) throws SQLException {
        return examinedDAO.updateEntity(toughness);
    }

    public Examined getExaminedById(Integer id) throws SQLException {
        return examinedDAO.getEntityById(id);
    }
    public Examined getEntityByGenotype(String genotype) throws SQLException {
        return examinedDAO.getEntityByGenotype(genotype);
    }

        public Boolean deleteExaminedById(Integer id) throws SQLException {
        return examinedDAO.deleteEntityById(id);
    }

    public Boolean deleteExaminedByGenotype(String genotype) throws SQLException {
        return examinedDAO.deleteEntityByGenotype(genotype);
    }

    // History:
    public List<History> getHistoryList() throws SQLException {
        return historyDAO.getEntities();
    }

    public History saveHistory(History history) throws SQLException {
        return historyDAO.saveEntity(history);
    }

    public History updateHistory(History history) throws SQLException {
        return historyDAO.updateEntity(history);
    }

    public History getHistoryById(Integer id) throws SQLException {
        return historyDAO.getEntityById(id);
    }

    public Boolean deleteHistoryById(Integer id) throws SQLException {
        return historyDAO.deleteEntityById(id);
    }

    public List<ProcedureHistory> getHistoryOfExaminedBacteria(String genotype) throws SQLException {
        return historyDAO.getHistoryOfExaminedBacteria(genotype);
    }

    // Other:
    public Examined classifyTheBacteria(String genotype, List<Flagella> flagellaList, List<Toughness> toughnessList){
        // TODO:

        return null;
    }
}
