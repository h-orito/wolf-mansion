package com.ort.app.fw.security

import com.ort.dbflute.cbean.PlayerCB
import com.ort.dbflute.exbhv.PlayerBhv
import com.ort.dbflute.exentity.Player
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserInfoService(
    private val playerBhv: PlayerBhv
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails? {
        if (username.isNullOrEmpty()) {
            throw UsernameNotFoundException("Username is empty")
        }
        val optPlayer = playerBhv.selectEntity { cb: PlayerCB ->
            cb.query().setPlayerName_Equal(username)
        }
        if (!optPlayer.isPresent) {
            throw UsernameNotFoundException("User not found for name: $username")
        }
        val userInfo = UserInfo()
        optPlayer.ifPresent { player: Player ->
            userInfo.username = player.playerName
            userInfo.password = player.playerPassword
            userInfo.setAuthority(player.authorityCodeAsAuthority)
        }
        return userInfo
    }
}