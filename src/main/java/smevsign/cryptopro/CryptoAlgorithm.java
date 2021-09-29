package smevsign.cryptopro;

import java.security.NoSuchAlgorithmException;

public enum CryptoAlgorithm {
    GOST_3411_2012_256(
            Algorithm.GOST_R3411_12_256_R3410,
            Algorithm.GOST_R3411_12_256,
            Algorithm.GOST_R3410_12_256,
            "urn:ietf:params:xml:ns:cpxmlsec:algorithms:gostr34102012-gostr34112012-256",
            "urn:ietf:params:xml:ns:cpxmlsec:algorithms:gostr34112012-256"
    ),
    GOST_3411_2012_512(
            Algorithm.GOST_R3411_12_512_R3410,
            Algorithm.GOST_R3411_12_512,
            Algorithm.GOST_R3410_12_512,
            "urn:ietf:params:xml:ns:cpxmlsec:algorithms:gostr34102012-gostr34112012-512",
            "urn:ietf:params:xml:ns:cpxmlsec:algorithms:gostr34112012-512"
    );
    public enum Algorithm {
        GOST_R3410_12_256("1.2.643.7.1.1.1.1", "GOST3410_2012_256", "Алгоритм ГОСТ Р 34.10-2012 для ключей длины 256 бит, используемый при экспорте/импорте ключей"),
        GOST_R3411_12_256("1.2.643.7.1.1.2.2", "GOST3411_2012_256", "Функция хэширования ГОСТ Р 34.11-2012, длина выхода 256 бит"),
        GOST_R3411_12_256_R3410("1.2.643.7.1.1.3.2", "GOST3411_2012_256withGOST3410DH_2012_256", "Алгоритм цифровой подписи ГОСТ Р 34.10-2012 для ключей длины 256 бит"),
        GOST_R3410_12_512("1.2.643.7.1.1.1.2", "GOST3410_2012_512", "Алгоритм ГОСТ Р 34.10-2012 для ключей длины 512 бит, используемый при экспорте/импорте ключей"),
        GOST_R3411_12_512("1.2.643.7.1.1.2.3", "GOST3411_2012_512", "Функция хэширования ГОСТ Р 34.11-2012, длина выхода 512 бит"),
        GOST_R3411_12_512_R3410("1.2.643.7.1.1.3.3", "GOST3411_2012_512withGOST3410DH_2012_512", "Алгоритм цифровой подписи ГОСТ Р 34.10-2012 для ключей длины 512 бит");
        public final String oid;
        public final String name;
        public final String description;
        Algorithm(String oid, String name, String description) {
            this.oid = oid;
            this.name = name;
            this.description = description;
        }
    }
    public final Algorithm signatureAlgorithm;
    public final Algorithm digestAlgorithm;
    public final Algorithm publicKeyAlgorithm;
    public final String signatureMethod;
    public final String digestMethod;
    CryptoAlgorithm(Algorithm signatureAlgorithm, Algorithm digestAlgorithm, Algorithm publicKeyAlgorithm, String signatureMethod, String digestMethod) {
        this.signatureAlgorithm = signatureAlgorithm;
        this.digestAlgorithm = digestAlgorithm;
        this.publicKeyAlgorithm = publicKeyAlgorithm;
        this.signatureMethod = signatureMethod;
        this.digestMethod = digestMethod;
    }
    static public CryptoAlgorithm getCryptoAlgorithm(String publicKeyAlgorithm) throws NoSuchAlgorithmException {
        for (CryptoAlgorithm cryptoAlgorithm : CryptoAlgorithm.values()) {
            if (cryptoAlgorithm.publicKeyAlgorithm.name.equals(publicKeyAlgorithm)) {
                return cryptoAlgorithm;
            }
        }
        throw new NoSuchAlgorithmException("Неподдерживаемый алгоритм открытого ключа: " + publicKeyAlgorithm);
    }
}