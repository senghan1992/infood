package service;

import java.io.File;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import dao.InfoodWebDao;
import vo.FoodVO;

@Service
public class InfoodWebServiceImpl implements InfoodWebService{
	
	@Autowired
	InfoodWebDao dao;
	
	@Autowired
	ServletContext application;

	@Override
	public int upload(Model model) {
		String webPath = "/resources/upload/";
		String savePath = application.getRealPath(webPath);
		System.out.println(savePath);
		
		FoodVO vo = (FoodVO) (model.asMap()).get("vo");
		System.out.println(vo.getContent());
		
		MultipartFile photo = vo.getPhoto();
		String filename = "no_file";
		if(!photo.isEmpty()) {
			filename = photo.getOriginalFilename();
			
			//저장할 파일 경로
			File saveFile = new File(savePath);
			if(!saveFile.exists()) {
				saveFile.mkdirs();
			}else {
				//동일한 파일명이 있을 경우 현재 업로드 시간을 붙여서 중복을 방지한다
				long time = System.currentTimeMillis();
				filename = String.format("%d_%s", time, filename);
				saveFile = new File(savePath, filename);
				
				//업로드 된 파일이 MultipartResolver라는 클래스가 지정한 임시저장소에 있는데
				//임시저장소의 파일은 일정 시간이 지나면 사라지기 때문에 내가 지정한 경로로 복사
				try {
					photo.transferTo(saveFile);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		
		vo.setImage(filename);
		int result = dao.upload(vo);
		return result;
	}

}
