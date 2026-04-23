package scoremanager.main;

import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の宣言 1
        String cd = "";
        Subject subject = null;

        // リクエストパラメータの取得 2
        cd = req.getParameter("no");

        // DBからデータ取得 3
        SubjectDao subjectDao = new SubjectDao();
        subject = subjectDao.get(cd);

        // ビジネスロジック 4
        // なし

        // DBへデータ保存 5
        // なし

        // レスポンス値をセット 6
        req.setAttribute("subject", subject);

        // JSPへフォワード 7
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}