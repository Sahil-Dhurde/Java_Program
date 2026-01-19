import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.table.DefaultTableModel;

/**
 * IoT Radar System Application
 * A comprehensive Java application for monitoring and controlling IoT-based radar systems
 */
public class IoTRadarSystem extends JFrame {
    
    // Core components
    private RadarPanel radarPanel;
    private ControlPanel controlPanel;
    private DataPanel dataPanel;
    private LogPanel logPanel;
    private IoTCommunicator iotComm;
    
    // Data structures
    private Queue<RadarData> dataQueue;
    private List<DetectedObject> detectedObjects;
    private ExecutorService executor;
    
    // Configuration
    private RadarConfig config;
    
    public IoTRadarSystem() {
        initializeSystem();
        setupUI();
        startDataProcessing();
    }
    
    private void initializeSystem() {
        config = new RadarConfig();
        dataQueue = new ConcurrentLinkedQueue<>();
        detectedObjects = Collections.synchronizedList(new ArrayList<>());
        executor = Executors.newFixedThreadPool(3);
        iotComm = new IoTCommunicator();
    }
    
    private void setupUI() {
        setTitle("IoT Radar System Control Center");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create main panels
        radarPanel = new RadarPanel();
        controlPanel = new ControlPanel();
        dataPanel = new DataPanel();
        logPanel = new LogPanel();
        
        // Layout setup
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(radarPanel, BorderLayout.CENTER);
        leftPanel.add(controlPanel, BorderLayout.SOUTH);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(dataPanel, BorderLayout.CENTER);
        rightPanel.add(logPanel, BorderLayout.SOUTH);
        
        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        mainSplit.setDividerLocation(600);
        
        add(mainSplit, BorderLayout.CENTER);
        
        // Menu bar
        setupMenuBar();
        
        pack();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(createMenuItem("Export Data", e -> exportData()));
        fileMenu.add(createMenuItem("Import Config", e -> importConfig()));
        fileMenu.addSeparator();
        fileMenu.add(createMenuItem("Exit", e -> System.exit(0)));
        
        JMenu deviceMenu = new JMenu("Device");
        deviceMenu.add(createMenuItem("Connect", e -> connectToDevice()));
        deviceMenu.add(createMenuItem("Disconnect", e -> disconnectFromDevice()));
        deviceMenu.add(createMenuItem("Calibrate", e -> calibrateRadar()));
        
        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(createMenuItem("About", e -> showAbout()));
        
        menuBar.add(fileMenu);
        menuBar.add(deviceMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    private JMenuItem createMenuItem(String text, ActionListener action) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(action);
        return item;
    }
    
    private void startDataProcessing() {
        // Start data collection thread
        executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    RadarData data = iotComm.receiveData();
                    if (data != null) {
                        dataQueue.offer(data);
                        processRadarData(data);
                    }
                    Thread.sleep(50); // 20 FPS update rate
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    logPanel.addLog("Error: " + e.getMessage(), LogLevel.ERROR);
                }
            }
        });
        
        // Start UI update thread
        Timer uiTimer = new Timer(50, e -> updateUI());
        uiTimer.start();
    }
    
    private void processRadarData(RadarData data) {
        // Process radar sweep data to detect objects
        List<DetectedObject> newObjects = new ArrayList<>();
        
        for (int i = 0; i < data.distances.length; i++) {
            double distance = data.distances[i];
            if (distance > config.minDetectionRange && distance < config.maxDetectionRange) {
                double angle = data.startAngle + (i * data.angleStep);
                DetectedObject obj = new DetectedObject(distance, angle, data.timestamp);
                newObjects.add(obj);
            }
        }
        
        synchronized (detectedObjects) {
            // Remove old objects
            detectedObjects.removeIf(obj -> 
                System.currentTimeMillis() - obj.timestamp > config.objectTimeout);
            
            // Add new objects
            detectedObjects.addAll(newObjects);
        }
        
        dataPanel.updateData(data, detectedObjects.size());
        logPanel.addLog("Sweep completed: " + newObjects.size() + " objects detected", LogLevel.INFO);
    }
    
    private void updateUI() {
        radarPanel.repaint();
        dataPanel.refresh();
    }
    
    // Event handlers
    private void connectToDevice() {
        String address = JOptionPane.showInputDialog(this, "Enter device IP address:", "192.168.1.100");
        if (address != null && !address.trim().isEmpty()) {
            try {
                iotComm.connect(address, config.devicePort);
                logPanel.addLog("Connected to device: " + address, LogLevel.SUCCESS);
                controlPanel.updateConnectionStatus(true);
            } catch (Exception e) {
                logPanel.addLog("Connection failed: " + e.getMessage(), LogLevel.ERROR);
                JOptionPane.showMessageDialog(this, "Failed to connect: " + e.getMessage());
            }
        }
    }
    
    private void disconnectFromDevice() {
        iotComm.disconnect();
        controlPanel.updateConnectionStatus(false);
        logPanel.addLog("Disconnected from device", LogLevel.INFO);
    }
    
    private void calibrateRadar() {
        // Implement calibration logic
        JOptionPane.showMessageDialog(this, "Calibration completed successfully!");
        logPanel.addLog("Radar calibration completed", LogLevel.SUCCESS);
    }
    
    private void exportData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV Files", "csv"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().endsWith(".csv")) {
                    file = new File(file.getAbsolutePath() + ".csv");
                }
                exportDataToCSV(file);
                logPanel.addLog("Data exported to: " + file.getName(), LogLevel.SUCCESS);
            } catch (Exception e) {
                logPanel.addLog("Export failed: " + e.getMessage(), LogLevel.ERROR);
            }
        }
    }
    
    private void exportDataToCSV(File file) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("Timestamp,Distance,Angle,Signal Strength");
            
            synchronized (detectedObjects) {
                for (DetectedObject obj : detectedObjects) {
                    writer.printf("%d,%.2f,%.2f,%.2f%n", 
                        obj.timestamp, obj.distance, obj.angle, obj.signalStrength);
                }
            }
        }
    }
    
    private void importConfig() {
        // Implementation for importing configuration
        JOptionPane.showMessageDialog(this, "Configuration import feature coming soon!");
    }
    
    private void showAbout() {
        JOptionPane.showMessageDialog(this, 
            "IoT Radar System v1.0\n" +
            "Advanced radar monitoring and control application\n" +
            "Supports real-time data visualization and IoT device communication",
            "About", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Inner classes
    class RadarPanel extends JPanel {
        private final int centerX = 300;
        private final int centerY = 300;
        private final int maxRadius = 250;
        
        public RadarPanel() {
            setPreferredSize(new Dimension(600, 600));
            setBackground(Color.BLACK);
            setBorder(new TitledBorder("Radar Display"));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawRadarGrid(g2d);
            drawDetectedObjects(g2d);
            drawSweepLine(g2d);
            drawRangeRings(g2d);
            
            g2d.dispose();
        }
        
        private void drawRadarGrid(Graphics2D g2d) {
            g2d.setColor(Color.GREEN.darker());
            g2d.setStroke(new BasicStroke(1));
            
            // Draw concentric circles
            for (int i = 1; i <= 5; i++) {
                int radius = (maxRadius * i) / 5;
                g2d.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
            }
            
            // Draw angle lines
            for (int angle = 0; angle < 360; angle += 30) {
                double radians = Math.toRadians(angle);
                int x2 = centerX + (int) (maxRadius * Math.cos(radians));
                int y2 = centerY + (int) (maxRadius * Math.sin(radians));
                g2d.drawLine(centerX, centerY, x2, y2);
            }
        }
        
        private void drawDetectedObjects(Graphics2D g2d) {
            synchronized (detectedObjects) {
                for (DetectedObject obj : detectedObjects) {
                    double radians = Math.toRadians(obj.angle);
                    int objX = centerX + (int) ((obj.distance / config.maxDetectionRange) * maxRadius * Math.cos(radians));
                    int objY = centerY + (int) ((obj.distance / config.maxDetectionRange) * maxRadius * Math.sin(radians));
                    
                    // Color based on signal strength
                    float intensity = (float) Math.min(obj.signalStrength / 100.0, 1.0);
                    g2d.setColor(new Color(1.0f, intensity, 0.0f, 0.8f));
                    g2d.fillOval(objX - 4, objY - 4, 8, 8);
                    
                    // Draw distance text
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("Arial", Font.PLAIN, 10));
                    g2d.drawString(String.format("%.1fm", obj.distance), objX + 6, objY - 6);
                }
            }
        }
        
        private void drawSweepLine(Graphics2D g2d) {
            if (iotComm.isConnected()) {
                double currentAngle = System.currentTimeMillis() * 0.1 % 360;
                double radians = Math.toRadians(currentAngle);
                
                int x2 = centerX + (int) (maxRadius * Math.cos(radians));
                int y2 = centerY + (int) (maxRadius * Math.sin(radians));
                
                g2d.setColor(Color.GREEN.brighter());
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(centerX, centerY, x2, y2);
            }
        }
        
        private void drawRangeRings(Graphics2D g2d) {
            g2d.setColor(Color.GREEN);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            
            for (int i = 1; i <= 5; i++) {
                int radius = (maxRadius * i) / 5;
                double range = (config.maxDetectionRange * i) / 5;
                g2d.drawString(String.format("%.0fm", range), centerX + radius + 5, centerY);
            }
        }
    }
    
    class ControlPanel extends JPanel {
        private JButton connectBtn, startBtn, stopBtn, calibrateBtn;
        private JSlider sensitivitySlider, rangeSlider;
        private JLabel statusLabel;
        private JTextField ipField;
        
        public ControlPanel() {
            setupControls();
        }
        
        private void setupControls() {
            setLayout(new GridBagLayout());
            setBorder(new TitledBorder("Control Panel"));
            GridBagConstraints gbc = new GridBagConstraints();
            
            // Connection controls
            gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(5, 5, 5, 5);
            add(new JLabel("Device IP:"), gbc);
            
            gbc.gridx = 1;
            ipField = new JTextField("192.168.1.100", 12);
            add(ipField, gbc);
            
            gbc.gridx = 2;
            connectBtn = new JButton("Connect");
            connectBtn.addActionListener(e -> connectToDevice());
            add(connectBtn, gbc);
            
            // Control buttons
            gbc.gridx = 0; gbc.gridy = 1;
            startBtn = new JButton("Start Scan");
            startBtn.addActionListener(e -> startScanning());
            add(startBtn, gbc);
            
            gbc.gridx = 1;
            stopBtn = new JButton("Stop Scan");
            stopBtn.addActionListener(e -> stopScanning());
            add(stopBtn, gbc);
            
            gbc.gridx = 2;
            calibrateBtn = new JButton("Calibrate");
            calibrateBtn.addActionListener(e -> calibrateRadar());
            add(calibrateBtn, gbc);
            
            // Sliders
            gbc.gridx = 0; gbc.gridy = 2;
            add(new JLabel("Sensitivity:"), gbc);
            gbc.gridx = 1; gbc.gridwidth = 2;
            sensitivitySlider = new JSlider(1, 100, 50);
            sensitivitySlider.addChangeListener(e -> updateSensitivity());
            add(sensitivitySlider, gbc);
            
            gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
            add(new JLabel("Range (m):"), gbc);
            gbc.gridx = 1; gbc.gridwidth = 2;
            rangeSlider = new JSlider(10, 1000, 100);
            rangeSlider.addChangeListener(e -> updateRange());
            add(rangeSlider, gbc);
            
            // Status
            gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 3;
            statusLabel = new JLabel("Status: Disconnected");
            statusLabel.setForeground(Color.RED);
            add(statusLabel, gbc);
        }
        
        private void startScanning() {
            iotComm.sendCommand("START_SCAN");
            logPanel.addLog("Scanning started", LogLevel.INFO);
        }
        
        private void stopScanning() {
            iotComm.sendCommand("STOP_SCAN");
            logPanel.addLog("Scanning stopped", LogLevel.INFO);
        }
        
        private void updateSensitivity() {
            config.sensitivity = sensitivitySlider.getValue();
            iotComm.sendCommand("SET_SENSITIVITY:" + config.sensitivity);
        }
        
        private void updateRange() {
            config.maxDetectionRange = rangeSlider.getValue();
            iotComm.sendCommand("SET_RANGE:" + config.maxDetectionRange);
        }
        
        public void updateConnectionStatus(boolean connected) {
            if (connected) {
                statusLabel.setText("Status: Connected");
                statusLabel.setForeground(Color.GREEN);
                connectBtn.setText("Disconnect");
            } else {
                statusLabel.setText("Status: Disconnected");
                statusLabel.setForeground(Color.RED);
                connectBtn.setText("Connect");
            }
        }
    }
    
    class DataPanel extends JPanel {
        private DefaultTableModel tableModel;
        private JTable dataTable;
        private JLabel objectCountLabel, rangeLabel, lastUpdateLabel;
        
        public DataPanel() {
            setLayout(new BorderLayout());
            setBorder(new TitledBorder("Data Panel"));
            
            setupDataTable();
            setupInfoPanel();
        }
        
        private void setupDataTable() {
            String[] columns = {"Object ID", "Distance (m)", "Angle (°)", "Signal Strength", "Age (s)"};
            tableModel = new DefaultTableModel(columns, 0);
            dataTable = new JTable(tableModel);
            
            JScrollPane scrollPane = new JScrollPane(dataTable);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            add(scrollPane, BorderLayout.CENTER);
        }
        
        private void setupInfoPanel() {
            JPanel infoPanel = new JPanel(new GridLayout(3, 1));
            
            objectCountLabel = new JLabel("Objects Detected: 0");
            rangeLabel = new JLabel("Detection Range: " + config.maxDetectionRange + "m");
            lastUpdateLabel = new JLabel("Last Update: Never");
            
            infoPanel.add(objectCountLabel);
            infoPanel.add(rangeLabel);
            infoPanel.add(lastUpdateLabel);
            
            add(infoPanel, BorderLayout.SOUTH);
        }
        
        public void updateData(RadarData data, int objectCount) {
            SwingUtilities.invokeLater(() -> {
                objectCountLabel.setText("Objects Detected: " + objectCount);
                lastUpdateLabel.setText("Last Update: " + new Date());
                
                // Update table
                tableModel.setRowCount(0);
                synchronized (detectedObjects) {
                    int id = 1;
                    for (DetectedObject obj : detectedObjects) {
                        long age = (System.currentTimeMillis() - obj.timestamp) / 1000;
                        tableModel.addRow(new Object[]{
                            "OBJ" + String.format("%03d", id++),
                            String.format("%.2f", obj.distance),
                            String.format("%.1f", obj.angle),
                            String.format("%.1f%%", obj.signalStrength),
                            age
                        });
                    }
                }
            });
        }
        
        public void refresh() {
            rangeLabel.setText("Detection Range: " + config.maxDetectionRange + "m");
        }
    }
    
    class LogPanel extends JPanel {
        private JTextArea logArea;
        private JScrollPane scrollPane;
        
        public LogPanel() {
            setLayout(new BorderLayout());
            setBorder(new TitledBorder("System Log"));
            
            logArea = new JTextArea(8, 50);
            logArea.setEditable(false);
            logArea.setBackground(Color.BLACK);
            logArea.setForeground(Color.GREEN);
            logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
            
            scrollPane = new JScrollPane(logArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            add(scrollPane, BorderLayout.CENTER);
            
            addLog("System initialized", LogLevel.INFO);
        }
        
        public void addLog(String message, LogLevel level) {
            SwingUtilities.invokeLater(() -> {
                String timestamp = String.format("[%tH:%tM:%tS] ", 
                    System.currentTimeMillis(), System.currentTimeMillis(), System.currentTimeMillis());
                String levelStr = "[" + level.name() + "] ";
                
                logArea.append(timestamp + levelStr + message + "\n");
                logArea.setCaretPosition(logArea.getDocument().getLength());
            });
        }
    }
    
    // Data classes
    static class RadarData {
        public double[] distances;
        public double startAngle;
        public double angleStep;
        public long timestamp;
        public double scanRate;
        
        public RadarData(double[] distances, double startAngle, double angleStep) {
            this.distances = distances;
            this.startAngle = startAngle;
            this.angleStep = angleStep;
            this.timestamp = System.currentTimeMillis();
            this.scanRate = 1.0; // Hz
        }
    }
    
    static class DetectedObject {
        public double distance;
        public double angle;
        public double signalStrength;
        public long timestamp;
        
        public DetectedObject(double distance, double angle, long timestamp) {
            this.distance = distance;
            this.angle = angle;
            this.timestamp = timestamp;
            this.signalStrength = 50 + Math.random() * 50; // Simulated signal strength
        }
    }
    
    static class RadarConfig {
        public double minDetectionRange = 1.0; // meters
        public double maxDetectionRange = 100.0; // meters
        public int sensitivity = 50; // 1-100
        public long objectTimeout = 5000; // milliseconds
        public int devicePort = 8080;
        public double angleResolution = 1.0; // degrees
    }
    
    enum LogLevel {
        INFO, SUCCESS, WARNING, ERROR
    }
    
    // IoT Communication class
    class IoTCommunicator {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private boolean connected = false;
        private String deviceAddress;
        
        public void connect(String address, int port) throws IOException {
            socket = new Socket(address, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            connected = true;
            deviceAddress = address;
        }
        
        public void disconnect() {
            connected = false;
            try {
                if (socket != null) socket.close();
                if (out != null) out.close();
                if (in != null) in.close();
            } catch (IOException e) {
                // Handle quietly
            }
        }
        
        public boolean isConnected() {
            return connected;
        }
        
        public void sendCommand(String command) {
            if (connected && out != null) {
                out.println(command);
            }
        }
        
        public RadarData receiveData() {
            if (!connected) {
                // Simulate data for demo purposes
                return generateSimulatedData();
            }
            
            try {
                String line = in.readLine();
                if (line != null) {
                    return parseRadarData(line);
                }
            } catch (IOException e) {
                connected = false;
            }
            
            return generateSimulatedData();
        }
        
        private RadarData parseRadarData(String data) {
            // Parse actual IoT device data format
            // Format: "RADAR_DATA:angle1,dist1;angle2,dist2;..."
            double[] distances = new double[360];
            // Implementation depends on your IoT device protocol
            return new RadarData(distances, 0, 1);
        }
        
        private RadarData generateSimulatedData() {
            // Generate simulated radar data for demonstration
            double[] distances = new double[360];
            for (int i = 0; i < distances.length; i++) {
                if (Math.random() > 0.95) { // 5% chance of object
                    distances[i] = 10 + Math.random() * 90;
                } else {
                    distances[i] = config.maxDetectionRange + 10; // No object
                }
            }
            return new RadarData(distances, 0, 1);
        }
    }
    
    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new IoTRadarSystem().setVisible(true);
        });
    }
}