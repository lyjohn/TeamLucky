package com.tmlk.service;

import com.tmlk.framework.session.SessionUser;
import com.tmlk.po.DocumentExt;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface IDocumentServiceExt extends IDocumentService{

    String doAvatarSave(HttpServletRequest request,MultipartFile file,int type);
}
