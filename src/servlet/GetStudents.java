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

@WebServlet("/GetStudents")
public class GetStudents extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解决跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");
        //解决中文乱码
        response.setContentType("text/plain;charset=utf-8");

        //获取传来的开始
       String start = request.getParameter("start");
       String count = request.getParameter("count");


        StudentService studentService = new StudentService();
        ArrayList<Student> students = studentService.getStudents(Integer.valueOf(start),Integer.valueOf(count));

        // jsonArray参数为原要返回的JSONArray数据
        JSONArray jsonArrayRtn= JSONArray.parseArray(JSON.toJSONString(students, SerializerFeature.DisableCircularReferenceDetect));
        //返回
        response.getWriter().println(jsonArrayRtn);
    }
}
