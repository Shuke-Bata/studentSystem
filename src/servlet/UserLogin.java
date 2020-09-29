package servlet;

import com.alibaba.fastjson.JSON;
import dto.MessageDto;
import entify.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解决跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");
        //解决中文乱码
        response.setContentType("text/plain;charset=utf-8");

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        User user = new User(userName, password);

        UserService userService = new UserService();

        MessageDto messageDto = null;
        //调用service中的login
        if (userService.login(user) != null) {
            //设置返回值
            messageDto = new MessageDto("登录成功", true);
        } else {
            messageDto = new MessageDto("登录失败", false);
        }

        //由于前台传送过来的数据为json，所以返回也要为json格式
        String json = JSON.toJSONString(messageDto);
        //返回
        response.getWriter().println(json);
    }
}
