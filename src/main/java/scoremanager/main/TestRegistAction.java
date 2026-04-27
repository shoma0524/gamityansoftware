package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");

        if (teacher == null) {
            return "login.jsp";
        }
        School school = teacher.getSchool();

        // 1. 各種一覧データの準備
        SubjectDao sDao = new SubjectDao();
        ClassNumDao cDao = new ClassNumDao();

        request.setAttribute("subjects", sDao.filter(school));
        request.setAttribute("class_num_set", cDao.filter(school));

        // 入学年度リスト
        int year = LocalDate.now().getYear();
        List<Integer> entYears = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYears.add(i);
        }
        request.setAttribute("ent_year_set", entYears);

        // 2. 検索処理（パラメータ f1, f2, f3, f4 がある場合）
        String entYearStr = request.getParameter("f1"); // 入学年度
        String classNum = request.getParameter("f2");  // クラス
        String subjectCd = request.getParameter("f3"); // 科目
        String numStr = request.getParameter("f4");     // 回数

        // 全ての検索条件が揃っているか確認
        if (entYearStr != null && classNum != null && subjectCd != null && numStr != null) {
            int entYear = Integer.parseInt(entYearStr);
            int num = Integer.parseInt(numStr);

            TestDao tDao = new TestDao();
            Subject subject = sDao.get(subjectCd, school);

            // 成績一覧を取得
            List<Test> tests = tDao.filter(entYear, classNum, subject, num, school);

            request.setAttribute("tests", tests);
        }

        return "test_regist.jsp";
    }
}