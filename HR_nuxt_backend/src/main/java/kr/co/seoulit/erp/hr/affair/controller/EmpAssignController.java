package kr.co.seoulit.erp.hr.affair.controller;

import java.util.ArrayList;
import java.util.HashMap;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.seoulit.erp.hr.affair.to.AppointTO;
import kr.co.seoulit.erp.hr.affair.to.RegiAppTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kr.co.seoulit.erp.hr.affair.servicefacade.EmpServiceFacade;



@CrossOrigin("*")
@Tag(name = "직원 발령", description = "직원 발령 API")
@RestController
@RequestMapping("/hr/affair/*")
public class  EmpAssignController {

	@Autowired
	private EmpServiceFacade empServiceFacade;


	// 직원 발령 등록
	@Operation(summary = "직원 발령 등록", description = "직원 발령을 등록합니다.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK !!"),
	})
	@PostMapping(value = "appoint")
	@ResponseBody
	public void appointEmployment(@RequestBody ArrayList<RegiAppTO> appRegi) {


		try {
			System.out.println("=========================EmpAssignController=======================");
			System.out.println("appRegi : " + appRegi);
			for (RegiAppTO regiApp : appRegi) {
				System.out.println("appRegi : " + regiApp);
				empServiceFacade.addAssignInfo(regiApp);
			}

		} catch (Exception e2) {
			e2.printStackTrace();

		}
	}

	// 직원 발령 내역 조회
	@Operation(summary = "직원 발령 조회", description = "직원 내역을 조회합니다.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK !!"),
	})
	@GetMapping(value = "appoint")
	public HashMap<String, Object> findAppointList() {

		System.out.println("=========================EmpAssignController=======================");
		HashMap<String, Object> appointList=new  HashMap<>();
		try {
			appointList.put("appoint",empServiceFacade.findAppointList());
			System.out.println("=========================두번쨰======================="+appointList);
			appointList.put("errorMsg", "success");
			appointList.put("errorCode", 0);
		} catch (Exception ioe) {
			appointList.clear();
			appointList.put("errorCode", -1);
			appointList.put("errorMsg", ioe.getMessage());
		}
		return appointList;
	}

	// 직원 발령 승인 및 반려
	@Operation(summary = "직원 발령 결재", description = "직원 발령을 승인 반려 합니다.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK !!"),
	})
	@PutMapping(value = "appoint")
	public HashMap<String, Object> AppointResult(@RequestBody AppointTO appResult) {

		System.out.println("=========================AppointResult======================="+appResult);
		HashMap<String, Object> appointList=new  HashMap<>();
		try {
			empServiceFacade.updateAppointment(appResult);
			System.out.println("=========================두번쨰======================="+appointList);
			appointList.put("errorMsg", "success");
			appointList.put("errorCode", 0);
		} catch (Exception ioe) {
			appointList.clear();
			appointList.put("errorCode", -1);
			appointList.put("errorMsg", ioe.getMessage());
		}
		return appointList;
	}


	// 직원 발령 삭제
	@Operation(summary = "직원 발령 삭제", description = "직원 발령을 삭제합니다.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK !!"),
	})
	@DeleteMapping(value = "appoint")
	public HashMap<String, Object> AppointDelete(@RequestParam String approveAppoint) {
		HashMap<String, String> delapp=new  HashMap<>();
		delapp.put("appointNo", approveAppoint);
		System.out.println("=========================AppointResult======================="+delapp);
		HashMap<String, Object> appointList=new  HashMap<>();
		try {
			empServiceFacade.deleteAppointment(delapp);
			System.out.println("=========================두번쨰======================="+appointList);
			appointList.put("errorMsg", "success");
			appointList.put("errorCode", 0);
		} catch (Exception ioe) {
			appointList.clear();
			appointList.put("errorCode", -1);
			appointList.put("errorMsg", ioe.getMessage());
		}
		return appointList;
	}


	// 직원 발령 내역 조회
	@Operation(summary = "직원 발령 조회", description = "직원 발령내용을 조회합니다.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK !!"),
	})
	@GetMapping(value = "appoint/log")
	public HashMap<String, Object> findAppointLog() {

		System.out.println("=========================EmpAssignController=======================");
		HashMap<String, Object> appointList=new  HashMap<>();
		try {
			appointList.put("appoint",empServiceFacade.findAppointLog());
			System.out.println("=========================두번쨰======================="+appointList);
			appointList.put("errorMsg", "success");
			appointList.put("errorCode", 0);
		} catch (Exception ioe) {
			appointList.clear();
			appointList.put("errorCode", -1);
			appointList.put("errorMsg", ioe.getMessage());
		}
		return appointList;
	}



}
