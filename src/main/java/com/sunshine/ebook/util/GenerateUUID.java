package com.sunshine.ebook.util;

import java.util.UUID;

public class GenerateUUID {

    public static String generateFileId() {
        UUID id = UUID.randomUUID();
        String uuid = id.toString().replace("-", "");
        return uuid;
    }

}
