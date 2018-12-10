import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_function {
	Connection DB = makeConnection();
	PreparedStatement pstmt = null;
	String sql = "";
	
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
					attcount += 0.5;
				} else if (temp.equals("late")) {
					attcount += 0.5;
				} else if (temp.equals("ok")) {
					attcount += 1;
				}
			}
			attScore[attstudent] = (int)attcount;
			attstudent ++;
			attcount = 0;
		}
		return attScore;
	}
	
	public String[][] get_allScore(int row, int[] attScore, int[] ratioArray, int[] maxArray) throws SQLException {
		// DB get score
		sql = ("SELECT st.name, sc.mid, sc.final, sc.hw, sc.quiz, sc.announcement, sc.report, sc.etc FROM student st, score sc WHERE st.stukey = sc.stukey;");
		pstmt = DB.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		String data[][] = new String[row][];
		int[] Score = new int[8];
		int totalScore = 0;
		int n = 0;
		while(rs.next()) {
			Score[0] = attScore[n]*ratioArray[0]/maxArray[0];
			
			totalScore += Score[0];
			for(int i=2; i<=8; i++) {
				Score[i-1] = rs.getInt(i)*ratioArray[i-1]/maxArray[i-1];
				totalScore += Score[i-1];
			}
			data[n] = new String[]  {
					rs.getString(1)
					//,Integer.toString(rs.getInt(2)*ratioArray[1]/maxArray[1])
					,Integer.toString(Score[0])
					,Integer.toString(Score[1])
					,Integer.toString(Score[2])
					,Integer.toString(Score[3])
					,Integer.toString(Score[4])
					,Integer.toString(Score[5])
					,Integer.toString(Score[6])
					,Integer.toString(Score[7])
					,Integer.toString(totalScore) 
					,"A+"};
			totalScore = 0;
			n++;
		}
		return data;
	}
	
	public static Connection makeConnection() {
		String url = "jdbc:mysql://localhost:3306/aoop_project?characterEncoding=UTF-8&serverTimezone=UTC";
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
}

