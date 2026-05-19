package scoremanager.main;
 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
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
       
        Teacher teacher = (Teacher)session.getAttribute("user");
 
        String entYearStr = request.getParameter("ent_year");
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String classNum = request.getParameter("classNum");
 
        Map<String,String> errors = new HashMap<>();
 
        // エラーが起きた場合に送る入学年度とクラス
        int year = LocalDate.now().getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }
 
        // クラス一覧
        ClassNumDao cdao = new ClassNumDao();
        List<String> classList = cdao.filter(teacher.getSchool());
 
        // 入力チェック
        if (entYearStr == null || entYearStr.equals("0")) {
            errors.put("entYear", "入学年度を選択してください");
        }
 
        if (no == null || no.isEmpty()) {
            errors.put("no", "学生番号を入力してください");
        }
 
        if (name == null || name.isEmpty()) {
            errors.put("name", "氏名を入力してください");
        } else if (name.length() > 10) {
            errors.put("name", "10文字以内で入力してください");
        }
 
      
        Student student = new Student();
        if (entYearStr != null && !entYearStr.equals("0") && !entYearStr.isEmpty()) {
            student.setEntYear(Integer.parseInt(entYearStr));
        }
        student.setNo(no);
        student.setName(name);
        student.setClassNum(classNum);

        // エラーがあれば戻る
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("student", student); // ★追加：入力された情報を保持
            request.setAttribute("ent_year_set", entYearSet);
            request.setAttribute("class_num_set", classList);
            return "student_create.jsp";
        }
 
        StudentDao sdao = new StudentDao();
 
        // 重複チェック
        if (sdao.get(no) != null) {
            errors.put("no", "学生番号が重複しています");
            request.setAttribute("errors", errors);
            request.setAttribute("student", student); // ★追加：重複エラー時も情報を保持
            request.setAttribute("ent_year_set", entYearSet);
            request.setAttribute("class_num_set", classList);
            return "student_create.jsp";
        }
 
        // 登録
        student.setSchool(teacher.getSchool());
        student.setIsAttend(true);
 
        sdao.save(student);
 
        return "student_create_done.jsp";
    }
}