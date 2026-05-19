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
        // 事前条件チェック
        if (teacher == null) {
            return "redirect:../Login.action";
        }

        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);

        // 入力値チェック
        if (name == null || name.isEmpty()) {
        	request.setAttribute("subject", subject);
        	request.setAttribute("error_name", "科目名を入力してください");

        	return "subject_update.jsp";
        } else if (name.length() > 20) {
        	request.setAttribute("subject", subject);
        	request.setAttribute("error_name", "科目名は20文字以内で入力してください");

        	return "subject_update.jsp";
        }

        SubjectDao sDao = new SubjectDao();

        // DBに存在確認
        Subject oldSubject = sDao.get(cd, teacher.getSchool());

        // 他画面で削除されていた場合
        if (oldSubject == null) {

            request.setAttribute("subject", subject);
            request.setAttribute("error", "科目が存在していません");

            return "subject_update.jsp";
        }

        subject.setSchool(teacher.getSchool());


        sDao.update(subject);

        return "subject_update_done.jsp";
    }
}
