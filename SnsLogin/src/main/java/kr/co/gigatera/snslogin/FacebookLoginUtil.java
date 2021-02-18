package kr.co.gigatera.snslogin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;

public class FacebookLoginUtil {
	
    public String getAuthorizationUrl(FacebookConnectionFactory connectionFactory,OAuth2Parameters oAuth2Parameters) throws Exception {
    	OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        String facebook_url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
        return facebook_url;
    }
    
    public Map<String,String> getGoogleUserInfo(FacebookConnectionFactory connectionFactory,OAuth2Parameters oAuth2Parameters,String code) throws Exception { 
    	
    	Map<String,String> userMap = new HashMap<String,String>();
    	
    	String redirectUri = oAuth2Parameters.getRedirectUri();
    	
    	OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
    	AccessGrant accessGrant = oauthOperations.exchangeForAccess(code, redirectUri, null);
        String accessToken = accessGrant.getAccessToken();
        
        Long expireTime = accessGrant.getExpireTime();
        if (expireTime != null && expireTime < System.currentTimeMillis()) {
            accessToken = accessGrant.getRefreshToken();
            //logger.info("accessToken is expired. refresh token = {}", accessToken);
        };
        
        Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
        Facebook facebook = connection == null ? new FacebookTemplate(accessToken) : connection.getApi();
        UserOperations userOperations = facebook.userOperations();
        
        try
        {            
            String [] fields = { "id", "email",  "name"};
            User userProfile = facebook.fetchObject("me", User.class, fields);
            
            userMap.put("id", userProfile.getId());
            userMap.put("email", userProfile.getEmail());
            userMap.put("name", userProfile.getName());
            
        } catch (MissingAuthorizationException e) {
            e.printStackTrace();
        }
        
        return userMap;
    }

}
