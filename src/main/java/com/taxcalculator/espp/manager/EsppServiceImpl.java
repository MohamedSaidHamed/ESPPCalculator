package com.taxcalculator.espp.manager;

import com.taxcalculator.espp.adapter.EuroRatesFetchingAdapter;
import com.taxcalculator.espp.exception.DateFormatException;
import com.taxcalculator.espp.core.EsppTax;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class EsppServiceImpl implements EsppService {

    @Autowired
    EuroRatesFetchingAdapter euroRatesFetchingAdapter;
    @Override
    public EsppTax getTaxOnEspp(double esppContribution, double esppStartPrice,
                                double esppEndPrice, String esppEndDate) {

        LocalDate esppDateFormatted = formatDate(esppEndDate);
        double eurToUsdRate = euroRatesFetchingAdapter.getEurToUsdRate(esppDateFormatted);

        double purchasePrice = calculatePurchasePrice(esppStartPrice, esppEndPrice);
        double purchaseQuantity = calculatePurchaseQuantity(purchasePrice, esppContribution);
        double purchaseValue = calculatePurchaseValue(purchasePrice, purchaseQuantity);
        double marketValue = calculateMarketValue(esppEndPrice, purchaseQuantity);
        double esppGain = calculateEsppGain(marketValue, purchaseValue);
        double esppTaxableAmount = calculateFreibetragTaxableAmount(esppGain, eurToUsdRate);
        return EsppTax.builder()
                .taxes(Precision.round(calculateEsppTaxRate(esppTaxableAmount),2))
                .taxableAmount(Precision.round(esppTaxableAmount,2))
                .build();
    }

    private double calculatePurchasePrice(double startEsppPrice, double endEsppPrice){
        return startEsppPrice < endEsppPrice? startEsppPrice*0.85: endEsppPrice*0.85;
    }

    private double calculatePurchaseQuantity(double purchasePrice, double contribution){
        return Math.floor(contribution/purchasePrice);
    }

    private double calculatePurchaseValue(double purchasePrice, double purchaseQuantity){
        return purchasePrice*purchaseQuantity;
    }

    private double calculateMarketValue(double endEsppPrice, double purchaseQuantity){
        return endEsppPrice*purchaseQuantity;
    }

    private double calculateEsppGain(double marketValue, double purchaseValue){
        return marketValue - purchaseValue;
    }

    private  double calculateFreibetragTaxableAmount(double esppGain, double eurToUsdRate){
        double esppGainInEur = esppGain * eurToUsdRate;
        double freibetrag = 1440.0;
        return esppGainInEur - freibetrag <0? 0: esppGain-freibetrag;
    }

    private double calculateEsppTaxRate(double taxableAmount){
        //value in EUR
        return taxableAmount * (33.0f/100.0f);
    }
    private LocalDate formatDate(String date){
        try {
            return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        }catch (DateTimeParseException ex){
            throw new DateFormatException("Invalid date format");
        }
    }
}
