package  com.gxecard.customerservice.util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;


public class ExportFileUtil {
	private static Logger looger = Logger.getLogger(ExportFileUtil.class);

	public static File createCSVFile(List exportData, LinkedHashMap map,
			String outPutPath, String fileName) {
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			File file = new File(outPutPath);
			if (!file.exists()) {
				file.mkdir();
			}
			// 定义文件名格式并创建
			csvFile = new File(file, fileName);
			// csvFile = File.createTempFile(fileName, ".csv",
			// new File(outPutPath));
			System.out.println("csvFile：" + csvFile);
			// UTF-8使正确读取分隔符","
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(csvFile), "UTF-8"), 1024);
			System.out.println("csvFileOutputStream：" + csvFileOutputStream);
			// 写入文件头部
			for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator
					.hasNext();) {
				Entry propertyEntry = (Entry) propertyIterator.next();
				csvFileOutputStream
						.write((String) propertyEntry.getValue() != null ? new String(
								((String) propertyEntry.getValue())
										.getBytes("UTF-8"), "UTF-8") : "");
				if (propertyIterator.hasNext()) {
					csvFileOutputStream.write(",");
				}
				System.out.println(new String(((String) propertyEntry
						.getValue()).getBytes("UTF-8"), "UTF-8"));
			}
			csvFileOutputStream.write("\r\n");
			// 写入文件内容
			for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
				Object row = (Object) iterator.next();
				for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator
						.hasNext();) {
					Entry propertyEntry = (Entry) propertyIterator.next();
					String property = BeanUtils.getProperty(row,(propertyEntry.getKey()) != null ? (String) propertyEntry.getKey() : "");
					String value = property == null? "" : property;
					csvFileOutputStream.write(value);
					if (propertyIterator.hasNext()) {
						csvFileOutputStream.write(",");
					}
				}
				if (iterator.hasNext()) {
					csvFileOutputStream.write("\r\n");
				}
			}
			csvFileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
			looger.error(e.toString());
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}

	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param csvFilePath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @throws IOException
	 */
	public static void exportFile(HttpServletResponse response,
			String csvFilePath, String fileName) throws IOException {
		response.setContentType("application/x-msexcel;charset=GBK");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ URLEncoder.encode(fileName, "UTF-8"));

		InputStream in = null;
		try {
			in = new FileInputStream(csvFilePath + fileName);

			int len = 0;
			byte[] buffer = new byte[1024];
			response.setCharacterEncoding("UTF-8");
			OutputStream out = response.getOutputStream();
			while ((len = in.read(buffer)) > 0) {
				out.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });

				out.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
			looger.error(e.toString());

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
