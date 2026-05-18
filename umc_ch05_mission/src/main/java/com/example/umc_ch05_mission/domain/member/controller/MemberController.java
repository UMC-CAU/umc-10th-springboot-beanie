package com.example.umc_ch05_mission.domain.member.controller;

import com.example.umc_ch05_mission.domain.member.dto.MemberReqDTO;
import com.example.umc_ch05_mission.domain.member.dto.MemberResDTO;
import com.example.umc_ch05_mission.domain.member.exception.code.MemberSuccessCode;
import com.example.umc_ch05_mission.domain.member.service.MemberService;
import com.example.umc_ch05_mission.domain.mission.dto.MissionResDTO;
import com.example.umc_ch05_mission.domain.review.dto.ReviewResDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "이름, 이메일, 비밀번호, 휴대폰번호로 회원을 등록합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력값 유효성 오류")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signin")
    public ApiResponse<MemberResDTO.SignUpRes> signUp(@Valid @RequestBody MemberReqDTO.SignUpReq req) {
        return ApiResponse.of(MemberSuccessCode.MEMBER_JOIN, memberService.signUp(req));
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인합니다. (소셜 로그인 미지원)")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력값 유효성 오류")
    })
    @PostMapping("/auth/login")
    public ApiResponse<MemberResDTO.LoginRes> login(@Valid @RequestBody MemberReqDTO.LoginReq req) {
        return ApiResponse.onSuccess(memberService.login(req));
    }

    @Operation(summary = "마이페이지 조회", description = "닉네임, 이메일, 보유 포인트, 작성 리뷰 수를 반환합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "회원 없음")
    })
    @GetMapping("/members/me")
    public ApiResponse<MemberResDTO.MyPageRes> getMyPageInfo() {
        // TODO: Spring Security 적용 후 @AuthenticationPrincipal로 memberId 주입
        return ApiResponse.onSuccess(memberService.getMyPageInfo(1L));
    }

    @Operation(summary = "내 정보 수정", description = "이름, 휴대폰번호, 프로필 이미지를 부분 수정합니다. null 필드는 변경하지 않습니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "회원 없음")
    })
    @PatchMapping("/members/me")
    public ApiResponse<Void> updateMyInfo(@RequestBody MemberReqDTO.UpdateMemberReq req) {
        // TODO: Spring Security 적용 후 @AuthenticationPrincipal로 memberId 주입
        memberService.updateMyInfo(1L, req);
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "홈화면 정보 조회", description = "현재 진행 중인 미션 수, 보유 포인트, 지역명을 반환합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "회원 없음")
    })
    @GetMapping("/members/me/mission/progress")
    public ApiResponse<MemberResDTO.HomeProgressRes> getHomeProgress(
            @Parameter(description = "조회할 지역명", example = "안암동", required = true)
            @RequestParam String region) {
        // TODO: Spring Security 적용 후 @AuthenticationPrincipal로 memberId 주입
        return ApiResponse.onSuccess(memberService.getHomeProgress(1L, region));
    }

    @Operation(summary = "내 미션 목록 조회", description = "상태(ONGOING / COMPLETE)로 필터링한 내 미션 목록을 페이징하여 반환합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 status 값")
    })
    @GetMapping("/members/me/missions")
    public ApiResponse<Page<MissionResDTO.MissionItemRes>> getMyMissions(
            @Parameter(description = "미션 상태 (ONGOING | COMPLETE)", example = "ONGOING", required = true)
            @RequestParam String status,
            @ParameterObject Pageable pageable) {
        // TODO: Spring Security 적용 후 @AuthenticationPrincipal로 memberId 주입
        return ApiResponse.onSuccess(memberService.getMyMissions(1L, status, pageable));
    }

    @Operation(summary = "내가 쓴 리뷰 목록 조회", description = "로그인한 회원이 작성한 모든 리뷰 목록을 반환합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "회원 없음")
    })
    @GetMapping("/members/me/reviews")
    public ApiResponse<ReviewResDTO.ReviewListRes> getMyReviews() {
        // TODO: Spring Security 적용 후 @AuthenticationPrincipal로 memberId 주입
        return ApiResponse.onSuccess(memberService.getMyReviews(1L));
    }
}
