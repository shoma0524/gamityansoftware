package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;

public class SchoolDao extends Dao {
	// Schoolを1件取得するメソッド
	public School get(String cd) 
			throws Exception {
		School school = new School();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(
					"select * from school where cd = ?");
			statement.setString(1, cd);
			ResultSet rSet = statement.executeQuery();
			
			if (rSet.next()) {
				school.setCd(rSet.getString("cd"));
				school.setName(rSet.getString("name"));
			} else {
				school = null;
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
		
		return school;
	}
	public List<School> all() throws Exception {
		List<School> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(
					"select * from school");
			ResultSet rSet = statement.executeQuery();

			while (rSet.next()) {
				School school = new School();
				
				school.setCd(rSet.getString("cd"));
				school.setName(rSet.getString("name"));
				list.add(school);
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
}