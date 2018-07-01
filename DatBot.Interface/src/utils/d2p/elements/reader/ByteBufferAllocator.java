package utils.d2p.elements.reader;

import java.nio.ByteBuffer;
import java.util.function.IntFunction;

public interface ByteBufferAllocator extends IntFunction<ByteBuffer> {

	default void release(ByteBuffer buffer) { }

	default ByteBuffer alloc(int capacity) { return apply(capacity); }

	public static ByteBufferAllocator unpooled(boolean direct) {
		return direct ? unpooledDirect() : unpooledHeap();
	}

	public static ByteBufferAllocator unpooledHeap() {
		return ByteBuffer::allocate;
	}

	public static ByteBufferAllocator unpooledDirect() {
		return ByteBuffer::allocateDirect;
	}
}
