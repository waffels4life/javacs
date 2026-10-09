package com.javacs.security;

import org.jetbrains.annotations.NotNull;

import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

public class JSecurity {
    /*
     * java.security
     * │
     * ├── 1. basic information
     * │   ├── Randomness
     * │   ├── Entropy
     * │   ├── Keys
     * │   ├── Algorithms
     * │   └── Providers
     * │
     * ├── 2. Random
     * │   ├── SecureRandom
     * │   ├── seed
     * │   └── entropy
     * │
     * ├── 3. Message Digest
     * │   ├── MessageDigest
     * │   ├── SHA-256
     * │   ├── SHA-512
     * │   └── integrity
     * │
     * ├── 4. Digital Signature
     * │   ├── Signature
     * │   ├── signing
     * │   ├── verification
     * │   └── public/private key
     * │
     * ├── 5. Key Management
     * │   ├── Key
     * │   ├── PublicKey
     * │   ├── PrivateKey
     * │   ├── KeyPair
     * │   ├── KeyFactory
     * │   └── KeyPairGenerator
     * │
     * ├── 6. KeyStore
     * │   ├── KeyStore
     * │   ├── aliases
     * │   ├── certificates
     * │   └── private keys
     * │
     * ├── 7. Certificates
     * │   ├── Certificate
     * │   ├── X509Certificate
     * │   └── CertificateFactory
     * │
     * ├── 8. Providers
     * │   ├── Security
     * │   ├── Provider
     * │   └── algorithm implementations
     * │
     * └── 9. Advanced
     *     ├── Secure coding
     *     ├── policy
     *     ├── permissions
     *     └── JCA architecture
     */
    static class Cryptography {
        /*
         *                        CRYPTOGRAPHY
         *                             │
         *           ┌─────────────────┼─────────────────┐
         *           │                 │                 │
         *           HASHING           SYMMETRIC         ASYMMETRIC
         *           │                 ENCRYPTION        CRYPTOGRAPHY
         *           │                 │                 │
         *           SHA-256           AES               RSA
         *           SHA-3             ChaCha20          EC
         *           │                 │                 │
         *           │                 │          ┌──────┴──────┐
         *           │                 │          │             │
         *           │                 │          Encryption    Signature
         *           │                 │
         *           │                 │
         *           │                 AEAD
         *           │                 │
         *           │                 GCM
         *           │
         *           └───────────────┐
         *                           │
         *                           MAC
         *                           │
         *                           HMAC
         *
         *      [1] Authentication
         *      [2] Authorization
         *      [3] Certificates
         *      [4] PKI
         *      [5] TLS
         *      [6] Key Management
         *
         *    Attacker
         *    │
         *    ├── Can read database?
         *    ├── Can intercept network?
         *    ├── Can modify files?
         *    ├── Can execute code?
         *    ├── Can steal device?
         *    ├── Can access logs?
         *    └── Can access memory?
         * */
    }

    static class LearnSecurity {
        /*
         *                  Your Application
         *                         │
         *                         ▼
         *                 Java Security API
         *                         │
         *               ┌─────────┴─────────┐
         *               │                   │
         *          java.security        javax.crypto
         *               │                   │
         *               └─────────┬─────────┘
         *                         │
         *                         ▼
         *                    Provider API
         *                         │
         *               ┌─────────┼─────────┐
         *               │         │         │
         *              SUN      SunJCE   SunRsaSign
         *               │         │         │
         *               ▼         ▼         ▼
         *            SHA-256     AES        RSA
         * */

        public static void printAllProviders() {
            Provider[] providers = Security.getProviders();
            Arrays.stream(providers)
                    .forEach(System.out::println);
            /*
             * SUN
             * SunRsaSign
             * SunEC
             * SunJSSE
             * SunJCE
             * SunJGSS
             * SunSASL
             * ...
             */
        }

        public static void printProvidersSecurity() {
            /*
             * Provider
             *    │
             *    └── Service
             *           │
             *           ├── Type
             *           └── Algorithm
             */
            for (Provider provider : Security.getProviders())
                for (Provider.Service service : provider.getServices())
                    System.out.println(
                            provider.getName()
                            + " -> "
                            + service.getType()
                            + " -> "
                            + service.getAlgorithm()
                    );
            /*
             * SUN -> MessageDigest -> SHA-256
             * SUN -> MessageDigest -> SHA-384
             * SUN -> MessageDigest -> SHA-512
             * SunRsaSign -> Signature -> SHA256withRSA
             * SunJCE -> Cipher -> AES/GCM/NoPadding
             * ...
             */
        }

        public static void fillArrayWithSR(int space) {
            SecureRandom secureRandom = new SecureRandom();
            byte[] nonce = new byte[space];
            //     └── number used once

            secureRandom.nextBytes(nonce);
        }

        /*
         * MessageDigest.getInstance("SHA-256")
         * └── "SHA-256"
         *      │
         *      ▼
         *      JCA API
         *      │
         *      ▼
         *      Provider lookup
         *      │
         *      ▼
         *      Provider Service
         *      │
         *      ▼
         *      Concrete implementation
         *      │
         *      ▼
         *      MessageDigest object
         */
    }
    static final class TokenGenerator {

        private final SecureRandom secureRandom;

        public TokenGenerator() {
            this.secureRandom = new SecureRandom();
        }
        public byte @NotNull [] generate(int length) {
            if (length <= 0)
                throw new IllegalArgumentException(
                        "length must be positive"
                );

            byte[] token = new byte[length];
            secureRandom.nextBytes(token);

            return token;
        }
        public void wipe(byte @NotNull [] array) {
            /*
             * For the best-effort clearing of a mutable byte array,
             * `Arrays.fill` is the standard and logical pattern.
             *
             * Of course, we shouldn't assume that `Arrays.fill()` means the secret
             * has definitely been wiped from all physical memory.
             * */
            Arrays.fill(array, (byte) 0);
        }
    }

    public static void main(String[] args) {
        TokenGenerator tokenGenerator = new TokenGenerator();
        byte[] randoms = tokenGenerator.generate(4);
    }
}
