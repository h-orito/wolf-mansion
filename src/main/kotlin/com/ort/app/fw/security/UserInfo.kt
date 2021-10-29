package com.ort.app.fw.security

import com.ort.dbflute.allcommon.CDef
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserInfo : UserDetails {
    private var username: String = ""
    private var password: String = ""
    private var authority: CDef.Authority = CDef.Authority.プレイヤー

    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authorities: MutableList<GrantedAuthority> = ArrayList()
        authorities.add(SimpleGrantedAuthority(authority.code()))
        return authorities
    }

    fun setAuthority(authority: CDef.Authority) {
        this.authority = authority
    }

    override fun getUsername(): String = username

    fun setUsername(username: String) {
        this.username = username
    }

    override fun getPassword(): String = password

    fun setPassword(password: String) {
        this.password = password
    }

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

    companion object {
        private const val serialVersionUID = 1L
    }
}