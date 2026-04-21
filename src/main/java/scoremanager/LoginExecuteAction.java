package scoremanager;

import bean.Teacher;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

//ログイン処理を行うクラス
public class LoginExecuteAction extends Action {

    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {

    	
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        // 未入力チェック
        if (id == null || id.isEmpty() ||
            password == null || password.isEmpty()) {

            request.setAttribute("error", "IDとパスワードを入力してください");
            return "login.jsp";
        }

        TeacherDao dao = new TeacherDao();
        Teacher teacher = dao.login(id, password);
       
        
        // 認証結果チェック
        if (teacher != null) {

            // ログイン成功
            HttpSession session = request.getSession();
            session.setAttribute("user", teacher);

            return "redirect:main/menu.jsp";
        } else {

            // ログイン失敗
            request.setAttribute("error", "ログインに失敗しました");
            request.setAttribute("id", id);

            return "login.jsp";
        }
    }
}