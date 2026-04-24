package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    /**
     * 指定した科目・学校・入学年度・クラスの成績一覧を取得する
     *
     * @param subject  対象の科目
     * @param school   対象の学校
     * @param entYear  入学年度
     * @param classNum クラス番号
     * @return 学生ごとの成績リスト
     */
    public List<TestListSubject> filter(Subject subject, School school, int entYear, String classNum) throws Exception {
        List<TestListSubject> list = new ArrayList<>();

        String sql = "SELECT t.student_no, t.subject_cd, t.point,"
                   + "       st.name AS student_name, st.ent_year, st.class_num, st.is_attend, st.school_cd"
                   + " FROM test t"
                   + " INNER JOIN student st ON t.student_no = st.no"
                   + " WHERE t.subject_cd = ?"
                   + "   AND st.school_cd = ?"
                   + "   AND st.ent_year = ?"
                   + "   AND st.class_num = ?"
                   + " ORDER BY t.student_no";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, subject.getCd());
            statement.setString(2, school.getCd());
            statement.setInt(3, entYear);
            statement.setString(4, classNum);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    TestListSubject record = new TestListSubject();
                    record.setStudentNo(rs.getString("student_no"));
                    record.setStudentName(rs.getString("student_name"));
                    record.setEntYear(rs.getInt("ent_year"));
                    record.setClassNum(rs.getString("class_num"));
                    list.add(record);
                }
            }
        }
        return list;
    }
}