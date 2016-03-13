package pl.niekoniecznie.polar.io;

import pl.niekoniecznie.polar.model.Model.ListMessage;
import pl.niekoniecznie.polar.model.Model.ListMessage.ListEntry;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PolarFileSystem {

    private class PolarInputStream extends InputStream {

        private final byte[] buffer;
        private int pointer;

        public PolarInputStream(byte[] buffer) {
            this.buffer = buffer;
        }

        @Override
        public int read() {
            if (pointer >= buffer.length) {
                return -1;
            }

            return buffer[pointer++] & 0xff;
        }

        @Override
        public int available() {
            if (buffer == null) {
                return 0;
            }

            return buffer.length - pointer;
        }
    }

    private final PolarService service;

    public PolarFileSystem(final PolarService service) {
        this.service = service;
    }

    public InputStream get(final String path) {
        PolarRequest request = new PolarRequest(path);
        PolarResponse response;

        try {
            service.write(request.getData());
            response = service.recv();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new PolarInputStream(response.getBytes());
    }

    public List<ListEntry> list(final String path) {
        PolarRequest request = new PolarRequest(path);
        PolarResponse response;

        try {
            service.write(request.getData());
            response = service.recv();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return ListMessage.parseFrom(response.getBytes()).getEntryList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
