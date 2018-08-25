package online.regme.fms.loader.controller;

import online.regme.fms.loader.RegmeOnlineTestProjectApplication;
import online.regme.fms.loader.TestConfig;
import online.regme.fms.loader.service.impl.FmsServiceImpl;
import online.regme.fms.loader.view.FmsView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FmsController.class)
public class FmsControllerTest {

    private final String CODE = "code";
    private final String NAME = "name";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FmsServiceImpl fmsService;

    /**
     * Test get fms by code
     */
    @Test
    public void getByCodeTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/fms/get/" + CODE)
                .contentType(MediaType.APPLICATION_JSON);

        FmsView fmsView = new FmsView( NAME, CODE);

        when(fmsService.getByCode(CODE)).thenReturn(fmsView);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":\"code\",\"name\":\"name\"}"))
                .andReturn();
    }


}
