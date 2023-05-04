package com.example.S20230403.model;

import java.util.List;

import com.example.S20230403.model.users.AddInfoCheck;
import com.example.S20230403.model.users.SaveCheck;
import lombok.Data;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class Users {
    @NotBlank(groups = {SaveCheck.class}, message = "이메일 주소를 입력해주세요.")
    private String user_id;

    @NotBlank(groups = {SaveCheck.class}, message = "이름을 입력해주세요")
    @Size(groups = {SaveCheck.class, AddInfoCheck.class}, message = "최대 10글자까지 입력 가능합니다.", max = 10)
    private String name;

    @NotBlank(groups = {SaveCheck.class, AddInfoCheck.class}, message = "닉네임을 입력해주세요")
    @Size(groups = {SaveCheck.class, AddInfoCheck.class}, message = "최대 10글자까지 입력 가능합니다.", max = 10)
    private String nickname;

    @NotBlank(groups = {SaveCheck.class}, message = "비밀번호를 입력해주세요")
    @Pattern(groups = {SaveCheck.class}, regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$", message = "알파벳 대소문자, 숫자, 일부 특수문자(!@#$%^&*()_+-=[]{};':\"|,.<>/?)만 입력 가능합니다.")
    @Size(groups = {SaveCheck.class}, min = 4, max = 12)
    private String password;

    @NotBlank(groups = {SaveCheck.class, AddInfoCheck.class}, message = "전화번호를 입력해주세요")
    @Pattern(groups = {SaveCheck.class, AddInfoCheck.class}, regexp = "^[0-9]*$", message = "숫자만 입력 가능합니다.")
    @Size(groups = {SaveCheck.class, AddInfoCheck.class}, max = 11)
    private String phone;

    @NotBlank(groups = {SaveCheck.class, AddInfoCheck.class}, message = "통신사를 선택해주세요")
    private String telecom;

    @NotBlank(groups = {SaveCheck.class, AddInfoCheck.class}, message = "성별을 선택해주세요")
    private String gender;

    @NotBlank(groups = {SaveCheck.class}, message = "약관에 동의해주세요")
    private String agree;

    private String user_status;

    @NotBlank(groups = {SaveCheck.class,AddInfoCheck.class}, message = "회원 유형을 선택해주세요")
    private String auth_level;

    // ACCOM 한얼
    private int rownum;
    private int rn;
    private String search;
    private String keyword;
    private String pageNum;
    private int start;
    private int end;

    private List<Accom> accomList;
}
