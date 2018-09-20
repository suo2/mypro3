package com.huawei.solarsafe.utils;

import java.security.Provider;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  :
 * </pre>
 */
public final class CryptoProvider extends Provider {
    public CryptoProvider() {
        super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
        put("SecureRandom.SHA1PRNG",
                "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
        put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
    }

}
