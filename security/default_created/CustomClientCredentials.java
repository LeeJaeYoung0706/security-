package newws.client_gateway.security.default_created;

import newws.client_gateway.security.authentication.client.AbstractClientCredentials;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 레스트 에이피 아이 통신 간 암호 관련 대칭키로 설정 기본 설정 AES
 *
 * RSA 로 변경할 경우    //  [After]
 * 1. DB 에 키페어 생성 후 저장하는 PostConstruct 생성하기
 * 2. 메세징 서비스 구축해서 모든 키페어가 완성 되었을 경우에 -> 공개키 저장하는 CashMap 생성 해서 각각 들고 있게 하기
 * 3. 너무 어렵다 생각만해도.
 */
public class CustomClientCredentials extends AbstractClientCredentials {

    @Value("${spring.custom-security.client-id}")
    private static String clientId;
    @Value("${spring.custom-security.client-secret}")
    private static String clientSecret;
    @Value("${spring.custom-security.aes.key}")
    private static String aesKey;

    @Override
    public String getClientId() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return encrypt(this.clientId, getAESKey(aesKey));
    }

    @Override
    public String getClientSecret() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return encrypt(this.clientSecret, getAESKey(aesKey));
    }

    @Override
    protected String encrypt(String clientSecret, SecretKeySpec key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return super.encrypt(clientSecret, key);
    }

    @Override
    public String decrypt(String encryptClientSecret, SecretKeySpec key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return super.decrypt(encryptClientSecret, key);
    }

    @Override
    protected SecretKeySpec getAESKey(String key) {
        return super.getAESKey(key);
    }
}
