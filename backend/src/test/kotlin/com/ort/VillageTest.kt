package com.ort

class VillageTest {
//
//
//    // ===================================================================================
//    //                                                                           Attribute
//    //                                                                           =========
//    @LocalServerPort
//    private val port: Int? = null
//
//    @Autowired
//    private val villagePlayerBhv: VillagePlayerBhv? = null
//
//    // ===================================================================================
//    //                                                                               setup
//    //                                                                          ==========
//    @BeforeClass
//    fun setUp() {
//        com.codeborne.selenide.Configuration.browser = WebDriverRunner.CHROME
//    }
//
//    @AfterClass
//    fun tearDownClass() {
//        WebDriverRunner.closeWebDriver()
//    }
//
//    // ===================================================================================
//    //                                                                                Test
//    //                                                                             =======
//    @Test
//    fun test_全参加者確認() {
//        // masterで村を作成して17人入村させる
//        open("/")
//        login("master")
//        assertHasError()
//        createVillage("村名001", "初期発言", "村狼狼狼魔狐賢導狩共共霊霊霊霊霊霊")
//        assertHasError()
//        allParticipate()
//
//        // 村のID
//        val villageId = getVillageId()!!
//
//        // エピローグまで日付を進めながら全プレイヤーで参照してエラーが出ないことを確認
//        val playerNameList: List<String> =
//            villagePlayerBhv.selectList(CBCall<VillagePlayerCB?> { cb: VillagePlayerCB? ->
//                cb.setupSelect_Player()
//                cb.query().setVillageId_Equal(villageId.toInt())
//            }).stream()
//                .map<kotlin.String?>(java.util.function.Function<VillagePlayer?, kotlin.String?> { vp: VillagePlayer? ->
//                    vp.getPlayer().get().getPlayerName()
//                }).filter(
//                    java.util.function.Predicate<kotlin.String?> { name: kotlin.String? -> "master" != name })
//                .collect<kotlin.collections.MutableList<kotlin.String?>?, kotlin.Any?>(Collectors.toList<kotlin.String?>())
//        var temp = 0
//        while (!isCompleteVillage()) {
//            // 全プレイヤー参照
//            playerNameList.forEach(java.util.function.Consumer<kotlin.String?> { playerName: kotlin.String? ->
//                // ログインして
//                logoutin(playerName)
//                // 村を参照
//                openVillage(villageId)
//            })
//            // ログアウト状態で参照
//            logout()
//            // 村を参照
//            openVillage(villageId)
//            // masterで参照
//            login("master")
//            // 村を参照
//            openVillage(villageId)
//            // 日付を進める
//            progressDay()
//            // 村を参照
//            openVillage(villageId)
//
//            // 安全のため
//            temp++
//            if (temp > 20) {
//                break
//            }
//        }
//    }
//
//    @Test
//    fun test_管理者と非ログインだけ確認() {
//        // masterで村を作成して17人入村させる
//        open("/")
//        login("master")
//        assertHasError()
//        createVillage("村名001", "初期発言", "村狼狼狼魔狐賢導狩共共霊霊霊霊霊霊")
//        assertHasError()
//        allParticipate()
//
//        // 村のID
//        val villageId = getVillageId()!!
//        var temp = 0
//        while (!isCompleteVillage()) {
//            // ログアウト状態で参照
//            logout()
//            // 村を参照
//            openVillage(villageId)
//            // masterで参照
//            login("master")
//            // 村を参照
//            openVillage(villageId)
//            // 日付を進める
//            progressDay()
//            // 村を参照
//            openVillage(villageId)
//
//            // 安全のため
//            temp++
//            if (temp > 20) {
//                break
//            }
//        }
//    }
//
//    @Test
//    fun test_エピローグまで進める() {
//        // masterで村を作成して17人入村させる
//        open("/")
//        login("master")
//        assertHasError()
//        createVillage("村名001", "初期発言", "村狼狼狼魔狐賢導狩共共霊霊霊霊霊霊")
//        assertHasError()
//        allParticipate()
//
//        // 村のID
//        val villageId = getVillageId()!!
//        var temp = 0
//        while (!isEpilogueVillage()) {
//            // 村を参照
//            openVillage(villageId)
//            // 日付を進める
//            progressDay()
//
//            // 安全のため
//            temp++
//            if (temp > 20) {
//                break
//            }
//        }
//    }
//
//    // ===================================================================================
//    //                                                                        Assist Logic
//    //                                                                        ============
//    private fun sleep(milliseconds: Int) {
//        try {
//            java.lang.Thread.sleep(milliseconds.toLong())
//        } catch (e: InterruptedException) {
//        }
//    }
//
//    private fun open(url: String) {
//        Selenide.open(getUrl(url))
//    }
//
//    private fun getUrl(url: String): String? {
//        return "http://localhost:$port/wolf-mansion$url"
//    }
//
//    // -----------------------------------------------------
//    //                                                assert
//    //                                                ------
//    private fun assertHasError() {
//        if (Selenide.`$`(Selectors.byText("エラーが発生しました")).exists()) {
//            org.junit.Assert.fail()
//        }
//    }
//
//    // -----------------------------------------------------
//    //                                              use case
//    //                                                ------
//    private fun login(userId: String) {
//        open("/login")
//        sleep(500)
//        Selenide.`$`("#userId").`val`(userId)
//        Selenide.`$`("#password").`val`("testuser")
//        Selenide.`$`("input[value=ログイン]").click()
//    }
//
//    private fun createVillage(villageName: String, initialMessage: String, org17: String) {
//        open("/new-village")
//        sleep(1000)
//        Selenide.`$`("#villageName").`val`(villageName)
//        Selenide.`$`("#dummy-chara-join-message").`val`(initialMessage)
//        val org: String = Selenide.`$`("#organization").`val`().replace("村狼狼狼魔狐賢導狩霊霊霊霊霊霊共共", org17)
//        Selenide.`$`("#organization").setValue(org)
//        Selenide.`$`("input[value=確認画面へ]").click()
//        Selenide.`$`("input[value=作成]").click()
//    }
//
//    private fun openVillage(villageId: String) {
//        open("/village/$villageId")
//        confirmIfNeeded()
//        assertHasError()
//    }
//
//    private fun confirmIfNeeded() {
//        // 確認したので次回以降表示しないが表示されていたらクリック
//        val element: SelenideElement = Selenide.`$`(Selectors.byText("確認したので次回以降表示しない"))
//        if (element.exists() && element.isDisplayed) {
//            scrollTo(element)
//            element.click()
//        }
//    }
//
//    private fun allParticipate() {
//        val element: SelenideElement = Selenide.`$`("input[value=人数分入村させる]")
//        scrollTo(element)
//        element.click()
//    }
//
//    private fun isCompleteVillage(): Boolean {
//        return Selenide.`$`("#day-list").find(Selectors.byText("終了")).exists()
//    }
//
//    private fun isEpilogueVillage(): Boolean {
//        return Selenide.`$`("#day-list").find(Selectors.byText("エピローグ")).exists()
//    }
//
//    private fun getVillageId(): String? {
//        return Selenide.`$`("[data-village-id]").attr("data-village-id")
//    }
//
//    private fun progressDay() {
//        val element: SelenideElement = Selenide.`$`("input[value=日付を進める]")
//        scrollTo(element)
//        element.click()
//    }
//
//    private fun logout() {
//        val element: SelenideElement = Selenide.`$`("input[value=ログアウト]")
//        scrollTo(element)
//        element.click()
//    }
//
//    private fun logoutin(playerName: String) {
//        logout()
//        sleep(1000)
//        login(playerName)
//    }
//
//    private fun scrollTo(element: SelenideElement) {
//        WebDriverRunner.driver().executeJavaScript<kotlin.Any?>("arguments[0].scrollIntoView(true);", element)
//    }
}