package kr.co.seoulit.erp.hr.conexpense.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class requestUserNotFoundException extends RuntimeException{
    public requestUserNotFoundException(String msg){
        super(msg);
    }
}
