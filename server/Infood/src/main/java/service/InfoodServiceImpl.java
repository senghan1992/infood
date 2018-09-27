package service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import dao.InfoodDao;
import utill.PwdSecurity;
import vo.FoodVO;
import vo.MemberVO;
import vo.StationVO;

@Service
public class InfoodServiceImpl implements InfoodService {

	@Autowired
	InfoodDao dao;

	@Autowired
	ServletContext application;

	@Override
	public int join(Map<String, Object> map) {
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String email = request.getParameter("email");
		String nikname = request.getParameter("nikname");
		String pwd = request.getParameter("pwd");

		// MemberVO vo = dao.check(email, nikname);
		String shaPwd = PwdSecurity.getSHA256(pwd);
		int result = dao.join(email, shaPwd, nikname);

		/*
		 * if(vo == null) { result = -1; }else { String shaPwd =
		 * PwdSecurity.getSHA256(pwd); result = dao.join(email, shaPwd, nikname); }
		 */

		return result;
	}

	@Override
	public MemberVO login(Map<String, Object> map) {
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		String shaPwd = PwdSecurity.getSHA256(pwd);
		
		MemberVO vo = dao.login(email, shaPwd);

		return vo;
	}

	@Override
	public MemberVO user_check(Map<String, Object> map) {
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String email = request.getParameter("email");
		MemberVO vo = dao.user_check(email);
		return vo;
	}

	@Override
	public int last_login(Map<String, Object> map) {
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		String shaPwd = PwdSecurity.getSHA256(pwd);
		int result = dao.last_login(email, shaPwd);
		return result;
	}

	@Override
	public MemberVO check(Map<String, Object> map) {
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String nikname = request.getParameter("nikname");
		MemberVO vo = dao.check(nikname);
		return vo;
	}

	@Override
	public MemberVO email_check(Map<String, Object> map) {
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String email = request.getParameter("email");
		MemberVO vo = dao.email_check(email);
		return vo;
	}

	@Override
	public List<FoodVO> food_list() {
		List<FoodVO> list = dao.food_list();
		return list;
	}

	@Override
	public List<StationVO> station(String station) {
		List<StationVO> list = dao.station(station);
		return list;
	}

	@Override
	public int upload_content_food(Map<String, Object> map) {
		String webPath = "/resources/upload/";
		String savePath = application.getRealPath(webPath);
		int max = 10 * 1024 * 1024;
		boolean isSuccess = false;
		String saveFileName = "";
		String subway, content, food, user_idx, user_nikname;

		File dir = new File(savePath);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}

		MultipartHttpServletRequest request = (MultipartHttpServletRequest) map.get("request");
		MultipartFile test = request.getFile("file");
		if(test == null) {
			System.out.println("null");
		}else {
			String originalFileName = test.getOriginalFilename();
			saveFileName = originalFileName;
			if (saveFileName != null && !saveFileName.equals("")) {
				try {
					test.transferTo(new File(savePath + saveFileName));
					isSuccess = true;
				} catch (IllegalStateException e) {
					e.printStackTrace();
					isSuccess = false;
				} catch (IOException e) {
					e.printStackTrace();
					isSuccess = false;
				}
			}
		}
		subway = request.getParameter("subway");
		content = request.getParameter("content");
		food = request.getParameter("food");
		user_idx = request.getParameter("user_idx");
		user_idx = user_idx.replace("\"", "");
		user_nikname = request.getParameter("user_nikname");
		user_nikname = user_nikname.replace("\"", "");
		
		int userIdx = Integer.parseInt(user_idx);
		
		int result = dao.upload_content_food(userIdx, user_nikname, saveFileName, subway, food, content);
		
		return result;
	}

}
