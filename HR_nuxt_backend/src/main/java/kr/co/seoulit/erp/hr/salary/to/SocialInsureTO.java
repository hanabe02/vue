package kr.co.seoulit.erp.hr.salary.to;

import lombok.Data;

@Data

public class SocialInsureTO{
	private String attributionYear,
	healthInsureRates,
	longTermCareRate,
	nationPensionRates,
	teachPenisionRates,
	empInsureRates,
	wrkInsureRates,
	jobstabilRates,
	vocaCompetencyRates,
	industInsureRates,
	industInsureCharRates;
}