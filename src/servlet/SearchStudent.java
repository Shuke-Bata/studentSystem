package servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import entify.Student;
import service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/SearchStudent")
public class SearchStudent extends HttpServlet {
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

        Student student = new Student(number, name, college, major, grade, stuClass, age);

        StudentService studentService = new StudentService();

        //定义返回前台的消息
        //调用studentService中的搜索方法
        ArrayList<Student> students = studentService.search(student);

        // jsonArray参数为原要返回的JSONArray数据
        JSONArray jsonArrayRtn= JSONArray.parseArray(JSON.toJSONString(students, SerializerFeature.DisableCircularReferenceDetect));
        //返回
        response.getWriter().println(jsonArrayRtn);
    }
}
