import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BloodUpdate extends Frame implements ActionListener {
    // UI Components
    Choice chNames, chBloodGroup;
    TextField tfName, tfPassword, tfPhone, tfPlace;
    Label lblMessage;
    Button btnUpdate, btnBack;
    Frame mainPage;

    // Database credentials
    final String DB_URL = "jdbc:mysql://localhost:3306/blooddonate";
    final String USER = "root";
    final String PASS = "200211";  // Change this to your MySQL password

    public BloodUpdate(Frame mainPage) {
        this.mainPage = mainPage;

        // Initialize components
        chNames = new Choice();
        tfName = new TextField();
        tfPassword = new TextField();
        tfPhone = new TextField();
        tfPlace = new TextField();
        chBloodGroup = new Choice();
        lblMessage = new Label();
        btnUpdate = new Button("Update");
        btnBack = new Button("Back");

        // Add blood group choices
        String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        for (String bg : bloodGroups) {
            chBloodGroup.add(bg);
        }

        // Set up layout
        setLayout(new GridLayout(8, 2));

        // Add components to the frame
        add(new Label("Select Name to Update:"));
        add(chNames);
        add(new Label("Name:"));
        add(tfName);
        add(new Label("Password:"));
        add(tfPassword);
        add(new Label("Blood Group:"));
        add(chBloodGroup);
        add(new Label("Phone Number:"));
        add(tfPhone);
        add(new Label("Place:"));
        add(tfPlace);
        add(btnUpdate);
        add(lblMessage);
        add(btnBack);

        // Load names into choice box
        loadNames();

        // Event Listeners
        chNames.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                loadDetails(chNames.getSelectedItem());
            }
        });
        btnUpdate.addActionListener(this);
        btnBack.addActionListener(this);

        // Frame settings
        setSize(400, 300);
        setTitle("Update Details");
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnUpdate) {
            updateDetails();
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

    private void loadDetails(String name) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM bloodreg WHERE name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                tfName.setText(rs.getString("name"));
                tfPassword.setText(rs.getString("password"));
                chBloodGroup.select(rs.getString("blood_group"));
                tfPhone.setText(rs.getString("phone_number"));
                tfPlace.setText(rs.getString("place"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            lblMessage.setText("Error loading details: " + ex.getMessage());
        }
    }

    private void updateDetails() {
        String name = tfName.getText();
        String password = tfPassword.getText();
        String bloodGroup = chBloodGroup.getSelectedItem();
        String phoneNumber = tfPhone.getText();
        String place = tfPlace.getText();
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "UPDATE bloodreg SET password = ?, blood_group = ?, phone_number = ?, place = ? WHERE name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, password);
            pstmt.setString(2, bloodGroup);
            pstmt.setString(3, phoneNumber);
            pstmt.setString(4, place);
            pstmt.setString(5, name);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                lblMessage.setText("Update successful!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            lblMessage.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new BloodUpdate(null);
    }
}
