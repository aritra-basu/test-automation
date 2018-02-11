package com.test.automation.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class FileUtils {
	
	public static File file;
	public static File newFile;
	public static File dir;
	public static File[] files;
	
	public static void moveFile(String fileToMove, String movedFile){
		file = new File(fileToMove);
		newFile = new File(movedFile);
		file.renameTo(newFile);
	}
	
	@SuppressWarnings("resource")
	public static void copyFileUsingChannel(String source, String dest) throws Exception {
	    FileChannel sourceChannel = null;
	    FileChannel destChannel = null;
	    sourceChannel = new FileInputStream(new File(source)).getChannel();
	    destChannel = new FileOutputStream(new File(dest)).getChannel();
	    destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
	    sourceChannel.close();
	    destChannel.close();
	   
	}

	public static String getFileNameFromDirectory(String parentDirectory, String substring) throws Exception{
		dir = new File(parentDirectory);
		files = dir.listFiles();
		String fileName = null;
		for (File file : files) {
			if (file.isFile() && file.getName().contains(substring)){
				fileName = file.getName();
			}	
		}
		return fileName;	
	}
	
	public static String getFilePathFromDirectory(String parentDirectory, String substring) throws Exception{
		dir = new File(parentDirectory);
		files = dir.listFiles();
		String filePath = null;
		for (File file : files) {
			if (file.isFile() && file.getName().contains(substring)){
				filePath = file.getPath();
				break;
			}	
		}
		 return filePath;	
	}
	
	public static String getFileNameWithoutExtension(String parentDirectory, String substring) throws Exception{
		String fileName = FileUtils.getFileNameFromDirectory(parentDirectory, substring);
		String fileNameWithoutExtension = fileName.substring(0, fileName.indexOf("."));
		return fileNameWithoutExtension;
	}

	public static String getFileExtension(String parentDirectory, String substring) throws Exception{
		String fileName = FileUtils.getFileNameFromDirectory(parentDirectory, substring);
		String fileExtension = fileName.substring(fileName.indexOf("."));
		return fileExtension;
	}

	

	
}
