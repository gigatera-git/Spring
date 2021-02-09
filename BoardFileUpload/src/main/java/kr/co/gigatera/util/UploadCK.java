package kr.co.gigatera.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class UploadCK {
	
public static Map<String,Object> uploadFiles(String uploadPath, MultipartFile multi) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapErr = new HashMap<String, Object>();
		
		String fileRealName = multi.getOriginalFilename();
		if (fileRealName!=null && fileRealName.length()>0) {
			UUID uuid= UUID.randomUUID();			
			String fileSaveName = uuid.toString() +"_"+ fileRealName;
			String fileSize = String.valueOf(multi.getSize());
			String fileType = multi.getContentType();
			byte[] fileData = multi.getBytes();
			
			String uploadSubPath = calcPath(uploadPath);			
			File file = new File(uploadPath+uploadSubPath, fileSaveName);
			FileCopyUtils.copy(fileData, file);
			
			String extention = fileRealName.substring(fileRealName.lastIndexOf(".")+1);
			String uploadFileName = null;
			
			if (Media.getImageType(extention)!=null) {
				uploadFileName = makeThumbnail(uploadPath, uploadSubPath, fileSaveName);
			} else {
				uploadFileName = makeIcon(uploadPath, uploadSubPath, fileSaveName);
			}			
			
			if (Media.getImageType(extention)!=null) {
				map.put("uploaded", 1);
				map.put("fileName", fileRealName);
				map.put("url", getUpFileUri(fileSaveName));
			} else {
				map.put("uploaded", 0);
				mapErr.put("message","이미지가 아닙니다");
				map.put("error",mapErr);
			}
			
		}
		
		return map;
	}

	private static String calcPath(String uploadPath) {
		Calendar cal = Calendar.getInstance();
		String yearPath = File.separator + cal.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		String thumbPath = datePath + File.separator + "thumb";
		makeDir(uploadPath, yearPath,monthPath,datePath,thumbPath );
		//logger.info(datePath);
				
	    return datePath;
	}
	
	private static String getUpFileUri(String fileSaveName) {
		Calendar cal = Calendar.getInstance();
		String yearPath = "/" + cal.get(Calendar.YEAR);
		String monthPath = yearPath + "/" + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
		String datePath = monthPath + "/" + new DecimalFormat("00").format(cal.get(Calendar.DATE));
				
	    return "/upload"+ datePath + "/" + fileSaveName;
	}
	
	private static void makeDir(String uploadPath, String...paths) {
		if (new File(paths[paths.length-1]).exists()) {
			return;
		}
		
		for (String path : paths) {
			File dirPath = new File(uploadPath + path);
			if (!dirPath.exists()) {
				dirPath.mkdir();
			}
		}
	}
	
	private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception{
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT,100);
		String thumbnailName = uploadPath + path + File.separator + "thumb" + File.separator + "s_" + fileName;
		//logger.info("thumbnailName : " + thumbnailName);
		
		File newFile = new File(thumbnailName);
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);  //extension
		
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		
		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	private static String makeIcon(String uploadPath, String path, String fileName) throws Exception{ 
		String iconName = uploadPath + path + File.separator + fileName;
		
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
}
