package kr.co.seoulit.erp.hr.affair.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@CrossOrigin("*")
@RestController
@Tag(name = "직원 사진관리", description = "직원사진관리 API")
@RequestMapping("/hr/affairFile/*")
public class EmpFileController {

    private final EmpController empController;


    public EmpFileController(EmpController empController) {
        this.empController = empController;
    }

    @Operation(summary = "직원 사진 수정", description = "직원 사진을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PostMapping("/photoUpdate")
    public String uploadFile(@RequestPart("file") MultipartFile file) {
        System.out.println("사진파일 확인"+ file);
        String photoFileName=empController.empcode();
        System.out.println("emp코드확인"+ photoFileName);

        try {
            // 파일이 비어있지 않은 경우에만 처리
            if (!file.isEmpty()) {
                String fileName = photoFileName;
                String filePath = "C:\\Users\\young\\OneDrive\\바탕 화면\\학생프로젝트\\77기 1차 Vue\\인사\\77th_hr_front\\assets\\images\\empImages\\" + fileName + ".png";
                System.out.print("저장 위치 확인"+ filePath);
                System.out.print("사진 이름 확인"+ fileName);
                // 기존 파일 덮어쓰기
                File destFile = new File(filePath);
                file.transferTo(destFile);

                return "파일 업로드 성공";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "파일 업로드 실패";
    }

    @Operation(summary = "직원 사진 등록", description = "직원 사진 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PostMapping("/photoResi")
    public String registFile(@RequestPart("file") MultipartFile file) {
        System.out.println("사진파일 확인" + file);
        String photoFileName = empController.empcode();
        System.out.println("emp코드확인" + photoFileName);

        try {
            // 파일이 비어있지 않은 경우에만 처리
            if (!file.isEmpty()) {
                String fileName = photoFileName;
                System.out.println("사진파일 등록~~~~~" + fileName);
                String filePath = "C:\\Users\\young\\OneDrive\\바탕 화면\\학생프로젝트\\77기 1차 Vue\\인사\\77th_hr_front\\assets\\images\\empImages\\" + fileName + ".png";
                System.out.println( "사진파일 등록 주소~~~~~"+  filePath);
                // 새로운 파일 저장
                Path path = Paths.get(filePath);
                System.out.println("사진파일 등록 주소2222222222222222~~~~~"+path);
                Files.write(path, file.getBytes());

                return "파일 업로드 성공";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "파일 업로드 실패";
    }

}


