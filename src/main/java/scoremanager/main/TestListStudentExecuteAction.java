package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// 事前条件チェック
		if (teacher == null) {
			return "redirect:../Login.action";
		}
		School school = teacher.getSchool();

		// 1. プルダウン用のデータ
		SubjectDao sDao = new SubjectDao();
		ClassNumDao cDao = new ClassNumDao();

		request.setAttribute("subjects", sDao.filter(school));
		request.setAttribute("class_num_set", cDao.filter(school));

		// 入学年度リスト
		int year = LocalDate.now().getYear();
		List<Integer> entYears = new ArrayList<>();
		for (int i = year - 10; i <= year; i++) {
			entYears.add(i);
		}
		request.setAttribute("ent_year_set", entYears);

		// エラーメッセージ格納先の準備
		Map<String, String> errors = new HashMap<>();

		//学生番号取得
		String studentNo = request.getParameter("studentNo");
		request.setAttribute("studentNo", studentNo);

		// 入力値のチェック
		// 入力値のエラーフラグ trueならエラー発生
		boolean hasError = false;
		if (studentNo == null || studentNo.isEmpty()) {
			errors.put("student", "このフィールドを入力してください");
			hasError = true;
		} else if (studentNo.length() > 20) {
			errors.put("student", "入力上限を超えています");
			hasError = true;
		}

		// 入力値に問題がある場合、test_list.jspに遷移
		if (hasError) {
			request.setAttribute("errors", errors);
			return "test_list.jsp";
		}

		// ④ 検索
		TestListStudentDao tlstdao = new TestListStudentDao();
		StudentDao sdao = new StudentDao();
		Student student = sdao.get(studentNo);

		if (student != null) {
			List<TestListStudent> list = tlstdao.filter(student);

			request.setAttribute("test_list_student", list);
			request.setAttribute("studentName", student.getName());
		}
		return "test_list_student.jsp";
	}
}