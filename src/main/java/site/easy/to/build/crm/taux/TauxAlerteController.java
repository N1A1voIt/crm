package site.easy.to.build.crm.taux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.easy.to.build.crm.util.ApiResponse;

import java.util.Arrays;
import java.util.List;

@RestController
public class TauxAlerteController {
    @Autowired
    TauxAlerteService tauxAlerteRespository;

    @PutMapping("/api/taux/update")
    public ResponseEntity<ApiResponse> update(@RequestBody TauxAlerte tauxAlerte) {
        try{
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setStatus(200);
            tauxAlerteRespository.save(tauxAlerte);
            apiResponse.setData("taux alerte updated");
            apiResponse.setExceptions(null);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }catch (Exception e){
            List<Exception> exceptions = Arrays.asList(e);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setStatus(500);
            apiResponse.setData("taux alerte échoué");
            apiResponse.setExceptions(exceptions);
            e.printStackTrace();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
