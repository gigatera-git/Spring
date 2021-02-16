package kr.co.gigatera.snslogin;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class GoogleLoginUtil {
	private final static String CLIENT_ID = "your client id";
	private final static String CLIENT_SECRET = "your client secret";
	private final static String REDIRECT_URI = "http://localhost:8080/google/callback";
	
	private final static String AUTH_URI = "https://accounts.google.com/o/oauth2/v2/auth";
	private final static String TOKEN_URI = "https://oauth2.googleapis.com/token";
	private final static String TOKEN_INFO_URI = "https://oauth2.googleapis.com/tokeninfo";
	private final static String REVOKE_URI = "https://oauth2.googleapis.com/revoke";
	
	public static String getClientId() {
		return CLIENT_ID;
	}
	public static String getClientSecret() {
		return CLIENT_SECRET;
	}
	public static String getRedirectUri() {
		return REDIRECT_URI;
	}
	public static String getAuthUri() {
		return AUTH_URI;
	}
	public static String getTokenUri() {
		return TOKEN_URI;
	}
	public static String getTokenInfoUri() {
		return TOKEN_INFO_URI;
	}
	public static String getRevokeUri() {
		return REVOKE_URI;
	}
	
	
	public String getAuthorizationUrl() {
		
		UriComponents uri = UriComponentsBuilder.newInstance()
                .queryParam("client_id", CLIENT_ID )
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("response_type", "code")
                .queryParam("scope", "email profile openid")
                .queryParam("access_type", "offline")  //reflesh token
                .build();
		
		String authUrl = "https://accounts.google.com/o/oauth2/v2/auth";
		
		return authUrl + uri.toUriString();
	}
}
