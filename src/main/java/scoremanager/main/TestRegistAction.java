package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

        // 事前条件チェック
        if (teacher == null) {
            return "redirect:../Login.action";
        }
		School school = teacher.getSchool();

		// 1. 各種一覧データの準備
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

		// 2. パラメータ取得
		String entYearStr = request.getParameter("f1");
		String classNum = request.getParameter("f2");
		String subjectCd = request.getParameter("f3");
		String numStr = request.getParameter("f4");

		// エラーメッセージ格納先
		Map<String, String> errors = new HashMap<>();

		// ★★★ ここが超重要（値をJSPに戻す）★★★
		request.setAttribute("f1", entYearStr);
		request.setAttribute("f2", classNum);
		request.setAttribute("f3", subjectCd);
		request.setAttribute("f4", numStr);

		// 3. 検索処理（空チェック付き）
		// 検索条件がすべて空の場合
		if (entYearStr == null && classNum == null && subjectCd == null && numStr == null) {
			return "test_regist.jsp";
		}
		// 検索条件がすべて入力されている場合
		else if (entYearStr != null && !entYearStr.isEmpty()
				&& classNum != null && !classNum.isEmpty()
				&& subjectCd != null && !subjectCd.isEmpty()
				&& numStr != null && !numStr.isEmpty()) {

			int entYear = Integer.parseInt(entYearStr);
			int num = Integer.parseInt(numStr);

			TestDao tDao = new TestDao();
			Subject subject = sDao.get(subjectCd, school);

			// 成績一覧取得
			List<Test> tests = tDao.filter(entYear, classNum, subject, num, school);

			request.setAttribute("tests", tests);
			request.setAttribute("subject", subject); // ★科目表示用（これも重要）
		} else {
			// どれか１つでも未入力ならエラーメッセージを表示
			errors.put("search", "入学年度とクラスと科目と回数を選択してください");
			request.setAttribute("errors", errors);
		}

		return "test_regist.jsp";
	}
}