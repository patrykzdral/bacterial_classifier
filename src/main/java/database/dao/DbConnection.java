package database.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    private static String ip = "185.24.216.248:3306";
    private static String DB_URL = "jdbc:mysql://"+ip+"/"+database+"+?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";


    private Connection connection;

    /**
     * <p>Constructor for DbConnection.</p>
     *
     * @throws java.sql.SQLException if any.
     */
    public DbConnection() throws SQLException, ClassNotFoundException {
        database= "lab7_bacteria_classification";
        ip = "185.24.216.248:3306";
        connection = DriverManager.getConnection(DB_URL, user, password);
        connection.setAutoCommit(true);
    }
    public DbConnection(String user, String password, String ip, String database ) throws SQLException, ClassNotFoundException {
        DbConnection.database =database;
        DbConnection.ip =ip;
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

    public static String getDatabase() {
        return database;
    }

    public static void setDatabase(String database) {
        DbConnection.database = database;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        DbConnection.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DbConnection.password = password;
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        DbConnection.ip = ip;
    }
}

