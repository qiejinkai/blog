package com.qjk.qblog.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import sun.tools.tree.ThisExpression;

import com.qjk.qblog.data.Pager;

public class HomePagerTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String value = "pager";// 存放数据实体的名字
	private String url = "";

	public void setValue(String value) {
		this.value = value;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		String outStr = makeString();
		try {
			out.write(outStr);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SKIP_BODY;
	}

	private String makeString() {

		// 获取到存放在request中的数据实体
		Object p = pageContext.getRequest().getAttribute(value);

		StringBuilder sb = new StringBuilder();

		if (p != null) {

			Pager pager = (Pager) pageContext.getRequest().getAttribute(value);
			sb.append("<a title='Total record'><b>").append(pager.getTotalPages()).append("</b></a>");
			if (pager != null && pager.getTotalPages() > 0) {
				for (int i = 1; i <= pager.getTotalPages(); i++) {
					if (i == pager.getPageIndex()) {
						sb.append("<b>").append(i)
								.append("</b>");
					} else if (Math.abs(i - pager.getPageIndex()) < 3 || i < 4
							|| i > pager.getTotalPages() - 4) {

						sb.append("<a href='").append(url).append(i).append("'>")
								.append(i).append("</a>");
					} else if (i == 4 || i == (pager.getTotalPages() - 4)) {
						sb.append("...");
					}
				}
			}
		}

		return sb.toString();
	}
}
