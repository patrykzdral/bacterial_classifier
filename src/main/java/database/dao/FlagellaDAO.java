package database.dao;


import database.entity.Flagella;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FlagellaDAO implements EntityCRUD<Flagella> {
    private DbConnection dbConnection;

    public FlagellaDAO(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Flagella> getEntities() throws SQLException {
        List<Flagella> flagellaList = new ArrayList<>();
        String statement = "SELECT * FROM flagella";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next())
            flagellaList.add(new Flagella(resultSet.getInt("id"),
                    resultSet.getInt("alpha"),
                    resultSet.getInt("beta"),
                    resultSet.getInt("number")));

        preparedStatement.close();
        return flagellaList;
    }

    @Override
    public Flagella saveEntity(Flagella entity) throws SQLException {
        String statement = "INSERT INTO flagella (alpha, beta, number) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, entity.getAlpha());
        preparedStatement.setInt(2, entity.getBeta());
        preparedStatement.setInt(3, entity.getNumber());
        Boolean methodSucceeded = preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next())
            entity.setId(resultSet.getInt(1));

        preparedStatement.close();
        return (methodSucceeded) ? entity : null;
    }

    @Override
    public Flagella updateEntity(Flagella entity) throws SQLException {
        String statement = "UPDATE flagella SET alpha=?, beta=?, number=? WHERE id=?";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        preparedStatement.setInt(1, entity.getAlpha());
        preparedStatement.setInt(2, entity.getBeta());
        preparedStatement.setInt(3, entity.getNumber());
        preparedStatement.setInt(4, entity.getId());

        Boolean methodSucceeded = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return (methodSucceeded) ? entity : null;
    }

    @Override
    public Flagella getEntityById(int id) throws SQLException {
        String statement = "SELECT * FROM flagella WHERE id=?";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        Flagella flagella = new Flagella(resultSet.getInt("id"),
                resultSet.getInt("alpha"),
                resultSet.getInt("beta"),
                resultSet.getInt("number"));

        preparedStatement.close();
        return flagella;
    }

    @Override
    public Boolean deleteEntityById(int id) throws SQLException {
        String statement = "DELETE FROM flagella WHERE id=?";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        preparedStatement.setInt(1, id);

        Boolean methodSucceeded = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return methodSucceeded;
    }
}
