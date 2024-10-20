import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.UUID;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
//import java.util.ArrayList;

public class project extends JFrame {

    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final JButton signInButton;
    private final JButton signUpButton;
    private final JButton adminLoginButton;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    // private Customer customer;

    public project() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        contentPane.add(cardPanel, BorderLayout.CENTER);

        // Login Page
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(10, 11, 88, 14);
        loginPanel.add(lblUsername);

        usernameField = new JTextField();
        usernameField.setBounds(10, 36, 168, 20);
        loginPanel.add(usernameField);
        usernameField.setColumns(10);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(10, 67, 88, 14);
        loginPanel.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(10, 92, 168, 20);
        loginPanel.add(passwordField);

        signInButton = new JButton("Sign In");
        signInButton.setBounds(10, 123, 89, 23);
        loginPanel.add(signInButton);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(107, 123, 89, 23);
        loginPanel.add(signUpButton);

        adminLoginButton = new JButton("Admin Login");
        adminLoginButton.setBounds(206, 123, 117, 23);
        loginPanel.add(adminLoginButton);

        cardPanel.add(loginPanel, "login");

        // Event Listeners
        signInButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Check if user exists and password matches
            if (checkCustomer(username, password)) {
                // Customer Login Success
                // customer = getCustomerByUsername(username);
                showCustomerView(username);
            } else {
                // Login Failed
                JOptionPane.showMessageDialog(contentPane, "Invalid username or password.");
            }
        });

        signUpButton.addActionListener((ActionEvent e) -> {
            // Show Signup Form
            showSignUpForm();
        });

        adminLoginButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Check if admin exists and password matches
            if (checkAdmin(username, password)) {
                // Admin Login Success
                showAdminView(username);
            } else {
                // Login Failed
                JOptionPane.showMessageDialog(contentPane, "Invalid username or password.");
            }
        });
    }

    private boolean checkCustomer(String username, String password) {
        try {
            try (Scanner scanner = new Scanner(new File("C:\\Users\\efatr\\OneDrive\\Documents\\real\\lib\\customers.txt"))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts[0].equals(username) && parts[1].equals(password)) {
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
        }
        return false;
    }

    private boolean checkAdmin(String username, String password) {
        try {

            try (Scanner scanner = new Scanner(new File("C:\\Users\\efatr\\OneDrive\\Documents\\real\\lib\\admins.txt"))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts[0].equals(username) && parts[1].equals(password)) {
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
        }
        return false;
    }

    // Show Customer View
    private void showCustomerView(String username) {
        // Create a new panel for the Customer View
        JPanel customerPanel = new JPanel(new GridLayout(4, 1));
        customerPanel.add(new JLabel("Welcome, " + username + "!"), BorderLayout.NORTH);

        // Display the available money of the customer

        JLabel availableMoneyLabel = new JLabel("Available Money: $" +
                getInvestedAmount(username));
        customerPanel.add(availableMoneyLabel);

        JButton viewPropertiesButton = new JButton("View Available Properties");
        JButton viewOwnedHousesButton = new JButton("View Owned Houses");
        JButton manageAccountButton = new JButton("Manage Account");
        JButton investButton = new JButton("Deposit");

        customerPanel.add(viewPropertiesButton);
        customerPanel.add(viewOwnedHousesButton);
        customerPanel.add(manageAccountButton);
        customerPanel.add(investButton);

        viewPropertiesButton.addActionListener((ActionEvent e) -> {
            Showproperty();
        });

        viewOwnedHousesButton.addActionListener((ActionEvent e) -> {
            // customerhouseinfo(username);
        });

        manageAccountButton.addActionListener((ActionEvent e) -> {
            ManageAccount(username);
        });

        investButton.addActionListener((ActionEvent e) -> {
            Invest(username);
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener((ActionEvent e) -> {
            cardLayout.show(cardPanel, "login");
        });
        customerPanel.add(backButton, BorderLayout.SOUTH);
        cardPanel.add(customerPanel, "customer");
        cardLayout.show(cardPanel, "customer");
    }

    // Show Signup Form
    private void showSignUpForm() {
        // Create a new panel for the Signup Form
        JPanel signupPanel = new JPanel(new GridLayout(4, 2));
        signupPanel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        signupPanel.add(usernameField);
        signupPanel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        signupPanel.add(passwordField);
        signupPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        signupPanel.add(emailField);
        JButton signupButton = new JButton("Sign Up");
        signupPanel.add(signupButton);

        // Add event listener to the signup button
        signupButton.addActionListener((ActionEvent e) -> {
            // Get user input from the fields
            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();

            // Validate input (e.g., check for empty fields, valid email address)

            // Add customer to file
            try {
                try (FileWriter writer = new FileWriter("C:\\Users\\efatr\\OneDrive\\Documents\\real\\lib\\customers.txt", true)) {
                    writer.write(username + "," + password + "," + email + "\n");
                }
                JOptionPane.showMessageDialog(contentPane, "Succsessfully signup!!");
            } catch (IOException e1) {
            }

            // Close the signup frame
            cardLayout.show(cardPanel, "login");
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener((ActionEvent e) -> {
            cardLayout.show(cardPanel, "login");
        });
        signupPanel.add(backButton);

        cardPanel.add(signupPanel, "signup");
        cardLayout.show(cardPanel, "signup");
    }

    // Show Admin View
    private void showAdminView(String username) {
        // Create a new panel for the Admin View
        JPanel adminPanel = new JPanel(new BorderLayout());
        adminPanel.add(new JLabel("Welcome, Admin!"), BorderLayout.NORTH);

        // Add a button to manage properties
        JButton managePropertiesButton = new JButton("Manage Properties");
        managePropertiesButton.addActionListener((ActionEvent e) -> {
            // Show a separate panel for managing properties
            showPropertyManagementView();
        });
        adminPanel.add(managePropertiesButton, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener((ActionEvent e) -> {
            cardLayout.show(cardPanel, "login");
        });
        adminPanel.add(backButton, BorderLayout.SOUTH);

        cardPanel.add(adminPanel, "admin");
        cardLayout.show(cardPanel, "admin");
    }

    // Show Property Management View
    private void showPropertyManagementView() {
        // Create a new panel for the Property Management View
        String filePath = "C:\\Users\\efatr\\OneDrive\\Documents\\real\\lib\\property.txt";

        JPanel propertyManagementPanel = new JPanel(new GridLayout(4, 1));
        JButton viewpro = new JButton("View Properties");
        JButton addpro = new JButton("Add property");
        JButton deletepro = new JButton("Delete property");
        JButton viewcustom = new JButton("view customers");
        viewpro.addActionListener((ActionEvent e) -> {
            Showproperty();
        });
        addpro.addActionListener((ActionEvent e) -> {
            Addpro();
        });
        deletepro.addActionListener((ActionEvent e) -> {
            delpro(filePath);
        });
        viewcustom.addActionListener((ActionEvent e) -> {
            Viewcustom();
        });
        propertyManagementPanel.add(viewpro);
        propertyManagementPanel.add(viewcustom);
        propertyManagementPanel.add(deletepro);
        propertyManagementPanel.add(addpro);

        JButton backButton = new JButton("Back");
        backButton.addActionListener((ActionEvent e) -> {
            cardLayout.show(cardPanel, "admin");
        });
        propertyManagementPanel.add(backButton);
        cardPanel.add(propertyManagementPanel, "propertyManagement");
        cardLayout.show(cardPanel, "propertyManagement");
    }

    private void Viewcustom() {
        JPanel pro = new JPanel();
        pro.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        pro.add(scrollPane, BorderLayout.CENTER);

        try {
            Files.lines(Paths.get("C:\\Users\\efatr\\OneDrive\\Documents\\real\\lib\\customers.txt"))
                    .forEach(line -> textArea.append(line + "\n"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        JButton backButton = new JButton("Back");
        backButton.addActionListener((ActionEvent e) -> {
            cardLayout.show(cardPanel, "login");
        });
        pro.add(backButton, BorderLayout.SOUTH);
        cardPanel.add(pro, "pro");
        cardLayout.show(cardPanel, "pro");
    }

    private void Addpro() {
        JPanel Add = new JPanel(new GridLayout(4, 1));
        Add.add(new JLabel("new property info:"));
        JTextField addtext = new JTextField();
        Add.add(addtext);
        JButton updateButton = new JButton("Save");
        Add.add(updateButton);
        updateButton.addActionListener((ActionEvent e) -> {
            String textfile = addtext.getText();
            try {
                try (FileWriter writer = new FileWriter("C:\\Users\\efatr\\OneDrive\\Documents\\real\\lib\\property.txt", true)) {
                    writer.write(textfile + "\n");
                }
                JOptionPane.showMessageDialog(contentPane, "Property added Succsessfully!!");
                cardLayout.show(cardPanel, "propertyManagement");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        cardPanel.add(Add, "addpro");
        cardLayout.show(cardPanel, "addpro");
    }

    private void delpro(String filePath) {

        JPanel delpro = new JPanel(new GridLayout(4, 1));
        delpro.add(new JLabel("Enter addres of property:"));
        JTextField keytext = new JTextField();
        delpro.add(keytext);
        JButton deleteButton = new JButton("Delete");
        delpro.add(deleteButton);
        deleteButton.addActionListener((ActionEvent e) -> {
            String keyword = keytext.getText();
            File tempFile = new File("C:\\Users\\efatr\\OneDrive\\Documents\\real\\lib\\tempFile_" + UUID.randomUUID() + ".txt");
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                String currentLine;

                // Read each line from the original file
                while ((currentLine = reader.readLine()) != null) {
                    // If the line does not contain the keyword, write it to the temp file
                    if (!currentLine.contains(keyword)) {
                        writer.write(currentLine);
                        writer.newLine();
                    }
                }
                writer.close();
                reader.close();
                // Delete the original file
                Files.delete(Paths.get(filePath));
                // Rename the temp file to the original file name
                tempFile.renameTo(new File(filePath));
                JOptionPane.showMessageDialog(contentPane, "Property Delete Succsessfully!!");
                cardLayout.show(cardPanel, "propertyManagement");
            } catch (IOException e2) {
            }
        });
        cardPanel.add(delpro, "delpro");
        cardLayout.show(cardPanel, "delpro");
    }

    private void Showproperty() {

        JPanel pro = new JPanel();
        pro.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        pro.add(scrollPane, BorderLayout.CENTER);

        try {
            Files.lines(Paths.get("C:\\Users\\efatr\\OneDrive\\Documents\\real\\lib\\property.txt"))
                    .forEach(line -> textArea.append(line + "\n"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        JButton backButton = new JButton("Back");
        backButton.addActionListener((ActionEvent e) -> {
            cardLayout.show(cardPanel, "login");
        });
        pro.add(backButton, BorderLayout.SOUTH);

        cardPanel.add(pro, "pro");

        cardLayout.show(cardPanel, "pro");

    }

    private void Invest(String username) {
        // Get the customer object from your data structure
        // Customer customer = getCustomerByUsername(username);

        // Allow the customer to invest money with a certain interest rate
        JPanel investPanel = new JPanel(new GridLayout(4, 1));
        investPanel.add(new JLabel("Investment Amount:"));
        JTextField investAmountField = new JTextField();
        investPanel.add(investAmountField);

        JButton investButton = new JButton("Invest");
        investButton.addActionListener((ActionEvent e) -> {
            double investAmount = Double.parseDouble(investAmountField.getText());
            invest(username, investAmount);
            JOptionPane.showMessageDialog(contentPane, "deposit succsesfully");
        });
        investPanel.add(investButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener((ActionEvent e) -> {
            cardLayout.show(cardPanel, "customer");
        });
        investPanel.add(backButton);

        cardPanel.add(investPanel, "invest");
        cardLayout.show(cardPanel, "invest");
    }

    private void ManageAccount(String username) {
        // Get the customer object from your data structure
        // Customer customer = getCustomerByUsername("username"); // Replace with actual
        // username retrieval

        // Allow the customer to manage their account
        JPanel manageAccountPanel = new JPanel(new GridLayout(4, 1));
        manageAccountPanel.add(new JLabel("Current Password:"));
        JPasswordField currentPasswordField = new JPasswordField();
        manageAccountPanel.add(currentPasswordField);

        manageAccountPanel.add(new JLabel("New Password:"));
        JPasswordField newPasswordField = new JPasswordField();
        manageAccountPanel.add(newPasswordField);

        JButton updateButton = new JButton("Update Password");
        updateButton.addActionListener((ActionEvent e) -> {
            String currentPassword = currentPasswordField.getText();
            String newPassword = newPasswordField.getText();
            try (BufferedReader reader = new BufferedReader(
                    new FileReader("C:\\Users\\efatr\\OneDrive\\Documents\\real\\lib\\customers.txt"));
                    BufferedWriter writer = new BufferedWriter(
                            new FileWriter("C:\\Users\\efatr\\OneDrive\\Documents\\real\\lib\\temp1.txt"))) {

                String line;
                boolean found = false;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(username) && parts[1].equals(currentPassword)) {
                        // Update the password and write the updated line to the temporary file
                        writer.write(parts[0] + "," + newPassword + "," + parts[2] + "\n");
                        found = true;
                    } else {

                        writer.write(line + "\n");
                    }
                }

                if (found) {

                    reader.close();
                    writer.close();
                    java.io.File oldFile = new java.io.File("C:\\Users\\efatr\\OneDrive\\Documents\\real\\lib\\customers.txt");
                    java.io.File newFile = new java.io.File("C:\\Users\\efatr\\OneDrive\\Documents\\real\\lib\\temp1.txt");
                    if (newFile.renameTo(oldFile)) {
                        JOptionPane.showMessageDialog(contentPane, "Password updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(contentPane, "file system is not function for now!!");
                    }
                } else {
                    JOptionPane.showMessageDialog(contentPane, "current password is incorect");
                }

            } catch (IOException e4) {
                JOptionPane.showMessageDialog(contentPane, "file must be fixed");
            }
        });
        manageAccountPanel.add(updateButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener((ActionEvent e) -> {
            cardLayout.show(cardPanel, "customer");
        });
        manageAccountPanel.add(backButton);

        cardPanel.add(manageAccountPanel, "manageAccount");
        cardLayout.show(cardPanel, "manageAccount");
    }

    public void invest(String username, double amount) {
        String filePath = "C:\\Users\\efatr\\OneDrive\\Documents\\real\\lib\\investment.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean foundUsername = false;
            StringBuilder updatedFileContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2 && parts[0].equals(username)) {
                    // Update the amount for existing username
                    updatedFileContent.append(username).append(" ").append(Double.parseDouble(parts[1]) + amount)
                            .append("\n");
                    foundUsername = true;
                } else {
                    // Append existing lines as is
                    updatedFileContent.append(line).append("\n");
                }
            }

            // If username wasn't found, add new investment
            if (!foundUsername) {
                updatedFileContent.append(username).append(" ").append(amount).append("\n");
            }

            // Write updated content back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(updatedFileContent.toString());
            }
        } catch (IOException e) {
        }
    }

    public double getInvestedAmount(String username) {
        // Parameter type is String
        String FILE_PATH = "C:\\Users\\efatr\\OneDrive\\Documents\\real\\lib\\investment.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2 && parts[0].equals(username)) {
                    return Double.parseDouble(parts[1]);
                }
            }
        } catch (IOException e) {
        }
        return 00.00;
    }

    public static void main(String[] args) {
        project frame = new project();
        frame.setVisible(true);
    }
}
/*
 * // Customer class for demonstration purposes
 * class Customer {
 * private String username;
 * private String password;
 * private String email;
 * private double availableMoney;
 * private String[] houses;
 * 
 * public Customer(String username, String password, String email) {
 * this.username = username;
 * this.password = password;
 * this.email = email;
 * }
 * 
 * public String getUsername() {
 * return username;
 * }
 * 
 * public String getPassword() {
 * return password;
 * }
 * 
 * public String getEmail() {
 * return email;
 * }
 * 
 * public void setAvailableMoney(double availableMoney) {
 * this.availableMoney = availableMoney;
 * }
 * 
 * public String[] getHouses() {
 * return houses;
 * }
 * 
 * public void setHouses(String[] houses) {
 * this.houses = houses;
 * }
 * 
 * public void deposit(double amount) {
 * // Implement deposit logic
 * }
 * 
 * public void setPassword(String password) {
 * this.password = password;
 * }
 * }
 */