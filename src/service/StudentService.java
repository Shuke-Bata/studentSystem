package service;

import dao.StudentDao;
import dto.MessageDto;
import entify.Student;

import java.util.ArrayList;

public class StudentService {
    //创建dao层的对象，执行对应的SQL语句
    private StudentDao studentDao = new StudentDao();

    /**
     * 保存对学生信息管理的增加和修改
     *
     * @param student  增加或者修改的学生对象
     * @param saveType 保存类型（add）or（change）
     * @return 返回保存信息
     */
    public MessageDto save(Student student, String saveType) {
        if (saveType.equals("add")) {
            //保存增加
            return studentDao.add(student);
        } else {
            //保存修改
            return studentDao.changeStudentInfo(student);
        }
    }

    /**
     * 删除学生信息
     *
     * @param studentIds id
     * @return 是否删除成功
     */
    public MessageDto delete(String[] studentIds) {
        return studentDao.deleteStudentInfo(studentIds);
    }

    /**
     * 查询学生信息
     *
     * @param student 查询的学生相关信息
     * @return ArrayList<Student>
     */
    public ArrayList<Student> search(Student student) {
        return studentDao.searchStudents(student);
    }

    /**
     * 获取指定条数的学生信息
     *
     * @param start
     * @param count
     * @return ArrayList<Student>
     */
    public ArrayList<Student> getStudents(int start, int count) {
        return studentDao.getStudents(start, count);
    }

    /**
     * 获取总的数据条数
     *
     * @return int
     */
    public int getCounts() {
        return studentDao.getSumInformation();
    }
}
