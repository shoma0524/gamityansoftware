package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import bean.School;
import bean.Student;

public class TestDao extends Dao {

	// ベースとなるSQL
	private String baseSql = "select * from test where ";

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
					"select * from test where student_no=? and subject_cd=? and school_cd=? and no=?");
			statement.setString(1, student.getNo());
			statement.setString(2, subject.getCd());
			statement.setString(3, school.getCd());
			statement.setInt(4, no);
			ResultSet rSet = statement.executeQuery();
			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new Subjectdao();

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
			SubjectDao subjectDao = new Subjectdao();
			while (rSet.next()) {
				Test test = new Test();

				test.setStudent(studentDao.get(rSet.getString("student_no")));
				test.setClassNum(rSet.getString("class_num"));
				test.setSubject(subjectDao.get(rSet.getString("subject_cd"), school));
				test.setSchool(school);
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
			}

		} catch (Exception e) {
			throw e;
		}

		return list;
	}

	/*
	 * filterメソッド: 入学年度, クラス番号, 科目, テストの回数, 学校を指定して、テストの一覧を取得する
	 * 戻り値: Testクラスのインスタンスが格納されたリスト
	 * */
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
		List<Test> list = new ArrayList<>();

		return list;
	}

	/*
	 * saveメソッド: テスト結果のリストの登録, 更新を行う
	 * 戻り値: boolean型 trueなら更新成功
	 * */
	public boolean save(List<Test> list) throws Exception {
		return true;
	}

	/*
	 * saveメソッド: テスト結果を1件登録, 更新を行う
	 * 戻り値: boolean型 trueなら更新成功
	 * */
	private boolean save(Test test, Connection connection) throws Exception {
		return true;
	}
}
