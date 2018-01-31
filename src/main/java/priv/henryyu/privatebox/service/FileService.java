package priv.henryyu.privatebox.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import priv.henryyu.privatebox.base.BaseComponent;
import priv.henryyu.privatebox.entity.File;
import priv.henryyu.privatebox.entity.InvitationCode;
import priv.henryyu.privatebox.entity.UniqueFile;
import priv.henryyu.privatebox.model.request.FileDownloadForm;
import priv.henryyu.privatebox.model.request.FileQueryForm;
import priv.henryyu.privatebox.model.request.PaginationForm;
import priv.henryyu.privatebox.model.response.DataGrid;
import priv.henryyu.privatebox.model.response.ResponseMessage;
import priv.henryyu.privatebox.model.response.error.ResponseCode;
import priv.henryyu.privatebox.repository.FileRepository;
import priv.henryyu.privatebox.repository.UniqueFileRepository;
import priv.henryyu.privatebox.tools.Encrypt;
import priv.henryyu.privatebox.tools.FileUploadUtil;
import priv.henryyu.privatebox.tools.FileUtil;

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
	
	public DataGrid<File> queryFiles(FileQueryForm fileQueryForm){
		DataGrid<File> dataGrid=new DataGrid<File>();
		//User user=userRepository.findByUsername(getUser().getUsername());
		Pageable pageable;
		if(fileQueryForm.getSort()==null||fileQueryForm.getOrder()==null) {
			Sort sort=new Sort(Direction.ASC,"createTime");
			pageable=new PageRequest(fileQueryForm.getPage()-1, fileQueryForm.getRows(),sort);
		}else {
			Sort sort=new Sort(Direction.fromString(fileQueryForm.getOrder()),fileQueryForm.getSort());
			pageable=new PageRequest(fileQueryForm.getPage()-1, fileQueryForm.getRows(),sort);
		}
		Page<File> page=fileRepository.findByUsernameAndOriginalNameLikeAndExtensionLikeAndDeleted(getUser().getUsername(), "%"+fileQueryForm.getOriginalName()+"%", "%"+fileQueryForm.getExtension()+"%",false, pageable);
		dataGrid.setRows(page.getContent());
		dataGrid.setTotal(page.getTotalElements());
		return dataGrid;
	}
	
	@SuppressWarnings("finally")
	public ResponseMessage<File> delete(List<String> ids) {
		ResponseMessage<File> responseMessage=new ResponseMessage<File>();
		try {
				Iterable<File> files = fileRepository.findAll(ids);
				for(File file:files) {
					file.setDeleted(true);
				}
				fileRepository.save(files);
			responseMessage.setCode(ResponseCode.Success);
		} catch (Exception e) {
			e.printStackTrace();
			responseMessage.setCode(ResponseCode.Exception);
		} finally {
			return responseMessage;
		}
	}
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
		ThreadPoolExecutor threadPoolExecutor=(ThreadPoolExecutor) Executors.newCachedThreadPool();
		threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		threadPoolExecutor.execute(new FileUploadUtil(responseMessage, getUser().getUsername(), file, path, encryptFileName, uniqueFileRepository, fileRepository));
		return responseMessage;
	}
	public ResponseEntity<byte[]> download(FileDownloadForm fileDownloadForm) throws Exception{
	     
		String fileName = fileDownloadForm.getOriginalName() + fileDownloadForm.getExtension();
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "files/"+fileDownloadForm.getEncryptName();
		InputStream in = new FileInputStream(new java.io.File(path));// 将该文件加入到输入流之中
		byte[] body = null;
		body = new byte[in.available()];// 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数
		in.read(body);// 读入到输入流里面
		
		fileName = new String(fileName.getBytes("gbk"), "iso8859-1");// 防止中文乱码
		HttpHeaders headers = new HttpHeaders();// 设置响应头
		headers.add("Content-Disposition", "attachment;filename=" + fileName);
		headers.add("Content-Type","application/octet-stream");
		HttpStatus statusCode = HttpStatus.OK;// 设置响应吗
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
		return response;
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
		File saveFile=FileUtil.getFileByUniqueFileAndOriginalFilename(uniqueFile,originalFilename,uniqueFile.getSize(), getUser().getUsername());
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
}
 

