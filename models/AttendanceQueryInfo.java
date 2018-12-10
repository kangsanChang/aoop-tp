
public class AttendanceQueryInfo {
	Integer stukey;
	String week;
	String status;
	
	public AttendanceQueryInfo(Integer stukey, String week, String status) {
		this.stukey = stukey;
		this.week = week;
		this.status = status;
	}

	@Override
	public String toString() {
		return "AttendanceQueryInfo [stukey=" + stukey + ", week=" + week + ", status=" + status + "]";
	}

	public Integer getStukey() {
		return stukey;
	}

	public String getWeek() {
		return week;
	}

	public String getStatus() {
		return status;
	}
	
}
