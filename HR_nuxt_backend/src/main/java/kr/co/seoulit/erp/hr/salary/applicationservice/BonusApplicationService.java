package kr.co.seoulit.erp.hr.salary.applicationservice;

import java.util.ArrayList;

import kr.co.seoulit.erp.hr.salary.to.BonusTO;

public interface BonusApplicationService {
	public BonusTO findterBonus(BonusTO bonus);
	public void registerBonus(BonusTO bonus);
	public void removeAllBonus();
	public void removeBonus(String empCode, String applyYearMonth, String bonusCode);
	public void approveBonus(String empCode, String applyYearMonth);
	public ArrayList<BonusTO> findBonusList(String empCode, String applyYearMonth, String detailCodeName,String finalizeStatus);
}