package com.mysteel.banksteel.entity;

import android.text.TextUtils;

/**
 * 物流报价查询
 * @author:wushaoge
 * @date：2015年8月15日14:38:17
 */
public class TransportQuotData extends BaseData{
	private Data data;
	
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	
	public class Data{
		private String resultCode;
		private MsgData resultMessage;
		private String timpstamp;
		
		public String getResultCode() {
			if (TextUtils.isEmpty(resultCode)) {
				return "";
			}
			return resultCode;
		}
		public void setResultCode(String resultCode) {
			this.resultCode = resultCode;
		}
		public MsgData getResultMessage() {
			return resultMessage;
		}
		public void setResultMessage(MsgData resultMessage) {
			this.resultMessage = resultMessage;
		}
		public String getTimpstamp() {
			if (TextUtils.isEmpty(timpstamp)) {
				return "";
			}
			return timpstamp;
		}
		public void setTimpstamp(String timpstamp) {
			this.timpstamp = timpstamp;
		}
		public class MsgData{
			private String extend1;
			private String extend2;
			private String extend3;
			private String extend4;
			private String extend5;
			private String favorablePrice;
			private String priceAdd1;
			private String priceAdd2;
			private String priceAdd3;
			private String priceFlag;
			private String quotePrice;
			private String seqId;
			private String taxPrice0;
			private String taxPrice11;
			private String taxPrice6;
			public String getExtend1() {
				if (TextUtils.isEmpty(extend1)) {
					return "";
				}
				return extend1;
			}
			public void setExtend1(String extend1) {
				this.extend1 = extend1;
			}
			public String getExtend2() {
				if (TextUtils.isEmpty(extend2)) {
					return "";
				}
				return extend2;
			}
			public void setExtend2(String extend2) {
				this.extend2 = extend2;
			}
			public String getExtend3() {
				if (TextUtils.isEmpty(extend3)) {
					return "";
				}
				return extend3;
			}
			public void setExtend3(String extend3) {
				this.extend3 = extend3;
			}
			public String getExtend4() {
				if (TextUtils.isEmpty(extend4)) {
					return "";
				}
				return extend4;
			}
			public void setExtend4(String extend4) {
				this.extend4 = extend4;
			}
			public String getExtend5() {
				if (TextUtils.isEmpty(extend5)) {
					return "";
				}
				return extend5;
			}
			public void setExtend5(String extend5) {
				this.extend5 = extend5;
			}
			public String getFavorablePrice() {
				if (TextUtils.isEmpty(favorablePrice)) {
					return "";
				}
				return favorablePrice;
			}
			public void setFavorablePrice(String favorablePrice) {
				this.favorablePrice = favorablePrice;
			}
			public String getPriceAdd1() {
				if (TextUtils.isEmpty(priceAdd1)) {
					return "";
				}
				return priceAdd1;
			}
			public void setPriceAdd1(String priceAdd1) {
				this.priceAdd1 = priceAdd1;
			}
			public String getPriceAdd2() {
				if (TextUtils.isEmpty(priceAdd2)) {
					return "";
				}
				return priceAdd2;
			}
			public void setPriceAdd2(String priceAdd2) {
				this.priceAdd2 = priceAdd2;
			}
			public String getPriceAdd3() {
				if (TextUtils.isEmpty(priceAdd3)) {
					return "";
				}
				return priceAdd3;
			}
			public void setPriceAdd3(String priceAdd3) {
				this.priceAdd3 = priceAdd3;
			}
			public String getPriceFlag() {
				if (TextUtils.isEmpty(priceFlag)) {
					return "";
				}
				return priceFlag;
			}
			public void setPriceFlag(String priceFlag) {
				this.priceFlag = priceFlag;
			}
			public String getQuotePrice() {
				if (TextUtils.isEmpty(quotePrice)) {
					return "";
				}
				return quotePrice;
			}
			public void setQuotePrice(String quotePrice) {
				this.quotePrice = quotePrice;
			}
			public String getSeqId() {
				if (TextUtils.isEmpty(seqId)) {
					return "";
				}
				return seqId;
			}
			public void setSeqId(String seqId) {
				this.seqId = seqId;
			}
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
		}
		
	}



	
}
