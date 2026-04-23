package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {

    private final String baseSql = "select * from student where school_cd=?";

    //コミットテスト
    
    public Student get(String no) throws Exception {
        String sql = "select * from student where no=?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, no);

            try (ResultSet rSet = statement.executeQuery()) {
                if (!rSet.next()) {
                    return null;
                }

                SchoolDao schoolDao = new SchoolDao();
                Student student = new Student();
                student.setNo(rSet.getString("no"));
                student.setName(rSet.getString("name"));
                student.setEntYear(rSet.getInt("ent_year"));
                student.setClassNum(rSet.getString("class_num"));
                student.setAttend(rSet.getBoolean("is_attend"));
                student.setSchool(schoolDao.get(rSet.getString("school_cd")));
                return student;
            }
        }
    }

    private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
        List<Student> list = new ArrayList<>();

        try {
            while (rSet.next()) {
                Student student = new Student();
                student.setNo(rSet.getString("no"));
                student.setName(rSet.getString("name"));
                student.setEntYear(rSet.getInt("ent_year"));
                student.setClassNum(rSet.getString("class_num"));
                student.setAttend(rSet.getBoolean("is_attend"));
                student.setSchool(school);
                list.add(student);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }

        return list;
    }

    private String getAttendCondition(Boolean isAttend) {
        return isAttend == null ? "" : " and is_attend=?";
    }

    private void setAttendParam(PreparedStatement statement, int index, Boolean isAttend) throws SQLException {
        if (isAttend != null) {
            statement.setBoolean(index, isAttend);
        }
    }

    public List<Student> filter(School school, int entYear, String classNum, Boolean isAttend) throws Exception {
        String sql = baseSql
            + " and ent_year=? and class_num=?"
            + getAttendCondition(isAttend)
            + " order by no asc";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);
            setAttendParam(statement, 4, isAttend);

            try (ResultSet rSet = statement.executeQuery()) {
                return postFilter(rSet, school);
            }
        }
    }

    public List<Student> filter(School school, int entYear, Boolean isAttend) throws Exception {
        String sql = baseSql
            + " and ent_year=?"
            + getAttendCondition(isAttend)
            + " order by no asc";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);
            setAttendParam(statement, 3, isAttend);

            try (ResultSet rSet = statement.executeQuery()) {
                return postFilter(rSet, school);
            }
        }
    }

    public List<Student> filter(School school, Boolean isAttend) throws Exception {
        String sql = baseSql
            + getAttendCondition(isAttend)
            + " order by no asc";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, school.getCd());
            setAttendParam(statement, 2, isAttend);

            try (ResultSet rSet = statement.executeQuery()) {
                return postFilter(rSet, school);
            }
        }
    }

    public boolean save(Student student) throws Exception {
        int count;
        Student old = get(student.getNo());

        try (Connection connection = getConnection()) {
            if (old == null) {
                String sql = "insert into student (no, name, ent_year, class_num, is_attend, school_cd) values (?, ?, ?, ?, ?, ?)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, student.getNo());
                    statement.setString(2, student.getName());
                    statement.setInt(3, student.getEntYear());
                    statement.setString(4, student.getClassNum());
                    statement.setBoolean(5, student.isAttend());
                    statement.setString(6, student.getSchool().getCd());
                    count = statement.executeUpdate();
                }
            } else {
                String sql = "update student set name=?, ent_year=?, class_num=?, is_attend=? where no=?";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, student.getName());
                    statement.setInt(2, student.getEntYear());
                    statement.setString(3, student.getClassNum());
                    statement.setBoolean(4, student.isAttend());
                    statement.setString(5, student.getNo());
                    count = statement.executeUpdate();
                }
            }
        }

        return count > 0;
    }
}
