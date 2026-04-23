package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {
	
	// 科目情報を1件取得
	public Subject get(String cd,School school) throws Exception {
		Subject subject = new Subject();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement("select * from subject where school_cd = ?,cd = ?");
			statement.setString(1, school.getCd());
			statement.setString(2, cd);
			ResultSet rSet = statement.executeQuery();
			SchoolDao schoolDao = new SchoolDao();
			
			if (rSet.next()) {
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				subject.setSchool(schoolDao.get(rSet.getString("school_cd")));
			} else {
				subject = null;
			}
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
		
		return subject;
	}
	
	// 学校コードで絞り込み
	public List<Subject> filter(School school) 	
			throws Exception {
		List<Subject> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		
		try {
			
			statement = connection.prepareStatement(
					"select * from subject where school_cd = ?");
			statement.setString(1,school.getCd());
			ResultSet rSet = statement.executeQuery();
			
			while (rSet.next()) {
				Subject subject = new Subject();
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				subject.setSchool(school);
				list.add(subject);
			}
			
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
	
	// 科目情報の登録 or 更新を行うメソッド
	public boolean save(Subject subject)
			throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count =0;
		School school = subject.getSchool();
		SchoolDao schoolDao = new SchoolDao();
		
		try {
			Subject old = get(subject.getCd(),subject.getSchool());
			if (old == null) {
				statement = connection.prepareStatement(
					"insert into subject(school_cd,cd,name) "
						+"values(?,?,?)");
				statement.setString(1,school.getCd());
				statement.setString(2,subject.getCd());
				statement.setString(3,subject.getName());
			} else {
				statement = connection.prepareStatement(
					"update subject set name = ? where school_cd = ?,cd = ?");
				statement.setString(1,school.getCd());
				statement.setInt(2,subject.getCd());
			}
			
			count = statement.executeUpdate();
			
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
		
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean delete(Subject subject) 
		throws Exception{
		Connection connection = getConnection();
		PreparedStatement statement = null;
		School school = subject.getSchool();
		int count =0;
		
		try {
			statement = connection.prepareStatement(
				"delete from subject where school_cd = ?,cd = ?");
			statement.setString(1,school.getCd());
			statement.setString(2,subject.getCd());
			
			count = statement.executeUpdate();
			
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
		
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
}