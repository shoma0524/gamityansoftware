package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {
	
	// ベースとなるSQL
	private String baseSql = "select * from student where school_cd=? ";
	
	// 学生情報を1件取得（学籍番号で検索）
	public Student get(String no) throws Exception {
		Student student = new Student();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement("select * from student where no = ?");
			statement.setString(1, no);
			ResultSet rSet = statement.executeQuery();
			SchoolDao schoolDao = new SchoolDao();
			
			if (rSet.next()) {
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setIsAttend(rSet.getBoolean("is_attend"));
				student.setSchool(schoolDao.get(rSet.getString("school_cd")));
			} else {
				student = null;
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
		
		return student;
	}
	
	// ResultSetをStudentのListに変換する共通処理
	private List<Student> postFilter(ResultSet rSet, School school) 
			throws Exception {
		List<Student> list = new ArrayList<>();
		
		try {
			while (rSet.next()) {
				Student student = new Student();
				
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setIsAttend(rSet.getBoolean("is_attend"));
				student.setSchool(school);
				list.add(student);
			}
		} catch (SQLException  | NullPointerException e) {
				e.printStackTrace();
		}
		
		return list;
	}
	
	// 学校・入学年・クラス・在学フラグで絞り込み検索
	public List<Student> filter(School school, int entYear,
			String classNum, boolean isAttend) 
			throws Exception {
		List<Student> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;
		String condition = "and ent_year = ? and class_num = ? ";
		String order = "order by no asc ";
		
		String conditionIsAttend = "";
		if (isAttend) {
			conditionIsAttend = "and is_attend = true ";
		}
		try {
			
			statement = connection.prepareStatement(
					baseSql+condition+conditionIsAttend+order);
			statement.setString(1,school.getCd());
			statement.setInt(2, entYear);
			statement.setString(3,classNum);
			rSet = statement.executeQuery();
			
			list = postFilter(rSet,school);
			
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
	
	// 学校・入学年・在学フラグで検索（クラス指定なし）
	public List<Student> filter(School school, int entYear, boolean isAttend) 
			throws Exception {
		List<Student> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;
		String condition = "and ent_year = ? ";
		String order = "order by no asc ";
		
		String conditionIsAttend = "";
		if (isAttend) {
			conditionIsAttend = "and is_attend = true ";
		}
		try {
			
			statement = connection.prepareStatement(
					baseSql+condition+conditionIsAttend+order);
			statement.setString(1,school.getCd());
			statement.setInt(2, entYear);
			rSet = statement.executeQuery();
			
			list = postFilter(rSet,school);
			
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
	
	// 学校と在学フラグのみで検索
	public List<Student> filter(School school, boolean isAttend) 
			throws Exception {
		List<Student> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;
		String order = "order by no asc ";
		
		String conditionIsAttend = "";
		if (isAttend) {
			conditionIsAttend = "and is_attend = true ";
		}
		try {
			
			statement = connection.prepareStatement(
					baseSql+conditionIsAttend+order);
			statement.setString(1,school.getCd());
			rSet = statement.executeQuery();
			
			list = postFilter(rSet,school);
			
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
	
	// 学生情報の登録 or 更新を行うメソッド
	public boolean save(Student student)
			throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count =0;
		
		try {
			Student old = get(student.getNo());
			if (old == null) {
				statement = connection.prepareStatement(
					"insert into student(no,name,ent_year,class_num,is_attend,school_cd) "
						+"values(?,?,?,?,?,?)");
				statement.setString(1,student.getNo());
				statement.setString(2,student.getName());
				statement.setInt(3,student.getEntYear());
				statement.setString(4,student.getClassNum());
				statement.setBoolean(5,student.getIsAttend());
				statement.setString(6,student.getSchool().getCd());
			} else {
				statement = connection.prepareStatement(
					"update student set name=?, ent_year=?, class_num=?, is_attend=? where no=?");
				statement.setString(1,student.getName());
				statement.setInt(2,student.getEntYear());
				statement.setString(3,student.getClassNum());
				statement.setBoolean(4,student.getIsAttend());
				statement.setString(5,student.getNo());
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
}