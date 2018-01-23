package priv.henryyu.privatebox.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import priv.henryyu.privatebox.base.BaseComponent;
import priv.henryyu.privatebox.entity.UniqueFile;
import priv.henryyu.privatebox.model.response.ResponseMessage;
import priv.henryyu.privatebox.model.response.error.ResponseError;
import priv.henryyu.privatebox.repository.UniqueFileRepository;
import priv.henryyu.privatebox.tools.Encrypt;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月12日下午1:12:38
 * @version 1.0.0
 */
@Service
public class FileService extends BaseComponent {
	@Autowired
	private UniqueFileRepository uniqueFileRepository;
	public ResponseMessage upload(MultipartFile file) throws IllegalStateException, IOException {
		ResponseMessage responseMessage=new ResponseMessage();
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"files";
		String encryptFileName;
		try{
			encryptFileName=Encrypt.EncodeMultipartFile(file, "SHA-512",0,1000000);
		}catch (Exception e) {
			responseMessage.setError(ResponseError.Success);
        	return responseMessage;
		}
		UniqueFile findUniqueFile=uniqueFileRepository.findOne(encryptFileName);
		if(findUniqueFile!=null) {
			responseMessage.setError(ResponseError.Success);
        	return responseMessage;
		}
		File targetFile = new File(path, encryptFileName);
		try {
		file.transferTo(targetFile);
		}catch(Exception e){
			e.printStackTrace();
		}
		UniqueFile uniqueFile=new UniqueFile();
		uniqueFile.setEncryptName(encryptFileName);
		uniqueFileRepository.save(uniqueFile);
        try {
        	file.transferTo(targetFile);
        	responseMessage.setError(ResponseError.Success);
        	return responseMessage;
        } catch (Exception e) {
        	responseMessage.setError(ResponseError.Exception);
            e.printStackTrace();
        	return responseMessage;
        }
	}
}
 

