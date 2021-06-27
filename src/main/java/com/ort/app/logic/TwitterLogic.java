package com.ort.app.logic;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Component
public class TwitterLogic {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final Logger logger = LoggerFactory.getLogger(TwitterLogic.class);
    private static final String VILLAGE_URL = "https://wolfort.net/wolf-mansion/village/";

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Value("${app.debug}")
    private Boolean debug;
    @Value("${twitter.oauth.consumer-key:}")
    private String consumerKey;
    @Value("${twitter.oauth.consumer-secret:}")
    private String consumerSecret;
    @Value("${twitter.oauth.access-token:}")
    private String accessToken;
    @Value("${twitter.oauth.access-token-secret:}")
    private String accessTokenSecret;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void tweet(String msg, Integer villageId) {
        String message = String.format("[WOLF MANSION] %s\r\n%s%d", msg, VILLAGE_URL, villageId);
        this.tweet(message);
    }

    public void tweet(String msg) {
        if (BooleanUtils.isTrue(debug)) {
            return;
        }
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        try {
            twitter.updateStatus("[自動通知]" + msg);
        } catch (TwitterException e) {
            // 失敗してもスルー
            logger.warn("fail tweet", e);
        }
    }
}
