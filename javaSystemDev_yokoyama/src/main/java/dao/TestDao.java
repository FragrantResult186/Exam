package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Student;
import bean.Subject;

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