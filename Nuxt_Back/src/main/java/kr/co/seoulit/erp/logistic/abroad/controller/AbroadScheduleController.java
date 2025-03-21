package kr.co.seoulit.erp.logistic.abroad.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.seoulit.erp.logistic.abroad.domain.AbroadSchedule;
import kr.co.seoulit.erp.logistic.abroad.servicefacade.AbroadScheduleServiceFacade;
import kr.co.seoulit.erp.logistic.abroad.to.abroadScheduleInfoTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Api(description = "해외 홍보 일정")
@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/logi/abroad/*")
public class AbroadScheduleController {

    @Autowired
    private AbroadScheduleServiceFacade abroadScheduleSF;
    private ModelMap modelMap;
    @ApiOperation(value = "행사 조회")
    @ResponseBody
    @RequestMapping(value = "/abroadScheduleList", method = RequestMethod.GET)
    public ModelMap getAbroadScheduleList() {
        return abroadScheduleSF.getAbroadScheduleList();
    }
    @ApiOperation(value = "행사 추가")
    @ResponseBody
    @RequestMapping(value = "/addAbroadSchedule", method = RequestMethod.POST)
    public void insertAbroadScheduleCode(@RequestBody @ApiParam(value = "행사JSON") AbroadSchedule abroadScheduleInfoTO) {
        abroadScheduleSF.insertAbroadScheduleCode(abroadScheduleInfoTO);
    }
    @ApiOperation(value = "행사 삭제")
    @ResponseBody
    @RequestMapping(value = "/abroadScheduleCode", method = RequestMethod.DELETE)
    public void deleteAbroadScheduleCode(@RequestParam @ApiParam(value = "행사JSON")String abroadScheduleCode) {
        abroadScheduleSF.deleteAbroadScheduleCode(abroadScheduleCode);
    }
    @ApiOperation(value = "행사 수정")
    @ResponseBody
    @RequestMapping(value = "/updateAbroadSchedule", method = RequestMethod.PUT)
    public void updateAbroadScheduleCode(@RequestBody @ApiParam(value = "행사JSON")AbroadSchedule abroadScheduleInfoTO) {
        abroadScheduleSF.updateAbroadScheduleCode(abroadScheduleInfoTO);
    }

}

