package pedro.prueba.interfaces.controller;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import pedro.prueba.vo.CocheVO;

/** Rest Controller for Coches */
public interface CochesController {

    @GetMapping(value = "/get/{params}")
    List<CocheVO> requestCochesByParams(String params);

    /**
     * Zuul controller
     */
    @FeignClient(value = "coches", contextId = "coches", path = "/coches")
    interface CochesControllerZuul extends CochesController { }

}
