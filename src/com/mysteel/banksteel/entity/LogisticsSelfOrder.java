package com.mysteel.banksteel.entity;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * @author:wushaoge
 * @date£º2015Äê8ÔÂ14ÈÕ16:59:05
 */
public class LogisticsSelfOrder implements Serializable {
		
	private String startAddress;
	private String endAddress;
	private String foodType;
	private String foodNum;
	private String load;
	private String unload;
	private String price;
	private String discountPrice;
	private String taxPrice0;
	private String taxPrice11;
	private String taxPrice6;


	public String getTaxPrice0() {
		if (TextUtils.isEmpty(taxPrice0)) {
			return "";
		}
		return taxPrice0;
	}

	public void setTaxPrice0(String taxPrice0) {
		this.taxPrice0 = taxPrice0;
	}

	public String getTaxPrice11() {
		if (TextUtils.isEmpty(taxPrice11)) {
			return "";
		}
		return taxPrice11;
	}

	public void setTaxPrice11(String taxPrice11) {
		this.taxPrice11 = taxPrice11;
	}

	public String getTaxPrice6() {
		if (TextUtils.isEmpty(taxPrice6)) {
			return "";
		}
		return taxPrice6;
	}

	public void setTaxPrice6(String taxPrice6) {
		this.taxPrice6 = taxPrice6;
	}

	public String getStartAddress() {
		if (TextUtils.isEmpty(startAddress)) {
			return "";
		}
		return startAddress;
	}
	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}
	public String getEndAddress() {
		if (TextUtils.isEmpty(endAddress)) {
			return "";
		}
		return endAddress;
	}
	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}
	public String getFoodType() {
		if (TextUtils.isEmpty(foodType)) {
			return "";
		}
		return foodType;
	}
	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}
	public String getFoodNum() {
		if (TextUtils.isEmpty(foodNum)) {
			return "";
		}
		return foodNum;
	}
	public void setFoodNum(String foodNum) {
		this.foodNum = foodNum;
	}
	public String getLoad() {
		if (TextUtils.isEmpty(load)) {
			return "";
		}
		return load;
	}
	public void setLoad(String load) {
		this.load = load;
	}
	public String getUnload() {
		if (TextUtils.isEmpty(unload)) {
			return "";
		}
		return unload;
	}
	public void setUnload(String unload) {
		this.unload = unload;
	}
	public String getPrice() {
		if (TextUtils.isEmpty(price)) {
			return "";
		}
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDiscountPrice() {
		if (TextUtils.isEmpty(discountPrice)) {
			return "";
		}
		return discountPrice;
	}
	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}
	
}
