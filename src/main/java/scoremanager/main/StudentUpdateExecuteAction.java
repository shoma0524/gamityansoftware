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

public class StudentUpdateExecuteAction extends Action {

    @Override
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");

   
        String entYearStr = request.getParameter("entYear");
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String classNum = request.getParameter("classNum");
        boolean isAttend = request.getParameter("isAttend") != null;

        Map<String, String> errors = new HashMap<>();

        //  入力チェック
        if (name == null || name.isEmpty()) {
            errors.put("name", "氏名を入力してください");
        }

        // エラーがある場合の処理
        if (!errors.isEmpty()) {
            
            int year = LocalDate.now().getYear();
            List<Integer> entYearSet = new ArrayList<>();
            for (int i = year - 10; i <= year; i++) {
                entYearSet.add(i);
            }
            ClassNumDao cdao = new ClassNumDao();
            List<String> classList = cdao.filter(teacher.getSchool());

            
            Student student = new Student();
            student.setEntYear(Integer.parseInt(entYearStr));
            student.setNo(no);
            student.setName(name);
            student.setClassNum(classNum);
            student.setIsAttend(isAttend);

            // リクエスト属性にセット
            request.setAttribute("errors", errors);
            request.setAttribute("student", student); 
            request.setAttribute("ent_year_set", entYearSet);
            request.setAttribute("class_num_set", classList);

            return "student_update.jsp"; 
        }

        //  保存処理（エラーがない場合）
        StudentDao sdao = new StudentDao();
        Student student = new Student();
        
        student.setEntYear(Integer.parseInt(entYearStr));
        student.setNo(no);
        student.setName(name);
        student.setClassNum(classNum);
        student.setIsAttend(isAttend);
        student.setSchool(teacher.getSchool());

        sdao.save(student);

        return "student_update_done.jsp";
    }
}