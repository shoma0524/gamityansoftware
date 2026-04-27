package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {
	
	// ベースとなるSQL
	private String baseSql = "select * from test as t ";
	
	// ResultSetをStudentのListに変換する共通処理
	private List<TestListStudent> postFilter(ResultSet rSet) 
			throws Exception {
		List<TestListStudent> list = new ArrayList<>();
		
		try {
			while (rSet.next()) {
				TestListStudent tls = new TestListStudent();
				
				tls.setSubjectName(rSet.getString("name"));
				tls.setSubjectCd(rSet.getString("subject_cd"));
				tls.setNum(rSet.getInt("no"));
				tls.setPoint(rSet.getInt("point"));
				list.add(tls);
			}
		} catch (SQLException  | NullPointerException e) {
				e.printStackTrace();
		}
		
		return list;
	}
	
	// 学生番号で検索するメソッド
	public List<TestListStudent> filter(Student student) 
			throws Exception {
		List<TestListStudent> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;

		try {
			
			statement = connection.prepareStatement(
					baseSql+"left join subject as s on t.school_cd = s.school_cd and t.subject_cd = s.cd "
							+ "where student_no = ?");
			statement.setString(1,student.getNo());
			rSet = statement.executeQuery();
			
			list = postFilter(rSet);
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		
		return list;
	}
}