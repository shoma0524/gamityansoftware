package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");
        School school = teacher.getSchool();

        // ===== パラメータ取得 =====
        String f1 = request.getParameter("f1");
        String classNum = request.getParameter("f2");
        String subjectCd = request.getParameter("f3");
        String f4 = request.getParameter("f4");

        // ===== 必須チェック =====
        if (f1 == null || f1.isEmpty()) {
            request.setAttribute("errors", "入学年度が未入力です");
            return "test_regist.jsp";
        }

        if (f4 == null || f4.isEmpty()) {
            request.setAttribute("errors", "回数が未入力です");
            return "test_regist.jsp";
        }

        int entYear = Integer.parseInt(f1);
        int num = Integer.parseInt(f4);

        // ===== DAO =====
        TestDao tDao = new TestDao();
        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(subjectCd, school);

        List<Test> testList = new ArrayList<>();

        String[] studentNos = request.getParameterValues("student_no_set");

        boolean hasError = false;

        if (studentNos != null) {
            for (String no : studentNos) {

                String pointStr = request.getParameter("point_" + no);

                // ★ 未入力チェック（超重要）
                if (pointStr == null || pointStr.isEmpty()) {
                    break;
                }

                int point = Integer.parseInt(pointStr);

                Test test = new Test();
                Student student = new Student();
                student.setNo(no);

                test.setStudent(student);
                test.setSubject(subject);
                test.setSchool(school);
                test.setNo(num);
                test.setPoint(point);
                test.setClassNum(classNum);

                testList.add(test);
            }
        }

        // ===== エラー時 =====
        /*
        if (hasError) {
            request.setAttribute("errors", "未入力の点数があります");
            return "test_regist.jsp";
        }
        */

        // ===== 保存 =====
        boolean isSuccess = tDao.save(testList);

        if (isSuccess) {
            return "test_regist_done.jsp";
        } else {
            request.setAttribute("errors", "登録に失敗しました。");
            return "test_regist.jsp";
        }
    }
}