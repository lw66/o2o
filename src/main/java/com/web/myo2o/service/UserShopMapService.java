package com.web.myo2o.service;

import com.web.myo2o.dto.UserShopMapExecution;
import com.web.myo2o.entity.UserShopMap;

public interface UserShopMapService {

	UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition,
                                         int pageIndex, int pageSize);

}
