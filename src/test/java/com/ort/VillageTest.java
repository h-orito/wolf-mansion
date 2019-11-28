package com.ort;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VillageTest {

    private static final Logger logger = LoggerFactory.getLogger(VillageTest.class);

    @LocalServerPort
    private Integer port;

    @BeforeClass
    public static void setUp() {
        Configuration.browser = WebDriverRunner.CHROME;
    }

    @AfterClass
    public static void tearDownClass() {
        WebDriverRunner.closeWebDriver();
    }

    @Test
    public void test() {
        open("/");
        assertHasError();
        sleep(5);
    }

    private void assertHasError() {
        Assert.assertFalse(Selenide.$(Selectors.byText("エラーが発生しました")).exists());
    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {}
    }

    private void open(String url) {
        Selenide.open(getUrl(url));
    }

    private String getUrl(String url) {
        return "http://localhost:" + port + "/wolf-mansion" + url;
    }
}
