package com.example.mobile;

import com.example.mobile.model.customer;
import com.example.mobile.model.discount;
import com.example.mobile.model.district;
import com.example.mobile.model.province;
import com.example.mobile.model.receivedOrder;
import com.example.mobile.model.ward;
import java.util.List;

public class currentUser {
    public static customer currentCustomer;
    public static discount appliedDiscount;
    public static receivedOrder order;
    public static List<province> provinceList;
    public static List<district> districtList;
    public static List<ward> wardList;

}
