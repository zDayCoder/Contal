package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import services.Databases;

public class Contact {

    private long rowId;
    private String name;
    private String description;
    private String telephone;
    private String email;
    private String address;

    public static String getCreateStatement() {
        return "CREATE TABLE IF NOT EXISTS contacts("
                + "name VARCHAR(200) NOT NULL,"
                + "description VARCHAR(500) DEFAULT NULL,"
                + "telephone VARCHAR(20) NOT NULL,"
                + "email VARCHAR(250) DEFAULT NULL,"
                + "address VARCHAR(200) DEFAULT NULL,"
                + "user_id VARCHAR(250),"
                + "FOREIGN KEY (user_id) REFERENCES users(email)"
                + ")";

    }

    public static List<Contact> getAllContacts() throws Exception {
        List<Contact> contacts = new ArrayList<>();
        try {
            Connection con = Databases.getConnection();
            String query = "SELECT * FROM contacts";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Contact contact = new Contact();
                contact.setName(rs.getString("name"));
                contact.setDescription(rs.getString("description"));
                contact.setTelephone(rs.getString("telephone"));
                contact.setEmail(rs.getString("email"));
                contact.setAddress(rs.getString("address"));
                contacts.add(contact);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
        }

        return contacts;
    }

    public static Contact findContactByEmail(String email) throws Exception {
        try {
            Connection con = Databases.getConnection();
            String query = "SELECT * FROM contacts WHERE email = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Contact contact = new Contact();
                contact.setName(rs.getString("name"));
                contact.setDescription(rs.getString("description"));
                contact.setTelephone(rs.getString("telephone"));
                contact.setEmail(rs.getString("email"));
                contact.setAddress(rs.getString("address"));
                return contact;
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
        }

        return null;
    }

    public static List<Contact> searchContactsByPhone(String phone) throws Exception {
        List<Contact> contacts = new ArrayList<>();

        try {
            Connection con = Databases.getConnection();
            String query = "SELECT * FROM contacts WHERE telephone LIKE ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, "%" + phone + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Contact contact = new Contact();
                contact.setName(rs.getString("name"));
                contact.setDescription(rs.getString("description"));
                contact.setTelephone(rs.getString("telephone"));
                contact.setEmail(rs.getString("email"));
                contact.setAddress(rs.getString("address"));
                contacts.add(contact);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
        }

        return contacts;
    }

    public static Contact getContactByTelephone(String telephone) throws Exception {
        Contact contact = null;

        try {
            Connection con = Databases.getConnection();
            String sql = "SELECT * FROM contacts WHERE telephone=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, telephone);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                long rowId = rs.getLong("rowid");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String email = rs.getString("email");
                String address = rs.getString("address");

                contact = new Contact(rowId, name, description, telephone, email, address);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
        }

        return contact;
    }

    public static boolean contactMailExists(String email, String userMail) throws Exception {
        Connection con = Databases.getConnection();
        String checkQuery = "SELECT COUNT(*) AS count FROM contacts WHERE email = ? AND user_id = ?";

        try (PreparedStatement stmt = con.prepareStatement(checkQuery)) {
            stmt.setString(1, email);
            stmt.setString(2, userMail);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0;
                }
                rs.close();
            }
            stmt.close();
        } catch (SQLException e) {
            // Tratar exceção
        } finally {
            con.close();
        }
        con.close();

        return false;
    }

    public static boolean contactTelephoneExists(String telephone, String userMail) throws Exception {
        Connection con = Databases.getConnection();
        String checkQuery = "SELECT COUNT(*) AS count FROM contacts WHERE telephone = ? AND user_id = ?";

        try (PreparedStatement stmt = con.prepareStatement(checkQuery)) {
            stmt.setString(1, telephone);
            stmt.setString(2, userMail);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0;
                }
                rs.close();
            }
            stmt.close();
        } catch (SQLException e) {
            // Tratar exceção
        } finally {
            con.close();
        }
        con.close();

        return false;
    }

    public static void insertContact(String name, String description, String telephone, String email, String address, String userMail) throws Exception {
        try {
            Connection con = Databases.getConnection();

            // Inserir o novo contato
            String insertQuery = "INSERT INTO contacts (name, description, telephone, email, address, user_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(insertQuery);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, telephone);
            stmt.setString(4, email);
            stmt.setString(5, address);
            stmt.setString(6, userMail);
            stmt.execute();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            // Tratar exceção
        }
    }

    public static boolean isTelephoneExists(String telephone) throws Exception {
        int count;
        try (Connection con = Databases.getConnection()) {
            String sql = "SELECT COUNT(*) FROM contacts WHERE telephone=?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, telephone);
                try (ResultSet rs = stmt.executeQuery()) {
                    rs.next();
                    count = rs.getInt(1);
                }
                stmt.close();
            }
            con.close();
        }
        return count > 0;
    }

    public static boolean isEmailExists(String email) throws Exception {
        int count;
        try (Connection con = Databases.getConnection()) {
            String sql = "SELECT COUNT(*) FROM contacts WHERE email=?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    rs.next();
                    count = rs.getInt(1);
                }
                stmt.close();
            }
            con.close();
        }
        return count > 0;
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
     * public static void changePassword(String email, String password) throws
     * Exception { Connection con = Databases.getConnection(); String sql =
     * "UPDATE users SET passwordHash = ? WHERE email = ?"; PreparedStatement
     * stmt = con.prepareStatement(sql); stmt.setString(1,
     * Databases.getMd5Hash(password)); stmt.setString(2, email);
     * stmt.execute(); stmt.close(); con.close(); }*
     */
    public Contact(long rowId, String name, String description, String telephone, String email, String address) {
        this.rowId = rowId;
        this.name = name;
        this.description = description;
        this.telephone = telephone;
        this.email = email;
        this.address = address;
    }

    public Contact(long rowId, String name, String email, String passwordHash) {
        this.rowId = rowId;
        this.name = name;
        this.email = email;
    }

    public Contact() {
    }

    public Contact(String name, String email) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
