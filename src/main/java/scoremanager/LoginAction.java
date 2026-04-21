package scoremanager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class LoginAction extends Action {

	public String execute(
		    HttpServletRequest request, HttpServletResponse response
		) throws Exception {;
		    return "login.jsp";
		}
}