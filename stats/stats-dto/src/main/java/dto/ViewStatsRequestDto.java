package dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
public class ViewStatsRequestDto {

	@Past
	@NotNull(message = "Необходимо указать начальную дату фильтрации")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime start;

	@NotNull(message = "Необходимо указать кончную дату фильтрации")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime end;

	private List<String> uris;

	@Builder.Default
	private Boolean unique = false;

	@AssertTrue(message = "Конечная дата должна быть после начальной")
	public boolean endIsAfterStart() {
		if (start != null && end != null) {
			return end.isAfter(start);
		}
		return true;
	}
}
