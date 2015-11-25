/**
 * 
 */
package com.myMusic.domains;

/**
 * @author bhanu
 *
 */
public class UserTaste {
	Integer id;
	Integer user_id;
	Double category_1;
	Double category_2;
	Double category_3;
	Double category_4;
	Double category_5;
	Double category_6;
	Double category_7;
	Double category_8;
	Double category_9;
	Double category_10;
	Integer count;

	@Override
	public String toString() {
		return "UserTaste [id=" + id + ", user_id=" + user_id + ", category_1="
				+ category_1 + ", category_2=" + category_2 + ", category_3="
				+ category_3 + ", category_4=" + category_4 + ", category_5="
				+ category_5 + ", category_6=" + category_6 + ", category_7="
				+ category_7 + ", category_8=" + category_8 + ", category_9="
				+ category_9 + ", category_10=" + category_10 + ", count="
				+ count + "]";
	}

	public UserTaste() {
		super();
	}

	public UserTaste(Integer id, Integer user_id, Double category_1,
			Double category_2, Double category_3, Double category_4,
			Double category_5, Double category_6, Double category_7,
			Double category_8, Double category_9, Double category_10,
			Integer count) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.category_1 = category_1;
		this.category_2 = category_2;
		this.category_3 = category_3;
		this.category_4 = category_4;
		this.category_5 = category_5;
		this.category_6 = category_6;
		this.category_7 = category_7;
		this.category_8 = category_8;
		this.category_9 = category_9;
		this.category_10 = category_10;
		this.count = count;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Double getCategory_1() {
		return category_1;
	}

	public void setCategory_1(Double category_1) {
		this.category_1 = category_1;
	}

	public Double getCategory_2() {
		return category_2;
	}

	public void setCategory_2(Double category_2) {
		this.category_2 = category_2;
	}

	public Double getCategory_3() {
		return category_3;
	}

	public void setCategory_3(Double category_3) {
		this.category_3 = category_3;
	}

	public Double getCategory_4() {
		return category_4;
	}

	public void setCategory_4(Double category_4) {
		this.category_4 = category_4;
	}

	public Double getCategory_5() {
		return category_5;
	}

	public void setCategory_5(Double category_5) {
		this.category_5 = category_5;
	}

	public Double getCategory_6() {
		return category_6;
	}

	public void setCategory_6(Double category_6) {
		this.category_6 = category_6;
	}

	public Double getCategory_7() {
		return category_7;
	}

	public void setCategory_7(Double category_7) {
		this.category_7 = category_7;
	}

	public Double getCategory_8() {
		return category_8;
	}

	public void setCategory_8(Double category_8) {
		this.category_8 = category_8;
	}

	public Double getCategory_9() {
		return category_9;
	}

	public void setCategory_9(Double category_9) {
		this.category_9 = category_9;
	}

	public Double getCategory_10() {
		return category_10;
	}

	public void setCategory_10(Double category_10) {
		this.category_10 = category_10;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
