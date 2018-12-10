
public class Score {
	private String stukey;
	private String name;
	private String mid_exam;
	private String final_exam;
	private String hw;
	private String quiz;
	private String announcement;
	private String report;
	private String etc;
	
	// csv import 용 (join table 에서 가져오는 것이 아니므로, parsing한 score data 를 그대로 가져와서 사용
	public Score(String stukey, String mid_exam, String final_exam, String hw, String quiz, String announcement,
			String report, String etc) {
		this.stukey = stukey;
		this.name = null;
		this.mid_exam = mid_exam;
		this.final_exam = final_exam;
		this.hw = hw;
		this.quiz = quiz;
		this.announcement = announcement;
		this.report = report;
		this.etc = etc;
	}
	
	@Override
	public String toString() {
		return "Score [stukey=" + stukey + ", name=" + name + ", mid_exam=" + mid_exam + ", final_exam=" + final_exam
				+ ", hw=" + hw + ", quiz=" + quiz + ", announcement=" + announcement + ", report=" + report + ", etc="
				+ etc + "]";
	}

	public Score(String stukey, String name, String mid_exam, String final_exam, String hw, String quiz, String announcement,
			String report, String etc) {
		this.stukey = stukey;
		this.name = name;
		this.mid_exam = mid_exam;
		this.final_exam = final_exam;
		this.hw = hw;
		this.quiz = quiz;
		this.announcement = announcement;
		this.report = report;
		this.etc = etc;
	}
	
	public String getStukey() {
		return stukey;
	}
	public String getName() {
		return name;
	}
	public String getMid_exam() {
		return mid_exam;
	}
	public String getFinal_exam() {
		return final_exam;
	}
	public String getHw() {
		return hw;
	}
	public String getQuiz() {
		return quiz;
	}
	public String getAnnouncement() {
		return announcement;
	}
	public String getReport() {
		return report;
	}
	public String getEtc() {
		return etc;
	}
	
	
}
