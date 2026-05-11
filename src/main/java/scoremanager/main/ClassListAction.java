package scoremanager.main;

import java.util.List;

import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

//クラス一覧を出すjava
public class ClassListAction extends Action {
    
    @Override
    public String execute(
        HttpServletRequest request,HttpServletResponse response
    )throws Exception{
        HttpSession session=request.getSession();

        //セッションからログイン中のユーザー(Teacher)を取得
        Teacher teacher = (Teacher)session.getAttribute("user");
        
        if (!"silver".equals(teacher.getPermission().getName()) && !"gold".equals(teacher.getPermission().getName())) {
        	request.setAttribute("error", "permission");
            return "/error.jsp";
        }

        ClassNumDao cnDao=new ClassNumDao();

        List<String> classNums=cnDao.filter(teacher.getSchool());

        request.setAttribute("classNums",classNums);

        return "class_list.jsp";
    }
}
