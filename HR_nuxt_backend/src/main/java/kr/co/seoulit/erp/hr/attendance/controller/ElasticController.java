package kr.co.seoulit.erp.hr.attendance.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.seoulit.erp.hr.attendance.servicefacade.ElasticServiceFacade;
import kr.co.seoulit.erp.hr.attendance.to.ElasticTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "유연근무", description = "유연근무 API")
@CrossOrigin("*")
@RequestMapping("/hr/attendance/*")
@RestController
public class ElasticController {

	@Autowired
	private ElasticServiceFacade elasticServiceFacade;

	// 초과근무 조회
	@GetMapping("/findElastic")
	public ResponseEntity<Map<String, Object>> findElasticList(
			@RequestParam("empCode") String empCode, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			ArrayList<ElasticTO> elasticList = elasticServiceFacade.findElasticList(empCode, startDate, endDate);
			map.put("elasticList", elasticList);
			map.put("errorCode", 0);
			map.put("errorMsg", "success");
			System.out.println("::::: [findElastic] Map :::::" + map);
			return ResponseEntity.ok(map);
		} catch (Exception e2) {
			e2.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e2.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
	}


	// 초과근무 등록
	@PostMapping("/insertElastic")
	public ResponseEntity<Map<String, Object>> insertElastic(@RequestBody ElasticTO elasticTO) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			elasticServiceFacade.insertElastic(elasticTO);
			map.put("errorCode", 0);
			map.put("errorMsg", "success");
			return ResponseEntity.ok(map);
		} catch (Exception e2) {
			e2.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e2.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
	}

	// 초과근무 수정
	@PostMapping("/updateElastic")
	public ResponseEntity<Map<String, Object>> modifyElastic (@RequestBody ElasticTO elasticTO) {
		Map<String, Object> map = new HashMap<>();
		try {
			elasticServiceFacade.modifyElasticOne(elasticTO);
			map.put("errorCode", 0);
			map.put("errorMsg", "success");
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			map.clear();
			e.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
	}

	// 초과근무 삭제
	@DeleteMapping("/deleteElasticOne")
	public ResponseEntity<Map<String, Object>> removeElasticOne (@RequestParam int eno) {
		Map<String, Object> map = new HashMap<>();
		try {
			elasticServiceFacade.removeElasticOne(eno);
			map.put("errorCode", 0);
			map.put("errorMsg", "success");
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			map.clear();
			e.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
	}

	// 초과근무 여러개 선택해서 리스트로 삭제하는 방법인듯?
	// 2021/09/09 범석 ~~~~~~ 탄력근무제 삭제
	@RequestMapping(value = "/elasticDelete", method = RequestMethod.POST)
	public void deleteDayAttd(@RequestBody Map<String, ArrayList<ElasticTO>> data) {
		System.out.println("server까지 날라옴");
		System.out.println(data);
		ArrayList<ElasticTO> elasticDelData = data.get("elasticDelData");
		System.out.println("Controller" + elasticDelData);
		elasticServiceFacade.deleteElastic(elasticDelData);

	}
}
