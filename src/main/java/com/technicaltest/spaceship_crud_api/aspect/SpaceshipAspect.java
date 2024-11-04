package com.technicaltest.spaceship_crud_api.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SpaceshipAspect {

	private static final Logger logger = LoggerFactory.getLogger(SpaceshipAspect.class);
	private static final String POINT_CUT = "execution(* com.technicaltest.spaceship_crud_api.controller.SpaceshipController.getSpaceshipById(..)) && args(id)";

	@Pointcut(POINT_CUT)
	public void getSpaceshipById(Long id) {
	}

	@Before("getSpaceshipById(id)")
	public void logNegativeId(Long id) {
		if (id < 0) {
			logger.warn("Attempt to fetch spaceship with negative ID: {}", id);
		}
	}
}
