
package kr.co.seoulit.erp.hr.salary.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.seoulit.erp.hr.salary.applicationservice.JpaBaseExtSalServiceImpl;
import kr.co.seoulit.erp.hr.salary.entity.BaseExtSalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import kr.co.seoulit.erp.hr.salary.servicefacade.SalaryServiceFacade;
import kr.co.seoulit.erp.hr.salary.to.BaseExtSalTO;

//**************************2020-08-21 63기 손유찬 수정*********************************
@RestController
@CrossOrigin("*")
@RequestMapping("/hr/salary/*")
@Tag(name = "초과수당 기준 관리", description = "초과수당 관리 API")
public class BaseExtSalController {
	@Autowired
	private SalaryServiceFacade salaryServiceFacade;
	@Autowired
	private JpaBaseExtSalServiceImpl jpaBaseExtSalService;

	HashMap<String, Object> map = new HashMap<>();


	@Operation(summary = "초과수당 리스트 조회", description = "초과수당 리스트를 출력합니다.")
	@GetMapping("/searchBaseExtSalary")
	public HashMap<String, Object> findBaseExtSalList() {
		try {
//			ArrayList<BaseExtSalTO> baseExtSalList = salaryServiceFacade.findBaseExtSalList();
			ArrayList<BaseExtSalEntity> baseExtSalList = jpaBaseExtSalService.findBaseExtSalList();
			map.clear();
			map.put("baseExtSalList", baseExtSalList);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);

		} catch (Exception ioe) {
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		return map;
	}
	@Operation(summary = "초과수당 수정", description = "초과수당을 수정합니다.")
	@PutMapping("/updateBaseExtSalary")
	public  HashMap<String, Object> modifyBaseExtSalList(@RequestBody BaseExtSalEntity baseExtSal) {
		System.out.println(baseExtSal);
		try {
//			salaryServiceFacade.modifyBaseExtSal(baseExtSalTo);
			jpaBaseExtSalService.modifyBaseExtSal(baseExtSal);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);

		} catch (Exception ioe) {
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		return map;
	}

	@Operation(summary = "초과수당 등록", description = "초과수당을 등록합니다.")
	@PostMapping("/registerBaseExtSal")
	public HashMap<String,Object> registerBaseExtSal(@RequestBody BaseExtSalEntity baseExtSal){
		System.out.println(baseExtSal);
		try {
//			salaryServiceFacade.registerBaseExtSal(baseExtSalTo);
			jpaBaseExtSalService.registerBaseExtSal(baseExtSal);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);

		} catch (Exception ioe) {
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		return map;
	}

	@Operation(summary = "초과수당 삭제", description = "초과수당을 삭제합니다.")
	@DeleteMapping("/removeBaseExtSal")
	public HashMap<String,Object> removeBaseExtSal(@RequestParam String extSalCode){
		System.out.println(extSalCode);
		try {
//			salaryServiceFacade.removeBaseExtSal(extSalCode);
			jpaBaseExtSalService.deleteBaseExtSal(extSalCode);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);

		} catch (Exception ioe) {
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		return map;
	}
}
//**************************2020-08-21 63기 손유찬 수정*********************************
