import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends Frame implements ActionListener {
    // UI Components
    TextField tfName, tfPassword;
    Label lblMessage;
    Button btnLogin, btnBack;

    // Database credentials
    final String DB_URL = "jdbc:mysql://localhost:3306/blooddonate";
    final String USER = "root";
    final String PASS = "200211";  // Change this to your MySQL password

    public LoginPage() {
        // Initialize components
        tfName = new TextField();
        tfPassword = new TextField();
        lblMessage = new Label();
        btnLogin = new Button("Login");
        btnBack = new Button("Back");

        // Set up layout
        setLayout(new GridLayout(4, 2));

        // Add components to the frame
        add(new Label("Name:"));
        add(tfName);
        add(new Label("Password:"));
        add(tfPassword);
        add(btnLogin);
        add(lblMessage);
        add(btnBack);

        // Event Listeners
        btnLogin.addActionListener(this);
        btnBack.addActionListener(this);

        // Frame settings
        setSize(300, 200);
        setTitle("Login");
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            if (login()) {
                new MainPage(); // Open the main page
                this.dispose(); // Close the login page
            }
        } else if (e.getSource() == btnBack) {
            this.dispose();
        }
    }

    private boolean login() {
        String name = tfName.getText();
        String password = tfPassword.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM admin WHERE name = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                lblMessage.setText("Login successful!");
                return true;
            } else {
                lblMessage.setText("Login failed. Please check your credentials.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            lblMessage.setText("Error: " + ex.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
