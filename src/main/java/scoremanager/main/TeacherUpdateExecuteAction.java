package scoremanager.main;

import bean.Teacher;
import dao.PermissionDao;
import dao.SchoolDao;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;


//学生情報の変更を行うクラス
public class TeacherUpdateExecuteAction extends Action {

    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
    	SchoolDao sdao = new SchoolDao();
    	PermissionDao pdao = new PermissionDao();
        TeacherDao tdao = new TeacherDao();
    	
    	String id = request.getParameter("id");
        String name = request.getParameter("name");
        String school = request.getParameter("school");
        String permission = request.getParameter("permission");

        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setName(name);
        teacher.setSchool(sdao.get(school));
        teacher.setPermission(pdao.get(permission));
        

        tdao.save(teacher);

        return "teacher_update_done.jsp";
    }
}