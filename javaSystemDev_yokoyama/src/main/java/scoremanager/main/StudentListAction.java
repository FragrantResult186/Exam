package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String entYearStr = request.getParameter("f1");
        String classNum = request.getParameter("f2");
        String attendFilter = request.getParameter("f3");

        int entYear = 0;
        Boolean isAttend = null;
        List<Student> students;

        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();
        Map<String, String> errors = new HashMap<>();

        if (classNum == null || classNum.isEmpty()) {
            classNum = "0";
        }
        if (attendFilter == null || attendFilter.isEmpty()) {
            attendFilter = "0";
        }

        if (entYearStr != null && !entYearStr.equals("0")) {
            entYear = Integer.parseInt(entYearStr);
        }

        if ("true".equals(attendFilter)) {
            isAttend = true;
        } else if ("false".equals(attendFilter)) {
            isAttend = false;
        } else {
            isAttend = false;
        }

        int year = LocalDate.now().getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i < year + 1; i++) {
            entYearSet.add(i);
        }

        List<String> classNumSet = classNumDao.filter(teacher.getSchool());

        if (entYear != 0 && !classNum.equals("0")) {
            students = studentDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
        } else if (entYear != 0) {
            students = studentDao.filter(teacher.getSchool(), entYear, isAttend);
        } else if (classNum.equals("0")) {
            students = studentDao.filter(teacher.getSchool(), isAttend);
        } else {
            errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
            request.setAttribute("errors", errors);
            students = studentDao.filter(teacher.getSchool(), isAttend);
        }

        request.setAttribute("f1", entYear);
        request.setAttribute("f2", classNum);
        request.setAttribute("f3", attendFilter);
        request.setAttribute("students", students);
        request.setAttribute("class_num_set", classNumSet);
        request.setAttribute("ent_year_set", entYearSet);

        request.getRequestDispatcher("student_list.jsp").forward(request, response);
    }
}
