package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

    // 科目情報を1件取得
    public Subject get(String cd, School school) throws Exception {
        Subject subject = new Subject();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("select * from subject where cd = ? and school_cd = ?");
            statement.setString(1, cd);
            statement.setString(2, school.getCd());
            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
                subject.setCd(rSet.getString("cd"));
                subject.setName(rSet.getString("name"));
                subject.setSchool(school);
            } else {
                subject = null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return subject;
    }

    // 学校に関連する科目一覧を取得
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("select * from subject where school_cd = ? order by cd asc");
            statement.setString(1, school.getCd());
            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {
                Subject subject = new Subject();
                subject.setCd(rSet.getString("cd"));
                subject.setName(rSet.getString("name"));
                subject.setSchool(school);
                list.add(subject);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return list;
    }

    // 科目情報の登録 or 更新を行う
    public boolean save(Subject subject) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            Subject old = get(subject.getCd(), subject.getSchool());
            if (old == null) {
                statement = connection.prepareStatement(
                    "insert into subject (cd, name, school_cd) values (?, ?, ?)");
                statement.setString(1, subject.getCd());
                statement.setString(2, subject.getName());
                statement.setString(3, subject.getSchool().getCd());
            } else {
                statement = connection.prepareStatement(
                    "update subject set name = ? where cd = ? and school_cd = ?");
                statement.setString(1, subject.getName());
                statement.setString(2, subject.getCd());
                statement.setString(3, subject.getSchool().getCd());
            }

            count = statement.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return count > 0;
    }

    // 科目情報の削除を行う
    public boolean delete(Subject subject) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            statement = connection.prepareStatement(
                "delete from subject where cd = ? and school_cd = ?");
            statement.setString(1, subject.getCd());
            statement.setString(2, subject.getSchool().getCd());

            count = statement.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return count > 0;
    }
}
