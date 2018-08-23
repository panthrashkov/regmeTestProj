package online.regme.fms.loader.service.impl;

import lombok.extern.slf4j.Slf4j;
import online.regme.fms.loader.model.Fms;
import online.regme.fms.loader.service.FmsCsvParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FmsCsvParserImpl implements FmsCsvParser {

    //    @Override
//    public List<Fms> getFmsList(String fileLocation) {
//
//        List<Fms> fms = new ArrayList<>();
//        try {
//
//            Scanner scanner = new Scanner(new File(fileLocation));
//            String version = scanner.nextLine().split(",")[1].trim();
//            while (scanner.hasNext()) {
//                String[] data = scanner.nextLine().split(",");
//                fms.add(new Fms(data[0].trim(), data[1].trim(), Integer.valueOf(version)));
//            }
//            scanner.close();
//        } catch (IOException e) {
//            log.error("Excel parse error", e.getMessage());
//        }
//
//        return fms;
//    }
    @Override
    public List<Fms> getFmsList(String fileLocation) {
        List<Fms> fms = new ArrayList<>();
        try {
            final Reader reader = new InputStreamReader(new FileInputStream(new File(fileLocation)), "WINDOWS-1251");
            List<CSVRecord> records = CSVFormat.EXCEL.parse(reader).getRecords();
            String version =  records.get(0).get(1).replaceAll("\"", "").trim();
            records.remove(0);
            records.remove(0);
            for (CSVRecord record : records) {
                String name = record.get(0);
                String code = record.get(1);
                fms.add(new Fms(code, name, Integer.valueOf(version)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fms;
    }
}
