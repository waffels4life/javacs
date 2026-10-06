package com.javacs.morph;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Images {
    /**
     * JPEG File
     * ┌──────────────────────────┐
     * │ JPEG Header              │
     * │ Metadata                 │
     * │ Quantization Tables      │
     * │ Huffman Tables           │
     * │ Compressed Image Data    │
     * └──────────────────────────┘
     *                      photo.jpg
     *                         │
     *         ┌───────────────┴───────────────┐
     *         │                               │
     *      Metadata                       Image Data
     *         │                               │
     *    dimensions                     compressed data
     *    color info
     *    EXIF
     *    ...
     *
     * JPEG
     *  │ decode
     *  ▼
     * Raw Pixel Representation >> BufferdImage
     *
     * ImageIO
     *   │
     *   ├── ImageReader
     *   │      ├── JPEG Reader
     *   │      ├── PNG Reader
     *   │      └── GIF Reader
     *   │
     *   └── ImageWriter
     *          ├── JPEG Writer
     *          ├── PNG Writer
     *          └── GIF Writer
     * ImageIO.read(...)    >> find the best ImageReader for this input
     * ImageIO.write(...)   >> find the best ImageWrite for this format
     */
    public static void main(String[] args) throws IOException {
        /*
         * File is just an abstraction for filesystem path
         */
        File input = new File("input.jpg");
        File output = new File("output.jpg");

        /*
         * input.jpg
         *     │
         *     ▼
         * ImageIO
         *     │
         *     ▼
         * ImageReader
         *     │
         *     ▼
         * JPEG Decoder
         *     │
         *     ▼
         * BufferedImage
         */
        BufferedImage bufferedImage = ImageIO.read(input);

        compressJpeg(bufferedImage, output, 0.60f);
    }

    private static void compressJpeg(BufferedImage bufferedImage,
                                     File fileOutput,
                                     float fileQuality) throws IOException {
        /*
         * BufferedImage
         *     │
         *     ▼
         * JPEG ImageWriter
         *     │
         *     ▼
         * JPEG Encoder
         *     │
         *     ▼
         * output.jpg
         */
        ImageWriter imageWriter = ImageIO
                .getImageWritersByFormatName("jpg")
                .next();

        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();

        imageWriteParam.setCompressionMode(
                ImageWriteParam.MODE_EXPLICIT
        );

        imageWriteParam.setCompressionQuality(fileQuality);

        imageWriter.setOutput(
                ImageIO.createImageOutputStream(fileOutput)
        );

        /*
         * IIOImage
         * ├── RenderedImage
         * ├── thumbnails
         * └── metadata
         */
        imageWriter.write(
                null,
                new IIOImage(bufferedImage, null, null),
                imageWriteParam
        );
        /*
         * new IIOImage(image, null, null)
         *              │      │     └── no metadata
         *              │      └── no thumbnails
         *              └── image
         */

        imageWriter.dispose();
    }
}
