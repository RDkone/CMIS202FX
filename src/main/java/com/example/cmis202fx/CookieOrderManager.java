package com.example.cmis202fx;

import java.io.Serializable;
import java.util.*;

public class CookieOrderManager implements Serializable {
    private HashMap<OrderKey, Integer> orders;

    public CookieOrderManager(){
        this.orders = new HashMap<>();
    }
    public int addOrder(String customer, Cookie cookie, int number){

        if (customer != null && number != 0){
            OrderKey order = new OrderKey(customer, cookie);
            orders.put(order, number);
            return number;
        }
        return 0;

    }
    public int changeOrder(OrderKey key, int number){
        if (key != null){
            if (orders.containsKey(key)){
                int newBox = orders.get(key) + number;
                if (newBox <= 0){
                    newBox = 0;
                    orders.remove(key);
                    return newBox;
                }
                else {
                    orders.put(key, newBox);
                }
                return Math.max(0, newBox);
            }
            else {
                return addOrder(key.getCustomerName(), key.getCookie(), number);
            }
        }
        return 0;
    }
    public int changeOrder(String customer, Cookie cookie, int number){
        return changeOrder(new OrderKey(customer, cookie), number);
    }
    public boolean deleteOrder(String customer, Cookie cookie){
        OrderKey key = new OrderKey(customer, cookie);
        boolean get = false;
        Set<Map.Entry<OrderKey, Integer>> entries = orders.entrySet();
        Iterator<Map.Entry<OrderKey, Integer>> itr = entries.iterator();
        while (itr.hasNext()){
            Map.Entry<OrderKey, Integer> entry = itr.next();
            if (entry.getKey().getCustomerName().equalsIgnoreCase(key.getCustomerName())
            && entry.getKey().getCookie().equals(key.getCookie())){
                itr.remove();
                get = true;

            }

        }
        return get;
    }
    public boolean deleteOrders(String customers){
        boolean get = false;
        Set<Map.Entry<OrderKey, Integer>> entries = orders.entrySet();
        Iterator<Map.Entry<OrderKey, Integer>> itr = entries.iterator();
        while (itr.hasNext()){
            Map.Entry<OrderKey, Integer> entry = itr.next();
            if (entry.getKey().getCustomerName().equalsIgnoreCase(customers)){
                itr.remove();
                get = true;
            }
        }

        return get;
    }
    public int getNumberBoxesByType(String cookieName){
        int total = 0;
        try {
            Cookie findingCookie = Cookie.valueOf(cookieName.toUpperCase());
            Set<Map.Entry<OrderKey, Integer>> entries = orders.entrySet();

            for (Map.Entry<OrderKey, Integer> entry : entries){
                if (entry.getKey().getCookie().equals(findingCookie)){
                    total += entry.getValue();
                }
            }
        } catch (IllegalArgumentException e){
            System.out.println(cookieName + ": Invalid cookie name");;
        }

        return total;
    }
    public double getTotalAmountPerCustomer(String customer){
        double total = 0.0;
        Set<Map.Entry<OrderKey, Integer>> entries = orders.entrySet();
        for (Map.Entry<OrderKey, Integer> entry : entries){
            if (entry.getKey().getCustomerName().equalsIgnoreCase(customer)){
                OrderKey ordk = entry.getKey();
                double calculation = ordk.getCookie().getPrice() * entry.getValue();
                total += calculation;
            }
        }
        return total;
    }
    public double getTotalAmountOfOrders(){
        double total = 0.0;
        List<String> customers = getCustomerList();
        for (int i = 0; i < customers.size(); i++) {
            String singleCustomer = customers.get(i);
            total += getTotalAmountPerCustomer(singleCustomer);
        }
        return total;
    }

    public List<String> getCustomerList(){
        List<String> customersInList = new LinkedList<>();
        for (Map.Entry<OrderKey, Integer> entry : orders.entrySet()) {
            if (!customersInList.contains(entry.getKey().getCustomerName()))
            customersInList.add(entry.getKey().getCustomerName());
        }
        Collections.sort(customersInList);
        return customersInList;
    }
    public String displayTotalOrdersPerCustomer(String customer){
        String name = customer.toUpperCase();
        double amountPerC = 0.0;
        String cookieOrderDesc = "";
        Set<Map.Entry<OrderKey, Integer>> entries = orders.entrySet();
        for (Map.Entry<OrderKey, Integer> entry : entries){
            if (entry.getKey().getCustomerName().equalsIgnoreCase(customer)){
                OrderKey ordk = entry.getKey();
                double pricePerBox = ordk.getCookie().getPrice() * entry.getValue();
                amountPerC = getTotalAmountPerCustomer(customer);
                cookieOrderDesc += ordk.getCookie().getCookieName() +
                " (" + entry.getValue() + " boxes" + ")" + " - $" + pricePerBox + "0" + "\n" + "\t";
            }
        }
        return name + "(" + amountPerC + ")" + "\n" +
                "\t" + cookieOrderDesc + "\n";
    }
    public String displayAllOrders(){
        String out = "";
        List<String> customers = getCustomerList();
        for (String customer : customers){
            out += displayTotalOrdersPerCustomer(customer);
        }
        return out;
    }
    public void displayRawOrders(){
        for (Map.Entry<OrderKey, Integer> entry: orders.entrySet()){
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
    private class OrderKey implements Serializable {
        private String customerName;
        private Cookie cookie;

        public OrderKey(String customerName, Cookie cookie) {
            this.customerName = customerName;
            this.cookie = cookie;
        }
        public String getCustomerName() {
            return customerName;
        }
        public Cookie getCookie() {
            return cookie;
        }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getEnclosingInstance().hashCode();     //  This ensures different instances have different hashcodes!!!
            result = prime * result + Objects.hash(customerName, cookie);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            OrderKey other = (OrderKey) obj;
            if (!getEnclosingInstance().equals(other.getEnclosingInstance()))   // Again here making sure that different instances are nor equal!!!
                return false;
            return Objects.equals(customerName, other.customerName) && cookie == other.cookie;
        }
        private CookieOrderManager getEnclosingInstance(){
            return CookieOrderManager.this;
        }
        @Override
        public String toString() {
            return "[" +
                    "customerName='" + customerName + '\'' +
                    ", cookie=" + cookie +
                    ']';
        }
    }
}
