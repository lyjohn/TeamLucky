package com.tmlk.service.impl;

import com.tmlk.framework.session.SessionUser;
import com.tmlk.framework.util.FormatUtils;
import com.tmlk.framework.util.ImageUtils;
import com.tmlk.po.DocumentExt;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.tmlk.service.IDocumentServiceExt;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DocumentServiceExt extends DocumentService implements IDocumentServiceExt {

    private static final Logger logger = Logger.getLogger(DocumentServiceExt.class);

    private String rootPath;

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    //第一次保持头像临时文件，待截取
    @Override
    public String doAvatarSave(HttpServletRequest request, MultipartFile file, int type) {
        boolean isSuccess = false;
        InputStream in = null;
        OutputStream out = null;

        String fileFullName = file.getOriginalFilename();
        String fileExtName = FilenameUtils.getExtension(fileFullName);

        StringBuffer sb = new StringBuffer();
        sb.append("resource");
        sb.append(File.separator);
        sb.append("avatar");
        sb.append(File.separator);
        sb.append("tmp");
//        sb.append(File.separator);
//        sb.append(FormatUtils.getCurrentDateString("yyyyMM"));
//        sb.append(File.separator);
//        sb.append(FormatUtils.getCurrentDateString("dd"));


        String savePath = request.getSession().getServletContext().getRealPath(sb.toString());
        File directoryTmp = new File(savePath);
        if (!directoryTmp.exists()) {
            directoryTmp.mkdirs();
        }

        //这个其实可以在部署的时候建好  存放大的原图
        String savePathSource = rootPath + "avatar"+File.separator+"source";
        File directorySource = new File(savePathSource);
        if (!directorySource.exists()) {
            directorySource.mkdirs();
        }

        sb.append(File.separator);
        String fileName = UUID.randomUUID().toString();
        if (!StringUtils.isEmpty(fileExtName)) {
            fileName+= "."+fileExtName;
        }
        sb.append(fileName);

        String filePath = request.getSession().getServletContext().getRealPath(sb.toString());
        String filePathSource = savePathSource +File.separator+ fileName;

        try {
            in = file.getInputStream();
            out = new FileOutputStream(new File(filePathSource));
            //保持图片
            FileCopyUtils.copy(in, out);

            //对图片进行压缩， 最长边为350
            ImageUtils.resize(filePathSource,350, filePath);

            isSuccess = true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
            }
        }
        if (isSuccess)
            return sb.toString();
        return null;
    }

    @Override
    public String doAvatarCut(HttpServletRequest request, HttpSession session, String filePath, int type, int x, int y, int width, int height) throws IOException {
        boolean isSuccess = false;

        StringBuffer sbSave = new StringBuffer();
        sbSave.append("avatar");
        sbSave.append(File.separator);
        if (type == 1)
            sbSave.append("user");
        else if (type == 2)
            sbSave.append("party");
        else if (type == 3)
            sbSave.append("group");
        else
            sbSave.append("temp");
        try {
            filePath = request.getSession().getServletContext().getRealPath(filePath);
            File file = new File(filePath);
            if (!file.exists())
                return null;

            String savePath = request.getSession().getServletContext().getRealPath("resource"+File.separator+sbSave.toString());
            File directorySave = new File(savePath);
            if (!directorySave.exists()) {
                directorySave.mkdirs();
            }

            //这个是真的位置
            String savePath1 = rootPath + sbSave.toString();
            File directorySave1 = new File(savePath1);
            if (!directorySave1.exists()) {
                directorySave1.mkdirs();
            }

            sbSave.append(File.separator);
            sbSave.append(file.getName());

//            sbSave.append(UUID.randomUUID());
//            sbSave.append(".");
//            String fileExtName = FilenameUtils.getExtension(file.getName());
//            sbSave.append(fileExtName);

            String destPath = rootPath + sbSave.toString();
            String destPath1 = request.getSession().getServletContext().getRealPath("resource"+File.separator+sbSave.toString());

            ImageUtils co = new ImageUtils(x, y, width, height);
            co.setSrcpath(filePath);

            List<String> savePathList = new ArrayList<String>();
            savePathList.add(destPath);
            savePathList.add(destPath1);
            co.cut(120,savePathList);
            isSuccess = true;
        } catch (Exception ex) {
            logger.trace(ex);
        }
        if (isSuccess)
            return sbSave.toString();

        return null;
    }

    @Override
    public void doAvatarShow(HttpServletResponse response, String filePath) {
        File file = new File(rootPath + filePath);
        if (file.exists()) {

            response.reset();
            response.setContentType("image/jpeg; charset=GBK");
            ServletOutputStream outputStream = null;
            try {
                outputStream = response.getOutputStream();
                FileInputStream inputStream = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int i = -1;
                while ((i = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, i);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(outputStream != null)
                {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
        }
    }
}