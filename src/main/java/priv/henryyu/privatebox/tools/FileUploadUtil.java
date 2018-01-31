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
	private String username;
	private MultipartFile file;
	private String path;
	private String encryptFileName;
	private CrudRepository<UniqueFile,String> uniqueFileRepository;
	private CrudRepository<priv.henryyu.privatebox.entity.File,String> fileRepository;
	
	public FileUploadUtil(ResponseMessage responseMessage, String username, MultipartFile file, String path,
			String encryptFileName, CrudRepository<UniqueFile, String> uniqueFileRepository,
			CrudRepository<priv.henryyu.privatebox.entity.File, String> fileRepository) {
		super();
		this.responseMessage = responseMessage;
		this.username = username;
		this.file = file;
		this.path = path;
		this.encryptFileName = encryptFileName;
		this.uniqueFileRepository = uniqueFileRepository;
		this.fileRepository = fileRepository;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		File targetFile = new File(path, encryptFileName);
		UniqueFile uniqueFile=new UniqueFile();
		uniqueFile.setEncryptName(encryptFileName);
		uniqueFile.setSize(file.getSize());
		uniqueFileRepository.save(uniqueFile);
		priv.henryyu.privatebox.entity.File saveFile=FileUtil.getFileByUniqueFileAndOriginalFilename(uniqueFile,file.getOriginalFilename(),file.getSize(),username);
		fileRepository.save(saveFile);
        try {
        	file.transferTo(targetFile);
        	responseMessage.setCode(ResponseCode.Success);
        } catch (Exception e) {
        	responseMessage.setCode(ResponseCode.Exception);
            e.printStackTrace();
        }
	}

}
 

