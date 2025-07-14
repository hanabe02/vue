package kr.co.seoulit.erp.hr.base.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.erp.hr.base.to.YearMonthTO;

@Mapper
public interface YearMonthDAO {
	public ArrayList<YearMonthTO> selectYearList();
	public ArrayList<YearMonthTO> selectMonthList();
}
