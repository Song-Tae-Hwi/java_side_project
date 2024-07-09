package Calendar.controller;

import Calendar.model.CalendarModel;
import Calendar.view.CalendarView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CalendarController {
    private CalendarModel model;
    private CalendarView view;
    
    public CalendarController(final CalendarModel model, final CalendarView view) {
        this.model = model;
        this.view = view;
        
        view.btPrev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.decrementMonth();
                updateView();
            }
        });
        
        view.btNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.incrementMonth();
                updateView();
            }
        });
        
        view.addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (view.selectedLabel != null) {
                    toDoList(model.getYear(), model.getMonth(), Integer.parseInt(view.selectedLabel.getText()));
                } else {
                    JOptionPane.showMessageDialog(null, "날짜를 선택해주세요");
                }
            }
        });
        
        updateView();
    }
    
    private void updateView() {
        view.dayLabel.setText(model.getYear() + "년 " + model.formatMonth(model.getMonth()) + " 월");
        updateCalendar();
    }
    
    private void updateCalendar() {
        view.bottomPanel.removeAll();
        
        int firstDay = model.getFirstDay();
        int lastDay = model.getLastDay();
        int prevDay = model.getLastDay();
        
        for (int i = firstDay - 1; i >= 0; i--) {
            JLabel label = new JLabel(String.valueOf(prevDay - i), SwingConstants.RIGHT);
            label.setVerticalAlignment(SwingConstants.TOP);
            label.setForeground(Color.GRAY);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            label.setBorder(BorderFactory.createCompoundBorder(
                    label.getBorder(),
                    BorderFactory.createEmptyBorder(3, 5, 5, 5))); // 여백 추가
                view.bottomPanel.add(label);
            view.bottomPanel.add(label);
        }
        
        for (int day = 1; day <= lastDay; day++) {
            final JLabel label = new JLabel(String.valueOf(day), SwingConstants.RIGHT);
            label.setVerticalAlignment(SwingConstants.TOP);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            label.setBorder(BorderFactory.createCompoundBorder(
                    label.getBorder(),
                    BorderFactory.createEmptyBorder(3, 5, 5, 5))); // 여백 추가
                view.bottomPanel.add(label);
            
            if ((firstDay + day - 1) % 7 == 6) {
                label.setForeground(Color.BLUE);
            } else if ((firstDay + day - 1) % 7 == 0) {
                label.setForeground(Color.RED);
            }
            
            final int clickedDay = day;
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if (view.selectedLabel != null) {
                            view.selectedLabel.setBackground(null);
                            view.selectedLabel.setOpaque(false);
                            view.selectedLabel.repaint();
                        }
                        label.setBackground(Color.YELLOW);
                        label.setOpaque(true);
                        label.repaint();
                        view.selectedLabel = label;
                        System.out.println("좌클릭 이벤트 발생 : " + model.getYear() + "-" + model.formatMonth(model.getMonth()) + "-" + model.formatMonth(clickedDay));
                    }
                }
            });
            
            view.bottomPanel.add(label);
        }
        
        int nextFirstDay = 1;
        int emptyCell = firstDay + lastDay;
        while (emptyCell % 7 != 0) {
            JLabel lLabel = new JLabel(String.valueOf(nextFirstDay), SwingConstants.RIGHT);
            lLabel.setVerticalAlignment(SwingConstants.TOP);
            lLabel.setForeground(Color.GRAY); // 다음 달 날짜를 회색으로 표시
            lLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // 테두리 추가
            lLabel.setBorder(BorderFactory.createCompoundBorder(
                lLabel.getBorder(),
                BorderFactory.createEmptyBorder(3, 5, 5, 5))); // 여백 추가
            view.bottomPanel.add(lLabel);
            nextFirstDay++;
            emptyCell++;
        }
        
        view.bottomPanel.revalidate();
        view.bottomPanel.repaint();
    }
    
    private void toDoList(int year, int month, int day) {
        final JDialog toDoDialog = new JDialog(view, "할일 추가", true);
        toDoDialog.setSize(300, 200);
        toDoDialog.setLayout(new BorderLayout());
        toDoDialog.setLocationRelativeTo(view);
        
        final JLabel dateLabel = new JLabel(year + "-" + model.formatMonth(month) + "-" + model.formatMonth(day));
        final JTextField taskField = new JTextField();
        final JButton saveButton = new JButton("저장");
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = taskField.getText();
                
                //할 일 추가 로직 요구
                System.out.println("저장된 할일: " + task);
                toDoDialog.dispose();
            }
        });
        
        toDoDialog.add(dateLabel, BorderLayout.NORTH);
        toDoDialog.add(taskField, BorderLayout.CENTER);
        toDoDialog.add(saveButton, BorderLayout.SOUTH);
        
        toDoDialog.setVisible(true);
    }
}
