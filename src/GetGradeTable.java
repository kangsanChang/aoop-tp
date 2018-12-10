import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class GetGradeTable extends JPanel {
	DB_function DB = new DB_function();
	
	public GetGradeTable() throws SQLException {
		this.setLayout(new BorderLayout());
		
		JPanel controlPanel = new JPanel(new BorderLayout()); // control area
		JPanel controlButton = new JPanel(); // button area
		JPanel controlSearch = new JPanel(); // searchbox area
		
		JButton btn_changeGrade = new JButton("Grade %");
		JButton btn_changePercent = new JButton("Score %");
		JButton btn_csvExport = new JButton("CSV Export");
		JTextField field_search = new JTextField(20);
		
        // -------------- button set --------------------------
		controlButton.add(btn_changeGrade);
		controlButton.add(btn_changePercent);
		controlButton.add(btn_csvExport);
		controlSearch.add(field_search);	
		controlPanel.add(controlSearch, BorderLayout.WEST);
		controlPanel.add(controlButton, BorderLayout.EAST);
		this.add(controlPanel, BorderLayout.NORTH);
		
		// -------------------- DB get ------------------
		/* in all array : attScore[], ratioArray[], maxArray[]....
		 * each data of Array's elements is
		 * 0 : attendance, 1 : midExam, 2 : finalExam, 3 : HomeWork
		 * 4 : Quiz, 5 : announcement, 6 : Report, 7 : etc
		 */
		int studentCount = DB.get_studentCount();
		int[] attScore = DB.cal_attScore(studentCount);
		int[] ratioArray = DB.get_ratioArray();
		int[] maxArray = DB.get_maxArray(); 
		String[][] data = DB.get_allScore(studentCount, attScore, ratioArray, maxArray);
		String title[] = {"", "attend / "+ratioArray[0], "midExam / "+ratioArray[1], "finExam / "+ratioArray[2],
				"HW / "+ratioArray[3], "QUIZ / "+ratioArray[4], "announce / "+ratioArray[5], 
				"Report / "+ratioArray[6], "etc / "+ratioArray[7], "Total", "Grade"};
        // ----------------------------------------------------
        JTable table_score = new JTable(data, title);
        JScrollPane sp = new JScrollPane(table_score);
        
        this.add(sp, BorderLayout.CENTER);
//        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
        
        btn_changeGrade.addActionListener(listen_changeGrade);
        btn_changePercent.addActionListener(listen_changePercent);
	}
	
	ActionListener listen_changeGrade = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				Button_changeGrade action1 = new Button_changeGrade(DB);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	};
	ActionListener listen_changePercent = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				Button_changePercent action1 = new Button_changePercent(DB);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	};
}