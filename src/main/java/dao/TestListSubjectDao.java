package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

	// ベースとなるSQL
	private String baseSql = "select * from ";

	/*
	 * postFilterメソッド: フィルター後のリストへの格納処理を行う
	 * 戻り値: TestListSubjectクラスのインスタンスが格納されたリスト
	 * */
	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		List<TestListSubject> list = new ArrayList<>();

		try {
			while (rSet.next()) {
				TestListSubject testLiSub = new TestListSubject();

				testLiSub.setEntYear(rSet.getInt("ent_year"));
				testLiSub.setStudentNo(rSet.getString("no"));
				testLiSub.setStudentName(rSet.getString("name"));
				testLiSub.setClassNum(rSet.getString("class_num"));

				Map<Integer, Integer> points = new HashMap<>();
				Integer test1Point = (Integer)rSet.getObject("test1_point");
				Integer test2Point = (Integer)rSet.getObject("test2_point");

				// 2回のテストの両方の得点がnullならリストに追加しない
				if (test1Point == null && test2Point == null) {
					continue;
				}

				points.put(1, test1Point);
				points.put(2, test2Point);
				testLiSub.setPoints(points);

				list.add(testLiSub);
			}
		} catch (SQLException  | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

	/*
	 * filterメソッド: 入学年度, クラス番号, 科目, 学校を指定して科目別のテストの一覧を取得する
	 * 戻り値: TestListSubjectクラスのインスタンスが格納されたリスト 並び順は学生番号の昇順
	 * */
	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
		List<TestListSubject> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;
		String order = "order by student.no asc ";

		try {
			statement = connection.prepareStatement(baseSql
					+ "(select * from student where ent_year=? and class_num=?) "
					+ "left join (select student_no as test1_student_no, subject_cd as test1_subject_cd, school_cd as test1_school_cd, no as test1_no, point as test1_point from test where subject_cd=? and school_cd=? and no=1) as test1 "
					+ "on no=test1_student_no "
					+ "left join (select student_no as test2_student_no, subject_cd as test2_subject_cd, school_cd as test2_school_cd, no as test2_no, point as test2_point from test where subject_cd=? and school_cd=? and no=2) as test2 "
					+ "on no=test2_student_no "
					+ order);
			statement.setInt(1, entYear);
			statement.setString(2, classNum);
			statement.setString(3, subject.getCd());
			statement.setString(4, school.getCd());
			statement.setString(5, subject.getCd());
			statement.setString(6, school.getCd());
			rSet = statement.executeQuery();

			list = postFilter(rSet);
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {

				}
			}
		}

		return list;
	}
}
