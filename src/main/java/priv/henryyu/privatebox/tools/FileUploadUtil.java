package priv.henryyu.privatebox.tools;


import java.io.File;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.multipart.MultipartFile;

import priv.henryyu.privatebox.entity.UniqueFile;
import priv.henryyu.privatebox.model.response.ResponseMessage;
import priv.henryyu.privatebox.model.response.error.ResponseCode;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月30日上午10:58:19
 * @version 1.0.0
 */
public class FileUploadUtil implements Runnable {
	private ResponseMessage responseMessage;
	private MultipartFile file;
	private String path;
	private String encryptFileName;
	

	public FileUploadUtil(ResponseMessage responseMessage, MultipartFile file, String path, String encryptFileName) {
		super();
		this.responseMessage = responseMessage;
		this.file = file;
		this.path = path;
		this.encryptFileName = encryptFileName;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		File targetFile = new File(path, encryptFileName);
        try {
        	file.transferTo(targetFile);
        	responseMessage.setCode(ResponseCode.Success);
        } catch (Exception e) {
        	responseMessage.setCode(ResponseCode.Exception);
            e.printStackTrace();
        }
	}

}
 

