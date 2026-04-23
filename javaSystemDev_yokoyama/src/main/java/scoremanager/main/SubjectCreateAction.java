package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

/**
 * 科目登録画面の表示（GET）
 * シーケンス図：SubjectCreateAction
 * 科目登録画面（subject_create.jsp）を表示する
 */
public class SubjectCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher("subject_create.jsp").forward(request, response);
    }
}