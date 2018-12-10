import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Button_changeGrade extends JFrame {
	DB_function local_DB = null;
	int[] gradeRatioArray = null;
	JTable table_changeGrade;
	
	public Button_changeGrade(DB_function DB) throws SQLException {
		local_DB = DB;
		gradeRatioArray = local_DB.get_gradeRatioArray();
		
		String title[] = {"A+", "A", "B+", "B", "C+", "C", "D", "F"};
		String data[][] = {
				{
					Integer.toString(gradeRatioArray[0]),
					Integer.toString(gradeRatioArray[1]),
					Integer.toString(gradeRatioArray[2]),
					Integer.toString(gradeRatioArray[3]),
					Integer.toString(gradeRatioArray[4]),
					Integer.toString(gradeRatioArray[5]),
					Integer.toString(gradeRatioArray[6]),
					Integer.toString(gradeRatioArray[7]),
				}
			};
		table_changeGrade = new JTable(data, title);
		JScrollPane sp = new JScrollPane(table_changeGrade);
		this.add(sp, BorderLayout.CENTER);
		
		JPanel controlButton = new JPanel(new BorderLayout());
		JButton btn_save = new JButton("SAVE");
		btn_save.addActionListener(listen_save);
		
		controlButton.add(btn_save, BorderLayout.EAST);
		this.add(controlButton, BorderLayout.SOUTH);
		
		this.setTitle("Change Grade Percent");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 120);
		setVisible(true);
	}
	
	ActionListener listen_save = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			int sum = 0;
			int[] setData = new int[8];
			for(int i=0; i<8; i++) {
				setData[i] = Integer.parseInt( (String) table_changeGrade.getValueAt(0, i));
				sum += setData[i];
			}
			if(sum != 100) {
				JOptionPane.showMessageDialog(null, "총 합이 100 이 되어야 합니다 !!");
			} else {
				try {
					local_DB.update_gradeArray(setData);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("등급 비율의 합 : " + sum);
				GetGradeTable.repaintTable();
			}
		}
	};
}
