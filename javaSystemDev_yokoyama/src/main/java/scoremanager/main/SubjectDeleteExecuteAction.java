package scoremanager.main;

import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の宣言 1
        String cd = "";

        // リクエストパラメータの取得 2
        cd = req.getParameter("cd");

        // DBからデータ取得 3
        // なし

        // ビジネスロジック 4
        // なし

        // DBへデータ保存 5
        SubjectDao subjectDao = new SubjectDao();
        boolean result = subjectDao.delete(cd);

        // レスポンス値をセット 6
        // なし

        // JSPへフォワード 7
        if (result) {
            res.sendRedirect("SubjectList.action");
        } else {
            req.setAttribute("errorMessage", "科目情報の削除に失敗しました。");
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}