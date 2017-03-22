package com.fuli_center;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.fuli_center.bean.Contact;
import com.fuli_center.bean.BoutiqueBean;
import com.fuli_center.bean.CartBean;
import com.fuli_center.bean.CategoryChildBean;
import com.fuli_center.bean.CategoryParentBean;
import com.fuli_center.bean.CollectBean;
import com.fuli_center.bean.ColorBean;
import com.fuli_center.bean.GoodDetailsBean;
import com.fuli_center.bean.Message;
import com.fuli_center.bean.MessageBean;
import com.fuli_center.bean.NewGoodBean;
import com.fuli_center.bean.User;
import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.model.Charge;

/**
 * Servlet implementation class Server
 */
@WebServlet("/Server")
public class Server extends HttpServlet {
	private static final long serialVersionUID = 1L;

	FuLiCenterBiz biz;
	
	static final String ISON8859_1 = "iso8859-1";
	static final String UTF_8 = "utf-8";
	
	static final String ROOT_PATH = "E:/EclipseWorkSpace/FuliCenterSqlite/FuLiCenter-data/";
	//"/Users/clawpo/work/ucai/work/projects/FuLiCenter/20160317/FuLiCenter-data/";
	///Users/clawpo/work/ucai/work/projects/FuLiCenter/source/FuLiCenter-data
//	"d:/chensaitao/fulishe/FuLiCenter-data/";
//	D:\chensaitao\fulishe\FuLiCenter-data
	static final String AVATAR_PATH = ROOT_PATH+"user_avatar/";
	static final String GOODS_IMG_PATH = ROOT_PATH+"images/";
	
	static final String FILE_NAME="file_name";
	
	private static final String KEY_EXTRAS = "extras";
	private static final String KEY_AMOUNT = "amount";
	private static final String KEY_CURRENCY = "currency";
	private static final String KEY_SUBJECT = "subject";
	private static final String KEY_BODY = "body";
	private static final String KEY_ORDER_NO = "order_no";
	private static final String KEY_CHANNEL = "channel";
	private static final String KEY_CLIENT_IP = "client_ip";
	private static final String KEY_APP_ID = "id";
	private static final String KEY_APP = "app";
	
	private static final String VALUE_RMB = "cny";
	
	
	private static String KEY_ALBUM_IMG_URL = "img_url";
	
	static final String KEY_REQUEST = "request";

	/**
	 * pingpp API key
	 */
	private static String API_KEY = "sk_test_GG8yrPPy94WH8eHGmTSCajfH";
	/**
	 * pingpp app id
	 */
	private static String APP_ID = "app_v1SSeTir5W58Oyvb";
	
	/**注册*/
	static final String REQUEST_REGISTER = "register";
	/**
	 * 发送取消注册的请求
	 */
	static final String REQUEST_UNREGISTER="unregister";
	
	/**上传头像*/
	static final String REQUEST_UPLOAD_AVATAR = "upload_avatar";
	/**登陆*/
	static final String REQUEST_LOGIN = "login";

	/**下载头像*/
	static final String REQUEST_DOWNLOAD_AVATAR = "download_avatar";

	static final String REQUEST_DOWNLOAD_CONTACTS = "download_contacts";

	static final String REQUEST_DOWNLOAD_CONTACTLIST = "download_contactlist";

	/** 添加联系人 */
	static final String REQUEST_ADD_CONTACT = "add_contact";

	/** 查找联系人 */
	static final String REQUEST_FIND_USER = "find_user";

	static final String REQUEST_DELETE_CONTACT = "delete_contact";

	/**下载商品属性颜色的图片*/
	static final String REQUEST_DOWNLOAD_COLOR_IMG = "download_color_img";
	
	private static final String REQUEST_FIND_CHARGE = "find_charge";
	/**
	 * ping++支付返回结果的实体
	 * */
	static Charge chargeResult;
	
	/** 从服务端查询精选首页的数据*/
	private static final String REQUEST_FIND_BOUTIQUES="find_boutiques";
	
	/** 从服务端查询新品或精选商品信息*/
	private static final String REQUEST_FIND_NEW_BOUTIQUE_GOODS="find_new_boutique_goods";

	/** 从服务端下载tb_category_parent表的数据*/
	private static final String REQUEST_FIND_CATEGORY_GROUP="find_category_group";
	
	/** 从服务端下载tb_category_child表的数据*/
	private static final String REQUEST_FIND_CATEGORY_CHILDREN="find_category_children";
	
	/** 从服务端下载tb_category_good表的数据*/
	private static final String REQUEST_FIND_GOOD_DETAILS="find_good_details";
	
	/** 从服务端下载一组商品详情的数据*/
	private static final String REQUEST_FIND_GOODS_DETAILS="find_goods_details";
    
	/** 下载指定小类别的颜色列表*/
	private static final String REQUEST_FIND_COLOR_LIST="find_color_list";
	
	/** 查询是否已收藏*/
	private static final String REQUEST_IS_COLLECT="is_collect";
    /** 添加收藏*/
	private static final String REQUEST_ADD_COLLECT="add_collect";
    /** 删除收藏*/
	private static final String REQUEST_DELETE_COLLECT="delete_collect";
    /** 下载收藏的商品信息*/
	private static final String REQUEST_FIND_COLLECTS="find_collects";
    /** 下载收藏的商品数量信息*/
	private static final String REQUEST_FIND_COLLECT_COUNT="find_collect_count";
	
	/** 删除收藏*/
	private static final String REQUEST_DELETE_WARDROBE="delete_wardrobe";
	
	private static final String REQUEST_ADD_CART="add_cart";
	
	private static final String REQUEST_FIND_CARTS="find_carts";

	private static final String REQUEST_DELETE_CART="delete_cart";
	
	private static final String REQUEST_UPDATE_CART="update_cart";
	
	/**下载新品首页商品图片*/
	private static final String REQUEST_DOWNLOAD_NEW_GOOD = "download_new_good";
    
	/** 下载商品相册图像的请求*/
	private static final String REQUEST_DOWNLOAD_ALBUM_IMG="download_album_img_url";
	
	private static final String REQUEST_DOWNLOAD_BOUTIQUE_IMG="download_boutique_img";
	
	private static final String REQUEST_DOWNLOAD_CATEGORY_GROUP_IMAGE="download_category_group_image";
	
	private static final String REQUEST_DOWNLOAD_CATEGORY_CHILD_IMAGE="download_category_child_image";
	
	private static final String REQUEST_UPLOAD_NICK="upload_nick";
	
	private static final String REQUEST_PAY="pay";
	
	 /** 下载收藏商品图像的请求*/
	private static final String REQUEST_DOWNLOAD_GOODS_THUMB="download_goods_thumb";
    
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Server() {
		super();
		biz=new FuLiCenterBiz();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String requestType = request.getParameter(KEY_REQUEST);
		response.setContentType("text/html;charset=utf-8");
		if (requestType == null) {
			return;
		}
		System.out.println("Server.doGet.requestType="+requestType);
		switch (requestType) {
		case REQUEST_FIND_BOUTIQUES:
			findBoutiques(request,response);
			break;
		case REQUEST_FIND_NEW_BOUTIQUE_GOODS:
			findNewAndBoutiqueGoods(request,response);
			break;
		case REQUEST_FIND_CATEGORY_GROUP:
			findCategoryGroup(request,response);
			break;
		case REQUEST_FIND_CATEGORY_CHILDREN:
			findCategoryChildren(request,response);
			break;
		case REQUEST_FIND_GOOD_DETAILS:
			findGoodDetails(request,response);
			break;
		case REQUEST_FIND_COLOR_LIST:
			findColorList(request,response);
			break;
		case REQUEST_ADD_COLLECT:
			addCollect(request,response);
			break;
		case REQUEST_DELETE_WARDROBE:
			deleteCollect(request,response);
			break;
		case REQUEST_FIND_COLLECTS:
			findCollects(request,response);
			break;
		case REQUEST_FIND_COLLECT_COUNT:
			findCollectCount(request,response);
			break;
		case REQUEST_ADD_CART:
			addCart(request,response);
			break;
		case REQUEST_FIND_CARTS:
			findCarts(request,response);
			break;
		case REQUEST_DELETE_CART:
			deleteCart(request,response);
			break;
		case REQUEST_UPDATE_CART:
			updateCart(request,response);
			break;
		case REQUEST_DOWNLOAD_NEW_GOOD:
			downloadNewGood(request,response);
			break;
		case REQUEST_DOWNLOAD_ALBUM_IMG:
			downloadAlbumImg(request,response);
			break;
		case REQUEST_DOWNLOAD_COLOR_IMG:
			downloadColorImg(request,response);
			break;
		case REQUEST_IS_COLLECT:
			isCollect(request,response);
			break;
		case REQUEST_DELETE_COLLECT:
			deleteCollect(request,response);
			break;
		case REQUEST_DOWNLOAD_BOUTIQUE_IMG:
			downloadBoutiqueImg(request,response);
			break;
		case REQUEST_DOWNLOAD_CATEGORY_GROUP_IMAGE:
			downloadCategoryGroupImage(request,response);
			break;
		case REQUEST_DOWNLOAD_CATEGORY_CHILD_IMAGE:
			downloadCategoryChildImage(request,response);
			break;
		case REQUEST_FIND_GOODS_DETAILS:
			findGoodDetailsList(request,response);
			break;
		case REQUEST_DOWNLOAD_GOODS_THUMB:
			downloadGoodsThumb(request,response);
			break;
		case REQUEST_FIND_CHARGE:
			findCharge(request,response);
			break;
		case I.REQUEST_UNREGISTER:
			unRegister(request,response);
			break;
		case I.REQUEST_LOGIN:
			login(response, request);
			break;
		case I.REQUEST_DOWNLOAD_AVATAR:
			downloadAvatar(request, response);
			break;
		case I.REQUEST_DOWNLOAD_CONTACT_LIST:
			downloadContactList(request, response);
			break;
		case I.REQUEST_DOWNLOAD_CONTACT_ALL_LIST:
			downloadContactAllList(request, response);
			break;
		case I.REQUEST_ADD_CONTACT:
			addContact(request, response);
			break;
		case I.REQUEST_DELETE_CONTACT:
			deleteContact(request, response);
			break;
		case I.REQUEST_FIND_USER:
			findUserByUserName(request, response);
			break;
		case I.REQUEST_FIND_USERS:
			findUsersByUserName(request, response);
			break;
		case I.REQUEST_FIND_USERS_BY_NICK:
			findUsersByNick(request, response);
			break;
		case I.REQUEST_FIND_USERS_FOR_SEARCH:
			findUsersForSearch(request, response);
			break;
		case I.REQUEST_UPDATE_USER_NICK:
			updateUserNickByName(request,response);
			break;
		case I.REQUEST_UPDATE_USER_PASSWORD:
			updateUserPassowrdByName(request,response);
			break;
		}
	}
	
	private void findCharge(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), chargeResult);
			String text = om.writeValueAsString(chargeResult);
			HashMap<String, Object> charge = om.readValue(text, HashMap.class);
			om.writeValue(response.getOutputStream(), charge);
		}  catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void downloadGoodsThumb(HttpServletRequest request,
			HttpServletResponse response) {
		String goodsThumb=request.getParameter(IFuLiCenterBiz.Collect.GOODS_THUMB);
		downloadImg(response, goodsThumb);
	}

	/**
	 * 下载一组商品详情的数据
	 * @param request
	 * @param response
	 */
	private void findGoodDetailsList(HttpServletRequest request,
			HttpServletResponse response) {
		int catId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.GoodDetails.CAT_ID));
		int pageId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_ID));
		int pageSize=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_SIZE));
		ArrayList<GoodDetailsBean> goodDetailsList = biz.findGoodsDetails(catId, pageId, pageSize);
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), goodDetailsList);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void downloadCategoryChildImage(HttpServletRequest request,
			HttpServletResponse response) {
		String fileName=request.getParameter(IFuLiCenterBiz.CategoryChild.IMAGE_URL);
		downloadImg(response, fileName);
	}

	private void downloadCategoryGroupImage(HttpServletRequest request,
			HttpServletResponse response) {
		String fileName=request.getParameter(IFuLiCenterBiz.CategoryParent.IMAGE_URL);
		downloadImg(response, fileName);
	}

	/**
	 * 下载精选首页各列表项图片
	 * @param request
	 * @param response
	 */
	private void downloadBoutiqueImg(HttpServletRequest request,
			HttpServletResponse response) {
		String imgUrl=request.getParameter(IFuLiCenterBiz.Boutique.IMAGE_URL);
		downloadImg(response, imgUrl);
	}

	/**
	 * 下载指定地址的图片
	 * @param response
	 * @param imgUrl
	 */
	private void downloadImg(HttpServletResponse response, String imgUrl) {
		File file=new File(GOODS_IMG_PATH, imgUrl);
		FileInputStream fis=null;
		try {
			fis=new FileInputStream(file);
			response.setContentType("image/jpeg");
			int len;
			byte[] buffer=new byte[8*1024];
			while((len=fis.read(buffer))!=-1){
				response.getOutputStream().write(buffer, 0, len);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 查询指定商品是否已由指定用户收藏
	 * @param request
	 * @param response
	 */
	private void isCollect(HttpServletRequest request,
			HttpServletResponse response) {
		String userName=request.getParameter(IFuLiCenterBiz.Collect.USER_NAME);
		int goodsId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.Collect.GOODS_ID));
		boolean isCollect = biz.isCollect(userName, goodsId);
		MessageBean msg=new MessageBean(isCollect, "");
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), msg);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 下载商品颜色图片
	 * @param request
	 * @param response
	 */
	private void downloadColorImg(HttpServletRequest request,
			HttpServletResponse response) {
		String imgUrl=request.getParameter(IFuLiCenterBiz.Color.COLOR_IMG);
		downloadImg(response, imgUrl);
	}

	/**
	 * 下载商品相册的图片
	 * @param request
	 * @param response
	 */
	private void downloadAlbumImg(HttpServletRequest request,
			HttpServletResponse response) {
		String imgUrl=request.getParameter(KEY_ALBUM_IMG_URL);
		downloadImg(response, imgUrl);
	}

	/**
	 * 下载新品首页商品图片
	 * @param request
	 * @param response
	 */
	private void downloadNewGood(HttpServletRequest request,
			HttpServletResponse response) {
		String fileName=request.getParameter(FILE_NAME);
		downloadImg(response, fileName);
	}

	private void updateCart(HttpServletRequest request,
			HttpServletResponse response) {
		int cartId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.Cart.ID));
		int count=Integer.parseInt(request.getParameter(IFuLiCenterBiz.Cart.COUNT));
		boolean isChecked=Boolean.parseBoolean(request.getParameter(IFuLiCenterBiz.Cart.IS_CHECKED));
		boolean isSuccess = biz.updateCart(cartId, count, isChecked);
		ObjectMapper om=new ObjectMapper();
		MessageBean msg=null;
		if(isSuccess){
			msg=new MessageBean(true, "修改购物车中的商品成功");
		}else{
			msg=new MessageBean(false, "修改购物车中的商品失败");
		}
		try {
			om.writeValue(response.getOutputStream(), msg);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteCart(HttpServletRequest request,
			HttpServletResponse response) {
		int id=Integer.parseInt(request.getParameter(IFuLiCenterBiz.Cart.ID));
		boolean isSuccess = biz.deleteCart(id);
		ObjectMapper om=new ObjectMapper();
		MessageBean msg=null;
		if(isSuccess){
			msg=new MessageBean(true, "删除购物车中的商品成功");
		}else{
			msg=new MessageBean(false, "删除购物车中的商品失败");
		}
		try {
			om.writeValue(response.getOutputStream(), msg);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findCarts(HttpServletRequest request,
			HttpServletResponse response) {
		String userName=request.getParameter(IFuLiCenterBiz.Cart.USER_NAME);
		int pageId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_ID));
		int pageSize=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_SIZE));
		CartBean[] carts = biz.findCarts(userName, pageId, pageSize);
		System.out.println(Arrays.toString(carts));
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), carts);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addCart(HttpServletRequest request, HttpServletResponse response) {
		int goodsId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.Cart.GOODS_ID));
		String userName=request.getParameter(IFuLiCenterBiz.Cart.USER_NAME);
		int count=Integer.parseInt(request.getParameter(IFuLiCenterBiz.Cart.COUNT));
		boolean isChecked=Boolean.parseBoolean(request.getParameter(IFuLiCenterBiz.Cart.IS_CHECKED));
		CartBean cart=new CartBean(0, userName, goodsId, count, isChecked);
		int id = biz.addCart(cart);
		ObjectMapper om=new ObjectMapper();
		MessageBean msg=null;
		if(id>0){
			msg=new MessageBean(true, ""+id);
		}else{
			msg=new MessageBean(false, "添加失败");
		}
		try {
			om.writeValue(response.getOutputStream(), msg);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findCollects(HttpServletRequest request,
			HttpServletResponse response) {
		String userName=request.getParameter(IFuLiCenterBiz.Collect.USER_NAME);
		int pageId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_ID));
		int pageSize=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_SIZE));
		CollectBean[] collects = biz.findCollects(userName, pageId, pageSize);
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), collects);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void findCollectCount(HttpServletRequest request,
			HttpServletResponse response){
		String userName=request.getParameter(IFuLiCenterBiz.Collect.USER_NAME);
		int count = biz.findCollectCount(userName);
		ObjectMapper om=new ObjectMapper();
		try {
			MessageBean msg=null;
			if(count>0){
				msg=new MessageBean(true, ""+count);
			}else{
				msg=new MessageBean(false, "查询失败");
			}
			om.writeValue(response.getOutputStream(), msg);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteCollect(HttpServletRequest request,
			HttpServletResponse response) {
		int goodsId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.Collect.GOODS_ID));
		String userName=request.getParameter(IFuLiCenterBiz.Collect.USER_NAME);
		boolean isSuccess = biz.deleteCollect(userName, goodsId);
		ObjectMapper om=new ObjectMapper();
		MessageBean msg=null;
		if(isSuccess){
			msg=new MessageBean(true, "删除收藏成功");
		}else{
			msg=new MessageBean(false, "删除收藏失败");
		}
		try {
			om.writeValue(response.getOutputStream(), msg);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addCollect(HttpServletRequest request,
			HttpServletResponse response) {
		int goodsId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.Collect.GOODS_ID));
		String userName=request.getParameter(IFuLiCenterBiz.Collect.USER_NAME);
		String goodsName=request.getParameter(IFuLiCenterBiz.Collect.GOODS_NAME);
		try {
			goodsName=new String(goodsName.getBytes(ISON8859_1),UTF_8);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String goodsEnglishName=request.getParameter(IFuLiCenterBiz.Collect.GOODS_ENGLISH_NAME);
		String goodsThumb=request.getParameter(IFuLiCenterBiz.Collect.GOODS_THUMB);
		String goodsImg=request.getParameter(IFuLiCenterBiz.Collect.GOODS_IMG);
		long addTime=Long.parseLong(request.getParameter(IFuLiCenterBiz.Collect.ADD_TIME));
		CollectBean collect=new CollectBean(userName, goodsId, goodsName, goodsEnglishName, goodsThumb, goodsImg, addTime);
		int id = biz.addCollect(collect);
		MessageBean msg=null;
		if(id>0){
			msg=new MessageBean(true, "收藏成功");
		}else{
			msg=new MessageBean(false, "收藏失败");
		}
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), msg);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findColorList(HttpServletRequest request,
			HttpServletResponse response) {
		int catId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.Color.CAT_ID));
		ColorBean[] colors = biz.findColorsByCatId(catId);
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), colors);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findGoodDetails(HttpServletRequest request,
			HttpServletResponse response) {
		int goodsId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.GoodDetails.GOODS_ID));
		GoodDetailsBean good = biz.findGoodDetails(goodsId);
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), good);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findCategoryChildren(HttpServletRequest request,
			HttpServletResponse response) {
		int parentId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.CategoryChild.PARENT_ID));
		int pageId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_ID));
		int pageSize=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_SIZE));
		CategoryChildBean[] children = biz.findCategoryChildren(parentId, pageId, pageSize);
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), children);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findCategoryGroup(HttpServletRequest request,
			HttpServletResponse response) {
		CategoryParentBean[] parents = biz.findCategoryParent();
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), parents);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findNewAndBoutiqueGoods(HttpServletRequest request,
			HttpServletResponse response) {
		int catId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.NewAndBoutiqueGood.CAT_ID));
		int pageId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_ID));
		int pageSize=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_SIZE));
		System.out.println("findNewAndBoutiqueGoods.....catId="+catId+",pageId="+pageId+",pageSize="+pageSize);
		NewGoodBean[] goods = biz.findNewAndBoutiqueGoods(catId, pageId, pageSize);
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), goods);
			for (NewGoodBean good : goods) {
				System.out.println(good.getGoodsEnglishName());
			}
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findBoutiques(HttpServletRequest request,
			HttpServletResponse response) {
		BoutiqueBean[] boutiques = biz.findBoutiques();
		ObjectMapper om=new ObjectMapper();
		try {
			if(boutiques!=null){
				om.writeValue(response.getOutputStream(), boutiques);
				System.out.println("精选首页数据下载完成");
			}else{
				System.out.println("精选首页数据读取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// 获取客户端上传的请求类型
		String requestType = request.getParameter(KEY_REQUEST);
		switch (requestType) {
		case REQUEST_PAY:
			doPay(request, response);
			break;
		case I.REQUEST_REGISTER:
			register(request, response);
			break;
		case I.REQUEST_UPLOAD_AVATAR:
			uploadAvatarByNameOrHXID(request,response);
			break;
		}
	}

	private void doPay(HttpServletRequest request, HttpServletResponse response) {
		try {
			//读取客户端上传的post请求中的参数
			BufferedReader reader=request.getReader();
			StringBuffer sb=new StringBuffer();
			String line;
			while((line=reader.readLine())!=null){
				sb.append(line);
			}
			ObjectMapper om=new ObjectMapper();
			HashMap<String,Object> map = om.readValue(sb.toString(),HashMap.class);
			//获取商品数量
			int amount=Integer.parseInt(map.get(KEY_AMOUNT).toString());
			//获取支付通道信息
			String channel=map.get(KEY_CHANNEL).toString();
			//获取支付的日期
			String orderNo=map.get(KEY_ORDER_NO).toString();
			
			//获取附加数据
			HashMap<String, Object> extras=(HashMap<String, Object>) map.get(KEY_EXTRAS);
			
			HashMap<String, Object> chargeMap=new HashMap<String, Object>();
			chargeMap.put(KEY_AMOUNT, amount);
			chargeMap.put(KEY_CHANNEL, channel);
			chargeMap.put(KEY_ORDER_NO, orderNo);
			chargeMap.put(KEY_CURRENCY, VALUE_RMB);
			String clientIp=getIpAddr(request);
			chargeMap.put(KEY_CLIENT_IP, clientIp);
			chargeMap.put(KEY_SUBJECT, extras.get(KEY_SUBJECT));
			chargeMap.put(KEY_BODY, extras.get(KEY_BODY));
			
			Pingpp.apiKey=API_KEY;
			HashMap<String, Object> appMap=new HashMap<String, Object>();
			appMap.put(KEY_APP_ID, APP_ID);
			chargeMap.put(KEY_APP, appMap);
			
			chargeResult=Charge.create(chargeMap);
			System.out.println(chargeResult.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取客户端ip地址
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 根据用户账号更新用户密码
	 * @param request
	 * @param response
	 */
	private void updateUserPassowrdByName(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		String newPassword = request.getParameter(I.User.PASSWORD);
		User user = biz.updateUserPasswordByUserName(userName, newPassword);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), user);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 根据用户账号更新用户昵称
	 * @param request
	 * @param response
	 */
	private void updateUserNickByName(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		String newNick = request.getParameter(I.User.NICK);
		try {
			newNick = new String(newNick.getBytes(I.ISON8859_1), I.UTF_8);
			User user = biz.updateUserNickByUserName(userName, newNick);
			ObjectMapper om = new ObjectMapper();
			om.writeValue(response.getOutputStream(), user);
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 根据用户账号查找用户
	 * @param request
	 * @param response
	 */
	private void findUserByUserName(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		User user = biz.findUserByUserName(userName);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), user);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据账号查找用户集合
	 * @param request
	 * @param response
	 */
	private void findUsersByUserName(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		int pageId = Integer.parseInt(request.getParameter(I.PAGE_ID));
		int pageSize = Integer.parseInt(request.getParameter(I.PAGE_SIZE));
		User[] user = biz.findUsersByUserName(userName,pageId,pageSize);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), user);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 根据昵称查找用户集合
	 * @param request
	 * @param response
	 */
	private void findUsersByNick(HttpServletRequest request,
			HttpServletResponse response) {
		String nick = request.getParameter(I.User.NICK);
		int pageId = Integer.parseInt(request.getParameter(I.PAGE_ID));
		int pageSize = Integer.parseInt(request.getParameter(I.PAGE_SIZE));
		User[] user = biz.findUsersByNick(nick,pageId,pageSize);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), user);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据昵称或者用户名查找用户集合
	 * @param request
	 * @param response
	 */
	private void findUsersForSearch(HttpServletRequest request,
			HttpServletResponse response) {
		String nick = request.getParameter(I.User.NICK);
		String username = request.getParameter(I.User.USER_NAME);
		int pageId = Integer.parseInt(request.getParameter(I.PAGE_ID));
		int pageSize = Integer.parseInt(request.getParameter(I.PAGE_SIZE));
		User[] user = biz.findUsersForSearch(nick,username,pageId,pageSize);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), user);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除好友
	 * @param request
	 * @param response
	 */
	private void deleteContact(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.Contact.USER_NAME);
		String name = request.getParameter(I.Contact.CU_NAME);
		boolean isSuccess = biz.deleteContact(userName, name);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), isSuccess);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 添加好友
	 * @param request
	 * @param response
	 */
	private void addContact(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.Contact.USER_NAME);
		String name = request.getParameter(I.Contact.CU_NAME);
		Contact contact = biz.addContact(userName, name);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), contact);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 下载好友集合
	 * @param request
	 * @param response
	 */
	private void downloadContactAllList(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.Contact.USER_NAME);
		Contact[] contacts = biz.findContactsByUserName(userName);
		ObjectMapper om = new ObjectMapper();
		 try {
			 om.writeValue(response.getOutputStream(), contacts);
		 }catch(IOException e){
			 e.printStackTrace();
		 }
	}
	
	/**
	 * 下载好友集合
	 * @param request
	 * @param response
	 */
	private void downloadContactList(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.Contact.USER_NAME);
		int pageId = Integer.parseInt(request.getParameter(I.PAGE_ID));
		int pageSize = Integer.parseInt(request.getParameter(I.PAGE_SIZE));
		Contact[] contacts = biz.findContactsByUserName(userName, pageId, pageSize);
		ObjectMapper om = new ObjectMapper();
		 try {
			 om.writeValue(response.getOutputStream(), contacts);
		 }catch(IOException e){
			 e.printStackTrace();
		 }
	}
	/**
	 * 下载用户头像
	 * @param request
	 * @param response
	 */
	private void downloadAvatar(HttpServletRequest request, HttpServletResponse response) {
		File file = null;
		String avatar = request.getParameter(I.AVATAR_TYPE);
		file = new File(AVATAR_PATH// + I.AVATAR_TYPE_USER_PATH 
				+ I.BACKSLASH + avatar + I.AVATAR_SUFFIX_JPG);
		System.out.println("file.path="+file.getPath());
		downloadAvatar(file, response);
	}
	
	/**
	 * 下载头像图片
	 * @param file
	 * @param response
	 */
	private void downloadAvatar(File file, HttpServletResponse response){
		if (!file.exists()) {
			System.out.println("头像不存在");
			return;
		}
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			ServletOutputStream out = response.getOutputStream();
			int len;
			byte[] buffer = new byte[1024];
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			System.out.println("头像下载完毕");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(in);
		}
	}

	/**
	 * 登陆
	 * @param response
	 * @param request
	 */
	private void login(HttpServletResponse response, HttpServletRequest request) {
		ObjectMapper om = new ObjectMapper();
		String userName = request.getParameter(I.User.USER_NAME);
		String password = request.getParameter(I.User.PASSWORD);
		User user = biz.login(userName, password);
		try {
			om.writeValue(response.getOutputStream(), user);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 取消注册，删除账号和图片
	 * @param request
	 * @param response
	 */
	private void unRegister(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectMapper om = new ObjectMapper();
		String userName = request.getParameter(I.User.USER_NAME);
		boolean isSuccess = biz.unRegister(userName);
		try {
			if(isSuccess){
				om.writeValue(response.getOutputStream(), new Message(false, I.MSG_UNREGISTER_SUCCESS));
			} else {
				om.writeValue(response.getOutputStream(), new Message(false, I.MSG_UNREGISTER_FAIL));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传头像
	 * @param request
	 * @param response
	 */
	private void uploadAvatarByNameOrHXID(HttpServletRequest request, HttpServletResponse response) {
		ObjectMapper om = new ObjectMapper();
		boolean isSuccess = false;
		String type = request.getParameter(I.AVATAR_TYPE);
		if(type.equals(I.AVATAR_TYPE_USER_PATH)) {
			String userName = request.getParameter(I.User.USER_NAME);
			isSuccess = uploadAvatar(userName, I.AVATAR_TYPE_USER, request);
		} else {
			String hxid = request.getParameter(I.Group.HX_ID);
			isSuccess = uploadAvatar(hxid, I.AVATAR_TYPE_GROUP, request);
		}
		try {
			if(isSuccess){
				om.writeValue(response.getOutputStream(), new Message(true, I.MSG_UPLOAD_AVATAR_SUCCESS));
			} else {
				om.writeValue(response.getOutputStream(), new Message(false, I.MSG_UPLOAD_AVATAR_FAIL));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 注册用户
	 * @param request
	 * @param response
	 */
	private void register(HttpServletRequest request, HttpServletResponse response) {
		ObjectMapper om = new ObjectMapper();
		boolean isSuccess = false;
		// 步骤1-从request中获取userName、nick、password
		String userName = request.getParameter(I.User.USER_NAME);
		// 步骤2-验证用户名是否已经存在
		try {
			if (biz.findUserByUserName(userName) != null) {
				om.writeValue(response.getOutputStream(), new Message(false, I.MSG_REGISTER_USERNAME_EXISTS));
			} else {
				// 步骤3-上传头像图片
				isSuccess = uploadAvatar(userName, I.AVATAR_TYPE_USER, request);
				if(isSuccess) {
					String nick = request.getParameter(I.User.NICK);
					// 解决乱码问题
					nick = new String(nick.getBytes(I.ISON8859_1), I.UTF_8);
					String password = request.getParameter(I.User.PASSWORD);
					// 步骤4-将三个数据封装在一个UserBean对象中
					User user = new User(I.ID_DEFAULT,userName, password, nick,I.UN_READ_MSG_COUNT_DEFAULT);
					// 步骤5-调用业务逻辑层的方法进行注册
					int id = biz.register(user);
					System.out.println("register user,id="+id);
					// 步骤6-调用业务逻辑层的方法上传头像数据
					if(id>0){
						isSuccess = biz.updateAvatar(id, userName, I.AVATAR_TYPE_USER);
						// 步骤7-将isSuccess发送给客户端
						if(isSuccess){
							om.writeValue(response.getOutputStream(), new Message(true, I.MSG_REGISTER_SUCCESS));
						} else {
							om.writeValue(response.getOutputStream(), new Message(false, I.MSG_UPLOAD_AVATAR_FAIL));
						}
					}else{
						om.writeValue(response.getOutputStream(), new Message(false, I.MSG_REGISTER_FAIL));
					}
				} else {
					om.writeValue(response.getOutputStream(), new Message(false, I.MSG_UPLOAD_AVATAR_FAIL));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 
	/**
	 * 上传头像
	 * @param name
	 * @param type
	 * @param request
	 * @return
	 */
	private boolean uploadAvatar(String name, int type, HttpServletRequest request) {
		String path;
		switch (type) {
		case I.AVATAR_TYPE_USER:
			path = ROOT_PATH + I.AVATAR_TYPE_USER_PATH + I.BACKSLASH;
			break;
		case I.AVATAR_TYPE_GROUP:
		default:
			path = I.AVATAR_PATH + I.AVATAR_TYPE_GROUP_PATH + I.BACKSLASH;
			break;
		}
		String fileName = name + I.AVATAR_SUFFIX_JPG;
		System.out.println("头像上传路径:" + path + fileName);
		File file = new File(path,fileName);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024 * 8];
			int len;
			while ((len = request.getInputStream().read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally{
			closeStream(fos);
		}
		return true;
	}
	
	/**
	 * 关闭文件输出流
	 * @param fos
	 */
	private void closeStream(FileOutputStream fos) {
		try {
			if(fos!=null){
				fos.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭文件输入流
	 * @param fis
	 */
	private void closeStream(FileInputStream fis) {
		try {
			if(fis!=null){
				fis.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
