package ru.mirinion.inMemoryDB;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
	@EqualsAndHashCode.Include
	private long account;
	private String name;
	private double value;

}
