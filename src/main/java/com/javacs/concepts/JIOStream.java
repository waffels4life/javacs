package com.javacs.concepts;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public class JIOStream {
    /**
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
    public static final class StreamChunkReader {

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
