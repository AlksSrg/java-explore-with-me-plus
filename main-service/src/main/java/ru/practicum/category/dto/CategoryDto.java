package ru.practicum.category.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class CategoryDto {
    @Positive
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id; // read only

    @Size(min = 1, message = "Название категории не должно быть меньше 1 символа")
    @Size(max = 50, message = "Название категории не должно быть больше 50 символов")
    @NotBlank
    private String name;

    public static CategoryDto mapFromCategory(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category mapToCategory() {
        return Category.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
