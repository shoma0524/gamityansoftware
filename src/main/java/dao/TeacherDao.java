package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Teacher;

public class TeacherDao extends Dao {

	// Teacherを1件取得するメソッド
	public Teacher get(String id) throws Exception {
		Teacher teacher = new Teacher();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("select * from teacher where id = ?");
			statement.setString(1, id);
			ResultSet rSet = statement.executeQuery();
			SchoolDao schoolDao = new SchoolDao();
			PermissionDao permissionDao = new PermissionDao();

			if (rSet.next()) {
				teacher.setId(rSet.getString("id"));
				teacher.setName(rSet.getString("name"));
				teacher.setPassword(rSet.getString("password"));
				teacher.setSchool(schoolDao.get(rSet.getString("school_cd")));
				teacher.setPermission(permissionDao.get(rSet.getString("permission_cd")));
			} else {
				teacher = null;
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

		return teacher;
	}

//	ログイン認証を行うメソッド
	public Teacher login(String id,String password) throws Exception {
		Teacher teacher = new Teacher();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(
					"select * from teacher where id = ? and password = ?");
			statement.setString(1, id);
			statement.setString(2, password);
			ResultSet rSet = statement.executeQuery();
			SchoolDao schoolDao = new SchoolDao();
			PermissionDao permissionDao = new PermissionDao();

			if (rSet.next()) {
				teacher.setId(rSet.getString("id"));
				teacher.setName(rSet.getString("name"));
				teacher.setPassword(rSet.getString("password"));
				teacher.setSchool(schoolDao.get(rSet.getString("school_cd")));
				teacher.setPermission(permissionDao.get(rSet.getString("permission_cd")));
			} else {
				teacher = null;
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

		return teacher;
	}

	// 指定された学校の先生一覧を取得するメソッド
		public List<Teacher> filter(School school)
				throws Exception {
			List<Teacher> list = new ArrayList<>();
			Connection connection = getConnection();
			PreparedStatement statement = null;
			PermissionDao pDao = new PermissionDao();

			try {

				statement = connection.prepareStatement(
						"select id,name,school_cd,permission_cd from teacher where school_cd=?");
				statement.setString(1,school.getCd());
				ResultSet rSet = statement.executeQuery();

				while (rSet.next()) {
					Teacher teacher = new Teacher();
					
					teacher.setId(rSet.getString("id"));
					teacher.setName(rSet.getString("name"));
					teacher.setSchool(school);
					teacher.setPermission(pDao.get(rSet.getNString("permission_cd")));
					list.add(teacher);
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
	
	// 先生情報の登録 or 更新を行うメソッド
	public boolean save(Teacher teacher) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;

		try {
			Teacher old = get(teacher.getId());
			if (old == null) {
				statement = connection.prepareStatement(
						"insert into teacher(id,password,name,school_cd,permission_cd) "
						+ "values(?,?,?,?,?) ");
				statement.setString(1, teacher.getId());
				statement.setString(2, teacher.getPassword());
				statement.setString(3, teacher.getName());
				statement.setString(4, teacher.getSchool().getCd());
				statement.setString(5, teacher.getPermission().getCd());
			} else {
				// 名前と権限の変更のみ
				// パスワードと学校コードを変更する必要があるなら、SQLを書き換えてください
				statement = connection.prepareStatement(
						"update teacher set name=?, permission_cd=? where id=? ");
				statement.setString(1, teacher.getName());
				statement.setString(2, teacher.getPermission().getCd());
				statement.setString(3, teacher.getId());
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

		return count > 0;
	}
	
	  // 先生情報の削除を行うメソッド
    public boolean delete(Teacher teacher) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            statement = connection.prepareStatement(
                "delete from teacher where id = ? and school_cd = ?");
            statement.setString(1, teacher.getId());
            statement.setString(2, teacher.getSchool().getCd());

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

        return count > 0;
    }
}