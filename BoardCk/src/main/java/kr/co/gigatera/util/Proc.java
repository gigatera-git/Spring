package kr.co.gigatera.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class Proc {
	/**
	 * <p>�Է��� ������(����Ʈ �迭)�� SHA1 �˰������� ó���Ͽ� �ؽ����� �����Ѵ�.</p>
	 * 
	 * <pre>
	 * getHash([0x68, 0x61, 0x6e]) = [0x4f, 0xf6, 0x15, 0x25, 0x34, 0x69, 0x98, 0x99, 0x32, 0x53, 0x2e, 0x92, 0x60, 0x06, 0xae, 0x5c, 0x99, 0x5e, 0x5d, 0xd6]
	 * </pre>
	 * 
	 * @param input �Է� ������(<code>null</code>�̸� �ȵȴ�.)
	 * @return �ؽ���
	 */
    public static byte[] getHash(byte[] input) {
        try {
				MessageDigest md = MessageDigest.getInstance("SHA1");
				return md.digest(input);
			} catch (NoSuchAlgorithmException e) {
				// �Ͼ ��찡 ���ٰ� ������ ������ ���� Exception �߻�
				throw new RuntimeException("SHA1" + " Algorithm Not Found", e);
			}
    }
    
	/**
	 * <p>MySQL �� PASSWORD() �Լ�.</p>
	 * 
	 * <pre>
	 * Proc.password(null)                    = null
	 * Proc.password("mypassword".getBytes()) = "*FABE5482D5AADF36D028AC443D117BE1180B9725"
	 * </pre> 
	 * 
	 * @param input
	 * @return
	 */
	public static String password(byte[] input)  {
		byte[] digest = null;
		
		// Stage 1
		digest = getHash(input);
		// Stage 2
		digest = getHash(digest);

		StringBuilder sb = new StringBuilder(1 + digest.length);
		sb.append("*");
		sb.append(ByteUtils.toHexString(digest).toUpperCase());
		return sb.toString();
	}
	
	/**
	 * <p>MySQL �� PASSWORD() �Լ�.</p>
	 * 
	 * <pre>
	 * Proc.password(null)         = null
	 * Proc.password("mypassword") = "*FABE5482D5AADF36D028AC443D117BE1180B9725"
	 * </pre> 
	 * 
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String password(String input) {
		if (input == null) {
			return null;
		}
		return password(input.getBytes());
	}
	
	/**
	 * <p>MySQL �� PASSWORD() �Լ�.</p>
	 * 
	 * <pre>
	 * Proc.password(null, *)                    = null
	 * Proc.password("mypassword", "ISO-8859-1") = "*FABE5482D5AADF36D028AC443D117BE1180B9725"
	 * </pre> 
	 * 
	 * @param input
	 * @param charsetName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String password(String input, String charsetName) throws UnsupportedEncodingException {
		if (input == null) {
			return null;
		}
		return password(input.getBytes(charsetName));
	}
	
	//getting client ip
	public static String getClientIp(HttpServletRequest request) {
		 
        String ip = request.getHeader("X-Forwarded-For");
 
        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
 
        return ip;
    }
	
	public static Timestamp getDateTime() {
		return Timestamp.valueOf(LocalDateTime.now());
	}
	
	public void printMemo(String...strs) {
		for(String str:strs) {
			System.out.println(str);
		}
	}
	
	
	public static String getQueryParam(Integer intPage, String SearchOpt, String SearchVal) throws Exception {
		UriComponents uri = UriComponentsBuilder.newInstance()
                .queryParam("intPage", intPage )
                .queryParam("SearchOpt", SearchOpt)
                .queryParam("SearchVal", URLEncoder.encode(SearchVal, "UTF-8"))
                .build();
		return uri.toUriString();
	}
	public static String getQueryParam(Integer idx,Integer intPage, String SearchOpt, String SearchVal) throws Exception {
		UriComponents uri = UriComponentsBuilder.newInstance()
				.queryParam("idx", idx )
                .queryParam("intPage", intPage )
                .queryParam("SearchOpt", SearchOpt)
                .queryParam("SearchVal", URLEncoder.encode(SearchVal, "UTF-8"))
                .build();
		return uri.toUriString();
	}
	

}
