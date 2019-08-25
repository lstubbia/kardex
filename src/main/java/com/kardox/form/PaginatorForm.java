package com.kardox.form;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Este formulario permite manipular el paginado y 
 * filtros que puedan aplicarse desde front.
 * 
 * @author lstubbia
 *
 */
@Data
@Getter
@Setter
public class PaginatorForm {

	public final static int DEFAULT_ROWS_TO_SHOW = 5;

	private String query = "";
	private Integer queryNumber;
	protected Integer selectedRowsToShow = 5;
	private Integer totalRow;
	private List<Integer> pages;
	private Integer selectedPage = 0;

	public static List<Integer> ROWS_TO_SHOW = Lists.newArrayList(5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100);

	public PaginatorForm() {
	}

	public PaginatorForm(Page<?> page) {
		update(page);
	}

	/**
	 * Este metodo deberá ser utilizado cuando el paginatorForm actualiza su contendido (page).
	 * 
	 * @param page
	 */
	public void update(Page<?> page) {
		this.selectedPage = page.getNumber();
		this.totalRow = page.getNumberOfElements();
		this.selectedRowsToShow = Optional.ofNullable(this.selectedRowsToShow).orElse(DEFAULT_ROWS_TO_SHOW);
		this.pages = Lists.newArrayListWithCapacity(page.getTotalPages());
		this.pages = IntStream.range(0, page.getTotalPages()).boxed().collect(Collectors.toList());
	}
}
