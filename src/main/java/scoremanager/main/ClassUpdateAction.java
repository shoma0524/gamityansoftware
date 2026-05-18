package scoremanager.main;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// クラス変更の画面表示をするためのクラス
public class ClassUpdateAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		// 事前条件チェック
		if (teacher == null) {
			return "redirect:../Login.action";
		}

		String class_num = request.getParameter("class_num");

		// パラメータに不正な数値が入力された場合、クラス一覧画面に戻す
		if (class_num.length() > 5) {
			return "ClassList.action";
		}

		ClassNumDao cnDao = new ClassNumDao();
		ClassNum classNum = cnDao.get(class_num, teacher.getSchool());

		request.setAttribute("classNum", classNum);

		return "class_update.jsp";
	}
}
