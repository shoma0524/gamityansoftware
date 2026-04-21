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

//学生一覧を出すクラス
public class StudentListAction extends Action {
	
	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response
		)throws Exception{
			HttpSession session = request.getSession();
			// セッションからログイン中のユーザー（Teacher）を取得
			Teacher teacher = (Teacher)session.getAttribute("user");
			
			String entYearStr = "";
			String classNum = "";
			String isAttendStr = "";
			int entYear = 0;
			boolean isAttend = false;
			List<Student> students = null;
			LocalDate todaysDate = LocalDate.now();
			int year = todaysDate.getYear();
			StudentDao sDao = new StudentDao();
			ClassNumDao cNumDao = new ClassNumDao();
			Map<String, String> errors = new HashMap<>();
			
			entYearStr = request.getParameter("f1");
			classNum = request.getParameter("f2");
			isAttendStr = request.getParameter("f3");
			
			// チェックボックスは値が来たらtrue
			if (isAttendStr != null) {
				isAttend = true;
			}
			
			if (entYearStr != null) {
				entYear = Integer.parseInt(entYearStr);
			}
			List<Integer> entYearSet = new ArrayList<>();
			for (int i = year - 10; i < year + 1; i++) {
				entYearSet.add(i);
			}
			
			List<String> list = cNumDao.filter(teacher.getSchool());
			
			// 検索条件による分岐
			
//			年度あり & クラスあり
			if (entYear != 0 && !classNum.equals("0")) {
				students = sDao.filter(
						teacher.getSchool(),entYear,classNum, isAttend);
			} 
//			年度あり & クラスなし
			else if (entYear != 0 && classNum.equals("0")) {
				students = sDao.filter(
						teacher.getSchool(),entYear, isAttend);
			} 
//			年度なし & クラスなし
			else if (entYear == 0 && classNum == null || 
						entYear == 0 && classNum.equals("0")) {
				students = sDao.filter(
						teacher.getSchool(), isAttend);
			} 
//			エラー
			else {
				errors.put("f1","クラスを指定する場合は入学年度も指定してください");
				request.setAttribute("errors",errors);
				students = sDao.filter(teacher.getSchool(), isAttend);
			}
			
			request.setAttribute("f1", entYear);
			request.setAttribute("f2",classNum);
			request.setAttribute("f3",isAttendStr);
			
			request.setAttribute("students",students);
			request.setAttribute("class_num_set", list);
			request.setAttribute("ent_year_set",entYearSet);
			
			return "student_list.jsp";
			
	}
}