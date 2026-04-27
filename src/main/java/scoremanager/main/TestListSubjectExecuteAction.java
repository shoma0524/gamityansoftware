package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // 1. パラメータ取得
        String entYearStr = request.getParameter("f1");
        String classNum = request.getParameter("f2");
        String subjectCd = request.getParameter("f3");

        // 2. 共通データの準備 (プルダウン用)
        SubjectDao sDao = new SubjectDao();
        ClassNumDao cDao = new ClassNumDao();
        
        List<Integer> entYearSet = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear; i++) {
            entYearSet.add(i);
        }
        
        List<String> classNumSet = cDao.filter(school);
        List<Subject> subjects = sDao.filter(school);

        // JSPに選択肢をセット
        request.setAttribute("ent_year_set", entYearSet);
        request.setAttribute("class_num_set", classNumSet);
        request.setAttribute("subjects", subjects);

        // 3. バリデーション
        if (entYearStr == null || entYearStr.equals("0") || 
            classNum == null || classNum.equals("0") || 
            subjectCd == null || subjectCd.equals("0")) {

            request.setAttribute("errors", "入学年度とクラスと科目を選択してください");
            return "test_list.jsp"; 
        }

        int entYear = Integer.parseInt(entYearStr);

        // 4. データ取得
        Subject subject = sDao.get(subjectCd, school);
        TestListSubjectDao tLiSubDao = new TestListSubjectDao();
        List<TestListSubject> list = tLiSubDao.filter(entYear, classNum, subject, school);

        // 5. 検索条件と結果をセット
        request.setAttribute("f1", entYear);
        request.setAttribute("f2", classNum);
        request.setAttribute("f3", subjectCd);
        request.setAttribute("subject", subject);

        if (list != null && !list.isEmpty()) {
            request.setAttribute("test_list_subject", list);
        } else {
            request.setAttribute("errors", "学生情報が存在しませんでした");
        }

        return "test_list_subject.jsp";
    }
}