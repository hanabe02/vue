package kr.co.seoulit.erp.hr.base.controller;

import java.util.*;

import kr.co.seoulit.erp.hr.base.applicationservice.JpaWorkplaceListServiceImpl;
import kr.co.seoulit.erp.hr.base.entity.WorkplaceListEntity;
import kr.co.seoulit.erp.hr.company.to.WorkplaceTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kr.co.seoulit.erp.hr.base.servicefacade.HrBaseServiceFacade;

@CrossOrigin("*")
@RestController
@RequestMapping("/hr/base/*")
public class WorkplaceListController{
    @Autowired
    private HrBaseServiceFacade hrBaseServiceFacade;
    @Autowired
    private JpaWorkplaceListServiceImpl jpaWorkplaceListService;

    @GetMapping("/workplaceList")
    public Map<String, Object> selectWorkplaceList() {
        String companyCode = "COM-01";
        Map<String, Object> map = new HashMap<String, Object>();

        ArrayList<WorkplaceListEntity> list = jpaWorkplaceListService.findWorkplaceList(companyCode);
//        ArrayList<WorkplaceTO> list = hrBaseServiceFacade.selectWorkplaceList();
        map.put("workplaceList", list);
        map.put("errorMsg","success");
        map.put("errorCode", 0);
        System.out.println(map);
        return map;
    }

}
