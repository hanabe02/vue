package kr.co.seoulit.erp.hr.attendance.servicefacade;

import kr.co.seoulit.erp.hr.attendance.applicationservice.ElasticApplicationService;
import kr.co.seoulit.erp.hr.attendance.to.ElasticTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class ElasticServiceFacadeImpl implements ElasticServiceFacade{

	@Autowired
	private ElasticApplicationService elasticApplicationService;

	@Override
	public ArrayList<ElasticTO> findElasticList(String empCode, String startDate, String endDate) {

		ArrayList<ElasticTO> elasticList=elasticApplicationService.findElasticList(empCode, startDate, endDate);
			return elasticList;
		}

//	@Override
//	public void insertElastic(ArrayList<ElasticTO> data) {
//		// TODO Auto-generated method stub
//		elasticApplicationService.insertElastic(data);
//	}

	@Override
	public void insertElastic(ElasticTO elasticTO) {
		Map<String, Object> map = new HashMap<>();
		map.put("empCode", elasticTO.getEmpCode());
		map.put("startTime", elasticTO.getStartTime());
		map.put("endTime", elasticTO.getEndTime());
		map.put("applyDay", elasticTO.getApplyDay());
		map.put("eno", elasticTO.getEno());
		System.out.println("::::: [insertElastic] map :::::= " + map);
		elasticApplicationService.insertElastic(map);
	}

	@Override
	public void modifyElasticOne(ElasticTO elasticTO) {
		Map<String, Object> map = new HashMap<>();
		map.put("empCode", elasticTO.getEmpCode());
		map.put("startTime", elasticTO.getStartTime());
		map.put("endTime", elasticTO.getEndTime());
		map.put("applyDay", elasticTO.getApplyDay());
		map.put("eno", elasticTO.getEno());
		elasticApplicationService.updateElastic(map);
	}

	@Override
	public void removeElasticOne(int eno) {
		elasticApplicationService.deleteElasticOne(eno);
	}

	@Override
	public void deleteElastic(ArrayList<ElasticTO> elasticDelData) {
		System.out.println("facade"+elasticDelData);
		elasticApplicationService.deleteElastic(elasticDelData);
	}
}
