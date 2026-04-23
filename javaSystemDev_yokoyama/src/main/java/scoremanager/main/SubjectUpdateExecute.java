package scoremanager.main;

import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectUpdateExecute extends Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // パラメータ取得
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        // 更新処理
        SubjectDao subjectDao = new SubjectDao();
        subjectDao.update(cd, name);

        // 一覧画面に戻す
        request.getRequestDispatcher("subject_list.jsp").forward(request,response);
    }
}