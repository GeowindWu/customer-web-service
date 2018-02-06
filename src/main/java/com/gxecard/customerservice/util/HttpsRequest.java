package com.gxecard.customerservice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;


public class HttpsRequest {
	// 每次读取的响应数据大小
	private static  int EveryReadResponseLen = 10240;
	
	
	@SuppressWarnings("rawtypes")
	public static  String httpsAccess(String accessUrl, Map params,
			String charSet,HtppsConfig config) throws Exception {
		// 信任库
		File trustStoreFile = new File(config.getTrustStorePath());
		// 密钥库
		File keyStoreFile = new File(config.getKeyStorePath());
		// 信任库密码
		char[] tru_ary = config.getTrustStorePw().toCharArray();
		// 客户端keystore库密码
		char[] keys_ary = config.getKeyStorePw().toCharArray();
		// 服务端支持的安全协议
		String[] supportedProtocols = { "TLSv1.2" };

		
		  KeyStore keyStore = KeyStore.getInstance("PKCS12");
		  keyStore.load(new FileInputStream(new File(config.getKeyStorePath())), keys_ary);
		SSLContext sslContext = SSLContexts.custom()
                //忽略掉对服务器端证书的校验
//                .loadTrustMaterial(new TrustStrategy() {
//                    @Override
//                    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                        return true;
//                    }
//                })
                
//                加载服务端提供的truststore(如果服务器提供truststore的话就不用忽略对服务器端证书的校验了)
                .loadTrustMaterial(new File(config.getTrustStorePath()), tru_ary)
//                		new TrustSelfSignedStrategy()  )
                .loadKeyMaterial(keyStore, keys_ary)
                .build();
		
		
		// 构建链接工厂
		// old :HostnameVerifier hostNameVer =
		// SSLConnectionSocketFactory.getDefaultHostnameVerifier();
		HostnameVerifier hostNameVer = new HostnameVerifier() {// 内网不校验主机名，因为内网可能是用IP访问
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		};
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
				sslContext, supportedProtocols, null, hostNameVer);

		return httpPost(socketFactory, accessUrl, params, charSet);
	}

	// 使用链接工厂进行httpPost访问
	@SuppressWarnings("rawtypes")
	private static String httpPost(SSLConnectionSocketFactory socketFactory,
			String accessUrl, Map params, String charSet) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;

		String returnStr = null;
		try {
			// 1.构建httpClient
			httpClient = HttpClients.custom()
					.setSSLSocketFactory(socketFactory).build();
			HttpPost post = new HttpPost(accessUrl);

			// 2.输入参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Iterator it = params.keySet().iterator();
			while (it.hasNext()) {// 所有的属性都构建参数
				Object tmp = it.next();
				if (tmp != null) { // 构建参数
					Object tmpVal = params.get(tmp);
					if (tmpVal != null)
						nvps.add(new BasicNameValuePair(tmp.toString(), tmpVal
								.toString()));
				}
			}
			post.setEntity(new UrlEncodedFormEntity(nvps));

			// 3.获取响应结果
			response = httpClient.execute(post);
			entity = response.getEntity();

			// 4.读取字符串
			returnStr = readString(entity, charSet);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			closeStream(httpClient, response, entity);
		}
		return returnStr;
	}

	/**
	 * 根据输入读取数据
	 * 
	 * @param entity
	 * @return
	 */
	private static String readString(HttpEntity entity, String charSet)
			throws Exception {
		InputStream inStream = null;

		byte[] allResponse = new byte[0];
		int allLen = 0;
		try {
			inStream = entity.getContent();
			byte[] readArray = new byte[EveryReadResponseLen];
			int readLen = inStream.read(readArray); // 读取满整个数组
			while (readLen > 0) {
				allLen += readLen;
				allResponse = addArray4Byte(allResponse, readArray, readLen);
				// 读取第二组数据
				readLen = inStream.read(readArray);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inStream != null)
					inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (allLen != allResponse.length)
			throw new RuntimeException("系统异常，读取长度不能与计算长度！");
		// 返回字符串
		return new String(allResponse, 0, allLen, charSet);
	}

	// 合并数组
	private static byte[] addArray4Byte(byte[] first, byte[] next, int nextLen) {
		byte[] addArray = new byte[first.length + nextLen];

		int index = 0, firstLen = first.length;
		for (index = 0; index < firstLen; ++index)
			// 拷贝第一个数组
			addArray[index] = first[index];

		for (; index < addArray.length; ++index)
			// 拷贝第二个数组
			addArray[index] = next[index - firstLen];

		return addArray;
	}

	private static void closeStream(CloseableHttpClient httpClient,
			CloseableHttpResponse response, HttpEntity entity) {
		try {// 关闭流数据
			if (entity != null)
				EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (response != null)
				response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (httpClient != null)
				httpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
}
