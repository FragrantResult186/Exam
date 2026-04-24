package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

    /**
     * 指定した学生の成績一覧を取得する
     *
     * @param student 対象の学生
     * @return 科目ごとの成績リスト
     */
    public List<TestListStudent> filter(Student student) throws Exception {
        List<TestListStudent> list = new ArrayList<>();

        String sql = "SELECT t.student_no, t.subject_cd, t.point, s.name AS subject_name"
                   + " FROM test t"
                   + " INNER JOIN subject s ON t.subject_cd = s.cd"
                   + " WHERE t.student_no = ?"
                   + " ORDER BY t.subject_cd";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, student.getNo());

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Subject subject = new Subject();
                    subject.setCD(rs.getString("subject_cd"));
                    subject.setName(rs.getString("subject_name"));

                    TestListStudent record = new TestListStudent();
                    record.setNum(Integer.parseInt(student.getClassNum()));
                    record.setSubjectCd(rs.getString("subject_cd"));
                    record.setSubjectName(rs.getString("subject_name"));
                    record.setPoint(rs.getInt("point"));
                    list.add(record);
                }
            }
        }
        return list;
    }
}