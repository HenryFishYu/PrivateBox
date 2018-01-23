package priv.henryyu.privatebox.tools;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月10日下午5:28:01
 * @version 1.0.0
 */
public class Encrypt {
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f' };
	protected static MessageDigest messagedigest = null;
	public static String EncodeString(String strText,String encryptType)  
	  {  
	    // 返回值  
	    String strResult = null;  
	  
	    // 是否是有效字符串  
	    if (strText != null && strText.length() > 0)  
	    {  
	      try  
	      {  
	        // SHA 加密开始  
	        // 创建加密对象 并傳入加密類型  
	        MessageDigest messageDigest = MessageDigest.getInstance(encryptType);  
	        // 传入要加密的字符串  
	        messageDigest.update(strText.getBytes());  
	        // 得到 byte 類型结果  
	        strResult=bufferToHex(messageDigest.digest());  
	  
	         
	      }  
	      catch (NoSuchAlgorithmException e)  
	      {  
	        e.printStackTrace();  
	      }  
	    }  
	  
	    return strResult;  
	  }
	public static String EncodeMultipartFile(MultipartFile file,String encrptType) throws Exception {

		if(file.getInputStream() instanceof FileInputStream){
			System.out.println("InputStreamType:FileInputStream");
			FileInputStream in = (FileInputStream) file.getInputStream();
			FileChannel ch = in.getChannel();
		   MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, ch.size());
		   messagedigest=MessageDigest.getInstance(encrptType.toString());
		   messagedigest.update(byteBuffer);
		   return bufferToHex(messagedigest.digest());
		}else if(file.getInputStream() instanceof ByteArrayInputStream){
			System.out.println("InputStreamType:ByteArrayInputStream");
			messagedigest=MessageDigest.getInstance(encrptType.toString());
			messagedigest.update(file.getBytes());
			return bufferToHex(messagedigest.digest());
		}else
			return null;
		   
	}
	public static String EncodeMultipartFile(MultipartFile file,String encrptType,int setoff,int length) throws Exception {
		if(length<=0) {
			throw new Exception("Illegal input,length should be larger than 0");
		}
		
		if(file.getInputStream() instanceof FileInputStream){
			System.out.println("InputStreamType:FileInputStream");
			FileInputStream in = (FileInputStream) file.getInputStream();
			FileChannel ch = in.getChannel();
			MappedByteBuffer byteBuffer;
			if(setoff>=ch.size()) {
				throw new Exception("Illegal input,setoff should be smaller than channel size");
			}
			if (ch.size() <= setoff+length) {
				byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, setoff, ch.size());
			}else {
				byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, setoff, setoff+length);
			}
			messagedigest = MessageDigest.getInstance(encrptType.toString());
			messagedigest.update(byteBuffer);
			return bufferToHex(messagedigest.digest());
		}else if(file.getInputStream() instanceof ByteArrayInputStream){
			System.out.println("InputStreamType:ByteArrayInputStream");
			messagedigest=MessageDigest.getInstance(encrptType.toString());
			if(setoff>=file.getBytes().length) {
				throw new Exception("Illegal input,setoff should be smaller than file bytes length");
			}
			if (file.getBytes().length <= setoff+length) {
				messagedigest.update(file.getBytes());
			} else {
				messagedigest.update(Arrays.copyOfRange(file.getBytes(), setoff, setoff+length));
			}
			return bufferToHex(messagedigest.digest());
		}else
			return null;
		   
	}
	public static String EncodeFile(File file,String encrptType) throws Exception {
		   FileInputStream in = new FileInputStream(file);
		   FileChannel ch = in.getChannel();
		   MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		   messagedigest=MessageDigest.getInstance(encrptType.toString());
		   messagedigest.update(byteBuffer);
		   return bufferToHex(messagedigest.digest());
	}
	public static String EncodeBytes(byte[] bytes,String encrptType) throws Exception {
		   messagedigest=MessageDigest.getInstance(encrptType.toString());
		   messagedigest.update(bytes);
		   return bufferToHex(messagedigest.digest());
	}
	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}
	
	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
			int k = m + n;
			for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
			}
		return stringbuffer.toString();
	}
	
	
	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
		}
}


