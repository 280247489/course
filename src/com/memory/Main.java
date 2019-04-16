package com.memory;

import com.memory.frame.LoginFrame;
import com.memory.utils.Utils;

import javax.swing.*;

/**
 * @Auther: cui.Memory
 * @Date: 2019/4/14 0014 17:29
 * @Description:
 */
public class Main {
    public static void main(String[] args) {
        if(Utils.checkProcess()){
            LoginFrame.init();
        }else{
            JOptionPane.showMessageDialog(null,
                    "另一程序已启动",
                    "提 示",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
