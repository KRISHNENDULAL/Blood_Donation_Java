import java.awt.*;
import java.awt.event.*;

public class MainPage extends Frame implements ActionListener {
    // UI Components
    Button btnUpdate, btnDelete, btnView, btnBack;

    public MainPage() {
        // Initialize components
        btnUpdate = new Button("Update Details");
        btnDelete = new Button("Delete Details");
        btnView = new Button("View Details");
        btnBack = new Button("Back");

        // Set up layout
        setLayout(new GridLayout(4, 1));

        // Add components to the frame
        add(btnUpdate);
        add(btnDelete);
        add(btnView);
        add(btnBack);

        // Event Listeners
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnView.addActionListener(this);
        btnBack.addActionListener(this);

        // Frame settings
        setTitle("Main Page");
        setSize(300, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnUpdate) {
            new BloodUpdate(this); // Pass the current instance of MainPage
            this.setVisible(false); // Hide the current MainPage
        } else if (e.getSource() == btnDelete) {
            new BloodDelete(this); // Pass the current instance of MainPage
            this.setVisible(false); // Hide the current MainPage
        } else if (e.getSource() == btnView) {
            new ViewPage(this); // Pass the current instance of MainPage
            this.setVisible(false); // Hide the current MainPage
        } else if (e.getSource() == btnBack) {
            new FrontPage(); // Create and show the FrontPage
            this.dispose(); // Close the current MainPage
        }
    }

    public static void main(String[] args) {
        new MainPage();
    }
}
