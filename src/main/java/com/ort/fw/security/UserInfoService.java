package com.ort.fw.security;

import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ort.dbflute.exbhv.PlayerBhv;
import com.ort.dbflute.exentity.Player;

@Service
public class UserInfoService implements UserDetailsService {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private PlayerBhv playerBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || "".equals(username)) {
            throw new UsernameNotFoundException("Username is empty");
        }
        OptionalEntity<Player> optPlayer = playerBhv.selectEntity(cb -> {
            cb.query().setPlayerName_Equal(username);
        });
        if (!optPlayer.isPresent()) {
            throw new UsernameNotFoundException("User not found for name: " + username);
        }
        UserInfo userInfo = new UserInfo();
        optPlayer.ifPresent(player -> {
            userInfo.setUsername(player.getPlayerName());
            userInfo.setPassword(player.getPlayerPassword());
            userInfo.setAuthority(player.getAuthorityCodeAsAuthority());
        });

        return userInfo;
    }
}
