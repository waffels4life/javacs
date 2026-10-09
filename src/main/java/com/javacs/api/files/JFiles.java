package com.javacs.api.files;

import com.javacs.api.annotations.Review;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class JFiles {
    /**
     * <h5>Path</h5>
     * <p><b>A {@code Path} is just an address on the file system.</b> It doesn't touch any files
     * or read anything; it simply indicates where that path is located.</p>
     * <p>The path represents <b>location</b>, not <b>content</b>.</p>
     *
     * <p>{@code Path}  -> Where is it</p>
     * <p>{@code Files} -> What to be done with it</p>
     */
    static class LearnPath {
        /*
         * Path
         * │
         * ├── creation
         * │   ├── Path.of()
         * │   └── Path.of(first, more...)
         * │
         * ├── analyze
         * │   ├── getFileName()
         * │   ├── getParent()
         * │   ├── getRoot()
         * │   └── getName(...)
         * │
         * ├── convert
         * │   ├── toAbsolutePath()
         * │   ├── toRealPath()
         * │   └── normalize()
         * │
         * ├── concat
         * │   ├── resolve()
         * │   └── resolveSibling()
         * │
         * ├── relations
         * │   └── relativize()
         * │
         * ├── checking
         * │   ├── isAbsolute()
         * │   └── startsWith() / endsWith()
         * │
         * └── convert
         *     ├── toString()
         *     └── toFile()
         */
        Path path_1 = Path.of("notes.txt");
        Path path_2 = Path.of("documents/java/notes.txt");

        // it is recommended to initialize path with separator
        Path path_3 = Path.of(
                "documents",
                "java",
                "notes.txt"
        ); // same as path_2

        // path.get() is old, but still valid
        Path path_4 = Paths.get("notes.txt");

        // absolute path
        Path path_abs_os = Path.of("/home/arsam/documents/notes.txt");  // Linux/Mac
        Path path_abs_win = Path.of("C:\\Users\\Arsam\\notes.txt");     // Windows
        // Always use `Path.of()` — it’s more readable and modern

        Path path = Path.of("projects/java/file-manager/src/Main.java");

        public Path pathFileName() {
            return path.getFileName();
        } // Main.java

        public Path pathParent() {
            return path.getParent();
        } // projects/java/file-manager/src

        public Path pathRoot() {
            // works with absolute path, else return null
            return path.getRoot();
        } // "C:\\User\Oreo\Meow.txt" -> C:\\

        /**
         * <p>real engagement with filesystem</p>
         * @return realPath
         */
        public Path pathToReal() throws IOException {
            /*
             * The path must exist.
             * Symbolic links are resolved.
             * The canonical/actual filesystem path is obtained.
             */
            return path.toRealPath();
        }

        /*
         * API	             |   Checks the filesystem?	  |  Purpose
         * normalize()	     |   No	                      |  Lexical simplification
         * toAbsolutePath()	 |   Usually no	              |  Converting to absolute path
         * toRealPath()	     |   Yes	                  |  Actual filesystem path
         */

        public Path pathNameIndex(int index) {
            /*
             * Path path = Path.of(
             *         "User",
             *         "Projects",
             *         "enrichable.java"
             * );
             *
             * path[0] -> User
             * path[1] -> Projects
             * path[2] -> enrichable.java
             */
            return path.getName(index);
        }

        public void pathPrintWithLoop() {

            for (int i = 0; i < path.getNameCount(); i++) {
                System.out.printf(
                        "[%d] : %s",
                        i,
                        path.getName(i)
                );
            }
        } // Path is iterable

        public Path pathSubPath(int start, int end) {
            if (start < end) {
                if (start < 0 || start >= path.getNameCount())
                    throw new ArrayIndexOutOfBoundsException();
                if (end >= path.getNameCount())
                    throw new ArrayIndexOutOfBoundsException();

                return path.subpath(start, end);
            }
            else throw new IllegalArgumentException(
                    "Start index cannot be higher form end index."
            );
        }

        public Path pathToAbsolute() {
            if (!path.isAbsolute())
                return path.toAbsolutePath();
            return path;
        } // convert to abs path, if not already

        public Path pathResolve(Path newPath) {
            return path.resolve(newPath);
        } // concat to paths

        public Path pathResolveNormal(Path newPath) {
            return path.resolve(newPath).normalize();
        } // concat to paths and normalize it

        /**
         * <p>Concat a serial of paths onto one.</p>
         * <p><b>Note:</b> concatenation paths using String is wrong.
         * it's much better and safer to user {@code p.resolve()} for it.</p>
         * @param paths
         * @return new set of path
         */
        public Path pathResolver(Path @NotNull ... paths) {
            if (paths.length == 0)
                throw new IllegalArgumentException(
                        "At least one path is required"
                );

            Path path = paths[0];
            for (Path value : paths) {
                path = path.resolve(value);
            }
            return path
                    .normalize()
                    .toAbsolutePath();
            /*
             * Now why is .resolve() better comparing to string concatenation :
             *      [1] Manages the separator manually
             *      [2] Eliminates the Path abstraction
             *      [3] Is error-prone
             *      [4] Does not account for filesystem semantics
             */
        }

        Path from     = Path.of("projects/java");
        Path to       = Path.of("projects/kotlin/src/Main.kt");
        Path relative = from.relativize(to).normalize();

        public boolean isJava() {
            String fileName = path.getFileName().toString();
            return fileName.endsWith(".java");
        }

        static final class PathPractice {

            Path root = Path.of("data");

            private Path create() {
                root = root
                        .resolve("user")
                        .resolve("arsam")
                        .resolve("config.properties");

                return root;
            }

            @Contract(pure = true) private @NotNull Path convertAbs() {
                return root.toAbsolutePath();
            }

            @Contract(pure = true) private @NotNull Path normalize() {
                return root.normalize();
            }
        }
    }

    static class LearnFiles {
        /**
         * File I/O
         * │
         * ├── 1. data type
         * │   ├── Text
         * │   └── Binary
         * │
         * ├── 2. Reading
         * │   ├── Files.readString
         * │   ├── Files.readAllLines
         * │   ├── BufferedReader
         * │   ├── InputStream
         * │   └── BufferedInputStream
         * │
         * ├── 3. Writing
         * │   ├── Files.writeString
         * │   ├── Files.write
         * │   ├── BufferedWriter
         * │   ├── OutputStream
         * │   └── BufferedOutputStream
         * │
         * ├── 4. Buffering
         * │   ├── buffer size
         * │   ├── flush
         * │   └── close
         * │
         * ├── 5. Resource Management
         * │   ├── AutoCloseable
         * │   ├── try-with-resources
         * │   └── ownership
         * │
         * ├── 6. Correctness
         * │   ├── Charset
         * │   ├── OpenOption
         * │   ├── EOF
         * │   └── partial reads/writes
         * │
         * ├── 7. Security
         * │   ├── Path traversal
         * │   ├── permissions
         * │   ├── symlink
         * │   └── TOCTOU
         * │
         * └── 8. Architecture
         *     ├── Repository
         *     ├── abstraction
         *     ├── error handling
         *     └── testability
         */

        public static byte @NotNull [] convertStrToByte(@NotNull String s) {
            return s.getBytes(StandardCharsets.UTF_8);
        }

        @Contract(value = "_ -> new", pure = true)
        public static @NotNull String convertByteToStr(byte[] bytes) {
            return new String(bytes, StandardCharsets.UTF_8);
        }

        public boolean filesExist(Path path) {
            return Files.exists(path); // == !Files.notExists(path)
        } // checks whether the file exists at the specified address

        /*
         * TOCTOU (not to be misunderstood with Hawk Tuah)
         *      — Time Of Check To Time Of Use
         *
         * The file might be deleted by another process.
         */

        public void filesReadSmallFiles(Path path) throws IOException {
            /*
             * It's better to explicitly specify the charset.
             * encoding is part of files contract.
             */
            String content = Files.readString(
                    path,
                    StandardCharsets.UTF_8
            );

            System.out.printf(content);
        }

        public void filesReadBigFiles(Path path) throws IOException {
            try (BufferedReader bufferedReader =
                         Files.newBufferedReader(path, StandardCharsets.UTF_8)
            ) {
                /*
                 * File
                 *  ↓
                 * Reader
                 *  ↓
                 * BufferedReader
                 */
                String line;
                while ((line = bufferedReader.readLine()) != null)
                    System.out.println(line);
                /*
                * null -> no more lines left
                * ↓
                * EOF  -> End Of File :: readLine() == null
                *
                * reader.ready() -> is not suitable for detecting eof
                * ↓
                * :: can we read something without getting blocked? != is it done
                *
                * */
            }
        }

        /*
        * Reader    -> character-oriented input
        *           -> A set of character that are readable
        * */

        public void filesBufferWriter(Path path, String content) throws IOException {
            try (BufferedWriter bufferedWriter =
                    Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

                bufferedWriter.write(content);
                bufferedWriter.newLine();
                /*
                * newLine() -> System.out.print("\n");
                *           -> an abstraction related to line separator
                * ↓
                * for 'portable' codes, newLine is much more semantic choice
                * */
            }
        }

        public void filesBufferReader(Path path) throws IOException {
            /*
            * sz: 8129  -> almost 8KB
            *           -> bigger buffer != faster buffer
            * */
            try (
                    BufferedReader reader = Files.newBufferedReader(path);
                    BufferedReader bufferedReader = new BufferedReader(reader, 8129)
            ) {
                bufferedReader.readLine();
            }
        }

        public void filesFlush(Path path, String content) throws IOException {

            try (
                    BufferedWriter writer = Files.newBufferedWriter(path);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer)
            ) {
                bufferedWriter.write(content);
                bufferedWriter.flush();
                /*
                * flush     -> push the pending data to down layers
                * close     -> flush and close the resource
                *
                * close = flush + closing
                * */
            }
        }

        public void filesWriteString(Path path, String content) throws IOException {
            /*
             * [ALERT] -> Risk of data loss
             *
             * This method deletes all existing content and data from the file (if any)
             * and overwrites it with the new content and data.
             */
            Files.writeString(
                    path,
                    content,
                    StandardCharsets.UTF_8
            );
        }

        // using buffer for binary files
        public void filesBufferReaderBinary(Path path) throws IOException {
            try (InputStream inputStream = Files.newInputStream(path)) {
                byte[] buffer = new byte[8129];
                int byteReads;
                while ((byteReads = inputStream.read()) != -1)
                    System.out.println(byteReads);
            }
        }

        /*
         * TEXT
         *      String
         *      ↓
         *      Writer
         *      ↓
         *      BufferedWriter
         *      ↓
         *      File
         *
         * BINARY
         *      byte[]
         *      ↓
         *      OutputStream
         *      ↓
         *      BufferedOutputStream
         *      ↓
         *      File
        * */

        public void filesWriteStringAppend(Path path,
                                           String content) throws IOException {

            /*
             * Here, new information is added to the existing file without deleting the old data.
             * WRITE    -> replace
             * APPEND   -> add to end
             */
            Files.writeString(
                    path,
                    content,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
            /*
             * @Review
             * CREATE               -> create new file, if it doesn't exist
             * CREATE_NEW           -> create new file under any circumstanced
             *                      -> throw [FileAlreadyExistsException] if exist
             * TRUNCATE_EXISTING    -> clear files information, if it exists
             * APPEND               ->
             * WRITE                ->
             * READ                 ->
             * DELETE_ON_CLOSE      ->
             * SYNC                 ->
             * DSYNC                ->
             * SPARSE               ->
             */
        }

        public void filesReadLines(Path path) throws IOException {
            List<String> lines = Files.readAllLines(
                    path,
                    StandardCharsets.UTF_8
            );
            for (String line : lines)
                System.out.printf(line);
        }

        public void filesReadLinesLambda(Path path) throws IOException {
            List<String> lines = Files.readAllLines(
                    path,
                    StandardCharsets.UTF_8
            );
            lines.forEach(System.out::println);
        }

        /*
         * [ALERT] -> Loading the entire file into memory completely and simultaneously
         *
         * `readAllLines` It reads the entire file and loads it all into memory,
         *  which is risky and represents poor design for large, heavy files.
         *
         * For large files, it is better to use `Files.lines()`, as it loads
         * data into memory incrementally using a stream.
         */

        public void filesReadLinesMassive(Path path) throws IOException {
            try (Stream<String> lines = Files.lines(path)) {
                lines.forEach(System.out::println);
            }
        }

        /*
         * Reading byte files (small files)
         *      [1] small image
         *      [2] small encrypted file
         *      [3] small binary file
         *      [4] hash input
         *
         * for larger files -> readAllBytes()
         */

        public void filesReadBytes(Path path) throws IOException {
            byte[] data = Files.readAllBytes(path);
        }

        public void filesWriteBytes(Path path, byte[] data) throws IOException {
            // no encoding needed because we are working with binary
            Files.write(
                    path,
                    data
            );
        }

        public void filesStreamInput(Path path) throws IOException {
            try (InputStream inputStream = Files.newInputStream(path)) {

                byte[] buffer = new byte[8192];
                int byteRead;

                while ((byteRead = inputStream.read(buffer)) != -1) {
                    // TODO
                }
            }
        }

        /*
         * Atomic-ish / transactional file update pattern
         *
         *     config.tmp
         *     ↓
         *     write completely
         *     ↓
         *     move
         *     ↓
         *     config.properties
        */

        public void filesCopy(Path source, Path target) throws IOException {
            Files.copy(
                    source,
                    target,
                    StandardCopyOption.REPLACE_EXISTING
            );
        }

        public void filesMove(Path source, Path target) throws IOException {
            Files.move(
                    source,
                    target,
                    StandardCopyOption.REPLACE_EXISTING
            );
        }

        public void filesCopyPasteContent(Path source, Path target) throws IOException {
            try (
                    InputStream inputStream = Files.newInputStream(source);
                    OutputStream outputStream = Files.newOutputStream(target)
            ) {

                byte[] buffer = new byte[8129];
                int byteRead;

                while ((byteRead = inputStream.read(buffer)) != -1)
                    outputStream.write(
                            buffer,
                            0,
                            byteRead
                    );
            }
        }

        /*
         * High-level
         *     Files.readString()
         *     Files.copy()
         *     Files.writeString()
         *
         *        ↓
         *
         * Mid-level
         *     BufferedReader
         *     BufferedWriter
         *
         *        ↓
         *
         * Low-level
         *     InputStream
         *     OutputStream
         *     byte[]
         *
         * the further we go down, more control and more responsibility there is.
         * */

        public void filesDirectory(Path path, int choice) throws IOException {
            if (choice == 0) Files.createDirectory(path);
            else Files.createDirectories(path);
        }

        public void filesDelete(Path path, boolean notSureExist) throws IOException {
            if (notSureExist) Files.deleteIfExists(path);
            else Files.delete(path);
        }

        static class FilesMetadata {

            private final Path path;

            public FilesMetadata(Path path) {
                this.path = path;
            }

            public long getFilesSize() throws IOException {
                return Files.size(path);
            }

            public long getFilesLastModified() throws IOException {
                return Files.getLastModifiedTime(path).toMillis();
            }

            public boolean getFilesIsReadable() throws IOException {
                return Files.isReadable(path);
            }

            public boolean getFilesIsWritable() throws IOException {
                return Files.isWritable(path);
            }

            public boolean getFilesIsExecutable() throws IOException {
                return Files.isExecutable(path);
            }

            public boolean filesCheckSameFile(Path path1, Path path2) throws IOException {
                return Files.isSameFile(path1, path2);
            }
        }

        /*
         * Java Files Exceptions
         *      [1] NoSuchFileException
         *      [2] FileAlreadyExistsException
         *      [3] AccessDeniedException
         *      [4] DirectoryNotEmptyException
         *      [5] NotDirectoryException
         *
         * catch (NoSuchFileException e) { <- more specific one comes first
         *     ...
         *
         * catch (IOException e) {
         *     ...
         *
         */
    }
    /*
     * Keynotes
     *
     * 1. Path = location
     *    Files = operation
     *
     * 2. Construct paths using `resolve`, not string concatenation.
     *
     * 3. Explicitly specify the encoding for text.
     *
     * 4. Resource → try-with-resources
     *
     * 5. Small file → `readString`/`readAllBytes`
     *    Large file → stream
     *
     * 6. Overwrite ≠ append
     *    Clearly understand the `OpenOption` choices.
     *
     * 7. `createDirectory` ≠ `createDirectories`
     *
     * 8. `delete` ≠ `deleteIfExists`
     *
     * 9. `list` ≠ `walk` ≠ `find`
     *
     * 10. Sensitive file → temp + move
     *
     * 11. User input + Path → normalize + containment check
     *
     * 12. Do not scatter the Files API throughout the entire application.
     *     Encapsulate it behind a suitable abstraction.
     *
     * 13. Inject the Path to make the code testable.
     *
     * 14. Do not swallow exceptions.
     *
     * 15. Do not rely on `exists()` as a security check.
     */
}
