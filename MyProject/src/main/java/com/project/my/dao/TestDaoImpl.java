package com.project.my.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.project.my.bean.MemberDto;
import com.project.my.bean.TestBean;

@Repository
public class TestDaoImpl implements TestDao {
	private static final String namespace = "com.project.my.testMapper";

	@Inject
	private SqlSession sqlSession;

	@Override
	public int count() throws Exception {
		return sqlSession.selectOne(namespace + ".count");
	}
	
	@Override
	public List<TestBean> selectTest(Map<String, Integer> param) throws Exception {
		return sqlSession.selectList(namespace + ".test", param);
	}

	@Override
	public TestBean detailTest(Long id) throws Exception {
		return sqlSession.selectOne(namespace+ ".detailTest", id);
	}

	@Override
	public void insertTest(Map<String, String> param) throws Exception {
		sqlSession.insert(namespace + ".insertTest", param);
	}

	@Override
	public void updateTest(Map<String, Object> param) throws Exception {
		sqlSession.update(namespace + ".updateTest", param);
	}
	
	@Override
	public void deleteTest(Long id) throws Exception {
		sqlSession.delete(namespace + ".deleteTest", id);
	}

	@Override
	public void signupTest(Map<String, Object> param) throws Exception {
		sqlSession.insert(namespace + ".signupTest", param);
	}

	@Override
	public MemberDto loginTest(Map<String, Object> param) throws Exception {
		return sqlSession.selectOne(namespace + ".loginTest", param);
	}
	
	@Override
	public MemberDto checkTest(String email) throws Exception {
		return sqlSession.selectOne(namespace + ".checkTest", email);
	}
}
