package priv.henryyu.privatebox.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

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
import priv.henryyu.privatebox.tools.FileDownloadUtil;
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
	private static final String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "files/";
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
		UniqueFile uniqueFile=new UniqueFile();
		uniqueFile.setEncryptName(encryptFileName);
		uniqueFile.setSize(file.getSize());
		uniqueFileRepository.save(uniqueFile);
		priv.henryyu.privatebox.entity.File saveFile=FileUtil.getFileByUniqueFileAndOriginalFilename(uniqueFile,file.getOriginalFilename(),file.getSize(),getUser().getUsername());
		fileRepository.save(saveFile);
		ThreadPoolExecutor threadPoolExecutor=(ThreadPoolExecutor) Executors.newCachedThreadPool();
		threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		threadPoolExecutor.execute(new FileUploadUtil(responseMessage,  file, path, encryptFileName));
		return responseMessage;
	}

	public ResponseMessage download(String[] ids) throws Exception {
		int size=0;
		ResponseMessage responseMessage = new ResponseMessage();
		Iterable<File> files = fileRepository.findAll(Arrays.asList(ids));
		for (File file : files) {
			if (!file.getUsername().equals(getUser().getUsername())) {
				responseMessage.setCode(ResponseCode.IllegalFileRequest);
				return responseMessage;
			}
		}
		for(File file:files) {
			size++;
		}
		
		response.setContentType("multipart/form-data");
		if(size<1) {
			responseMessage.setCode(ResponseCode.Exception);
			return responseMessage;
		}
		if(size==1) {
		File targetFile=files.iterator().next();
		String fileName=targetFile.getOriginalName()+targetFile.getExtension();
		fileName = new String(fileName.getBytes("gbk"), "iso8859-1");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		java.io.File file=new java.io.File(path+targetFile.getEncryptName());
			InputStream inputStrean=new FileInputStream(file);
			byte[] b = new byte[2048];
	        int length;
	        while ((length = inputStrean.read(b)) > 0) {
	            response.getOutputStream().write(b, 0, length);
	        }
	        inputStrean.close();
		}
		if(size>1) {
			String zipName="";
			for(File file:files) {
				zipName+="&"+file.getOriginalName();
			}
			zipName=zipName.substring(1);
			zipName+=".zip";
			zipName = new String(zipName.getBytes("gbk"), "iso8859-1");
			response.setHeader("Content-Disposition", "attachment;fileName="+zipName );
			toZip(files,response.getOutputStream());
		}
		return responseMessage;
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
	public void toZip(Iterable<File> files , OutputStream out)throws RuntimeException {
		ZipOutputStream zos = null ;
		try {
			zos = new ZipOutputStream(out);
			for (File file : files) {
				byte[] buf = new byte[2048];
				zos.putNextEntry(new ZipEntry(file.getOriginalName()+file.getExtension()));
				int len;
				FileInputStream in = new FileInputStream(new java.io.File(path+file.getEncryptName()));
				while ((len = in.read(buf)) != -1){
					zos.write(buf, 0, len);
				}
				zos.closeEntry();
				in.close();
			}
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils",e);
		}finally{
			if(zos != null){
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
 

