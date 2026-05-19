package scoremanager.main;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// クラス削除の確認画面を表示するためのクラス
public class ClassDeleteAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		// 事前条件チェック
        if (teacher == null) {
            return "redirect:../Login.action";
        }

		String class_num = request.getParameter("class_num");

		ClassNumDao cnDao = new ClassNumDao();
		ClassNum classNum = cnDao.get(class_num, teacher.getSchool());

		request.setAttribute("classNum", classNum);

		return "class_delete.jsp";
	}

}
