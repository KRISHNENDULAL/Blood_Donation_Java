import java.awt.*;
import java.awt.event.*;

public class FrontPage extends Frame implements ActionListener {
    Button btnRegister, btnLogin;

    public FrontPage() {
        // Initialize components
        btnRegister = new Button("Donor Register");
        btnLogin = new Button("Admin Login");

        // Set up layout
        setLayout(new GridLayout(2, 1));

        // Add components to the frame
        add(btnRegister);
        add(btnLogin);

        // Event Listeners
        btnRegister.addActionListener(this);
        btnLogin.addActionListener(this);

        // Frame settings
        setSize(300, 200);
        setTitle("Blood Donation System");
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            // Open the registration page
            new BloodReg();
        } else if (e.getSource() == btnLogin) {
            // Open the login page
            new LoginPage();
        }
    }

    public static void main(String[] args) {
        new FrontPage();
    }
}
