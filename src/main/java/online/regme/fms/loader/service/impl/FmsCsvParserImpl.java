package online.regme.fms.loader.service.impl;

import lombok.extern.slf4j.Slf4j;
import online.regme.fms.loader.exception.ParseCsvException;
import online.regme.fms.loader.model.Fms;
import online.regme.fms.loader.service.FmsCsvParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FmsCsvParserImpl implements FmsCsvParser {

    private static final String CHARSET_NAME = "WINDOWS-1251";

    @Override
    public List<Fms> getFmsList(String fileLocation) {
        List<Fms> fmsList = new ArrayList<>();
        try (final FileInputStream fileInputStream = new FileInputStream(new File(fileLocation));
                final Reader reader = new InputStreamReader(fileInputStream, CHARSET_NAME)){
            List<CSVRecord> records = CSVFormat.EXCEL.parse(reader).getRecords();
            String version = preprocessRecord(records.get(0).get(1));
            records.remove(0);
            records.remove(0);
            for (CSVRecord record : records) {
                String name = preprocessRecord(record.get(0));
                String code = preprocessRecord(record.get(1));
                fmsList.add(new Fms(code, name, Integer.valueOf(version)));
            }
        } catch (IOException exception) {
            log.error("Не удалось распарсить файл", exception);
            throw new ParseCsvException(exception);
        }
        return fmsList;
    }

    private String preprocessRecord(String input) {
        input = input.trim();
        if(input.startsWith("\"") && input.endsWith("\"")){
            return  input.substring(1, input.length()-1);
        }
        return input;
    }
}
