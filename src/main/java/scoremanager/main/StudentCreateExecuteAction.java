package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

//学生登録を実行するクラス
public class StudentCreateExecuteAction extends Action {

    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {

        HttpSession session = request.getSession();
     // セッションからログイン中のユーザー（Teacher）を取得
        Teacher teacher = (Teacher)session.getAttribute("user");

        String entYearStr = request.getParameter("ent_year");
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String classNum = request.getParameter("classNum");

        Map<String,String> errors = new HashMap<>();

        // 入力チェック
        if (entYearStr == null || entYearStr.equals("0")) {
            errors.put("entYear", "入学年度を選択してください");
        }

        if (no == null || no.isEmpty()) {
            errors.put("no", "学生番号を入力してください");
        }

        if (name == null || name.isEmpty()) {
            errors.put("name", "氏名を入力してください");
        }

        // エラーがあれば戻る
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("student_create.jsp")
                   .forward(request, response);
            return "student_create.jsp";
        }

        StudentDao dao = new StudentDao();

        // 重複チェック
        if (dao.get(no) != null) {
            errors.put("no", "学生番号が重複しています");
            request.setAttribute("errors", errors);
            return "student_create.jsp";
        }

        // 登録
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setClassNum(classNum);
        student.setEntYear(Integer.parseInt(entYearStr));
        student.setSchool(teacher.getSchool());
        student.setIsAttend(true);

        dao.save(student);

        return "student_create_done.jsp";
    }
}