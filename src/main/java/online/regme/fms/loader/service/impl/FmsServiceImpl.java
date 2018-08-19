package online.regme.fms.loader.service.impl;

import online.regme.fms.loader.service.FmsService;
import online.regme.fms.loader.view.FmsView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FmsServiceImpl implements FmsService {

    @Value("${application.fms.url}")
    private String fmsUrl;

    @Override
    public void update() {
        downloadFile(fmsUrl, "temp.zip");
    }

    @Override
    public FmsView getByCode(String code) {
        return null;
    }

    private void downloadFile(String url, String localFilename) {
        try (InputStream in = new URL(url).openStream()) {
            Files.copy(in, Paths.get(localFilename), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ignore) {

        }
    }
}
