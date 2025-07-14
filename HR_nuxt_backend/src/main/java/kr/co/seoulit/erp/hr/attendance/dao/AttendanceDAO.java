package kr.co.seoulit.erp.hr.attendance.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AttendanceDAO {
    void callEnterProcedure(@Param("empcode") String empcode, @Param("workDate") String workDate);

    void callLeaveProcedure(@Param("empcode") String empcode, @Param("workDate") String workDate);
}
