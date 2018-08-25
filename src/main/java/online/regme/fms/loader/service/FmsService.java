package online.regme.fms.loader.service;

import online.regme.fms.loader.view.FmsView;

public interface FmsService {

    /**
     * Метод обновляет справочник отделений ФМС России
     */
    void update();

    /**
     * Метод получения названия отделения ФМС по коду
     * @param code - код ФМС
     * @return возвращает представление для отделения ФМС
     */
    FmsView getByCode(String code);
}
