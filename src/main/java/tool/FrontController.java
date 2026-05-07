package tool;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//「*.action」にマッチする全リクエストをこのサーブレットで受ける
@WebServlet("*.action")
@MultipartConfig
public class FrontController extends HttpServlet {
	
	private void process(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    try {
	        String path = request.getServletPath().substring(1);
	        String name = path.replace(".a", "A").replace("/", ".");

	        Action action = (Action) Class.forName(name)
	                .getDeclaredConstructor().newInstance();

	        String url = action.execute(request, response);

	        if (url != null) {
	            if (url.startsWith("redirect:")) {
	                response.sendRedirect(url.substring(9));
	            } else {
	                request.getRequestDispatcher(url).forward(request, response);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        request.getRequestDispatcher("/error.jsp").forward(request, response);
	    }
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    process(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    process(request, response);
	}
}