package org.linys.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * @Description: 文件处理类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-2-1
 * @author lys
 * @vesion 1.0
 */
public class FileUtil {
	/**
	 * @Description: 保存文件
	 * @Create: 2013-2-1 下午2:04:47
	 * @author lys
	 * @update logs
	 * @param source
	 * @param dest
	 */
	public static void saveFile(File source,File dest){
		InputStream is = null;
		OutputStream os = null;
		try {
			 is = new  BufferedInputStream(new FileInputStream(source));
			
			 os = new BufferedOutputStream(new FileOutputStream(dest));
			
			int len = 0;
			
			byte[] buffer = new byte[1024];

			while (-1 != (len = is.read(buffer)))
			{
				os.write(buffer, 0, len);
				
			}
			os.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(os !=null){
					os.close();
				}
				if(is!=null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * @Description: 将文件保存到指定的路径下
	 * @Create: 2013-2-1 下午2:07:46
	 * @author lys
	 * @update logs
	 * @param source
	 * @param destPath
	 */
	public static void saveFile(File source,String destPath){
		InputStream is = null;
		OutputStream os = null;
		File dest = new File(destPath);
		try {
			 is = new  BufferedInputStream(new FileInputStream(source));
			
			 os = new BufferedOutputStream(new FileOutputStream(dest));
			
			int len = 0;
			
			byte[] buffer = new byte[1024];

			while (-1 != (len = is.read(buffer)))
			{
				os.write(buffer, 0, len);
				
			}
			os.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(os !=null){
					os.close();
				}
				if(is!=null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}


