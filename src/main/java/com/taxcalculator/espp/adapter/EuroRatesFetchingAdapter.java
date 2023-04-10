package com.taxcalculator.espp.adapter;

import java.time.LocalDate;

public interface EuroRatesFetchingAdapter {
    double getEurToUsdRate(LocalDate date);
}
