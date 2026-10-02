import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;

/**
 * Fast, efficient, buffered file I/O for test harnesses.
 *
 * - Reading uses FileReader, which only ever opens a file in read mode.
 *   It has no write capability, so input.txt can never be truncated or
 *   modified by this class, no matter how it's used.
 * - Both streams use a 64KB buffer instead of the JDK default (8KB),
 *   cutting down real disk reads/writes for large files.
 */
class FastIO implements Closeable {

    private static final int BUFFER_SIZE = 1 << 16; // 64 KB

    private final BufferedReader reader;
    private final BufferedWriter writer;

    public FastIO(String inputPath, String outputPath) throws IOException {
        this.reader = new BufferedReader(new FileReader(inputPath), BUFFER_SIZE);
        this.writer = new BufferedWriter(new FileWriter(outputPath), BUFFER_SIZE);
    }

    /** Reads the next line, raw and unparsed. Returns null at end of file. */
    public String readLine() throws IOException {
        return reader.readLine();
    }

    /** Reads the next line and casts it to type T using the supplied parser. */
    public <T> T read(Function<String, T> caster) throws IOException {
        String line = reader.readLine();
        return (line == null) ? null : caster.apply(line.trim());
    }

    public String readString() throws IOException { return read(s -> s); }
    public int readInt() throws IOException { return read(Integer::parseInt); }
    public long readLong() throws IOException { return read(Long::parseLong); }
    public double readDouble() throws IOException { return read(Double::parseDouble); }

    /** Next line as a comma-separated int array. */
    public int[] readIntArray() throws IOException {
        return read(line -> Arrays.stream(line.split(","))
                .mapToInt(Integer::parseInt)
                .toArray());
    }

    /** Writes a value followed by a newline. */
    public void write(Object value) throws IOException {
        writer.write(String.valueOf(value));
        writer.newLine();
    }
    public void write(Object value, boolean flush) throws IOException {
        writer.write(String.valueOf(value));
        writer.newLine();
        if (flush) writer.flush();
    }

    @Override
    public void close() throws IOException {
        reader.close();
        writer.flush();
        writer.close();
    }
}
