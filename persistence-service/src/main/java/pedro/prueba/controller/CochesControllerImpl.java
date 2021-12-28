package pedro.prueba.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pedro.prueba.interfaces.controller.CochesController;
import pedro.prueba.vo.CocheVO;

import java.util.List;

@RestController
@RequestMapping("/request/coches")
public class CochesControllerImpl implements CochesController {

    /**
     * Get all Coches by params
     * @param params
     * @return list CocheVO
     */
    @Override
    @HystrixCommand
    @GetMapping(value = {"/get", "/get/{params}"})
    public List<CocheVO> requestCochesByParams(@PathVariable(value = "params", required = false) String params){

        return null;
    }
}
