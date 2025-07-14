package kr.co.seoulit.erp.hr.attendance.dao;

import kr.co.seoulit.erp.hr.attendance.to.ElasticTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mapper
public interface ElasticDAO {
	public ArrayList<ElasticTO> selectElasticList(Map<String, Object> map);

//	public void insertElastic(ElasticTO data);

	void insertElastic(Map<String, Object> map);

	void updateElastic(Map<String, Object> map);

	void deleteElasticOne(int eno);

	public void deleteElastic(ElasticTO elastic);
}
