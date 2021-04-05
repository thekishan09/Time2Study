package com.google.common.p011io;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.TreeTraverser;
import com.google.common.graph.SuccessorsFunction;
import com.google.common.graph.Traverser;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* renamed from: com.google.common.io.Files */
public final class Files {
    private static final SuccessorsFunction<File> FILE_TREE = new SuccessorsFunction<File>() {
        public Iterable<File> successors(File file) {
            return Files.fileTreeChildren(file);
        }
    };
    private static final TreeTraverser<File> FILE_TREE_TRAVERSER = new TreeTraverser<File>() {
        public Iterable<File> children(File file) {
            return Files.fileTreeChildren(file);
        }

        public String toString() {
            return "Files.fileTreeTraverser()";
        }
    };
    private static final int TEMP_DIR_ATTEMPTS = 10000;

    /* renamed from: com.google.common.io.Files$FilePredicate */
    private enum FilePredicate implements Predicate<File> {
        IS_DIRECTORY {
            public boolean apply(File file) {
                return file.isDirectory();
            }

            public String toString() {
                return "Files.isDirectory()";
            }
        },
        IS_FILE {
            public boolean apply(File file) {
                return file.isFile();
            }

            public String toString() {
                return "Files.isFile()";
            }
        }
    }

    private Files() {
    }

    public static BufferedReader newReader(File file, Charset charset) throws FileNotFoundException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(charset);
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
    }

    public static BufferedWriter newWriter(File file, Charset charset) throws FileNotFoundException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(charset);
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
    }

    public static ByteSource asByteSource(File file) {
        return new FileByteSource(file);
    }

    /* renamed from: com.google.common.io.Files$FileByteSource */
    private static final class FileByteSource extends ByteSource {
        private final File file;

        private FileByteSource(File file2) {
            this.file = (File) Preconditions.checkNotNull(file2);
        }

        public FileInputStream openStream() throws IOException {
            return new FileInputStream(this.file);
        }

        public Optional<Long> sizeIfKnown() {
            if (this.file.isFile()) {
                return Optional.m398of(Long.valueOf(this.file.length()));
            }
            return Optional.absent();
        }

        public long size() throws IOException {
            if (this.file.isFile()) {
                return this.file.length();
            }
            throw new FileNotFoundException(this.file.toString());
        }

        public byte[] read() throws IOException {
            Closer closer = Closer.create();
            try {
                FileInputStream in = (FileInputStream) closer.register(openStream());
                byte[] byteArray = ByteStreams.toByteArray(in, in.getChannel().size());
                closer.close();
                return byteArray;
            } catch (Throwable e) {
                closer.close();
                throw e;
            }
        }

        public String toString() {
            return "Files.asByteSource(" + this.file + ")";
        }
    }

    public static ByteSink asByteSink(File file, FileWriteMode... modes) {
        return new FileByteSink(file, modes);
    }

    /* renamed from: com.google.common.io.Files$FileByteSink */
    private static final class FileByteSink extends ByteSink {
        private final File file;
        private final ImmutableSet<FileWriteMode> modes;

        private FileByteSink(File file2, FileWriteMode... modes2) {
            this.file = (File) Preconditions.checkNotNull(file2);
            this.modes = ImmutableSet.copyOf((E[]) modes2);
        }

        public FileOutputStream openStream() throws IOException {
            return new FileOutputStream(this.file, this.modes.contains(FileWriteMode.APPEND));
        }

        public String toString() {
            return "Files.asByteSink(" + this.file + ", " + this.modes + ")";
        }
    }

    public static CharSource asCharSource(File file, Charset charset) {
        return asByteSource(file).asCharSource(charset);
    }

    public static CharSink asCharSink(File file, Charset charset, FileWriteMode... modes) {
        return asByteSink(file, modes).asCharSink(charset);
    }

    public static byte[] toByteArray(File file) throws IOException {
        return asByteSource(file).read();
    }

    @Deprecated
    public static String toString(File file, Charset charset) throws IOException {
        return asCharSource(file, charset).read();
    }

    public static void write(byte[] from, File to) throws IOException {
        asByteSink(to, new FileWriteMode[0]).write(from);
    }

    @Deprecated
    public static void write(CharSequence from, File to, Charset charset) throws IOException {
        asCharSink(to, charset, new FileWriteMode[0]).write(from);
    }

    public static void copy(File from, OutputStream to) throws IOException {
        asByteSource(from).copyTo(to);
    }

    public static void copy(File from, File to) throws IOException {
        Preconditions.checkArgument(!from.equals(to), "Source %s and destination %s must be different", (Object) from, (Object) to);
        asByteSource(from).copyTo(asByteSink(to, new FileWriteMode[0]));
    }

    @Deprecated
    public static void copy(File from, Charset charset, Appendable to) throws IOException {
        asCharSource(from, charset).copyTo(to);
    }

    @Deprecated
    public static void append(CharSequence from, File to, Charset charset) throws IOException {
        asCharSink(to, charset, FileWriteMode.APPEND).write(from);
    }

    public static boolean equal(File file1, File file2) throws IOException {
        Preconditions.checkNotNull(file1);
        Preconditions.checkNotNull(file2);
        if (file1 == file2 || file1.equals(file2)) {
            return true;
        }
        long len1 = file1.length();
        long len2 = file2.length();
        if (len1 == 0 || len2 == 0 || len1 == len2) {
            return asByteSource(file1).contentEquals(asByteSource(file2));
        }
        return false;
    }

    public static File createTempDir() {
        File baseDir = new File(System.getProperty("java.io.tmpdir"));
        String baseName = System.currentTimeMillis() + "-";
        for (int counter = 0; counter < TEMP_DIR_ATTEMPTS; counter++) {
            File tempDir = new File(baseDir, baseName + counter);
            if (tempDir.mkdir()) {
                return tempDir;
            }
        }
        throw new IllegalStateException("Failed to create directory within 10000 attempts (tried " + baseName + "0 to " + baseName + 9999 + ')');
    }

    public static void touch(File file) throws IOException {
        Preconditions.checkNotNull(file);
        if (!file.createNewFile() && !file.setLastModified(System.currentTimeMillis())) {
            throw new IOException("Unable to update modification time of " + file);
        }
    }

    public static void createParentDirs(File file) throws IOException {
        Preconditions.checkNotNull(file);
        File parent = file.getCanonicalFile().getParentFile();
        if (parent != null) {
            parent.mkdirs();
            if (!parent.isDirectory()) {
                throw new IOException("Unable to create parent directories of " + file);
            }
        }
    }

    public static void move(File from, File to) throws IOException {
        Preconditions.checkNotNull(from);
        Preconditions.checkNotNull(to);
        Preconditions.checkArgument(!from.equals(to), "Source %s and destination %s must be different", (Object) from, (Object) to);
        if (!from.renameTo(to)) {
            copy(from, to);
            if (from.delete()) {
                return;
            }
            if (!to.delete()) {
                throw new IOException("Unable to delete " + to);
            }
            throw new IOException("Unable to delete " + from);
        }
    }

    @Deprecated
    public static String readFirstLine(File file, Charset charset) throws IOException {
        return asCharSource(file, charset).readFirstLine();
    }

    public static List<String> readLines(File file, Charset charset) throws IOException {
        return (List) asCharSource(file, charset).readLines(new LineProcessor<List<String>>() {
            final List<String> result = Lists.newArrayList();

            public boolean processLine(String line) {
                this.result.add(line);
                return true;
            }

            public List<String> getResult() {
                return this.result;
            }
        });
    }

    @Deprecated
    public static <T> T readLines(File file, Charset charset, LineProcessor<T> callback) throws IOException {
        return asCharSource(file, charset).readLines(callback);
    }

    @Deprecated
    public static <T> T readBytes(File file, ByteProcessor<T> processor) throws IOException {
        return asByteSource(file).read(processor);
    }

    @Deprecated
    public static HashCode hash(File file, HashFunction hashFunction) throws IOException {
        return asByteSource(file).hash(hashFunction);
    }

    public static MappedByteBuffer map(File file) throws IOException {
        Preconditions.checkNotNull(file);
        return map(file, FileChannel.MapMode.READ_ONLY);
    }

    public static MappedByteBuffer map(File file, FileChannel.MapMode mode) throws IOException {
        return mapInternal(file, mode, -1);
    }

    public static MappedByteBuffer map(File file, FileChannel.MapMode mode, long size) throws IOException {
        Preconditions.checkArgument(size >= 0, "size (%s) may not be negative", size);
        return mapInternal(file, mode, size);
    }

    private static MappedByteBuffer mapInternal(File file, FileChannel.MapMode mode, long size) throws IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(mode);
        Closer closer = Closer.create();
        try {
            FileChannel channel = (FileChannel) closer.register(((RandomAccessFile) closer.register(new RandomAccessFile(file, mode == FileChannel.MapMode.READ_ONLY ? "r" : "rw"))).getChannel());
            MappedByteBuffer map = channel.map(mode, 0, size == -1 ? channel.size() : size);
            closer.close();
            return map;
        } catch (Throwable e) {
            closer.close();
            throw e;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x004d, code lost:
        if (r5.equals(".") != false) goto L_0x0051;
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0023 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String simplifyPath(java.lang.String r12) {
        /*
            com.google.common.base.Preconditions.checkNotNull(r12)
            int r0 = r12.length()
            java.lang.String r1 = "."
            if (r0 != 0) goto L_0x000c
            return r1
        L_0x000c:
            r0 = 47
            com.google.common.base.Splitter r2 = com.google.common.base.Splitter.m409on((char) r0)
            com.google.common.base.Splitter r2 = r2.omitEmptyStrings()
            java.lang.Iterable r2 = r2.split(r12)
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.util.Iterator r4 = r2.iterator()
        L_0x0023:
            boolean r5 = r4.hasNext()
            r6 = 0
            if (r5 == 0) goto L_0x007f
            java.lang.Object r5 = r4.next()
            java.lang.String r5 = (java.lang.String) r5
            r7 = -1
            int r8 = r5.hashCode()
            r9 = 46
            java.lang.String r10 = ".."
            r11 = 1
            if (r8 == r9) goto L_0x0049
            r6 = 1472(0x5c0, float:2.063E-42)
            if (r8 == r6) goto L_0x0041
        L_0x0040:
            goto L_0x0050
        L_0x0041:
            boolean r6 = r5.equals(r10)
            if (r6 == 0) goto L_0x0040
            r6 = 1
            goto L_0x0051
        L_0x0049:
            boolean r8 = r5.equals(r1)
            if (r8 == 0) goto L_0x0040
            goto L_0x0051
        L_0x0050:
            r6 = -1
        L_0x0051:
            if (r6 == 0) goto L_0x007e
            if (r6 == r11) goto L_0x0059
            r3.add(r5)
            goto L_0x007d
        L_0x0059:
            int r6 = r3.size()
            if (r6 <= 0) goto L_0x0079
            int r6 = r3.size()
            int r6 = r6 - r11
            java.lang.Object r6 = r3.get(r6)
            java.lang.String r6 = (java.lang.String) r6
            boolean r6 = r6.equals(r10)
            if (r6 != 0) goto L_0x0079
            int r6 = r3.size()
            int r6 = r6 - r11
            r3.remove(r6)
            goto L_0x007d
        L_0x0079:
            r3.add(r10)
        L_0x007d:
            goto L_0x0023
        L_0x007e:
            goto L_0x0023
        L_0x007f:
            com.google.common.base.Joiner r1 = com.google.common.base.Joiner.m396on((char) r0)
            java.lang.String r1 = r1.join((java.lang.Iterable<?>) r3)
            char r4 = r12.charAt(r6)
            if (r4 != r0) goto L_0x009e
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r4 = "/"
            r0.append(r4)
            r0.append(r1)
            java.lang.String r1 = r0.toString()
        L_0x009e:
            java.lang.String r0 = "/../"
            boolean r0 = r1.startsWith(r0)
            if (r0 == 0) goto L_0x00ac
            r0 = 3
            java.lang.String r1 = r1.substring(r0)
            goto L_0x009e
        L_0x00ac:
            java.lang.String r0 = "/.."
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x00b7
            java.lang.String r1 = "/"
            goto L_0x00c1
        L_0x00b7:
            java.lang.String r0 = ""
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00c1
            java.lang.String r1 = "."
        L_0x00c1:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.p011io.Files.simplifyPath(java.lang.String):java.lang.String");
    }

    public static String getFileExtension(String fullName) {
        Preconditions.checkNotNull(fullName);
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf(46);
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
    }

    public static String getNameWithoutExtension(String file) {
        Preconditions.checkNotNull(file);
        String fileName = new File(file).getName();
        int dotIndex = fileName.lastIndexOf(46);
        return dotIndex == -1 ? fileName : fileName.substring(0, dotIndex);
    }

    @Deprecated
    static TreeTraverser<File> fileTreeTraverser() {
        return FILE_TREE_TRAVERSER;
    }

    public static Traverser<File> fileTraverser() {
        return Traverser.forTree(FILE_TREE);
    }

    /* access modifiers changed from: private */
    public static Iterable<File> fileTreeChildren(File file) {
        File[] files;
        if (!file.isDirectory() || (files = file.listFiles()) == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(Arrays.asList(files));
    }

    public static Predicate<File> isDirectory() {
        return FilePredicate.IS_DIRECTORY;
    }

    public static Predicate<File> isFile() {
        return FilePredicate.IS_FILE;
    }
}
