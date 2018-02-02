package priv.henryyu.privatebox.tools;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

	private Object stream;
	private byte[] bytes;

	
	
	public FileDownloadUtil(Object stream, byte[] bytes) {
		super();
		this.stream = stream;
		this.bytes = bytes;
	}



	@Override
	public void run(){
		// TODO Auto-generated method stub
		if(stream instanceof FileInputStream) {
			try {
				((FileInputStream) stream).read(bytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(stream instanceof ByteArrayOutputStream) {
			bytes=((ByteArrayOutputStream) stream).toByteArray();
		}
	}

}
 

