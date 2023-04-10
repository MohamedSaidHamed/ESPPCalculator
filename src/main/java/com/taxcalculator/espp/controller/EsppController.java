package com.taxcalculator.espp.controller;

import com.taxcalculator.espp.core.EsppTax;
import com.taxcalculator.espp.manager.EsppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("espp/")
public class EsppController {
    @Autowired
    EsppService esppService;

    @CrossOrigin(origins = "*")
    @PostMapping(value = "tax")
    public EsppTax getEsppTax(@RequestParam("contribution") double esppContribution,
                                              @RequestParam("startPrice") double esppStartPrice,
                                              @RequestParam("endPrice") double esppEndPrice,
                                              @RequestParam("endDate") String esppEndDate){
       return esppService.getTaxOnEspp(esppContribution,esppStartPrice,esppEndPrice,esppEndDate);
    }
}
