package kr.co.seoulit.erp.hr.salary.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SOCIAL_INSURE_RATIO")
@NoArgsConstructor
@Data
public class SocialInsureEntity {
    @Id
    private String attributionYear;
    private String healthInsureRates;
    private String longTermCareRate;
    private String nationPensionRates;
    private String teachPenisionRates;
    private String empInsureRates;
    private String wrkInsureRates;
    private String jobstabilRates;
    private String vocaCompetencyRates;
    private String industInsureRates;
    private String industInsureCharRates;
}
