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

public class ClassCreateExecuteAction extends Action {

    public String execute(
        HttpServletRequest request, HttpServletResponse response
    )throws Exception{

        HttpSession session=request.getSession();
        //セッションからログイン中のユーザー（Teacher）を取得
        Teacher teacher = (Teacher)session.getAttribute("user");
        // 事前条件チェック
        if (teacher == null) {
            return "redirect:../Login.action";
        }

        String classNumStr = request.getParameter("class_num");

        Map<String,String> errors=new HashMap<>();

        //入力チェック
        if(classNumStr == null || classNumStr.isEmpty()){
            errors.put("class_num", "クラス番号を入力してください");
        } else if (classNumStr.length() > 5) {
            errors.put("class_num", "クラス番号は5文字以内で入力してください");
        }

        //エラーがあれば戻す
        if(!errors.isEmpty()){
            request.setAttribute("errors",errors);
            request.setAttribute("class_num",classNumStr);
            request.getRequestDispatcher("class_create.jsp")
                    .forward(request,response);
            return "class_create.jsp";
        }

        ClassNumDao dao=new ClassNumDao();

        //重複チェック
        if(dao.get(classNumStr, teacher.getSchool()) != null){
           errors.put("class_num","クラス番号が重複しています");
           request.setAttribute("errors",errors);
           request.setAttribute("class_num",classNumStr);
           return "class_create.jsp";
        }

        //登録
        ClassNum classNum=new ClassNum();
        classNum.setClass_num(classNumStr);
        classNum.setSchool(teacher.getSchool());

        dao.save(classNum);

        return "class_create_done.jsp";
    }
}
