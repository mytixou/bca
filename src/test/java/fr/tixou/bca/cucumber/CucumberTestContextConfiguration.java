package fr.tixou.bca.cucumber;

import fr.tixou.bca.BcaApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = BcaApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
