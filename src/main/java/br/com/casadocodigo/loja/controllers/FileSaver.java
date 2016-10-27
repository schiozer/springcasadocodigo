package br.com.casadocodigo.loja.controllers;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSaver {
	
	@Autowired
	private HttpServletRequest request;
	
	public String write(String baseFolder, MultipartFile file) {
		
		String realPath = request.getServletContext().getRealPath("/" + baseFolder);
		try {
		
			String path = realPath + "/" + file.getOriginalFilename();
			
			File auxFileDir = new File(realPath);
			if(! auxFileDir.exists()) {
				auxFileDir.mkdir();
				System.out.println(" Real path created: " + auxFileDir);
            }
			
			file.transferTo(new File(path));
			return baseFolder + "/" + file.getOriginalFilename();
		
		} catch (IOException e) {
	
			throw new RuntimeException(e);
		}
	}

}
