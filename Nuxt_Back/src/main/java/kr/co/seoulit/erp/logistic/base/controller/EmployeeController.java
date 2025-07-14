package kr.co.seoulit.erp.logistic.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import kr.co.seoulit.erp.logistic.base.servicefacade.EmployeeServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import kr.co.seoulit.erp.hr.company.to.CompanyTO;

import java.util.ArrayList;
import java.util.Map;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/logi/*")
public class EmployeeController {
    @Autowired
    private EmployeeServiceFacade employeeServiceFacade;
    private ModelMap modelMap = new ModelMap();

    // private ModelMap modelMap = new ModelMap();

    @ApiOperation(value = "사원조회")
    @RequestMapping("/employee")
    public ModelMap getEmployeeInfo(){
        return employeeServiceFacade.getEmployeeInfo();
    }
    @RequestMapping(value = "/employeeDelete", method = RequestMethod.GET)
    public void getEmployeeDelete(@RequestParam Map<String, String> params ){
        System.out.println(params);

        System.out.println("\n" + "디버깅 포인트 : 사원삭제" + params.get("empCode") + "\n");
        String empCode = params.get("empCode");
        System.out.println(empCode);
        employeeServiceFacade.getEmployeeDelete(empCode);
    }
    //    @RequestMapping(value = "/employeeDelete", method = RequestMethod.GET)
//    public void getEmployeeDelete(@RequestParam String empCode){
//
//        System.out.println("\n" + "디버깅 포인트 : 사원삭제" + empCode + "\n");
//        // String empCode = params.get("empCode");
//        employeeServiceFacade.getEmployeeDelete(empCode);
//    }
    @RequestMapping(value = "/companyInfo", method = RequestMethod.GET)
    public ModelMap getCompanyInfo(){
        System.out.println("디버깅 포인트");
        try{
            ArrayList<CompanyTO> companyInfo = employeeServiceFacade.getCompanyInfo();
            modelMap.clear();
            modelMap.put("companyInfo", companyInfo);
            modelMap.put("errorCode", 1);
            modelMap.put("errorMsg", "성공");
        } catch (Exception e2){
            e2.printStackTrace();
            modelMap.put("errorCode", 2);
            modelMap.put("errorMsg", e2.getMessage());
        }
        return modelMap;
    }
    @RequestMapping(value = "/employeeAdd", method = RequestMethod.GET)
    public void getEmployeeAdd(@RequestParam Map<String, String> clientdata){
        System.out.println(clientdata);
        employeeServiceFacade.getEmployeeAdd(clientdata);
    }

    @RequestMapping(value = "/employeeEdit", method = RequestMethod.GET)
    public void getEmployeeEdit(@RequestParam Map<String, String> updatedEmployee){
        System.out.println("\n"+ updatedEmployee+"\n");
        employeeServiceFacade.getEmployeeEdit(updatedEmployee);
    }
}
