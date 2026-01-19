import java.awt.*;
import java.awt.event.*;

public class Practical26 extends Frame implements ActionListener {

    Button btnFlow, btnBorder, btnGrid;

    public Practical26() {
        setTitle("AWT Layout Frontend");
        setSize(300, 150);
        setLayout(new FlowLayout());

        btnFlow = new Button("FlowLayout");
        btnBorder = new Button("BorderLayout");
        btnGrid = new Button("GridLayout");

        add(btnFlow);
        add(btnBorder);
        add(btnGrid);

        btnFlow.addActionListener(this);
        btnBorder.addActionListener(this);
        btnGrid.addActionListener(this);

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnFlow) {
            Frame flow = new Frame("FlowLayout");
            flow.setSize(250, 150);
            flow.setLayout(new FlowLayout());
            for (int i = 1; i <= 5; i++) {
                flow.add(new Button("Button " + i));
            }
            flow.setVisible(true);
            flow.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    flow.dispose();
                }
            });
        }

        if (e.getSource() == btnBorder) {
            Frame border = new Frame("BorderLayout");
            border.setSize(250, 150);
            border.setLayout(new BorderLayout());
            border.add(new Button("North"), BorderLayout.NORTH);
            border.add(new Button("South"), BorderLayout.SOUTH);
            border.add(new Button("East"), BorderLayout.EAST);
            border.add(new Button("West"), BorderLayout.WEST);
            border.add(new Button("Center"), BorderLayout.CENTER);
            border.setVisible(true);
            border.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    border.dispose();
                }
            });
        }

        if (e.getSource() == btnGrid) {
            Frame grid = new Frame("GridLayout");
            grid.setSize(250, 150);
            grid.setLayout(new GridLayout(2, 3));
            for (int i = 1; i <= 6; i++) {
                grid.add(new Button("Btn " + i));
            }
            grid.setVisible(true);
            grid.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    grid.dispose();
                }
            });
        }
    }

    public static void main(String[] args) {
        new Practical26();
    }
}

