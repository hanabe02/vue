package kr.co.seoulit.erp.hr.base.controller;

import java.util.*;

import kr.co.seoulit.erp.hr.company.to.CompanyTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kr.co.seoulit.erp.hr.base.servicefacade.HrBaseServiceFacade;

@CrossOrigin("*")
@RestController
@RequestMapping("/hr/base/*")
public class CompanyListController{
    @Autowired
    private HrBaseServiceFacade hrBaseServiceFacade;

    @GetMapping("/companyList")
    public Map<String, Object> selectCompanyList() {
        Map<String, Object> map = new HashMap<String, Object>();
        ArrayList<CompanyTO> list = hrBaseServiceFacade.selectCompanyList();
        map.put("companyList", list);
        map.put("errorMsg","success");
        map.put("errorCode", 0);
        System.out.println(map);
        return map;
    }

}
