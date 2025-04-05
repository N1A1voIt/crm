package site.easy.to.build.crm.duplicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.service.user.UserService;
import site.easy.to.build.crm.util.ApiResponse;

import java.util.ArrayList;

@RestController
public class DuplicateRestController {
    @Autowired
    DuplicateService duplicateService;
    @Autowired
    UserService userService;

    @PostMapping("/api/specific/duplicate/import")
    public ResponseEntity<?> importData(@RequestParam("file") MultipartFile file) {
        try {
            duplicateService.importJSON(file);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setStatus(200);
            apiResponse.setData("Duplication succedeed");
            apiResponse.setExceptions(null);
            return ResponseEntity.ok().body(apiResponse);
        } catch (Exception e){
            e.printStackTrace();
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setStatus(400);
            apiResponse.setData(null);
            apiResponse.setExceptions(new ArrayList<>(){{add(e);}});
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }
}
