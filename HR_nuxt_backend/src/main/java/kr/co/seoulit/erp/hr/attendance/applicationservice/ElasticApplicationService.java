package kr.co.seoulit.erp.hr.attendance.applicationservice;

import kr.co.seoulit.erp.hr.attendance.to.ElasticTO;

import java.util.ArrayList;
import java.util.Map;

public interface ElasticApplicationService {
	public ArrayList<ElasticTO> findElasticList(String empCode, String startDate, String endDate);

//	public void insertElastic(ArrayList<ElasticTO> data);
	public void insertElastic(Map<String, Object> map);

	void updateElastic(Map<String, Object> map);
	void deleteElasticOne(int eno);

	public void deleteElastic(ArrayList<ElasticTO> elasticDelData);
}
