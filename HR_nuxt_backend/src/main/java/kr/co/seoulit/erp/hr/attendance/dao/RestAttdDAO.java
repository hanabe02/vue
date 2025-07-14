package kr.co.seoulit.erp.hr.attendance.dao;

import kr.co.seoulit.erp.hr.attendance.to.RestAttdTO;
import kr.co.seoulit.erp.hr.base.to.HrDetailCodeTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mapper
public interface RestAttdDAO {
	void insertRequestVacation(RestAttdTO restAttdTO);

	void updateVacation(RestAttdTO restAttdTO);

	ArrayList<RestAttdTO> selectTravelAndEducationList(@Param("empCode") String empCode,
													   @Param("startDate") String startDate,
													   @Param("endDate") String endDate);
	void updateTravelAndEducationList(RestAttdTO restAttdTO);
	void deleteTravelAndEducationList(Map<String, Object> map);


	ArrayList<RestAttdTO> selectRestAttdListByEmpCodeAndYM(@Param("empCode") String empCode,
														   @Param("yearMonth") String yearMonth);
	ArrayList<RestAttdTO> selectRestAttdListByAnnulVacation(@Param("empCode") String empCode,
															@Param("yearMonth") String yearMonth);
	void insertRestAttd(RestAttdTO restAttd);

	void updateRestAttd(HashMap<String, String> attdApplMap);





	ArrayList<RestAttdTO> selectRestAttdListByToday(String empCode, String toDay);

	ArrayList<RestAttdTO> selectRestAttdList(HashMap<String, Object> map);

	ArrayList<RestAttdTO> selectRestAttdListCode(HashMap<String, Object> map);

	ArrayList<RestAttdTO> selectRestAttdListByDept(HashMap<String, String> attdApplMap);

	ArrayList<RestAttdTO> selectRestAttdListByAllDept(String applyDay);



	void deleteRestAttd(@Param("restAttdCode") String restAttdCode);

	void deleteRestAttdlist(ArrayList<RestAttdTO> restAttdList);

	ArrayList<HrDetailCodeTO> selectRestDatailCodeName();
}
