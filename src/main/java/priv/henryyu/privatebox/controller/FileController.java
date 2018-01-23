package priv.henryyu.privatebox.controller;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import priv.henryyu.privatebox.service.FileService;
import priv.henryyu.privatebox.tools.Encrypt;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月10日下午3:24:44
 * @version 1.0.0
 */
@Controller
@RequestMapping("/file")
public class FileController {
	@Autowired
	private FileService fileService;
	@RequestMapping("/upload")
	@ResponseBody
	public String upload(MultipartFile file) throws IllegalStateException, IOException {
		try {
			System.out.println(Encrypt.EncodeMultipartFile(file, "SHA-512",0,1000000));
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getContentType());
		System.out.println(file.getSize());
		fileService.upload(file);
		return String.valueOf(file.getSize());
	}
}
 

