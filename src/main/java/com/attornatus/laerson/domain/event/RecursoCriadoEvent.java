package com.attornatus.laerson.domain.event;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RecursoCriadoEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    private HttpServletResponse response;

    private Long codigo;

    public RecursoCriadoEvent(Object source, HttpServletResponse response,Long codigo) {
        super(source);
        this.response = response;
        this.codigo = codigo;
    }

}
