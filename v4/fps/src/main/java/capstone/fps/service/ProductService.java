package capstone.fps.service;

import capstone.fps.common.Fix;
import capstone.fps.common.Methods;
import capstone.fps.common.Validator;
import capstone.fps.entity.FRProduct;
import capstone.fps.entity.FRStore;
import capstone.fps.model.Response;
import capstone.fps.model.store.MdlProduct;
import capstone.fps.model.store.MdlProductBuilder;
import capstone.fps.repository.ProductRepo;
import capstone.fps.repository.StoreRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public Response<List<MdlProduct>> getBest5() {
        MdlProductBuilder mdlProductBuilder = new MdlProductBuilder();
        Response<List<MdlProduct>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        List<FRProduct> frProducts = productRepository.findAllByStatusOrderByRatingDesc(Fix.PRO_NEW.index);
        int size = Math.min(5, frProducts.size());
        List<MdlProduct> mdlProducts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            mdlProducts.add(mdlProductBuilder.buildBig(frProducts.get(i)));
        }
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlProducts);
        return response;
    }

    public Response<MdlProduct> createProduct(String proName, Integer storeId, Double price, MultipartFile proImg, String description, String note) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();
        MdlProductBuilder mdlProductBuilder = new MdlProductBuilder();
        Response<MdlProduct> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        Optional<FRStore> frStoreOptional = storeRepository.findById(storeId);
        if (!frStoreOptional.isPresent()) {
            response.setResponse(Response.STATUS_FAIL, "Cant find store");
            return response;
        }
        byte[] proImgBytes = null;
        if (proImg != null) {
            try {
                proImgBytes = proImg.getBytes();
            } catch (IOException e) {
                response.setResponse(Response.STATUS_FAIL, "Cant read proImg");
                e.printStackTrace();
                return response;
            }
        }

        FRProduct frProduct = new FRProduct();
        frProduct.setProductName(proName);
        frProduct.setStore(frStoreOptional.get());
        frProduct.setPrice(price);
        frProduct.setProductImage(proImgBytes);
        frProduct.setDescription(valid.nullProof(description));
        frProduct.setRatingCount(0);
        frProduct.setCreateTime(time);
        frProduct.setNote(valid.nullProof(note));
        frProduct.setStatus(Fix.PRO_NEW.index);
        frProduct.setEditor(methods.getUser());
        productRepository.save(frProduct);

        MdlProduct mdlProduct = mdlProductBuilder.buildBig(frProduct);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlProduct);
        return response;
    }


    public Response<MdlProduct> updateProduct(Integer proId, String name, Double price, MultipartFile proImg, String description, String note, Integer status) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();
        MdlProductBuilder mdlProductBuilder = new MdlProductBuilder();
        Response<MdlProduct> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        if (proId == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find pro");
            return response;
        }
        Optional<FRProduct> optional = productRepository.findById(proId);
        if (!optional.isPresent()) {
            response.setResponse(Response.STATUS_FAIL, "Cant find pro");
            return response;
        }
        FRProduct frProduct = optional.get();

        if (!methods.nullOrSpace(name)) {
            frProduct.setProductName(name);
        }
        if (price != null) {
            frProduct.setPrice(price);
        }
        if (proImg != null) {
            try {
                frProduct.setProductImage(proImg.getBytes());
            } catch (IOException e) {
                response.setResponse(Response.STATUS_FAIL, "Cant read proImg");
                e.printStackTrace();
                return response;
            }
        }
        if (description != null) {
            frProduct.setDescription(valid.nullProof(description));
        }
        if (note != null) {
            frProduct.setNote(valid.nullProof(note));
        }
        frProduct.setUpdateTime(time);
        frProduct.setStatus(valid.checkUpdateStatus(frProduct.getStatus(), status, Fix.PRO_STAT_LIST));
        frProduct.setEditor(methods.getUser());
        productRepository.save(frProduct);

        MdlProduct mdlProduct = mdlProductBuilder.buildBig(frProduct);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlProduct);
        return response;
    }

    public List<MdlProduct> getProList(Integer storeId) {
        MdlProductBuilder mdlProductBuilder = new MdlProductBuilder();
        List<MdlProduct> mdlProducts = new ArrayList<>();
        List<FRProduct> frProductList;
        if (storeId != null) {
            Optional<FRStore> optionalFRStore = storeRepository.findById(storeId);
            if (optionalFRStore.isPresent()) {
                frProductList = productRepository.findAllByStore(optionalFRStore.get());
            } else {
                frProductList = productRepository.findAll();
            }
        } else {
            frProductList = productRepository.findAll();
        }

        for (FRProduct frProduct : frProductList) {
            mdlProducts.add(mdlProductBuilder.buildBig(frProduct));
        }
        return mdlProducts;
    }
}
