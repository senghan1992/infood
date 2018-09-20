package controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import service.InfoodService;
import vo.FoodVO;
import vo.MemberVO;
import vo.StationVO;

@Controller
public class Infood_Mobile_Controller {

	@Autowired
	InfoodService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		return "/WEB-INF/views/home.jsp";
	}

	@RequestMapping("/mobile/login")
	@ResponseBody
	public String login(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("request", request);
		MemberVO vo = service.user_check(map);
		String resultStr = "";
		if (vo == null) {
			resultStr = String.format("{res:[{'result':'%s'}]}", "none");
		} else {
			vo = service.login(map);
			if (vo == null) {
				resultStr = String.format("{res:[{'result':'%s'}]}", "fail");
			} else {
				resultStr = String.format("{res:[{'result':'%s'," + "'last_login':'%s',"+"'nikname':'%s',"+"'user_idx':'%s'}]}", "success",
						vo.getLast_login(), vo.getNikname(), vo.getIdx()+"");
				int result = service.last_login(map);
			}
		}

		return resultStr;
	}

	@RequestMapping("/mobile/nikname_check")
	@ResponseBody
	public String join_check(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("request", request);

		MemberVO vo = service.check(map);
		String resultStr = "";
		if (vo == null) {
			resultStr = String.format("{res:[{'result':'%s'}]}", "can");
		} else {
			resultStr = String.format("{res:[{'result':'%s'}]}", "cant");
		}
		return resultStr;
	}

	@RequestMapping("/mobile/email_check")
	@ResponseBody
	public String email_check(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("request", request);
		MemberVO vo = service.email_check(map);
		String resultStr = "";
		if (vo == null) {
			resultStr = String.format("{res:[{'result':'%s'}]}", "can");
		} else {
			resultStr = String.format("{res:[{'result':'%s'}]}", "cant");
		}
		return resultStr;
	}

	@RequestMapping("/mobile/join")
	@ResponseBody
	public String join(HttpServletRequest request) {
		// System.out.println(request.getParameter("email"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("request", request);

		int result = service.join(map);
		String resultStr = "";

		if (result == 1) {
			resultStr = String.format("{res:[{'result':'%s'}]}", "success");
		} else {
			resultStr = String.format("{res:[{'result':'%s'}]}", "fail");
		}

		return resultStr;
	}

	@RequestMapping("/mobile/home")
	@ResponseBody
	public String home() {
		List<FoodVO> food_list = service.food_list();

		JSONArray food_list_json = new JSONArray();

		for (FoodVO vo : food_list) {
			JSONObject object = new JSONObject();
			object.put("idx", vo.getIdx());
			object.put("user_idx", vo.getUser_idx());
			object.put("user_nikname", vo.getUser_nikname());
			object.put("image", vo.getImage());
			object.put("subway", vo.getSubway());
			object.put("food", vo.getFood());
			object.put("content", vo.getContent());
			object.put("regidate", vo.getRegidate());
			food_list_json.add(object);
		}

		return food_list_json.toString();
	}

	@RequestMapping(value="/mobile/station",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String station(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("utf-8");
		String station = request.getParameter("station");
		List<StationVO> list = service.station(station);
		if (list.size() <= 0 || list == null) {
			return "";
		} else {
			JSONArray jsonList = new JSONArray();
			for (StationVO vo : list) {
				JSONObject obj = new JSONObject();
				obj.put("station_code", vo.getStation_code());
				obj.put("station_name", vo.getStation_name());
				obj.put("station_line", vo.getStation_line());
				obj.put("station_out_code", vo.getStation_out_code());
				jsonList.add(obj);
			}
			return jsonList.toJSONString();
		}
	}
	
	@RequestMapping("/mobile/upload_food")
	@ResponseBody
	public String upload_content_food(MultipartHttpServletRequest request){
		Map map = new HashMap<String, Object>();
		map.put("request", request);
		int result = service.upload_content_food(map);
		String resultStr = "";

		if (result == 1) {
			resultStr = String.format("{res:[{'result':'%s'}]}", "success");
		} else {
			resultStr = String.format("{res:[{'result':'%s'}]}", "fail");
		}

		return resultStr;
	}
	
	@RequestMapping("/mobile/upload_tip")
	@ResponseBody
	public String upload_content_tip() {
		
		return "";
	}

}
