package scoremanager.main;

import java.util.List;

import bean.Teacher;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

//クラス一覧を出すjava
public class TeacherListAction extends Action {
    
    @Override
    public String execute(
        HttpServletRequest request,HttpServletResponse response
    )throws Exception{
        HttpSession session=request.getSession();

        //セッションからログイン中のユーザー(Teacher)を取得
        Teacher teacher = (Teacher)session.getAttribute("user");

        if (!"003".equals(teacher.getPermission().getCd())) {
        	request.setAttribute("error", "permission");
            return "/error.jsp";
        }        
        
        TeacherDao dao=new TeacherDao();

        List<Teacher> teachers= dao.filter(teacher.getSchool());

        request.setAttribute("teachers",teachers);

        return "teacher_list.jsp";
    }
}
