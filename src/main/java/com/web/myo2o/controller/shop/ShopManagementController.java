package com.web.myo2o.controller.shop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.myo2o.dto.ShopExecution;
import com.web.myo2o.entity.Area;
import com.web.myo2o.entity.PersonInfo;
import com.web.myo2o.entity.Shop;
import com.web.myo2o.entity.ShopCategory;
import com.web.myo2o.enums.ProductCategoryStateEnum;
import com.web.myo2o.enums.ShopStateEnum;
import com.web.myo2o.service.AreaService;
import com.web.myo2o.service.ShopCategoryService;
import com.web.myo2o.service.ShopService;
import com.web.myo2o.util.CodeUtil;
import com.web.myo2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shop")
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
//	@Autowired
//	private LocalAuthService localAuthService;

//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	@ResponseBody
//	private Map<String, Object> list(HttpServletRequest request) {
//		Map<String, Object> modelMap = new HashMap<String, Object>();
//		PersonInfo user = (PersonInfo) request.getSession()
//				.getAttribute("user");
//		long employeeId = user.getUserId();
//		if (hasAccountBind(request, employeeId)) {
//			modelMap.put("hasAccountBind", true);
//		} else {
//			modelMap.put("hasAccountBind", false);
//		}
//		List<Shop> list = new ArrayList<Shop>();
//		try {
//			ShopExecution shopExecution = shopService
//					.getByEmployeeId(employeeId);
//			list = shopExecution.getShopList();
//			modelMap.put("shopList", list);
//			modelMap.put("user", user);
//			modelMap.put("success", true);
//			// 列出店铺成功之后，将店铺放入session中作为权限验证依据，即该帐号只能操作它自己的店铺
//			request.getSession().setAttribute("shopList", list);
//		} catch (Exception e) {
//			e.printStackTrace();
//			modelMap.put("success", false);
//			modelMap.put("errMsg", e.toString());
//		}
//		return modelMap;
//	}
    @RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopManagementInfo(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId=HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId<=0){
            Object currentShopObj=request.getSession().getAttribute("currentShop");
            if(currentShopObj==null){
                modelMap.put("redirect",true);
                modelMap.put( "url","/myo2o/shop/shoplist");

            }
            else {
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect",false);
                modelMap.put("shopId",currentShop.getShopId());
            }
        }else{
            Shop currentShop =new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            modelMap.put("redirect",false);
        }
        return  modelMap;
    }


@RequestMapping(value = "/list", method = RequestMethod.GET)
@ResponseBody
private Map<String, Object> getShopList(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    PersonInfo user=new PersonInfo();
    user.setUserId(8L);
    user.setName("lee");
    request.getSession().setAttribute("user",user);

    user = (PersonInfo) request.getSession()
            .getAttribute("user");

    List<Shop> list = new ArrayList<Shop>();

    try {
        Shop shopCondition = new Shop();
        shopCondition.setOwnerId(user.getUserId());

        ShopExecution shopExecution = shopService
                .getShopList(shopCondition, 0, 100);

        list = shopExecution.getShopList();
        modelMap.put("shopList", list);
        modelMap.put("user", user);
        modelMap.put("success", true);
        // 列出店铺成功之后，将店铺放入session中作为权限验证依据，即该帐号只能操作它自己的店铺
        request.getSession().setAttribute("shopList", list);

    } catch (Exception e) {
        e.printStackTrace();
        modelMap.put("success", false);
        modelMap.put("errMsg", e.toString());
    }
    return modelMap;
}



	@RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopById(@RequestParam Long shopId,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (shopId != null && shopId > -1) {
//			Shop shop = shopService.getByShopId(shopId);
//			shop.getShopCategory().setShopCategoryName(
//					shopCategoryService.getShopCategoryById(
//							shop.getShopCategory().getShopCategoryId())
//							.getShopCategoryName());
//			shop.getParentCategory().setShopCategoryName(
//					shopCategoryService.getShopCategoryById(
//							shop.getParentCategory().getShopCategoryId())
//							.getShopCategoryName());
//			modelMap.put("shop", shop);
//			request.getSession().setAttribute("currentShop", "shop");
//			List<Area> areaList = new ArrayList<Area>();
//			try {
//				areaList = areaService.getAreaList();
//			} catch (IOException e) {
//				modelMap.put("success", false);
//				modelMap.put("errMsg", e.toString());
//			}
//			modelMap.put("areaList", areaList);
//			modelMap.put("success", true);
            try{
            Shop shop = shopService.getByShopId(shopId);
            List<Area> areaList = new ArrayList<Area>();areaList = areaService.getAreaList();
            modelMap.put("shop", shop);
            request.getSession().setAttribute("currentShop", shop);
            modelMap.put("areaList", areaList);
			modelMap.put("success", true);}
			catch(Exception e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");

		}
		return modelMap;
	}

	@RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService
					//.getAllSecondLevelShopCategory();
			.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
		} catch (IOException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		modelMap.put("shopCategoryList", shopCategoryList);
		modelMap.put("areaList", areaList);
		modelMap.put("success", true);
		return modelMap;
	}

	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShop(HttpServletRequest request) {
		boolean statusChange = HttpServletRequestUtil.getBoolean(request,
				"statusChange");
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartRequest
					.getFile("shopImg");
		}
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		Shop currentShop = (Shop) request.getSession().getAttribute(
				"currentShop");
		shop.setShopId(currentShop.getShopId());
        ShopExecution se;
		if (shop != null && shop.getShopId() != null) {
			filterAttribute(shop);
			try {
			    if(shopImg==null){
                     se= shopService.modifyShop(shop, null);
                }else {
                     se = shopService.modifyShop(shop, shopImg);
                }
				if (se.getState() == ProductCategoryStateEnum.SUCCESS
						.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
		}
		return modelMap;
	}

	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartRequest
					.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		//2.注册店铺
		if (shop != null && shopImg != null) {
			try {
//				PersonInfo user = (PersonInfo) request.getSession()
//						.getAttribute("user");
//				shop.setOwnerId(user.getUserId());
				shop.setOwnerId(9L);
				ShopExecution se = shopService.addShop(shop, shopImg);
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					// 若shop创建成功，则加入session中，作为权限使用
					@SuppressWarnings("unchecked")
					List<Shop> shopList = (List<Shop>) request.getSession()
							.getAttribute("shopList");
					if (shopList != null && shopList.size() > 0) {
						shopList.add(se.getShop());
						request.getSession().setAttribute("shopList", shopList);
					} else {
						shopList = new ArrayList<Shop>();
						shopList.add(se.getShop());
						request.getSession().setAttribute("shopList", shopList);
					}
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
		}
		return modelMap;
	}

	private void filterAttribute(Shop shop) {

	}

//	private boolean hasAccountBind(HttpServletRequest request, long userId) {
//		if (request.getSession().getAttribute("bindAccount") == null) {
//			LocalAuth localAuth = localAuthService.getLocalAuthByUserId(userId);
//			if (localAuth != null && localAuth.getUserId() != null) {
//				request.getSession().setAttribute("bindAccount", localAuth);
//				return true;
//			} else {
//				return false;
//			}
//		} else {
//			return true;
//		}
//	}
}
