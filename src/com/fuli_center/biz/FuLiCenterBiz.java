package com.fuli_center.biz;

import java.io.File;
import java.util.ArrayList;

import com.fuli_center.I;
import com.fuli_center.bean.BoutiqueBean;
import com.fuli_center.bean.CartBean;
import com.fuli_center.bean.CategoryChildBean;
import com.fuli_center.bean.Contact;
import com.fuli_center.bean.GoodDetailsBean;
import com.fuli_center.bean.CategoryParentBean;
import com.fuli_center.bean.ColorBean;
import com.fuli_center.bean.NewGoodBean;
import com.fuli_center.bean.User;
import com.fuli_center.bean.CollectBean;
import com.fuli_center.dao.FuLiCenterDao;
import com.fuli_center.dao.IFuLiCenterDao;

public class FuLiCenterBiz implements IFuLiCenterBiz{

	IFuLiCenterDao dao;//������ݷ��ʲ�����ñ���
	
	/**
	 * ҵ���߼���Ĺ�����
	 */
	public FuLiCenterBiz() {
		dao=new FuLiCenterDao();
	}

	@Override
	public int addCart(CartBean cart) {
		return dao.addCart(cart);
	}

	@Override
	public boolean deleteCart(int cartId) {
		return dao.deleteCart(cartId);
	}

	@Override
	public boolean updateCart(int cartId,int count) {
		return dao.updateCart(cartId,count);
	}
	
	@Override
	public boolean updateCart(int cartId,int count,boolean isChecked) {
		return dao.updateCart(cartId,count,isChecked);
	}
	
	@Override
	public CartBean[] findCarts(String userName,int pageId, int pageSize) {
		return dao.findCarts(userName,pageId, pageSize);
	}
	
	@Override
	public NewGoodBean[] findNewAndBoutiqueGoods(int catId,int pageId, int pageSize) {
		return dao.findNewAndBoutiqueGoods(catId,pageId, pageSize);
	}

	@Override
	public CategoryParentBean[] findCategoryParent() {
		return dao.findCategoryParent();
	}

	@Override
	public CategoryChildBean[] findCategoryChildren(int parentId, int pageId,
			int pageSize) {
		return dao.findCategoryChildren(parentId, pageId, pageSize);
	}

	@Override
	public GoodDetailsBean findGoodDetails(int goodsId) {
		return dao.findGoodDetails(goodsId);
	}

	@Override
	public ColorBean[] findColorsByCatId(int catId) {
		return dao.findColorsByCatId(catId);
	}

	@Override
	public int addCollect(CollectBean collect) {
		
		return dao.addCollect(collect);
	}

	@Override
	public boolean  deleteCollect(String userName,int id) {
		return dao.deleteCollect(userName,id);
	}
	
	@Override
	public CollectBean[] findCollects(String userName, int pageId, int pageSize) {
		return dao.findCollects(userName, pageId, pageSize);
	}
	

	@Override
	public int findCollectCount(String userName) {
		return dao.findCollectCount(userName);
	}
	
	@Override
	public boolean isCollect(String userName, int goodsId) {
		return dao.isCollect(userName, goodsId);
	}

	@Override
	public BoutiqueBean[] findBoutiques() {
		return dao.findBoutiques();
	}
	
	@Override
	public ArrayList<GoodDetailsBean> findGoodsDetails(int catId, int pageId, int pageSize) {
		return dao.findGoodsDetails(catId, pageId, pageSize);
	}
	
	@Override
	public int register(User user) throws Exception {
		int id = dao.addUser(user);
		return id;
	}

	@Override
	public boolean unRegister(String userName) {
		boolean isDelete = dao.deleteUser(userName);
		if(isDelete){
			String path = I.AVATAR_PATH + I.AVATAR_TYPE_USER_PATH 
					+ I.BACKSLASH + userName + I.AVATAR_SUFFIX_JPG;
			File file = new File(path);
			if (file.exists()){
				file.delete();
			}
		}
		return isDelete;
	}

	@Override
	public User login(String userName, String password){
		System.out.println("SuperQQBiz.login,userName="+userName+",password="+password);
		User user = dao.findUserByUserName(userName);
		if (user == null) {
			user = new User(false,I.MSG_LOGIN_UNKNOW_USER);
		}
		if (user!=null && !user.getMUserPassword().equals(password)) {
			user = new User(false,I.MSG_LOGIN_ERROR_PASSWORD);
		}
		user.setResult(true);
		user.setMsg(I.MSG_LOGIN_SUCCESS);
		return user;
	}

	@Override
	public User findUserByUserName(String userName) {
		if(userName==null || userName.isEmpty()) return null;
		User user = dao.findUserByUserName(userName);
		return user;
	}

	@Override
	public User[] findUsersByUserName(String userName, int pageId, int pageSize) {
		User[] users = dao.findUsersByUserName(userName, pageId, pageSize);
		return users;
	}

	@Override
	public User[] findUsersByNick(String nick, int pageId, int pageSize) {
		User[] users = dao.findUsersByNick(nick, pageId, pageSize);
		return users;
	}
	
	@Override
	public User[] findUsersForSearch(String nick,String username,int pageId,int pageSize) {
		User[] users = dao.findUsersForSearch(nick,username, pageId, pageSize);
		return users;
	}
	@Override
	public User updateUserNickByUserName(String userName, String newNick) {
		User user = dao.findUserByUserName(userName);
		if(user==null){
			user = new User(false,I.MSG_LOGIN_UNKNOW_USER);
		} else if(user.getMUserNick().equals(newNick)) {
			user = new User(false,I.MSG_USER_SAME_NICK);
		} else {
			boolean isSuccess = dao.updateUserNick(user, newNick);
			if(isSuccess){
				user.setMUserNick(newNick);
				user.setResult(true);
				user.setMsg(I.MSG_USER_UPDATE_NICK_SUCCESS);
			} else {
				user.setResult(false);
				user.setMsg(I.MSG_USER_UPDATE_NICK_FAIL);
			}
		}
		return user;
	}

	@Override
	public User updateUserPasswordByUserName(String userName, String newPassword) {
		User user = dao.findUserByUserName(userName);
		if(user==null){
			user = new User(false,I.MSG_LOGIN_UNKNOW_USER);
		} else if(user.getMUserPassword().equals(newPassword)) {
			user = new User(false,I.MSG_USER_SAME_PASSWORD);
		} else {
			boolean isSuccess = dao.updateUserPassword(user, newPassword);
			if(isSuccess){
				user.setMUserPassword(newPassword);
				user.setResult(true);
				user.setMsg(I.MSG_USER_UPDATE_PASSWORD_SUCCESS);
			} else {
				user.setResult(false);
				user.setMsg(I.MSG_USER_UPDATE_PASSWORD_FAIL);
			}
		}
		return user;
	}

	@Override
	public Contact[] findContactsByUserName(String userName) {
		Contact[] contacts = dao.findContactsByUserName(userName);
		return contacts;
	}

	@Override
	public Contact[] findContactListByMyuid(int myuid) {
		Contact[] contacts = dao.findContactListByMyuid(myuid);
		return contacts;
	}
	
	@Override
	public Contact[] findContactsByUserName(String userName, int pageId, int pageSize) {
		Contact[] contacts = dao.findContactsByUserName(userName, pageId, pageSize);
		return contacts;
	}

	@Override
	public Contact[] findContactListByMyuid(int myuid, int pageId, int pageSize) {
		Contact[] contacts = dao.findContactListByMyuid(myuid, pageId, pageSize);
		return contacts;
	}

	@Override
	public Contact addContact(String userName, String name) {
		return dao.addContact(userName, name);
	}

	@Override
	public boolean deleteContact(int myuid, int cuid) {
		boolean isSuccess = dao.deleteContact(myuid, cuid);
		if(isSuccess){
			isSuccess = dao.deleteContact(cuid, myuid);
		}
		return isSuccess;
	}

	@Override
	public boolean deleteContact(String userName, String name) {
		boolean isSuccess = dao.deleteContact(userName, name);
		if(isSuccess){
			isSuccess = dao.deleteContact(name, userName);
		}
		return isSuccess;
	}
	@Override
	public boolean updateAvatar(int id, String name,int type) {
		if(id>0 && name!=null) {
			if(type==I.AVATAR_TYPE_USER){
				return dao.updateAvatar(id, name, I.AVATAR_TYPE_USER_PATH, I.AVATAR_TYPE_USER);
			}else{
				return dao.updateAvatar(id, name, I.AVATAR_TYPE_GROUP_PATH, I.AVATAR_TYPE_GROUP);
			}
		}
		return false;
	}

}
