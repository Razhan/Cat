package com.ef.cat.utils;

import android.content.Context;

import com.ef.cat.Constant;
import com.ran.delta.utils.MiscUtils;

import org.json.JSONObject;

public class SystemText {

    private JSONObject systemText;

    public SystemText(Context context) {
        String language = MiscUtils.getSystemLanguage(context);
        JSONObject mapping = MiscUtils.readJsonFile(context, Constant.RESOURCE_FOLDER, Constant.MAPPING_FILE);
        String textFile = mapping.optString(language);

        if (textFile == null || textFile.isEmpty()) {
            textFile = "en";
        }
        systemText = MiscUtils.readJsonFile(context, Constant.SYSTEM_TEXT_FOLDER, textFile + ".json");
    }

    public String getSystemText(String key) {
        if (systemText == null || systemText.optString(key) == null || systemText.optString(key).isEmpty()) {
            return key;
        }

        return systemText.optString(key);
    }
}
