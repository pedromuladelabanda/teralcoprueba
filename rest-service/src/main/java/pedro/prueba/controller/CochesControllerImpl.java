package pedro.prueba.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import org.springframework.http.MediaType;
import pedro.prueba.interfaces.controller.CochesController;
import pedro.prueba.vo.CocheVO;

@RestController
@RequestMapping("/coches")
public class CochesControllerImpl implements CochesController {


    @Override
    @HystrixCommand
    @GetMapping(value="/get/{params}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<CocheVO> requestCochesByParams(@PathVariable("params") String params) {
        System.out.println("REST CONTROLLER " + this.getClass().getName() + " -params- " + params);
        return null;
    }
}
