package com.tmlk.framework.util;


import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * 图片截取 并剪切 剪切
 */
public class ImageUtils {

    // ===源图片路径名称如：c:\1.jpg
    private String srcpath;

    // ===剪切图片存放路径名称。如：c:\2.jpg
    private String savepath;
    // ===剪切点x坐标
    private int x;
    private int y;

    // ===剪切点宽度
    private int width;
    private int height;

    public ImageUtils() {
    }

    public ImageUtils(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * 剪切图片 并压缩为 border 的边长的正方形
     *
     * @param border
     * @throws IOException
     */
    public void cut(int border) throws IOException {
        FileInputStream is = null;
        ImageInputStream iis = null;
        try {
            // 读取图片文件
            is = new FileInputStream(srcpath);

            int begin = srcpath.lastIndexOf(".") + 1;
            String extName = srcpath.substring(begin);

            System.out.println(extName);

            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(new String(extName.getBytes(), "utf-8"));
            ImageReader reader = it.next();

            // 获取图片流
            iis = ImageIO.createImageInputStream(is);

            reader.setInput(iis, true);

            ImageReadParam param = reader.getDefaultReadParam();

            //剪切的方框
            Rectangle rect = new Rectangle(x, y, width, height);

            // 提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);

            BufferedImage bi = reader.read(0, param);

            //压缩为 border * border
            BufferedImage bb = this.resize(bi, border, border);
            // 保存新图片
            ImageIO.write(bb, extName, new File(savepath));
        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            if (is != null)
                is.close();
            if (iis != null)
                iis.close();
        }
    }



    public static BufferedImage resize(BufferedImage srcBufImage, int width, int height) {
        BufferedImage bufTarget = null;
        double sx = (double) width / srcBufImage.getWidth();
        double sy = (double) height / srcBufImage.getHeight();
        int type = srcBufImage.getType();
        if (type == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = srcBufImage.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(width,
                    height);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            bufTarget = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else
            bufTarget = new BufferedImage(width, height, type);
        Graphics2D g = bufTarget.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(srcBufImage, AffineTransform.getScaleInstance(sx,
                sy));
        g.dispose();
        return bufTarget;
    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSrcpath() {
        return srcpath;
    }

    public void setSrcpath(String srcpath) {
        this.srcpath = srcpath;
    }

    public String getSavepath() {
        return savepath;
    }

    public void setSavepath(String savepath) {
        this.savepath = savepath;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
