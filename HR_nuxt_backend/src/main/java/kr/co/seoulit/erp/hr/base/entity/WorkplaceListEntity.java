package kr.co.seoulit.erp.hr.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "WORKPLACE")
@NoArgsConstructor
@Data
public class WorkplaceListEntity {
    @Id
    private String workplaceCode;
    private String workplaceName;
    private String companyCode;
}
