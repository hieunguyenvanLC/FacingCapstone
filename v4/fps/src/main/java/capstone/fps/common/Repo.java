package capstone.fps.common;

import capstone.fps.entity.*;
import capstone.fps.repository.*;

import java.util.Optional;

public class Repo {

    public FRDistrict getDIst(Integer distId, DistrictRepo districtRepository) {
        if (distId == null) {
            return null;
        }
        Optional<FRDistrict> optional = districtRepository.findById(distId);
        return optional.orElse(null);
    }

    public FRStore getStore(Integer storeId, StoreRepo storeRepository) {
        if (storeId == null) {
            return null;
        }
        Optional<FRStore> optional = storeRepository.findById(storeId);
        return optional.orElse(null);
    }

    public FROrder getOrder(Integer orderId, OrderRepo orderRepository) {
        if (orderId == null) {
            return null;
        }
        Optional<FROrder> optional = orderRepository.findById(orderId);
        return optional.orElse(null);
    }

    public FRAccount getAccount(Integer accId, AccountRepo accountRepository) {
        if (accId == null) {
            return null;
        }
        Optional<FRAccount> optional = accountRepository.findById(accId);
        return optional.orElse(null);
    }

    public FRAccount getAccountByPhone(String phone, AccountRepo accountRepository) {
        Methods methods = new Methods();
        if (methods.nullOrSpace(phone)) {
            return null;
        }
        Optional<FRAccount> optional = accountRepository.findByPhone(phone);
        return optional.orElse(null);
    }

    public FRSource getSource(Integer srcId, SourceRepo sourceRepository) {
        if (srcId == null) {
            return null;
        }
        Optional<FRSource> optional = sourceRepository.findById(srcId);
        return optional.orElse(null);
    }

    public FRPriceLevel getPriceLevel(Integer priceLevelId, PriceLevelRepo priceLevelRepository) {
        if (priceLevelId == null) {
            return null;
        }
        Optional<FRPriceLevel> optional = priceLevelRepository.findById(priceLevelId);
        return optional.orElse(null);
    }


    public FRProduct getProduct(Integer proId, ProductRepo productRepository) {
        if (proId == null) {
            return null;
        }
        Optional<FRProduct> optional = productRepository.findById(proId);
        return optional.orElse(null);
    }
}
