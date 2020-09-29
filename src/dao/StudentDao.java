package dao;

import dto.MessageDto;
import entify.Student;
import util.Util;

import java.sql.*;
import java.util.ArrayList;

public class StudentDao {
    //连接数据库
    Connection connection = Util.getConnection();

    /**
     * 增加学生信息
     *
     * @param student 增加的学生对象
     * @return MessageDto 对象
     */
    public MessageDto add(Student student) {
        MessageDto messageDto = new MessageDto();

        if (isExist(student.getNumber())) {
            messageDto.setMsg("该学号已经存在");
            messageDto.setSuccess(false);

        } else {
            //拼接增加sql语句
            String sql = "INSERT INTO `student`.`student`(`student_number`, `student_name`, `student_college`, " +
                    "`student_major`, `student_grade`, `student_class`, `student_age`)" +
                    " VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')";
            //格式化，将sql命令补全
            sql = String.format(sql, student.getNumber(), student.getName(), student.getCollege(), student.getMajor(),
                    student.getGrade(), student.getStuClass(), student.getAge());

            //操作，发送sql语句
            try {
                Statement statement = connection.createStatement();
                //执行sql语句
                statement.executeUpdate(sql);

                messageDto.setMsg("添加成功");
                messageDto.setSuccess(true);

            } catch (SQLException e) {
                e.printStackTrace();

                messageDto.setMsg("添加失败");
                messageDto.setSuccess(false);
            }
        }
        return messageDto;
    }

    /**
     * 删除学生信息
     *
     * @param studentIds id数组
     * @return
     */
    public MessageDto deleteStudentInfo(String[] studentIds) {
        MessageDto messageDto = new MessageDto();
        //拼接批量删除sql语句
        String sql = "delete from student where eid in (";

        //将sql命令补全
        for (int i = 0; i < studentIds.length; i++) {
            sql += "'" + studentIds[i] + "'";
            if (i < studentIds.length - 1) {
                sql += ",";
            }
        }
        sql += ")";

        //操作，发送sql语句
        try {
            Statement statement = connection.createStatement();
            //执行sql语句
            statement.executeUpdate(sql);

            messageDto.setMsg("删除成功");
            messageDto.setSuccess(true);

        } catch (SQLException e) {
            e.printStackTrace();

            messageDto.setMsg("删除失败");
            messageDto.setSuccess(false);
        }

        return messageDto;
    }

    /**
     * 修该学生信息
     *
     * @param student 修改的学生信息对象
     * @return
     */
    public MessageDto changeStudentInfo(Student student) {
        MessageDto messageDto = new MessageDto();

        String sql = null;

        if (student.getNumber().length() == 0) {
            //拼接修改sql语句
            sql = "update student set student_name = '%s',student_college = '%s', " +
                    "student_major = '%s',student_grade = '%s',student_class = '%s',student_age = '%s'" +
                    " where eid = '%d'";

            //格式化，将sql命令补全
            sql = String.format(sql, student.getName(), student.getCollege(), student.getMajor(),
                    student.getGrade(), student.getStuClass(), student.getAge(), student.getId());
        } else {
            if (isExist(student.getNumber())) {
                messageDto.setMsg("该学号已经存在");
                messageDto.setSuccess(false);

            } else {
                //拼接修改sql语句
                sql = "update student set student_number = '%s',student_name = '%s',student_college = '%s', " +
                        "student_major = '%s',student_grade = '%s',student_class = '%s',student_age = '%s'" +
                        " where eid = '%d'";

                //格式化，将sql命令补全
                sql = String.format(sql, student.getNumber(), student.getName(), student.getCollege(), student.getMajor(),
                        student.getGrade(), student.getStuClass(), student.getAge(), student.getId());
            }
        }
        if (sql != null) {
            //操作，发送sql语句
            try {
                Statement statement = connection.createStatement();
                //执行sql语句
                statement.executeUpdate(sql);

                messageDto.setMsg("修改成功");
                messageDto.setSuccess(true);

            } catch (SQLException e) {
                e.printStackTrace();

                messageDto.setMsg("修改失败");
                messageDto.setSuccess(false);
            }
        }

        return messageDto;
    }


    /**
     * 获取数据中的指定数目信息
     *
     * @param start 开始的地方
     * @param count 信息条数
     * @return 返回Student[]
     */
    public ArrayList<Student> getStudents(int start, int count) {
        //拼接查询sql语句
        String sql = "SELECT * FROM student LIMIT " + start + "," + count;

        try {
            //拼接sql语句
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            return getStudentsFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询当前数据库中的信息条数
     *
     * @return 返回当前数据库中的数据条数
     */
    public int getSumInformation() {
        String sql = "select count(*) from student";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 读取ResultSet集合的数据
     *
     * @param resultSet
     * @return ArrayList<Student>
     * @throws SQLException
     */
    private ArrayList<Student> getStudentsFromResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Student> students = new ArrayList<>();

        while (resultSet.next()) {
            Student student = new Student();
            student.setId(resultSet.getLong("eid"));
            student.setNumber(resultSet.getString("student_number"));
            student.setName(resultSet.getString("student_name"));
            student.setCollege(resultSet.getString("student_college"));
            student.setMajor(resultSet.getString("student_major"));
            student.setGrade(resultSet.getString("student_grade"));
            student.setStuClass(resultSet.getString("student_class"));
            student.setAge(resultSet.getString("student_age"));

            students.add(student);
        }
        return students;
    }

    /**
     * 判断当前学号的是否已经存在
     *
     * @param studentNumber
     * @return
     */
    private boolean isExist(String studentNumber) {
        ArrayList<Student> students = searchStudentNumber(studentNumber);

        //判断是否存在
        if (students.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 学号查询
     *
     * @param studentNumber 学号
     * @return 该学生对象
     */
    public ArrayList<Student> searchStudentNumber(String studentNumber) {
        //拼接查询sql语句
        String sql = "select * from student where student_number = '%s'";
        //格式化，将sql命令补全
        sql = String.format(sql, studentNumber);

        return executeSearchSql(sql);
    }

    /**
     * 查询学生信息
     * 根据提供的相应信息进行查询
     *
     * @param student
     * @return
     */
    public ArrayList<Student> searchStudents(Student student) {
        //拼接查询sql语句
        String sql = "select * from student where ";

        String[] data = {student.getNumber(), student.getName(), student.getCollege(), student.getMajor(),
                student.getGrade(), student.getStuClass(), student.getAge()};

        String[] sqlData = {"student_number = ", "student_name = ", "student_college = ", "student_major = ",
                "student_grade = ", "student_class = ", "student_age = "};

        //是否添加AND
        boolean flag = false;

        for (int i = 0; i < data.length; i++) {
            if (data[i].length() != 0) {
                if (flag) {
                    sql += " AND ";
                    flag = false;
                }
                sql += sqlData[i] + "'" + data[i] + "'";
                flag = true;
            }
        }

        return executeSearchSql(sql);
    }

    /**
     * 执行查询sql语句
     *
     * @param sql
     * @return
     */
    private ArrayList<Student> executeSearchSql(String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            return getStudentsFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
