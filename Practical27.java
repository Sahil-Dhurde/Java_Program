import java.awt.*;
import java.awt.event.*;

public class Practical27 {
    public static void main(String[] args) {
        Frame frame = new Frame("AWT Calculator");

        // Create components
        Label l1 = new Label("Number 1:");
        l1.setBounds(50, 50, 80, 20);
        TextField t1 = new TextField();
        t1.setBounds(150, 50, 100, 20);

        Label l2 = new Label("Number 2:");
        l2.setBounds(50, 80, 80, 20);
        TextField t2 = new TextField();
        t2.setBounds(150, 80, 100, 20);

        Label result = new Label("Result:");
        result.setBounds(50, 110, 200, 20);

        // Buttons
        Button add = new Button("+");
        add.setBounds(30, 150, 40, 30);

        Button sub = new Button("-");
        sub.setBounds(80, 150, 40, 30);

        Button mul = new Button("*");
        mul.setBounds(130, 150, 40, 30);

        Button div = new Button("/");
        div.setBounds(180, 150, 40, 30);

        Button mod = new Button("%");
        mod.setBounds(230, 150, 40, 30);

        // Add action using anonymous inner classes
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a = Integer.parseInt(t1.getText());
                int b = Integer.parseInt(t2.getText());
                result.setText("Result: " + (a + b));
            }
        });

        sub.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a = Integer.parseInt(t1.getText());
                int b = Integer.parseInt(t2.getText());
                result.setText("Result: " + (a - b));
            }
        });

        mul.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a = Integer.parseInt(t1.getText());
                int b = Integer.parseInt(t2.getText());
                result.setText("Result: " + (a * b));
            }
        });

        div.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a = Integer.parseInt(t1.getText());
                int b = Integer.parseInt(t2.getText());
                if (b != 0)
                    result.setText("Result: " + (a / b));
                else
                    result.setText("Cannot divide by zero");
            }
        });

        mod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a = Integer.parseInt(t1.getText());
                int b = Integer.parseInt(t2.getText());
                result.setText("Result: " + (a % b));
            }
        });

        // Add components to frame
        frame.add(l1);
        frame.add(t1);
        frame.add(l2);
        frame.add(t2);
        frame.add(result);
        frame.add(add);
        frame.add(sub);
        frame.add(mul);
        frame.add(div);
        frame.add(mod);

        frame.setSize(320, 250);
        frame.setLayout(null);
        frame.setVisible(true);

        // Close window on exit
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                frame.dispose();
            }
        });
    }
}

