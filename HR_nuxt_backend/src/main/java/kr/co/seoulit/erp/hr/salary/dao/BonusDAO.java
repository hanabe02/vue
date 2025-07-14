package kr.co.seoulit.erp.hr.salary.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.*;
import kr.co.seoulit.erp.hr.salary.to.BonusTO;

@Mapper
public interface BonusDAO {
	public BonusTO selectBonus(BonusTO bonus);

	public void insertBonus(BonusTO bonus);

	public void deleteAllBonus();
	
	public void deleteBonus(@Param("empCode") String empCode,@Param("applyYearMonth") String applyYearMonth, @Param("bonusCode") String bonusCode);
	
	public void approveBonus(@Param("empCode") String empCode,@Param("applyYearMonth") String applyYearMonth);
	
	public ArrayList<BonusTO> selectBonusList(@Param("empCode") String empCode,
			@Param("applyYearMonth") String applyYearMonth,
			@Param("detailCodeName") String detailCodeName,
			@Param("finalizeStatus") String finalizeStatus);
}
