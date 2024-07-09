package Calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class CalendarMain02 extends JFrame {
    Calendar cal;
    JPanel first_panel; //top, middle 패널을 담을 공간
    JPanel top_panel; // 이전, 다음 버튼과 년도객체담을 panel
    String[] d_day = {"일", "월", "화", "수", "목", "금", "토"};
    JButton bt_prev; // 이전버튼 객체
    JButton bt_next; // 다음 버튼 객체
    JLabel day_label; // 년도객체
    JPanel middle_panel; // 요일 패널
    JPanel bottom_panel; // day panel
    JLabel selecteLabel; //서택 레이블
    JButton addButton; //할일 추가 버튼

    // 날짜계산
    int year;
    int month;
    int firstDay;
    int lastDay;
    int clickedDay;

    public CalendarMain02() {
        // Frame 기본설정
        setSize(1200, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        cal = Calendar.getInstance();
        // 기본적으로 뿌려줄 날짜객체 생성
        year = cal.get(Calendar.YEAR);
        month = (cal.get(Calendar.MONTH) + 1);
        
        first_panel = new JPanel(new BorderLayout());

        // top_panel에 들어갈 객체들
        top_panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        day_label = new JLabel(year + "년 " + setZero_Month(month) + " 월", SwingConstants.CENTER);
        day_label.setVerticalAlignment(SwingConstants.TOP); // 수직 정렬 설정
        bt_prev = new JButton("<");
        bt_next = new JButton(">");
        addButton = new JButton("+");
        
        //이 외의 패널들 선언
        middle_panel = new JPanel();
        middle_panel.setLayout(new GridLayout(1, 7)); // 7열의 요일 레이아웃 설정
        
        first_panel.add(top_panel, BorderLayout.NORTH);
        first_panel.add(middle_panel, BorderLayout.CENTER);
        bottom_panel = new JPanel();
        bottom_panel.setLayout(new GridLayout(0, 7)); // 7열의 날짜 레이아웃 설정
        
     // 왼쪽 버튼 (<)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        
        top_panel.add(bt_prev, gbc);

        // 가운데 날짜 레이블
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        top_panel.add(day_label, gbc);

        // 오른쪽 버튼 (>)
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        
        top_panel.add(bt_next, gbc);

        // 오른쪽 끝 +버튼
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.1;
        top_panel.add(addButton, gbc);

        top_panel.setBackground(Color.GRAY);

        //<버튼클릭시 이전월
        bt_prev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDown(-1);
            }
        });

        //>버튼 클릭시 다음 월
        bt_next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateUp(1);
            }
        });
        
        //할일추가 버튼
        addButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(selecteLabel!=null) {
        			toDoList(year, month, clickedDay);
        		}else {
        			JOptionPane.showMessageDialog(null, "날짜를 선택해주세요"); //에러메시지
        		}
        	}
        });

        add(first_panel, BorderLayout.NORTH);
        
        add(bottom_panel, BorderLayout.CENTER);
        
        // 날짜 생성
        updateCalendar();
        
        // 요일생성
        addDayLabels();
        
        setVisible(true);
    }

    // 요일 라벨 추가
    public void addDayLabels() {
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1); // 테두리 생성
        System.out.println("요일이 생성되었습니다");
        for (String day : d_day) {
            JLabel dlabel = new JLabel(day, SwingConstants.CENTER);
            dlabel.setBackground(Color.LIGHT_GRAY);
            dlabel.setOpaque(true); // 배경색 적용
            dlabel.setBorder(border);
            //dlabel.setPreferredSize(new Dimension(dlabel.getPreferredSize().width, 25));
            
            middle_panel.add(dlabel);
        }
    }

    // 날짜 생성
    public void updateCalendar() {
        bottom_panel.removeAll();
        

        
        firstDay = getFirstDay(year, month);
        lastDay = getLastDay(year, month);

        // 이전 달의 마지막 날 계산
        int prevDay = getLastDay(year, month - 1);

        // 첫째 날 이전의 빈 셀을 이전 달의 날짜로 채우기
        for (int i = firstDay - 1; i >= 0; i--) {
            JLabel fLabel = new JLabel(String.valueOf(prevDay - i), SwingConstants.RIGHT);
            fLabel.setVerticalAlignment(SwingConstants.TOP);
            fLabel.setForeground(Color.GRAY); // 이전 달 날짜를 회색으로 표시
            fLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // 테두리 추가
            fLabel.setBorder(BorderFactory.createCompoundBorder(
                fLabel.getBorder(),
                BorderFactory.createEmptyBorder(3, 5, 5, 5))); // 여백 추가
            bottom_panel.add(fLabel);
        }

        // 해당 월의 날짜 셀 추가
        for (int day = 1; day <= lastDay; day++) {
        	final JLabel midLabel = new JLabel(String.valueOf(day), SwingConstants.RIGHT);
            midLabel.setVerticalAlignment(SwingConstants.TOP);
            midLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // 날짜 셀에 테두리 추가
            midLabel.setBorder(BorderFactory.createCompoundBorder(
                midLabel.getBorder(),
                BorderFactory.createEmptyBorder(3, 5, 5, 5))); // 여백 추가

            // 토요일 파란색 표시
            if ((firstDay + day - 1) % 7 == 6) {
                midLabel.setForeground(Color.BLUE);
                midLabel.setOpaque(true);
            }
            // 일요일 빨간색 표시
            else if ((firstDay + day - 1) % 7 == 0) {
                midLabel.setForeground(Color.RED);
                midLabel.setOpaque(true);
            }
            
            //클릭이벤트 추가
            final int clickedDay = day;
            midLabel.addMouseListener(new MouseAdapter() {
            	@Override
            	public void mouseClicked(MouseEvent e) {
            		if(e.getButton()==MouseEvent.BUTTON1) { //좌클릭 감지 이벤트	
            			if (selecteLabel != null) {
            				selecteLabel.setBackground(null);
            				selecteLabel.setOpaque(false);
            				selecteLabel.repaint();
            			}
            			midLabel.setBackground(Color.yellow);
            			midLabel.setOpaque(true);
            			midLabel.repaint();
            			selecteLabel=midLabel;
            			CalendarMain02.this.clickedDay = clickedDay;
            			System.out.println("좌클릭 이벤트 발생 : " +year + "-" + setZero_Month(month) + "-" + setZero_Month(clickedDay));                     
            		}
            	}
            });
            
            bottom_panel.add(midLabel);
        }

        // 마지막 날 이후의 빈 셀을 다음 달의 날짜로 채우기
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
            bottom_panel.add(lLabel);
            nextFirstDay++;
            emptyCell++;
        }

        bottom_panel.revalidate();
        bottom_panel.repaint();
    }
    //할일 저장
    public void toDoList(int year, int month, int day) {
    	//Dialog 기본 세팅
    	final JDialog toDoDialog = new JDialog(this, "할일 추가", true);
    	toDoDialog.setSize(300, 200);
    	toDoDialog.setLayout(new BorderLayout());
    	toDoDialog.setLocationRelativeTo(this);
    	
    	final JLabel dateLabel = new JLabel(year + "-" + setZero_Month(month) + "-" + setZero_Month(day));
    	final JTextField taskField = new JTextField();
    	final JButton saveButton = new JButton("저장");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = taskField.getText();
                
                //실제 캘린더에 할일 표시 추가 필요
                System.out.println("저장된 할일: " + task);
                toDoDialog.dispose();
            }
        });
        
        toDoDialog.add(dateLabel, BorderLayout.NORTH);
        toDoDialog.add(taskField, BorderLayout.CENTER);
        toDoDialog.add(saveButton, BorderLayout.SOUTH);

        toDoDialog.setVisible(true);
        

    }

    // month 내리기
    public void updateDown(int data) {
        // 날짜가 0월이 될 수 없으므로
        if (month == 1) {
            year -= 1;
            month = 12;
        } else {
            month -= 1;
        }
        setDay_Label();
        updateCalendar();
    }

    // month 올리기
    public void updateUp(int data) {
        if (month == 12) {
            year += 1;
            month = 1;
        } else {
            month += 1;
        }
        setDay_Label();
        updateCalendar();
    }

    // 날짜가 변경하면 Label 텍스트 변경, 날짜 변경 시 계속 업데이트
    public void setDay_Label() {
        day_label.setText(year + "년 " + setZero_Month(month) + " 월");
        day_label.updateUI();
    }

    // 날짜를 항상 두 자리로 유지
    public String setZero_Month(int n) {
        return ((n < 10) ? "0" + n : Integer.toString(n));
    }

    // 등록된 날짜로 첫째 날 계산
    public int getFirstDay(int year, int month) {
        cal.set(year, month - 1, 1);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    // 등록된 날짜로 마지막 날 계산
    public int getLastDay(int year, int month) {
    	//예외처리
    	if(month == 0) {
    		month = 12;
    		year -= 1;
    	}else if(month == 13) {
    		month = 1;
    		year += 1;
    	}
        cal.set(year, month, 0);
        return cal.get(Calendar.DATE);
    }

    public static void main(String[] args) {
        new CalendarMain();
    }
    
}