package com.javacs.morph;

import javax.imageio.ImageIO;
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
        ImageIO.write(bufferedImage, "jpg", output);
    }
}
