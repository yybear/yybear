package org.bear.filestore.handle.impl;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.bear.filestore.ex.FileStoreException;
import org.bear.filestore.handle.SpaceHandle;
import org.bear.filestore.handle.SpaceHandleChain;
import org.bear.filestore.model.File;
import org.bear.filestore.model.Space;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-8
 */
public class DefaultSpaceHandleChain implements SpaceHandleChain {
    private List<SpaceHandle> handles;

    public void setHandles(List<SpaceHandle> handles) {
        this.handles = handles;
    }

    @Override
    public void requestFile(Space space, File file, String path, HttpServletRequest request, HttpServletResponse response) throws FileStoreException {
        new Chain(handles.iterator()).requestFile(space, null, path, request, response);
    }

    @Override
    public boolean uploadNextFile(Space space, File file, InputStream input, MultipartHttpServletRequest request, HttpServletResponse response) throws FileStoreException {
        return new Chain(handles.iterator()).uploadNextFile(space, file, input, request, response);
    }

    private class Chain implements SpaceHandleChain {
        private Iterator<SpaceHandle> handleIterator;

        public Chain(Iterator<SpaceHandle> handleIterator) {
            this.handleIterator = handleIterator;
        }

        @Override
        public void requestFile(Space space, File file, String path, HttpServletRequest request, HttpServletResponse response) throws FileStoreException {
            if (handleIterator.hasNext()) {
                SpaceHandle handle = handleIterator.next();
                if (handle.accept(space.getType())) {
                    handle.requestFile(space, file, path, request, response, this);
                } else {
                    this.requestFile(space, file, path, request, response);
                }
            }
        }

        @Override
        public boolean uploadNextFile(Space space, File file, InputStream input, MultipartHttpServletRequest request, HttpServletResponse response) throws FileStoreException {
            if (handleIterator.hasNext()) {
                SpaceHandle handle = handleIterator.next();
                if (handle.accept(space.getType())) {
                    return handle.uploadNextFile(space, file, input, request, response, this);
                } else {
                    return this.uploadNextFile(space, file, input, request, response);
                }
            }
            return true;
        }
    }
}
