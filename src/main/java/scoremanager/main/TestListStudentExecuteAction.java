package scoremanager.main;

import java.util.List;

import bean.Teacher;
import bean.Test;
import bean.TestListStudent;
import dao.StudentDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 事前条件チェック
        if (teacher == null) {
            return "login.jsp";
        }

        //学生番号取得
        String studentNo = request.getParameter("studentNo");

        // 学生 =
        if (studentNo == null || studentNo.isEmpty()) {
            request.setAttribute("errors", "このフィールドを入力してください");
            return "test_list_student.jsp";
        }

        // ④ 検索
        TestListStudentDao tlsdao = new TestListStudentDao();
        StudentDao sdao = new StudentDao();

        List<TestListStudent> list =
            tlsdao.filter(sdao.get(studentNo));

        // ===== 代替フロー② =====
        if (list == null || list.size() == 0) {
            request.setAttribute("errors", "成績情報が存在しませんでした");
        } else {
            request.setAttribute("test_list_student", list);
        }

        request.setAttribute("studentNo", studentNo);

        return "test_list_student.jsp";
    }
}