package com.ort.app.api.v1.villages

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt
import org.springframework.test.web.servlet.request.RequestPostProcessor

/**
 * `/api/v1/...` 配下の controller テスト用共通ヘルパー。
 *
 * `WolfMansionUserInfoUtil.getUserInfo()` は `Jwt` principal を想定しているため、
 * mockMvc のテストでは `.with(authed("..."))` で JWT post-processor を被せる。
 * `@WithMockUser` は plain `User` principal を生成するので getUserInfo() が null を返してしまい、
 * "認証あり" シナリオとして機能しない点に注意。
 */
internal fun authed(username: String = "tester"): RequestPostProcessor =
    jwt().jwt { it.subject(username) }
