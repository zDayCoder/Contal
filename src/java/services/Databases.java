package services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import javax.servlet.ServletContextListener;

public class Databases implements ServletContextListener {

    public static final String CLASS_NAME = "org.sqlite.JDBC";
    public static final String URL = "jdbc:sqlite:C:/Users/ryana/Downloads/Fatec-POO/Contal - Contato Digital/src/sqlite/contal.db";

    public static String getMd5Hash(String text) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(text.getBytes(), 0, text.length());
        return new BigInteger(1, m.digest()).toString();
    }

    public static Connection getConnection() throws Exception {
        Class.forName(CLASS_NAME);

        Connection connection = DriverManager.getConnection(URL);

        // Define a codificação UTF-8 para a conexão
        connection.createStatement().execute("PRAGMA encoding = 'UTF-8';");
        connection.createStatement().execute("PRAGMA foreign_keys = ON;");

        return connection;

    }

}
