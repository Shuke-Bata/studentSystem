package servlet;

import com.alibaba.fastjson.JSON;
import dto.MessageDto;
import service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeleteStudents")
public class DeleteStudents extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 解决跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 解决中文乱码
        response.setContentType("text/plain;charset=utf-8");

        // 获取传来的id
        // 注意，要用[]来接收前台传来的数组
        String[] ids = request.getParameterValues("ids[]");

        StudentService studentService = new StudentService();

        // 定义返回前台的消息
        // 调用studentService中的delete（）
        MessageDto messageDto = studentService.delete(ids);

        //由于前台传送过来的数据为json，所以返回也要为json格式
        String json = JSON.toJSONString(messageDto);
        //返回
        response.getWriter().println(json);
    }
}
