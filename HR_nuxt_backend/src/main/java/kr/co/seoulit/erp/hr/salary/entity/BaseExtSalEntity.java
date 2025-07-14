package kr.co.seoulit.erp.hr.salary.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "BASE_EXT_SAL")
@NoArgsConstructor
@Data
public class BaseExtSalEntity {
    @Id
    private String extSalCode;
    private String extSalName;
    private String ratio;
}
