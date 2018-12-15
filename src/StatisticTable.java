import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatisticTable extends JPanel {

	String[] conditions = { "총점", "출석", "중간고사", "기말고사" };
	String[] scoreGap = { "0-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90", "91-100" };
	String[] attendGap = { "1회", "2회", "3회", "4회", "5회", "6회", "7회", "8회", "9회", "10회", "11회", "12회", "13회", "14회", "15회", "16회"};
	String chartTitle = conditions[0];
	HistogramPanel chart = getChart(scoreGap, getDataSet(0));
	Queries q = new Queries();

	public StatisticTable() {
		setLayout(new BorderLayout());
		JPanel topPanel = new JPanel(new BorderLayout());
		JPanel conditionBox = new JPanel();

		JLabel title = new JLabel("성적 통계 (총점 평균 : "+DB_function.cal_scoreAverage()+" )");
		title.setFont(getFont().deriveFont(25.0f));

		JLabel condition_msg = new JLabel("조건 선택");
		conditionBox.add(condition_msg);

		JComboBox box = new JComboBox(conditions);
		conditionBox.add(box);

		box.addActionListener(e -> {
			JComboBox cb = (JComboBox) e.getSource();
			int option = cb.getSelectedIndex();
			chartTitle = conditions[option];
			this.remove(chart);
			chart = (option==1) ? getChart(attendGap, getDataSet(option)) : getChart(scoreGap, getDataSet(option));
			chart.setPreferredSize(new Dimension(800, 600));
			add(chart, BorderLayout.CENTER);
			chart.revalidate();
			this.repaint();
		});

		topPanel.add(title, BorderLayout.WEST);
		topPanel.add(conditionBox, BorderLayout.EAST);
		add(topPanel, BorderLayout.NORTH);
        
		chart.setPreferredSize(new Dimension(800, 600));
		add(chart, BorderLayout.CENTER);
	}
	
	private HistogramPanel getChart(String[] gap, int[] values) {
		HistogramPanel hp = new HistogramPanel();
    	for(int i=0 ; i<gap.length ; i++)
    		hp.addHistogramColumn(gap[i], values[i], Color.BLUE);
    	hp.layoutHistogram();
    	return hp;
	}

	private int[] getDataSet(int option) {
		switch (option) {
		case 0:
			// total
			return DB_function.count_stuScoreArea();
		case 1:
			// attendance
			return q.getAttendanceStatisticInfo();
		case 2:
			// mid
			return q.getScoreStatisticInfo(conditions[2]);
		case 3:
			// final
			return q.getScoreStatisticInfo(conditions[3]);
		default:
			return null;
		}
	}
}
