package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
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
        String numStr = request.getParameter("f4"); 

        // 2. 未入力チェック
        if (entYearStr == null || entYearStr.equals("0") || 
            classNum == null || classNum.equals("0") || 
            subjectCd == null || subjectCd.equals("0")) {
            
            request.setAttribute("errors", "入学年度とクラスと科目を選択してください");
            return "TestList.action";
        }

        int entYear = Integer.parseInt(entYearStr);
        int num = 0; 
        if (numStr != null && !numStr.isEmpty()) {
            num = Integer.parseInt(numStr);
        }

        // 3. 画面表示用に科目情報を取得
        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(subjectCd, school); 

        // 4. 成績一覧を取得
        TestDao tDao = new TestDao();
        
        List<Test> list = tDao.filter(entYear, classNum, subject, num, school);

        // 5. JSPへ渡すデータをセット
        request.setAttribute("f1", entYear);
        request.setAttribute("f2", classNum);
        request.setAttribute("f3", subjectCd);
        request.setAttribute("f4", num); 
        request.setAttribute("subject", subject); 

        // 検索結果リスト
        if (list != null && list.size() > 0) {
            request.setAttribute("test_list_subject", list);
        } else {
            request.setAttribute("errors", "学生情報が存在しませんでした");
        }

        return "test_list_subject.jsp";
    }
}