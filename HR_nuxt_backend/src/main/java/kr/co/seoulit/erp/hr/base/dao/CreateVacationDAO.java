package kr.co.seoulit.erp.hr.base.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CreateVacationDAO {
    void callInsertCreateVacationProcedure(Map<String, Object> paramMap);

}
