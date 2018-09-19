package dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vo.FoodVO;

@Repository
public class InfoodWebDaoImpl implements InfoodWebDao{
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public int upload(FoodVO vo) {
		int result = sqlSession.insert("web.upload", vo);
		return result;
	}

}
