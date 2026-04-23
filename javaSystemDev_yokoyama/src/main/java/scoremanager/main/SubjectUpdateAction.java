package scoremanager.main;

import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;



public class SubjectUpdateAction extends Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 科目コード取得
        String cd = request.getParameter("cd");

        // DAOで取得
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(cd);

        // JSPに渡す
        request.setAttribute("subject", subject);

        // 更新画面へ
        request.getRequestDispatcher("subject_update.jsp").forward(request,response);
       
}
}