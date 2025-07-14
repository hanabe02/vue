package kr.co.seoulit.erp.hr.conexpense.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import kr.co.seoulit.erp.hr.conexpense.servicefacade.ConExpenseServiceFacade;
import kr.co.seoulit.erp.hr.conexpense.to.ConExpenseTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@CrossOrigin("*")
@Tag(name = "경조비 신청", description = "경조비 신청 API")
@RestController
@RequestMapping("/hr/conexpense/*")
public class ConExpenseController {

    @Autowired
    private ConExpenseServiceFacade conExpenseServiceFacade;

    // 경조비 신청 내역 전체 조회
    @Operation(summary = "경조비 신청 내역 전체 조회", description = "경조비 신청 내역 전체를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @GetMapping(value = "conexp")
    public ResponseEntity<HashMap<String, Object>> getAllConExpenseList() {
        System.out.println("=====ConExpenseController=====");
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            ArrayList<ConExpenseTO> conExpenseList = conExpenseServiceFacade.getAllConExpenseList();
            System.out.println("===@--`--===" + conExpenseList);

            responseMap.put("conExpenseList", conExpenseList);
            responseMap.put("errorMsg", "success");
            responseMap.put("errorCode", 0);
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            responseMap.clear();
            responseMap.put("errorCode", -1);
            responseMap.put("errorMsg", e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    // 경조비 신청 내역 조회
    @Operation(summary = "경조비 신청 내역 조회", description = "경조비 신청 내역을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @GetMapping(value = "conexp/{startDate}/{endDate}/{searchEmpCode}")
    public ResponseEntity<HashMap<String, Object>> getConExpenseList(
            @PathVariable(required = false) String startDate,
            @PathVariable(required = false) String endDate,
            @PathVariable(required = false) String searchEmpCode
    ) {

        System.out.println("=====ConExpenseController======");
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            ArrayList<ConExpenseTO> conExpenseList = conExpenseServiceFacade.getConExpenseList(startDate, endDate, searchEmpCode);
            System.out.println("===@--`--===" + conExpenseList);

            responseMap.put("conExpenseList", conExpenseList);
            responseMap.put("errorMsg", "success");
            responseMap.put("errorCode", 0);
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            responseMap.clear();
            responseMap.put("errorCode", -1);
            responseMap.put("errorMsg", e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    // 경조비 신청 등록
    @Operation(summary = "경조비 신청 등록", description = "경조비를 신청합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PostMapping("conexp")
    public ResponseEntity<HashMap<String, Object>> insertConExpense(@RequestBody ConExpenseTO conExpenseTO) {
        System.out.println("=====ConExpenseController=====");
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            conExpenseServiceFacade.insertConExpense(conExpenseTO);
            responseMap.put("errorMsg", "success");
            responseMap.put("errorCode", 0);
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            responseMap.clear();
            responseMap.put("errorCode", -1);
            responseMap.put("errorMsg", e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    // 경조비 신청 수정
    @Operation(summary = "경조비 신청 수정", description = "경조비 신청 내역을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PutMapping("conexp")
    public ResponseEntity<HashMap<String, Object>> updateConExpense(@RequestBody ConExpenseTO conExpenseTO) {
        System.out.println("=====ConExpenseController=====");
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            conExpenseServiceFacade.updateConExpense(conExpenseTO);
            responseMap.put("errorMsg", "success");
            responseMap.put("errorCode", 0);
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            responseMap.clear();
            responseMap.put("errorCode", -1);
            responseMap.put("errorMsg", e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    // 경조비 신청 삭제
    @Operation(summary = "경조비 신청 삭제", description = "경조비 신청 내역을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @DeleteMapping("conexp")
    public ResponseEntity<HashMap<String, Object>> deleteConExpense(@RequestParam String reqNum) {
        System.out.println("=====ConExpenseController=====");
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            conExpenseServiceFacade.deleteConExpense(reqNum);
            responseMap.put("errorMsg", "success");
            responseMap.put("errorCode", 0);
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            responseMap.clear();
            responseMap.put("errorCode", -1);
            responseMap.put("errorMsg", e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    @Operation(summary = "경조비 신청 승인/반려 처리", description = "경조비 신청 상태를 승인 또는 반려로 변경합니다.")
    @PutMapping("conexp/status")
    public ResponseEntity<HashMap<String, Object>> updateApprovalStatus(@RequestBody HashMap<String, Object> paramMap) {
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            List<String> reqNumList = (List<String>) paramMap.get("reqNumList");
            String status = (String) paramMap.get("status");

            conExpenseServiceFacade.updateApprovalStatus(reqNumList, status);

            responseMap.put("errorMsg", "success");
            responseMap.put("errorCode", 0);
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            responseMap.put("errorCode", -1);
            responseMap.put("errorMsg", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
        }
    }

}
