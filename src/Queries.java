import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Queries {
	private static final String URL = "jdbc:mysql://localhost:3306/aoop_project?characterEncoding=UTF-8&serverTimezone=UTC";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "pass";

	private Connection connection = null;
	private PreparedStatement getAllAttendance = null;
	private PreparedStatement updateAttendance = null;

	private PreparedStatement getAllScores = null;
	private PreparedStatement updateScore = null;
	private PreparedStatement updateScoreRow = null;

	public Queries() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Database 연결 중");
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Database 연결성공!");

			getAllAttendance = connection.prepareStatement(
					"select stukey,name,week1,week2,week3,week4,week5,week6,week7,week8,week9,week10,week11,week12,week13,week14,week15,week16 from attendance natural join student;");
			getAllScores = connection.prepareStatement(
					"select stukey, name, mid_exam, final_exam, hw, quiz, announcement, report, etc from score natural join student;");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.exit(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	void close() {
		try {
			connection.close();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

	public void updateAttendance(ArrayList<AttendanceQueryInfo> updateQueryList) {
		for (AttendanceQueryInfo qi : updateQueryList) {
			try {
				updateAttendance = connection
						.prepareStatement("update attendance set week" + qi.getWeek() + " = ? where stukey = ?;");
				updateAttendance.setString(1, qi.getStatus());
				updateAttendance.setInt(2, qi.getStukey());
				updateAttendance.executeUpdate();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
	}

	public List<Attendance> getAllAttendance() {
		List<Attendance> results = null;
		ResultSet resultSet = null;

		try {
			resultSet = getAllAttendance.executeQuery();
			results = new ArrayList<Attendance>();
			while (resultSet.next()) {
				results.add(new Attendance(resultSet.getString("stukey"), resultSet.getString("name"),
						resultSet.getString("week1"), resultSet.getString("week2"), resultSet.getString("week3"),
						resultSet.getString("week4"), resultSet.getString("week5"), resultSet.getString("week6"),
						resultSet.getString("week7"), resultSet.getString("week8"), resultSet.getString("week9"),
						resultSet.getString("week10"), resultSet.getString("week11"), resultSet.getString("week12"),
						resultSet.getString("week13"), resultSet.getString("week14"), resultSet.getString("week15"),
						resultSet.getString("week16")));
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
				close();
			}
		}
		return results;
	}

	public void updateScore(ArrayList<ScoreQueryInfo> updateQueryList) {
		for (ScoreQueryInfo qi : updateQueryList) {
			try {
				updateScore = connection
						.prepareStatement("update score set "+ qi.getFieldName() +" = ? where stukey = ?;");
				updateScore.setInt(1, qi.getScore());
				updateScore.setInt(2, qi.getStukey());
				updateScore.executeUpdate();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
	}
	
	public void updateScoreRow(Score score) {
		try {
			updateScoreRow = connection.prepareStatement("update score set mid_exam = ?, final_exam = ?, hw = ?, quiz = ?, announcement = ?, report = ?, etc = ? WHERE stukey = ?;");
			updateScoreRow.setInt(1, Integer.parseInt(score.getMid_exam()));
			updateScoreRow.setInt(2, Integer.parseInt(score.getFinal_exam()));
			updateScoreRow.setInt(3, Integer.parseInt(score.getHw()));
			updateScoreRow.setInt(4, Integer.parseInt(score.getQuiz()));
			updateScoreRow.setInt(5, Integer.parseInt(score.getAnnouncement()));
			updateScoreRow.setInt(6, Integer.parseInt(score.getReport()));
			updateScoreRow.setInt(7, Integer.parseInt(score.getEtc()));
			updateScoreRow.setInt(8, Integer.parseInt(score.getStukey()));
			updateScoreRow.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Score> getAllScores() {
		List<Score> results = null;
		ResultSet resultSet = null;

		try {
			resultSet = getAllScores.executeQuery();
			results = new ArrayList<Score>();
			while (resultSet.next()) {
				results.add(new Score(resultSet.getString("stukey"), resultSet.getString("name"),
						resultSet.getString("mid_exam"), resultSet.getString("final_exam"), resultSet.getString("hw"),
						resultSet.getString("quiz"), resultSet.getString("announcement"), resultSet.getString("report"),
						resultSet.getString("etc")));
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
				close();
			}
		}
		return results;
	}

}
