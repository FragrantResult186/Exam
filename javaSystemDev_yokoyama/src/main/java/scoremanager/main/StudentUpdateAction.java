package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();// セッション
        Teacher teacher=(Teacher)session.getAttribute("user");
        // パラメーター
        String no=req.getParameter("no");
        StudentDao studentDao=new StudentDao();
        Student student=studentDao.get(no);
        LocalDate today=LocalDate.now();
        int year=today.getYear();
        List<Integer> entYearSet=new ArrayList<>();
        for (int i=year-10;i<=year+1;i++) {
            entYearSet.add(i);
        }
        // クラス番号の一覧を取得
        ClassNumDao classNumDao=new ClassNumDao();
        List<String> classNumSet=classNumDao.filter(teacher.getSchool());
        req.setAttribute("student",student);
        req.setAttribute("ent_year_set",entYearSet);
        req.setAttribute("class_num_set",classNumSet);
        // フォワード
        req.getRequestDispatcher("student_update.jsp").forward(req,res);
    }
}