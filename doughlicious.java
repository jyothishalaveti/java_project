import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.table.*;
class GetStarted extends JFrame {
 public GetStarted() {
 setTitle("DOUGH LICIOUS DASHBOARD!");
 ImageIcon backgroundImage = new ImageIcon("pass.jpg");
 JLabel background = new JLabel(backgroundImage);
 setContentPane(background);
 JButton b = new JButton("Get Started");
 int buttonWidth = 100;
 int buttonHeight = 30;
 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
 int centerX = (screenSize.width - buttonWidth) / 2;
 int centerY = (screenSize.height - buttonHeight) / 2;
 b.setBounds(centerX, centerY, buttonWidth, buttonHeight);
 add(b);
 pack();
 setLocationRelativeTo(null);
 setVisible(true);
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 b.addActionListener(new ActionListener() {
 public void actionPerformed(ActionEvent e) {
 SwingUtilities.invokeLater(new Runnable() {
 public void run() {
 dispose();
 new Menu();
 }
 });
 }
 });
 }
 public static void main(String args[]) {
 SwingUtilities.invokeLater(new Runnable() {
 public void run() {
 new GetStarted();
 }
 });
 }
}
class Menu extends JFrame {
 private static final String DB_URL = "jdbc:mysql://localhost:3306/dough";
 private static final String DB_USERNAME = "root";
 private static final String DB_PASSWORD = "bhoomika";
 private double totalOrderSum;
 private java.util.List<JButton> addToOrderButtons; // Store the "Add to 
Order" buttons
 public Menu() {
 setTitle("Image Display");
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setLayout(new BorderLayout());
 JPanel imagePanel = new JPanel(new GridLayout(4, 5, 10, 10)); // 4 
rows, 5 columns grid layout
 int squareSize = 200; // Size of the square for each image
 addToOrderButtons = new ArrayList<>(); // Initialize the list of "Add to 
Order" buttons
 try {
 // Establish a database connection
 Connection connection = DriverManager.getConnection(DB_URL, 
DB_USERNAME, DB_PASSWORD);
 Statement statement = connection.createStatement();
 // Retrieve cake details from the database
 String query = "SELECT * FROM licious";
 ResultSet resultSet = statement.executeQuery(query);
 // Load image files and cake details
 int i = 0;
 while (resultSet.next() && i < 20) {
 String imagePath = "image" + (i + 1) + ".jpg";
 String cakeName = resultSet.getString("flavour");
 double cost = resultSet.getDouble("price");
 ImageIcon imageIcon = new ImageIcon(imagePath);
 Image image = imageIcon.getImage();
 Image scaledImage = image.getScaledInstance(squareSize, 
squareSize, Image.SCALE_SMOOTH); // Scale the image to square size
 ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
 JLabel imageLabel = new JLabel(scaledImageIcon);
 JButton l1 = new JButton(cakeName);
 JButton l2 = new JButton("Cost: " + cost + "/-");
 JButton addToOrderButton = new JButton("Add to Order");
 addToOrderButtons.add(addToOrderButton); // Add the button to 
the list
 JPanel imagePanelWithButtons = new JPanel(new 
BorderLayout());
 JPanel centerPanel = new JPanel(new GridBagLayout());
 centerPanel.add(imageLabel);
 imagePanelWithButtons.add(centerPanel, BorderLayout.CENTER);
 JPanel buttonPanel = new JPanel(new 
FlowLayout(FlowLayout.CENTER));
 buttonPanel.add(l2);
 buttonPanel.add(addToOrderButton);
 imagePanelWithButtons.add(l1, BorderLayout.NORTH);
 imagePanelWithButtons.add(buttonPanel, BorderLayout.SOUTH);
 imagePanel.add(imagePanelWithButtons);
 // Calculate subtotal when addToOrderButton is clicked
 addToOrderButton.addActionListener(new ActionListener() {
 public void actionPerformed(ActionEvent e) {
 double subtotal = cost;
 // System.out.println(cakeName + ":" + subtotal);
 totalOrderSum += subtotal;
 addToOrderButton.setEnabled(false); // Disable the button
 }
 });
 i++;
 }
 resultSet.close();
 statement.close();
 connection.close();
 } catch (Exception e) {
 e.printStackTrace();
 }
 JScrollPane scrollPane = new JScrollPane(imagePanel);
 add(scrollPane, BorderLayout.CENTER);
 JButton placeOrderButton = new JButton("PLACE ORDER");
 placeOrderButton.addActionListener(new ActionListener() {
 public void actionPerformed(ActionEvent e) {
 SwingUtilities.invokeLater(new Runnable() {
 public void run() {
 dispose();
 new TextFieldExample(totalOrderSum);
 }
 });
 }
 });
 add(placeOrderButton, BorderLayout.SOUTH);
 setSize(800,630);
 setLocationRelativeTo(null);
 setVisible(true);
 }
 public static void main(String args[]) {
 SwingUtilities.invokeLater(new Runnable() {
 public void run() {
 new Menu();
 }
 });
 }
}
class TextFieldExample {
 private JFrame frame;
 private JLabel phoneLabel, nameLabel, locationLabel;
 private JTextField phoneTextField, nameTextField;
 private JComboBox<String> locationComboBox;
 private static final String DB_URL = "jdbc:mysql://localhost:3306/dough";
 private static final String DB_USERNAME = "root";
 private static final String DB_PASSWORD = "bhoomika";
 private double totalOrderSum;
 private double totalOrderAmount = 0.0;
 private double deliveryCharges = 0.0;
 private JButton submitButton;
 private JButton previousOrdersButton;
 private JTable ordersTable;
 public TextFieldExample(double totalOrderSum) {
 this.totalOrderSum = totalOrderSum;
 frame = new JFrame("Text Field Example");
 frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
 ImageIcon backgroundImage = new ImageIcon("my.jpg");
 JLabel background = new JLabel(backgroundImage);
 background.setLayout(new GridBagLayout());
 frame.setContentPane(background);
 JPanel panel = new JPanel(new GridBagLayout());
 panel.setOpaque(false);
 frame.add(panel);
 GridBagConstraints constraints = new GridBagConstraints();
 constraints.insets = new Insets(5, 10, 5, 10);
 phoneLabel = new JLabel("Phone Number:");
 phoneLabel.setFont(phoneLabel.getFont().deriveFont(Font.BOLD, 16));
 nameLabel = new JLabel("Name:");
 nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 16));
 locationLabel = new JLabel("Location:");
 locationLabel.setFont(locationLabel.getFont().deriveFont(Font.BOLD, 
16));
 phoneTextField = new JTextField(15);
 
phoneTextField.setFont(phoneTextField.getFont().deriveFont(Font.PLAIN, 
16));
 nameTextField = new JTextField(15);
 
nameTextField.setFont(nameTextField.getFont().deriveFont(Font.PLAIN, 
16));
 constraints.gridx = 0;
 constraints.gridy = 0;
 constraints.anchor = GridBagConstraints.LINE_START;
 panel.add(phoneLabel, constraints);
 constraints.gridx = 1;
 constraints.anchor = GridBagConstraints.LINE_START;
 panel.add(phoneTextField, constraints);
 constraints.gridx = 0;
 constraints.gridy = 1;
 panel.add(nameLabel, constraints);
 constraints.gridx = 1;
 panel.add(nameTextField, constraints);
 constraints.gridx = 0;
 constraints.gridy = 2;
 panel.add(locationLabel, constraints);
 constraints.gridx = 1;
 constraints.gridy = 2;
 constraints.anchor = GridBagConstraints.LINE_START;
 try {
 Connection connection = DriverManager.getConnection(DB_URL, 
DB_USERNAME, DB_PASSWORD);
 Statement statement = connection.createStatement();
 String query = "SELECT block FROM location";
 ResultSet resultSet = statement.executeQuery(query);
 DefaultComboBoxModel<String> locationComboBoxModel = new 
DefaultComboBoxModel<>();
 while (resultSet.next()) {
 String locationName = resultSet.getString("block");
 locationComboBoxModel.addElement(locationName);
 }
 locationComboBox = new JComboBox<>(locationComboBoxModel);
 
locationComboBox.setFont(locationComboBox.getFont().deriveFont(Font.PL
AIN, 16));
 locationComboBox.setEditable(false);
 panel.add(locationComboBox, constraints);
 resultSet.close();
 statement.close();
 connection.close();
 } catch (Exception e) {
 e.printStackTrace();
 }
 submitButton = new JButton("Submit");
 submitButton.addActionListener(new ActionListener() {
 public void actionPerformed(ActionEvent e) {
 String phoneNumber = phoneTextField.getText();
 String name = nameTextField.getText();
 String location = locationComboBox.getSelectedItem().toString();
 try {
 Connection connection = 
DriverManager.getConnection(DB_URL, DB_USERNAME, 
DB_PASSWORD);
 Statement statement = connection.createStatement();
 // Check if the mobile number already exists in the 'mobile' table
 String checkQuery = "SELECT * FROM mobile WHERE phone = 
'" + phoneNumber + "'";
 ResultSet checkResult = statement.executeQuery(checkQuery);
 if (checkResult.next()) {
 // Mobile number already exists, add order details to the 
respective table
 String tableName = "order_" + phoneNumber;
 
 // Retrieve delivery charges based on the location from 
'location' table 
 String deliveryChargesQuery = "SELECT delprice FROM 
location WHERE block = '" + location + "'";
 ResultSet deliveryChargesResult = 
statement.executeQuery(deliveryChargesQuery);
 if (deliveryChargesResult.next()) {
 deliveryCharges = 
deliveryChargesResult.getDouble("delprice");
 }
 // Calculate the total order amount
 totalOrderAmount = totalOrderSum + deliveryCharges;
 // Insert order details into the table
 String insertQuery = "INSERT INTO " + tableName + " (name, 
location, total, delivery_charges) VALUES ('"
 + name + "', '" + location + "', " + totalOrderSum + ", " + 
deliveryCharges + ")";
 statement.executeUpdate(insertQuery);
 } else {
 // Mobile number doesn't exist, insert it into the 'mobile' table 
and create a new table for the order
 String insertMobileQuery = "INSERT INTO mobile (phone) 
VALUES ('" + phoneNumber + "')";
 statement.executeUpdate(insertMobileQuery);
 String tableName = "order_" + phoneNumber;
 String createTableQuery = "CREATE TABLE IF NOT EXISTS 
" + tableName + " (id INT AUTO_INCREMENT, "
 + "name VARCHAR(255), location VARCHAR(255), total 
INT, delivery_charges INT, PRIMARY KEY (id))";
 statement.executeUpdate(createTableQuery);
 // Retrieve delivery charges based on the location from 
'delprice' table
 String deliveryChargesQuery = "SELECT delprice FROM 
location WHERE block = '" + location + "'";
 ResultSet deliveryChargesResult = 
statement.executeQuery(deliveryChargesQuery);
 if (deliveryChargesResult.next()) {
 deliveryCharges = 
deliveryChargesResult.getDouble("delprice");
 }
 // Calculate the total order amount
 totalOrderAmount = totalOrderSum + deliveryCharges;
 // Insert order details into the table
 String insertQuery = "INSERT INTO " + tableName + " (name, 
location, total, delivery_charges) VALUES ('"
 + name + "', '" + location + "', " + totalOrderSum + ", " + 
deliveryCharges + ")";
 statement.executeUpdate(insertQuery);
 }
 statement.close();
 connection.close();
 } catch (Exception ex) {
 ex.printStackTrace();
 }
 String message = "Sub Total: " + totalOrderSum + "\nDelivery Fee: 
" + deliveryCharges + "\nTotal: " + totalOrderAmount + "\nLocation: " + 
location;
 JOptionPane.showMessageDialog(frame, message, "Order 
Details", JOptionPane.INFORMATION_MESSAGE);
 
 }
 });
 constraints.gridx = 0;
constraints.gridy = 4;
constraints.gridwidth = 2;
constraints.anchor = GridBagConstraints.CENTER;
panel.add(submitButton, constraints);
 previousOrdersButton = new JButton("Previous Orders");
 previousOrdersButton.addActionListener(new ActionListener() {
 public void actionPerformed(ActionEvent e) {
 String phoneNumber = phoneTextField.getText();
 String tableName = "order_" + phoneNumber;
 try {
 Connection connection = DriverManager.getConnection(DB_URL, 
DB_USERNAME, DB_PASSWORD);
 Statement statement = connection.createStatement();
 // Check if the table exists
 String checkTableQuery = "SHOW TABLES LIKE '" + tableName + "'";
 ResultSet tableResultSet = 
statement.executeQuery(checkTableQuery);
 if (!tableResultSet.next()) {
 JOptionPane.showMessageDialog(frame, "Order does not exist for 
the given phone number.", "Order Not Found", 
JOptionPane.INFORMATION_MESSAGE);
 tableResultSet.close();
 statement.close();
 connection.close();
 return;
 }
 // Retrieve data from the existing table
 String selectQuery = "SELECT * FROM " + tableName;
 ResultSet resultSet = statement.executeQuery(selectQuery);
 // Create a table model to hold the data
 DefaultTableModel tableModel = new DefaultTableModel();
 tableModel.addColumn("Order ID");
 tableModel.addColumn("Name");
 tableModel.addColumn("Location");
 tableModel.addColumn("Total");
 tableModel.addColumn("Delivery Charges");
 // Add rows to the table model with the retrieved data
 while (resultSet.next()) {
 int orderID = resultSet.getInt("id");
 String name = resultSet.getString("name");
 String location = resultSet.getString("location");
 double total = resultSet.getDouble("total");
 double deliveryCharges = resultSet.getDouble("delivery_charges");
 tableModel.addRow(new Object[]{orderID, name, location, total, 
deliveryCharges});
 }
 // Create a JTable with the table model
 ordersTable = new JTable(tableModel);
 JScrollPane scrollPane = new JScrollPane(ordersTable);
 // Create a dialog to display the previous orders
 JDialog ordersDialog = new JDialog(frame, "Previous Orders", true);
 ordersDialog.setLayout(new BorderLayout());
 ordersDialog.add(scrollPane, BorderLayout.CENTER);
 ordersDialog.setSize(500, 300);
 ordersDialog.setLocationRelativeTo(frame);
 ordersDialog.setVisible(true);
 resultSet.close();
 statement.close();
 connection.close();
 } catch (Exception ex) {
 ex.printStackTrace();
 }
 }
});
 constraints.gridx = 0;
constraints.gridy = 5;
constraints.gridwidth = 2;
constraints.anchor = GridBagConstraints.CENTER;
panel.add(previousOrdersButton, constraints);
 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 frame.setVisible(true);
}
public static void main(String[] args) {
 SwingUtilities.invokeLater(new Runnable() {
 public void run() {
 new TextFieldExample(0);
 }
 });
}
}
