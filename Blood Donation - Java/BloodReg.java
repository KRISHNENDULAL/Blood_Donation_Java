import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BloodReg extends Frame implements ActionListener {
    // UI Components
    TextField tfName, tfPassword, tfPhone, tfPlace;
    Choice chBloodGroup;
    Label lblMessage;
    Button btnSubmit, btnBack;

    // Database credentials
    final String DB_URL = "jdbc:mysql://localhost:3306/blooddonate";
    final String USER = "root";
    final String PASS = "200211";  // Change this to your MySQL password

    public BloodReg() {
        // Initialize components
        tfName = new TextField();
        tfPassword = new TextField();
        tfPhone = new TextField();
        tfPlace = new TextField();
        chBloodGroup = new Choice();
        lblMessage = new Label();
        btnSubmit = new Button("Submit");
        btnBack = new Button("Back");

        // Add blood group choices
        String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        for (String bg : bloodGroups) {
            chBloodGroup.add(bg);
        }

        // Set up layout
        setLayout(new GridLayout(7, 2));

        // Add components to the frame
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
        add(btnSubmit);
        add(lblMessage);
        add(btnBack);

        // Event Listeners
        btnSubmit.addActionListener(this);
        btnBack.addActionListener(this);

        // Frame settings
        setSize(400, 250);
        setTitle("Register Details");
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit) {
            register();
        } else if (e.getSource() == btnBack) {
            this.dispose();
        }
    }

    private void register() {
        String name = tfName.getText();
        String password = tfPassword.getText();
        String bloodGroup = chBloodGroup.getSelectedItem();
        String phoneNumber = tfPhone.getText();
        String place = tfPlace.getText();

        // Validation to ensure all fields are filled
        if (name.isEmpty() || password.isEmpty() || bloodGroup.isEmpty() || phoneNumber.isEmpty() || place.isEmpty()) {
            lblMessage.setText("Enter all the details to submit");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "INSERT INTO bloodreg (name, password, blood_group, phone_number, place) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.setString(3, bloodGroup);
            pstmt.setString(4, phoneNumber);
            pstmt.setString(5, place);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                lblMessage.setText("Registration successful!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            lblMessage.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new BloodReg();
    }
}
