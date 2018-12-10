import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

public class DB_function {
	Connection DB = makeConnection();
	PreparedStatement pstmt = null;
	String sql = "";
	
	static int[] totalScoreArray;
	static int row;
	
	
	// -------------------- DB get ------------------
	/* in all array : attScore[], ratioArray[], maxArray[]....
	 * each data of Array's elements is
	 * 0 : attendance, 1 : midExam, 2 : finalExam, 3 : HomeWork
	 * 4 : Quiz, 5 : announcement, 6 : Report, 7 : etc
	 */
	
	public DB_function() throws SQLException {
		makeConnection();
	}
	public int get_studentCount() throws SQLException {
		// get total student count
		sql = ("SELECT COUNT(stukey) FROM student");
		pstmt = DB.prepareStatement(sql);
		ResultSet count = pstmt.executeQuery();
		int rowCount = 0;
		while(count.next()) { rowCount = Integer.parseInt(count.getString(1)); }
		return rowCount;
	}
	
	public int[] get_ratioArray() throws SQLException {
		sql = ("SELECT * FROM cal_ratio_settings;");
		pstmt = DB.prepareStatement(sql);
		ResultSet ra = pstmt.executeQuery();
		int[] ratioArray = new int[8];
		while(ra.next()) {
			for(int i=0; i<8; i++) {
				ratioArray[i] = ra.getInt(i+1);
			}
		}
		return ratioArray;
	}
	
	public int[] get_maxArray() throws SQLException {
		// DB get max ratio
		sql = ("SELECT * FROM max_settings;");
		pstmt = DB.prepareStatement(sql);
		ResultSet max = pstmt.executeQuery();
		int[] maxArray = new int[8];
		while(max.next()) {
			for(int i=0; i<8; i++) {
				maxArray[i] = max.getInt(i+1);
			}
		}
		return maxArray;
	}
	
	public int[] get_gradeRatioArray() throws SQLException {
		sql = ("SELECT * FROM grade_ratio_settings;");
		pstmt = DB.prepareStatement(sql);
		ResultSet max = pstmt.executeQuery();
		int[] gradeRatioArray = new int[9];
		while(max.next()) {
			for(int i=0; i<8; i++) {
				gradeRatioArray[i] = max.getInt(i+1);
			}
		}
		return gradeRatioArray;
	}

	public int[] cal_attScore(int row) throws SQLException {
		// DB attendance score
		sql = ("SELECT * FROM attendance;");
		pstmt = DB.prepareStatement(sql);
		ResultSet at = pstmt.executeQuery();
		int[] attScore = new int[row];
		int attstudent = 0;
		double attcount = 0;
		String late = "late"; String no = "no"; String ok = "ok";
		while(at.next()) {
			for(int i = 1; i<18; i++) {
				String temp = at.getString(i);
				if(temp == null) {
					attcount += 0;
				} else if (temp.equals("지각")) {
					attcount += 0.5;
				} else if (temp.equals("출석")) {
					attcount += 1;
				}
			}
			attScore[attstudent] = (int)attcount;
			attstudent ++;
			attcount = 0;
		}
		return attScore;
	}
	
	public static double cal_scoreAverage() {
	      int sum = 0;
	      for(int i = 0; i < row; i++) {
	         sum += totalScoreArray[i];
	      }
	      //float average = Math.round((((float)sum/row)*100)/100.0);
	      double average = (double)sum/row;
	      //System.out.println(Math.round((average*100) / 100.0));
	      System.out.println(average);
	      return average;
	   }
	
	public static int[] count_stuScoreArea() {
		int[] countArray = new int[10];
		for(int i = 0; i < 10; i++) {
			for(int c = 0; c < row; c++ ) {
				if(i*10 < totalScoreArray[c] && totalScoreArray[c] <= (i+1)*10) {
					countArray[i]++;
				}
			}
		}
		return countArray;
	}
	public Object[][] get_allScore(int row, int[] attScore, int[] ratioArray, int[] maxArray) throws SQLException {
		// DB get score
		sql = ("SELECT st.name, sc.mid_exam, sc.final_exam, sc.hw, sc.quiz, sc.announcement, sc.report, sc.etc, st.stukey FROM student st, score sc WHERE st.stukey = sc.stukey;");
		pstmt = DB.prepareStatement(sql);	// update sql
		ResultSet rs = pstmt.executeQuery();
		String data[][] = new String[row][];
		int[] Score = new int[8];
		//int[] totalScoreArray = new int[row];							
		totalScoreArray = new int[row];									
		this.row = row;													
		int totalScore = 0;

		int[] stukey = new int[row];	// update grade
		
		// make Array of total Score
		int n = 0;
		
		n = 0;
		// make Array for return
		while(rs.next()) {
			//Score[0] = attScore[n]*ratioArray[0]/maxArray[0];
			Score[0] = attScore[n]*ratioArray[0]/16;
			totalScore += Score[0];
			for(int i=2; i<=8; i++) {
				Score[i-1] = rs.getInt(i)*ratioArray[i-1]/maxArray[i-1];
				totalScore += Score[i-1]; 
			}
			totalScoreArray[n] = totalScore;

			stukey[n] = rs.getInt(9); // update grade
			data[n] = new String[]  {
					Integer.toString(stukey[n]),
					rs.getString(1)
					,Integer.toString(Score[0])
					,Integer.toString(Score[1])
					,Integer.toString(Score[2])
					,Integer.toString(Score[3])
					,Integer.toString(Score[4])
					,Integer.toString(Score[5])
					,Integer.toString(Score[6])
					,Integer.toString(Score[7])
					,Integer.toString(totalScore) 
					,"T"};
			n++;
			totalScore = 0;
		}
		int[] gradeBoundary = this.cal_gradeBoundary(totalScoreArray, row);
		for(int a = 0; a < row; a++) {	// update
			String grade = null;
			if(totalScoreArray[a] >= gradeBoundary[0]) grade = "A+";
			else if(totalScoreArray[a] >= gradeBoundary[1]) grade = "A";
			else if(totalScoreArray[a] >= gradeBoundary[2]) grade = "B+";
			else if(totalScoreArray[a] >= gradeBoundary[3]) grade = "B";
			else if(totalScoreArray[a] >= gradeBoundary[4]) grade = "C+";
			else if(totalScoreArray[a] >= gradeBoundary[5]) grade = "C";
			else if(totalScoreArray[a] >= gradeBoundary[6]) grade = "D";
			else grade = "F";
			data[a][11] = grade;
			update_stuGrade(stukey[a], grade);
		}
		this.count_stuScoreArea();
		return data;
	}
	
	// update grade
	public void update_stuGrade(int stukey, String grade) throws SQLException {
		sql = ("UPDATE student SET grade = ? WHERE stukey = ?;");
		pstmt = DB.prepareStatement(sql);
		pstmt.setString(1, grade);
		pstmt.setInt(2, stukey);
		pstmt.executeUpdate();
	}
	
	public void update_ratioArray(int[] setData) throws SQLException {
		sql = ("UPDATE cal_ratio_settings SET attendratio=?, midratio=?, finalratio=?, hwratio=?, quizratio=?, announcementratio=?, reportratio=?, etcratio=?");
		pstmt = DB.prepareStatement(sql);
		for(int i=0; i<8; i++) {
			pstmt.setInt(i+1, setData[i]);
		}
		pstmt.executeUpdate();
	}
	
	public void update_gradeArray(int[] setData) throws SQLException {
		sql = ("UPDATE grade_ratio_settings SET ap_ratio=?, a_ratio=?, bp_ratio=?, b_ratio=?, cp_ratio=?, c_ratio=?, d_ratio=?, f_ratio=?");
		pstmt = DB.prepareStatement(sql);
		for(int i=0; i<8; i++) {
			pstmt.setInt(i+1, setData[i]);
		}
		pstmt.executeUpdate();
	}
	
	public static Connection makeConnection() {
		String url = "jdbc:mysql://localhost/aoop_project?characterEncoding=UTF-8&serverTimezone=UTC";
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("database connecting....");
			con = DriverManager.getConnection(url, "root", "pass");
			System.out.println("connection success !!");
		} catch(ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch(SQLException ex) {
			System.out.println("SQL Exception : " + ex.getMessage());
		}
		return con;
	}
	
	/* -------------------------- cal function -------------------*/
	public int[] cal_gradeBoundary(int[] data, int row) {

		int[] calData = new int[row]; // 모든 점수들의 합 배열
		for(int i=0; i<row; i++) {
			calData[i] = data[i];
		}
		int[] gradeFilter = new int[8];
		int ratio = 0;
		Arrays.sort(calData);
		
		try {
			int[] gradeRatio = this.get_gradeRatioArray();
			for(int i=0; i<8; i++) {
				ratio += gradeRatio[i];
				double percent = (double)ratio/100;
				gradeFilter[i] = 30-((int) (row*percent));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int[] gradeBoundary = {
			calData[gradeFilter[0]], calData[gradeFilter[1]], calData[gradeFilter[2]], 
			calData[gradeFilter[3]], calData[gradeFilter[4]], calData[gradeFilter[5]], 
			calData[gradeFilter[6]], calData[gradeFilter[7]]
		};	
		//for(int i=0;i<row;i++) System.out.println(calData[29-i] + " , " + data[i]);
		//for(int i=0;i<8;i++) System.out.println(gradeBoundary[i]);
		return gradeBoundary;
	}
}

