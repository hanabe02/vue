package kr.co.seoulit.erp.sys.controller;

import kr.co.seoulit.erp.hr.affair.to.EmpInfoTO;
import kr.co.seoulit.erp.sys.exception.IdNotFoundException;
import kr.co.seoulit.erp.sys.exception.PwMissMatchException;
import kr.co.seoulit.erp.sys.to.LoginTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import kr.co.seoulit.erp.sys.serviceFacade.BaseServiceFacade;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sys")
@CrossOrigin(value = "*", exposedHeaders = { "Authorization" })
public class MyLoginHandler {
	private static final Logger logger = LoggerFactory.getLogger(MyLoginHandler.class);

	@Autowired
	BaseServiceFacade baseServiceFacade;

	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody LoginTo loginData, HttpServletResponse response)
			throws DataAccessException {
		Map<String, Object> result = new HashMap<>();
		logger.info("로그인 시도 이력:"+loginData);

		try {

			// response.addHeader("Authorization", baseServiceFacade.login(loginTo));

			// 여기부터
			EmpInfoTO empTo = baseServiceFacade.myLogin(loginData);
			logger.info("로그인 디버깅 포인트1:"+empTo);
			if (empTo != null && empTo.getEmpCode() != null) {
				result.put("empCode", empTo.getEmpCode());
				result.put("empInfo", empTo);
				result.put("errorCode", 0); // 성공 코드
				result.put("errorMsg", "Login successful");

				System.out.println("로그인 성공! empCode: " + empTo.getEmpCode() + ", empName: " + empTo.getEmpName());

			} else {
				throw new IdNotFoundException("Id not found");
			}
//
//			if(empTo.getEmpCode()!=null) {
//				response.addHeader("Authorization", "enter");
//			}
//			System.out.println(empTo);
			result.put("empInfo", empTo);

			// 여기까지

//			result.put("empName", logi)

			result.put("errorCode", loginData.getErrorCode());
			result.put("errorMsg", loginData.getErrorMsg());

		} catch (IdNotFoundException | PwMissMatchException e) {
			result.put("errorCode", 1);
			result.put("errorMsg", e.getMessage());
		}
		logger.info("로그인 디버깅 포인트1:"+result);
		return result;
	}

	@PostMapping("/api/auth/logout")
	public Map<String, Object> logout(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();

		try {
			request.getSession().invalidate();
			System.out.println("세션만료");
		} catch (Exception e) {
			result.put("errorCode", -1);
			result.put("errorMsg", e.getMessage());
		}

		return result;
	}

}
