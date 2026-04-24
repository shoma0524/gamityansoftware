package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

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


        return "test_list.jsp";
	}
}
