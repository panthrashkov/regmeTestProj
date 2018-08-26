package online.regme.fms.loader.service.impl;

import online.regme.fms.loader.model.Fms;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FmsFileServiceTest {

    private FmsFileServiceImpl fmsFileService = new FmsFileServiceImpl();
    private static final String FILE_PATH_CSV = "fms_structure_10012018.csv";
    private static final String FILE_PATH = "test_fms_structure_10012018";

    @Test
    public void testParseFile(){
        List<Fms> list = fmsFileService.getFmsList(getClass().getResource(FILE_PATH_CSV).getFile());
        assertEquals(81, list.size());
        assertEquals("580-001", list.get(0).getCode());
        assertEquals("ОУФМС РОССИИ ПО ПЕНЗЕНСКОЙ ОБЛ. В ЛЕНИНСКОМ Р-НЕ Г. ПЕНЗЫ", list.get(0).getName());
        assertEquals(new Integer(13), list.get(0).getRecordVersion());
    }

    @Test
    public void testUnzipFile(){
        String fileName = getClass().getResource(FILE_PATH + ".zip").getFile();
        fileName = fileName.substring(0, fileName.indexOf("."));
        fmsFileService.unzipFile(fileName);
        File f = new File(fileName + ".csv");
        assertTrue(f.exists() && !f.isDirectory());
        FileUtils.deleteQuietly(f);
        assertTrue(!f.exists());
    }
}
