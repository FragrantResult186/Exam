package scoremanager.main;

import bean.School;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session=request.getSession();// セッション
        Teacher teacher=(Teacher) session.getAttribute("user");
        School school=teacher.getSchool();

        // パラメータ取得
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");
        //String schoolCd = (String)request.getSession().getAttribute("school_cd");
        
        
        

        // 更新処理
        SubjectDao subjectDao = new SubjectDao();
        subjectDao.update(cd, name,school);
        
        System.out.println("cd=" + cd);
        System.out.println("name=" + name);

        // 一覧画面に戻す
        response.sendRedirect("SubjectList.action");
    }
}