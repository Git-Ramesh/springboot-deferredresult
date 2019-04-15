/*
 *  com.rs.app.controller.UserController.java  Apr 4, 2019
 *
 *  © Radiant Sage.
 *  Manjeera Trinity Corporate, Plot No. 912,K P H B Phase 3, Kukatpally, Hyderabad, Telangana 500072,INDIA.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of
 *  Radiant Sage . ("Confidential Information").  You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into
 *  with Radiant Sage.
 */
package com.rs.app.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.rs.app.model.User;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ramesh
 * @version 1.0
 * @since 2019-04-04 11:37:29.421
 */
@RestController
@RequestMapping(UserController.USER_CONTROLLER_URI)
@Slf4j
public class UserController {
	protected static final String USER_CONTROLLER_URI = "/user";

	@GetMapping("/deferredresult")
	public DeferredResult<ResponseEntity<User>> getUserInfoByDeferredResult() {
		log.debug("getUserInfo...");
		DeferredResult<ResponseEntity<User>> output = new DeferredResult<>();
		ForkJoinPool.commonPool().submit(() -> {
			log.debug("Request Processing in separate thread");
			output.setResult(ResponseEntity.ok(getUser(false)));
		});
		log.debug("servlet thread freed");
		return output;
	}
	@GetMapping("/completablefuture")
	public CompletableFuture<ResponseEntity<User>> getUserInfoByCompletableFuture() {
		log.debug("getUserInfo...");
		CompletableFuture<ResponseEntity<User>> supplyAsync = CompletableFuture.supplyAsync(() -> ResponseEntity.ok(getUser(true)));
		supplyAsync.exceptionally(this::handleException);
		log.debug("servlet thread freed");
		return supplyAsync;
	}
	private User getUser(boolean throwError) {
		log.debug("getUser..");
		sleep(6, TimeUnit.SECONDS);
		if(throwError) {
			throw new RuntimeException("Unable to get user");
		}
		return new User(1L, "Ramesh", 8106067236L, "ramesh@rsageventures.com");
	}

	private static void sleep(long timeout, TimeUnit timeUnit) {
		try {
			timeUnit.sleep(timeout);
		} catch (InterruptedException ie) {
			// Ignore
		}
	}
	
	private ResponseEntity<User> handleException(Throwable th) {
		log.debug("handleException..");
		System.out.println(th.getCause());
		return null;
}
}
