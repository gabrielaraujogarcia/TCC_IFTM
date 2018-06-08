package br.com.iftm.financeiroapi.model.utils;

import java.util.UUID;

public abstract class IdentifierUtil {

    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

}
