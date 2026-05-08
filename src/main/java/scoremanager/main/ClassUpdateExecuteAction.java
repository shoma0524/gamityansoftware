package scoremanager.main;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassUpdateExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String oldClassnum = request.getParameter("classnum");
        String newClassnum = request.getParameter("newclassnum");

        ClassNum classNum = new ClassNum();
        classNum.setSchool(teacher.getSchool());
        classNum.setClass_num(oldClassnum);

        ClassNumDao cnDao = new ClassNumDao();
        cnDao.save(classNum,newClassnum);

        return "class_update_done.jsp";
    }
}