import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class StatisticTable extends JPanel {

	String[] conditions = { "총점", "출석", "중간고사", "기말고사" };
	String[] scoreGap = { "0-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90", "91-100" };
	String chartTitle = conditions[0];
	DefaultCategoryDataset chartData = getDataSet(0);
	JFreeChart chart = getChart(chartTitle, chartData);
	ChartPanel cp = new ChartPanel(chart);
	Queries q = new Queries();

	public StatisticTable() {
		setLayout(new BorderLayout());
		JPanel topPanel = new JPanel(new BorderLayout());
		JPanel conditionBox = new JPanel();

		JLabel title = new JLabel("성적 통계");
		title.setFont(getFont().deriveFont(25.0f));

		JLabel condition_msg = new JLabel("조건 선택");
		conditionBox.add(condition_msg);

		JComboBox box = new JComboBox(conditions);
		conditionBox.add(box);

		box.addActionListener(e -> {
			JComboBox cb = (JComboBox) e.getSource();
			int option = cb.getSelectedIndex();
			chartTitle = conditions[option];
			chartData = getDataSet(option);
			refreshChart();
		});

		topPanel.add(title, BorderLayout.WEST);
		topPanel.add(conditionBox, BorderLayout.EAST);
		add(topPanel, BorderLayout.NORTH);

		cp.setPreferredSize(new Dimension(800, 600));
		add(cp, BorderLayout.CENTER);
	}

	public void refreshChart() {
		cp.removeAll();
		cp.revalidate();
		chart = getChart(chartTitle, chartData);
		chart.removeLegend();
		ChartPanel chartPanel = new ChartPanel(chart);
		cp.setLayout(new BorderLayout());
		cp.add(chartPanel);
		cp.repaint();
	}

	public JFreeChart getChart(String title, DefaultCategoryDataset dataset) {
		return ChartFactory.createBarChart(title, "점수", "인원 수", dataset);
	}

	private DefaultCategoryDataset getDataSet(int option) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		switch (option) {
		case 0:
			chartTitle += " (평균 :"+DB_function.cal_scoreAverage()+"점)";
			int[] totalResult = DB_function.count_stuScoreArea();
			for(int i=0 ; i<totalResult.length ; i++)
				dataset.addValue(totalResult[i], "score", scoreGap[i]);
			break;
		case 1:
			int[] attendResult = q.getAttendanceStatisticInfo();
			for(int i=0; i<attendResult.length ; i++) {
				dataset.addValue(attendResult[i], "attendance", (i+1)+"회");
			}
			break;
		case 2:
			int[] midResult = q.getScoreStatisticInfo(conditions[2]);
			for(int i=0; i<midResult.length ; i++) {
				dataset.addValue(midResult[i], "score", scoreGap[i]);
			}
			break;
		case 3:
			int[] finalResult = q.getScoreStatisticInfo(conditions[3]);
			for(int i=0; i<finalResult.length ; i++) {
				dataset.addValue(finalResult[i], "score", scoreGap[i]);
			}
			break;
		}

		return dataset;
	}
}
