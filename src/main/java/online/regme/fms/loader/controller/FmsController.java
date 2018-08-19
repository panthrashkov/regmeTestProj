package online.regme.fms.loader.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import online.regme.fms.loader.service.FmsService;
import online.regme.fms.loader.view.ErrorView;
import online.regme.fms.loader.view.FmsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/fms", produces = APPLICATION_JSON_VALUE)
@Api(value = "Fms controller API")
@Slf4j
public class FmsController {


    private final FmsService fmsService;

    @Autowired
    public FmsController(FmsService fmsService) {
        this.fmsService = fmsService;
    }

    /**
     *
     */
    @ApiOperation(value = "get fms name  by code", httpMethod = "GET")
    @GetMapping("/get/{code}")
    public FmsView fmsByCode(@PathVariable String code) {
        return fmsService.getByCode(code);
    }

    /**
     *
     */
    @ApiOperation(value = "update fms records", httpMethod = "POST")
    @PostMapping("/update")
    public void updateOrganization() {
        fmsService.update();
    }

    @ExceptionHandler(Exception.class)
    public ErrorView userExceptionHandler(Exception exception){
        ErrorView view = new ErrorView(exception.getMessage());
        log.error("Ошибка обновления справочника", exception);
        return view;
    }

}
