package com.sunshine.ebook.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import org.apache.hadoop.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.sunshine.ebook.common.response.ErrorResponse;
import com.sunshine.ebook.request.BookRequest;
import com.sunshine.ebook.service.BookService;
import com.sunshine.ebook.util.GenerateUUID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "电子书相关接口")
@RestController
@RequestMapping(value = "api/v1/book")
public class BookController {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Value("${ebook.ebookPath}")
    private String ebookPath;
    
    @Autowired
    private BookService bookService;

    @ApiOperation(value = "上传电子书")
    @RequestMapping(value = "/uploadBookFile", method = RequestMethod.POST)
    //@RequiresRoles("admin")
    //@RequiresPermissions("upload")
    public ResponseEntity<ErrorResponse> uploadBookFile(
            @ApiParam(value = "文件", required = true) @RequestPart MultipartFile file,
            @ApiParam(value = "Json请求体", required = true) @RequestBody BookRequest bookRequest) {
        //获取当前的Subject
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "请先登录");
            return ResponseEntity.badRequest().body(response);
        }
        try {
            //检查用户是否有上传权限
            subject.checkPermission("upload");
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "没有权限");
            return ResponseEntity.badRequest().body(response);
        }
        String fileName = file.getOriginalFilename();
        String sux = fileName.substring(fileName.lastIndexOf("."));
        String relativelyPath = System.getProperty("user.dir");
        File parentFile = new File(relativelyPath).getParentFile();
        Integer userid = bookRequest.getUserid();
        String savePath = parentFile.getPath() + File.separator + ebookPath + File.separator + userid + File.separator;
        File folder = new File(savePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String saveFile = savePath + GenerateUUID.generateFileId() + sux;
        logger.info("文件保存路径：" + saveFile);
        try {
            FileOutputStream out = new FileOutputStream(new File(saveFile));
            IOUtils.copyBytes(file.getInputStream(), out, 1024, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> map = new HashMap<>();
        String name = bookRequest.getName();
        String author = bookRequest.getAuthor();
        Integer categoryid = bookRequest.getCategoryid();
        String cover = bookRequest.getCover();
        String description = bookRequest.getDescription();
        Date date = new Date();
        if (null != name) {
        	map.put("name", name);
        }
        if (null !=author) {
        	map.put("author", author);
        }
        if (null != categoryid) {
        	map.put("categoryid", categoryid);
        }
        if (null != cover) {
        	map.put("cover", cover);
        }
        if (null != description) {
        	map.put("description", description);
        }
        map.put("createtime", date);
        map.put("updatetime", date);
        boolean flag = bookService.saveBookinfo(map);
        if (flag) {
        	return ResponseEntity.ok().build();
        } else {
        	ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "文件上传失败");
			return ResponseEntity.badRequest().body(response);
        }
    }

}
