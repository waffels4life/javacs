package com.javacs.projects.hackMethod;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.function.Function;

public class GetterTool {

    @FunctionalInterface
    public interface Getter<T, R> extends Function<T, R>, Serializable {}

    public static <T, R> String nameOf(final Getter<T, R> methodRef) {
        return toSerializedLambda(methodRef).getImplMethodName();
    }

    private static <T, R> SerializedLambda toSerializedLambda(final Object methodRef) {
        try {
            try (final CustomObjectOutputStream stream = new CustomObjectOutputStream()) {
                stream.writeObject(methodRef);
                return (SerializedLambda) stream.interceptedObject;
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class CustomObjectOutputStream extends ObjectOutputStream {
        private Object interceptedObject = null;

        public CustomObjectOutputStream() throws IOException {
            super(nullOutputStream());
            enableReplaceObject(true);
        }

        @Override protected Object replaceObject(final Object obj) {
            if (this.interceptedObject == null)
                this.interceptedObject = obj;

            return obj;
        }

        @Contract(value = " -> new", pure = true)
        public static @NotNull OutputStream nullOutputStream() {
            return new OutputStream() {
                @Override public void write(final int b) { }
                @Override public void write(final byte[] b, final int off, final int len) { }
                @Override public void close() { }
            };
        }
    }
}