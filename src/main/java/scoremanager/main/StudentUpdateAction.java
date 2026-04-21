package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

//学生情報の変更の画面表示をするためのクラス
public class StudentUpdateAction extends Action {
	
	public String execute(
			HttpServletRequest request,HttpServletResponse response
	) throws Exception {
		
		
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		String no = request.getParameter("no");
		
		ClassNumDao cNumDao = new ClassNumDao();
		StudentDao sdao = new StudentDao();
		Student student = sdao.get(no);
		List<String> list = cNumDao.filter(teacher.getSchool());
		
		request.setAttribute("class_num_set", list);
		request.setAttribute("student", student);
		
		return "student_update.jsp";
	}
}