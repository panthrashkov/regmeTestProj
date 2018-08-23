package online.regme.fms.loader.service.impl;

import lombok.extern.slf4j.Slf4j;
import online.regme.fms.loader.model.Fms;
import online.regme.fms.loader.service.FmsCsvParser;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
@Slf4j
public class FmsCsvParserImpl implements FmsCsvParser {

    @Override
    public List<Fms> getFmsList(String fileLocation) {

        List<Fms> fms = new ArrayList<>();
        try {

            Scanner scanner = new Scanner(new File(fileLocation));
            String version = scanner.nextLine().split(",")[1].trim();
            while (scanner.hasNext()) {
                String[] data = scanner.nextLine().split(",");
                fms.add(new Fms(data[0].trim(), data[1].trim(), Integer.valueOf(version)));
            }
            scanner.close();
        } catch (IOException e) {
            log.error("Excel parse error", e.getMessage());
        }

        return fms;
    }
}
