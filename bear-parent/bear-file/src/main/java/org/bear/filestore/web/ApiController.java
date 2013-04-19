package org.bear.filestore.web;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.bear.filestore.enums.ImageOperate;
import org.bear.filestore.ex.FileStoreException;
import org.bear.filestore.handle.ClientHandle;
import org.bear.filestore.handle.ClientHandleFactory;
import org.bear.filestore.handle.SpaceHandleChain;
import org.bear.filestore.model.File;
import org.bear.filestore.model.ImageAction;
import org.bear.filestore.model.Space;
import org.bear.filestore.service.FileStoreManager;
import org.bear.filestore.service.SpaceManager;
import org.bear.framework.Constants;
import org.bear.framework.ex.GlobalException;
import org.bear.framework.ex.ErrorCode;
import org.bear.framework.ex.ExceptionUtils;
import org.bear.framework.ex.NoPermissonException;
import org.bear.framework.security.SecHelper;
import org.bear.framework.util.Page;
import org.bear.global.type.AccessScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-7
 */

@Controller
@RequestMapping(value = "api")
public class ApiController {
    protected static final Logger LOG = LoggerFactory.getLogger(ApiController.class);
    @Autowired
    private FileStoreManager fileStoreService;
    @Autowired
    private SpaceManager spaceService;
    @Autowired
    private SpaceHandleChain spaceHandleChain;
    @Autowired
    private ClientHandleFactory clientHandleFactory;

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Map<String, Serializable> handleException(Exception ex, HttpServletRequest request) {
        LOG.debug("handle exception from request [" + request.getRequestURI() + "]", ex);
        int code = ErrorCode.SERVER_ERROR;
        if (ex instanceof GlobalException) {
            code = ((GlobalException) ex).getCode();
        } else if (ex instanceof MissingServletRequestParameterException) {
            code = ErrorCode.MISS_PARAM;
        } else if (ex instanceof TypeMismatchException || ex instanceof BindException) {
            code = ErrorCode.ILLEGAL_PARAM;
        }
        Map<String, Serializable> map = Maps.newHashMap();
        map.put(Constants.RET, code);
        map.put("msg", ex.toString());
        if (LOG.isDebugEnabled()) {
            map.put("detail", ExceptionUtils.buildStackTrace(ex));
        }
        return map;
    }

    @ModelAttribute("file")
    public File getFile(@RequestParam(value = "id", required = false) Long id) {
        return id == null ? new File() : fileStoreService.getFile(id);
    }

    @RequestMapping(value = "files")
    @ResponseBody
    public List<File> showFiles(
            @RequestParam(value = "bizKey") String bizKey,
            @RequestParam(value = "owner") String owner) {
        return fileStoreService.getFiles(bizKey, owner);
    }

    @RequestMapping(value = "user/files")
    @ResponseBody
    public Page<File> userFiles(@RequestParam(value = "bizKey", required = false) String bizKey,
                                @RequestParam(value = "owner", required = false) String owner,
                                @RequestParam(value = "start", defaultValue = "0") int start,
                                @RequestParam(value = "size", defaultValue = "10") int size) {
    	
        //return fileStoreService.getUserFiles(bizKey, owner, SecHelper.getUserId(), start, size);
    	return null;
    } 

    @RequestMapping(value = "files/remove")
    @ResponseBody
    public Map<String, Integer> removeFiles(HttpServletRequest request,
                                            @RequestParam(value = "bizKey") String bizKey,
                                            @RequestParam(value = "owner") String owner) {
        checkSpacePermission(bizKey, owner, request, true);
        fileStoreService.removeFiles(bizKey, owner);
        return success();
    }

    @RequestMapping(value = "file")
    @ResponseBody
    public File showFile(@ModelAttribute("file") File file) {
        return file;
    }

    @RequestMapping(value = "file/update")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public File updateFile(@ModelAttribute("file") File file,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "attr") String attr,
                           @RequestParam(value = "scope", required = false) AccessScope scope,
                           HttpServletRequest request, HttpServletResponse response) throws IOException {
        file.setName(name);
        if (scope != null) {
            file.setScope(scope);
        }
        if (StringUtils.isNotBlank(attr)) {
            file.setData(JSON.parseObject(attr));
        }
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
            Iterator<String> it = mRequest.getFileNames();
            if (it.hasNext()) {
                MultipartFile mFile = mRequest.getFile(it.next());
                spaceHandleChain.uploadNextFile(spaceService.getSpace(file.getBizKey()), file, mFile.getInputStream(), mRequest, response);
            }
        }
        fileStoreService.saveFile(file);
        return file;
    }

    @RequestMapping(value = "image/transform")
    @ResponseBody
    public File transformFile(@ModelAttribute("file") File file,
                              @RequestParam(value = "action") String[] actions, HttpServletRequest request) throws IOException {
        checkFilePermission(file, request, true);
        if (file.isImage() && ArrayUtils.isNotEmpty(actions)) {
            List<ImageAction> list = Lists.newArrayListWithCapacity(actions.length);
            for (String action : actions) {
                JSONObject json = JSON.parseObject(action);
                ImageAction ia = new ImageAction();
                ia.setImageOperate(ImageOperate.valueOf(StringUtils.upperCase(json.getString("op"))));
                ia.setParam(json);
                list.add(ia);
            }
            fileStoreService.transformImage(file.getId(), list);
        }
        return file;
    }


    @RequestMapping(value = "file/remove")
    @ResponseBody
    public Map<String, Integer> removeFile(@RequestParam(value = "id") Long[] ids, HttpServletRequest request) {
        for (Long id : ids) {
            checkFilePermission(fileStoreService.getFile(id), request, true);
        }
        fileStoreService.removeFile(ids);
        return success();
    }

    @RequestMapping(value = "upload")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public void uploadFile(@RequestParam(value = "bizKey") String bizKey,
                           @RequestParam(value = "owner", required = false) String owner,
                           @RequestParam(value = "attr", required = false) String attr,
                           @RequestParam(value = "client", defaultValue = "json") String client,
                           @RequestParam(value = "scope", required = false) AccessScope scope,
                           MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {
        Iterator<String> it = request.getFileNames();
        List<File> files = new ArrayList<File>();
        while (it.hasNext()) {
            MultipartFile mFile = request.getFile(it.next());
            if (!mFile.isEmpty()) {
                File file = new File();
                file.setBizKey(bizKey);
                file.setOwner(owner);
                file.setUserId(SecHelper.getUserId());
                file.setName(mFile.getOriginalFilename());
                file.setSize((int) mFile.getSize());
                if (scope != null) {
                    file.setScope(scope);
                }
                if (StringUtils.isNotBlank(attr)) {
                    file.setData(JSON.parseObject(attr));
                }
                boolean goon = spaceHandleChain.uploadNextFile(spaceService.getSpace(bizKey), file, mFile.getInputStream(), request, response);
                files.add(file);
                if (!goon) {
                    break;
                }
            }
        }
        ClientHandle ch = clientHandleFactory.getClientHandle(client.toLowerCase().trim());
        if (ch != null) {
            ch.doHandle(files, request, response);
        } else {
            failed(ErrorCode.ILLEGAL_PARAM, "clientHandle for client type [" + client + "] not found");
        }
    }

    protected Map<String, Integer> success() {
        return Collections.singletonMap(Constants.RET, ErrorCode.SUCCEED);
    }

    protected Map failed(int errorCode, String msg) {
        throw new FileStoreException(errorCode, msg);
    }

    private void checkSpacePermission(String bizKey, String owner, HttpServletRequest request, boolean needWrite) {
        Space space = spaceService.getSpace(bizKey);
        switch (space.getWriteScope()) {
            case TOKEN:
                if (!fileStoreService.hasPermission(request.getParameter("token"), space.getBizKey(), owner, needWrite)) {
                    throw new NoPermissonException("space:" + space.getBizKey() + ",owner:" + owner);
                }
                break;
        }
    }

    private void checkFilePermission(File file, HttpServletRequest request, boolean needWrite) {
        Space space = spaceService.getSpace(file.getBizId());
        AccessScope as = file.getScope();
        if (as == null) {
            as = space.getReadScope();
        }
        if (as != null) {
            switch (as) {
                case USER:
                    SecHelper.assertUser();
                    break;
                case SELF:
                    if (file.getUserId() != SecHelper.getUserId()) {
                        throw new NoPermissonException();
                    }
                    break;
                case TOKEN:
                    if (!fileStoreService.hasPermission(request.getParameter("token"), file.getId(), needWrite)) {
                        throw new NoPermissonException(file.getId().toString());
                    }
                    break;
                case ROLE:
            }
        }
    }
}
