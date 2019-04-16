package com.memory.frame;


import it.sauronsoftware.jave.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @Auther: cui.Memory
 * @Date: 2018/12/18 0018 14:41
 * @Description:
 */
public class LoginFrame {
    public static void init(){
        Font font =new Font("微软雅黑", Font.PLAIN, 16);//设置字体
        JFrame jFrame = new JFrame("爱中医课程转换系统");

        ImageIcon imageIcon = new ImageIcon("title300.png");
        jFrame.setIconImage(imageIcon.getImage());

        jFrame.setResizable(false);
        jFrame.setSize(400, 300);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel label_info=new JLabel("---");
        label_info.setBounds(20, 60, 200, 40);
        label_info.setFont(font);
        panel.add(label_info);

        JLabel label_info1=new JLabel("---");
        label_info1.setBounds(20, 100, 200, 40);
        label_info1.setFont(font);
        panel.add(label_info1);


        JButton btn_login1=new JButton("开始转换");
        btn_login1.setBounds(30, 200, 100, 30);
        btn_login1.setFont(font);
        panel.add(btn_login1);

        JButton btn_login=new JButton("选择文件");
        btn_login.setBounds(150, 200, 100, 30);
        btn_login.setFont(font);
        panel.add(btn_login);


        JButton btn_exit=new JButton("退 出");
        btn_exit.setBounds(270, 200, 100, 30);
        btn_exit.setFont(font);
        panel.add(btn_exit);

        btn_login1.addActionListener(e -> {
            if(!label_info.getText().equals("---")){
                start(label_info1);
            }else{
                JOptionPane.showMessageDialog(null,
                        "请先选择转换文件",
                        "提 示",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        });

        btn_login.addActionListener(e -> {
            choose(panel, font, label_info, label_info1);
        });

        btn_exit.addActionListener(e -> {
            System.exit(0);
        });

        jFrame.setContentPane(panel);
        jFrame.setVisible(true);
    }

    private static void choose(JPanel panel, Font font, JLabel label_info, JLabel label_info1) {
       JFileChooser fc = new JFileChooser();
        fc.setBounds(20, 60, 60, 40);
        fc.setFont(font);
        fc.setDialogTitle("请选择要上传的文件...");
        fc.setApproveButtonText("确定");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(panel)) {
            flag_sum = 0;
            fileList.clear();
            file_path = fc.getSelectedFile().getPath();

            String path = fc.getSelectedFile().getPath();
            path = path.substring(path.lastIndexOf("\\")+1, path.length());

            label_info.setText(path);
            getFile(file_path);
            label_info1.setText("共 "+flag_sum+" 条音频");
        }

    }

    private static String file_path="";
    private static void start(JLabel label_info1){
        System.out.println("开始转换:"+file_path);
        File dirFile = new File(file_path);
        if(dirFile.exists()){
            //递归处理文件夹内文件夹
            outFile(fileList, label_info1);
        }else{
            JOptionPane.showMessageDialog(null,
                    "文件夹不存在",
                    "提 示",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private static int flag_sum = 0;
    private static List<File> fileList = new ArrayList<File>();
    private static void getFile(String path){
        File dirFile = new File(path);
        File[] listfile = dirFile.listFiles();
        for (int i = 0; i < listfile.length; i++) {
            if(listfile[i].isDirectory()){
                getFile(listfile[i].getPath());
            }else if(listfile[i].isFile()&&(listfile[i].getName().indexOf(".amr")!=-1)){
                fileList.add(listfile[i]);
                flag_sum++;
            }
        }
    }
    private static List<File> sortList = new ArrayList<File>();
    private static List<File> amrList = new ArrayList<File>();
    private static void outFile(List<File> fileList, JLabel label_info1){
        sortList.clear();
        File newDir = new File(file_path + File.separator + "course");
        if(!newDir.exists()){
            newDir.mkdir();
        }
        BasicFileAttributes basicFileAttributes = null;
        SimpleDateFormat sf = new SimpleDateFormat("HHmmss");
        for (int i = 0; i < fileList.size(); i++) {
            long time = fileList.get(i).lastModified();
            File newFile = new File(newDir.getPath() + File.separator + sf.format(time));
            fileList.get(i).renameTo(newFile);//+ ".amr"
            sortList.add(newFile);
        }
        sortList.sort(Comparator.comparing(File::getName));
        for (int i = 0; i < sortList.size(); i++) {
            File amrFile = new File(newDir.getPath() + File.separator + "李总-" + (i+1) + ".amr");
            sortList.get(i).renameTo(amrFile);

            amrList.add(amrFile);
        }
        label_info1.setText(label_info1.getText()+"(排序完成)");

    }
}
