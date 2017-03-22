package com.fuli_center.test;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.fuli_center.bean.BoutiqueBean;
import com.fuli_center.bean.CategoryChildBean;
import com.fuli_center.bean.GoodDetailsBean;
import com.fuli_center.bean.CategoryParentBean;
import com.fuli_center.bean.ColorBean;
import com.fuli_center.bean.NewGoodBean;
import com.fuli_center.dao.FuLiCenterDao;
import com.fuli_center.dao.IFuLiCenterDao;

public class FuLiCenterDaoTest{
	IFuLiCenterDao dao=new FuLiCenterDao();
	
	@Test
	public void findNewGoods() throws Exception{
//		NewGoodBean[] newGoods = dao.findNewAndBoutiqueGoods(0, 20);
//		for (NewGoodBean good : newGoods) {
//			System.out.println(good);
//		}
//		printJson(newGoods[0]);
//		printJson(newGoods[1]);
	}

	private <T> void printJson(T t) throws IOException,
			JsonGenerationException, JsonMappingException {
		ObjectMapper om=new ObjectMapper();
		String strGood = om.writeValueAsString(t);
		System.out.println(strGood);
	}
	
	@Test
	public void findBoutiques() throws Exception{
		BoutiqueBean[] boutiques = dao.findBoutiques();
		for (BoutiqueBean boutique : boutiques) {
			System.out.println(boutique);
		}
		printJson(boutiques[0]);
		printJson(boutiques[1]);
	}
	
	@Test
	public void findBoutiueGoods() throws Exception{
		NewGoodBean[] boutiqueGoods = dao.findNewAndBoutiqueGoods(259, 0, 20);
		for (NewGoodBean good : boutiqueGoods) {
			System.out.println(good);
		}
		printJson(boutiqueGoods[0]);
		printJson(boutiqueGoods[1]);
	}
	
	@Test
	public void findCategoryParent() throws Exception{
		CategoryParentBean[] parents = dao.findCategoryParent();
		for (CategoryParentBean category : parents) {
			System.out.println(category);
		}
		printJson(parents[0]);
		printJson(parents[1]);
	}
	
	@Test
	public void findCategoryChildren() throws Exception{
		CategoryChildBean[] children = dao.findCategoryChildren(344, 0, 20);
		for (CategoryChildBean child : children) {
			System.out.println(child);
		}
		printJson(children[0]);
		printJson(children[1]);
	}
	
	@Test
	public void findCategoryGood() throws Exception{
		GoodDetailsBean good = dao.findGoodDetails(6372);
		System.out.println(good);
		printJson(good);
	}
	
	@Test
	public void findColorsByCatid() throws Exception{
		ColorBean[] colors = dao.findColorsByCatId(262);
		printJson(colors[0]);
		printJson(colors[1]);
	}
}
