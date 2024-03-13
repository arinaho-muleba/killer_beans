package com.killerbeans.server.services;

import com.killerbeans.server.models.Bean;
import com.killerbeans.server.models.Price;
import com.killerbeans.server.repositories.BeanRepository;
import com.killerbeans.server.repositories.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price savePrice(Price price) {
        return priceRepository.save(price);
    }

    public List<Price> getAllPrices() {
        return priceRepository.findAll();
    }

    public Optional<Price> getPriceById(Long id) {
        return priceRepository.findById(id);
    }

    public void deletePriceById(Long id) {
        priceRepository.deleteById(id);
    }

    public List<Price> getAllPricesByBeanId(Long beanId) {
        return priceRepository.findByBeanId(beanId).stream().filter((price) -> price.getEndDate() == null).collect(Collectors.toList());
    }

}
