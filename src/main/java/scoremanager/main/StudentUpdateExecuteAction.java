package scoremanager.main;

import bean.Student;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;


//学生情報の変更を行うクラス
public class StudentUpdateExecuteAction extends Action {

    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
    	
    	String entYearStr = "";
    	entYearStr = request.getParameter("entYear");
    	int entYear = Integer.parseInt(entYearStr);
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String classNum = request.getParameter("classNum");

        // checkboxはチェックなしだとnullになる
        boolean isAttend = request.getParameter("isAttend") != null;

        Student student = new Student();
        student.setEntYear(entYear);
        student.setNo(no);
        student.setName(name);
        student.setClassNum(classNum);
        student.setIsAttend(isAttend);

        StudentDao dao = new StudentDao();
        dao.save(student);

        return "student_update_done.jsp";
    }
}