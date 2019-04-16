package com.memory.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @Auther: cui.Memory
 * @Date: 2019/4/14 0014 17:35
 * @Description:
 */
public class Utils {
    private static FileLock lock = null;
    private static final String file_dir = "course";
    private static final String fileLockPath = "course.lock";
    public static boolean checkProcess() {
        File dir = new File(file_dir);
        boolean flag = false;
        try {
            if(!dir.exists()){
                dir.mkdir();
            }
            RandomAccessFile raf = new RandomAccessFile(file_dir+File.separator+fileLockPath, "rw");
            FileChannel fc = raf.getChannel();
            lock  = fc.tryLock();
            if (lock != null && lock.isValid()) {
                flag = true;
            }
        } catch (IOException e) {
            flag = false;
        }
        return flag;
    }
}
