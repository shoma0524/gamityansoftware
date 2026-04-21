package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;


//学生登録画面を表示するためのクラス
public class StudentCreateAction extends Action {

    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {

        HttpSession session = request.getSession();
     // セッションからログイン中のユーザー（Teacher）を取得
        Teacher teacher = (Teacher)session.getAttribute("user");

        // 入学年度リスト
        int year = LocalDate.now().getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
        	entYearSet.add(i);
        }

        // クラス一覧
        ClassNumDao dao = new ClassNumDao();
        List<String> classList = dao.filter(teacher.getSchool());

        request.setAttribute("ent_year_set", entYearSet);
        request.setAttribute("class_num_set", classList);

        return "student_create.jsp";
    }
}