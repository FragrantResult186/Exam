package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // パラメータ
        String entYearStr = request.getParameter("f1");
        String classNum = request.getParameter("f2");
        String subjectCd = request.getParameter("f4");
        String countStr = request.getParameter("f5");

        int entYear = 0;
        int count = 0;

        if (entYearStr != null && !entYearStr.equals("0")) {
            entYear = Integer.parseInt(entYearStr);
        }

        if (countStr != null && !countStr.isEmpty()) {
            count = Integer.parseInt(countStr);
        }

        // 入学年度リスト
        int year = LocalDate.now().getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        // クラスリスト
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumSet = classNumDao.filter(teacher.getSchool());

        // 科目リスト
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.findAll();

        
        List<?> tests = new ArrayList<>();
        Subject subject = null;

        if (entYear != 0 && classNum != null && !classNum.equals("0")
                && subjectCd != null && !subjectCd.isEmpty() && count != 0) {

            TestDao testDao = new TestDao();

            // テスト一覧取得
            tests = testDao.filter(
                teacher.getSchool().getCd(),
                entYear,
                classNum,
                subjectCd,
                count
            );

            // 科目取得（表示用）
            subject = subjectDao.get(subjectCd);
        }

        // JSPへ
        request.setAttribute("f1", entYear);
        request.setAttribute("f2", classNum);
        request.setAttribute("f4", subjectCd);
        request.setAttribute("f5", count);

        request.setAttribute("ent_year_set", entYearSet);
        request.setAttribute("class_num_set", classNumSet);
        request.setAttribute("subjects", subjectList);

        request.setAttribute("tests", tests);
        request.setAttribute("subject", subject);

        request.getRequestDispatcher("test_list.jsp").forward(request, response);
    }
}