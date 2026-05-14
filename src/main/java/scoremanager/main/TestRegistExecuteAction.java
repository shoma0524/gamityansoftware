package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		School school = teacher.getSchool();

		// ===== パラメータ取得 =====
		String entYearStr = request.getParameter("f1");
		String classNum = request.getParameter("f2");
		String subjectCd = request.getParameter("f3");
		String countStr = request.getParameter("f4");

		Map<String, String> errors = new HashMap<>();

		// 入力チェック trueなら入力されている
		boolean isEntered = true;

		// ===== 必須チェック =====
		if (entYearStr == null || entYearStr.isBlank()) {
			isEntered = false;
		}
		if (classNum == null || classNum.isBlank()) {
			isEntered = false;
		}
		if (subjectCd == null || subjectCd.isBlank()) {
			isEntered = false;
		}
		if (countStr == null || countStr.isBlank()) {
			isEntered = false;
		}

		// 1つでも未入力項目があれば、エラーメッセージを返す
		if (!isEntered) {
			System.out.println("未入力");
			errors.put("search", "入学年度とクラスと科目と回数を選択してください");
			request.setAttribute("errors", errors);
			request.setAttribute("f1", entYearStr);
			request.setAttribute("f2", classNum);
			request.setAttribute("f3", subjectCd);
			request.setAttribute("f4", countStr);
			return "test_regist.jsp";
		}

		int entYear = Integer.parseInt(entYearStr);
		int count = Integer.parseInt(countStr);

		// ===== DAO =====
		TestDao tDao = new TestDao();
		SubjectDao sDao = new SubjectDao();
		Subject subject = sDao.get(subjectCd, school);

		List<Test> testList = new ArrayList<>();

		String[] studentNos = request.getParameterValues("student_no_set");

		// 点数入力値のエラーフラグ trueならエラー発生
		boolean hasError = false;

		if (studentNos != null) {
			for (String no : studentNos) {

				String pointStr = request.getParameter("point_" + no);

				// ★ 未入力チェック（超重要）
				if (pointStr == null || pointStr.isEmpty()) {
					continue;
				}

				// 入力値に半角数字以外が入力された場合(マイナス記号は除く)
				if (!pointStr.matches("^[0-9]+$") && !pointStr.matches("^-[0-9]+$")) {
					errors.put(no, "半角数字で入力してください");
					hasError = true;
					continue;
				}

				int point = Integer.parseInt(pointStr);
				if (point < 0 || 100 < point) {
					errors.put(no, "0～100の範囲で入力してください");
					hasError = true;
					continue;
				}
				Test test = new Test();
				Student student = new Student();
				student.setNo(no);

				test.setStudent(student);
				test.setSubject(subject);
				test.setSchool(school);
				test.setNo(count);
				test.setPoint(point);
				test.setClassNum(classNum);

				testList.add(test);
			}
		}

		// ===== エラー時 =====
		if (hasError) {
			request.setAttribute("errors", errors);
		    return "TestRegist.action";
		}

		// ===== 保存 =====
		tDao.save(testList);
		return "test_regist_done.jsp";
	}
}