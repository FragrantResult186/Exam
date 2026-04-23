package scoremanager.main;

import java.util.List;

import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {        
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjects = subjectDao.findAll();
        request.setAttribute("subjects", subjects);
        request.getRequestDispatcher("subject_list.jsp").forward(request, response);
    }
}
