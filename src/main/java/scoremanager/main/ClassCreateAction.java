package scoremanager.main;

import tool.Action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// クラス登録画面を表示するためのクラス
public class ClassCreateAction extends Action {
    
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    )throws Exception{
        return "class_create.jsp";
    }
}
