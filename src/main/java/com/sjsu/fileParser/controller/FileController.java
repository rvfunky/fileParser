
package com.sjsu.fileParser.controller;

import com.sjsu.fileParser.controller.Parser;
import com.sjsu.fileParser.controller.ResultId;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import javax.servlet.annotation.MultipartConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@MultipartConfig
public class FileController {
    @RequestMapping(value={"/"}, method={RequestMethod.GET})
    public String showHome() {
        return "home.jsp";
    }

    @RequestMapping(value={"/upload"}, method={RequestMethod.POST})
    public ResponseEntity parse(@RequestParam(value="file") MultipartFile uploadedFile, @RequestParam(value="email") String email) throws FileNotFoundException {
        Object line = null;
        try {
            File file = new File(uploadedFile.getOriginalFilename());
            uploadedFile.transferTo(file);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ResultId obj = new ResultId(email);
            int id = obj.getResultId();
            if (id == -2) {
                return ResponseEntity.ok((Object)null);
            }
            if (id == -1) {
                obj.insert();
                id = obj.getResultId();
            }
            Parser parser = new Parser(bufferedReader, email, String.valueOf(id));
            parser.parse();
            return ResponseEntity.ok((Object)null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}