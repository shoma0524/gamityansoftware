package scoremanager.main;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// クラス削除を実行するクラス
public class ClassDeleteExecuteAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		String class_num = request.getParameter("class_num");

		ClassNum classNum = new ClassNum();
		classNum.setClass_num(class_num);
		classNum.setSchool(teacher.getSchool());

		ClassNumDao cnDao = new ClassNumDao();
		cnDao.delete(classNum);

		return "class_delete_done.jsp";
	}
}
