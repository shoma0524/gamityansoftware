package scoremanager.main;

import java.util.List;

import bean.Permission;
import bean.School;
import bean.Teacher;
import dao.PermissionDao;
import dao.SchoolDao;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

// 教員情報の変更の画面表示をするためのクラス
public class TeacherUpdateAction extends Action {

	public String execute(
			HttpServletRequest request,HttpServletResponse response
	) throws Exception {

		String id = request.getParameter("id");

		TeacherDao tDao = new TeacherDao();
		PermissionDao pDao = new PermissionDao();
		SchoolDao sDao = new SchoolDao();
		Teacher teacher = tDao.get(id);
		List<Permission> plist = pDao.all();
		List<School> slist = sDao.all();


		request.setAttribute("permission_set", plist);
		request.setAttribute("school_set", slist);
		request.setAttribute("teacher",teacher);

		return "teacher_update.jsp";
	}
}