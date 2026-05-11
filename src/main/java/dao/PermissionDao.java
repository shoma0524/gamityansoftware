package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Permission;

public class PermissionDao extends Dao {
	// permission(権限)を1件取得するメソッド
	public Permission get(String cd) throws Exception {
		Permission permission = new Permission();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(
					"select * from permission where cd=? ");
			statement.setString(1, cd);
			ResultSet rSet = statement.executeQuery();

			if (rSet.next()) {
				permission.setCd(rSet.getString("cd"));
				permission.setName(rSet.getString("name"));
			} else {
				permission = null;
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

		return permission;
	}
	
	public List<Permission> all() throws Exception {
		List<Permission> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(
					"select * from permission");
			ResultSet rSet = statement.executeQuery();

			while (rSet.next()) {
				Permission permission = new Permission();
				
				permission.setCd(rSet.getString("cd"));
				permission.setName(rSet.getString("name"));
				list.add(permission);
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
