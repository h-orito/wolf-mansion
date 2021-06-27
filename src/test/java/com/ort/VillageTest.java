package com.ort;

import com.codeborne.selenide.*;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VillageTest {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @LocalServerPort
    private Integer port;

    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    // ===================================================================================
    //                                                                               setup
    //                                                                          ==========
    @BeforeClass
    public static void setUp() {
        Configuration.browser = WebDriverRunner.CHROME;
    }

    @AfterClass
    public static void tearDownClass() {
        WebDriverRunner.closeWebDriver();
    }

    // ===================================================================================
    //                                                                                Test
    //                                                                             =======
    @Test
    public void test_全参加者確認() {
        // masterで村を作成して17人入村させる
        open("/");
        login("master");
        assertHasError();
        createVillage("村名001", "初期発言", "村狼狼狼魔狐賢導狩共共霊霊霊霊霊霊");
        assertHasError();
        allParticipate();

        // 村のID
        String villageId = getVillageId();

        // エピローグまで日付を進めながら全プレイヤーで参照してエラーが出ないことを確認
        List<String> playerNameList = villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Player();
            cb.query().setVillageId_Equal(Integer.parseInt(villageId));
        }).stream().map(vp -> vp.getPlayer().get().getPlayerName()).filter(name -> !"master".equals(name)).collect(Collectors.toList());

        int temp = 0;
        while (!isCompleteVillage()) {
            // 全プレイヤー参照
            playerNameList.forEach(playerName -> {
                // ログインして
                logoutin(playerName);
                // 村を参照
                openVillage(villageId);
            });
            // ログアウト状態で参照
            logout();
            // 村を参照
            openVillage(villageId);
            // masterで参照
            login("master");
            // 村を参照
            openVillage(villageId);
            // 日付を進める
            progressDay();
            // 村を参照
            openVillage(villageId);

            // 安全のため
            temp++;
            if (temp > 20) {
                break;
            }
        }
    }

    @Test
    public void test_管理者と非ログインだけ確認() {
        // masterで村を作成して17人入村させる
        open("/");
        login("master");
        assertHasError();
        createVillage("村名001", "初期発言", "村狼狼狼魔狐賢導狩共共霊霊霊霊霊霊");
        assertHasError();
        allParticipate();

        // 村のID
        String villageId = getVillageId();

        int temp = 0;
        while (!isCompleteVillage()) {
            // ログアウト状態で参照
            logout();
            // 村を参照
            openVillage(villageId);
            // masterで参照
            login("master");
            // 村を参照
            openVillage(villageId);
            // 日付を進める
            progressDay();
            // 村を参照
            openVillage(villageId);

            // 安全のため
            temp++;
            if (temp > 20) {
                break;
            }
        }
    }

    @Test
    public void test_エピローグまで進める() {
        // masterで村を作成して17人入村させる
        open("/");
        login("master");
        assertHasError();
        createVillage("村名001", "初期発言", "村狼狼狼魔狐賢導狩共共霊霊霊霊霊霊");
        assertHasError();
        allParticipate();

        // 村のID
        String villageId = getVillageId();

        int temp = 0;
        while (!isEpilogueVillage()) {
            // 村を参照
            openVillage(villageId);
            // 日付を進める
            progressDay();

            // 安全のため
            temp++;
            if (temp > 20) {
                break;
            }
        }
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
        }
    }

    private void open(String url) {
        Selenide.open(getUrl(url));
    }

    private String getUrl(String url) {
        return "http://localhost:" + port + "/wolf-mansion" + url;
    }

    // -----------------------------------------------------
    //                                                assert
    //                                                ------
    private void assertHasError() {
        if (Selenide.$(Selectors.byText("エラーが発生しました")).exists()) {
            Assert.fail();
        }
    }

    // -----------------------------------------------------
    //                                              use case
    //                                                ------
    private void login(String userId) {
        open("/login");
        sleep(500);
        Selenide.$("#userId").val(userId);
        Selenide.$("#password").val("testuser");
        Selenide.$("input[value=ログイン]").click();
    }

    private void createVillage(String villageName, String initialMessage, String org17) {
        open("/new-village");
        sleep(1000);
        Selenide.$("#villageName").val(villageName);
        Selenide.$("#dummy-chara-join-message").val(initialMessage);
        String org = Selenide.$("#organization").val().replace("村狼狼狼魔狐賢導狩霊霊霊霊霊霊共共", org17);
        Selenide.$("#organization").setValue(org);
        Selenide.$("input[value=確認画面へ]").click();
        Selenide.$("input[value=作成]").click();
    }

    private void openVillage(String villageId) {
        open("/village/" + villageId);
        confirmIfNeeded();
        assertHasError();
    }

    private void confirmIfNeeded() {
        // 確認したので次回以降表示しないが表示されていたらクリック
        SelenideElement element = Selenide.$(Selectors.byText("確認したので次回以降表示しない"));
        if (element.exists() && element.isDisplayed()) {
            scrollTo(element);
            element.click();
        }
    }

    private void allParticipate() {
        SelenideElement element = Selenide.$("input[value=人数分入村させる]");
        scrollTo(element);
        element.click();
    }

    private boolean isCompleteVillage() {
        return Selenide.$("#day-list").find(Selectors.byText("終了")).exists();
    }

    private boolean isEpilogueVillage() {
        return Selenide.$("#day-list").find(Selectors.byText("エピローグ")).exists();
    }

    private String getVillageId() {
        return Selenide.$("[data-village-id]").attr("data-village-id");
    }

    private void progressDay() {
        SelenideElement element = Selenide.$("input[value=日付を進める]");
        scrollTo(element);
        element.click();
    }

    private void logout() {
        SelenideElement element = Selenide.$("input[value=ログアウト]");
        scrollTo(element);
        element.click();
    }

    private void logoutin(String playerName) {
        logout();
        sleep(1000);
        login(playerName);
    }

    private void scrollTo(SelenideElement element) {
        WebDriverRunner.driver().executeJavaScript("arguments[0].scrollIntoView(true);", element);
    }
}
