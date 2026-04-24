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
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // 2. リクエストパラメータ（検索条件・回数）の取得
        int entYear = Integer.parseInt(request.getParameter("f1"));
        String classNum = request.getParameter("f2");
        String subjectCd = request.getParameter("f3");
        int num = Integer.parseInt(request.getParameter("f4"));

        // 3. DAOの初期化と科目情報の取得
        TestDao tDao = new TestDao();
        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(subjectCd, school);

        // 4. 保存用データの作成
        List<Test> testList = new ArrayList<>();
        
        // JSP側の学生番号を配列で取得
        // ※JSPのname属性が "student_no_set" であることを想定しています
        String[] studentNos = request.getParameterValues("student_no_set");

        if (studentNos != null) {
            for (String no : studentNos) {
                // 各学生の点数を取得 (name="point_学生番号")
                String pointStr = request.getParameter("point_" + no);
                
                // 点数が入力されている場合のみ、Testオブジェクトを作成してリストに追加
                if (pointStr != null && !pointStr.isEmpty()) {
                    int point = Integer.parseInt(pointStr);

                    // Testビーンを組み立てる
                    Test test = new Test();
                    Student student = new Student();
                    student.setNo(no); // 学生番号をセット
                    
                    test.setStudent(student);
                    test.setSubject(subject);
                    test.setSchool(school);
                    test.setNo(num);         // 回数
                    test.setPoint(point);     // 点数
                    test.setClassNum(classNum);

                    testList.add(test);
                }
            }
        }

        // TestDaoの既存メソッド save(List<Test>) を呼び出す
        boolean isSuccess = tDao.save(testList);

        // 処理結果による遷移先の決定
        if (isSuccess) {
            // 登録成功：完了画面へ
            return "test_regist_done.jsp";
        } else {
            // 登録失敗：エラーメッセージをセットして入力画面へ戻る
            request.setAttribute("errors", "登録に失敗しました。");
            return "test_regist.jsp";
        }
    }
}