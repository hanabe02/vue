package kr.co.seoulit.erp.hr.affair.controller;

import kr.co.seoulit.erp.hr.affair.servicefacade.EmpServiceFacade;
import kr.co.seoulit.erp.hr.affair.to.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/hr/affair/*")
public class EmpDetailController {
	@Autowired
	private EmpServiceFacade empServiceFacade;

	private ModelMap modelMap = new ModelMap();
	private String empCode = null;

	//파일 등록용
	public String empcode() {return this.empCode;}





	//******************************************************2023-05-16 75기 인사 **************************************************** */









	@RequestMapping(value = "/empDetail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findAllEmployeeInfo(@RequestParam String company, @RequestParam String workPlace,
												   @RequestParam String position, @RequestParam String empName) {

		ArrayList<EmpTO> empDetailedList = null;

		try {
			System.out.println("=========================EmpDetailController=======================");
			System.out.println("company : " + company);
			System.out.println("workPlace : " + workPlace);
			System.out.println("position : " + position);
			System.out.println("empName : " + empName);

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("company", company);
			map.put("workPlace", workPlace);
			map.put("position", position);
			map.put("empName", empName);

			empDetailedList = empServiceFacade.findAllEmpInfo(map);

			modelMap.put("gridRowJson", empDetailedList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "����");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}
		return modelMap;
	}

	@GetMapping("/empDetailInfo")
	public Map<String, Object> findEmpDetail(@RequestParam String empCode) {
		ArrayList<EmployeeDetailTO> empDetail = null;
		try {
			empDetail = empServiceFacade.findEmpDetail(empCode);
			modelMap.put("empDetailInfo", empDetail);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "success");
		} catch (Exception e) {
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e);
		}
		return modelMap;
	}


//******************************************************2023-05-16 75기 인사  끝 **************************************************** */


	//은비 사원상세 기본 조회
	@RequestMapping(value = "/searchEmpBasicInfo", method = RequestMethod.GET)
	public ModelMap searchEmpBasicInfo(@RequestParam String empCode, HttpServletResponse response) {

		ArrayList<EmployeeBasicTO> basicInfoList = null;

		try {

			basicInfoList = empServiceFacade.getEmpBasicInfo(empCode);

			modelMap.put("gridRowJson", basicInfoList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (DataAccessException e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}
		System.out.println("나옵니다요오오옹 " + modelMap);
		return modelMap;
	}

}
