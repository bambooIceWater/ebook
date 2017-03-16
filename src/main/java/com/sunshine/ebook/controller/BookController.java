package com.sunshine.ebook.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.sunshine.ebook.common.response.ContentResponse;
import com.sunshine.ebook.entity.Book;
import com.sunshine.ebook.service.BookService;
import com.sunshine.ebook.util.GenerateUUID;
import com.sunshine.ebook.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "电子书相关接口")
@RestController
@RequestMapping(value = "api/v1/book")
public class BookController {
	
	private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Value("${ebook.ebookPath}")
    private String ebookPath;
    
    @Autowired
    private BookService bookService;

    @ApiOperation(value = "上传电子书")
    @RequestMapping(value = "/uploadBookFile", method = RequestMethod.POST)
    //@RequiresRoles("admin")
    //@RequiresPermissions("upload")
    public ResponseEntity<ContentResponse> uploadBookFile(
            @ApiParam(value = "电子书文件", required = true) @RequestPart MultipartFile bookFile,
            @ApiParam(value = "电子书封面", required = true) @RequestPart MultipartFile coverFile,
            @ApiParam(value = "书名", required = true) @RequestParam("name") String name,
            @ApiParam(value = "作者", required = true) @RequestParam("author") String author,
            @ApiParam(value = "分类ID", required = true) @RequestParam("categoryid") String categoryid,
            @ApiParam(value = "简介", required = true) @RequestParam("description") String description,
            @ApiParam(value = "用户ID", required = true) @RequestParam("userid") Integer userid) {
        //获取当前的Subject
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            ContentResponse response = new ContentResponse(HttpStatus.BAD_REQUEST.value(), "请先登录");
            return ResponseEntity.badRequest().body(response);
        }
        try {
            //检查用户是否有上传权限
            subject.checkPermission("upload");
        } catch (Exception e) {
            e.printStackTrace();
            ContentResponse response = new ContentResponse(HttpStatus.BAD_REQUEST.value(), "没有权限");
            return ResponseEntity.badRequest().body(response);
        }
        String fileName = bookFile.getOriginalFilename();
        String bookSux = fileName.substring(fileName.lastIndexOf("."));
        String coverName = coverFile.getOriginalFilename();
        String coverSux = coverName.substring(coverName.lastIndexOf("."));
        String relativelyPath = System.getProperty("user.dir");
        File parentFile = new File(relativelyPath).getParentFile();
        String uuid = GenerateUUID.generateFileId();
        String savePath = parentFile.getPath() + File.separator + ebookPath + File.separator + userid + File.separator + uuid + File.separator;
        File folder = new File(savePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String saveBookFile = savePath + uuid + bookSux;
        String saveCoverFile = savePath + uuid + coverSux;
        logger.info("文件保存路径：" + saveBookFile);
        try {
            //保存电子书文件
        	FileUtil.saveFileToPath(saveBookFile, bookFile.getInputStream(), true);

            //保存封面文件
        	FileUtil.saveFileToPath(saveCoverFile, coverFile.getInputStream(), false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int lineCount = FileUtil.getTotalLines(saveBookFile);
        HashMap<String, Object> map = new HashMap<>();
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
        if (null != description) {
        	map.put("description", description);
        }
        map.put("savepath", saveBookFile);
        map.put("cover", saveCoverFile);
        map.put("linecount", lineCount);
        map.put("createtime", date);
        map.put("updatetime", date);
        boolean flag = bookService.saveBookinfo(map);
        if (flag) {
        	return ResponseEntity.ok().build();
        } else {
        	ContentResponse response = new ContentResponse(HttpStatus.BAD_REQUEST.value(), "文件上传失败");
			return ResponseEntity.badRequest().body(response);
        }
    }

    @ApiOperation(value = "阅读电子书")
    @RequestMapping(value = "/readBookForPage", method = RequestMethod.GET)
    public ResponseEntity<String> readBookForPage(
            @ApiParam(value = "电子书ID", required = true) @RequestParam("bookId") String bookId,
            @ApiParam(value = "每页行数", required = true) @RequestParam("pageSize") int pageSize,
            @ApiParam(value = "当前页码", required = true) @RequestParam("pageNum") int pageNum) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("bookid", bookId);
        Book bookinfo = bookService.getBookinfoByCondition(map);
        String content = null;
        if (null != bookinfo) {
            String filePath = bookinfo.getSavepath();
            Integer lineCount = bookinfo.getLinecount();
            content = FileUtil.readForPage(filePath, pageNum, pageSize, lineCount);
        }
        return ResponseEntity.ok().body(content);
    }

    @ApiOperation(value = "查询电子书")
    @RequestMapping(value = "/queryBook", method = RequestMethod.GET)
    public ResponseEntity<String> queryBook(
            @ApiParam(value = "电子书ID", required = true) @RequestParam("bookId") String bookId,
            @ApiParam(value = "每页行数", required = true) @RequestParam("pageSize") int pageSize,
            @ApiParam(value = "当前页码", required = true) @RequestParam("pageNum") int pageNum) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("bookid", bookId);
        Book bookinfo = bookService.getBookinfoByCondition(map);
        String content = null;
        if (null != bookinfo) {
            String filePath = bookinfo.getSavepath();
            Integer lineCount = bookinfo.getLinecount();
            content = FileUtil.readForPage(filePath, pageNum, pageSize, lineCount);
        }
        return ResponseEntity.ok().body(content);
    }

}
