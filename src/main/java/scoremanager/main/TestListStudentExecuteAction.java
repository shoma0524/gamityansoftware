package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
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
        
        
        School school = teacher.getSchool();

        // 1. プルダウン用のデータ
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
        
        //参照データ
        
        //学生番号取得
        String studentNo = request.getParameter("studentNo");

        // 学生 =
        if (studentNo == null || studentNo.isEmpty()) {
            request.setAttribute("errors", "このフィールドを入力してください");
            return "test_list_student.jsp";
        }

        // ④ 検索
        TestListStudentDao tlstdao = new TestListStudentDao();
        StudentDao sdao = new StudentDao();
        Student student = sdao.get(studentNo);

        List<TestListStudent> list =
            tlstdao.filter(student);
        
        // ===== 代替フロー② =====
        if (list == null || list.size() == 0) {
            request.setAttribute("errors", "成績情報が存在しませんでした");
        } else {
            request.setAttribute("test_list_student", list);
        }

        request.setAttribute("studentNo", studentNo);
        request.setAttribute("studentName", student.getName());

        return "test_list_student.jsp";
    }
}