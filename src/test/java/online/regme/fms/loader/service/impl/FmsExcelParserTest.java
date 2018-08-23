package online.regme.fms.loader.service.impl;

import online.regme.fms.loader.model.Fms;
import online.regme.fms.loader.service.FmsCsvParser;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class FmsExcelParserTest {
    private FmsCsvParser fmsExcelParser = new FmsCsvParserImpl();

    @Test
    public void testParseFile(){
        List<Fms> list = fmsExcelParser.getFmsList("fms_structure_10012018.csv");
        assertEquals(4, list.size());
    }
}
