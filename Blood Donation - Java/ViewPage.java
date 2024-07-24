import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class ViewPage extends Frame implements ActionListener {
    // UI Components
    Label lblBloodGroup;
    Choice chBloodGroup;
    TextArea taResult;
    Button btnBack;

    // Database credentials
    final String DB_URL = "jdbc:mysql://localhost:3306/blooddonate";
    final String USER = "root";
    final String PASS = "200211";  // Change this to your MySQL password

    public ViewPage(Frame parent) {
        // Initialize components
        lblBloodGroup = new Label("Select Blood Group:");
        chBloodGroup = new Choice();
        chBloodGroup.add("A+");
        chBloodGroup.add("A-");
        chBloodGroup.add("B+");
        chBloodGroup.add("B-");
        chBloodGroup.add("AB+");
        chBloodGroup.add("AB-");
        chBloodGroup.add("O+");
        chBloodGroup.add("O-");
        chBloodGroup.add("All");

        taResult = new TextArea();
        btnBack = new Button("Back");

        // Set up layout
        setLayout(new BorderLayout());

        // Add components to the frame
        Panel topPanel = new Panel(new FlowLayout());
        topPanel.add(lblBloodGroup);
        topPanel.add(chBloodGroup);
        add(topPanel, BorderLayout.NORTH);
        add(taResult, BorderLayout.CENTER);
        add(btnBack, BorderLayout.SOUTH);

        // Event Listener
        chBloodGroup.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedBloodGroup = chBloodGroup.getSelectedItem();
                    displayBloodDonors(selectedBloodGroup);
                }
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.setVisible(true);  // Show the parent frame (MainPage)
                dispose();  // Close the current frame (ViewPage)
            }
        });

        // Frame settings
        setTitle("View Page");
        setSize(400, 300);
        setVisible(true);
    }

    private void displayBloodDonors(String bloodGroup) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql;
            if (bloodGroup.equals("All")) {
                sql = "SELECT name, phone_number, place, blood_group FROM bloodreg";
            } else {
                sql = "SELECT name, phone_number, place, blood_group FROM bloodreg WHERE blood_group = ?";
            }
            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (!bloodGroup.equals("All")) {
                pstmt.setString(1, bloodGroup);
            }
            ResultSet rs = pstmt.executeQuery();

            ArrayList<String> donors = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("name");
                String phoneNumber = rs.getString("phone_number");
                String place = rs.getString("place");
                String bloodGroupResult = rs.getString("blood_group");
                donors.add("Name: " + name + ", Blood Group: " + bloodGroupResult + ", Phone Number: " + phoneNumber + ", Place: " + place);
            }

            if (donors.isEmpty()) {
                taResult.setText("\nNo donors found for selected blood group.");
            } else {
                StringBuilder result = new StringBuilder();
                result.append("\nDonors").append(":\n\n");
                for (String donor : donors) {
                    result.append(donor).append("\n");
                }
                taResult.setText(result.toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            taResult.setText("Error: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Not used in this implementation
    }

    public static void main(String[] args) {
        new ViewPage(new MainPage());
    }
}
