//package com.goorm.LocC;
//
//import com.goorm.LocC.global.common.dto.ApiResponse;
//import com.goorm.LocC.member.exception.MemberException;
//import com.goorm.LocC.store.domain.Category;
//import com.goorm.LocC.store.domain.City;
//import com.goorm.LocC.store.domain.Province;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import static com.goorm.LocC.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;
//
//@RestController
//public class TestController {
//
//    @PostMapping("/province")
//    public ResponseEntity<ApiResponse<Province>> test(
//            @RequestBody Province province
//            ) {
//        return ResponseEntity.ok(ApiResponse.success(province));
//    }
//
//    @PostMapping("/category")
//    public ResponseEntity<ApiResponse<Category>> test2(
//            @RequestBody Category category
//    ) {
//        return ResponseEntity.ok(ApiResponse.success(category));
//    }
//
//    @PostMapping("/city")
//    public ResponseEntity<ApiResponse<City>> test3(
//            @RequestBody City city
//    ) {
//        return ResponseEntity.ok(ApiResponse.success(city));
//    }
//
//    @GetMapping("/test")
//    public ResponseEntity<ApiResponse<City>> test4(
//    ) {
//        throw new MemberException(MEMBER_NOT_FOUND);
//    }
//}
