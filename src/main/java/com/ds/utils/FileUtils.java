package com.ds.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
/**
 * 
 * @author tongdongsheng
 * @date 2016-12-09
 */
public class FileUtils extends BaseCloseUtils {
	/**
	 * @param path
	 * @return FileInputStream
	 */
	public static FileInputStream getInputStream(String path) {
		File file = new File(path);
		FileInputStream fis=null;
		if(file.exists()) {
			try {
				fis= new FileInputStream(file);
				return fis;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				closeResources(fis);
			}
		}
		return null;
	}
	/**
	 * get FileOutputStream ,if file not exits ,it will create new file
	 * @param path
	 * @return FileOutputStream
	 */
	public static FileOutputStream getOutputStream(String path) {
		File file = new File(path);
		FileOutputStream fout=null;
		keepFileExists(file, false);
		try {
			fout= new FileOutputStream(file);
			return fout;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeResources(fout);
		}
		return null;
	}
	/**
	 * get content from BufferedReader
	 * @param reader
	 * @return file content;
	 */
	public static String getReader(BufferedReader reader) {
		if(reader==null)return "";
		StringBuffer result=new StringBuffer();
		try {
			String line = reader.readLine();
			while(line !=null) {
				 result.append(line);
				 line =reader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeResources(reader);
		}
		return result.toString();
	}
	/**
	 * User InputStreamReader to reader file ,CHARSET is UTF-8
	 * @param path
	 * @return file content
	 */
	public static String streamToString(String path) {
		FileInputStream fis=getInputStream(path);
		if(fis==null) return null;
		BufferedReader reader =null;
		String content =null;
		try {
			reader= new BufferedReader(new InputStreamReader(fis, "UTF-8"));
			content=getReader(reader);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResources(fis,reader);
		}
		return content;
	}
	/**
	 * InputStream to String
	 * @param inputstream
	 * @return
	 */
	public static String streamToString(InputStream inputstream) {
		return streamToString(new InputStreamReader(inputstream));
	}
	/**
	 * InputStream to String
	 * @param inputstream
	 * @return
	 */
	public static String streamToString(InputStream inputstream,String charset) {
		try {
			return streamToString(new InputStreamReader(inputstream,charset));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeResources(inputstream);
			return null;
		}
	}
	/**
	 * Stream To String
	 * @param inputstream
	 * @return
	 */
	public static String streamToString(InputStreamReader inputstream) {
		if(inputstream==null) return null;
		BufferedReader reader =new BufferedReader(inputstream);
		String content=getReader(reader);
		closeResources(inputstream,reader);
		return content;
	}
	/**
	 * @param file
	 */
	public static void keepDirectoryExists(File file) {
		if(file ==null) return;
		if(!file.exists()||file.isFile()) {
			file.mkdirs();
		}
	}
	/**
	 * Keep File Exists
	 * @param file
	 * @param isClean , if set true ,it will delete the file to make clean,else nothing to do
	 */
	public static void keepFileExists(File file,boolean isClean) {
		if(file ==null) return;
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if(file.isFile()) {
	     	if(file.exists()) {
	     		if(isClean) {
	     			file.delete();
	     		} else return;
	     	}
	     	try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * Use OutputStreamWriter to save file
	 * @param path
	 * @param content
	 * @return if success return true else return false
	 */
	public static boolean streamWriter(String path,String content) {
		return streamWriter(getOutputStream(path), content);
	}
	/**
	 *  Use OutputStreamWriter to save file
	 * @param stream FileOutputStream
	 * @param content
	 * @return
	 */
	public static boolean streamWriter(OutputStream stream,String content) {
		return streamWriter(new OutputStreamWriter(stream), content);
	}
	/**
	 * Use OutputStreamWriter to save file
	 * @param stream OutputStreamWriter
	 * @param content
	 * @return
	 */
	public static boolean streamWriter(OutputStreamWriter stream,String content) {
		BufferedWriter writer =null;
		boolean save = false;
		writer = new BufferedWriter(stream);
		try {
			writer.write(content);
			writer.flush();
			save =true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResources(stream,writer);
		}
		return save;
	}
	/**
	 * Use FileReader to read file
	 * @param path
	 * @return file content
	 */
	public static String fileReaderString(String path) {
		File file = new File(path);
		if(!file.exists()) return null;
		FileReader freader=null;
		BufferedReader reader=null;
		String str= "";
		try {
			 freader =new FileReader(file);
			 reader = new BufferedReader(freader);
			 str = getReader(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResources(freader,reader);
		}
		return str;
	}
	/**
	 * Use FileWriter to save 
	 * @param path
	 * @param content
	 * @return if success return true else return false
	 */
	public static boolean fileWriter(String path,String content) {
		BufferedWriter writer=null;
		boolean save =false;
		File file = new File(path);
        try {
	        keepFileExists(file, false);	      
	        writer=new BufferedWriter(new FileWriter(file));  
	        writer.write(content);  
	        writer.flush();
	        save=true;
		} catch (IOException e) {
			e.printStackTrace();
		}
        finally  {
           closeResources(writer);
        }
        return save;
	}
	/**
	 * Use OutputStreamWriter to save UTF-8 
	 * @param path
	 * @param content
	 * @return if success return true else return false
	 */
	public static boolean fileWriteUtf8(String path,String content) {
		boolean save =false;
		File file = new File(path);
		keepFileExists(file, false);
		FileOutputStream fos=null;
		OutputStreamWriter osw=null;
		try {
		   fos = new FileOutputStream(path);   
	       osw = new OutputStreamWriter(fos, "UTF-8");   
	       osw.write(content);   
	       osw.flush(); 
	       save=true;
		}catch (IOException e) {
			e.printStackTrace();
		} finally  {
	        closeResources(fos,osw);
	    }
		return save;
	}
	/**
	 * Use fileAppend to save 
	 * @param path
	 * @param content
	 * @return if success return true else return false
	 */
	public static boolean fileAppend(String path,String content) {
		BufferedWriter writer=null;
		boolean save =false;
		File file = new File(path);
        try {
	        keepFileExists(file, false);	      
	        writer=new BufferedWriter(new FileWriter(file,true));
	        writer.append(content);  
	        writer.flush();
	        save=true;
		} catch (IOException e) {
			e.printStackTrace();
		}
        finally  {
           closeResources(writer);
        }
        return save;
	}
	/**
	 * read from ins, write to outs
	 * @param ins
	 * @param outs
	 * @return true or false
	 */
	public static boolean inputStreamToOutStream(InputStream ins,OutputStream outs) {
		boolean save = false;
		if(ins!=null&& outs!=null) {
			byte[] buffer = new byte[8192];
			int read;
			try {
				while((read=ins.read(buffer))!=-1) {
					outs.write(buffer,0,read);
				}
				outs.flush();
				save = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
		return save;
	}
	/**
	 * save network image to directory
	 * @param url
	 * @param dir
	 * @return filename
	 */
	public static String saveHttpImage(String url,String dir) {
		if(StringUtils.isEmpty(url)) return "";
		InputStream in = NetWorkUtils.getInputStream(url);
		int index = url.lastIndexOf(".");
		String filename=UUID.randomUUID().toString();
		
		if(index >0) {
			String ext = url.substring(index, url.length());
			if(!StringUtils.isEmpty(ext)&& index>url.lastIndexOf("/")) {
				if(".jpg".equalsIgnoreCase(ext)||".jpeg".equalsIgnoreCase(ext)||".gif".equalsIgnoreCase(ext)
						||".png".equalsIgnoreCase(ext)||".bmp".equalsIgnoreCase(ext)) {
					filename+=ext;
				}
			}
		}
		
		OutputStream out =getOutputStream(dir+"/"+filename);
		boolean ret = inputStreamToOutStream(in, out);
		closeResources(in,out);
		if(ret) {
			return filename;
		}
		return "";
		
	}
	
	public static int getFileCount(File file) {
		int count=0;
		if(file==null||!file.exists()) return count;
		if(file.isFile()) return ++count;
		for(File f :file.listFiles()) {
			count+=getFileCount(f);
		}
		return count;
	}
	public static void copyFile(File fromFile,File toFile) {
		if(fromFile==null||toFile==null) {
			return;
		}
		if(fromFile.isDirectory() && toFile.isDirectory()) {
			copyDirectory(fromFile, toFile);
		} else if(fromFile.isDirectory() && toFile.isFile()) {
			copyDirectory(fromFile, toFile.getParentFile());
		} else if(fromFile.isFile()) {
			copyFile2(fromFile, toFile);
		}
	}
	private static void copyDirectory(File fromFile,File toFile) {
		if(fromFile==null||toFile==null||!fromFile.exists()||fromFile.isFile()||toFile.isFile()) {
			return;
		}
		for(File f:fromFile.listFiles()) {
			if(f.isFile()) {
				copyFile2(f,toFile);
			} else if(f.isDirectory()) {
				copyDirectory(f,new File(toFile.getAbsolutePath()+File.separator+f.getName()));
			}
		}
	}
	private static void copyFile2(File fromFile,File toFile) {
		if(fromFile==null||toFile==null||!fromFile.exists()||fromFile.isDirectory()) {
			return;
		}
		if(toFile.isDirectory()) {
			copyFile2File(fromFile, new File(toFile.getAbsolutePath()+File.separator+fromFile.getName()));
		} else if(toFile.isFile()){
			copyFile2File(fromFile,toFile);
		}
	}
	private static void copyFile2File(File fromFile,File toFile) {
		if(fromFile==null||toFile==null||!fromFile.exists()
				||fromFile.isDirectory()||fromFile.isDirectory()) {
			return;
		}
		if(!toFile.getParentFile().exists()) {
			toFile.getParentFile().mkdirs();
		}
        FileInputStream ins=null;
        FileOutputStream out=null;
		try {
	        ins = new FileInputStream(fromFile);
	        out = new FileOutputStream(toFile);
	        inputStreamToOutStream(ins, out);
		} catch(IOException e) {
			
		}finally {
			closeResources(ins,out);
		}
	}
}
