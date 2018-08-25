package online.regme.fms.loader.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import online.regme.fms.loader.exception.FmsNotFoundException;
import online.regme.fms.loader.exception.IfrastructureException;
import online.regme.fms.loader.service.FmsService;
import online.regme.fms.loader.view.ErrorView;
import online.regme.fms.loader.view.FmsView;
import online.regme.fms.loader.view.ResultView;
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
     * Обработка запроса на получение названия отделения ФМС по коду
     * @param code код подразделения ФМС
     * @return возвращает представление для отделения ФМС
     */
    @ApiOperation(value = "get fms name  by code", httpMethod = "GET")
    @GetMapping("/get/{code}")
    public FmsView fmsByCode(@PathVariable String code) {
        return fmsService.getByCode(code);
    }

    /**
     * Обновление справочника
     */
    @ApiOperation(value = "update fms records", httpMethod = "POST")
    @PostMapping("/update")
    public ResultView updateOrganization() {
        fmsService.update();
        return ResultView.SUCCESS;
    }

    @ExceptionHandler(FmsNotFoundException.class)
    public ErrorView fmsNotFoundExceptionHandler(Exception exception){
        log.error("Не удалось найти отделение ФМС в справочнике", exception);
        return new ErrorView(exception.getMessage());
    }

    @ExceptionHandler(IfrastructureException.class)
    public ErrorView userExceptionHandler(Exception exception){
        log.error("Ошибка обновления справочника", exception);
        return new ErrorView(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorView systemExceptionHandler(Exception exception){
        log.error("Не удалось выполнить действие. Обратитесь к разработчикам.", exception);
        return new ErrorView(exception.getMessage());
    }

}
