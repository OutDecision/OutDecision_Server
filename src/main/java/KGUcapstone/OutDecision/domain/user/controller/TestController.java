package KGUcapstone.OutDecision.domain.user.controller;

import KGUcapstone.OutDecision.domain.user.KakaoApi;
import KGUcapstone.OutDecision.domain.user.domain.Member;
import KGUcapstone.OutDecision.domain.user.dto.MemberDTO;
import KGUcapstone.OutDecision.domain.user.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class TestController {

    private final KakaoApi kakaoApi = new KakaoApi();

    private final MemberRepository memberRepository;

    @Transactional
    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request){
        model.addAttribute("kakaoApiKey", kakaoApi.getApi_key());
        model.addAttribute("redirectUri", kakaoApi.getRedirect_uri());
        System.out.println("kakaoApi.getApi_key() = " + kakaoApi.getApi_key());
        System.out.println("kakaoApi.getRedirect_uri() = " + kakaoApi.getRedirect_uri());

        HttpSession session = request.getSession();
        System.out.println("session.getId() = " + session.getId());
        return "login";
    }

    @RequestMapping("/login/oauth2/code/kakao")
    public String kakaoLogin(@RequestParam String code, Model model, HttpSession session){
        System.out.println("TestController.kakaoLogin");

        // 세션 확인
        System.out.println("Kakao login process started.");
        System.out.println("Session ID: " + session.getId());
        // 세션에 저장된 정보 출력
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attributeValue = session.getAttribute(attributeName);
            System.out.println("Attribute: " + attributeName + ", Value: " + attributeValue);
        }

        // 1. 인가 코드 받기 (@RequestParam String code)

        // 2. 토큰 받기
        String accessToken = kakaoApi.getAccessToken(code);

        // 3. 사용자 정보 받기
        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        String email = (String) userInfo.get("email");
        String nickname = (String) userInfo.get("nickname");

        System.out.println("nickname = " + nickname);
        System.out.println("email = " + email);
        System.out.println("accessToken = " + accessToken);


//        if(nickname != null){
//            System.out.println("nickname != null");
//            session.setAttribute("accessToken", accessToken);
//            session.setAttribute("userId", nickname);
//        }



        if(memberRepository.findByEmail(email) == null){
            // 회원가입
            MemberDTO memberDTO = new MemberDTO();
            System.out.println("TestController.kakaoLogin 회원가입 memberRepository.findByEmail(email) == null");
            memberDTO.setEmail(email);
            memberDTO.setName(nickname);
            memberDTO.setAccessToken(accessToken);

            model.addAttribute("memberDTO", memberDTO);
            System.out.println("memberDTO.getEmail() = " + memberDTO.getEmail());
            System.out.println("memberDTO.getNickname() = " + memberDTO.getName());
            System.out.println("memberDTO.getAccessToken() = " + memberDTO.getAccessToken());

            return "register";
        }

        return "index";
    }

    @PostMapping("/register")
    public String signup(@ModelAttribute MemberDTO memberDTO,
                         @RequestParam String nickname,
                         @RequestParam String phone,
                         HttpSession session) {

        System.out.println("TestController.signup");

        memberDTO.setNickname(nickname);
        memberDTO.setPhone(phone);

        boolean success = registerMember(memberDTO);

        if (success) {
            session.setAttribute("accessToken", memberDTO.getAccessToken());
            session.setAttribute("userId", memberDTO.getName());

            Member member = Member.builder()
                    .email(memberDTO.getEmail())
                    .name(memberDTO.getName())
                    .phone(memberDTO.getPhone())
                    .nickname(memberDTO.getNickname())
                    .build();
            memberRepository.save(member);
            // 회원가입이 성공했을 경우, 로그인 페이지로 리다이렉트 또는 적절한 페이지로 이동
            return "home";
        } else {
            // 회원가입이 실패했을 경우, 적절한 오류 페이지로 이동하거나 오류 메시지를 보여줌
            return "error";
        }
    }

    private boolean registerMember(MemberDTO memberDTO) {
        // 적절한 회원가입 처리 로직을 수행
        // 여기서는 간단히 콘솔에 출력하는 예시를 보여줍니다.
        System.out.println("Received registration request:");
        System.out.println("Name: " + memberDTO.getName());
        System.out.println("Nickname: " + memberDTO.getNickname());
        System.out.println("Email: " + memberDTO.getEmail());
        System.out.println("Phone: " + memberDTO.getPhone());
        System.out.println("Access Token: " + memberDTO.getAccessToken());


        return true;
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        System.out.println("TestController.logout");

        String accessToken = (String) session.getAttribute("accessToken");
        if (accessToken != null && !accessToken.isEmpty()) {
            // 토큰의 유효성을 확인하고, 유효한 경우에만 로그아웃을 수행
            kakaoApi.kakaoLogout(accessToken);
        }

        // 세션에서 사용자 정보 삭제
        session.removeAttribute("accessToken");
        session.removeAttribute("userId");
        session.invalidate();

        return "home";

    }
}
