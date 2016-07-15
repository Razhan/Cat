package com.ef.cat.utils;

import android.content.Context;
import android.os.Environment;

import com.ef.cat.Constant;
import com.ran.delta.utils.FileUtils;
import com.ran.delta.utils.MiscUtils;

import org.json.JSONObject;

import java.io.File;

public class SystemText {

    private JSONObject systemText;

    public SystemText(Context context) {

        File appFolder = new File(Environment.getExternalStorageDirectory() +
                File.separator + Constant.RESOURCE_FOLDER);

        if (!appFolder.exists()) {
            FileUtils.CopyAssets(context, Constant.RESOURCE_FOLDER_NAME, FileUtils.getFolderPath(Constant.RESOURCE_FOLDER));
        }

        String language = MiscUtils.getSystemLanguage(context);
        JSONObject mapping = FileUtils.readJsonFile(context, Constant.RESOURCE_FOLDER, Constant.MAPPING_FILE);
        String textFile = mapping.optString(language);

        if (textFile == null || textFile.isEmpty()) {
            textFile = "en";
        }
        systemText = FileUtils.readJsonFile(context, Constant.SYSTEM_TEXT_FOLDER, textFile + ".json");
    }

    public String getSystemText(String key) {
        if (systemText == null || systemText.optString(key) == null || systemText.optString(key).isEmpty()) {
            return key;
        }

        return systemText.optString(key);
    }
}
