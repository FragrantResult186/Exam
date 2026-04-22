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

public class StudentCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session=req.getSession();// セッション
        Teacher teacher=(Teacher)session.getAttribute("user");
        School school=teacher.getSchool();
        //パラメーター
        String entYearStr=req.getParameter("ent_year");//入学年度
        String no=req.getParameter("no");//番号
        String name=req.getParameter("name");//名前
        String classNum=req.getParameter("class_num");//クラス
        Map<String,String> errors=new HashMap<>();//エラーメッセージ
        if (no == null||no.trim().isEmpty()){
            errors.put("no","学生番号を入力してください");
        }
        if (name == null||name.trim().isEmpty()){
            errors.put("name","氏名を入力してください");
        }
        //重複かどうか
        if (!errors.containsKey("no")&&no!=null&&
        		!no.trim().isEmpty()//""も排除
        		){
            StudentDao studentDao=new StudentDao();
            Student existing=studentDao.get(no.trim());
            if (existing!=null){
                errors.put("no","この学生番号はすでに登録されています");
            }
        }
        // エラーがある場合
        if (!errors.isEmpty()){
            ClassNumDao classNumDao=new ClassNumDao();
            List<String>classNumSet=classNumDao.filter(school);
            //入学年度の選択肢を再生成
            LocalDate today=LocalDate.now();
            int year=today.getYear();
            List<Integer> entYearSet=new ArrayList<>();
            for (int i=year-10;i<=year+10;i++){
                entYearSet.add(i);
            }
            // 入力値とエラーをリクエストにセット
            req.setAttribute("errors",errors);
            req.setAttribute("ent_year_set",entYearSet);
            req.setAttribute("class_num_set",classNumSet);
            req.getRequestDispatcher("student_create.jsp").forward(req,res);
            return;
        }
        // Studentインスタンス
        Student student=new Student();
        student.setNo(no.trim());
        student.setName(name.trim());
        student.setEntYear(Integer.parseInt(entYearStr));
        student.setClassNum(classNum);
        student.setAttend(true);// 新規登録時は在学中
        student.setSchool(school);
        // DB
        StudentDao studentDao=new StudentDao();
        boolean result=studentDao.save(student);
        if (result){//成功
            res.sendRedirect("student_create_done.jsp");
        } else{//失敗
            req.setAttribute("errorMessage", "学生の登録に失敗しました。");
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}