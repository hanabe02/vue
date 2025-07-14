package kr.co.seoulit.erp.hr.affair.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.seoulit.erp.hr.affair.to.PrintCertificateTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import kr.co.seoulit.erp.hr.affair.servicefacade.CertificateServiceFacade;
import kr.co.seoulit.erp.hr.affair.to.CertificateTO;

@CrossOrigin("*")
@RestController
@RequestMapping("/hr/certificate/*")
public class CertificateApprovalController {
	
	@Autowired
	private CertificateServiceFacade certificateServiceFacade;
	private final ModelMap modelMap = new ModelMap();

	// 조회
	@GetMapping("/findCertificateListByDept")
	public ModelMap findCertificateListByDept(@RequestParam String deptName, @RequestParam String startDate,
			@RequestParam String endDate) {
		try {

			ArrayList<CertificateTO> certificateList = certificateServiceFacade.findCertificateListByDept(deptName,
					startDate, endDate);
			modelMap.put("certificateList", certificateList);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
			modelMap.put("errorCode", -1);
		}

		return modelMap;
	}

	// 승인
	@PostMapping("/approvalCertificateList")
	public ModelMap approvalCertificateList(@RequestBody ArrayList<CertificateTO> certificateList) {
		try {
			certificateServiceFacade.approvalCertificateList(certificateList);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
			modelMap.put("errorCode", -1);
		}
		return modelMap;
	}

	@PatchMapping("/updateUseDate/{certificateNo}")
	public ResponseEntity<Map<String, Object>> upgradeUseDate(@PathVariable String certificateNo) {
		Map<String, Object> map = new HashMap<>();
		try {
			certificateServiceFacade.upgradeUseDate(certificateNo);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
			return ResponseEntity.status(HttpStatus.OK).body(map);
		} catch (Exception e) {
			map.put("errorCode", -1);
			map.put("errorMsg", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
	}

	@GetMapping("/printCertificate")
	public ResponseEntity<Map<String, Object>> printCertificate(String certificateNo) {
		Map<String, Object> map = new HashMap<>();
		try {
			PrintCertificateTO printCertificate = certificateServiceFacade.printCertificate(certificateNo);
			map.put("printCertificate", printCertificate);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
			return ResponseEntity.status(HttpStatus.OK).body(map);
		} catch (Exception e) {
			map.put("errorCode", -1);
			map.put("errorMsg", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
	}
}

// 여러개 승인 ✔️
// 승인대기, 승인완료만 조회되야함. 승인반려, 승인취소는 안보여야함. 신청페이지에서 조회시 완료는 조회X ✔️
// 반려된 건을 수정 시 다시 승인대기 상태가 됨. ✔️
// 승인취소된 건을 수정 시 다시 승인대기 상태가 됨. ✔️
// 반려된 건을 승인 완료하면 반려사유가 초기화됨. ✔️
// 승인완료된 상태는 삭제, 수정불가. 승인완료된 건은 승인취소 후 삭제가능. ✔️

// 승인완료된 건만 출력 가능. ✔️
// 출력시 사용일자가 등록되고 엑셀형식으로 출력됨. ✔️
// 출력시 증명서에 들어가야할 정보: 이름, 주민번호, 주소등, 근로기간, 직급,... printCertificateTO 참고 ✔️

// 승인 반려는 승인 과정 자체를 거부하거나 취소하는 것이며,
// 승인 취소는 이미 완료된 승인을 취소하는 것입니다.
// 두 용어는 프로세스의 다른 단계에서 발생하며, 요청을 거부하거나 취소하는 목적과 시점이 다를 수 있습니다.

