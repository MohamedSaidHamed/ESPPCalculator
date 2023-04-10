package com.taxcalculator.espp.adapter;

import com.taxcalculator.espp.exception.InvalidRateException;
import com.taxcalculator.espp.core.CurrencyRate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.Map;

@Service
public class EuroRatesFetchingAdapterImpl implements EuroRatesFetchingAdapter {
    private RestTemplate restTemplate = new RestTemplate();
    @Override
    public double getEurToUsdRate(LocalDate date) {
        URI uri = constructUri(date);
        ResponseEntity<CurrencyRate> result = restTemplate.getForEntity(uri,
                CurrencyRate.class);
        Map<String, Double> eurRates = result.getBody().getEur();
        return eurRates.entrySet().stream().filter(rate -> rate.getKey().equals("usd")).findFirst()
                  .map(Map.Entry::getValue).orElseThrow(()-> new InvalidRateException("Couldn't fetch Eur rates."));
    }

    private URI constructUri(LocalDate date) {
        return UriComponentsBuilder.newInstance()
                .uri(URI.create("https://cdn.jsdelivr.net"))
                .path("gh/fawazahmed0/currency-api@1")
                .pathSegment(String.valueOf(date))
                .pathSegment("currencies/eur.json")
                .build().toUri();
    }
}
