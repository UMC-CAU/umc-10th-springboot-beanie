package com.example.umc_ch05_mission.domain.mission.controller;

import com.example.umc_ch05_mission.domain.mission.dto.MissionReqDTO;
import com.example.umc_ch05_mission.domain.mission.dto.MissionResDTO;
import com.example.umc_ch05_mission.domain.mission.service.MissionService;
import com.example.umc_ch05_mission.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Mission", description = "미션 관련 API")
@RestController
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @Operation(summary = "홈화면 도전 가능한 미션 목록 조회",
               description = "특정 지역에서 아직 도전하지 않은(ONGOING/PENDING 제외) 미션 목록을 페이징하여 반환합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @GetMapping("/missions")
    public ApiResponse<Page<MissionResDTO.MissionItemRes>> getHomeMissions(
            @Parameter(description = "조회할 지역 ID", example = "1", required = true)
            @RequestParam Long regionId,
            @ParameterObject Pageable pageable) {
        return ApiResponse.onSuccess(missionService.getHomeMissions(regionId, pageable));
    }

    @Operation(summary = "미션 성공 요청",
               description = "회원이 미션 완료 후 성공 요청 버튼을 누르면 상태가 PENDING(인증 대기)으로 변경됩니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공 요청 완료"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "회원 미션 없음")
    })
    @PatchMapping("/member-missions/{memberMissionId}")
    public ApiResponse<Void> requestMissionSuccess(
            @Parameter(description = "회원 미션 ID", example = "1", required = true)
            @PathVariable Long memberMissionId) {
        missionService.requestMissionSuccess(memberMissionId);
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "미션 인증 번호 요청",
               description = "사장님께 보여줄 일회용 인증 번호를 발급합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "인증 번호 발급 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "회원 미션 없음")
    })
    @GetMapping("/member-mission/{id}/verification")
    public ApiResponse<MissionResDTO.VerificationCodeRes> getVerificationCode(
            @Parameter(description = "회원 미션 ID", example = "1", required = true)
            @PathVariable Long id) {
        return ApiResponse.onSuccess(missionService.getVerificationCode(id));
    }

    @Operation(summary = "사장님의 미션 승인",
               description = "사장님이 인증 번호를 확인하여 미션을 승인합니다. 코드 일치 시 상태가 COMPLETE로 변경되고 포인트가 지급됩니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "승인 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "인증 번호 불일치"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "회원 미션 없음")
    })
    @PostMapping("/owner/member-missions/{memberMissionId}/confirm")
    public ApiResponse<Void> confirmMission(
            @Parameter(description = "회원 미션 ID", example = "1", required = true)
            @PathVariable Long memberMissionId,
            @Valid @RequestBody MissionReqDTO.ConfirmMissionReq req) {
        missionService.confirmMission(memberMissionId, req);
        return ApiResponse.onSuccess(null);
    }
}
