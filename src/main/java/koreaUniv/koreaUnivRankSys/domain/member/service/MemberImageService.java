package koreaUniv.koreaUnivRankSys.domain.member.service;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberImage;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberImageService {

    private final MemberImageRepository memberImageRepository;

    @Value("${koreaUniv.upload.path}")
    private String uploadPath;
    public MemberImage createMemberImage(MultipartFile profileImage) {
        String originName = profileImage.getOriginalFilename();
        String fileName = originName.substring(originName.lastIndexOf("\\") + 1);
        String saveName = uploadPath + File.separator + fileName;
        Path savePath = Paths.get(saveName);
        try {
            profileImage.transferTo(savePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MemberImage memberImage = MemberImage.builder()
                .originName(originName)
                .fileName(fileName)
                .path(saveName)
                .build();

        return memberImage;
    }

    @Transactional
    public void delete(Member member, MemberImage currentMemberImage) {
        // 연관관계를 제거하고 remove 를 해야 한다.
        member.setMemberImage(null);
        memberImageRepository.delete(currentMemberImage);
        // 로컬 저장소에서도 사진 삭제 필요
    }
}
