
public class ScoreQueryInfo {
	Integer stukey;
	String fieldName;
	Integer score;

	public ScoreQueryInfo(Integer stukey, String fieldName, Integer score) {
		this.stukey = stukey;
		this.fieldName = fieldName;
		this.score = score;
	}
	

	@Override
	public String toString() {
		return "ScoreQueryInfo [stukey=" + stukey + ", fieldName=" + fieldName + ", score=" + score + "]";
	}

	public Integer getStukey() {
		return stukey;
	}
	public String getFieldName() {
		return fieldName;
	}
	public Integer getScore() {
		return score;
	}
	
}
