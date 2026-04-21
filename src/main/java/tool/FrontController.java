package tool;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//「*.action」にマッチする全リクエストをこのサーブレットで受ける
@WebServlet("*.action")
public class FrontController extends HttpServlet {
	
	@Override
	protected void doGet(
		HttpServletRequest request, HttpServletResponse response
		)throws ServletException, IOException {
			try {
				String path = request.getServletPath().substring(1);
				String name = path.replace(".a", "A").replace("/", ".");
				
				System.out.println("★ servlet path ->"+request.getServletPath());
				System.out.println("★ class name ->"+name);
				// Actionクラスを動的に生成
				Action action = (Action) Class.forName(name).
						getDeclaredConstructor().newInstance();
				// Actionの処理を実行
	            String url=action.execute(request, response);
	         // 遷移処理
	            if (url != null) {
	            	// redirect指定の場合
	                if (url.startsWith("redirect:")) {
	                	// "redirect:" を除去
	                    response.sendRedirect(url.substring(9)); 
	                } else {
	                	// 通常はフォワード
	                    request.getRequestDispatcher(url).
	                    forward(request, response);
	                }
	            }
			} catch (Exception e) {
				e.printStackTrace();
				// エラーページへ遷移
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
	}
	// POSTリクエストもGETと同じ処理にする
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response
			)throws ServletException, IOException {
			doGet(request,response);
		}
}