package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session=req.getSession();// セッション
        Teacher teacher=(Teacher) session.getAttribute("user");
        School school=teacher.getSchool();
        String no=req.getParameter("no");
        String name=req.getParameter("name");
        String entYearStr=req.getParameter("ent_year");
        String classNum=req.getParameter("class_num");
        String isAttendStr=req.getParameter("is_attend");// チェックされていればnonnull
        Map<String, String> errors = new HashMap<>();
        if (name == null || name.trim().isEmpty()){
            errors.put("name", "氏名を入力してください");
        }
        if (name.length() > 10){
            errors.put("name", "１０文字以内で入力してください");
        }
        if (!errors.isEmpty()){
            // 学生情報を再セット（入力値で上書き）
            StudentDao studentDao=new StudentDao();
            Student student=studentDao.get(no);
            student.setName(name==null ? "" : name);
            // 入学年度の選択肢
            LocalDate today=LocalDate.now();
            int year=today.getYear();
            List<Integer> entYearSet=new ArrayList<>();
            for (int i=year-10;i<=year+1;i++) {
                entYearSet.add(i);
            }
            // クラス番号の一覧
            ClassNumDao classNumDao = new ClassNumDao();
            List<String> classNumSet = classNumDao.filter(school);
            req.setAttribute("errors", errors);
            req.setAttribute("student", student);
            req.setAttribute("ent_year_set", entYearSet);
            req.setAttribute("class_num_set", classNumSet);
            req.getRequestDispatcher("student_update.jsp").forward(req, res);
            return;
        }
        //Studentインスタンス
        Student student=new Student();
        student.setNo(no);
        student.setName(name.trim());
        student.setEntYear(Integer.parseInt(entYearStr));
        student.setClassNum(classNum);
        student.setAttend(isAttendStr!=null);// チェックボックスがONならtrue
        student.setSchool(school);
        // DB
        StudentDao studentDao=new StudentDao();
        boolean result=studentDao.save(student);
        if (result){
            //成功
            res.sendRedirect("student_update_done.jsp");
        } else{
            // 失敗
            req.setAttribute("errorMessage","学生情報の更新に失敗しました");
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}