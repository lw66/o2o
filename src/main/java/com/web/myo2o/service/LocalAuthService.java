package com.web.myo2o.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.web.myo2o.dto.LocalAuthExecution;
import com.web.myo2o.entity.LocalAuth;

public interface LocalAuthService {
	/**
	 * 
	 * @param userName
	 * @return
	 */
	LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	LocalAuth getLocalAuthByUserId(long userId);

	/**
	 * 
	 * @param localAuth
	 * @param profileImg
	 * @return
	 * @throws RuntimeException
	 */
	LocalAuthExecution register(LocalAuth localAuth,
                                CommonsMultipartFile profileImg) throws RuntimeException;

	/**
	 * 
	 * @param localAuth
	 * @return
	 * @throws RuntimeException
	 */
	LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
			throws RuntimeException;

	/**
	 * 
	 * @param
	 * @param userName
	 * @param password
	 * @param newPassword
	 * @param
	 * @return
	 */
	LocalAuthExecution modifyLocalAuth(Long userId, String userName,
                                       String password, String newPassword);
}