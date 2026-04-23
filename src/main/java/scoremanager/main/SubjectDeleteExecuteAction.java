package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 科目削除を実行するクラス
public class SubjectDeleteExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String cd = request.getParameter("cd");

        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setSchool(teacher.getSchool());

        SubjectDao sDao = new SubjectDao();
        sDao.delete(subject);

        return "subject_delete_done.jsp";
    }
}
