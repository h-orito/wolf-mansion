package com.ort.app.api.request

import org.hibernate.validator.constraints.Length

data class UserDetailForm(
    @field:Length(max=50)
    var twitterUserName: String? = null,
    @field:Length(max=2000)
    var introduction: String? = null
)
