package dao;

import entify.User;
import util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private  Connection connection = Util.getConnection();

    /**
     * 登录
     * @param userName
     * @return
     */
    public User login(String userName) {
        String sql = "select * from users where userName = '%s'";

        //格式化，将sql命令补全
        sql = String.format(sql, userName);

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            User user = new User();
            //遍历数据集合，
            while (resultSet.next()) {
                user.setEid(resultSet.getInt("eid"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("password"));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    //注册
    public void register(User user) {
        String sql = "insert into users (userName,password) values(?,?)";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement =connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUserName());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   /* //删除
    public void logout(String userName) {
        String sql = "delete from user where userName = '%s'";
        //格式化，将sql命令补全
        sql = String.format(sql, userName);
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //修改
    public void update(User user) {
        String sql = "update user set password = '%s' where userName = '%s'";
        //格式化，将sql命令补全
        sql = String.format(sql, user.getPassword(), user.getUserName());
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

}
