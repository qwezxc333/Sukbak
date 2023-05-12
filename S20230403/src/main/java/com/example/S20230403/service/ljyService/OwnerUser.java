package com.example.S20230403.service.ljyService;

import com.example.S20230403.model.Accom;

import lombok.Data;

@Data
public class OwnerUser {
	
    // getter/setter 생략
    public Accom toAccom() {
        Accom accom = new Accom();
        // String pool = (this.pool == null) ? "N" : this.pool; //디스풀이널 넣음참이면 N, 거짓이면 this.pool넣음
        // accom.setPool((this.pool) ? "Y" : "N");
        accom.setUser_id(this.user_id);
        accom.setBiz_id(this.biz_id);
        accom.setBiz_name(this.biz_name);
        accom.setTel(this.tel);
        accom.setAccom_type(this.accom_type);
        accom.setDescription(this.description);
        accom.setPool(this.getStringBool(this.hasPool));
        accom.setParking(this.getStringBool(this.hasParking)); 
        accom.setCafe(this.getStringBool(this.hasCafe)); 
        accom.setRestaurant(this.getStringBool(this.hasRestaurant)); 
        accom.setStore(this.getStringBool(this.hasStore)); 
        accom.setSauna(this.getStringBool(this.hasSauna)); 
        accom.setLaundry(this.getStringBool(this.hasLaundry)); 
        accom.setFitness(this.getStringBool(this.hasFitness)); 
        accom.setAccom_avail("210");
        accom.setAddr(convertAddr());
        accom.setLatitude(this.latitude);
        accom.setLongitude(this.longitude);
        
        return accom;
    }
    
     
    private String convertAddr() {
    	String addr = this.addr;
        addr = addr.replaceAll("대한민국 ", "");
        addr = addr.replace(",", "");
        return addr;
    }
    
    private String getStringBool(boolean isTrue) {
    	//				true면 Y반환, false면 N반환
    	return isTrue ? "Y" : "N";		     	
    }
	
	private boolean hasPool = false;
	private boolean hasParking = false;
	private boolean hasCafe = false;
	private boolean hasRestaurant = false;
	private boolean hasStore = false;
	private boolean hasSauna = false;
	private boolean hasLaundry = false;
	private boolean hasFitness = false;
	
	private String biz_id;
	private String user_id;
	private String accom_avail;
	private String accom_type;
	private String biz_name;
	private String addr;
	private String tel;
	private int    room_count;
	private String description;
	private String latitude;
	private String longitude;
	
}
