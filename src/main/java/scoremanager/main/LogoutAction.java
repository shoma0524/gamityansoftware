package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

//ログアウトを行うクラス
public class LogoutAction extends Action {

    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {

        // セッション取得
        HttpSession session = request.getSession(false);

        // セッションがあれば削除
        if (session != null) {
            session.invalidate();
        }

        // ログアウト画面へ
        return "logout.jsp";
    }
}