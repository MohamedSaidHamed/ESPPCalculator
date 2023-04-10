package com.taxcalculator.espp.core;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EsppTax implements Serializable {
    double taxableAmount;
    double taxes;
    @Builder.Default
    String currency = "EUR";
}
