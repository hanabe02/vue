package kr.co.seoulit.erp.hr.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MONTH")
@NoArgsConstructor
@Data
public class MonthEntity {
    @Id
    private String month;
}
