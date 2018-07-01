package utils.d2p.elements.reader;

public interface BaseDataReader {
    long getPosition();
    void setPosition(long position);
    long plusPosition(long position);
    void resetPosition();
    boolean canRead(int n);
}
