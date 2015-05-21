/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ajaxandxml.ws;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.joda.time.Period;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;
/**
 *
 * @author vasigorc
 */
public class DurationAdapter extends XmlAdapter<String, Period> {
    private static final PeriodFormatter parser = ISOPeriodFormat.standard();

    @Override
    public Period unmarshal(String v) throws Exception {
        return parser.parsePeriod(v);
    }

    @Override
    public String marshal(Period v) throws Exception {
        return v.getHours()+" hour "+v.getMinutes()+" minutes and "+v.getSeconds()+" seconds";
    }
    
}
