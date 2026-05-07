package bean;

public class Permission implements java.io.Serializable {

	private String cd;	    // 権限コード
	private String name;	// 権限名

	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
