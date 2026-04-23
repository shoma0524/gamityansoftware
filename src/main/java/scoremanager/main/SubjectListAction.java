package scoremanager.main;

import tool.Action;
import bean.Teacher;
import dao.SubjectDao;

import java.util.List;

import bean.Subject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//科目一覧を出すクラス
public class SubjectListAction extends Action {

    @Override
    public String execute(
        HttpServletRequest request,HttpServletResponse response
    )throws Exception{
        HttpSession session=request.getSession();

        //セッションからログイン中のユーザー(Teacher)を取得
        Teacher teacher= (Teacher)session.getAttribute("user");

        SubjectDao sDao=new SubjectDao();

        List<Subject> subjects=sDao.filter(teacher.getSchool());

        request.setAttribute("subjects",subjects);

        return "subject_list.jsp";

    }
}
