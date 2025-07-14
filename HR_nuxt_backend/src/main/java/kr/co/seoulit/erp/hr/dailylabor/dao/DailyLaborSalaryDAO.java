package kr.co.seoulit.erp.hr.dailylabor.dao;

import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborSalaryTO;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborSalaryTaxTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DailyLaborSalaryDAO {

    List<DailyLaborSalaryTO> findSalaryList(@Param("date") String date, @Param("deadline") String deadline, @Param("status") String status);

    void insertSalaryList(@Param("date") String date);

    void updateSalaryList(DailyLaborSalaryTO item);

    void deleteSalary(DailyLaborSalaryTO item);

    List<DailyLaborSalaryTO> findTaxList(@Param("date") String date);

    void insertTaxList(String date);

    List<DailyLaborSalaryTaxTO> selectSalaryTaxList(@Param("workDate") String workDate, @Param("deadlineRequest") String deadlineRequest, @Param("status") String status);

    void deleteTax(DailyLaborSalaryTO item);

    void updateSalaryTaxList(DailyLaborSalaryTO item);
}
