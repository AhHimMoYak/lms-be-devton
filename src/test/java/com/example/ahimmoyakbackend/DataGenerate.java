package com.example.ahimmoyakbackend;

import com.example.ahimmoyakbackend.auth.common.Gender;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.entity.CourseBoard;
import com.example.ahimmoyakbackend.board.entity.CourseComment;
import com.example.ahimmoyakbackend.board.repository.CourseBoardRepository;
import com.example.ahimmoyakbackend.board.repository.CourseCommentRepository;
import com.example.ahimmoyakbackend.course.common.ContentType;
import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.common.CourseState;
import com.example.ahimmoyakbackend.course.common.EnrollmentState;
import com.example.ahimmoyakbackend.course.entity.Contents;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.entity.Curriculum;
import com.example.ahimmoyakbackend.course.entity.Enrollment;
import com.example.ahimmoyakbackend.course.repository.*;
import com.example.ahimmoyakbackend.live.common.LiveState;
import com.example.ahimmoyakbackend.live.entity.LiveStreaming;
import com.example.ahimmoyakbackend.live.repository.LiveQuizAnswerRepository;
import com.example.ahimmoyakbackend.live.repository.LiveQuizOptionRepository;
import com.example.ahimmoyakbackend.live.repository.LiveQuizRepository;
import com.example.ahimmoyakbackend.live.repository.LiveStreamingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * DB 에 작업과 테스트에 필요한 데이터들을 생성해주는 코드입니다.
 * start 메소드를 실행하면 자동으로 데이터베이스에 데이터가 생성됩니다.
 * (테스트용으로 작성된 테스트 코드가 아님 주의!)
 * <p>
 * [...딸깍!]
 */
@SpringBootTest
public class DataGenerate {

    private EnrollmentState getRandomState() {
        EnrollmentState[] states = EnrollmentState.values();
        int randomIndex = new Random().nextInt(states.length);
        return states[randomIndex];
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 이 메소드를 실행하면 데이터가 생성 됩니다.
     */
    @Test
    void start() {
        part1();
    }

    /*
    생성되는 데이터
    유저1
    유저2
    유저3
    코스1 (교육기관1, 유저1)
    커리큘럼1,2 (코스1)
    인롤먼트1 (유저2, 컨트랙트1)
    인롤먼트2 (유저3, 컨트랙트2)
     */

    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseBoardRepository courseBoardRepository;
    @Autowired
    CurriculumRepository curriculumRepository;
    @Autowired
    CourseCommentRepository courseCommentRepository;
    @Autowired
    AttendHistoryRepository attendHistoryRepository;
    @Autowired
    ContentsHistoryRepository contentsHistoryRepository;
    @Autowired
    ContentsMaterialRepository contentsMaterialRepository;
    @Autowired
    ContentsRepository contentsRepository;
    @Autowired
    ContentsVideoRepository contentsVideoRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CurriculumRepository CurriculumRepository;
    @Autowired
    EnrollmentRepository enrollmentRepository;
    @Autowired
    LiveQuizAnswerRepository liveQuizAnswerRepository;
    @Autowired
    LiveQuizOptionRepository liveQuizOptionRepository;
    @Autowired
    LiveQuizRepository liveQuizRepository;
    @Autowired
    LiveStreamingRepository liveStreamingRepository;

    void part1() {

        // User (Supervisor) 생성
        // 일반 사용자 6명
        User user1 = User.builder()
                .username("jhkim")
                .name("김지훈")
                .password(passwordEncoder.encode("password"))
                .birth(LocalDate.of(1990, 5, 21))
                .phone("01012341234")
                .email("jhkim@example.com")
                .gender(Gender.MALE)
                .build();

        User user2 = User.builder()
                .username("yslee")
                .name("이영숙")
                .password(passwordEncoder.encode("password"))
                .birth(LocalDate.of(1985, 11, 15))
                .phone("01056785678")
                .email("yslee@example.com")
                .gender(Gender.FEMALE)
                .build();

        User user3 = User.builder()
                .username("mspark")
                .name("박민수")
                .password(passwordEncoder.encode("password"))
                .birth(LocalDate.of(1997, 8, 9))
                .phone("01098769876")
                .email("mspark@example.com")
                .gender(Gender.MALE)
                .build();

        User user4 = User.builder()
                .username("hjchoi")
                .name("최현정")
                .password(passwordEncoder.encode("password"))
                .birth(LocalDate.of(1993, 3, 27))
                .phone("01034563456")
                .email("hjchoi@example.com")
                .gender(Gender.FEMALE)
                .build();

        User user5 = User.builder()
                .username("dskim")
                .name("김동수")
                .password(passwordEncoder.encode("password"))
                .birth(LocalDate.of(1988, 12, 5))
                .phone("01076547654")
                .email("dskim@example.com")
                .gender(Gender.MALE)
                .build();

        User user6 = User.builder()
                .username("syjung")
                .name("정수영")
                .password(passwordEncoder.encode("password"))
                .birth(LocalDate.of(1995, 6, 17))
                .phone("01067896789")
                .email("syjung@example.com")
                .gender(Gender.FEMALE)
                .build();

// 강사 7번 ~ 15번
        User user7 = User.builder()
                .username("ckang")
                .name("강철수")
                .password(passwordEncoder.encode("password"))
                .birth(LocalDate.of(1980, 7, 10))
                .phone("01011112222")
                .email("ckang@example.com")
                .gender(Gender.MALE)
                .tutorState(true)
                .build();

        User user8 = User.builder()
                .username("mshin")
                .name("신미영")
                .password(passwordEncoder.encode("password"))
                .birth(LocalDate.of(1982, 3, 25))
                .phone("01033334444")
                .email("mshin@example.com")
                .gender(Gender.FEMALE)
                .tutorState(true)
                .build();

        User user9 = User.builder()
                .username("jsong")
                .name("송지훈")
                .password(passwordEncoder.encode("password"))
                .birth(LocalDate.of(1978, 9, 2))
                .phone("01055556666")
                .email("jsong@example.com")
                .gender(Gender.MALE)
                .tutorState(true)
                .build();

        User user10 = User.builder()
                .username("kyoon")
                .name("윤경아")
                .password(passwordEncoder.encode("password"))
                .birth(LocalDate.of(1989, 1, 19))
                .phone("01077778888")
                .email("kyoon@example.com")
                .gender(Gender.FEMALE)
                .tutorState(true)
                .build();

        User user11 = User.builder()
                .username("lchoi")
                .name("최래원")
                .password(passwordEncoder.encode("password"))
                .birth(LocalDate.of(1985, 5, 11))
                .phone("01099990000")
                .email("lchoi@example.com")
                .gender(Gender.MALE)
                .tutorState(true)
                .build();

        User user12 = User.builder()
                .username("hpark")
                .name("박현정")
                .password(passwordEncoder.encode("password"))
                .birth(LocalDate.of(1990, 12, 3))
                .phone("01012345555")
                .email("hpark@example.com")
                .gender(Gender.FEMALE)
                .tutorState(true)
                .build();

        User user13 = User.builder()
                .username("ksim")
                .name("심규동")
                .password(passwordEncoder.encode("password"))
                .birth(LocalDate.of(1975, 4, 18))
                .phone("01066667777")
                .email("ksim@example.com")
                .gender(Gender.MALE)
                .tutorState(true)
                .build();

        User user14 = User.builder()
                .username("ojung")
                .name("정옥희")
                .password(passwordEncoder.encode("password"))
                .birth(LocalDate.of(1988, 8, 14))
                .phone("01088889999")
                .email("ojung@example.com")
                .gender(Gender.FEMALE)
                .tutorState(true)
                .build();

        User user15 = User.builder()
                .username("ysong")
                .name("송영호")
                .password(passwordEncoder.encode("password"))
                .birth(LocalDate.of(1987, 6, 9))
                .phone("01055557777")
                .email("ysong@example.com")
                .gender(Gender.MALE)
                .tutorState(true)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);
        userRepository.save(user7);
        userRepository.save(user8);
        userRepository.save(user9);
        userRepository.save(user10);
        userRepository.save(user11);
        userRepository.save(user12);
        userRepository.save(user13);
        userRepository.save(user14);
        userRepository.save(user15);
        // 강사 7의 강의 (user7)
        Course course1 = Course.builder()
                .tutor(user7)
                .title("비즈니스 전략 수립의 기초")
                .introduction("비즈니스 전략 수립을 위한 기본 개념을 학습합니다.")
                .beginDate(LocalDate.of(2024, 12, 1))
                .endDate(LocalDate.of(2025, 2, 28))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.AGRICULTURE_FORESTRY_FISHERIES)
                .build();
        courseRepository.save(course1);

        Course course2 = Course.builder()
                .tutor(user7)
                .title("마케팅 이론과 실전")
                .introduction("마케팅 이론을 바탕으로 실제 사례를 분석합니다.")
                .beginDate(LocalDate.of(2024, 10, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .state(CourseState.ONGOING)
                .category(CourseCategory.BUSINESS_MANAGEMENT)
                .build();
        courseRepository.save(course2);

        Course course3 = Course.builder()
                .tutor(user7)
                .title("팀 리더십과 조직 관리")
                .introduction("조직 내에서 효과적인 리더십을 개발합니다.")
                .beginDate(LocalDate.of(2024, 8, 1))
                .endDate(LocalDate.of(2024, 10, 31))
                .state(CourseState.FINISHED)
                .category(CourseCategory.CHEMICAL_BIO)
                .build();
        courseRepository.save(course3);

        Course course4 = Course.builder()
                .tutor(user7)
                .title("협상 스킬 마스터")
                .introduction("성공적인 협상을 위한 기술을 배웁니다.")
                .beginDate(LocalDate.of(2024, 11, 1))
                .endDate(LocalDate.of(2025, 1, 31))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.CONSTRUCTION)
                .build();
        courseRepository.save(course4);

        Course course5 = Course.builder()
                .tutor(user7)
                .title("디지털 전환 시대의 경영")
                .introduction("디지털 전환에 따른 경영의 변화와 대응 전략을 학습합니다.")
                .beginDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2024, 12, 1))
                .state(CourseState.ONGOING)
                .category(CourseCategory.BUSINESS_SALES)
                .build();
        courseRepository.save(course5);

// 강사 8의 강의 (user8)
        Course course6 = Course.builder()
                .tutor(user8)
                .title("재무제표 분석과 활용")
                .introduction("기업의 재무제표를 분석하고 활용하는 방법을 배웁니다.")
                .beginDate(LocalDate.of(2024, 11, 15))
                .endDate(LocalDate.of(2025, 2, 15))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.BUSINESS_MANAGEMENT)
                .build();
        courseRepository.save(course6);

        Course course7 = Course.builder()
                .tutor(user8)
                .title("보험 상품 기획 실무")
                .introduction("보험 상품을 기획하고 운영하는 방법을 학습합니다.")
                .beginDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .state(CourseState.ONGOING)
                .category(CourseCategory.CULTURE_ART_DESIGN_BROADCASTING)
                .build();
        courseRepository.save(course7);

        Course course8 = Course.builder()
                .tutor(user8)
                .title("관리회계의 이해")
                .introduction("관리회계의 개념과 실제 적용 사례를 배웁니다.")
                .beginDate(LocalDate.of(2024, 7, 1))
                .endDate(LocalDate.of(2024, 9, 30))
                .state(CourseState.FINISHED)
                .category(CourseCategory.DRIVING_TRANSPORTATION)
                .build();
        courseRepository.save(course8);

        Course course9 = Course.builder()
                .tutor(user8)
                .title("기업 예산 편성 전략")
                .introduction("효과적인 예산 편성 전략을 수립하는 방법을 배웁니다.")
                .beginDate(LocalDate.of(2024, 10, 10))
                .endDate(LocalDate.of(2024, 12, 10))
                .state(CourseState.ONGOING)
                .category(CourseCategory.EDUCATION_NATURE_EDUCATION_NATURE_SOCIAL_SCIENCE)
                .build();
        courseRepository.save(course9);

        Course course10 = Course.builder()
                .tutor(user8)
                .title("세무 전략과 리스크 관리")
                .introduction("세무 전략과 리스크 관리를 학습합니다.")
                .beginDate(LocalDate.of(2024, 12, 1))
                .endDate(LocalDate.of(2025, 2, 1))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.ELECTRICAL_ELECTRONIC)
                .build();
        courseRepository.save(course10);

// 강사 9의 강의 (user9)
        Course course11 = Course.builder()
                .tutor(user9)
                .title("Python 프로그래밍 기초")
                .introduction("Python의 기본 문법과 활용법을 학습합니다.")
                .beginDate(LocalDate.of(2024, 10, 1))
                .endDate(LocalDate.of(2025, 1, 1))
                .state(CourseState.ONGOING)
                .category(CourseCategory.ENVIRONMENTAL_ENERGY_SAFETY)
                .build();
        courseRepository.save(course11);

        Course course12 = Course.builder()
                .tutor(user9)
                .title("HTML과 CSS로 웹 개발하기")
                .introduction("HTML과 CSS를 이용한 웹 페이지 개발을 실습합니다.")
                .beginDate(LocalDate.of(2024, 11, 5))
                .endDate(LocalDate.of(2025, 2, 5))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.FINANCE_INSURANCE)
                .build();
        courseRepository.save(course12);

        Course course13 = Course.builder()
                .tutor(user9)
                .title("SQL과 데이터베이스 설계")
                .introduction("데이터베이스 설계와 SQL 활용을 학습합니다.")
                .beginDate(LocalDate.of(2024, 8, 1))
                .endDate(LocalDate.of(2024, 10, 1))
                .state(CourseState.FINISHED)
                .category(CourseCategory.FOOD_PROCESSING)
                .build();
        courseRepository.save(course13);

        Course course14 = Course.builder()
                .tutor(user9)
                .title("클라우드 컴퓨팅 개요")
                .introduction("AWS와 같은 클라우드 서비스의 개요를 학습합니다.")
                .beginDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2024, 12, 1))
                .state(CourseState.ONGOING)
                .category(CourseCategory.FOOD_SERVICE)
                .build();
        courseRepository.save(course14);

        Course course15 = Course.builder()
                .tutor(user9)
                .title("네트워크와 보안 기초")
                .introduction("네트워크와 정보 보안의 기본 개념을 학습합니다.")
                .beginDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 3, 1))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.HEALTH_MEDICAL_CARE)
                .build();
        courseRepository.save(course15);

// 강사 10의 강의 (user10)
        Course course16 = Course.builder()
                .tutor(user10)
                .title("물리 실험과 응용")
                .introduction("물리 실험을 통해 개념을 학습하고 응용합니다.")
                .beginDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 3, 1))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.INFORMATION_COMMUNICATION)
                .build();
        courseRepository.save(course16);

        Course course17 = Course.builder()
                .tutor(user10)
                .title("지구과학과 환경 변화")
                .introduction("지구의 환경 변화와 기후 변화를 학습합니다.")
                .beginDate(LocalDate.of(2024, 10, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .state(CourseState.ONGOING)
                .category(CourseCategory.LAW_POLICE_FIRE_RELIGION_DEFENSE)
                .build();
        courseRepository.save(course17);

        Course course18 = Course.builder()
                .tutor(user10)
                .title("천문학의 세계")
                .introduction("우주와 별에 대해 탐구하는 천문학 과정입니다.")
                .beginDate(LocalDate.of(2024, 8, 1))
                .endDate(LocalDate.of(2024, 10, 1))
                .state(CourseState.FINISHED)
                .category(CourseCategory.MACHINERY)
                .build();
        courseRepository.save(course18);

        Course course19 = Course.builder()
                .tutor(user10)
                .title("생물학 연구 기초")
                .introduction("생물 연구를 위한 기초 이론과 실습을 배웁니다.")
                .beginDate(LocalDate.of(2024, 11, 10))
                .endDate(LocalDate.of(2025, 2, 10))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.MATERIALS)
                .build();
        courseRepository.save(course19);

        Course course20 = Course.builder()
                .tutor(user10)
                .title("화학 반응과 실험 분석")
                .introduction("화학 반응을 실험을 통해 분석하는 과정입니다.")
                .beginDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2024, 12, 1))
                .state(CourseState.ONGOING)
                .category(CourseCategory.TEXTILE_CLOTHING)
                .build();
        courseRepository.save(course20);

// 강사 11의 강의 (user11)
        Course course21 = Course.builder()
                .tutor(user11)
                .title("헌법과 시민의 권리")
                .introduction("헌법의 기초와 시민의 권리에 대해 학습합니다.")
                .beginDate(LocalDate.of(2024, 10, 5))
                .endDate(LocalDate.of(2024, 12, 5))
                .state(CourseState.ONGOING)
                .category(CourseCategory.UTILIZING_ACCOMMODATION_TRAVEL_ENTERTAINMENT_SPORTS)
                .build();
        courseRepository.save(course21);

        Course course22 = Course.builder()
                .tutor(user11)
                .title("형사법의 이해")
                .introduction("형사법의 기본 개념과 실무를 학습합니다.")
                .beginDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2024, 11, 1))
                .state(CourseState.FINISHED)
                .category(CourseCategory.SECURITY_CLEANING)
                .build();
        courseRepository.save(course22);

        Course course23 = Course.builder()
                .tutor(user11)
                .title("경찰학 개론")
                .introduction("경찰 조직과 운영 방식에 대해 학습합니다.")
                .beginDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 3, 1))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.BUSINESS_SALES)
                .build();
        courseRepository.save(course23);

        Course course24 = Course.builder()
                .tutor(user11)
                .title("교정학의 이해")
                .introduction("교정 행정의 기초와 복지 시스템을 학습합니다.")
                .beginDate(LocalDate.of(2024, 11, 10))
                .endDate(LocalDate.of(2025, 2, 10))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.PRINTED_WOOD_FURNITURE_CRAFTS)
                .build();
        courseRepository.save(course24);

        Course course25 = Course.builder()
                .tutor(user11)
                .title("소방 안전과 화재 예방")
                .introduction("소방 안전과 화재 예방의 기초를 학습합니다.")
                .beginDate(LocalDate.of(2024, 8, 1))
                .endDate(LocalDate.of(2024, 10, 1))
                .state(CourseState.FINISHED)
                .category(CourseCategory.CULTURE_ART_DESIGN_BROADCASTING)
                .build();
        courseRepository.save(course25);

// 강사 12의 강의 (user12)
        Course course26 = Course.builder()
                .tutor(user12)
                .title("응급 처치 기본 과정")
                .introduction("응급 상황에서의 처치 방법을 학습합니다.")
                .beginDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 3, 1))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.DRIVING_TRANSPORTATION)
                .build();
        courseRepository.save(course26);

        Course course27 = Course.builder()
                .tutor(user12)
                .title("의료 윤리와 법률")
                .introduction("의료 윤리와 관련 법규를 학습합니다.")
                .beginDate(LocalDate.of(2024, 10, 1))
                .endDate(LocalDate.of(2024, 12, 1))
                .state(CourseState.ONGOING)
                .category(CourseCategory.SOCIAL_WELFARE_RELIGION)
                .build();
        courseRepository.save(course27);

        Course course28 = Course.builder()
                .tutor(user12)
                .title("병원 행정의 이해")
                .introduction("병원의 운영과 관리 방식을 학습합니다.")
                .beginDate(LocalDate.of(2024, 8, 1))
                .endDate(LocalDate.of(2024, 10, 1))
                .state(CourseState.FINISHED)
                .category(CourseCategory.MANAGEMENT_ACCOUNTING_ADMINISTRATIVE_AFFAIRS)
                .build();
        courseRepository.save(course28);

        Course course29 = Course.builder()
                .tutor(user12)
                .title("공공보건 정책 분석")
                .introduction("공공보건 정책과 시스템을 분석합니다.")
                .beginDate(LocalDate.of(2025, 2, 1))
                .endDate(LocalDate.of(2025, 4, 1))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.HEALTH_MEDICAL_CARE)
                .build();
        courseRepository.save(course29);

        Course course30 = Course.builder()
                .tutor(user12)
                .title("보건 통계와 데이터 분석")
                .introduction("보건 데이터를 분석하는 방법을 학습합니다.")
                .beginDate(LocalDate.of(2024, 11, 1))
                .endDate(LocalDate.of(2025, 1, 1))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.CONSTRUCTION)
                .build();
        courseRepository.save(course30);

// 강사 13의 강의 (user13)
        Course course31 = Course.builder()
                .tutor(user13)
                .title("네트워크 설계와 운영")
                .introduction("네트워크 설계와 운영의 기본 개념을 학습합니다.")
                .beginDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2024, 12, 1))
                .state(CourseState.ONGOING)
                .category(CourseCategory.LAW_POLICE_FIRE_RELIGION_DEFENSE)
                .build();
        courseRepository.save(course31);

        Course course32 = Course.builder()
                .tutor(user13)
                .title("보안 시스템 구축")
                .introduction("네트워크 보안 시스템을 구축하는 방법을 배웁니다.")
                .beginDate(LocalDate.of(2024, 10, 1))
                .endDate(LocalDate.of(2025, 1, 1))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.FOOD_PROCESSING)
                .build();
        courseRepository.save(course32);

        Course course33 = Course.builder()
                .tutor(user13)
                .title("서버 관리의 기초")
                .introduction("서버의 구성과 운영 방법을 실습합니다.")
                .beginDate(LocalDate.of(2024, 8, 1))
                .endDate(LocalDate.of(2024, 10, 1))
                .state(CourseState.FINISHED)
                .category(CourseCategory.UTILIZING_ACCOMMODATION_TRAVEL_ENTERTAINMENT_SPORTS)
                .build();
        courseRepository.save(course33);

        Course course34 = Course.builder()
                .tutor(user13)
                .title("클라우드 네트워크 구성")
                .introduction("AWS를 활용해 클라우드 네트워크를 구성합니다.")
                .beginDate(LocalDate.of(2024, 11, 1))
                .endDate(LocalDate.of(2025, 2, 1))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.TEXTILE_CLOTHING)
                .build();
        courseRepository.save(course34);

        Course course35 = Course.builder()
                .tutor(user13)
                .title("정보 보안 실무")
                .introduction("정보 보안을 위한 실무 기법을 학습합니다.")
                .beginDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2024, 11, 1))
                .state(CourseState.FINISHED)
                .category(CourseCategory.FOOD_SERVICE)
                .build();
        courseRepository.save(course35);

// 강사 14의 강의 (user14)
        Course course36 = Course.builder()
                .tutor(user14)
                .title("UX/UI 디자인 기초")
                .introduction("사용자 경험과 인터페이스 디자인을 학습합니다.")
                .beginDate(LocalDate.of(2024, 9, 15))
                .endDate(LocalDate.of(2024, 12, 15))
                .state(CourseState.ONGOING)
                .category(CourseCategory.ENVIRONMENTAL_ENERGY_SAFETY)
                .build();
        courseRepository.save(course36);

        Course course37 = Course.builder()
                .tutor(user14)
                .title("그래픽 디자인 실습")
                .introduction("포토샵과 일러스트레이터를 활용한 디자인 실습입니다.")
                .beginDate(LocalDate.of(2024, 10, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .state(CourseState.ONGOING)
                .category(CourseCategory.INFORMATION_COMMUNICATION)
                .build();
        courseRepository.save(course37);

        Course course38 = Course.builder()
                .tutor(user14)
                .title("디지털 마케팅 디자인")
                .introduction("디지털 마케팅을 위한 디자인 기법을 배웁니다.")
                .beginDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 3, 31))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.FINANCE_INSURANCE)
                .build();
        courseRepository.save(course38);

        Course course39 = Course.builder()
                .tutor(user14)
                .title("영상 편집의 이해")
                .introduction("프리미어와 애프터 이펙트를 사용한 영상 편집입니다.")
                .beginDate(LocalDate.of(2024, 8, 1))
                .endDate(LocalDate.of(2024, 10, 1))
                .state(CourseState.FINISHED)
                .category(CourseCategory.CULTURE_ART_DESIGN_BROADCASTING)
                .build();
        courseRepository.save(course39);

        Course course40 = Course.builder()
                .tutor(user14)
                .title("광고 디자인의 원리")
                .introduction("광고 디자인과 마케팅 원리를 배웁니다.")
                .beginDate(LocalDate.of(2025, 2, 1))
                .endDate(LocalDate.of(2025, 4, 1))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.MATERIALS)
                .build();
        courseRepository.save(course40);

// 강사 15의 강의 (user15)
        Course course41 = Course.builder()
                .tutor(user15)
                .title("스포츠 트레이닝 기초")
                .introduction("스포츠 트레이닝의 기본 이론과 실습입니다.")
                .beginDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 3, 1))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.LAW_POLICE_FIRE_RELIGION_DEFENSE)
                .build();
        courseRepository.save(course41);

        Course course42 = Course.builder()
                .tutor(user15)
                .title("축구 코칭의 기초")
                .introduction("축구 경기와 훈련 기법을 배웁니다.")
                .beginDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2024, 12, 1))
                .state(CourseState.ONGOING)
                .category(CourseCategory.HEALTH_MEDICAL_CARE)
                .build();
        courseRepository.save(course42);

        Course course43 = Course.builder()
                .tutor(user15)
                .title("운동생리학의 이해")
                .introduction("운동이 인체에 미치는 영향을 학습합니다.")
                .beginDate(LocalDate.of(2024, 7, 1))
                .endDate(LocalDate.of(2024, 9, 1))
                .state(CourseState.FINISHED)
                .category(CourseCategory.PRINTED_WOOD_FURNITURE_CRAFTS)
                .build();
        courseRepository.save(course43);

        Course course44 = Course.builder()
                .tutor(user15)
                .title("헬스 트레이닝 프로그램")
                .introduction("헬스 트레이닝 프로그램 설계와 실습입니다.")
                .beginDate(LocalDate.of(2024, 10, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .state(CourseState.ONGOING)
                .category(CourseCategory.SOCIAL_WELFARE_RELIGION)
                .build();
        courseRepository.save(course44);

        Course course45 = Course.builder()
                .tutor(user15)
                .title("스포츠 심리학 개론")
                .introduction("스포츠 심리학의 기초 개념을 학습합니다.")
                .beginDate(LocalDate.of(2025, 2, 1))
                .endDate(LocalDate.of(2025, 4, 1))
                .state(CourseState.NOT_STARTED)
                .category(CourseCategory.DRIVING_TRANSPORTATION)
                .build();
        courseRepository.save(course45);


        // Course 1과 연관된 커리큘럼 5개 생성
        Curriculum curriculum1 = Curriculum.builder()
                .course(course1)  // course1과 연관
                .title("비즈니스 환경 분석")
                .idx(1)
                .build();
        curriculumRepository.save(curriculum1);

        Curriculum curriculum2 = Curriculum.builder()
                .course(course1)
                .title("전략 수립 프로세스 이해")
                .idx(2)
                .build();
        curriculumRepository.save(curriculum2);

        Curriculum curriculum3 = Curriculum.builder()
                .course(course1)
                .title("핵심 역량 분석 및 적용")
                .idx(3)
                .build();
        curriculumRepository.save(curriculum3);

        Curriculum curriculum4 = Curriculum.builder()
                .course(course1)
                .title("비즈니스 전략 실행 계획")
                .idx(4)
                .build();
        curriculumRepository.save(curriculum4);

        Curriculum curriculum5 = Curriculum.builder()
                .course(course1)
                .title("성과 측정 및 개선")
                .idx(5)
                .build();
        curriculumRepository.save(curriculum5);

// Course 2: 마케팅 이론과 실전 커리큘럼 5개 생성
        Curriculum curriculum6 = Curriculum.builder()
                .course(course2)
                .title("마케팅 기본 개념 이해")
                .idx(1)
                .build();
        curriculumRepository.save(curriculum6);

        Curriculum curriculum7 = Curriculum.builder()
                .course(course2)
                .title("소비자 행동 분석")
                .idx(2)
                .build();
        curriculumRepository.save(curriculum7);

        Curriculum curriculum8 = Curriculum.builder()
                .course(course2)
                .title("브랜드 전략 수립")
                .idx(3)
                .build();
        curriculumRepository.save(curriculum8);

        Curriculum curriculum9 = Curriculum.builder()
                .course(course2)
                .title("디지털 마케팅 기법")
                .idx(4)
                .build();
        curriculumRepository.save(curriculum9);

        Curriculum curriculum10 = Curriculum.builder()
                .course(course2)
                .title("마케팅 성과 분석과 개선")
                .idx(5)
                .build();
        curriculumRepository.save(curriculum10);

// Course 3: 팀 리더십과 조직 관리 커리큘럼 5개 생성
        Curriculum curriculum11 = Curriculum.builder()
                .course(course3)
                .title("리더십 이론의 이해")
                .idx(1)
                .build();
        curriculumRepository.save(curriculum11);

        Curriculum curriculum12 = Curriculum.builder()
                .course(course3)
                .title("효과적인 팀 구축 방법")
                .idx(2)
                .build();
        curriculumRepository.save(curriculum12);

        Curriculum curriculum13 = Curriculum.builder()
                .course(course3)
                .title("팀 내 갈등 해결 전략")
                .idx(3)
                .build();
        curriculumRepository.save(curriculum13);

        Curriculum curriculum14 = Curriculum.builder()
                .course(course3)
                .title("의사소통 스킬 향상")
                .idx(4)
                .build();
        curriculumRepository.save(curriculum14);

        Curriculum curriculum15 = Curriculum.builder()
                .course(course3)
                .title("조직 문화와 성과 관리")
                .idx(5)
                .build();
        curriculumRepository.save(curriculum15);

// Course 4: 협상 스킬 마스터 커리큘럼 5개 생성
        Curriculum curriculum16 = Curriculum.builder()
                .course(course4)
                .title("협상 기본 개념")
                .idx(1)
                .build();
        curriculumRepository.save(curriculum16);

        Curriculum curriculum17 = Curriculum.builder()
                .course(course4)
                .title("협상 준비와 전략 수립")
                .idx(2)
                .build();
        curriculumRepository.save(curriculum17);

        Curriculum curriculum18 = Curriculum.builder()
                .course(course4)
                .title("상대방 이해와 설득 기술")
                .idx(3)
                .build();
        curriculumRepository.save(curriculum18);

        Curriculum curriculum19 = Curriculum.builder()
                .course(course4)
                .title("협상 과정에서의 의사소통")
                .idx(4)
                .build();
        curriculumRepository.save(curriculum19);

        Curriculum curriculum20 = Curriculum.builder()
                .course(course4)
                .title("협상 결과의 평가와 개선")
                .idx(5)
                .build();
        curriculumRepository.save(curriculum20);

// Course 5: 디지털 전환 시대의 경영 커리큘럼 5개 생성
        Curriculum curriculum21 = Curriculum.builder()
                .course(course5)
                .title("디지털 기술의 이해")
                .idx(1)
                .build();
        curriculumRepository.save(curriculum21);

        Curriculum curriculum22 = Curriculum.builder()
                .course(course5)
                .title("디지털 전환 성공 사례 분석")
                .idx(2)
                .build();
        curriculumRepository.save(curriculum22);

        Curriculum curriculum23 = Curriculum.builder()
                .course(course5)
                .title("경영 전략 수립과 디지털화")
                .idx(3)
                .build();
        curriculumRepository.save(curriculum23);

        Curriculum curriculum24 = Curriculum.builder()
                .course(course5)
                .title("데이터 활용 경영")
                .idx(4)
                .build();
        curriculumRepository.save(curriculum24);

        Curriculum curriculum25 = Curriculum.builder()
                .course(course5)
                .title("디지털 리더십 개발")
                .idx(5)
                .build();
        curriculumRepository.save(curriculum25);
// 나머지 40개 코스에 대해 각각 5개의 커리큘럼 생성
        for (int courseIdx = 6; courseIdx <= 45; courseIdx++) {
            Course currentCourse = courseRepository.findById((long) courseIdx).orElseThrow(() -> new IllegalArgumentException("asd"));  // Course 객체 가져오기

            for (int idx = 1; idx <= 5; idx++) {
                Curriculum curriculum = Curriculum.builder()
                        .course(currentCourse)
                        .title("Course " + courseIdx + " - Curriculum " + idx)
                        .idx(idx)
                        .build();
                curriculumRepository.save(curriculum);
            }
        }


        Contents content1 = Contents.builder()
                .curriculum(curriculum1)
                .title("비즈니스 환경 분석 - 자료 1")
                .type(ContentType.MATERIAL)
                .idx(1)
                .build();
        contentsRepository.save(content1);

        Contents content2 = Contents.builder()
                .curriculum(curriculum1)
                .title("비즈니스 환경 분석 - 컨텐츠 1")
                .type(ContentType.VIDEO)
                .idx(2)
                .build();
        contentsRepository.save(content2);

// 나머지 224개의 커리큘럼에 대해 2개씩 콘텐츠 생성
        for (long curriculumId = 2; curriculumId <= 225; curriculumId++) {
            Curriculum curriculum = curriculumRepository.findById(curriculumId)
                    .orElseThrow(() -> new RuntimeException("Curriculum not found"));

            Contents contentA = Contents.builder()
                    .curriculum(curriculum)
                    .title("커리큘럼 " + curriculumId + " - 자료 1")
                    .type(ContentType.MATERIAL)
                    .idx(1)
                    .build();
            contentsRepository.save(contentA);

            Contents contentB = Contents.builder()
                    .curriculum(curriculum)
                    .title("커리큘럼 " + curriculumId + " - 컨텐츠 1 ")
                    .type(ContentType.VIDEO)
                    .idx(2)
                    .build();

            contentsRepository.save(contentB);
        }
        // EnrollmentState와 함께 상태 설정
        Enrollment enrollment1 = Enrollment.builder()
                .user(user1)
                .course(course1)
                .state(EnrollmentState.ONPROGRESS)
                .certificateDate(null)  // 아직 수료하지 않은 경우
                .build();
        enrollmentRepository.save(enrollment1);

        Enrollment enrollment2 = Enrollment.builder()
                .user(user1)
                .course(course6)
                .state(EnrollmentState.COMPLETED)
                .certificateDate(LocalDateTime.now().minusDays(1))  // 수료한 경우
                .build();
        enrollmentRepository.save(enrollment2);

        Enrollment enrollment3 = Enrollment.builder()
                .user(user1)
                .course(course11)
                .state(EnrollmentState.FAILED)
                .certificateDate(null)
                .build();
        enrollmentRepository.save(enrollment3);

// User 2 ~ 6에 대해 무작위로 상태와 코스 등록
        Random random = new Random();

        for (int userId = 2; userId <= 6; userId++) {
            User user = userRepository.findById((long) userId).orElseThrow(); // 회원
            User tutor = userRepository.findById((long) userId).orElseThrow();
            for (int i = 0; i < 5; i++) {
                int courseId = random.nextInt(45) + 1; // 1부터 45까지의 랜덤 코스 ID 생성
                Course course = courseRepository.findById((long) courseId).orElseThrow();
                EnrollmentState state = getRandomState();  // 상태 무작위 설정

                LocalDateTime certificateDate =
                        (state == EnrollmentState.COMPLETED) ? LocalDateTime.now().minusDays(5) : null;

                Enrollment enrollment = Enrollment.builder()
                        .user(user)
                        .course(course)
                        .state(state)
                        .certificateDate(certificateDate)
                        .build();
                enrollmentRepository.save(enrollment);
            }
        }
// 무작위 상태를 반환하는 함수


        random = new Random();

        for (int userId = 2; userId <= 6; userId++) {
            User user = userRepository.findById((long) userId).orElseThrow(); // 회원
            User tutor = userRepository.findById((long) userId).orElseThrow();
            for (int i = 0; i < 5; i++) {
                int courseId = random.nextInt(45) + 1; // 1부터 45까지의 랜덤 코스 ID 생성
                Course course = courseRepository.findById((long) courseId).orElseThrow();
                EnrollmentState state = getRandomState();  // 상태 무작위 설정

                LocalDateTime certificateDate =
                        (state == EnrollmentState.COMPLETED) ? LocalDateTime.now().minusDays(5) : null;

                Enrollment enrollment = Enrollment.builder()
                        .user(user)
                        .course(course)
                        .state(state)
                        .certificateDate(certificateDate)
                        .build();
                enrollmentRepository.save(enrollment);
            }

            // 각 사용자당 3개의 게시글 생성
            for (int i = 0; i < 3; i++) {
                long courseId = random.nextInt(45) + 1;  // 무작위로 코스 선택
                Course course = courseRepository.findById(courseId)
                        .orElseThrow(() -> new RuntimeException("Course not found"));

                BoardType boardType;
                if (userId >= 7 && userId <= 16) {
                    boardType = BoardType.NOTICE; // 강사는 NOTICE만 가능
                } else {
                    boardType = BoardType.values()[random.nextInt(2)]; // 일반 유저는 QNA, NORMAL 중 선택
                }

                // CourseBoard 생성 및 저장
                CourseBoard board = CourseBoard.builder()
                        .course(course)
                        .user(user)
                        .title("게시글 제목 " + (i + 1))
                        .content("게시글 내용 " + (i + 1))
                        .type(boardType)
                        .build();
                courseBoardRepository.save(board);  // 저장

                // 각 게시글에 대해 2개의 댓글 생성
                if (boardType != BoardType.QNA || (userId >= 7 && userId <= 16)) { // QNA는 강사만 답변 가능
                    for (int j = 0; j < 2; j++) {
                        User commentUser = userRepository.findById((long) (random.nextInt(15) + 1))
                                .orElseThrow(() -> new RuntimeException("User not found"));

                        // CourseComment 생성 및 저장
                        CourseComment comment = CourseComment.builder()
                                .courseBoard(board)
                                .user(commentUser)
                                .content("댓글 내용 " + (j + 1))
                                .build();
                        courseCommentRepository.save(comment);  // 저장
                    }
                }
            }
        }
        List<Course> user1Course = courseRepository.findAllByEnrollments_User(user1);
        LiveState[] liveStates = LiveState.values();

        User[] instructors = {user7, user8, user9};
        for (int i = 0; i < instructors.length; i++) {
            User instructor = instructors[i];
            LiveStreaming liveStreaming = LiveStreaming.builder()
                    .course(user1Course.get(i))
                    .title("Live Streaming " + user1Course.get(i).getTitle())
                    .startTime(LocalDateTime.now().plusDays(i))
                    .endTime(LocalDateTime.now().plusDays(i).plusHours(1))
                    .state(liveStates[i])
                    .build();
            liveStreamingRepository.save(liveStreaming);
        }

        for (int i = 0; i < 20; i++) {
            int randomCourseIdForLive = random.nextInt(45) + 1;
            Course randomCourse = courseRepository.findById((long) randomCourseIdForLive).orElseThrow(() -> new RuntimeException("Course not found"));
            LiveState liveState = liveStates[i % liveStates.length];
            LiveStreaming liveStreaming = LiveStreaming.builder()
                    .course(randomCourse)
                    .title("Live Streaming " + randomCourse.getTitle())
                    .startTime(LocalDateTime.now().plusDays(i))
                    .endTime(LocalDateTime.now().plusDays(i).plusHours(1))
                    .state(liveState)
                    .build();
            liveStreamingRepository.save(liveStreaming);
        }
    }

}