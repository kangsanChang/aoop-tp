import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GradeCalculationApplication extends JFrame{
	JTabbedPane tab;
	AttendanceTable at;
	InputScoreTable it;
	GetGradeTable gt;
	StatisticTable st;
	
	GradeCalculationApplication(){
		tab = new JTabbedPane(JTabbedPane.TOP);
		JPanel attendance = new JPanel();
		JPanel inputScore = new JPanel();
		JPanel getGrade = new JPanel();
		JPanel statistic = new JPanel();
		at = new AttendanceTable();
		it = new InputScoreTable();
		gt = new GetGradeTable();
		st = new StatisticTable();
		
		// panel 한글 체크
		attendance.add(at);
		inputScore.add(it);
		getGrade.add(gt);
		statistic.add(st);
		
		tab.addTab("출석", attendance);
		tab.addTab("성적 입력", inputScore);
		tab.addTab("성적 계산", getGrade);
		tab.addTab("통계", statistic);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(tab);
		setTitle("Grade Calculation Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(1200, 800);
		pack();
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new GradeCalculationApplication();
	}

}
