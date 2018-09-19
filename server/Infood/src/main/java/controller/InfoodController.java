package controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import service.InfoodWebService;
import vo.FoodVO;

@Controller
public class InfoodController {
	
	@Autowired
	InfoodWebService service;

	public String VIEW_PATH = "/WEB-INF/views/infood/";

	@RequestMapping("/upload")
	public String upload() {
		return VIEW_PATH + "upload.jsp";
	}
	
	@RequestMapping("/upload.do")
	public String doUpload(FoodVO vo, Model model) {
		model.addAttribute("vo", vo);
		System.out.println(vo.getContent());
		int result = service.upload(model);
		System.out.println(result+"");
		return "redirect:upload";
	}

}
