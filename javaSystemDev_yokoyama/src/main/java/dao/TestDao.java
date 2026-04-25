package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    /**
     * 得点を登録する（INSERT）
     *
     * @param student  対象の学生
     * @param subject  対象の科目
     * @param point    得点
     */
    public void insert(Student student, Subject subject, int point) throws Exception {
        String sql = "INSERT INTO test (student_no, subject_cd, point) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, student.getNo());
            statement.setString(2, subject.getCd());
            statement.setInt(3, point);
            statement.executeUpdate();
        }
    }

    /**
     * 得点を更新する（UPDATE）
     *
     * @param student  対象の学生
     * @param subject  対象の科目
     * @param point    新しい得点
     */
    public void update(Student student, Subject subject, int point) throws Exception {
        String sql = "UPDATE test SET point = ? WHERE student_no = ? AND subject_cd = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, point);
            statement.setString(2, student.getNo());
            statement.setString(3, subject.getCd());
            statement.executeUpdate();
        }
    }

    /**
     * 得点を取得する（SELECT）
     * 該当レコードがない場合は -1 を返す
     *
     * @param student  対象の学生
     * @param subject  対象の科目
     * @return 得点。レコードが存在しない場合は -1
     */
    public int findPoint(Student student, Subject subject) throws Exception {
        String sql = "SELECT point FROM test WHERE student_no = ? AND subject_cd = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, student.getNo());
            statement.setString(2, subject.getCd());

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("point");
                }
            }
        }
        return -1;
    }
    
    public List<Test> filter(String schoolCd, int entYear, String classNum, String subjectCd, int count) throws Exception {

        List<Test> list = new ArrayList<>();

        String sql = """
            SELECT
                s.ent_year,
                s.class_num,
                s.no,
                s.name,
                t.point
            FROM student s
            LEFT JOIN test t
                ON s.no = t.student_no
                AND t.subject_cd = ?
                AND t.no = ?
            WHERE
                s.school_cd = ?
                AND s.ent_year = ?
                AND s.class_num = ?
            ORDER BY s.no
        """;

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, subjectCd);
            st.setInt(2, count);
            st.setString(3, schoolCd);
            st.setInt(4, entYear);
            st.setString(5, classNum);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Test t = new Test();

                t.setEntYear(rs.getInt("ent_year"));
                t.setClassNum(rs.getString("class_num"));
                t.setStudentNo(rs.getString("no"));
                t.setStudentName(rs.getString("name"));

                // pointはNULLの可能性ある
                int point = rs.getInt("point");
                if (rs.wasNull()) {
                    t.setPoint(0); // またはnull扱いでもOK
                } else {
                    t.setPoint(point);
                }

                list.add(t);
            }
        }

        return list;
    }
    
    

    /**
     * 得点を削除する（DELETE）
     *
     * @param student  対象の学生
     * @param subject  対象の科目
     */
    public void delete(Student student, Subject subject) throws Exception {
        String sql = "DELETE FROM test WHERE student_no = ? AND subject_cd = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, student.getNo());
            statement.setString(2, subject.getCd());
            statement.executeUpdate();
        }
    }

    /**
     * 得点を登録または更新する（UPSERT）
     * すでにレコードが存在すれば UPDATE、なければ INSERT を行う
     *
     * @param student  対象の学生
     * @param subject  対象の科目
     * @param point    得点
     */
    public void save(Student student, Subject subject, int point) throws Exception {
        int existing = findPoint(student, subject);
        if (existing == -1) {
            insert(student, subject, point);
        } else {
            update(student, subject, point);
        }
    }
}