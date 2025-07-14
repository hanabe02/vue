package kr.co.seoulit.erp.hr.dailylabor.dao;

import kr.co.seoulit.erp.hr.dailylabor.to.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface DailyLaborEmpDAO {
    public ArrayList<DailyLaborEmpTO> selectDailyLaborEmpAllList();

    void dailyLaborRegiEmployee(DailyLaborEmpTO dailyLaborEmpTO);

    ArrayList<DailyLaborEmpTO> selectDailyLaborEmpDetail(String empCode);

    void updateDailyLaborEmployee(DailyLaborEmpTO dailyLaborEmpTO);

    void deleteEmp(DailyLaborEmpTO dailyLaborEmpTO);
    void insertLog(DailyLaborEmpTO dailyLaborEmpTO);
    List<DailyLaborEmpLogTO> selectEmpLogList();

    void deleteEmpLog(DailyLaborEmpLogTO dailyLaborEmpLogTO);
}
