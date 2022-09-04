package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestLifeCyle {			
	
	//	테스트를 시작하기 전에 호출되는 메서드
	@BeforeAll
	static void beforeAll() {
		System.out.println("beforeAll > 테스트를 시작하기 전에 호출되는 메서드");
		System.out.println();
	}
	
	
	//	테스트를 종료하면 호출되는 메서드
	@AfterAll
	static void afterAll() {
		System.out.println("afterAll > 테스트를 종료하면 호출되는 메서드");
		System.out.println();	
	}
	
	//	각 테스트 메서드가 실행되기 전에 동작하는 메서드를 정의
	@BeforeEach
	void beforeEach() {
		System.out.println("beforeEach > 각 테스트 메서드가 실행되기 전에 동작하는 메서드");
		System.out.println();
	}
	
	//	각 테스트 메서드가 종료되면 호출되는 메서드
	@AfterEach
	void afterEach() {
		System.out.println("afterEach > 각 테스트 메서드가 종료되면 호출 되는 메서드");
		System.out.println();
	}
	
	@Test
	void test1() {
		System.out.println("1");
		System.out.println();
	}
	

	@Test
	void test2() {
		System.out.println("2");
		System.out.println();
	}
	
	@Test
	@Disabled
	void test3() {
		System.out.println("3");
		System.out.println();
	}
}
