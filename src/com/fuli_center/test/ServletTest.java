package com.fuli_center.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;

import com.fuli_center.bean.BoutiqueBean;
import com.fuli_center.bean.CartBean;
import com.fuli_center.bean.CategoryChildBean;
import com.fuli_center.bean.CategoryParentBean;
import com.fuli_center.bean.CollectBean;
import com.fuli_center.bean.ColorBean;
import com.fuli_center.bean.GoodDetailsBean;
import com.fuli_center.bean.MessageBean;
import com.fuli_center.bean.NewGoodBean;
import com.fuli_center.biz.IFuLiCenterBiz;
import com.fuli_center.utils.HttpUtils;

public class ServletTest {
	
	static final String KEY_REQUEST="request";
	static final String PAGE_ID="page_id";
	static final String PAGE_SIZE="page_size";
	
	static final String SERVER_ROOT="http://127.0.0.1:8080/FuLiCenterServer/Server";
	static final String REQUEST_FIND_NEW_GOODS="find_new_goods";
	static final String REQUEST_FIND_BOUTIQUES="find_boutiques";
	static final String REQUEST_FIND_BOUTIQUE_GOODS="find_boutique_goods";
	static final String REQUEST_FIND_CATEGORY_PARENTS="find_category_parents";
	static final String REQUEST_FIND_CATEGORY_CHILDREN="find_category_children";
	static final String REQUEST_FIND_CATEGORY_GOOD="find_category_good";
	static final String REQUEST_FIND_COLOR_LIST="find_color_list";
	static final String REQUEST_ADD_WARDROBE="add_wardrobe";
	static final String REQUEST_DELETE_WARDROBE="delete_wardrobe";
	static final String REQUEST_FIND_WARDROBES="find_wardrobes";
	static final String REQUEST_ADD_CART="add_cart";
	static final String REQUEST_FIND_CARTS="find_carts";
	static final String REQUEST_DELETE_CART="delete_cart";
	static final String REQUEST_UPDATE_CART="update_cart";
	public static void main(String[] args) {
		System.out.println("1-下载新品数据");
		System.out.println("2-下载精选首页数据");
		System.out.println("3-下载某类精选商品的数据");
		System.out.println("4-下载分类首页数据");
		System.out.println("5-下载分类二级页面数据");
		System.out.println("6-下载分类中指定商品的数据");
		System.out.println("7-下载颜色列表的数据");
		System.out.println("8-添加收藏");
		System.out.println("9-删除收藏");
		System.out.println("10-下载收藏");
		System.out.println("11-添加商品至购物车");
		System.out.println("12-查询用户的购物车");
		System.out.println("13-删除购物车中的商品");
		System.out.println("14-修改购物车中的商品的选中件数");
		
		int select=new Scanner(System.in).nextInt();
		switch (select) {
		case 1://下载新品数据
			findNewGoods();
			break;
		case 2://下载精选首页数据
			findBoutique();
		case 3://下载某类精选商品的数据
			findBoutiqueGoods();
			break;
		case 4:
			findCategoryParents();
			break;
		case 5:
			findCategoryChildren();
			break;
		case 6:
			findCategoryGood();
			break;
		case 7://下载颜色列表的数据
			findColorList();
			break;
		case 8://添加收藏
			addWardrobe();
			break;
		case 9://删除收藏
			deleteWardrobe();
			break;
		case 10://下载收藏
			findWardrobes();
			break;
		case 11://添加商品至购物车
			addCart();
			break;
		case 12://查询用户的购物车
			findCarts();
			break;
		case 13://删除购物车中的商品
			deleteCart();
			break;
		case 14://修改购物车中的商品的选中件数
			updateCart();
			break;
		}
	}
	private static void updateCart() {
		ArrayList<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(KEY_REQUEST, REQUEST_UPDATE_CART));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.Cart.ID, "3"));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.Cart.COUNT, "10"));
		try {
			InputStream in = HttpUtils.getInputStream(SERVER_ROOT, params, HttpUtils.METHOD_GET_NO_DELAY);
			ObjectMapper om=new ObjectMapper();
			MessageBean msg = om.readValue(in, MessageBean.class);
			System.out.println(msg.getMsg());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void findWardrobes() {
		ArrayList<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(KEY_REQUEST, REQUEST_FIND_WARDROBES));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.Cart.USER_NAME, "18910319196"));
		params.add(new BasicNameValuePair(PAGE_ID, "0"));
		params.add(new BasicNameValuePair(PAGE_SIZE, "20"));
		try {
			InputStream in = HttpUtils.getInputStream(SERVER_ROOT, params, HttpUtils.METHOD_GET_NO_DELAY);
			ObjectMapper om=new ObjectMapper();
			CollectBean[] wardrobes = om.readValue(in, CollectBean[].class);
			for (CollectBean w : wardrobes) {
				System.out.println(w);
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void deleteWardrobe() {
		ArrayList<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(KEY_REQUEST, REQUEST_DELETE_WARDROBE));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.Cart.ID, "2"));
		try {
			InputStream in = HttpUtils.getInputStream(SERVER_ROOT, params, HttpUtils.METHOD_GET_NO_DELAY);
			ObjectMapper om=new ObjectMapper();
			MessageBean msg = om.readValue(in, MessageBean.class);
			System.out.println(msg.getMsg());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void addWardrobe() {
		ArrayList<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(KEY_REQUEST, REQUEST_ADD_WARDROBE));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.Collect.ADD_TIME, "1442419200000"));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.Collect.GOODS_NAME, "Murad"));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.Collect.GOODS_ENGLISH_NAME, "Murad"));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.Collect.GOODS_ID,"6372"));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.Collect.GOODS_IMG,"http://121.197.1.20/images/201507/1437108490034171398.jpg"));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.Collect.GOODS_THUMB,"http://121.197.1.20/images/201507/thumb_img/6372_thumb_G_1437108490316.jpg"));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.Collect.USER_NAME,"18910319196"));
		try {
			InputStream in = HttpUtils.getInputStream(SERVER_ROOT, params, HttpUtils.METHOD_GET_NO_DELAY);
			ObjectMapper om=new ObjectMapper();
			MessageBean msg = om.readValue(in, MessageBean.class);
			System.out.println(msg);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void findCarts() {
		ArrayList<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(KEY_REQUEST, REQUEST_FIND_CARTS));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.Cart.USER_NAME, "18813065586"));
		params.add(new BasicNameValuePair(PAGE_ID, "0"));
		params.add(new BasicNameValuePair(PAGE_SIZE, "20"));
		try {
			InputStream in = HttpUtils.getInputStream(SERVER_ROOT, params, HttpUtils.METHOD_GET_NO_DELAY);
			ObjectMapper om=new ObjectMapper();
			CartBean[] carts = om.readValue(in, CartBean[].class);
			for (CartBean cart : carts) {
				System.out.println(cart);
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void deleteCart() {
		ArrayList<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(KEY_REQUEST, REQUEST_DELETE_CART));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.Cart.ID, "1"));
		try {
			InputStream in = HttpUtils.getInputStream(SERVER_ROOT, params, HttpUtils.METHOD_GET_NO_DELAY);
			ObjectMapper om=new ObjectMapper();
			MessageBean msg = om.readValue(in, MessageBean.class);
			System.out.println(msg.getMsg());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * String userName,--购物者的账号
  goodsId int,  --good表的主键
  count int --购物车中该商品的件数
	 */
	private static void addCart() {
		ArrayList<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(KEY_REQUEST, REQUEST_ADD_CART));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.Cart.USER_NAME, "18813065586"));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.Cart.GOODS_ID, "6372"));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.Cart.COUNT, "1"));
		try {
			InputStream in = HttpUtils.getInputStream(SERVER_ROOT, params, HttpUtils.METHOD_GET_NO_DELAY);
			ObjectMapper om=new ObjectMapper();
			MessageBean msg = om.readValue(in, MessageBean.class);
			if(msg.isSuccess()){
				System.out.println("添加商品在购物车中的id:"+msg.getMsg());
			}else{
				System.out.println(msg.getMsg());
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void findColorList() {
		ArrayList<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(KEY_REQUEST, REQUEST_FIND_COLOR_LIST));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.Color.CAT_ID, "262"));
		try {
			InputStream in = HttpUtils.getInputStream(SERVER_ROOT, params, HttpUtils.METHOD_GET_NO_DELAY);
			ObjectMapper om=new ObjectMapper();
			ColorBean[] colors = om.readValue(in, ColorBean[].class);
			System.out.println(colors.length);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void findCategoryGood() {
		ArrayList<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(KEY_REQUEST, REQUEST_FIND_CATEGORY_GOOD));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.GoodDetails.GOODS_ID, "6372"));
		try {
			InputStream in = HttpUtils.getInputStream(SERVER_ROOT, params, HttpUtils.METHOD_GET);
			ObjectMapper om=new ObjectMapper();
			GoodDetailsBean good = om.readValue(in, GoodDetailsBean.class);
			System.out.println(good);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void findCategoryChildren() {
		ArrayList<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(KEY_REQUEST, REQUEST_FIND_CATEGORY_CHILDREN));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.CategoryChild.PARENT_ID, "344"));
		params.add(new BasicNameValuePair(PAGE_ID, "0"));
		params.add(new BasicNameValuePair(PAGE_SIZE, "20"));
		try {
			InputStream in = HttpUtils.getInputStream(SERVER_ROOT, params, HttpUtils.METHOD_GET);
			ObjectMapper om=new ObjectMapper();
			CategoryChildBean[] children = om.readValue(in, CategoryChildBean[].class);
			for (CategoryChildBean child : children) {
				System.out.println(child);
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void findCategoryParents() {
		ArrayList<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(KEY_REQUEST, REQUEST_FIND_CATEGORY_PARENTS));
		try {
			InputStream in = HttpUtils.getInputStream(SERVER_ROOT, params, HttpUtils.METHOD_GET);
			ObjectMapper om=new ObjectMapper();
			CategoryParentBean[] parents = om.readValue(in, CategoryParentBean[].class);
			for (CategoryParentBean good : parents) {
				System.out.println(good);
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void findBoutiqueGoods() {
		ArrayList<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(KEY_REQUEST, REQUEST_FIND_BOUTIQUE_GOODS));
		params.add(new BasicNameValuePair(IFuLiCenterBiz.NewAndBoutiqueGood.CAT_ID, "259"));
		params.add(new BasicNameValuePair(PAGE_ID, "0"));
		params.add(new BasicNameValuePair(PAGE_SIZE, "20"));
		try {
			InputStream in = HttpUtils.getInputStream(SERVER_ROOT, params, HttpUtils.METHOD_GET);
			ObjectMapper om=new ObjectMapper();
			NewGoodBean[] goods = om.readValue(in, NewGoodBean[].class);
			for (NewGoodBean good : goods) {
				System.out.println(good);
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**下载精选首页数据*/
	private static void findBoutique() {
		ArrayList<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(KEY_REQUEST, REQUEST_FIND_BOUTIQUES));
		try {
			InputStream in = HttpUtils.getInputStream(SERVER_ROOT, params, HttpUtils.METHOD_GET);
			ObjectMapper om=new ObjectMapper();
			BoutiqueBean[] boutiques = om.readValue(in, BoutiqueBean[].class);
			for (BoutiqueBean good : boutiques) {
				System.out.println(good);
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void findNewGoods() {
		ArrayList<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(KEY_REQUEST, REQUEST_FIND_NEW_GOODS));
		params.add(new BasicNameValuePair(PAGE_ID, "0"));
		params.add(new BasicNameValuePair(PAGE_SIZE, "20"));
		try {
			InputStream in = HttpUtils.getInputStream(SERVER_ROOT, params, HttpUtils.METHOD_GET);
			ObjectMapper om=new ObjectMapper();
			NewGoodBean[] goods = om.readValue(in, NewGoodBean[].class);
			for (NewGoodBean good : goods) {
				System.out.println(good);
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
