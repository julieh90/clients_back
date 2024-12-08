package com.alianza.clients.commons.util;


import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class UtilsCustom {
    String pattern = "yyyy-MM-dd-HH.mm.ss";

    public Date formatDateUtil(String convertDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        try {
            return dateFormat.parse(convertDate);


        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
        return null;
    }
}
