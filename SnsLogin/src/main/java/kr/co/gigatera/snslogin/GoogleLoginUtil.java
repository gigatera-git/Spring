package kr.co.gigatera.snslogin;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class GoogleLoginUtil {
	private final static String CLIENT_ID = "your client id";
	private final static String CLIENT_SECRET = "your client secret";
	private final static String REDIRECT_URI = "http://localhost:8080/google/callback";
	
	private final static String AUTH_URI = "https://accounts.google.com/o/oauth2/v2/auth";
	private final static String TOKEN_URI = "https://oauth2.googleapis.com/token";
	private final static String TOKEN_INFO_URI = "https://oauth2.googleapis.com/tokeninfo";
	private final static String REVOKE_URI = "https://oauth2.googleapis.com/revoke";

	public String getAuthorizationUrl() {
		
		UriComponents uri = UriComponentsBuilder.newInstance()
                .queryParam("client_id", CLIENT_ID )
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("response_type", "code")
                .queryParam("scope", "email profile openid")
                .queryParam("access_type", "offline")  //reflesh token
                .build();
		
		return AUTH_URI + uri.toUriString();
	}
	
	public String getAccessToken(String code) throws Exception { 
		GoogleOAuthRequest googleOAuthRequest = new GoogleOAuthRequest(
				REDIRECT_URI,
				CLIENT_ID,
				CLIENT_SECRET,
				code,
				"authorization_code"
			);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> resultEntity = restTemplate.postForEntity(
				TOKEN_URI, googleOAuthRequest, String.class);
		
		ObjectMapper mapper = new ObjectMapper(); //object to json
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES); //snake_case
		mapper.setSerializationInclusion(Include.NON_NULL);

		GoogleOAuthResponse result = mapper.readValue(resultEntity.getBody(), new TypeReference<GoogleOAuthResponse>() {
		});
		
		return result.getIdToken();
	}
	
	public Map<String,String> getGoogleUserInfo(String code) throws Exception {
		String access_token = getAccessToken(code);
		
		String requestUrl = UriComponentsBuilder
				.fromHttpUrl(TOKEN_INFO_URI)
				.queryParam("id_token", access_token)
				.build()
				.encode()
				.toUriString();
		
		ObjectMapper mapper = new ObjectMapper(); //object to json
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES); //snake_case
		mapper.setSerializationInclusion(Include.NON_NULL);
		
		RestTemplate restTemplate = new RestTemplate();
		String resultJson = restTemplate.getForObject(requestUrl, String.class);
		Map<String,String> userInfo = mapper.readValue(resultJson, new TypeReference<Map<String, String>>(){});
		
		//userInfo를 활용하여 로그인 및 db저장 처리
		return userInfo;
	}
}
