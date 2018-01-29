package priv.henryyu.privatebox.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import priv.henryyu.privatebox.base.BaseComponent;
import priv.henryyu.privatebox.entity.File;
import priv.henryyu.privatebox.entity.UniqueFile;
import priv.henryyu.privatebox.model.response.ResponseMessage;
import priv.henryyu.privatebox.model.response.error.ResponseCode;
import priv.henryyu.privatebox.repository.FileRepository;
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
	@Autowired
	private FileRepository fileRepository;
	
	public ResponseMessage upload(MultipartFile file) throws IllegalStateException, IOException {
		ResponseMessage responseMessage=new ResponseMessage();
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"files";
		String encryptFileName;
		try{
			encryptFileName=Encrypt.EncodeMultipartFile(file, "SHA-512",0,1000000);
		}catch (Exception e) {
			responseMessage.setCode(ResponseCode.Exception);
        	return responseMessage;
		}
		UniqueFile findUniqueFile=uniqueFileRepository.findOne(encryptFileName);
		if(findUniqueFile!=null) {
			responseMessage.setCode(ResponseCode.Success);
        	return responseMessage;
		}
		java.io.File targetFile = new java.io.File(path, encryptFileName);
		try {
		file.transferTo(targetFile);
		}catch(Exception e){
			e.printStackTrace();
		}
		UniqueFile uniqueFile=new UniqueFile();
		uniqueFile.setEncryptName(encryptFileName);
		uniqueFileRepository.save(uniqueFile);
		File saveFile=getFileByUniqueFileAndOriginalFilename(uniqueFile,file.getOriginalFilename());
		fileRepository.save(saveFile);
        try {
        	file.transferTo(targetFile);
        	responseMessage.setCode(ResponseCode.Success);
        	return responseMessage;
        } catch (Exception e) {
        	responseMessage.setCode(ResponseCode.Exception);
            e.printStackTrace();
        	return responseMessage;
        }
	}
	public ResponseMessage preUpload(String pieceSHA512,String originalFilename) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage=new ResponseMessage();
		String encryptFileName=pieceSHA512;
		UniqueFile uniqueFile=uniqueFileRepository.findOne(encryptFileName);
		if(uniqueFile==null) {
			responseMessage.setCode(ResponseCode.FileNotExist);
			return responseMessage;
		}
		File saveFile=getFileByUniqueFileAndOriginalFilename(uniqueFile,originalFilename);
		responseMessage.setCode(ResponseCode.FileExist);
		fileRepository.save(saveFile);
		return responseMessage;
	}
	/*private File getFileByMultipartFileAndUniqueFile(UniqueFile uniqueFile,MultipartFile multipartFile) {
		File file=new File();
		file.setDeleted(false);
		file.setEncryptName(uniqueFile.getEncryptName());
		file.setUsername(getUser().getUsername());
		String originalFilename=multipartFile.getOriginalFilename();
		int pointPosition=originalFilename.lastIndexOf('.');
		if(pointPosition<0) {
			file.setOriginalName(originalFilename);
			return file;
		}
		file.setOriginalName(originalFilename.substring(0, pointPosition-1));
		file.setExtension(originalFilename.substring(pointPosition, originalFilename.length()));
		return file;
	}*/
	private File getFileByUniqueFileAndOriginalFilename(UniqueFile uniqueFile,String originalFilename) {
		File file=new File();
		file.setDeleted(false);
		file.setEncryptName(uniqueFile.getEncryptName());
		file.setUsername(getUser().getUsername());
		int pointPosition=originalFilename.lastIndexOf('.');
		if(pointPosition<0) {
			file.setOriginalName(originalFilename);
			return file;
		}
		file.setOriginalName(originalFilename.substring(0, pointPosition-1));
		file.setExtension(originalFilename.substring(pointPosition, originalFilename.length()));
		return file;
	}
}
 

