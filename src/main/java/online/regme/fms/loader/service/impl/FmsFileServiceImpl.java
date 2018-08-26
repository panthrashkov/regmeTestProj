package online.regme.fms.loader.service.impl;

import lombok.extern.slf4j.Slf4j;
import online.regme.fms.loader.exception.DownloadFileException;
import online.regme.fms.loader.exception.ParseCsvException;
import online.regme.fms.loader.exception.UnzipFileException;
import online.regme.fms.loader.model.Fms;
import online.regme.fms.loader.service.FmsFileService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@Slf4j
public class FmsFileServiceImpl implements FmsFileService {


    private static final String CHARSET_NAME = "WINDOWS-1251";

    @Value("${application.fms.url}")
    private String fmsUrl;

    @Override
    public List<Fms> getFmsList() {
        String fileName = createFileName();
        downloadFile(fmsUrl, fileName);
        unzipFile(fileName);
        List<Fms> fmsList = getFmsList(fileName + ".csv");
        deleteFiles(fileName);
        return fmsList;
    }

    private String createFileName() {
        return "temp" + UUID.randomUUID();
    }

    private void downloadFile(String url, String localFilename) {
        try (InputStream in = new URL(url).openStream()) {
            Files.copy(in, Paths.get(localFilename + ".zip"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            log.error("Не удалось скачать файл", exception);
            throw new DownloadFileException(exception);
        }
    }

    void unzipFile(String fileName) {
        final byte[] buffer = new byte[1024];
        try (FileInputStream fis = new FileInputStream(fileName + ".zip");
             final FileOutputStream fos = new FileOutputStream(new File(fileName + ".csv"));
             ZipInputStream zis = new ZipInputStream(fis)) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                if (!zipEntry.isDirectory()) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        } catch (IOException exception) {
            log.error("Не удалось разархивировать файл", exception);
            throw new UnzipFileException(exception);
        }
    }


    List<Fms> getFmsList(String fileLocation) {
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

    private void deleteFiles(String fileName) {
        FileUtils.deleteQuietly(new File(fileName + ".zip"));
        FileUtils.deleteQuietly(new File(fileName + ".csv"));
    }
}
