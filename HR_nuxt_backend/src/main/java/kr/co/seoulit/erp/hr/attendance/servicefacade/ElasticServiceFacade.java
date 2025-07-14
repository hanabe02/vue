package kr.co.seoulit.erp.hr.attendance.servicefacade;

import kr.co.seoulit.erp.hr.attendance.to.ElasticTO;

import java.util.ArrayList;

public interface ElasticServiceFacade {
	public ArrayList<ElasticTO> findElasticList(String empCode, String startDate, String endDate);
//	public void insertElastic(ArrayList<ElasticTO> data);
	public void insertElastic(ElasticTO elasticTO);

	void modifyElasticOne(ElasticTO elasticTO);

	void removeElasticOne(int eno);
	public void deleteElastic(ArrayList<ElasticTO> elasticDelData);
}
