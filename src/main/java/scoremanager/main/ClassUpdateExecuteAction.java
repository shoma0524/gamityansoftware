package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassUpdateExecuteAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		// 事前条件チェック
		if (teacher == null) {
			return "redirect:../Login.action";
		}

		String oldClassnum = request.getParameter("classnum");
		String newClassnum = request.getParameter("newclassnum");
		ClassNumDao cnDao = new ClassNumDao();
		// エラーメッセージ保存先
		Map<String, String> errors = new HashMap<>();
		// エラーフラグ true ならエラー発生
		boolean hasError = false;

		// 変更前のデータを格納
		ClassNum classNum = new ClassNum();
		classNum.setSchool(teacher.getSchool());
		classNum.setClass_num(oldClassnum);

		// 入力値チェック
		if (newClassnum.length() > 5) {
			errors.put("num", "クラス番号は5文字以内で入力してください");
			hasError = true;
		} else if (cnDao.get(newClassnum, teacher.getSchool()) != null) {
			errors.put("num", "クラス番号が重複しています");
			hasError = true;
		}

		// 入力値でエラー発生
		if (hasError) {
			// 変更前のデータを返却
			request.setAttribute("classNum", classNum);
			request.setAttribute("errors", errors);
			return "class_update.jsp";
		}

		// 正常な値が入力されていれば更新される
		cnDao.save(classNum, newClassnum);
		return "class_update_done.jsp";
	}
}