package kr.co.seoulit.erp.hr.dailylabor.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DailyLaborUserNotFoundException extends RuntimeException{
    public DailyLaborUserNotFoundException(String msg){
        super(msg);
    }
}