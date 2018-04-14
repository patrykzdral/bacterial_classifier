package database.dao;

import java.sql.*;

/**
 * <p>DbConnection class.</p>
 *
 * @author mrfarinq
 * @version $Id: $Id
 */
public class DbConnection {
    private static String database = "lab7_bacteria_classification";
    private static String  user = "Patrykz13";
    private static String password = "kochamjave";
    private static String DB_URL = "jdbc:mysql://185.24.216.248:3306/lab7_bacteria_classification?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";


    private Connection connection;

    /**
     * <p>Constructor for DbConnection.</p>
     *
     * @throws java.sql.SQLException if any.
     */
    public DbConnection() throws SQLException, ClassNotFoundException {
        connection = DriverManager.getConnection(DB_URL, user, password);
        connection.setAutoCommit(true);
    }

    /**
     * <p>Getter for the field <code>connection</code>.</p>
     *
     * @return a {@link java.sql.Connection} object.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * <p>closeConnection.</p>
     *
     * @throws java.sql.SQLException if any.
     */
    public void closeConnection() throws SQLException {
        if (!connection.isClosed())
            connection.close();
    }
    public static void main(String[] args) {
        DbConnection dbConnection;
        try {
            /*
            Group group;
            dbConnection = new DbConnection();
            GroupDAO userDAO = new GroupDAO(dbConnection);
            user = userDAO.getUserByLogin("zenek");
            user.setFirstName("Renata");
            userDAO.updateEntity(user);
            System.out.println(userDAO.getEntities());
            dbConnection.closeConnection();
            */

            dbConnection = new DbConnection();
            /*
            group = groupDAO.getEntityById(5);
            System.out.println(group.getId());
            group.addRole(3);
            group.addRole(2);
            group.addRole(1);

            groupDAO.updateEntity(group);
            System.out.println(group);
            */
            //groupDAO.deleteEntityById(5);
            dbConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

