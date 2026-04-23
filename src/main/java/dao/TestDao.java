package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

	// ベースとなるSQL
	private String baseSql = "select * from test left join student on test.student_no=student.no ";

	/*
	 * getメソッド: 学生, 科目, 学校, テストの回数を指定して、テストの情報を1件取得する
	 * 戻り値: Testクラスのインスタンス 存在しない場合はnull
	 * */
	public Test get(Student student, Subject subject, School school, int no) throws Exception {
		Test test = new Test();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(
					"select * from test where student_no=? and subject_cd=? and school_cd=? and no=? ");
			statement.setString(1, student.getNo());
			statement.setString(2, subject.getCd());
			statement.setString(3, school.getCd());
			statement.setInt(4, no);
			ResultSet rSet = statement.executeQuery();
			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new SubjectDao();

			if (rSet.next()) {
				test.setStudent(studentDao.get(rSet.getString("student_no")));
				test.setClassNum(rSet.getString("class_num"));
				test.setSubject(subjectDao.get(rSet.getString("subject_cd"), school));
				test.setSchool(school);
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
			} else {
				test = null;
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
				} catch(SQLException sqle) {
					throw sqle;
				}
			}
		}

		return test;
	}

	/*
	 * postFilterメソッド: フィルター後のリストへの格納処理を行う
	 * 戻り値: Testクラスのインスタンスが格納されたリスト
	 * */
	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
		List<Test> list = new ArrayList<>();

		try {
			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new SubjectDao();
			while (rSet.next()) {
				Test test = new Test();

				test.setStudent(studentDao.get(rSet.getString("student_no")));
				test.setClassNum(rSet.getString("class_num"));
				test.setSubject(subjectDao.get(rSet.getString("subject_cd"), school));
				test.setSchool(school);
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				list.add(test);
			}

		} catch (SQLException  | NullPointerException e) {
			e.printStackTrace();;
		}

		return list;
	}

	/*
	 * filterメソッド: 入学年度, クラス番号, 科目, テストの回数, 学校を指定して、テストの一覧を取得する
	 * 戻り値: Testクラスのインスタンスが格納されたリスト
	 * */
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
		List<Test> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;
		String order = "order by student_no asc ";

		try {
			statement = connection.prepareStatement(
					baseSql + "where ent_year=? and test.class_num=? and subject_cd=? and test.no=? and school_cd=?" + order);
			statement.setInt(1, entYear);
			statement.setString(2, classNum);
			statement.setString(3, subject.getCd());
			statement.setInt(4, num);
			statement.setString(5, school.getCd());
			rSet = statement.executeQuery();

			list = postFilter(rSet, school);
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

	/*
	 * saveメソッド: テスト結果のリストの登録・更新を行う
	 * 戻り値: boolean型 trueなら登録・更新成功
	 * */
	public boolean save(List<Test> list) throws Exception {
		Connection connection = getConnection();

		try {
			connection.setAutoCommit(false);

			for (Test test : list) {
				// isSuccess: 登録・更新結果 trueなら成功
				boolean isSuccess =  save(test, connection);
				if (isSuccess == false) {
					// 登録・更新に失敗した場合
					return false;
				}
			}

			// すべての処理が正常に登録・更新された場合
			connection.commit();
			return true;
		} catch (Exception e) {
			throw e;
		} finally {
			if (connection != null) {
				try {
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
	}

	/*
	 * saveメソッド: テスト結果を1件登録, 更新を行う
	 * 戻り値: boolean型 trueなら更新成功
	 * */
	private boolean save(Test test, Connection connection) throws Exception {
		PreparedStatement statement = null;
		int count = 0;

		try {
			Test old = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());
			if (old == null) {
				statement = connection.prepareStatement(
						"insert into test(student_no,subject_cd,school_cd,no,point,class_num) values(?,?,?,?,?,?)");
				statement.setString(1, test.getStudent().getNo());
				statement.setString(2, test.getSubject().getCd());
				statement.setString(3, test.getSchool().getCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());
			} else {
				statement = connection.prepareStatement(
						"update test set point=? where student_no=? and subject_cd=? and school_cd=? and no=?");
				statement.setInt(1, test.getPoint());
				statement.setString(2, test.getStudent().getNo());
				statement.setString(3, test.getSubject().getCd());
				statement.setString(4, test.getSchool().getCd());
				statement.setInt(5, test.getNo());
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
		}

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
}
