package kloud.backend.util;

import kloud.backend.service.dto.HomeworkFileDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {


    public static void urldownloadZip(List<HomeworkFileDTO> list, HttpServletResponse response){
        String fileName = new String("homework.zip");
        //响应头的设置
        response.reset();
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition","attachment;fileName=" + fileName);
        response.setContentType("application/octet-stream;charset=utf-8");
        //设置压缩流：直接写入response，实现边压缩边下载
        ZipOutputStream zipos = null;
        try {
            zipos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
        //设置压缩方法
        zipos.setMethod(ZipOutputStream.DEFLATED);
        DataOutputStream dos = null;
        try {
            //循环将文件写入流
            for(HomeworkFileDTO file : list) {
                URL url = new URL(file.getUrl());
                URLConnection conn = url.openConnection();
                InputStream is = conn.getInputStream();
                //添加ZipEntry并将ZipEntry写入流
                zipos.putNextEntry(new ZipEntry(file.getFileName()));
                dos = new DataOutputStream(zipos);
                byte[] buff = new byte[1024 * 10];
                int len = 0;
                //循环读写
                while((len = is.read(buff)) > -1) {
                    dos.write(buff, 0, len);
                }
                //关闭此文件流
                is.close();
                //关闭当前ZIP项，并将流放置到写入的位置，下一个条目
                zipos.closeEntry();
            }
            //释放资源
            dos.flush();
            dos.close();
            zipos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            try {
                dos.close();
                zipos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
