package capstone.fps.service;

import capstone.fps.common.ConstantList;
import capstone.fps.entity.FRCity;
import capstone.fps.entity.FRProduct;
import capstone.fps.entity.FRStore;
import capstone.fps.model.MdlProBest;
import capstone.fps.repository.ProductRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<MdlProBest> getBest5() {
        List<FRProduct> frProducts = productRepository.findAllByStatusOrderByRatingDesc(ConstantList.PRO_NEW);
        int size = Math.min(5, frProducts.size());
        List<MdlProBest> mdlProBests = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            MdlProBest mdlPro = new MdlProBest();
            FRProduct frPro = frProducts.get(i);
            mdlPro.setId(frPro.getId());
            mdlPro.setName(frPro.getProductName());
            mdlPro.setPrice(frPro.getPrice());
            mdlPro.setImage(Base64.encodeBase64String(frPro.getProductImage()));
            FRStore frStore = frPro.getStore();
            String address = (frStore.getAddress() + " " + frStore.getDistrict().getName()).trim();
            mdlPro.setAddress(address);
            mdlProBests.add(mdlPro);
        }
        return mdlProBests;
    }
}
