package ru.practicum.category.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

	@Positive
	private Long id; // read only

	@Size(min = 1, message = "Название категории не должно быть меньше 1 символа")
	@Size(max = 50, message = "Название категории не должно быть больше 50 символов")
	private String name;
}
