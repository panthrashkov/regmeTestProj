package online.regme.fms.loader.service;

import online.regme.fms.loader.view.FmsView;

public interface FmsService {
    void update();

    FmsView getByCode(String code);
}
