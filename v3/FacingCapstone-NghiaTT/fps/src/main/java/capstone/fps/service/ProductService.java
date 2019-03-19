package capstone.fps.service;

import capstone.fps.common.Fix;
import capstone.fps.common.Methods;
import capstone.fps.common.Validator;
import capstone.fps.entity.FRProduct;
import capstone.fps.entity.FRStore;
import capstone.fps.model.product.MdlMemProBest;
import capstone.fps.repository.ProductRepo;
import capstone.fps.repository.StoreRepo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepo productRepository;
    private StoreRepo storeRepository;

    public ProductService(ProductRepo productRepository, StoreRepo storeRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    public List<MdlMemProBest> getAll() {
        List<FRProduct> frProducts = productRepository.findAll();
        int size =  frProducts.size();
        List<MdlMemProBest> mdlProBests = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            MdlMemProBest mdlPro = new MdlMemProBest();
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

    public List<MdlMemProBest> getBest5() {
        List<FRProduct> frProducts = productRepository.findAllByStatusOrderByRatingDesc(Fix.PRO_NEW.index);
        int size = Math.min(5, frProducts.size());
        List<MdlMemProBest> mdlProBests = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            MdlMemProBest mdlPro = new MdlMemProBest();
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

    // To do: set schedule
    public boolean createProduct(String proName, Integer storeId, Double price, byte[] proImg, String description, String note) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();

        Optional<FRStore> frStoreOptional = storeRepository.findById(storeId);
        if (!frStoreOptional.isPresent()) {
            return false;
        }

        FRProduct frProduct = new FRProduct();
        frProduct.setProductName(proName);
        frProduct.setStore(frStoreOptional.get());
        frProduct.setPrice(price);
        frProduct.setProductImage(proImg);
        frProduct.setDescription(description);
        frProduct.setRatingCount(0);
        frProduct.setCreateTime(time);
        frProduct.setNote(note);
        frProduct.setStatus(Fix.PRO_NEW.index);
        frProduct.setEditor(methods.getUser());
        productRepository.save(frProduct);
        return true;
    }

    public boolean deactivateProduct(Integer id, String note) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Optional<FRProduct> optional = productRepository.findById(id);
        if (!optional.isPresent()) {
            return false;
        }
        FRProduct frProduct = optional.get();
        frProduct.setDeleteTime(time);
        if (!methods.nullOrSpace(note)) {
            frProduct.setNote(note);
        }
        frProduct.setStatus(Fix.PRO_HID.index);
        frProduct.setEditor(methods.getUser());
        productRepository.save(frProduct);
        return true;
    }

    public boolean updateProduct(Integer proId, String name, Double price, byte[] proImg, String description, String note) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();

        Optional<FRProduct> optional = productRepository.findById(proId);
        if (!optional.isPresent()) {
            return false;
        }
        FRProduct frProduct = optional.get();
        if (!methods.nullOrSpace(name)) {
            frProduct.setProductName(name);
        }
        if (price != null) {
            frProduct.setPrice(price);
        }
        if (proImg != null) {
            frProduct.setProductImage(proImg);
        }
        if (!methods.nullOrSpace(description)) {
            frProduct.setDescription(description);
        }
        frProduct.setUpdateTime(time);
        if (!methods.nullOrSpace(note)) {
            frProduct.setNote(note);
        }
        frProduct.setEditor(methods.getUser());
        productRepository.save(frProduct);
        return true;
    }

}