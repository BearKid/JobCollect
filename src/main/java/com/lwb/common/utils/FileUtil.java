package com.lwb.common.utils;

import java.io.File;
import java.io.IOException;

/**
 * <p></p>
 * Date: 2015/4/29 9:26
 *
 * @version 1.0
 * @autor: Lu Weibiao
 */
public class FileUtil {
    public static File newFile(String fulPath) throws IOException{
        File file = new File(fulPath);
        if(!file.exists()){
            File dir = file.getParentFile();
            if(!dir.exists()){
                dir.mkdirs();
            }
            file.createNewFile();
        }
        return file;
    }
}
