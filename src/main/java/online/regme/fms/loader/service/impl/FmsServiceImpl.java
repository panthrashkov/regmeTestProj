package online.regme.fms.loader.service.impl;

import lombok.extern.slf4j.Slf4j;
import online.regme.fms.loader.dao.FmsDao;
import online.regme.fms.loader.exception.DownloadFileException;
import online.regme.fms.loader.exception.UnzipFileException;
import online.regme.fms.loader.model.Fms;
import online.regme.fms.loader.service.FmsCsvParser;
import online.regme.fms.loader.service.FmsService;
import online.regme.fms.loader.view.FmsView;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@Slf4j
public class FmsServiceImpl implements FmsService {

    @Value("${application.fms.url}")
    private String fmsUrl;

    private final FmsCsvParser fmsCsvParser;
    private final FmsDao fmsDao;

    public FmsServiceImpl(FmsCsvParser fmsCsvParser, FmsDao fmsDao) {
        this.fmsCsvParser = fmsCsvParser;
        this.fmsDao = fmsDao;
    }

    @Override
    @Transactional
    public void update() {
        String fileName = createFileName();
        downloadFile(fmsUrl, fileName);
        unzipFile(fileName);
        List<Fms> fmsList = fmsCsvParser.getFmsList(fileName + ".csv");
        log.debug(fmsList.get(0).getCode());
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
            }
        }
        return new FmsView(StringUtils.EMPTY, StringUtils.EMPTY);

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

    private void validate(List<Fms> fmsList) {
        // TODO добавить валидацию если необходимо
    }

    private void unzipFile(String fileName) {
        final byte[] buffer = new byte[1024];
        try (FileInputStream fis = new FileInputStream(fileName + ".zip");
             ZipInputStream zis = new ZipInputStream(fis)) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                if (!zipEntry.isDirectory()) {
                    final File newFile = new File(fileName + ".csv");
                    final FileOutputStream fos = new FileOutputStream(newFile);
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
        } finally {
            FileUtils.deleteQuietly(new File(fileName + ".zip"));
        }
    }

    private void downloadFile(String url, String localFilename) {
        try (InputStream in = new URL(url).openStream()) {
            Files.copy(in, Paths.get(localFilename + ".zip"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            log.error("Не удалось скачать файл", exception);
            throw new DownloadFileException(exception);
        }
    }
    private String createFileName() {
        return "temp" + UUID.randomUUID();
    }
}
