package servlet;

import com.alibaba.fastjson.JSON;
import dto.MessageDto;
import entify.Student;
import service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SaveStudent")
public class SaveStudent extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解决跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");
        //解决中文乱码
        response.setContentType("text/plain;charset=utf-8");

        String number = request.getParameter("number");
        String name = request.getParameter("name");
        String college = request.getParameter("college");
        String major = request.getParameter("major");
        String grade = request.getParameter("grade");
        String stuClass = request.getParameter("stuClass");
        String age = request.getParameter("age");
        String saveType = request.getParameter("saveType");

        Student student = new Student(number, name, college, major, grade, stuClass, age);

        if (saveType.equals("change")){
            int id = Integer.valueOf(request.getParameter("id"));
            student.setId(id);
        }

        StudentService studentService = new StudentService();

        //调用studentService中的save()
        MessageDto messageDto = studentService.save(student,saveType);

        //由于前台传送过来的数据为json，所以返回也要为json格式
        String json = JSON.toJSONString(messageDto);
        //返回
        response.getWriter().println(json);
    }
}
