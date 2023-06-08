package model;

import java.sql.*;
import services.Databases;

public class User {

    private long rowId;
    private String name;
    private String email;
    private String passwordHash;

    public static String getCreateStatement() {
        return "CREATE TABLE IF NOT EXISTS users("
                + "email VARCHAR(250) UNIQUE NOT NULL PRIMARY KEY,"
                + "name VARCHAR(200) NOT NULL,"
                + "passwordHash VARCHAR(255) NOT NULL"
                + ")";
    }

    public static boolean checkEmailExists(String email) throws Exception {
        boolean emailExists;
        try (Connection con = Databases.getConnection(); PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) FROM users WHERE email = ?")) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                emailExists = rs.getInt(1) > 0;
                rs.close();
            }
            stmt.close();
            con.close();
        }
        return emailExists;

    }

    public static User findUserByEmail(String email) throws Exception {
        try (Connection con = Databases.getConnection(); PreparedStatement stmt = con.prepareStatement("SELECT name FROM users WHERE email = ?")) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    //String passwordHash = rs.getString("passwordHash");
                    User user = new User(name, email);
                    rs.close();
                    stmt.close();
                    con.close();
                    return user;
                }
            }
        }
        return null;
    }

    public static boolean verifyUser(String email, String password) throws Exception {
        try (Connection con = Databases.getConnection(); PreparedStatement stmt = con.prepareStatement("SELECT * FROM users WHERE email = ?")) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("passwordHash");
                    String hashedPassword = Databases.getMd5Hash(password); // Aplicar o hash MD5 à senha informada pelo usuário
                    boolean passwordMatch = hashedPassword.equals(storedPassword);
                    rs.close();
                    stmt.close();
                    con.close();
                    return passwordMatch;
                }
            }
        }
        return false;
    }

    public static User getUser(String email, String password) throws Exception {
        User user = null;
        try (Connection con = (Connection) Databases.getConnection()) {
            String sql = "SELECT rowid, * from users WHERE email=? AND passwordHash=?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, email);
                stmt.setString(2, Databases.getMd5Hash(password));
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        long rowId = rs.getLong("rowid");
                        String name = rs.getString("name");
                        String email2 = rs.getString("email");
                        String passwordHash = rs.getString("passwordHash");
                        user = new User(rowId, name, email2, passwordHash);
                    }rs.close();
                    stmt.close();
                    con.close();
                }
            }
        }
        return user;
    }

    public static void insertUser(String name, String email, String passwordHash) throws Exception {
        try (Connection con = (Connection) Databases.getConnection()) {
            String sql = "INSERT INTO users(name, email, passwordHash) "
                    + "VALUES(?,?,?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, Databases.getMd5Hash(passwordHash));
                stmt.execute();

                stmt.close();
                con.close();
            }
        }
    }

    /**
     * public static void updateUser(String login, String name, String role,
     * String password) throws Exception{ Connection con =
     * AppListener.getConnection(); String sql = "UPDATE users SET name=?,
     * role=?, password_hash=? WHERE login=?"; PreparedStatement stmt =
     * con.prepareStatement(sql); stmt.setString(1, name); stmt.setString(2,
     * role); stmt.setString(3, AppListener.getMd5Hash(password));
     * stmt.setString(4, login); stmt.execute(); stmt.close(); con.close(); }
     *
     */
    public static void changePassword(String email, String password) throws Exception {
        try (Connection con = Databases.getConnection()) {
            String sql = "UPDATE users SET passwordHash = ? WHERE email = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, Databases.getMd5Hash(password));
                stmt.setString(2, email);
                stmt.execute();

                stmt.close();
                con.close();
            }
        }
    }

    public User(long rowId, String name, String email, String passwordHash) {
        this.rowId = rowId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

}
