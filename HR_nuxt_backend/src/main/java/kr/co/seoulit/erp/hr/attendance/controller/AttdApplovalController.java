package kr.co.seoulit.erp.hr.attendance.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.seoulit.erp.hr.attendance.servicefacade.AttdServiceFacade;
import kr.co.seoulit.erp.hr.attendance.to.RestAttdTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Tag(name = "일근태승인", description = "일근태승인 API")
@CrossOrigin("*")
@RestController
@RequestMapping("/hr/attendance/*")
public class AttdApplovalController {

	@Autowired
	private AttdServiceFacade attdServiceFacade;
	private ModelMap modelMap = new ModelMap();


	@GetMapping(value = "/attendanceApploval")    //조회하기
	public ModelMap findRestAttdListByDept(
			@RequestParam HashMap<String, String> attdApplMap,
			HttpServletResponse response) {
		System.out.println("<< findRestAttdListByDept >>");
		System.out.println(attdApplMap);
		try {
			response.setContentType("application/json; charset=UTF-8");
			ArrayList<RestAttdTO> restAttdList = attdServiceFacade.findRestAttdListByDept(attdApplMap);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
			modelMap.put("restAttdList", restAttdList);
		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		return modelMap;
	}
	@PostMapping(value = "/attendanceApploval")   //입력하기
	public ModelMap modifyRestAttdList(@RequestBody HashMap<String, Object> checkData) {

		String deptCode = (String) checkData.get("deptCode");
		String startDate = (String) checkData.get("startDate");
		String endDate = (String) checkData.get("endDate");

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 파라미터Map에서 TO에 들어있지 않는 변수가 있어도
																					// 무시함.
		List<RestAttdTO> restAttdTo = mapper.convertValue(checkData.get("checkData"),
				TypeFactory.defaultInstance().constructCollectionType(List.class, RestAttdTO.class));// https://zorba91.tistory.com/28

		// System.out.println("| deptCode : "+deptCode+" | startDate : "+startDate+" |
		// endDate : "+endDate+" | ");
		// System.out.println("restAttdTo : "+restAttdTo);
		try {
			ArrayList<RestAttdTO> restAttdList = attdServiceFacade.modifyRestAttdList(restAttdTo, deptCode, startDate,
					endDate);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
			modelMap.put("restAttdList", restAttdList);
		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		return modelMap;
	}
}

