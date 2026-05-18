package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 科目変更を実行するクラス
public class SubjectUpdateExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        SubjectDao sDao = new SubjectDao();

        // DBに存在確認
        Subject oldSubject = sDao.get(cd, teacher.getSchool());

        // 他画面で削除されていた場合
        if (oldSubject == null) {

            Subject subject = new Subject();
            subject.setCd(cd);
            subject.setName(name);

            request.setAttribute("subject", subject);
            request.setAttribute("error", "科目が存在していません");

            return "subject_update.jsp";
        }


        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());


        sDao.update(subject);

        return "subject_update_done.jsp";
    }
}
