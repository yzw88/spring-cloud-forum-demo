package pers.walyex.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;

/**
 * 证书工具类
 *
 * @author Waldron Ye
 * @date 2019/6/22 18:34
 */
public class CertUtil {
    public static final String KEY_STORE = "JKS";
    public static final String X509 = "X.509";
    public static final String PKCS12 = "PKCS12";
    public static final String CERTYPE_JKS = "JKS";
    public static final String CERTYPE_PFX = "PFX";
    public static final String CERTYPE_CER = "CER";
    private static final String DATE_FORMAT_PATTEN = "yyyy-MM-dd";

    /**
     * 通过CER文件提取公钥
     *
     * @param certFile
     * @return
     * @throws CertificateException
     * @throws FileNotFoundException
     */
    public static PublicKey getPublicKey(String certFile) throws CertificateException, FileNotFoundException {
        CertificateFactory cf = CertificateFactory.getInstance(X509);
        Certificate cert = cf.generateCertificate(new FileInputStream(certFile));
        return cert.getPublicKey();
    }

    /**
     * 从pfx文件中获取公钥或私钥
     *
     * @param pfxFile
     * @param keystore_password
     * @param isPrivateKey      true私钥 false公钥
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     */
    public static Key getKey(String pfxFile, String fileType, String keystore_password, boolean isPrivateKey)
            throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, UnrecoverableKeyException {
        KeyStore inputKeyStore = KeyStore.getInstance(getKSType(fileType));
        FileInputStream fis = new FileInputStream(pfxFile);
        char[] nPassword = keystore_password.toCharArray();
        inputKeyStore.load(fis, nPassword);
        fis.close();

        Enumeration<String> enumas = inputKeyStore.aliases();
        String keyAlias = null;
        if (enumas.hasMoreElements()) {
            keyAlias = enumas.nextElement();
        }

        if (isPrivateKey) {
            return inputKeyStore.getKey(keyAlias, nPassword);
        }
        Certificate cert = inputKeyStore.getCertificate(keyAlias);
        return cert.getPublicKey();
    }

    public static String getKSType(String fileType) {
        if (CERTYPE_JKS.equalsIgnoreCase(fileType)) {
            return KEY_STORE;
        }
        return PKCS12;
    }


    /**
     * 获得Certificate
     *
     * @param keyStorePath
     * @param keyStorePassword
     * @param alias
     * @return
     * @throws Exception
     */
    private static Certificate getCertificate(String keyStorePath, String keyStorePassword, String alias) throws Exception {
        KeyStore ks = getKeyStore(keyStorePath, keyStorePassword);
        return ks.getCertificate(alias);
    }

    /**
     * 获得Certificate
     *
     * @param certificatePath
     * @return
     * @throws Exception
     */
    private static Certificate getCertificate(String certificatePath) throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
        FileInputStream in = new FileInputStream(certificatePath);

        Certificate certificate = certificateFactory.generateCertificate(in);
        in.close();

        return certificate;
    }

    /**
     * 获得KeyStore
     *
     * @param keyStorePath
     * @param password
     * @return
     * @throws Exception
     */
    private static KeyStore getKeyStore(String keyStorePath, String password) throws Exception {
        FileInputStream is = new FileInputStream(keyStorePath);
        KeyStore ks = KeyStore.getInstance(KEY_STORE);
        ks.load(is, password.toCharArray());
        is.close();
        return ks;
    }

}
