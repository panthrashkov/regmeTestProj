package online.regme.fms.loader.service.impl;

import online.regme.fms.loader.model.Fms;
import online.regme.fms.loader.service.FmsCsvParser;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class FmsCsvParserTest {

    private FmsCsvParser fmsExcelParser = new FmsCsvParserImpl();
    private static final String FILE_PATH = "fms_structure_10012018.csv";

    @Test
    public void testParseFile(){
        List<Fms> list = fmsExcelParser.getFmsList(getClass().getResource(FILE_PATH).getFile());
        assertEquals(81, list.size());
        assertEquals("580-001", list.get(0).getCode());
        assertEquals("ОУФМС РОССИИ ПО ПЕНЗЕНСКОЙ ОБЛ. В ЛЕНИНСКОМ Р-НЕ Г. ПЕНЗЫ", list.get(0).getName());
        assertEquals(new Integer(13), list.get(0).getRecordVersion());
    }
}
