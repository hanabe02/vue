package kr.co.seoulit.erp.hr.attendance.applicationservice;

import kr.co.seoulit.erp.hr.attendance.dao.ElasticDAO;
import kr.co.seoulit.erp.hr.attendance.to.ElasticTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Component
public class ElasticApplicationServiceImpl implements ElasticApplicationService{
	@Autowired
	private ElasticDAO elasticDAO;

	@Override
	public ArrayList<ElasticTO> findElasticList(String empCode, String startDate, String endDate) {
		Map<String, Object> map = new HashMap<>();
		map.put("empCode", empCode);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		ArrayList<ElasticTO> elasticList=elasticDAO.selectElasticList(map);

		for (ElasticTO elasticTO : elasticList) {
			String startTime = elasticTO.getStartTime();
			String endTime = elasticTO.getEndTime();

			if (startTime.length() == 3) {
				StringBuffer t1 = new StringBuffer(startTime);
				t1.insert(1, ":");
				String t2 = t1.toString();
				elasticTO.setStartTime(t2);
			} else if (startTime.length() == 4) {
				StringBuffer tt1 = new StringBuffer(startTime);
				tt1.insert(2, ":");
				String tt2 = tt1.toString();
				elasticTO.setStartTime(tt2);
			}

			if (endTime.length() == 3) {
				StringBuffer t1 = new StringBuffer(endTime);
				t1.insert(1, ":");
				String t2 = t1.toString();
				elasticTO.setEndTime(t2);
			} else if (endTime.length() == 4) {
				StringBuffer tt1 = new StringBuffer(endTime);
				tt1.insert(2, ":");
				String tt2 = tt1.toString();
				elasticTO.setEndTime(tt2);
			}
		}

		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!"+elasticList);

		return elasticList;
	}

//	@Override
//	public void insertElastic(ArrayList<ElasticTO> data) {
//		// TODO Auto-generated method stub
//		for(ElasticTO elasticdata : data) {
//			elasticDAO.insertElastic(elasticdata);
//			}
//	}

	@Override
	public void insertElastic(Map<String, Object> map) {
			elasticDAO.insertElastic(map);
	}

	@Override
	public void updateElastic(Map<String, Object> map) {
		String startTime = (String) map.get("startTime");
		String endTime = (String) map.get("endTime");

		String[] start = startTime.split(":");
		int startTime1 = Integer.parseInt(start[0] + start[1]);
		System.out.println("startTime1 = " + startTime1);
		map.put("startTime", startTime1);

		String[] end = endTime.split(":");
		int endTime1 = Integer.parseInt(end[0] + end[1]);
		System.out.println("endTime1 = " + endTime1);
		map.put("endTime", endTime1);

		elasticDAO.updateElastic(map);
	}

	@Override
	public void deleteElasticOne(int eno) {
		elasticDAO.deleteElasticOne(eno);
	}

	@Override
	public void deleteElastic(ArrayList<ElasticTO> elasticDelData) {
		// TODO Auto-generated method stub

		System.out.println("ApplicationService"+elasticDelData);

		for(ElasticTO elastic : elasticDelData) {
			System.out.println(elastic);
			elasticDAO.deleteElastic(elastic);
			}

	}
}
