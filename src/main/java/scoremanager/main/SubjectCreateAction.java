package scoremanager.main;

import tool.Action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//科目登録画面を表示するためのクラス
public class SubjectCreateAction extends Action {
    
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    )throws Exception{
        return "subject_create.jsp";
    }
}
