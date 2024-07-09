package Calendar.view;

import javax.swing.*;
import java.awt.*;

public class CalendarView extends JFrame {
    public JPanel firstPanel;
    public JPanel topPanel;
    public JPanel middlePanel;
    public JPanel bottomPanel;
    public JLabel dayLabel;
    public JButton btPrev;
    public JButton btNext;
    public JButton addButton;
    public JLabel selectedLabel;
    
    public CalendarView() {
        setSize(1200, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        firstPanel = new JPanel(new BorderLayout());
        topPanel = new JPanel(new GridBagLayout());
        middlePanel = new JPanel(new GridLayout(1, 7));
        bottomPanel = new JPanel(new GridLayout(0, 7));
        
        dayLabel = new JLabel("", SwingConstants.CENTER);
        btPrev = new JButton("<");
        btNext = new JButton(">");
        addButton = new JButton("+");
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(btPrev, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        topPanel.add(dayLabel, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 0;
        topPanel.add(btNext, gbc);
        
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.1;
        topPanel.add(addButton, gbc);
        
        topPanel.setBackground(Color.GRAY);
        
        firstPanel.add(topPanel, BorderLayout.NORTH);
        firstPanel.add(middlePanel, BorderLayout.CENTER);
        
        add(firstPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
        
        setVisible(true);
    }
}
