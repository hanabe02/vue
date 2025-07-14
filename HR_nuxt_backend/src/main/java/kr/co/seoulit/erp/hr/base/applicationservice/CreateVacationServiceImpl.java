package kr.co.seoulit.erp.hr.base.applicationservice;

import kr.co.seoulit.erp.hr.base.dao.CreateVacationDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateVacationServiceImpl implements CreateVacationService {

    private final CreateVacationDAO createVacationDAO;

    @Override
    public void executeCreateProcedure() {
        Map<String, Object> paramMap = new HashMap<>();
        createVacationDAO.callInsertCreateVacationProcedure(paramMap);

        Integer error_code = ((BigDecimal)paramMap.get("error_code")).intValue();
        String error_msg = (String) paramMap.get("error_msg");


        if (error_code < 0) {
            throw new RuntimeException("연차생성실패" + error_msg);
        }
        System.out.println("연차생성 성공");
    }
}
