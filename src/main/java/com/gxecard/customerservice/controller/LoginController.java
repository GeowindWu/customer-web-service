package com.gxecard.customerservice.controller;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gxecard.customerservice.service.MessageService;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

@RestController
@Slf4j
public class LoginController extends BaseController {

	public LoginController(MessageService messageService) {
		super(messageService);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JSONObject welcome(String userName, String password, HttpServletRequest request, HttpServletResponse response) {
		String sql = "select password from https_access_log.administrator a where a.adminName = ? ";
		JSONObject result = new JSONObject();
		try {
			Map<String, Object> queryMap = messageService.queryMap(sql, userName);
			String pw = (String) queryMap.get("password");

			if (pw.equals(password)) {
				System.out.println("登录成功");

				result.put("serverStatus", "true");
				result.put("MSG", "登录成功");
				HttpSession session = request.getSession();
//				session.setMaxInactiveInterval(60 * 5);// 五分钟
				session.setAttribute("ISLOGIN", "true");
			
				session.setAttribute("userName",userName);
				session.setAttribute("uuid", "");// uuid标识
			} else {
				result.put("serverStatus", "false");
				result.put("MSG", "密码错误");
			}
		} catch (Exception e) {
			log.error("login exception :" +e.toString());
			result.put("serverStatus", "false");
			result.put("MSG", "用户不存在");
		}
		return result;
	}
	
	@RequestMapping(value="/isLogin.do",method=RequestMethod.POST)
	public JSONObject isLogin(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		boolean isLogin = (Boolean) session.getAttribute("ISLOGIN");
		JSONObject result = new JSONObject();
		if(isLogin){
			result.put("isLogin", "true");
			result.put("userName",session.getAttribute("userName")+"");
		}else{
			result.put("isLogin", "false");
		}
		return result;
	}

	/**
	 * 处理请求并且返回客服端
	 * 
	 * @param response
	 */
	public boolean writeToClient(HttpServletResponse response, String returnInfo) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = null;
		boolean trage = false;
		try {
			if (returnInfo == null)
				returnInfo = "";

			// 将数据写入前台
			out = response.getWriter();

			out.write(returnInfo);
			// response.sendRedirect("index.jsp");
			trage = true;
		} catch (Exception e) {
			trage = false;
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		return trage;
	}

}
