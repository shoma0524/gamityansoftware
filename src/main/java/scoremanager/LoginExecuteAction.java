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
       
        // 半角英数字チェック
        if (!password.matches("^[a-zA-Z0-9]+$") || !id.matches("^[a-zA-Z0-9]+$")) {

            request.setAttribute(
                "error",
                "半角英数字のみ入力してください"
            );

            return "login.jsp";
        }
        
        //文字数チェック
        if(id.length() > 10 || password.length() > 30) {
        	request.setAttribute(
        		"error",
        		"IDは10文字、パスワードは30文字以内で　　　　　入力してください"
        		);
        	
        	return "login.jsp";
        }
        
        // 認証結果チェック
        if (teacher != null) {

            // ログイン成功
            HttpSession session = request.getSession();
            session.setAttribute("user", teacher);

            return "redirect:main/menu.jsp";
        } else {

            // ログイン失敗
            request.setAttribute(
            		"error", 
            		"IDまたはパスワードが違います");
            request.setAttribute("id", id);

            return "login.jsp";
        }
    }
}