import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoadService {
    @Autowired
    private LoadRepository loadRepository;

    public Load createLoad(Load load) {
        return loadRepository.save(load);
    }

    public List<Load> getAllLoads() {
        return loadRepository.findAll();
    }

    public Optional<Load> getLoadById(String loadId) {
        return loadRepository.findById(loadId);
    }

    public Load updateLoad(String loadId, Load loadDetails) {
        Load load = loadRepository.findById(loadId)
                .orElseThrow(() -> new RuntimeException("Load not found with id: " + loadId));
        load.setShipperId(loadDetails.getShipperId());
        load.setFacility(loadDetails.getFacility());
        load.setProductType(loadDetails.getProductType());
        load.setTruckType(loadDetails.getTruckType());
        load.setNoOfTrucks(loadDetails.getNoOfTrucks());
        load.setWeight(loadDetails.getWeight());
        load.setComment(loadDetails.getComment());
        return loadRepository.save(load);
    }

    public void deleteLoad(String loadId) {
        loadRepository.deleteById(loadId);
    }
}