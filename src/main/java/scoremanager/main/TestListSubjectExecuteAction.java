package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// 事前条件チェック
		if (teacher == null) {
			return "redirect:../Login.action";
		}
		School school = teacher.getSchool();

		// 1. パラメータ取得
		String entYearStr = request.getParameter("f1");
		String classNum = request.getParameter("f2");
		String subjectCd = request.getParameter("f3");

		// 検索条件をセット
		request.setAttribute("f1", entYearStr);
		request.setAttribute("f2", classNum);
		request.setAttribute("f3", subjectCd);

		// 2. 共通データの準備 (プルダウン用)
		SubjectDao sDao = new SubjectDao();
		ClassNumDao cDao = new ClassNumDao();
		List<Integer> entYearSet = new ArrayList<>();
		int currentYear = LocalDate.now().getYear();
		for (int i = currentYear - 10; i <= currentYear; i++) {
			entYearSet.add(i);
		}
		List<String> classNumSet = cDao.filter(school);
		List<Subject> subjects = sDao.filter(school);

		// エラーメッセージ格納先の準備
		Map<String, String> errors = new HashMap<>();

		// JSPに選択肢をセット
		request.setAttribute("ent_year_set", entYearSet);
		request.setAttribute("class_num_set", classNumSet);
		request.setAttribute("subjects", subjects);

		// 3. バリデーション
		if (entYearStr == null || entYearStr.isEmpty() ||
				classNum == null || classNum.isEmpty() ||
				subjectCd == null || subjectCd.isEmpty()) {
			// 検索条件のいずれかが未入力だった場合
			// エラーメッセージをMapに格納、キーは "subject"
			errors.put("subject", "入学年度とクラスと科目を選択してください");
			request.setAttribute("errors", errors);
			return "test_list.jsp";
		}

		int entYear = Integer.parseInt(entYearStr);

		// 4. データ取得
		Subject subject = sDao.get(subjectCd, school);
		if (subject == null) {
			errors.put("subject", "科目が存在しませんでした");
			request.setAttribute("errors", errors);
			return "test_list.jsp";
			}

		TestListSubjectDao tLiSubDao = new TestListSubjectDao();
		List<TestListSubject> list = tLiSubDao.filter(entYear, classNum, subject, school);

		// 5. 結果をセット
		request.setAttribute("subject", subject);
		request.setAttribute("test_list_subject", list);

		return "test_list_subject.jsp";
	}
}