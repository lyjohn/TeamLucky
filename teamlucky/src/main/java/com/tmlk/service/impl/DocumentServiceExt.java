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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
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

        sb.append(File.separator);
        sb.append(UUID.randomUUID());
        if (!StringUtils.isEmpty(fileExtName)) {
            sb.append(".");
            sb.append(fileExtName);
        }

        String filePath = request.getSession().getServletContext().getRealPath(sb.toString());
        try {
            in = file.getInputStream();
            out = new FileOutputStream(new File(filePath));
            //保持图片
            FileCopyUtils.copy(in, out);

            //对图片进行压缩， 最长边为350
            ImageUtils.resize(filePath,350);

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
        if(isSuccess)
            return sb.toString();
        return null;
    }

    @Override
    public String doAvatarCut(HttpServletRequest request,HttpSession session, String filePath, int type, int x, int y, int width, int height) throws IOException {
        boolean isSuccess =false;

        StringBuffer sbSave = new StringBuffer();
        sbSave.append("resource");
        sbSave.append(File.separator);
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


            String savePath = request.getSession().getServletContext().getRealPath(sbSave.toString());
            File directorySave = new File(savePath);
            if (!directorySave.exists()) {
                directorySave.mkdirs();
            }

            sbSave.append(File.separator);
            sbSave.append(UUID.randomUUID());
            sbSave.append(".");
            String fileExtName = FilenameUtils.getExtension(file.getName());
            sbSave.append(fileExtName);

            String destPath = request.getSession().getServletContext().getRealPath(sbSave.toString());

            ImageUtils co = new ImageUtils(x,y,width,height);
            co.setSrcpath(filePath);
            co.setSavepath(destPath);
            co.cut(120);
            isSuccess = true;
        }catch (Exception ex){
            logger.trace(ex);
        }
        if(isSuccess)
            return sbSave.toString();

        return null;
    }
}