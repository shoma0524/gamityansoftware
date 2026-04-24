package scoremanager.main;

import tool.Action;

import java.util.HashMap;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SubjectCreateExecuteAction extends Action {
    
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    )throws Exception{

        HttpSession session=request.getSession();
        //セッションからログイン中のユーザー（Teacher）を取得
        Teacher teacher = (Teacher)session.getAttribute("user");

        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        Map<String,String> errors=new HashMap<>();

        //入力チェック
        if(cd == null || cd.length() != 3){
            errors.put("cd", "科目コードは３文字で入力してください");
        }

        if(name==null || name.isEmpty()){
            errors.put("name","科目名を入力してください");
        }
        //エラーがあれば戻す
        if(!errors.isEmpty()){
            request.setAttribute("errors",errors);
            request.setAttribute("cd",cd);
            request.setAttribute("name",name);
            request.getRequestDispatcher("subject_create.jsp")
                    .forward(request,response);
            return "subject_create.jsp";
        }

        SubjectDao dao=new SubjectDao();

        //重複チェック
        if(dao.get(cd, teacher.getSchool()) != null){
           errors.put("cd","科目コードが重複しています");
           request.setAttribute("errors",errors);
           return "subject_create.jsp";
        }

        //登録
        Subject subject=new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());

        dao.save(subject);

        return "subject_create_done.jsp";
    }
}
