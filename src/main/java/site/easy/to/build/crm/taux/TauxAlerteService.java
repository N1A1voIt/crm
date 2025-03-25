package site.easy.to.build.crm.taux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TauxAlerteService {
    @Autowired
    TauxAlerteRespository tauxAlerteRespository;

    public TauxAlerte save(TauxAlerte tauxAlerte) {
        List<TauxAlerte> list = tauxAlerteRespository.findAll();
        if (!tauxAlerteRespository.findAll().isEmpty()) {
            TauxAlerte tauxAlerte1 = list.get(0);
            tauxAlerte1.setTaux(tauxAlerte.getTaux());
            tauxAlerte1 = tauxAlerteRespository.save(tauxAlerte1);
            return tauxAlerte1;
        } else {
            tauxAlerteRespository.save(tauxAlerte);
            return tauxAlerte;
        }
    }
    public double getTaux(){
        try{
            return tauxAlerteRespository.findAll().get(0).getTaux().doubleValue() / 100;

        }catch (Exception e){
            return 1;
        }
    }
}
