package online.regme.fms.loader.service.impl;

import lombok.extern.slf4j.Slf4j;
import online.regme.fms.loader.dao.FmsDao;
import online.regme.fms.loader.exception.FmsNotFoundException;
import online.regme.fms.loader.model.Fms;
import online.regme.fms.loader.service.FmsFileService;
import online.regme.fms.loader.service.FmsService;
import online.regme.fms.loader.view.FmsView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FmsServiceImpl implements FmsService {

    private final FmsFileService fmsFileService;
    private final FmsDao fmsDao;

    public FmsServiceImpl(FmsFileService fmsFileService, FmsDao fmsDao) {
        this.fmsFileService = fmsFileService;
        this.fmsDao = fmsDao;
    }

    @Override
    @Transactional
    public void update() {
        List<Fms> fmsList = fmsFileService.getFmsList();
        validate(fmsList);
        saveList(fmsList);

    }

    @Override
    @Transactional(readOnly = true)
    public FmsView getByCode(String code) {
        if(StringUtils.isNotEmpty(code)){
            Optional<Fms> fms = fmsDao.getByCode(code);
            if(fms.isPresent()) {
                return new FmsView(fms.get().getName(), fms.get().getCode());
            }else {
                throw new FmsNotFoundException(MessageFormat.format("Отделение по коду {0} не найдено!",  code));
            }
        }else {
            throw new FmsNotFoundException("Передан не валидный код отделения!");
        }
    }


    private void validate(List<Fms> fmsList) {
    }

    private void saveList(List<Fms> fmsList) {
        for(Fms fms : fmsList){
            Optional<Fms> loadedFms = fmsDao.getByCode(fms.getCode());
            if(!loadedFms.isPresent()){
                fmsDao.persist(fms);
            }else if(loadedFms.get().getRecordVersion() <= fms.getRecordVersion()){
                loadedFms.get().setName(fms.getName());
                loadedFms.get().setRecordVersion(fms.getRecordVersion());
            }
        }
    }








}
