package com.tmlk.service.impl;

import com.tmlk.aop.SysServiceLog;
import com.tmlk.po.NewsExt;
import org.apache.log4j.Logger;

import com.tmlk.service.INewsServiceExt;

public class NewsServiceExt extends NewsService implements INewsServiceExt {
	
	private static final Logger logger = Logger.getLogger(NewsServiceExt.class);

	@Override
	@SysServiceLog(description = "发布新闻通知",code = 401)
	public NewsExt launch(NewsExt newsExt) {

		return this.create(newsExt);
	}
}