package com.taxcalculator.espp.manager;

import com.taxcalculator.espp.core.EsppTax;

public interface EsppService {


    EsppTax getTaxOnEspp(double esppContribution, double esppStartPrice,
                         double esppEndPrice, String esppEndDate);
}
