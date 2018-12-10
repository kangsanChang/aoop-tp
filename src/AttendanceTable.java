import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class AttendanceTable extends JPanel {
	private List<Attendance> results; // 전체 출석 정보를 가지고 있는 배열
	ArrayList<AttendanceQueryInfo> updateQueryList = new ArrayList<AttendanceQueryInfo>(); // update 될 정보(수정한정보) 가 담기는 곳
	private String columnNames[] = { "학번", "학생이름", "1주 ", "2주", "3주", "4주", "5주", "6주", "7주", "8주", "9주", "10주", "11주",
			"12주", "13주", "14주", "15주", "16주" };
	private DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
		// 테이블에서 학번, 이름 은 readonly 로 만들기 위함
		@Override
		public boolean isCellEditable(int row, int column) {
			return (column == 0 || column == 1) ? false : true;
		}
	};

	public AttendanceTable() {
		setLayout(new BorderLayout());
		Queries q = new Queries();
		results = q.getAllAttendance();

		// create panels
		JPanel controlPanel = new JPanel();

		// set title
		JLabel title = new JLabel("출결 현황");
		title.setFont(getFont().deriveFont(25.0f));

		// set button
		JButton savebtn = new JButton("SAVE");

		savebtn.addActionListener(e -> {
			if (e.getSource() == savebtn) {
				q.updateAttendance(updateQueryList);
				System.out.println("update 완료");
				updateQueryList.clear();
			}
		});
		controlPanel.add(title);
		controlPanel.add(savebtn);

		// add components
		add(controlPanel, BorderLayout.NORTH);
		
		// create table
		JTable table = new JTable(model);

		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				TableCellListener tcl = (TableCellListener) e.getSource();
				updateQueryList.add(new AttendanceQueryInfo(tcl.getRow() + 1, Integer.toString(tcl.getColumn() - 1),
						tcl.getNewValue().toString()));
				System.out.println(Arrays.toString(updateQueryList.toArray()));
			}
		};

		new TableCellListener(table, action); // table cell의 event catch 하기 위함

		for (int i = 2; i < 18; i++) {
			// 1~16주차의 combo box 만듦
			setUpAttendColumn(table, table.getColumnModel().getColumn(i));
		}

		for (Attendance a : results) {
			model.addRow(new Object[] { a.getStukey(), a.getName(), a.getWeek1(), a.getWeek2(), a.getWeek3(),
					a.getWeek4(), a.getWeek5(), a.getWeek6(), a.getWeek7(), a.getWeek8(), a.getWeek9(), a.getWeek10(),
					a.getWeek11(), a.getWeek12(), a.getWeek13(), a.getWeek14(), a.getWeek15(), a.getWeek16() });
		}

		JScrollPane sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(1200, 600));
		add(sp, BorderLayout.CENTER);
	}

	public void setUpAttendColumn(JTable table, TableColumn attendColumn) {
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("결석");
		comboBox.addItem("지각");
		comboBox.addItem("출석");

		attendColumn.setCellEditor(new DefaultCellEditor(comboBox));

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for comboBox");
		attendColumn.setCellRenderer(renderer);

	}
}
