import java.awt.*;
import java.awt.event.*;

public class Practical25 extends Frame implements ActionListener {
    // Components
    Label nameLabel, genderLabel, courseLabel, addressLabel, resultLabel;
    TextField nameField;
    CheckboxGroup genderGroup;
    Checkbox male, female;
    Choice courseChoice;
    TextArea addressArea;
    Button submitButton;

    public Practical25() {
        // Frame properties
        setTitle("Registration Form");
        setSize(400, 450);
        setLayout(null);
        setVisible(true);

        // Name
        nameLabel = new Label("Name:");
        nameLabel.setBounds(50, 50, 100, 20);
        add(nameLabel);

        nameField = new TextField();
        nameField.setBounds(150, 50, 200, 20);
        add(nameField);

        // Gender
        genderLabel = new Label("Gender:");
        genderLabel.setBounds(50, 90, 100, 20);
        add(genderLabel);

        genderGroup = new CheckboxGroup();
        male = new Checkbox("Male", genderGroup, false);
        female = new Checkbox("Female", genderGroup, false);
        male.setBounds(150, 90, 60, 20);
        female.setBounds(220, 90, 70, 20);
        add(male);
        add(female);

        // Course
        courseLabel = new Label("Course:");
        courseLabel.setBounds(50, 130, 100, 20);
        add(courseLabel);

        courseChoice = new Choice();
        courseChoice.setBounds(150, 130, 200, 20);
        courseChoice.add("Computer Engineering");
        courseChoice.add("Mechanical Engineering");
        courseChoice.add("Electrical Engineering");
        courseChoice.add("Civil Engineering");
        add(courseChoice);

        // Address
        addressLabel = new Label("Address:");
        addressLabel.setBounds(50, 170, 100, 20);
        add(addressLabel);

        addressArea = new TextArea();
        addressArea.setBounds(150, 170, 200, 80);
        add(addressArea);

        // Submit Button
        submitButton = new Button("Submit");
        submitButton.setBounds(150, 270, 80, 30);
        submitButton.addActionListener(this);
        add(submitButton);

        // Result Label
        resultLabel = new Label();
        resultLabel.setBounds(50, 320, 300, 80);
        add(resultLabel);

        // Window Close Event
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
    }

    // Action Listener Method
    public void actionPerformed(ActionEvent ae) {
        String name = nameField.getText();
        String gender = genderGroup.getSelectedCheckbox() != null ?
                        genderGroup.getSelectedCheckbox().getLabel() : "Not Selected";
        String course = courseChoice.getSelectedItem();
        String address = addressArea.getText();

        resultLabel.setText("Registered: " + name + " | " + gender + " | " + course);
    }

    // Main Method
    public static void main(String[] args) {
        new Practical25();
    }
}
