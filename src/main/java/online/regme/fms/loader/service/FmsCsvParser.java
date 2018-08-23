package online.regme.fms.loader.service;

import online.regme.fms.loader.model.Fms;

import java.util.List;

public interface FmsCsvParser {
    List<Fms> getFmsList(String fileLocation);
}
