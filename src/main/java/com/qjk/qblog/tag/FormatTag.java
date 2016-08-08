package com.qjk.qblog.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import sun.tools.tree.ThisExpression;

import com.qjk.qblog.data.Pager;
import com.qjk.qblog.util.DigestUtil;
import com.qjk.qblog.util.JsonUtil;
import com.qjk.qblog.util.Value;

public class FormatTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object src;
	private String type;
	private String format;

	public Object getSrc() {
		return src;
	}

	public void setSrc(Object src) {
		this.src = src;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		Object outData= formatData();
		try {
			out.write(Value.stringValue(outData, ""));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SKIP_BODY;
	}

	private Object formatData() {
		if (src == null) {
			return null;
		}

		if ("string".equals(type)) {

			if (format == null) {
				return src.toString();

			}

		}

		// if ("eval".equals(type)) {
		//
		// return Parser.parse(src.toString()).evaluate();
		//
		// }

		if ("uuid".equals(type)) {
			return DigestUtil.md5(UUID.randomUUID().toString());
		}

		if ("datetime".equals(type)) {

			String fm = format;

			if (Value.isEmpty(fm)) {
				fm = "yyyy-MM-dd HH:mm:ss";
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat(fm);
			
			Date date = new Date(Value.longValue(src, 0)*1000);
			return dateFormat.format(date);

		} else if ("boolean".equals(type)) {
			return Value.booleanValue(src, false);
		} else if ("double".equals(type)) {
			double v = Value.doubleValue(src, 0.0);
			String scale = "0.1";

			if (Value.isEmpty(scale)) {
				return v;
			}

			return v * Value.doubleValue(scale, 1.0);
		} else if ("long".equals(type)) {

			return Value.longValue(src, 0);

		} else if ("int".equals(type)) {
			return Value.intValue(src, 0);
		}

		return null;
	}

}
