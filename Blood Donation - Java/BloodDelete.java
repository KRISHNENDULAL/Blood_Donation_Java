import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BloodDelete extends Frame implements ActionListener {
    // UI Components
    Choice chNames;
    TextField tfPassword;
    Label lblMessage;
    Button btnDelete, btnBack;
    Frame mainPage;

    // Database credentials
    final String DB_URL = "jdbc:mysql://localhost:3306/blooddonate";
    final String USER = "root";
    final String PASS = "200211";  // Change this to your MySQL password

    public BloodDelete(Frame mainPage) {
        this.mainPage = mainPage;

        // Initialize components
        chNames = new Choice();
        tfPassword = new TextField();
        lblMessage = new Label();
        btnDelete = new Button("Delete");
        btnBack = new Button("Back");

        // Set up layout
        setLayout(new GridLayout(5, 1));

        // Add components to the frame
        add(new Label("Select Name to Delete:"));
        add(chNames);
        add(new Label("Enter Password:"));
        add(tfPassword);
        add(btnDelete);
        add(lblMessage);
        add(btnBack);

        // Load names into choice box
        loadNames();

        // Event Listeners
        btnDelete.addActionListener(this);
        btnBack.addActionListener(this);

        // Frame settings
        setSize(400, 200);
        setTitle("Delete Details");
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDelete) {
            delete();
            loadNames(); // Refresh names list after deletion
        } else if (e.getSource() == btnBack) {
            this.dispose();
            mainPage.setVisible(true);
        }
    }

    private void loadNames() {
        chNames.removeAll();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT name FROM bloodreg";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                chNames.add(rs.getString("name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            lblMessage.setText("Error loading names: " + ex.getMessage());
        }
    }

    private void delete() {
        String name = chNames.getSelectedItem();
        String password = tfPassword.getText();
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "DELETE FROM bloodreg WHERE name = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, password);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                lblMessage.setText("Deletion successful!");
            } else {
                lblMessage.setText("Incorrect password or name not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            lblMessage.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new BloodDelete(null);
    }
}
