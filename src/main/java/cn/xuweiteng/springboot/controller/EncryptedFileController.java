package cn.xuweiteng.springboot.controller;

import cn.xuweiteng.springboot.service.GenerateFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Controller
public class EncryptedFileController {

    /**
     * 跳转到生成加密文件的页面
     *
     * @return 页面
     */
    @GetMapping("/generateFilePage")
    public String toGenerateFilePage(){
        return "background-generate-file.html";
    }

    @PostMapping("/generateFile")
    @ResponseBody
    public String generateFile(MultipartFile fileUpload,
                               @RequestParam("times") String times,
                               @RequestParam("order") String order,
                               HttpServletResponse response) throws Exception{

        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        String filePath = "D:/";

        try {
            //将文件保存到指定路径中
            fileUpload.transferTo(new File(filePath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 加载DLL文件
        System.loadLibrary("JniFltGzDll");
        GenerateFile gf = new GenerateFile();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String[] strs = null;

        // 利用NIO读取指定路径的文件内容，并将内容传入strs[]数组
        try(FileInputStream is = new FileInputStream(filePath + fileName);
            FileChannel channel = is.getChannel()) {
            channel.read(buffer);
            int length = buffer.position();
            byte[] b = new byte[length];
            for(int i = 0; i<length; i++) {
                b[i] = buffer.get(i);
            }

            String str = new String(b, "utf-8");
            strs = str.split("@");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 拼接机型号，使用次数，指令
         */
        StringBuilder sb = new StringBuilder();
        String modleNumber = strs[0];

        // 计算机型号长度，以及长度的长度
        int modleNumberLength = modleNumber.length();
        int lengthOfModleNumberLength = String.valueOf(modleNumberLength).length();

        // 计算使用次数的长度
        int timeLength = times.length();

        // 计算命令长度和命令长度的长度
        int orderLength = order.length();
        int lengthOfOrderLength = String.valueOf(orderLength).length();

        // 最终需要加密的字符串
        String text = "" + lengthOfModleNumberLength + modleNumberLength + modleNumber +
                           timeLength + times +
                           lengthOfOrderLength + orderLength + order;


        char[] eChar = strs[1].toCharArray();
        char[] nChar = strs[2].toCharArray();

        // 将String字符串 转化成int[]
        int[] e = new int[100];
        int[] n = new int[100];
        for(int i = 0; i<100; i++) {
            if(i == 99 && eChar.length > 100)
                e[i] = (eChar[i] - '0') * 10 +
                        (eChar[i+1] - '0');
            else
                e[i] = eChar[i] - '0';
        }
        for(int i = 0; i<100; i++) {
            if(i == 99 && nChar.length > 100)
                n[i] = (nChar[i] - '0') * 10 +
                        (nChar[i+1] - '0');
            else
                n[i] = nChar[i] - '0';
        }

        // 加密以后的密文
        String textTencry = gf.tencrypto(e, n, text.toCharArray());

        // 将下载加密后的文件
        // 配置文件下载
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
        response.setHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode(modleNumber+".gzsd", "UTF-8"));

        try(OutputStream os = response.getOutputStream()){
            os.write(textTencry.getBytes(), 0, textTencry.getBytes().length);
        }catch (Exception e1){
            e1.printStackTrace();
        }


        return "null";
    }
}
