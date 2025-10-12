package ru.practicum.category.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.category.model.Category;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewCategoryDto {
    @Length(min = 1, message = "Длина названия категории не болжна быть меньше 1 символа")
    @Length(max = 50, message = "Длина названия категории не должна быть больше 50 символов")
    @NotBlank
    private String name;

    public Category mapToCategory() {
        return Category.builder()
                .name(this.name)
                .build();
    }
}
