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

/**
 * 成績登録実行アクション
 */
public class TestRegistExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 1. セッションからログインユーザー
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");
        School school = teacher.getSchool();

        // 2. パラメータ取得（★修正：空チェック追加）
        String f1 = request.getParameter("f1");
        String classNum = request.getParameter("f2");
        String subjectCd = request.getParameter("f3");
        String f4 = request.getParameter("f4");

        int entYear = 0;
        int num = 0;

        // ★ここが今回のエラー対策
        if (f1 != null && !f1.isEmpty()) {
            entYear = Integer.parseInt(f1);
        } else {
            request.setAttribute("errors", "入学年度が未入力です");
            return "test_regist.jsp";
        }

        if (f4 != null && !f4.isEmpty()) {
            num = Integer.parseInt(f4);
        } else {
            request.setAttribute("errors", "回数が未入力です");
            return "test_regist.jsp";
        }

        // 3. DAO初期化
        TestDao tDao = new TestDao();
        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(subjectCd, school);

        // 4. 保存用リスト
        List<Test> testList = new ArrayList<>();

        // 学生番号取得
        String[] studentNos = request.getParameterValues("student_no_set");

        if (studentNos != null) {
            for (String no : studentNos) {

                // 点数取得
                String pointStr = request.getParameter("point_" + no);

                // ★ここは元からOK（空チェック済み）
                if (pointStr != null && !pointStr.isEmpty()) {

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
        }

        // 保存処理
        boolean isSuccess = tDao.save(testList);

        // 遷移
        if (isSuccess) {
            return "test_regist_done.jsp";
        } else {
            request.setAttribute("errors", "登録に失敗しました。");
            return "test_regist.jsp";
        }
    }
}