package connection;

import java.nio.ByteBuffer;

public interface Parser {
    Message convertToMessage(ByteBuffer byteBuffer);
    ByteBuffer convertToByteBuffer(Message message);
}
