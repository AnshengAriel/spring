package org.springframework.bean;

/**
 * @author derekyi
 * @date 2020/12/6
 */
public class WorldServiceImpl implements WorldService {

	private String name;

	@Override
	public void explode() {
		System.out.println("WorldServiceImpl#explode");
		System.out.println("世界爆炸了");
	}

	@Override
	public String getName() {
		System.out.println("WorldServiceImpl#getName");
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
