package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.School;
import bean.Teacher;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションから学校情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // リクエストパラメータ（検索条件）の取得
        int entYear = Integer.parseInt(request.getParameter("ent_year"));
        String classNum = request.getParameter("class_num");
        String subjectCd = request.getParameter("subject_cd");

        //  画面から入力された「点数」をMapなどで取得

        String[] studentNos = request.getParameterValues("student_no_set[]");
        Map<String, Integer> points = new HashMap<>();
        
        for (String no : studentNos) {
            String pointStr = request.getParameter("point_" + no);
            if (pointStr != null && !pointStr.isEmpty()) {
                points.put(no, Integer.parseInt(pointStr));
            }
        }

        // DAOを使ってDBへ保存
        TestDao tDao = new TestDao();
        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(subjectCd, school);

        // シーケンス図にある保存処理の実行
        boolean isSuccess = tDao.save(points, school, subject, entYear, classNum);

        // 5. 結果に応じた画面遷移
        if (isSuccess) {
            // 登録完了画面、または元の画面にリダイレクト
            request.getRequestDispatcher("test_regist_done.jsp").forward(request, response);
        } else {
            request.setAttribute("errors", "登録に失敗しました。");
            request.getRequestDispatcher("test_regist.jsp").forward(request, response);
        }
    }
}