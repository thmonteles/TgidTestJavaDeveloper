package br.com.tgid.TgidTestJavaDeveloper.DTOs;

import java.util.List;

public record SuccessCompanyListResponse(boolean success, String msg, List<CompanyResponseDTO> companies) {
}
