package com.mmnttech.me.common.server.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * @类名 ImageHelper
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 云南动量科技有限公司
 * @创建人 James
 * @创建时间 2018年1月6日 下午5:12:08
 * @版本 v1.0
 * 
 */
public class ImageHelper {

	private static Logger logger = LoggerFactory.getLogger(ImageHelper.class);
	
	public static void scaleImage(InputStream imageInputStream, String destinationPath, double scale) {

		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(imageInputStream);
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();

			width = parseDoubleToInt(width * scale);
			height = parseDoubleToInt(height * scale);

			Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage outputImage = new BufferedImage(width, height, Transparency.TRANSLUCENT);
			Graphics graphics = outputImage.getGraphics();
			graphics.drawImage(image, 0, 0, null);
			graphics.dispose();

			ImageIO.write(outputImage, "png", new File(destinationPath));
		} catch (IOException e) {
			logger.error("进行图片尺寸缩放异常", e);
		}

	}

	public static void scaleImage(InputStream imageInputStream, String destinationPath
			, int width, int height) {
		FileOutputStream fos = null;
		try {
			BufferedImage bufferedImage = null;
			bufferedImage = ImageIO.read(imageInputStream);
			
			int oldImageWidth = bufferedImage.getWidth(null);
			int oldImageHeight = bufferedImage.getHeight(null);
			
			if(oldImageWidth < width && oldImageHeight < height) {
				byte[] byteInfos = new byte[imageInputStream.available()];
				imageInputStream.read(byteInfos);
				
				File tmpFile = new File(destinationPath);
				fos = new FileOutputStream(tmpFile);
				fos.write(byteInfos);
			} else {
				double rate1 = ((double) oldImageWidth) / (double) width + 0.1;
				double rate2 = ((double) oldImageHeight) / (double) height + 0.1;
				double rate = rate1 > rate2 ? rate1 : rate2;
				
				int newWidth = (int) (((double) oldImageWidth) / rate);
				int newHeight = (int) (((double) oldImageHeight) / rate);

				Image image = bufferedImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
				BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TRANSLUCENT);
				Graphics graphics = outputImage.getGraphics();
				graphics.drawImage(image, 0, 0, null);
				graphics.dispose();
				ImageIO.write(outputImage, "png", new File(destinationPath));
			}
		} catch (Exception e) {
			logger.error("scaleImageWithParams进行图片压缩异常", e);
		} finally {
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static byte[] createQRImage(String url) {
		int width = 300;
		int height = 300;
		
		ByteArrayOutputStream os = null;
		try {
			BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height);
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					bufferedImage.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
				}
			}
			os = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", os);
			
			return os.toByteArray();
		} catch (Exception e) {
			 logger.error("生成二维码图片异常", e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					logger.error("生成二维码图片关闭IO异常", e);
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 将double类型的数据转换为int，四舍五入原则
	 * 
	 * @param sourceDouble
	 * @return
	 */
	private static int parseDoubleToInt(double sourceDouble) {
		int result = 0;
		result = (int) sourceDouble;
		return result;
	}

	/***
	 * 返回两个数a/b的小数点后三位的表示
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double getDot2Decimal(int a, int b) {

		BigDecimal bigDecimal_1 = new BigDecimal(a);
		BigDecimal bigDecimal_2 = new BigDecimal(b);
		BigDecimal bigDecimal_result = bigDecimal_1.divide(bigDecimal_2, new MathContext(4));
		Double double1 = new Double(bigDecimal_result.toString());
		System.out.println("相除后的double为：" + double1);
		return double1;
	}

}
