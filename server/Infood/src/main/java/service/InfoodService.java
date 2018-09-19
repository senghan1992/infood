package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vo.FoodVO;
import vo.MemberVO;
import vo.StationVO;

public interface InfoodService {
	//닉네임 중복체크
	public MemberVO check(Map<String, Object> map);
	//이메일 중복체크
	public MemberVO email_check(Map<String , Object> map);
	//회원가입
	public int join(Map<String, Object> map);
	//로그인
	public MemberVO login(Map<String, Object> map);
	//로그인(가입된 회원인지 먼저 판별)
	public MemberVO user_check(Map<String, Object> map);
	//로그인이 되면 최근 로그인이 현재 시간으로 변경
	public int last_login(Map<String, Object> map);
	//홈화면 리스트뷰 가져오기
	public List<FoodVO> food_list();
	
	//실시간 지하철역 검색
	public List<StationVO> station(String station);
	
	//음식 업로드
	public int upload_content_food(Map<String,Object>map);
}
