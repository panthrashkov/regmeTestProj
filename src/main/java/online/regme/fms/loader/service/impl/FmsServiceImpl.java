package online.regme.fms.loader.service.impl;

import online.regme.fms.loader.service.FmsService;
import online.regme.fms.loader.view.FmsView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class FmsServiceImpl implements FmsService {

    @Value("${application.fms.url}")
    private String fmsUrl;

    @Override
    public void update() {
        String fileName  = createFileName();
        downloadFile(fmsUrl, fileName );
        parseFile(fileName);
    }

    private void parseFile(String zipFileName) {
        try {
            unzipFile(zipFileName);
        } catch (IOException ignore) {

        }
    }

    private void unzipFile(String zipFileName) throws IOException {
        final byte[] buffer = new byte[1024];
        final ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            final String fileName = zipEntry.getName();
            final File newFile = new File(fileName + ".csv");
            final FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    private String createFileName() {
        return "temp" + UUID.randomUUID();
    }

    @Override
    public FmsView getByCode(String code) {
        return null;
    }

    private void downloadFile(String url, String localFilename) {
        try (InputStream in = new URL(url).openStream()) {
            Files.copy(in, Paths.get(localFilename + ".zip"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ignore) {

        }
    }
}
