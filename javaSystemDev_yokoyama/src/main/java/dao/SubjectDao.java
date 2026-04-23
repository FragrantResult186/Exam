package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;

public class SubjectDao extends Dao {

    public List<Subject> findAll() throws Exception {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT cd, name FROM subject ORDER BY cd";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setCD(rs.getString("cd"));
                subject.setName(rs.getString("name"));
                list.add(subject);
            }
        }
        return list;
    }

    public Subject get(String cd) throws Exception {
        String sql = "SELECT cd, name FROM subject WHERE cd = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cd);

            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Subject subject = new Subject();
                subject.setCD(rs.getString("cd"));
                subject.setName(rs.getString("name"));
                return subject;
            }
        }
    }

    public boolean save(Subject subject) throws Exception {
        int count;
        Subject old = get(subject.getCd());

        try (Connection connection = getConnection()) {
            if (old == null) {
                String sql = "INSERT INTO subject (school_cd, cd, name) VALUES (?, ?, ?)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                	statement.setString(1, subject.getSchoolCd());
                	statement.setString(2, subject.getCd());
                    statement.setString(3, subject.getName());
                    count = statement.executeUpdate();
                }
            } else {
                String sql = "UPDATE subject SET name = ? WHERE cd = ?";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, subject.getName());
                    statement.setString(2, subject.getCd());
                    count = statement.executeUpdate();
                }
            }
        }
        return count > 0;
    }
    public void update(String cd, String name) throws Exception {

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "UPDATE subject SET name=? WHERE cd=?"
        );

        st.setString(1, name);
        st.setString(2, cd);

        st.executeUpdate();

        st.close();
        con.close();
    }

    public boolean delete(String cd) throws Exception {
        String sql = "DELETE FROM subject WHERE cd = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cd);
            return statement.executeUpdate() > 0;
        }
    }
}