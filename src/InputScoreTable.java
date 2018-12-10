import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class InputScoreTable extends JPanel{
	private List<Score> results; // 전체 점수 정보를 가지고 있는 배열
	ArrayList<ScoreQueryInfo> updateQueryList = new ArrayList<ScoreQueryInfo>();
	private String columnNames[] = {"학번", "이름", "중간", "기말", "과제", "퀴즈", "발표", "보고서", "기타"};
	private String dbColumnNames[] = {"stukey", "name", "mid_exam", "final_exam", "hw", "quiz", "announcement", "report", "etc"};
	private DefaultTableModel model = new DefaultTableModel(columnNames, 0){
		// 테이블에서 학번, 이름 은 readonly 로 만들기 위함
		@Override
		public boolean isCellEditable(int row, int column) {
			return (column == 0 || column == 1) ? false : true;
		}
	};
	
	public InputScoreTable() {
		setLayout(new BorderLayout());
		Queries q = new Queries();
		results = q.getAllScores();
		
		JPanel topPanel = new JPanel(new BorderLayout());
		
		// create panels
		JPanel controlPanel = new JPanel();
		
		// set title
		JLabel title = new JLabel("점수 입력");
		title.setFont(getFont().deriveFont(25.0f));
		
		// set button
		JButton csvbtn = new JButton("CSV Import");
		JButton savebtn = new JButton("SAVE");
		
		csvbtn.addActionListener(e -> {
			if(e.getSource() == csvbtn) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("csv file", "csv");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					new csvImport(chooser.getSelectedFile().getAbsolutePath());
					System.out.println("CSV import 완료");
					// import 된 model로 view 교체
					results = q.getAllScores();
					model.setNumRows(0);
					for(Score s : results) {
						model.addRow(new Object[] { s.getStukey(), s.getName(), s.getMid_exam(), s.getFinal_exam(), s.getHw(), s.getQuiz(), s.getAnnouncement(), s.getReport(), s.getEtc()});
					}
				}
			}
		});
		
		savebtn.addActionListener(e -> {
			if (e.getSource() == savebtn) {
				q.updateScore(updateQueryList);
				System.out.println("update 완료");
				GetGradeTable.repaintTable();
			}
		});
		
		controlPanel.add(csvbtn);
		controlPanel.add(savebtn);
		topPanel.add(title, BorderLayout.WEST);
		topPanel.add(controlPanel, BorderLayout.EAST);
		
		// add components
		add(topPanel, BorderLayout.NORTH);
		
		// create table
		JTable table = new JTable(model);
		
		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				TableCellListener tcl = (TableCellListener) e.getSource();
				updateQueryList.add(new ScoreQueryInfo(tcl.getRow() + 1, dbColumnNames[tcl.getColumn()],
						Integer.parseInt((String) tcl.getNewValue())));
				System.out.println(Arrays.toString(updateQueryList.toArray()));
			}
		};
		
		new TableCellListener(table, action);
		
		for(Score s : results) {
			model.addRow(new Object[] { s.getStukey(), s.getName(), s.getMid_exam(), s.getFinal_exam(), s.getHw(), s.getQuiz(), s.getAnnouncement(), s.getReport(), s.getEtc()});
		}
		
		JScrollPane sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(800, 600));
		add(sp, BorderLayout.CENTER);

	}
}
