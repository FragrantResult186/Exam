package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/**
 * 科目登録の実行処理（POST）
 * シーケンス図：SubjectCreateExecuteAction
 * 入力値のチェック → 科目コードの重複チェック → DBに科目を保存
 */
public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        

    	SubjectDao subjectDao = new SubjectDao();
        Map<String, String> errors = new HashMap<>();

        // パラメータ取得
        String cd   = request.getParameter("cd");
        String name = request.getParameter("name");

        // ---- 入力値のチェック ----

        // 科目コード：未入力チェック
        if (cd == null || cd.trim().isEmpty()) {
            errors.put("cd", "科目コードを入力してください");
        } else if (cd.trim().length() != 3) {
            // 科目コードが3文字でなかった場合
            errors.put("cd", "科目コードは3文字で入力してください");
        }

        // 科目名：未入力チェック
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "科目名を入力してください");
        }

        // 入力エラーがあれば科目登録画面に戻す
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("cd",     cd);
            request.setAttribute("name",   name);
            request.getRequestDispatcher("subject_create.jsp").forward(request, response);
            return;
        }

        // ---- 科目コードと学校コードに合致するデータ取得（重複チェック）----
        // 科目コードが重複していた場合はDBへの書き込みを止めエラーを表示する
        Subject existing = subjectDao.get(cd.trim());
        if (existing != null) {
            errors.put("cd", "科目コードが重複しています");
            request.setAttribute("errors", errors);
            request.setAttribute("cd",     cd);
            request.setAttribute("name",   name);
            request.getRequestDispatcher("subject_create.jsp").forward(request, response);
            return;
        }

        // ---- DBに科目を保存する（入力された値をDBに保存）----
        Subject subject = new Subject();
      
        //System.out.println(teacher.getSchool().getCd());
        subject.setSchoolCd(teacher.getSchool().getCd());
        subject.setCD(cd.trim());
        subject.setName(name.trim());

        subjectDao.save(subject);

        // 科目登録完了画面を表示する
        request.getRequestDispatcher("subject_create_done.jsp").forward(request, response);
    }
}