package kr.co.seoulit.erp.logistic.production.controller;

import java.util.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.seoulit.erp.logistic.production.servicefacade.MrpServiceFacade;
import kr.co.seoulit.erp.logistic.production.to.MpsTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import kr.co.seoulit.erp.logistic.production.to.MrpTO;

import static kr.co.seoulit.erp.sys.to.response.Response.success;

@Api(description = "소요량전개(MRP)")
@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/logi/logistics/production/*", produces = "application/json")
public class MrpController {

	private final MrpServiceFacade mrpSF;
	@Autowired
	public MrpController(MrpServiceFacade mrpSF) {
		this.mrpSF = mrpSF;
	}

	/*****************************
			 MPS 테이블 조회
	 *****************************/
	@ApiOperation(value = "MPS 테이블 조회")
	@RequestMapping("/searchMpsInfo")
	public Map<String, List<MpsTO>> getMpsList(@RequestParam String startDate,
											   @RequestParam String endDate) {
		System.out.println("\n"+"디버깅 포인트 : mps 수주조회 " + "\n");

		Map<String, List<MpsTO>> mpsList = mrpSF.getMpsList(startDate, endDate);
		return mpsList;
	}

	/*****************************
			    MPS 수정
	 *****************************/
	@ApiOperation(value = "MPS 수정")
	@RequestMapping(value="/updateMps", method = RequestMethod.POST)
	public void updateMps(@RequestBody MpsTO mpsTO) {
		mrpSF.updateMps(mpsTO);
	}

	/*****************************
	          MRP 모의전개
	 *****************************/
	@ApiOperation(value = "MRP 모의전개")
	@RequestMapping("/openMrp")
	public HashMap<String, Object> openMrp(@RequestParam String mpsNo) {
		System.out.println("\n"+"디버깅 포인트 : mrp 모의전개"+ "\n");
		HashMap<String, Object> map = mrpSF.openMrp(mpsNo);
		return map;
	}

	/*****************************
	 			MRP 등록
	 *****************************/

	@ApiOperation(value = "MRP 등록")
	@RequestMapping(value = "/registerMrp", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<HashMap<String, Object>> registerMrp(@RequestBody Map<String, Object> params) {
		System.out.println("\n"+"디버깅 포인트 : mrp 등록");
		System.out.println(params + "\n");
		String mrpRegisterDate = params.get("mrpRegisterDate").toString();
		ObjectMapper mapper = new ObjectMapper();

		// 이건 뭐지??
		System.out.println("\n" + "디버깅 포인트 실행시작 : mapper.configure" + "\n");
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		System.out.println("\n" + "디버깅 포인트 실행종료 : mapper.configure" + "\n");

		System.out.println("\n" + "디버깅 포인트 실행시작 : mapper.convertValue" + "\n");
		ArrayList<MrpTO> newMrpList = mapper.convertValue(params.get("batchList"),
				TypeFactory.defaultInstance().constructCollectionType(List.class, MrpTO.class));
		System.out.println("\n" + "디버깅 포인트 실행종료 : mapper.convertValue" + "\n");

		System.out.println("\n" + "디버깅 포인트 실행시작 : mrpSF.registerMrp" + "\n");
		HashMap<String, Object> resultMap = mrpSF.registerMrp(mrpRegisterDate, newMrpList);
		System.out.println("\n" + "디버깅 포인트 실행종료 : mrpSF.registerMrp" + "\n");

		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

}
