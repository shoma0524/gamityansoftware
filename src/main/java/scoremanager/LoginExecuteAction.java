package scoremanager;

import java.util.ArrayList;
import java.util.List;

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
        List<String> errors = new ArrayList<>();

        // 半角英数字チェック
        if (!password.matches("^[a-zA-Z0-9]+$") || !id.matches("^[a-zA-Z0-9]+$")) {
        	
        	errors.add("半角英数字のみ入力してください");
        }
        
        //文字数チェック
        if(id.length() > 10) {
        	
        	errors.add("IDは10文字以内で入力してください");     	
        }
        
        if(password.length() > 30) {
        	
        	errors.add("パスワードは30文字以内で入力してください");
        }
        
        // エラーがある場合
        if (!errors.isEmpty()) {

            request.setAttribute("errors", errors);

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
        	errors.add("IDまたはパスワードが確認できませんでした");
        	request.setAttribute("errors", errors);
            request.setAttribute("id", id);

            return "login.jsp";
        }
    }
}