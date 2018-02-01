package priv.henryyu.privatebox.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import priv.henryyu.privatebox.entity.File;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年2月1日下午2:15:51
 * @version 1.0.0
 */
public class FileDownloadUtil implements Runnable {

	private String path;
	private Iterable<File> files;
	private OutputStream outputStream;
	private boolean singleFile;
	public FileDownloadUtil(String path, Iterable<File> files, OutputStream outputStream,boolean singleFile) {
		super();
		this.path = path;
		this.files = files;
		this.outputStream = outputStream;
		this.singleFile=singleFile;
	}

	@Override
	public void run(){
		// TODO Auto-generated method stub
		if(singleFile) {
			
			java.io.File file=new java.io.File(path+files.iterator().next().getEncryptName());
			try {
				InputStream inputStrean=new FileInputStream(file);
				byte[] b = new byte[2048];
		        int length;
		        while ((length = inputStrean.read(b)) > 0) {
		            outputStream.write(b, 0, length);
		        }

		        // 这里主要关闭。
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
 

