package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

//科目一覧を出すクラス
public class SubjectListAction extends Action {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		//セッションからログイン中のユーザー(Teacher)を取得
		Teacher teacher = (Teacher) session.getAttribute("user");
		// 事前条件チェック
		if (teacher == null) {
			return "redirect:../Login.action";
		}

		if (!"002".equals(teacher.getPermission().getCd()) && !"003".equals(teacher.getPermission().getCd())) {
			request.setAttribute("error", "permission");
			return "/error.jsp";
		}

		SubjectDao sDao = new SubjectDao();

		List<Subject> subjects = sDao.filter(teacher.getSchool());

		request.setAttribute("subjects", subjects);

		return "subject_list.jsp";

	}
}
