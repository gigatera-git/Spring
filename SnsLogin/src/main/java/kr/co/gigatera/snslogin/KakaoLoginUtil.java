package kr.co.gigatera.snslogin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KakaoLoginUtil {
	private final static String CLIENT_ID = "your client id"; //rest api key
	//private final static String CLIENT_SECRET = ""; //kakao에선 꼭 필요하지 않음
	private final static String REDIRECT_URI = "http://localhost:8080/kakao/callback";
	
	private final static String AUTH_URI = "https://kauth.kakao.com/oauth/authorize";
	private final static String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
	private final static String TOKEN_INFO_URI = "https://kapi.kakao.com/v2/user/me";
	private final static String REVOKE_URI = "https://kapi.kakao.com/v1/user/logout";
	
	public String getAuthorizationUrl() {
		
		UriComponents uri = UriComponentsBuilder.newInstance()
                .queryParam("client_id", CLIENT_ID )
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("response_type", "code")
                .queryParam("scope", "account_email profile")
                .build();
		
		return AUTH_URI + uri.toUriString();
	}
	
	public String getAccessToken(String code) {
		
		final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
		postParams.add(new BasicNameValuePair("client_id", CLIENT_ID)); // REST API KEY
		postParams.add(new BasicNameValuePair("redirect_uri", REDIRECT_URI)); // 리다이렉트 URI
		postParams.add(new BasicNameValuePair("code", code)); // 로그인 과정 중 얻은 code 값

		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(TOKEN_URI);
		JsonNode returnNode = null;

		try {
			post.setEntity(new UrlEncodedFormEntity(postParams));
			final HttpResponse response = client.execute(post);
			final int responseCode = response.getStatusLine().getStatusCode();

			// JSON 형태 반환값 처리
			ObjectMapper mapper = new ObjectMapper();
			returnNode = mapper.readTree(response.getEntity().getContent());

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// clear resources
		}
		return returnNode.get("access_token").toString();
	}
	
	public JsonNode getKakaoUserInfo(String code) {

	      final HttpClient client = HttpClientBuilder.create().build();
	      final HttpPost post = new HttpPost(TOKEN_INFO_URI);
	      String accessToken = getAccessToken(code);
	      // add header
	      post.addHeader("Authorization", "Bearer " + accessToken);

	      JsonNode returnNode = null;

	      try {

	        final HttpResponse response = client.execute(post);
	        final int responseCode = response.getStatusLine().getStatusCode();
	        //System.out.println("\nSending 'POST' request to URL : " + TOKEN_INFO_URI);
	        //System.out.println("Response Code : " + responseCode);

	        // JSON 형태 반환값 처리
	        ObjectMapper mapper = new ObjectMapper();
	        returnNode = mapper.readTree(response.getEntity().getContent());
	        
	      } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	      } catch (ClientProtocolException e) {
	        e.printStackTrace();
	      } catch (IOException e) {
	        e.printStackTrace();
	      } finally {
	        // clear resources
	      }
	      return returnNode;
	    }
}
