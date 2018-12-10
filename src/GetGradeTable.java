import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class GetGradeTable extends JPanel {
	static DB_function DB;
	
	static int studentCount;
	static int[] attScore;
	static int[] ratioArray;
	static int[] maxArray; 
	static Object[][] data;
	static String[] title; 
	
	static JTextField field_search;
	
	private static JTable table_score;
	private static JScrollPane sp;
	private static DefaultTableModel model;

	private static TableRowSorter sorter;
	static RowFilter<DefaultTableModel , Object> rf = null; 
	
	public GetGradeTable() {
		try {
			DB = new DB_function();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setLayout(new BorderLayout());
		
		JPanel controlPanel = new JPanel(new BorderLayout()); // control area
		JPanel controlButton = new JPanel(); // button area
		JPanel controlSearch = new JPanel(); // searchbox area
		
		JButton btn_changeGrade = new JButton("Grade %");
		JButton btn_changePercent = new JButton("Score %");
		JButton btn_csvExport = new JButton("CSV Export");
		
		field_search = new JTextField(20);
		//JButton btn_test = new JButton("Test");
		
        // -------------- button set --------------------------
		controlButton.add(btn_changeGrade);
		controlButton.add(btn_changePercent);
		controlButton.add(btn_csvExport);
		//controlButton.add(btn_test);
		
		controlSearch.add(field_search);	
		controlPanel.add(controlSearch, BorderLayout.WEST);
		controlPanel.add(controlButton, BorderLayout.EAST);
		this.add(controlPanel, BorderLayout.NORTH);
		
		try {
			this.setTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
	    this.add(sp, BorderLayout.CENTER);
        
        this.setVisible(true);
        
        btn_changeGrade.addActionListener(listen_changeGrade);
        btn_changePercent.addActionListener(listen_changePercent);
        btn_csvExport.addActionListener(listen_csvExport);
        field_search.addKeyListener(listen_searchBox);
	}
	
	public static boolean exportToCSV(JTable tableToExport, String pathToExportTo) {

	    try {
	        TableModel model = tableToExport.getModel();
	        BufferedWriter csv = new BufferedWriter(new OutputStreamWriter(
	        		new FileOutputStream(pathToExportTo), "UTF-8"));
	        for (int i = 0; i < model.getColumnCount(); i++) {
	            csv.write(model.getColumnName(i) + ",");
	        }
	        csv.write("\n");
	        for (int i = 0; i < model.getRowCount(); i++) {
	            for (int j = 0; j < model.getColumnCount(); j++) {
	                csv.write(model.getValueAt(i, j).toString() + ",");
	            }
	            csv.write("\n");
	        }
	        csv.close();
	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public static void repaintTable() {
		JLabel test = new JLabel("test");
		model = null;
		
		try {
			studentCount = DB.get_studentCount();
			attScore = DB.cal_attScore(studentCount);
			ratioArray = DB.get_ratioArray();
			maxArray = DB.get_maxArray(); 
			data = DB.get_allScore(studentCount, attScore, ratioArray, maxArray);
			title = new String[] {"학번", "이름", "출석 / "+ratioArray[0], "중간시험 / "+ratioArray[1], "기말시험 / "+ratioArray[2],
					"과제 / "+ratioArray[3], "퀴즈 / "+ratioArray[4], "발표 / "+ratioArray[5], 
					"보고서 / "+ratioArray[6], "기타 / "+ratioArray[7], "총점", "학점"};
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // ----------------------------------------------------
        //table_score = new JTable(data, title);
	
        model = new DefaultTableModel(data, title);
        
        table_score.setModel(model);
        
        table_score.revalidate();
        table_score.repaint();
	}
	
	private static void setTable() throws SQLException {
		studentCount = DB.get_studentCount();
		attScore = DB.cal_attScore(studentCount);
		ratioArray = DB.get_ratioArray();
		maxArray = DB.get_maxArray(); 
		data = DB.get_allScore(studentCount, attScore, ratioArray, maxArray);
		
		title = new String[] {"학번", "이름", "출석 / "+ratioArray[0], "중간시험 / "+ratioArray[1], "기말시험 / "+ratioArray[2],
				"과제 / "+ratioArray[3], "퀴즈 / "+ratioArray[4], "발표 / "+ratioArray[5], 
				"보고서 / "+ratioArray[6], "기타 / "+ratioArray[7], "총점", "학점"};
        // ----------------------------------------------------
        table_score = new JTable();
        model = new DefaultTableModel(data, title);
        
        table_score.setModel(model);
        table_score.setPreferredScrollableViewportSize(table_score.getPreferredSize());
        table_score.setFillsViewportHeight(true);
        
        // set table sorter
        sorter = new TableRowSorter(model);
        table_score.setRowSorter(sorter);
       
        
        sp = new JScrollPane(table_score);
        sp.setPreferredSize(new Dimension(sp.getPreferredSize().width, 400));
	}
	
	ActionListener listen_csvExport = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showSaveDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				String path = chooser.getSelectedFile().getAbsolutePath();
				exportToCSV(table_score, path + ".csv");
			}
		}
	};
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
	KeyListener listen_searchBox = new KeyListener() {

		@Override
		public void keyPressed(KeyEvent arg0) {
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			//declare a row filter for your table model
	        try {
	        	rf = RowFilter.regexFilter(field_search.getText(), 1);
        	}
	        catch (java.util.regex.PatternSyntaxException e) {
	            return;
	        }
	        sorter.setRowFilter(rf);
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}
	};
}