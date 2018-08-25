package online.regme.fms.loader;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@EnableAutoConfiguration(exclude={LiquibaseAutoConfiguration.class})
@ComponentScan(basePackages = "online.regme.fms.loader",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = RegmeOnlineTestProjectApplication.class)})
public class TestConfig {}
