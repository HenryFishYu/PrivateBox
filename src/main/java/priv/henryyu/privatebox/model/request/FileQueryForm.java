package priv.henryyu.privatebox.model.request;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Id;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月30日下午5:13:13
 * @version 1.0.0
 */
public class FileQueryForm extends PaginationForm {
	private static final String empty="";
	private String beginCreateTime;
	private String endCreateTime;
	private String originalName;
	private String extension;
	
	

	public String getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(String beginCreateTime) {
		if(empty.equals(beginCreateTime)) {
			this.beginCreateTime = "1970-01-01 00:00:00";
			return;
		}
		this.beginCreateTime = beginCreateTime+" 00:00:00";
	}

	public String getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(String endCreateTime) {
		if(empty.equals(endCreateTime)) {
			this.endCreateTime = "2020-01-01 00:00:00";
			return;
		}
		this.endCreateTime = endCreateTime+" 00:00:00";
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	
	

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}
 

