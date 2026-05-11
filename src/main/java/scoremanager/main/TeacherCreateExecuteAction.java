package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Permission;
import bean.Teacher;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

//学生登録を実行するクラス
public class TeacherCreateExecuteAction extends Action {

	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		// セッションからログイン中のユーザー（Teacher）を取得
		Teacher teacher = (Teacher) session.getAttribute("user");

		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");

		Map<String, String> errors = new HashMap<>();

		// 入力チェック
		if (id.length() > 10) {
			errors.put("id", "教員IDは10文字以内で入力してください");
		}

		if (name.length() > 10) {
			errors.put("name", "名前は10文字以内で入力してください");
		}

		if (password1.length() < 8 || password1.length() > 30) {
			errors.put("password_1", "パスワードは8文字以上30文字以内の半角英数字で入力してください");
		}

		if (!password1.equals(password2)) {
			errors.put("password_2", "入力されたパスワードが確認用と異なります");
		}

		// エラーがあれば戻る
		if (!errors.isEmpty()) {
			request.setAttribute("id", id);
			request.setAttribute("name", name);
			request.setAttribute("password1", password1);
			request.setAttribute("password2", password2);
			request.setAttribute("errors", errors);
			return "teacher_create.jsp";
		}

		TeacherDao teacherDao = new TeacherDao();

		// 重複チェック
		if (teacherDao.get(id) != null) {
			errors.put("id", "教員番号が重複しています");
			request.setAttribute("id", id);
			request.setAttribute("name", name);
			request.setAttribute("password1", password1);
			request.setAttribute("password2", password2);
			request.setAttribute("errors", errors);
			return "teacher_create.jsp";
		}

		// permission(権限)には初期値として "001" (最低ランクの権限)を設定
		Permission permission = new Permission();
		permission.setCd("001");

		// 登録
		Teacher newTeacher = new Teacher();
		newTeacher.setId(id);
		newTeacher.setName(name);
		newTeacher.setPassword(password1);
		newTeacher.setSchool(teacher.getSchool());
		newTeacher.setPermission(permission);
		teacherDao.save(newTeacher);

		return "teacher_create_done.jsp";
	}
}