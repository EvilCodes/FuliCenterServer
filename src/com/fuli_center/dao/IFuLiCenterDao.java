package com.fuli_center.dao;

import java.util.ArrayList;

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

public interface IFuLiCenterDao {

	/**
	 * 向购物车添加数据
	 * @param cart
	 * @return
	 */
	public int addCart(CartBean cart);
	
	/**
	 * 删除购物车中指定的数据
	 * @param cartId:
	 * @return
	 */
	public boolean deleteCart(int cartId);
	
	/**
	 * 更新购物车
	 * @param cart
	 * @return
	 */
	public boolean updateCart(int cartId,int count);
	/**
	 * 更新购物车
	 * @param cart
	 * @return
	 */
	public boolean updateCart(int cartId,int count,boolean isChecked);
	
	/**
	 *从购物车下载数据
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	public CartBean[] findCarts(String userName,int pageId,int pageSize);
	
	/**
	 * 从tb_boutique表中查询所有精选的列表数据
	 * @return
	 */
	BoutiqueBean[] findBoutiques();
	
	/**
	 * 从tb_boutique_good表中查询精选的商品信息
	 * @param catId:商品小类别id
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	NewGoodBean[] findNewAndBoutiqueGoods(int catId,int pageId,int pageSize);
	
	/** 查询分类中的大类型信息*/
	CategoryParentBean[] findCategoryParent();
	
	/**
	 * 查询小类别商品列表信息
	 * @param catId:小类别id
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	CategoryChildBean[] findCategoryChildren(int catId,int pageId,int pageSize);
	
	/**
	 * 下载分类列表中指定id的商品详情信息
	 * @param goodsId：商品id
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	GoodDetailsBean findGoodDetails(int goodsId);
	
	/**
	 * 下载catId指定的一组商品详情数据
	 * @param catId:分类的id
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	ArrayList<GoodDetailsBean> findGoodsDetails(int catId,int pageId,int pageSize);
	
	/**
	 * 下载指定类别的颜色数据
	 * @param catId：小类别id
	 * @return
	 */
	ColorBean[] findColorsByCatId(int catId);
	
	/**
	 * 添加收藏
	 * @param w
	 */
	int addCollect(CollectBean collect);
	
	/**
	 * 删除收藏
	 * @param userName
	 * @param goodsId
	 * @return
	 */
	boolean deleteCollect(String userName,int id);
	
	/**
	 * 下载指定用户的收藏
	 * @param userName：用户账号
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	CollectBean[] findCollects(String userName,int pageId,int pageSize);

	/**
	 * 下载指定用户的收藏数量
	 * @param userName：用户账号
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	int findCollectCount(String userName);

	/**
	 * 获取指定用户是否收藏了指定商品
	 * @param userName
	 * @param goodsId
	 * @return
	 */
	boolean isCollect(String userName,int goodsId);
	
	/**
	 * 根据用户账号查找用户
	 * @param userName
	 * @return
	 */
	User findUserByUserName(String userName);

	/**
	 * 根据用户账号查找用户集合，模糊查询
	 * @param userName
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	User[] findUsersByUserName(String userName,int pageId,int pageSize);

	/**
	 * 根据用户昵称查找用户集合，模糊查询
	 * @param nick
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	User[] findUsersByNick(String nick,int pageId,int pageSize);

	/**
	 * 根据昵称或者用户名查找用户集合，模糊查询
	 * @param nick
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	User[] findUsersForSearch(String nick,String username,int pageId,int pageSize);
	
	/**
	 * 添加用户,并返回用户id
	 * @param user
	 * @return
	 */
	int addUser(User user);

	/**
	 * 删除用户
	 * @param userName
	 * @return
	 */
	boolean deleteUser(String userName);

	/**
	 * 更新用户昵称
	 * @param user
	 * @return
	 */
	boolean updateUserNick(User user, String newNick);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	boolean updateUserPassword(User user, String newPassword);
	
	/**
	 * 根据用户账号查询双方是否为好友
	 * @param userName
	 * @param name
	 * @return
	 */
	boolean isExistsContact(String userName,String name);

	/**
	 * 根据uid和ciu查询联系人信息
	 * @param myuid
	 * @param cuid
	 * @return
	 */
	Contact findContactById(int myuid,int cuid);

	/**
	 * 根据id查找好友数据
	 * @param id
	 * @return
	 */
	Contact findContactById(int id);

	/**
	 * 根据用户账号查找好友集合
	 * @param userName
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	Contact[] findContactsByUserName(String userName);

	/**
	 * 根据用户id查找好友集合
	 * @param myuid
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	Contact[] findContactListByMyuid(int myuid);
	
	/**
	 * 根据用户账号查找好友集合
	 * @param userName
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	Contact[] findContactsByUserName(String userName,int pageId,int pageSize);

	/**
	 * 根据用户id查找好友集合
	 * @param myuid
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	Contact[] findContactListByMyuid(int myuid,int pageId,int pageSize);

	/**
	 * 添加好友
	 * @param userName
	 * @param name
	 * @return
	 */
	Contact addContact(String userName,String name);

	/**
	 * 删除好友
	 * @param myuid
	 * @param cuid
	 * @return
	 */
	boolean deleteContact(int myuid,int cuid);

	/**
	 * 删除好友
	 * @param myuid
	 * @param cuid
	 * @return
	 */
	boolean deleteContact(String userName,String name);
	
	/**
	 * 更新头像
	 * @param name
	 * @param avatar
	 * @return
	 */
	boolean updateAvatar(int id, String name, String path, int type);

	/**
	 * 根据用户id查找用户
	 * @param id
	 * @return
	 */
	public User findUserById(int id);
	
}
