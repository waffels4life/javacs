package com.javacs.api.io;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public class JIOStream {
    /**
     * IO
     * ├── Byte Streams (for binary data)
     * │   ├── InputStream
     * │   └── OutputStream
     * │
     * └── Character Streams (for characters and strings)
     *     ├── Reader
     *     └── Writer
     *
     * The golden rule : if it's binary data (byte)          >> Byte Stream
     *                 : if it's text and characters (char)  >> Character Stream
     *
     */

    public static final class JInputStream {
        /*
         * InputStream (abstract)
         * ├── FileInputStream
         * ├── ByteArrayInputStream
         * ├── BufferedInputStream
         * ├── DataInputStream
         * ├── ObjectInputStream
         * ├── PipedInputStream
         * ├── SequenceInputStream
         * └── FilterInputStream
         */

        public JInputStream(Path path) throws IOException {
            try (InputStream inputStream = new FileInputStream(path.toFile())) {
                int data;
                while ((data = inputStream.read()) != -1)
                    System.out.println((char) data);
            }
        }
    }

    public static final class JOutputStream {
        /*
         * OutputStream (abstract)
         * ├── FileOutputStream
         * ├── ByteArrayOutputStream
         * ├── BufferedOutputStream
         * ├── DataOutputStream
         * ├── ObjectOutputStream
         * ├── PrintStream
         * └── PipedOutputStream
         */
        public <T> JOutputStream(@NotNull Path path, T message) throws IOException {
            try (OutputStream outputStream = new FileOutputStream(path.toFile())) {
                outputStream.write((byte) message);
                outputStream.flush();
            }
        }
    }

    /**
     * Character Streams
     *
     * Reader (abstract)
     * ├── FileReader
     * ├── BufferedReader
     * ├── InputStreamReader  → bridge between byte & char
     * ├── StringReader
     * └── CharArrayReader
     *
     * Writer (abstract)
     * ├── FileWriter
     * ├── BufferedWriter
     * ├── OutputStreamWriter → bridge between byte & char
     * ├── StringWriter
     * └── PrintWriter
     */
    public <T> void charStream(T message) throws IOException {
        try (Writer writer = new FileWriter("chars.txt")) {
            writer.write((String) message);
        }
    }

    public <T> void byteArray(T message) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream
                = new ByteArrayOutputStream();

        byteArrayOutputStream.write((byte) message);

        byte[] data = byteArrayOutputStream.toByteArray();

        ByteArrayInputStream byteArrayInputStream
                = new ByteArrayInputStream(data);

        int c;
        while ((c = byteArrayInputStream.read()) != -1)
            System.out.print((char) c);

    }

    public static final class StreamChunkReader {
        /*
         *                  10 GB File
         *                      │
         *                      │
         *           ┌──────────┴──────────┐
         *           │                     │
         *         Disk                   RAM
         *           │                     │
         *           └─────── chunks ──────┘
         *
         * Disk:
         * [A][B][C][D][E][F][G][H]...
         *
         * RAM:
         *      [A] -> process
         *               ↓
         *              [B] -> process
         *                       ↓
         *                      [C] -> process
         *                               ↓
         *                              ...
         * [InputStream]  >> A sequential source of bytes from which we can read data
         * [OutputStream] >> A destination into which we can write bytes
         *
         * If we use abstractions, it does not matter where the data comes from;
         * it is simply processed by the central engine core.
         *
         * File
         * ↓
         * FileInputStream ┐
         * Network         ├── InputStream ──→ engine
         * Socket          ┘
         *
         * we use 'int' for read()  >> showcase 0..255 -> for bytes
         *                          >> -1 for EOF
         *
         */

        private static final int MIN_LOG2_SIZE = 1;
        private static final int MAX_LOG2_SIZE = 20;

        private final int bufferSize;

        public StreamChunkReader(int log2BufferSize) {
            if (log2BufferSize < MIN_LOG2_SIZE || log2BufferSize > MAX_LOG2_SIZE)
                throw new IllegalArgumentException(
                        "log2BufferSize must be between "
                                + MIN_LOG2_SIZE
                                + " and "
                                + MAX_LOG2_SIZE
                );

            this.bufferSize = 1 << log2BufferSize;
        }

        public long readFully(InputStream inputStream,
                              Consumer<byte[]> chunkConsumer) throws IOException {

            Objects.requireNonNull(inputStream, "inputStream");
            Objects.requireNonNull(chunkConsumer, "chunkConsumer");

            final byte[] buffer = new byte[bufferSize];
            long total = 0;
            int count;

            try (InputStream in = inputStream) {
                while ((count = in.read(buffer)) != -1) {
                    if (count == 0) continue;
                    chunkConsumer.accept(Arrays.copyOf(buffer, count));
                    total += count;
                }
            }
            return total;
        }
    }
}
