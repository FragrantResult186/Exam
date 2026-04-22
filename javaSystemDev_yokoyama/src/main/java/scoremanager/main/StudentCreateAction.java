package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// セッションからユーザー情報を取得
		HttpSession session=req.getSession();
		Teacher teacher=(Teacher)session.getAttribute("user");
		// 入学年度の選択肢
		LocalDate todaysDate=LocalDate.now();
		int year=todaysDate.getYear();
		List<Integer> entYearSet = new ArrayList<>();
		for (int i=year-10; i<=year+10;i++) {
			entYearSet.add(i);
		}
		// クラス番号の一覧を取得
		ClassNumDao classNumDao = new ClassNumDao();
		List<String> classNumSet = classNumDao.filter(teacher.getSchool());
		//リクエストにデータをセット
		req.setAttribute("ent_year_set",entYearSet);
		req.setAttribute("class_num_set",classNumSet);
		//フォワード
		req.getRequestDispatcher("student_create.jsp").forward(req,res);
	}
}