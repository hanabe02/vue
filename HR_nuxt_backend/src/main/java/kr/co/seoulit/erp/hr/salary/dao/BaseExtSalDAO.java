package kr.co.seoulit.erp.hr.salary.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.erp.hr.salary.to.BaseExtSalTO;

@Mapper
public interface BaseExtSalDAO {
	public ArrayList<BaseExtSalTO> selectBaseExtSalList();

	public void updateBaseExtSal(BaseExtSalTO baseExtSalTo);
	
	public void insertBaseExtSal(BaseExtSalTO baseExtSalTo);
	
	public void deleteBaseExtSal(String extSalCode);
	
	public int selectLastNumber();
}
