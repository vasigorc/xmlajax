/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ajaxandxml.ws;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
/**
 *
 * @author vasigorc
 */
public class LocalTimeAdapter  extends XmlAdapter<String, LocalTime> {
private static final DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm:ss");
    @Override
    public LocalTime unmarshal(String v) throws Exception {        
        return LocalTime.parse(v, timeFormatter);
    }

    @Override
    public String marshal(LocalTime v) throws Exception {
        return v.toString("HH:mm:ss");
    }
}
